SELECT f.codfac, f.fecha, c.codcli, c.nombre
FROM facturas as f right JOIN clientes as c USING(codcli)
WHERE UPPER(c.nombre) LIKE 'A%'
ORDER BY f.codfac;
