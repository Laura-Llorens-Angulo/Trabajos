SELECT a.*
FROM   ( SELECT codart, SUM( cant ) as cant
         FROM lineas_fac as lf
         GROUP BY codart ) as t 
JOIN   articulos as a USING( codart )
WHERE  t.cant >= ALL ( SELECT SUM( cant ) 
                       FROM lineas_fac as lf
                       GROUP BY codart );