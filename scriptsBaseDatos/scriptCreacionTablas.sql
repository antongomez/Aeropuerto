--Función que devuelve 1 si la fecha es en primavera, 2 para verano, 3 para otoño y 4 para invierno
create or replace function Estacion (fecha timestamp)
returns integer as $$
declare mes integer; 
		dia integer;
begin
mes=EXTRACT(MONTH FROM cast(fecha as date));
dia=EXTRACT(DAY FROM cast(fecha as date));

if(mes=4 or mes=5 or (mes=3 and dia>=21) or (mes=6 and dia<21)) then return 1;
elseif (mes=7 or mes=8 or (mes=6 and dia>=21) or (mes=9 and dia<21)) then return 2;
elseif (mes=10 or mes=11 or (mes=9 and dia>=21) or (mes=12 and dia<21)) then return 3;
elseif (mes=1 or mes=2 or (mes=12 and dia>=21) or (mes=3 and dia<21)) then return 4;
end if; 
end;
$$ language plpgsql;

-- Función para las contraseñas
CREATE EXTENSION pgcrypto;
-- función para comprobar que el dni es correcto
create or replace function valida_dni(dni char) returns boolean as $vd$ 
declare
	letrasValidas char(23) := 'TRWAGMYFPDXBNJZSQVHLCKE';
	letraCorrecta char;
	letra char := SUBSTR(dni, length(dni), 1);
	dniCorrecto char(8) := SUBSTR(dni, 1, length(dni)-1);
	letraLeida char := substr(letra, 1);
	resto integer;
begin
	resto := mod(cast(dniCorrecto as integer),23);
	letraCorrecta := SUBSTR(letrasValidas, resto+1, 1);
	if (letraCorrecta = letraLeida) then
	return true;
	else
 	return false;
	end if;
end;
$vd$ Language plpgsql;

create table usuario
(
    dni char(9) not null primary key,
    id varchar(20) unique not null,
    correoElectronico varchar(30) unique not null,
    contrasenha text not null,
    nombre varchar(40) not null,
    primerApellido varchar(40) not null,
    segundoApellido varchar(40) not null,
    paisProcedencia varchar(30) not null,
    telefono integer not null,
    sexo char(1) not null,
    check (sexo='m' or sexo='h' or sexo='o'),
    check(valida_dni(dni) = true)
);
create table personalExterno
(
	usuario char(9) not null primary key,
	estarDentro boolean not null default false,
    
	foreign key (usuario) references usuario (dni)
		on delete cascade on update cascade
);

create table personalLaboral
(
    usuario char(9) not null,
    labor varchar(30) not null,
    descripcionTarea varchar(150) not null,
    fechaInicio timestamp not null default CURRENT_TIMESTAMP,
    
    primary key(usuario),
    foreign key(usuario) references usuario(dni)
    	on delete cascade on update cascade
    
);

create table historialTrabajo
(
    personalLaboral char(9),
    fechaEntrada timestamp not null default CURRENT_TIMESTAMP,
    fechaSalida timestamp,
    
    primary key (personalLaboral, fechaEntrada),
    foreign key (personalLaboral) references personalLaboral(usuario)
    	on delete cascade on update cascade
); 

create table administrador
(
    usuario char(9) not null,
    fechaInicio timestamp not null default current_timestamp,
    curriculum varchar(500),
    
    primary key(usuario),
    foreign key(usuario) references usuario(dni)
    	on delete cascade on update cascade
);

create table terminal
(
    numero integer not null primary key,
    primeraPuerta integer not null,
    ultimaPuerta integer not null
);

--Funcion que crea/borra secuencias que controlan o numero dos codigos de tendas
create or replace function controla_secuencias_terminal() returns trigger as $cst$
declare
	tienda integer;
begin
  case TG_OP
    when 'INSERT' then
      execute 'create sequence seq_codigo_tiendas_'||cast(NEW.numero as text);
      return new;
    when 'UPDATE' then
      IF OLD.terminal <> NEW.terminal THEN
	     tienda:=nextval('seq_codigo_tiendas_'||cast(OLD.numero as text));
	     execute 'drop sequence seq_codigo_tiendas_'||cast(OLD.numero as text);
	     execute 'create sequence seq_codigo_tiendas_'||cast(NEW.numero as text)|| ' start '||cast(tienda as text);
      END IF;
      return new;
    when 'DELETE' then 
      execute 'drop sequence seq_codigo_tiendas_'||cast(OLD.numero as text);
      return old;
    else null;
  end case;
end;
$cst$ Language plpgsql;

--Disparador que activa a funcion controla_sequencia_terminal
create trigger afterTiendas after insert or update or delete on terminal
for each row execute procedure controla_secuencias_terminal();

create table parking
(
    terminal integer,
    piso integer,
    numPlazas integer not null,
    primary key (terminal, piso),
    foreign key (terminal) references terminal(numero)
    	on delete restrict on update cascade
);

create or replace function comprobarPlazaParking
(
 numTerminal integer, numPiso integer, plaza integer
)
returns boolean as $$

declare
	numPlazas integer;
begin
	numPlazas=(select p.numPlazas
	from parking as p
	where p.terminal = numTerminal
	  and p.piso = numPiso);


	if (plaza between 1 and numPlazas) then
		return true;
	else 
		return false;
	end if;
end;
$$ language plpgsql;

create or replace function plazaDisponible
(
 numTerminal integer, numPiso integer, plaza integer, fechaInicio timestamp, fechaSalida timestamp
)
returns boolean as $$

begin
	return not exists(select *
	from reservarParking r
	where r.terminal=numTerminal and r.piso=numPiso and r.numPlaza=plaza
	and fechaInicio<=r.fechaFin and fechaSalida>=r.fechaEntrada);
end;
$$ language plpgsql;

create table reservarParking
(
    usuario char(9) not null,
    terminal integer not null,
    piso integer not null,
    numPlaza integer not null,
    fechaEntrada timestamp not null,
    fechaFin timestamp not null,
    matricula char(7) not null,
    
    check(fechaEntrada<=fechaFin),
	check(comprobarPlazaParking(terminal, piso, numPlaza)),
	check(plazaDisponible(terminal,piso,numPlaza,fechaEntrada,fechaFin)), --comprobamos que la plaza esté disponible en el momento de insertar la reserva
    primary key (terminal, piso, numPlaza, fechaEntrada),
    foreign key (usuario) references usuario(dni)
    	on delete cascade on update cascade,
    foreign key (terminal, piso) references parking (terminal, piso)
    	on delete cascade on update cascade
);

create table tienda
(
    terminal integer,
    codigo integer,
    tipoVentas varchar(20) not null,
    nombre varchar(20) not null,

    primary key (terminal, codigo) ,
    foreign key (terminal)  references terminal(numero)
        on delete restrict on update cascade
);

--Funcion que introduce os codigos nas tendas
create or replace function introduce_codigo_tienda() returns trigger as $cst$
begin
  new.codigo:=nextval('seq_codigo_tiendas_'||cast(new.terminal as text));
  return new;
end;
$cst$ Language plpgsql;

--Disparador que activa a funcion introduce_codigo_tienda
create trigger beforeInsertTienda before insert on tienda
for each row execute procedure introduce_codigo_tienda();

create table cocheAlquiler
(
    matricula char(7) not null,
    modelo varchar(100) not null,
    caballos integer,
    nPlazas integer not null,
    nPuertas integer not null,
    precioPorDia float not null,
    tipoCombustible varchar(15) not null,
    retirado boolean not null default false,
    
    primary key(matricula)
);
create or replace function cocheDisponibleAlq
(
 usu char(9),coche char(7),fechaInicio timestamp, fechaSalida timestamp
)
returns boolean as $$

begin
	return (not exists((select r.cocheAlquiler
	from reservar r
	where r.cocheAlquiler=coche and r.usuario!=usu
	and fechaInicio<=r.fechaFinReserva and fechaSalida>=r.fechaInicioReserva)
			UNION (select a.matricula
	from alquilar a
	where a.matricula=coche and a.usuario!=usu
	and fechaInicio<=a.fechaTeoricaDevolucion and fechaSalida>=a.fechaAlquiler)));
end;
$$ language plpgsql;
create or replace function cocheDisponibleRes
(
 coche char(7),fechaInicio timestamp, fechaSalida timestamp
)
returns boolean as $$

begin
	return (not exists((select r.cocheAlquiler
	from reservar r
	where r.cocheAlquiler=coche
	and fechaInicio<=r.fechaFinReserva and fechaSalida>=r.fechaInicioReserva)
			UNION (select a.matricula
	from alquilar a
	where a.matricula=coche
	and fechaInicio<=a.fechaTeoricaDevolucion and fechaSalida>=a.fechaAlquiler)));
end;
$$ language plpgsql;

create table alquilar
(
    fechaAlquiler timestamp not null,
    usuario char(9) not null,
    matricula char(7) not null,
    fechaTeoricaDevolucion timestamp not null,
    fechaDevolucion timestamp,
    
    check(fechaAlquiler<=fechaTeoricaDevolucion),
	check(cocheDisponibleAlq(usuario,matricula, fechaAlquiler, fechaTeoricaDevolucion)),
    primary key (fechaAlquiler, usuario, matricula),
    foreign key (usuario) references usuario (dni)
    	on delete restrict on update cascade, --no permitimos borrar un usuario si tiene algún alquiler
    foreign key (matricula) references cocheAlquiler (matricula)
    	on delete restrict on update cascade

);


create table reservar
(
    fechaInicioReserva timestamp not null default current_timestamp,
    fechaFinReserva timestamp not null,
    
    usuario char(9) not null,
    cocheAlquiler char(7) not null,
    
    check(fechaInicioReserva<=fechaFinReserva),
	check(cocheDisponibleRes(cocheAlquiler, fechaInicioReserva, fechaFinReserva)),
    primary key(usuario,cocheAlquiler,fechaInicioReserva),
    foreign key(usuario) references usuario (dni)
    	on delete cascade on update cascade,
    foreign key(cocheAlquiler) references cocheAlquiler (matricula)
   		on delete cascade on update cascade
    
);


create table modeloAvion
(
    nombre varchar(30) primary key,
    capacidadNormal integer not null,
    capacidadPremium integer not null,
    consumo float not null,
    empresaFabricante varchar(20) not null
);


create table aerolinea 
(
    nombre varchar(25) not null primary key,
    paisSede varchar(25) not null,
    precioBaseMaleta float not null,
    pesoBaseMaleta float not null
);

create table avion
(
    codigo char(5) primary key,  
    modeloAvion varchar(30) not null,
    aerolinea varchar(25) not null,
    anhoFabricacion int not null,
    retirado boolean not null default false,
    foreign key (modeloAvion) references modeloAvion(nombre)
    	on delete restrict on update cascade,
    foreign key (aerolinea) references aerolinea(nombre)
    	on delete restrict on update cascade
);

create or replace function comprobarPuertaCorrecta
(
 terminal integer, puerta integer
)
returns boolean as $$

declare
	primeraPuerta integer;
	ultimaPuerta integer;
begin
	primeraPuerta=(select t.primeraPuerta
	from terminal as t
	where t.numero=terminal);
	
	ultimaPuerta=(select t.ultimaPuerta
	from terminal as t
	where t.numero=terminal);
	if (puerta is not null) then
		if (puerta between primeraPuerta and ultimaPuerta) then
			return true;
		else 
			return false;
		end if;
    else
    	return true;
    end if;
end;
$$ language plpgsql;

create table vuelo
(
    numVuelo char(5) not null,
    origen varchar(30) not null,
    destino varchar(30) not null,
    fechaSalidaTeorica timestamp not null,
    fechaSalidaReal timestamp not null,
    fechaLlegadaTeorica timestamp not null,
    fechaLlegadaReal timestamp not null,
    precioActual float not null,
    puertaEmbarque integer,
    cancelado boolean not null default false,
    terminal integer not null,
    avion char(5) not null,
    
    check(comprobarPuertaCorrecta(terminal, puertaEmbarque)),
    primary key (numVuelo),
    foreign key (terminal) references terminal(numero)
    on delete restrict on update cascade,
    foreign key (avion) references avion(codigo)
    on delete restrict on update cascade

);

create or replace function comprobarAsientoCorrecto
(
 vuelo char(5), tipoAsiento varchar(15), numAsiento integer
 )
returns boolean as $$

declare
	numNormal integer;
	numPremium integer;
begin
	numNormal=(select ma.capacidadNormal
	from vuelo v, avion a, modeloAvion ma
	where v.avion=a.codigo and a.modeloAvion=ma.nombre
	and v.numVuelo=vuelo);
	
	numPremium=(select ma.capacidadPremium
	from vuelo v, avion a, modeloAvion ma
	where v.avion=a.codigo and a.modeloAvion=ma.nombre
	and v.numVuelo=vuelo);

	if (tipoAsiento='normal') then
		if(numAsiento between 1 and numNormal) then
			return true;
		else
			return false;
		end if;
	else 
		if(numAsiento between (numNormal+1) and (numPremium + numNormal)) then
			return true;
		else
			return false;
		end if;

	end if;
end;
$$ language plpgsql;

create table comprarBillete
(
    usuario char(9) not null,
    vuelo char(5) not null,
    fechaCompra timestamp not null default CURRENT_TIMESTAMP,
    numAsiento integer not null,
    tipoAsiento varchar(15) not null default 'normal',
    numMaletasReserva integer not null default 0,
    tenerAcompanhante boolean not null default false,
    pasarControl boolean not null default false,
    preciobillete float not null,
    
    unique(vuelo,numAsiento),--comprobamos que el asiento elegido esté libre en el momento de la inserción
    check(tipoAsiento='premium' or tipoAsiento='normal'),
    check(comprobarAsientoCorrecto(vuelo, tipoAsiento, numAsiento)),
    primary key (usuario, vuelo),
    foreign key (usuario) references usuario (dni)
    on delete restrict on update cascade,--no permitimos eliminar un usuario si ya ha comprado algún billete
    foreign key (vuelo) references vuelo (numVuelo)
    on delete restrict on update cascade

);

create sequence seq_id_maleta;

create table facturarMaleta
(
    idmaleta integer default nextval('seq_id_maleta') not null primary key,
    usuario char(9),
    vuelo char(5),
    peso float not null,
    fecha timestamp not null default CURRENT_TIMESTAMP,

    foreign key (usuario,vuelo) references comprarBillete(usuario,vuelo)
    	on delete cascade on update cascade

);
