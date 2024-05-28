SELECT DISTINCT p.codpue,p.nombre
FROM clientes as c JOIN pueblos as p USING(codpue)
			JOIN provincias as pro USING(codpro)
WHERE UPPER(pro.nombre) = 'CASTELLON'
ORDER BY p.nombre;
