SELECT   t.codart
FROM     ( SELECT codart, SUM(cant) as cant
           FROM lineas_fac as lf
           GROUP BY codart ) as t
ORDER BY t.cant DESC
FETCH FIRST 1 ROWS ONLY;