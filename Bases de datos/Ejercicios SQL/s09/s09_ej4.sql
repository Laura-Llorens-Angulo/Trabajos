SELECT COUNT(codart) AS n_articulos
FROM   ( SELECT codart
         FROM   articulos
         WHERE  stock > 5 AND precio > 3
         EXCEPT   
         SELECT lf.codart
         FROM   lineas_fac AS lf 
         JOIN   facturas AS f USING( codfac )
         WHERE  EXTRACT( quarter FROM f.fecha ) = 4 
         AND    EXTRACT( year from f.fecha ) = EXTRACT( year from CURRENT_DATE ) - 1 ) AS t
;

