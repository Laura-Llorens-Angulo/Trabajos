SELECT f.codfac, f.fecha, c.codcli, c.nombre
FROM facturas as f FULL OUTER JOIN clientes as c USING(codcli)
WHERE UPPER(c.nombre) LIKE 'A%' OR c.nombre IS NULL
ORDER BY f.codfac;
