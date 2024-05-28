SELECT   codcli
FROM     ( SELECT codcli
           FROM   facturas
           WHERE  EXTRACT( quarter from fecha ) = 1
           EXCEPT
           SELECT codcli
           FROM   facturas
           WHERE  EXTRACT( quarter from fecha ) != 1 ) AS t
ORDER BY codcli;
