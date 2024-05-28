SELECT          pu.codpue, pu.codpro
FROM            pueblos AS pu 
LEFT OUTER JOIN clientes AS c USING( codpue )
JOIN            provincias AS pr USING( codpro )
WHERE           LOWER( pr.nombre ) = 'castellon'
GROUP BY        pu.codpue
HAVING          COUNT(*) < 2
ORDER BY        pu.codpue;
