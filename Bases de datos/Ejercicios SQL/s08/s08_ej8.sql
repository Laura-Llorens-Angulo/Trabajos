SELECT   DISTINCT pu.codpue, pu.nombre
FROM     pueblos AS pu
JOIN     clientes AS c USING( codpue )
JOIN     facturas AS f USING( codcli )
WHERE    f.iva > ( SELECT AVG( iva )
                   FROM facturas )
AND      f.dto > ( SELECT AVG( dto )
                   FROM facturas )
ORDER BY pu.codpue;