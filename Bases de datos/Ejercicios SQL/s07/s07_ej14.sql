SELECT   f.codcli, c.nombre, COUNT( DISTINCT f.* ) AS n_clientes, COUNT( DISTINCT f.codven ) AS n_vendedores 
FROM     facturas AS f 
JOIN     clientes AS c USING( codcli )
WHERE    f.iva = 18 
OR       COALESCE( f.dto, 0 ) = 0
GROUP BY f.codcli, c.nombre
HAVING   COUNT( DISTINCT f.codven ) > 5 
ORDER BY codcli;