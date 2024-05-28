SELECT v.codven, v.nombre, v.codpostal
FROM   vendedores AS v
EXCEPT
SELECT v.codven, v.nombre, v.codpostal
FROM   vendedores AS v 
JOIN   facturas AS f USING( codven ) 
JOIN   clientes AS c USING( codcli )
WHERE  SUBSTRING( v.codpostal, 1, 2 ) = SUBSTRING( c.codpostal, 1, 2 )
ORDER BY nombre;