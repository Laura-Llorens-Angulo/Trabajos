SELECT   *
FROM     clientes
WHERE    codpue IN ( SELECT codpue 
                     FROM pueblos 
                     WHERE codpro IN( '03', '12', '46' ) )
ORDER BY codpue, nombre;

