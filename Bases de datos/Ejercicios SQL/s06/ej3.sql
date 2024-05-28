SELECT c.codcli,c.nombre, 
	INITCAP(c.direccion)||' - '||INITCAP(p.nombre)||' ('||UPPER(pro.nombre)||')' as direccion
FROM clientes as c JOIN pueblos as p USING(codpue)
			JOIN provincias as pro USING(codpro)
WHERE UPPER(pro.nombre) IN ('VALENCIA', 'CASTELLON', 'ALICANTE')
ORDER BY c.codcli;
