SELECT   lf.codart, a.descrip, SUM( lf.cant ) AS cantidad
FROM     lineas_fac AS lf 
         JOIN articulos AS a USING( codart )
GROUP BY lf.codart, a.descrip
HAVING   COUNT( DISTINCT lf.codfac ) > 12
ORDER BY cantidad;
