SELECT f.fecha
FROM   ( SELECT   COUNT(*) AS cant, codfac  
         FROM     lineas_fac 
         GROUP BY codfac ) AS t 
JOIN   facturas AS f USING (codfac)
WHERE  t.cant >= ALL ( SELECT COUNT(*)
                       FROM lineas_fac
                       GROUP BY codfac )
ORDER BY f.fecha ASC;