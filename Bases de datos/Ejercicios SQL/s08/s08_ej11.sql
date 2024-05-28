SELECT v.codven, v.nombre
FROM   ( SELECT   v.codven, v.nombre
         FROM     vendedores as v 
         JOIN     facturas AS f USING(codven)
         JOIN     lineas_fac AS lf USING(codfac)
         WHERE    EXTRACT( year from f.fecha ) = EXTRACT( year from CURRENT_DATE ) - 1
         GROUP BY v.codven
         ORDER BY SUM( lf.cant * lf.precio ) ASC
         FETCH FIRST 1 ROWS ONLY ) AS t;
