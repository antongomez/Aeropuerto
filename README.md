# Aeropuerto, Maio de 2020

Esta aplicación foi desenvolvida con JavaFx 17 e JDK 16 con motivo académico para a materia de Bases de Datos II no grao de Enxeñería Informática.

Usamos PostgrSQL como xestor de bases de datos. Para replicar a base de datos basta con crear unha base de datos chamada "Aeropuerto" e executar os scripts para crear as táboas da base de datos e os scripts para introducir os datos.
A conexión coa base de datos faise a través dun arquivo "baseDatos.properties" similar ao seguinte:

	gestor=postgresql
	servidor=localhost
	puerto=5432
	baseDatos=Aeropuerto
	usuario=username
	clave=password

"usuario" e "clave" deben ser as credenciais do usuario de PostgreSQL.
