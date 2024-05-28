SELECT *
FROM   facturas
WHERE  fecha = ( SELECT MAX (fecha) 
	         FROM   facturas );
