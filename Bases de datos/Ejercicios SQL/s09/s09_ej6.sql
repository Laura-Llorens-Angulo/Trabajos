SELECT  p.codpro, COUNT(*)
FROM    ( SELECT codcli 
          FROM   facturas
          WHERE  EXTRACT( year from fecha ) = EXTRACT( year from CURRENT_DATE ) - 1 
          AND    codcli IS NOT NULL
          EXCEPT
          SELECT codcli 
          FROM   facturas
          WHERE  COALESCE( iva, 0 ) = 0 
          AND    EXTRACT( year from fecha ) = EXTRACT( year from CURRENT_DATE ) - 1 
          AND    codcli IS NOT NULL ) AS t 
JOIN     clientes AS c USING( codcli ) 
JOIN     pueblos AS p USING( codpue )
GROUP BY p.codpro;
