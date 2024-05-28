SELECT   codart, SUM(cant) AS cantidad
FROM     lineas_fac
GROUP BY codart
HAVING   COUNT( DISTINCT codfac ) > 12
ORDER BY codart;