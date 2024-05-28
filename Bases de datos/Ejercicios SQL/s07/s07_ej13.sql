SELECT   codart, MIN( precio ) AS minimo, MAX( precio ) AS maximo, ROUND( AVG( dto ), 2 ) AS dto_medio
FROM     lineas_fac
WHERE    UPPER( codart ) LIKE 'R%'
GROUP BY codart
ORDER BY MAX( precio ) - MIN( precio ) ASC;
