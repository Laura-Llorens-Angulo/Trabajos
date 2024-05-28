SELECT t AS n_lineas
FROM   ( SELECT   COUNT(*) AS t 
         FROM     lineas_fac 
         GROUP BY codfac 
         ORDER BY COUNT(*) DESC 
         FETCH FIRST 1 ROWS ONLY ) AS f
;
