SELECT   DISTINCT v.codven, v.nombre
FROM     facturas AS f 
JOIN     vendedores AS v USING( codven )
JOIN     clientes AS c USING( codcli )
GROUP BY v.codven, c.codcli
HAVING   COUNT( DISTINCT f.codfac ) >= 5;