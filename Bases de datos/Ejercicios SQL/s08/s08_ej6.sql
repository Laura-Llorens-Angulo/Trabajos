SELECT   DISTINCT c.* 
FROM     clientes as c 
JOIN     facturas AS f USING( codcli )
WHERE    c.codcli NOT IN ( SELECT DISTINCT codcli
	   	           FROM   facturas
			   WHERE  codcli IS NOT NULL
		       	   AND    EXTRACT( month from fecha) BETWEEN 1 AND 6 )
ORDER BY codcli;
