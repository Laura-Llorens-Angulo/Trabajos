SELECT   c.codcli, c.nombre
FROM     clientes AS c 
JOIN     facturas AS f USING( codcli )
WHERE    EXTRACT( year from f.fecha ) = EXTRACT( year from CURRENT_DATE ) - 1
GROUP BY c.codcli, c.nombre
HAVING   MAX( f.fecha ) - MIN( f.fecha ) > 60     
ORDER BY c.nombre; 