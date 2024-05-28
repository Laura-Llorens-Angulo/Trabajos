SELECT   DISTINCT p.*, pr.nombre 
FROM     pueblos AS p 
JOIN     provincias AS pr USING( codpro)
JOIN     clientes AS c USING( codpue )
WHERE    codpue IN ( SELECT   codpue
	             FROM     clientes AS c
	             WHERE    codpue IS NOT NULL	
		     GROUP BY codpue
		     HAVING   COUNT(*) > 2 )
ORDER BY p.codpue;