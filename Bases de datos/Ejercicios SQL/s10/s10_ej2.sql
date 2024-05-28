SELECT   c.codcli, c.nombre,  EXTRACT( year from f.fecha ) AS fecha
FROM     facturas AS f 
JOIN     lineas_fac AS lf USING( codfac )
JOIN     clientes AS c USING( codcli )
GROUP BY c.codcli, c.nombre, EXTRACT( year from f.fecha )
HAVING   SUM( lf.cant * lf.precio ) >= ALL( SELECT   SUM( lf.cant * lf.precio )
                                            FROM     lineas_fac AS lf
                                            JOIN     facturas AS f USING( codfac )
                                            GROUP BY f.codcli, EXTRACT( year from f.fecha )
                                            HAVING   c.codcli = f.codcli )
ORDER BY c.codcli;