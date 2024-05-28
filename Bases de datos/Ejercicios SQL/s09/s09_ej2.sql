SELECT   c.*
FROM     ( SELECT codcli
           FROM   facturas
           WHERE  EXTRACT( quarter from fecha ) = 1
           EXCEPT
           SELECT codcli
           FROM   facturas
           WHERE  EXTRACT( quarter from fecha ) != 1 ) AS t
JOIN     clientes AS c USING( codcli )
ORDER BY codcli;

