SELECT COUNT (*) AS cant_provincias
FROM   ( SELECT   DISTINCT pu.codpro
         FROM     pueblos AS pu 
         JOIN     clientes AS c USING( codpue )
         JOIN     facturas AS f USING ( codcli )
         GROUP BY pu.codpro, c.codcli
         HAVING COUNT( DISTINCT f.codfac ) > 10) AS t;