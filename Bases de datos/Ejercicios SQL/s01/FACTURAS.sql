CREATE TABLE facturas (
 codfac numeric (6, 0) ,
 fecha date NOT NULL ,
 codcli numeric (5, 0) ,
 iva numeric (2, 0),
 dto numeric (2, 0) ,
 CONSTRAINT cf_factura PRIMARY KEY ( codfac ) , -- Primary key
 CONSTRAINT ca_fac_cli FOREIGN KEY ( codcli ) REFERENCES clientes ON DELETE SET NULL ON UPDATE CASCADE ,
 CONSTRAINT ri_facturas_codfac CHECK ( codfac > 0 ), -- Integrity rule
 CONSTRAINT ri_dto_fac CHECK ( dto >= 0 AND dto <= 50 ) -- Integrity rule
 ) ;
