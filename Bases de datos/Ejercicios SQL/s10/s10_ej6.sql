SELECT c.codcli, c.nombre, SUM( lf.cant * lf.precio ), SUM( lf.precio ), 
       SUM(lf.cant * lf.precio) / SUM( lf.cant ) AS ratio
FROM   clientes AS c 
JOIN   pueblos AS pu USING( codpue )
JOIN   provincias AS pr USING( codpro )
JOIN   facturas AS f USING( codcli )
JOIN   lineas_fac AS lf USING( codfac )
WHERE  UPPER( pr.nombre ) = 'CASTELLON'
AND    EXTRACT( year from f.fecha ) = EXTRACT( year from CURRENT_DATE ) - 1
AND    codcli IN ( SELECT   codcli
                   FROM     facturas
                   GROUP BY codcli, codven
                   HAVING   COUNT(*) = 1 )
GROUP BY c.codcli, c.nombre;