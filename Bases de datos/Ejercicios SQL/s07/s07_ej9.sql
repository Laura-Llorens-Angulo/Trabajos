SELECT   codart, SUM( cant ) AS cantidad
FROM     lineas_fac
GROUP BY codart
ORDER BY codart;
