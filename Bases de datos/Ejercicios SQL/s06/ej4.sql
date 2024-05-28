SELECT DISTINCT f.*
FROM articulos as a JOIN lineas_fac as l USING(codart) 
			JOIN facturas as f USING(codfac)
WHERE LOWER(a.descrip) LIKE '%interruptor%'
ORDER BY f.codfac;
