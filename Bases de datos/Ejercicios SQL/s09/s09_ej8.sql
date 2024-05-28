SELECT codart, descrip
FROM   articulos AS a LEFT OUTER JOIN lineas_fac USING( codart )
LEFT OUTER JOIN facturas AS f USING(codfac)
WHERE  a.precio > 90
EXCEPT
SELECT codart, descrip
FROM   articulos AS a
WHERE  ( SELECT SUM(cant)
         FROM   lineas_fac AS l LEFT OUTER JOIN facturas AS f USING(codfac)
         WHERE  EXTRACT(year FROM CURRENT_DATE) - EXTRACT(year FROM fecha) = 1 
         AND    a.codart = l.codart
         GROUP BY codart ) >= 10
ORDER BY codart;
