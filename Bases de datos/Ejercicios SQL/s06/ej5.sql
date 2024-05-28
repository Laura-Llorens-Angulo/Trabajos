SELECT c.codcli, c.nombre
FROM clientes as c JOIN pueblos AS p USING(codpue) JOIN provincias as pro USING(codpro)
WHERE UPPER(pro.nombre) = 'MADRID';
