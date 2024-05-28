SELECT   EXTRACT( month from fecha ) AS mes, COUNT( codfac ) AS facturas
FROM     facturas
WHERE    EXTRACT( year from fecha ) = EXTRACT( year from CURRENT_DATE ) - 1
AND      codven IN( 255, 355, 455 )
GROUP BY EXTRACT( month from fecha )
ORDER BY EXTRACT( month from fecha );