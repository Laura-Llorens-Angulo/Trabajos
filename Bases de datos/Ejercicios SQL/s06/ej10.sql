SELECT p.codpue, p.nombre, c.codcli
FROM clientes as c right JOIN pueblos as p USING(codpue)
			right JOIN provincias as pro USING(codpro)
WHERE UPPER(pro.nombre) = 'CASTELLON'
ORDER BY p.nombre, c.codcli;
