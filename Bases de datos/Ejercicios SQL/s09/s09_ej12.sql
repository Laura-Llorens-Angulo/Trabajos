SELECT COUNT(*)
FROM ( SELECT p.codpue
       FROM   pueblos AS p 
       JOIN   provincias AS pr USING( codpro )
       WHERE  UPPER( pr.nombre ) = 'ALICANTE'
       EXCEPT
       SELECT   p.codpue
       FROM     clientes AS c 
       JOIN     pueblos AS p USING( codpue )
       JOIN     provincias AS pr USING( codpro )
       WHERE    UPPER( pr.nombre ) = 'ALICANTE'
       GROUP BY p.codpue ) AS t
;
