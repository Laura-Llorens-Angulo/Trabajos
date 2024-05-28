CREATE TABLE clientes (
 codcli numeric (5 ,0) ,
 nombre varchar (50) NOT NULL ,
 telefono varchar (12) NOT NULL ,
 direccion varchar (50) NOT NULL ,
 codpostal varchar (5) ,
 pueblo varchar (50) NOT NULL ,
 CONSTRAINT cp_clientes PRIMARY KEY ( codcli ) , -- Primary key
 CONSTRAINT calt_clientes UNIQUE ( telefono ) , -- Alternative key
 CONSTRAINT ri_clientes_codcli CHECK ( codcli > 0 ) -- Integrity rule
 ) ;
