SELECT   INITCAP( nombre ), COUNT( codcli )
FROM     ( SELECT pr.nombre, c.codcli
           FROM lineas_fac AS lf
           JOIN facturas AS f USING( codfac )
           JOIN clientes AS c USING( codcli )
           JOIN pueblos AS pu USING( codpue )
           JOIN provincias AS pr USING( codpro )
           GROUP BY pr.nombre, c.codcli
           HAVING COUNT( DISTINCT lf.codart ) > 5 ) AS t
GROUP BY nombre;