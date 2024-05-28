SELECT codven, nombre 
FROM   vendedores
EXCEPT
SELECT   v.codven, v.nombre
FROM     vendedores AS v 
JOIN     facturas AS f USING(codven)
JOIN     lineas_fac AS lf USING(codfac)
JOIN     articulos AS a USING(codart)
WHERE    LOWER(a.descrip) LIKE 'al%'
ORDER BY nombre; 
