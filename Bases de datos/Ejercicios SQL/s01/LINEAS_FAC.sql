CREATE TABLE lineas_fac (
 codfac numeric(5, 0) ,
 linea varchar(1000) ,
 cant numeric(1000,0) NOT NULL,
 descrip_art  varchar(500) NOT NULL,
 precio numeric(100, 0) NOT NULL,
 dto numeric(2, 0) ,
 CONSTRAINT cp_lineas_fac PRIMARY KEY ( codfac, linea ),
 CONSTRAINT ca_lin_fac FOREIGN KEY ( codfac ) REFERENCES facturas ON DELETE CASCADE ON UPDATE CASCADE , -- Discount in a line must be between 0 and 50 percent
 CONSTRAINT ri_dto_lin CHECK (dto between 0 and 50 ) , -- cant has to be greater than 0
 CONSTRAINT ri_cant_lin CHECK ( cant>0 )
 ) ;
