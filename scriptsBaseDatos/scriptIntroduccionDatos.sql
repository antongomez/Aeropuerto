--Insertase usuarios
insert into usuario values (
'48116361Q', 'eliPita', 'eliPita@gmail.com', crypt('eli', gen_salt('md5')), 'Eliseo','Pita','Vilariño','Noruega','981666789','m'
); --Administrador
insert into usuario values (
'49204425L', 'antong', 'antong@gmail.com', crypt('ant', gen_salt('md5')), 'Anton','Gomez','Lopez','Galicia','604025148','o'
);--Personal Laboral
insert into usuario values (
'12345678Z', 'crisl', 'crisl@gmail.com', crypt('cri', gen_salt('md5')), 'Cristina','Lopez','Amado','Portugal','981234666','m'
);--Administrador
insert into usuario values (
'45678913Q', 'danim', 'danim@gmail.com', crypt('dan', gen_salt('md5')), 'Daniel','Mosteiro','Angueira','Francia','981234166','h'
); --Personal Laboral

insert into usuario values (
'59518667A', 'fernandof', 'fernandof@gmail.com', crypt('fer', gen_salt('md5')), 'Fernando','Fraga','Bocheiras','Italia','981234610','h'
);
insert into usuario values (
'65892114N', 'minm', 'minom@gmail.com', crypt('min', gen_salt('md5')), 'Mino','Moral','Fernández','España','900234610','h'
);
insert into usuario values (
'09308274J', 'franciscog', 'franciscog@gmail.com', crypt('fra', gen_salt('md5')), 'Francisco','Gómez','Álvarez','España','611234610','h'
);
insert into usuario values (
'60749238M', 'monican', 'mincan@gmail.com', crypt('mon', gen_salt('md5')), 'Mónica','Naranjo','de la Fuente','España','611234178','m'
);--Personal Externo

--Insertase persoalExterno
insert into personalexterno values('60749238M', false);

--Insertase persoal Laboral
insert into personallaboral(usuario, labor, descripcionTarea)
	values('45678913Q', 'controlador', 'controla los accesos de los pasajeros');
insert into personallaboral(usuario, labor, descripcionTarea)
	values('49204425L', 'controlador', 'controla los accesos de los pasajeros');

--Insertase historial de Traballo
insert into historialtrabajo(personalLaboral)
	values('45678913Q');
insert into historialtrabajo(personalLaboral)
	values('49204425L');

--Insertase administrador
insert into administrador(usuario, curriculum) values('48116361Q', '- Traballei como becario en Repsol');
insert into administrador(usuario, curriculum) values('12345678Z', '- Trabellei na NASA durante 3 anos.');

--Insertase terminal
insert into terminal values (1, 1, 20);
insert into terminal values (2, 21, 40);
insert into terminal values (3, 41, 60);

--Insertase parking
insert into parking values(1, 1, 50);
insert into parking values(1, 2, 50);
insert into parking values(2, 1, 75);
insert into parking values(2, 2, 25);
insert into parking values(3, 1, 100);

--Insertase reservarParking
insert into reservarparking values
(
	'12345678Z', 1, 1, 50, '2020-04-20', '2020-04-29', '6364CDD'
);

--Insertase tiendas
insert into tienda(terminal, tipoVentas, nombre) values
(
    1, 'moda y complementos', 'Calvin Klein' 
);
insert into tienda(terminal, tipoVentas, nombre) values
(
    1, 'moda y complementos', 'Pull & Bear' 
);
insert into tienda(terminal, tipoVentas, nombre) values
(
    1, 'moda y complementos', 'Bershka' 
);
insert into tienda(terminal, tipoVentas, nombre) values
(
    1, 'deportes', 'Nike' 
);
insert into tienda(terminal, tipoVentas, nombre) values
(
    1, 'joyería y relojería', 'Tous' 
);
insert into tienda(terminal, tipoVentas, nombre) values
(
    1, 'alimentación', 'Baggeterie Boulevard' 
);
insert into tienda(terminal, tipoVentas, nombre) values
(
    1, 'alimentación', 'Burger King' 
);
insert into tienda(terminal, tipoVentas, nombre) values
(
    1, 'farmacia', 'Farmacia' 
);
insert into tienda(terminal, tipoVentas, nombre) values
(
    1, 'moda y complementos', 'Ray-Ban' 
);


insert into tienda(terminal, tipoVentas, nombre) values
(
    2, 'joyería y relojería', 'Swarovski' 
);

insert into tienda(terminal, tipoVentas, nombre) values
(
    2, 'moda y complementos', 'Adolfo Domínguez' 
);
insert into tienda(terminal, tipoVentas, nombre) values
(
    2, 'deportes', 'Adidas' 
);
insert into tienda(terminal, tipoVentas, nombre) values
(
    2, 'moda y complementos', 'Calzedonia' 
);
insert into tienda(terminal, tipoVentas, nombre) values
(
    2, 'moda y complementos', 'Zara' 
);
insert into tienda(terminal, tipoVentas, nombre) values
(
    2, 'alimentación', 'Chocolat Factory' 
);
insert into tienda(terminal, tipoVentas, nombre) values
(
    2, 'alimentación', 'Farga' 
);
insert into tienda(terminal, tipoVentas, nombre) values
(
    2, 'farmacia', 'Farmacia' 
);
insert into tienda(terminal, tipoVentas, nombre) values
(
    2, 'moda y complementos', 'Mango' 
);


insert into tienda(terminal, tipoVentas, nombre) values
(
    3, 'moda y complementos', 'Massimo Dutti' 
);
insert into tienda(terminal, tipoVentas, nombre) values
(
    3, 'moda y complementos', 'Geox' 
);
insert into tienda(terminal, tipoVentas, nombre) values
(
    3, 'moda y complementos', 'Calzedonia' 
);
insert into tienda(terminal, tipoVentas, nombre) values
(
    3, 'moda y complementos', 'Levis' 
);
insert into tienda(terminal, tipoVentas, nombre) values
(
    3, 'moda y complementos', 'Victoria`s Secret' 
);
insert into tienda(terminal, tipoVentas, nombre) values
(
    3, 'mundo infantil', 'Tutti Frutti' 
);
insert into tienda(terminal, tipoVentas, nombre) values
(
    3, 'regalos y decoración', 'Thinking ETSE' 
);
insert into tienda(terminal, tipoVentas, nombre) values
(
    3, 'regalos y decoración', 'Superskunk' 
);
insert into tienda(terminal, tipoVentas, nombre) values
(
    3, 'alimentación', 'Sibarium' 
);
insert into tienda(terminal, tipoVentas, nombre) values
(
    3, 'alimentación', 'Farga' 
);
insert into tienda(terminal, tipoVentas, nombre) values
(
    3, 'alimentación', 'Baggeterie Boulevard' 
);
insert into tienda(terminal, tipoVentas, nombre) values
(
    3, 'farmacia', 'Farmacia' 
);

--Insertar en cochealquiler
insert into cochealquiler values
(
    '8432DFG', 'Seat Ibiza', 75, 5, 5, 30, 'Gasolina', 'false'
);
insert into cochealquiler values
(
    '9127BBN', 'Renault Clio', 72, 5, 3, 30, 'Gasolina', 'false'
);
insert into cochealquiler values
(
    '7385CFT', 'Audi A3', 160, 5, 5, 30, 'Diesel', 'false'
);

--Insertar en alquilar
insert into alquilar(fechaAlquiler, usuario, matricula, fechateoricadevolucion) values
(
    '2020-04-20', '12345678Z', '7385CFT', '2020-04-29'
);

--Insertar en reservar
insert into reservar values('2020-06-20', '2020-06-30', '49204425L', '8432DFG');


--Insertase en modeloAvion
insert into modeloavion values('Boeing 747',500,29,800,'Boeing');
insert into modeloavion values('Airbus A380',800,50,1200,'Airbus');
insert into modeloavion values('Airbus A350',310,38,500,'Airbus');
insert into modeloavion values('Airbus A319',141,0,495,'Airbus');
insert into modeloavion values('Beechraft King Air 350',0,6,250,'Beechraft');

--Insertase en aerolinea
insert into aerolinea values('Vueling','España',10,12);
insert into aerolinea values('Iberia','España',11,13);
insert into aerolinea values('Ryanair','Irlanda',10,10);
insert into aerolinea values('Czech Airlines','República Checa',8,10);
insert into aerolinea values('Turkish Airlines','Turquía',20,30);
insert into aerolinea values('Europair','España',40,50);

--Insertase en avion
insert into avion values(1000,'Airbus A350','Vueling',2018,false);
insert into avion values(1001,'Airbus A350','Vueling',2016,false);
insert into avion values(603,'Airbus A319','Vueling',2010,true);

insert into avion values(1002,'Airbus A350','Iberia',2019,false);
insert into avion values(1003,'Boeing 747','Iberia',2015,false);
insert into avion values(1004,'Airbus A380','Iberia',2019,false);

insert into avion values(1005,'Airbus A380','Turkish Airlines',2018,false);

insert into avion values(1006,'Airbus A319','Czech Airlines',2014,false);
insert into avion values(1007,'Airbus A350','Czech Airlines',2017,false);

insert into avion values(1008,'Airbus A319','Ryanair',2013,false);
insert into avion values(1009,'Airbus A350','Ryanair',2018,false);

insert into avion values(900,'Beechraft King Air 350','Europair',2019,false);

insert into vuelo values('964','Folgoso do Courel','Milán','2020-02-12 10:00','2020-02-12 10:00','2020-02-12 13:20','2020-02-12 13:20','90.0',null,'false','1','603');
insert into vuelo values('965','Vannes','Folgoso do Courel','2020-02-15 12:00','2020-02-15 12:00','2020-02-15 14:00','2020-02-15 14:00','60.0',null,'false','1','603');
insert into vuelo values('966','Barcelona','Folgoso do Courel','2020-06-01 15:45','2020-06-01 15:45','2020-06-01 17:00','2020-06-01 17:00','80.0',null,'false','1','1000');
insert into vuelo values('967','Folgoso do Courel','Barcelona','2020-06-15 20:30','2020-06-15 20:30','2020-06-15 22:00','2020-06-15 22:00','90.0',null,'false','1','1001');
insert into vuelo values('968','Folgoso do Courel','Santiago de Compostela','2020-11-03 00:17','2020-11-03 00:17','2020-11-03 02:45','2020-11-03 02:45','396.2396',null,'false','3','1006');
insert into vuelo values('969','Folgoso do Courel','Ámsterdam','2020-07-04 22:57','2020-07-04 22:57','2020-07-05 02:21','2020-07-05 02:21','354.1093',null,'false','1','1009');
insert into vuelo values('970','Folgoso do Courel','Derry','2020-10-05 01:37','2020-10-05 01:37','2020-10-05 02:54','2020-10-05 02:54','408.2813',null,'false','1','1005');
insert into vuelo values('971','Folgoso do Courel','Lisboa','2020-07-06 13:13','2020-07-06 13:13','2020-07-06 17:39','2020-07-06 17:39','321.74588',null,'false','2','1008');
insert into vuelo values('972','Folgoso do Courel','Santiago de Compostela','2020-05-07 16:45','2020-05-07 16:45','2020-05-07 21:50','2020-05-07 21:50','426.50705',null,'false','3','1006');
insert into vuelo values('973','Folgoso do Courel','París','2020-10-08 16:15','2020-10-08 16:15','2020-10-08 18:27','2020-10-08 18:27','301.02264',null,'false','2','1003');
insert into vuelo values('974','Folgoso do Courel','Praga','2020-07-09 22:09','2020-07-09 22:09','2020-07-09 23:30','2020-07-09 23:30','538.43475',null,'false','2','1004');
insert into vuelo values('975','Folgoso do Courel','París','2020-07-10 13:16','2020-07-10 13:16','2020-07-10 15:29','2020-07-10 15:29','326.09836',null,'false','3','1003');
insert into vuelo values('976','Folgoso do Courel','Santiago de Compostela','2020-07-11 23:36','2020-07-11 23:36','2020-07-12 03:51','2020-07-12 03:51','497.84955',null,'false','1','1007');
insert into vuelo values('977','Folgoso do Courel','Dublin','2020-11-12 21:07','2020-11-12 21:07','2020-11-12 22:12','2020-11-12 22:12','304.94104',null,'false','3','1009');
insert into vuelo values('978','Folgoso do Courel','Santiago de Compostela','2020-09-13 06:46','2020-09-13 06:46','2020-09-13 09:52','2020-09-13 09:52','506.79266',null,'false','1','1001');
insert into vuelo values('979','Folgoso do Courel','Praga','2020-08-14 19:13','2020-08-14 19:13','2020-08-14 20:14','2020-08-14 20:14','301.59793',null,'false','1','1005');
insert into vuelo values('980','Folgoso do Courel','Lisboa','2020-05-15 00:09','2020-05-15 00:09','2020-05-15 04:37','2020-05-15 04:37','443.2042',null,'false','3','1002');
insert into vuelo values('981','Folgoso do Courel','Dublin','2020-10-16 21:31','2020-10-16 21:31','2020-10-17 02:01','2020-10-17 02:01','507.78384',null,'false','1','1006');
insert into vuelo values('982','Folgoso do Courel','Ámsterdam','2020-11-17 00:16','2020-11-17 00:16','2020-11-17 05:33','2020-11-17 05:33','502.92706',null,'false','1','1006');
insert into vuelo values('983','Folgoso do Courel','Lisboa','2020-10-18 07:12','2020-10-18 07:12','2020-10-18 08:34','2020-10-18 08:34','441.6105',null,'false','3','1006');
insert into vuelo values('984','Folgoso do Courel','Santiago de Compostela','2020-10-19 04:30','2020-10-19 04:30','2020-10-19 09:31','2020-10-19 09:31','517.45386',null,'false','2','1007');
insert into vuelo values('985','Folgoso do Courel','Dublin','2020-07-20 09:52','2020-07-20 09:52','2020-07-20 12:52','2020-07-20 12:52','486.09134',null,'false','1','1006');
insert into vuelo values('986','Folgoso do Courel','Santiago de Compostela','2020-09-21 00:06','2020-09-21 00:06','2020-09-21 04:34','2020-09-21 04:34','373.54254',null,'false','3','1005');
insert into vuelo values('987','Folgoso do Courel','Praga','2020-07-22 17:11','2020-07-22 17:11','2020-07-22 22:32','2020-07-22 22:32','363.99005',null,'false','3','1000');
insert into vuelo values('988','Folgoso do Courel','Lisboa','2020-08-23 01:11','2020-08-23 01:11','2020-08-23 05:16','2020-08-23 05:16','459.09842',null,'false','1','1006');
insert into vuelo values('989','Folgoso do Courel','París','2020-06-24 07:55','2020-06-24 07:55','2020-06-24 09:05','2020-06-24 09:05','433.52658',null,'false','3','1000');
insert into vuelo values('990','Folgoso do Courel','Ámsterdam','2020-11-25 10:49','2020-11-25 10:49','2020-11-25 11:59','2020-11-25 11:59','324.03244',null,'false','3','1003');
insert into vuelo values('991','Folgoso do Courel','Vigo','2020-07-26 08:29','2020-07-26 08:29','2020-07-26 09:59','2020-07-26 09:59','384.2299',null,'false','2','1005');
insert into vuelo values('992','Folgoso do Courel','Dublin','2020-06-27 16:55','2020-06-27 16:55','2020-06-27 22:04','2020-06-27 22:04','340.46582',null,'false','1','1000');
insert into vuelo values('993','Vigo','Folgoso do Courel','2020-06-03 07:18','2020-06-03 07:18','2020-06-03 09:25','2020-06-03 09:25','529.3171',null,'false','2','1000');
insert into vuelo values('994','Vigo','Folgoso do Courel','2020-09-04 06:12','2020-09-04 06:12','2020-09-04 10:33','2020-09-04 10:33','495.514',null,'false','2','1004');
insert into vuelo values('995','Barcelona','Folgoso do Courel','2020-11-05 12:40','2020-11-05 12:40','2020-11-05 14:40','2020-11-05 14:40','476.29584',null,'false','2','1004');
insert into vuelo values('996','Barcelona','Folgoso do Courel','2020-05-06 18:14','2020-05-06 18:14','2020-05-06 23:39','2020-05-06 23:39','508.0983',null,'false','1','1006');
insert into vuelo values('997','Lisboa','Folgoso do Courel','2020-11-07 06:30','2020-11-07 06:30','2020-11-07 11:37','2020-11-07 11:37','336.10883',null,'false','3','1003');
insert into vuelo values('998','Dublin','Folgoso do Courel','2020-10-08 23:03','2020-10-08 23:03','2020-10-09 04:26','2020-10-09 04:26','489.0029',null,'false','2','1009');
insert into vuelo values('999','Derry','Folgoso do Courel','2020-07-09 01:24','2020-07-09 01:24','2020-07-09 06:29','2020-07-09 06:29','304.07166',null,'false','2','1005');
insert into vuelo values('1000','Vannes','Folgoso do Courel','2020-05-10 02:03','2020-05-10 02:03','2020-05-10 07:21','2020-05-10 07:21','499.70926',null,'false','2','1008');
insert into vuelo values('1001','Vigo','Folgoso do Courel','2020-05-11 06:56','2020-05-11 06:56','2020-05-11 08:22','2020-05-11 08:22','531.7709',null,'false','1','1005');
insert into vuelo values('1002','Dublin','Folgoso do Courel','2020-09-12 13:30','2020-09-12 13:30','2020-09-12 17:49','2020-09-12 17:49','394.9443',null,'false','3','1007');
insert into vuelo values('1003','Praga','Folgoso do Courel','2020-10-13 01:28','2020-10-13 01:28','2020-10-13 05:48','2020-10-13 05:48','469.08627',null,'false','3','1006');
insert into vuelo values('1004','Ámsterdam','Folgoso do Courel','2020-05-14 18:24','2020-05-14 18:24','2020-05-14 23:34','2020-05-14 23:34','546.2116',null,'false','3','1002');
insert into vuelo values('1005','Londres','Folgoso do Courel','2020-05-15 19:52','2020-05-15 19:52','2020-05-15 22:20','2020-05-15 22:20','398.84775',null,'false','1','1000');
insert into vuelo values('1006','Lisboa','Folgoso do Courel','2020-08-16 20:29','2020-08-16 20:29','2020-08-16 22:35','2020-08-16 22:35','360.71994',null,'false','1','1008');
insert into vuelo values('1007','Vannes','Folgoso do Courel','2020-11-17 07:43','2020-11-17 07:43','2020-11-17 08:53','2020-11-17 08:53','447.52148',null,'false','3','1004');
insert into vuelo values('1008','Barcelona','Folgoso do Courel','2020-06-18 02:57','2020-06-18 02:57','2020-06-18 06:08','2020-06-18 06:08','321.7658',null,'false','1','1000');
insert into vuelo values('1009','Dublin','Folgoso do Courel','2020-10-19 03:11','2020-10-19 03:11','2020-10-19 07:12','2020-10-19 07:12','408.61038',null,'false','1','1001');
insert into vuelo values('1010','Vigo','Folgoso do Courel','2020-09-20 23:47','2020-09-20 23:47','2020-09-21 01:53','2020-09-21 01:53','497.9951',null,'false','3','1001');
insert into vuelo values('1011','Ámsterdam','Folgoso do Courel','2020-10-21 23:02','2020-10-21 23:02','2020-10-22 04:15','2020-10-22 04:15','402.42523',null,'false','3','1009');
insert into vuelo values('1012','Vannes','Folgoso do Courel','2020-09-22 08:47','2020-09-22 08:47','2020-09-22 09:53','2020-09-22 09:53','370.58804',null,'false','2','1007');
insert into vuelo values('1013','Derry','Folgoso do Courel','2020-06-23 21:21','2020-06-23 21:21','2020-06-23 23:49','2020-06-23 23:49','457.00174',null,'false','1','1008');
insert into vuelo values('1014','Praga','Folgoso do Courel','2020-06-24 07:55','2020-06-24 07:55','2020-06-24 11:01','2020-06-24 11:01','429.8582',null,'false','3','1005');
insert into vuelo values('1015','Ámsterdam','Folgoso do Courel','2020-09-25 15:34','2020-09-25 15:34','2020-09-25 19:35','2020-09-25 19:35','489.573',null,'false','1','1003');
insert into vuelo values('1016','Lisboa','Folgoso do Courel','2020-09-26 22:36','2020-09-26 22:36','2020-09-27 02:53','2020-09-27 02:53','450.3955',null,'false','1','1006');
insert into vuelo values('1017','Berlín','Folgoso do Courel','2020-08-27 09:31','2020-08-27 09:31','2020-08-27 11:37','2020-08-27 11:37','364.21545',null,'false','2','1009');
insert into vuelo values('1018','Folgoso do Courel','Maldivas','2020-08-04 08:00', '2020-08-04 08:00','2020-08-04 18:00','2020-08-04
    18:00', '2500.0',null,'false','1','900');
insert into vuelo values('1019','Berlín','Folgoso do Courel','2020-05-06 23:44:31','2020-05-06 23:57:31','2020-05-07 08:55:30','2020-05-07 08:59:30','334.78632',null,'false','2','1009');
insert into vuelo values('1020','Vigo','Folgoso do Courel','2020-05-05 23:53:31','2020-05-05 23:58:31','2020-05-06 05:55:30','2020-05-06 05:59:30','334.78632',null,'false','1','1006');

insert into comprarbillete values ('59518667A',964,'2020-01-15',1,'normal',0,false,true,90);
insert into comprarbillete values ('65892114N',964,'2020-01-18',2,'normal',1,false,true,90);
insert into comprarbillete values ('09308274J',965,'2020-01-16',1,'normal',1,false,true,60);
insert into comprarbillete values ('60749238M',965,'2020-01-16',2,'normal',1,false,true,60);
insert into comprarbillete values ('48116361Q',964,'2020-01-15',3,'normal',0,false,true,90);
insert into comprarbillete values ('48116361Q',965,'2020-01-15',3,'normal',0,false,true,60);
insert into comprarbillete values ('49204425L',985,'2020-05-15 20:01:00',1,'normal',0,false,false,60);
insert into comprarbillete values ('49204425L',987,'2020-03-30 22:12:05',2,'normal',0,false,false,80);
insert into comprarbillete values ('49204425L',1017,'2020-01-30 19:56:43',4,'normal',0,false,false,90);
insert into comprarbillete values ('49204425L',1019,'2020-02-28 19:56:43',7,'normal',0,false,false,90);
insert into comprarbillete values ('49204425L',1020,'2020-03-30 19:56:43',9,'normal',0,false,true,90);


insert into facturarmaleta (usuario,vuelo,peso) values ('65892114N', 964, 10);
insert into facturarmaleta (usuario,vuelo,peso) values ('09308274J', 965, 11);
insert into facturarmaleta (usuario,vuelo,peso) values('60749238M', 965, 12);
