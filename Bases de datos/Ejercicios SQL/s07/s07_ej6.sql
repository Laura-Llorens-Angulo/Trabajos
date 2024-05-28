SELECT ROUND( COUNT( c.codcli ) * 1.0 / COUNT( DISTINCT pu.codpue ), 2 ) as media
FROM   clientes AS c
       JOIN pueblos AS pu USING( codpue );
