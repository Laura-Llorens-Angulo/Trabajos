SELECT   *
FROM     vendedores
WHERE    codven NOT IN ( SELECT codven
                         FROM facturas
                         WHERE EXTRACT( year FROM fecha ) = EXTRACT( year FROM CURRENT_DATE ) - 1 
                         AND EXTRACT( month FROM fecha ) = 12 
                         AND codven IS NOT NULL )
ORDER BY codven;
