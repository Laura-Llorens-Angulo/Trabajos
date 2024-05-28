WITH ventas_cs AS ( SELECT   a.codart, a.descrip, SUM(lf.cant) AS sum_cant
                    FROM     articulos AS a
                    JOIN     lineas_fac AS lf USING( codart )
                    JOIN     facturas AS f USING( codfac )
                    JOIN     clientes AS c USING( codcli )
                    JOIN     pueblos AS pu USING( codpue )
                    WHERE    pu.codpro = '12'
                    AND      LOWER( a.descrip ) LIKE 'base%'
                    GROUP BY a.codart ),
     ventas_vl AS ( SELECT   a.codart, a.descrip, SUM( lf.cant ) as sum_cant
                    FROM     articulos AS a
                    JOIN     lineas_fac AS lf USING( codart )
                    JOIN     facturas AS f USING( codfac )
                    JOIN     clientes AS c USING( codcli )
                    JOIN     pueblos AS pu USING( codpue )
                    WHERE    pu.codpro = '46'
                    AND      LOWER( a.descrip ) LIKE 'base%'
                    GROUP BY a.codart ),
          aux1 AS ( SELECT     a.codart, a.descrip, cs.sum_cant AS ventas_cs, vl.sum_cant AS ventas_vl
                    FROM       articulos AS a
                    RIGHT JOIN ventas_cs AS cs USING(codart)
                    LEFT JOIN  ventas_vl AS vl USING(codart)
                    ORDER BY   a.descrip, a.codart ),
          aux2 AS ( SELECT     a.codart, a.descrip, cs.sum_cant AS ventas_cs, vl.sum_cant AS ventas_vl
                    FROM       articulos AS a
                    LEFT JOIN  ventas_cs AS cs USING(codart)
                    RIGHT JOIN ventas_vl AS vl USING(codart)
                    ORDER BY   a.descrip, a.codart )
SELECT   *
FROM     aux1
UNION
SELECT   *
FROM     aux2
ORDER BY descrip, codart
;