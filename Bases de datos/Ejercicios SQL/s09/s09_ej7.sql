SELECT codpro, SUM( codcli )
FROM ( SELECT codpro, 0 AS codcli
       FROM pueblos
       UNION
       SELECT codpro, COUNT(codcli)
       FROM ( SELECT DISTINCT codpro, codcli
              FROM clientes 
              JOIN facturas USING( codcli )
              JOIN pueblos USING( codpue )
              WHERE EXTRACT( year from CURRENT_DATE ) - EXTRACT(year from fecha) = 1
              EXCEPT
              SELECT codpro, codcli
              FROM clientes 
              JOIN facturas USING(codcli) 
              JOIN pueblos USING(codpue)
              WHERE COALESCE( iva, 0 ) = 0
              AND EXTRACT( year from CURRENT_DATE ) - EXTRACT( year from fecha ) = 1
              ORDER BY codcli ) AS tab
        GROUP BY codpro
        ORDER BY codpro ) AS tabla
GROUP BY codpro;