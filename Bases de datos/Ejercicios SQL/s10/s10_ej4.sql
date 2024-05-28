SELECT   c1.codcli, c1.nombre
FROM     clientes AS c1
JOIN     facturas AS f1 USING( codcli )
JOIN     lineas_fac AS lf1 USING( codfac )
WHERE    EXTRACT( year from f1.fecha ) = EXTRACT( year from CURRENT_DATE ) - 1
GROUP BY c1.codcli, c1.nombre
HAVING   SUM( lf1.cant * lf1.precio ) <= ALL ( SELECT SUM( lf2.cant * lf2.precio )
                                               FROM facturas AS f2
                                               JOIN lineas_fac AS lf2 USING( codfac )
                                               WHERE EXTRACT( year from f2.fecha ) = EXTRACT( year from CURRENT_DATE ) - 1
                                               GROUP BY f2.codcli )
;