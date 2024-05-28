SELECT   *
FROM     articulos
WHERE    codart NOT IN( SELECT codart
		        FROM lineas_fac )
ORDER BY codart;
