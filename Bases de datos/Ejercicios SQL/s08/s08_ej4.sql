SELECT *
FROM   clientes
WHERE  codcli IN ( SELECT DISTINCT codcli
		   FROM   facturas
		   WHERE  fecha = ( SELECT MAX( fecha )
			            FROM   facturas
			            WHERE  EXTRACT( year FROM fecha ) = EXTRACT( year FROM CURRENT_DATE ) - 1 ) )
ORDER BY codcli;
