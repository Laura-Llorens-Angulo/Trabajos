-- Database generated with pgModeler (PostgreSQL Database Modeler).
-- pgModeler  version: 0.9.2
-- PostgreSQL version: 12.0
-- Project Site: pgmodeler.io
-- Model Author: ---


-- Database creation must be done outside a multicommand file.
-- These commands were put in this file only as a convenience.
-- -- object: new_database | type: DATABASE --
-- -- DROP DATABASE IF EXISTS new_database;
-- CREATE DATABASE new_database;
-- -- ddl-end --
-- 

-- object: public.clientes | type: TABLE --
-- DROP TABLE IF EXISTS public.clientes CASCADE;
CREATE TABLE public.clientes (
	codcli numeric(5,0) NOT NULL,
	nombre varchar(50) NOT NULL,
	telefono varchar(12) NOT NULL,
	direccion varchar(50) NOT NULL,
	codpostal varchar(5),
	pueblo varchar(50) NOT NULL,
	CONSTRAINT cp_codcli PRIMARY KEY (codcli)

);
-- ddl-end --

-- object: public.facturas | type: TABLE --
-- DROP TABLE IF EXISTS public.facturas CASCADE;
CREATE TABLE public.facturas (
	codfac numeric(6,0) NOT NULL,
	fecha date NOT NULL,
	iva numeric(2,0),
	dto numeric(4,2),
	codcli numeric(5,0),
	CONSTRAINT facturas_pk PRIMARY KEY (codfac)

);
-- ddl-end --

-- object: public.lineas_fac | type: TABLE --
-- DROP TABLE IF EXISTS public.lineas_fac CASCADE;
CREATE TABLE public.lineas_fac (
	linea numeric(2,0) NOT NULL,
	cant numeric(5,0) NOT NULL,
	precio numeric(6,2) NOT NULL,
	dto numeric(4,2),
	codfac numeric(6,0) NOT NULL,
	codart varchar(8),
	CONSTRAINT lineas_fac_pk PRIMARY KEY (linea,codfac)

);
-- ddl-end --

-- object: clientes_fk | type: CONSTRAINT --
-- ALTER TABLE public.facturas DROP CONSTRAINT IF EXISTS clientes_fk CASCADE;
ALTER TABLE public.facturas ADD CONSTRAINT clientes_fk FOREIGN KEY (codcli)
REFERENCES public.clientes (codcli) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: facturas_fk | type: CONSTRAINT --
-- ALTER TABLE public.lineas_fac DROP CONSTRAINT IF EXISTS facturas_fk CASCADE;
ALTER TABLE public.lineas_fac ADD CONSTRAINT facturas_fk FOREIGN KEY (codfac)
REFERENCES public.facturas (codfac) MATCH FULL
ON DELETE CASCADE ON UPDATE CASCADE;
-- ddl-end --

-- object: public.articulos | type: TABLE --
-- DROP TABLE IF EXISTS public.articulos CASCADE;
CREATE TABLE public.articulos (
	codart varchar(8) NOT NULL,
	descrip varchar(50) NOT NULL,
	precio numeric(6,2) NOT NULL,
	stock numeric(6,0) NOT NULL,
	stock_min numeric(6,0) NOT NULL,
	dto numeric(4,2),
	codart_equiv varchar(8),
	CONSTRAINT cp_codart PRIMARY KEY (codart)

);
-- ddl-end --

-- object: articulos_fk | type: CONSTRAINT --
-- ALTER TABLE public.lineas_fac DROP CONSTRAINT IF EXISTS articulos_fk CASCADE;
ALTER TABLE public.lineas_fac ADD CONSTRAINT articulos_fk FOREIGN KEY (codart)
REFERENCES public.articulos (codart) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --

-- object: articulos_fk | type: CONSTRAINT --
-- ALTER TABLE public.articulos DROP CONSTRAINT IF EXISTS articulos_fk CASCADE;
ALTER TABLE public.articulos ADD CONSTRAINT articulos_fk FOREIGN KEY (codart_equiv)
REFERENCES public.articulos (codart) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: articulos_uq | type: CONSTRAINT --
-- ALTER TABLE public.articulos DROP CONSTRAINT IF EXISTS articulos_uq CASCADE;
ALTER TABLE public.articulos ADD CONSTRAINT articulos_uq UNIQUE (codart_equiv);
-- ddl-end --

-- object: public.vips | type: TABLE --
-- DROP TABLE IF EXISTS public.vips CASCADE;
CREATE TABLE public.vips (
	dto numeric(4,2) NOT NULL,
	factumbral numeric(5,2) NOT NULL,
	codcli numeric(5,0) NOT NULL
);
-- ddl-end --

-- object: public.jovenes | type: TABLE --
-- DROP TABLE IF EXISTS public.jovenes CASCADE;
CREATE TABLE public.jovenes (
	dto numeric(5,2) NOT NULL,
	codcli numeric(5,0) NOT NULL
);
-- ddl-end --

-- object: clientes_fk | type: CONSTRAINT --
-- ALTER TABLE public.vips DROP CONSTRAINT IF EXISTS clientes_fk CASCADE;
ALTER TABLE public.vips ADD CONSTRAINT clientes_fk FOREIGN KEY (codcli)
REFERENCES public.clientes (codcli) MATCH FULL
ON DELETE CASCADE ON UPDATE CASCADE;
-- ddl-end --

-- object: vips_uq | type: CONSTRAINT --
-- ALTER TABLE public.vips DROP CONSTRAINT IF EXISTS vips_uq CASCADE;
ALTER TABLE public.vips ADD CONSTRAINT vips_uq UNIQUE (codcli);
-- ddl-end --

-- object: clientes_fk | type: CONSTRAINT --
-- ALTER TABLE public.jovenes DROP CONSTRAINT IF EXISTS clientes_fk CASCADE;
ALTER TABLE public.jovenes ADD CONSTRAINT clientes_fk FOREIGN KEY (codcli)
REFERENCES public.clientes (codcli) MATCH FULL
ON DELETE CASCADE ON UPDATE CASCADE;
-- ddl-end --

-- object: jovenes_uq | type: CONSTRAINT --
-- ALTER TABLE public.jovenes DROP CONSTRAINT IF EXISTS jovenes_uq CASCADE;
ALTER TABLE public.jovenes ADD CONSTRAINT jovenes_uq UNIQUE (codcli);
-- ddl-end --


