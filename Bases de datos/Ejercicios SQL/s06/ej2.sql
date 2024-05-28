SELECT l.*, v.codven, v.nombre
FROM lineas_fac as l JOIN facturas as f USING(codfac)
		JOIN vendedores as v USING(codven)
		JOIN pueblos as p USING(codpue)
WHERE UPPER(p.nombre) = 'VIVER'
ORDER BY l.codfac,l.linea;


