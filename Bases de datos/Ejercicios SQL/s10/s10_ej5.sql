SELECT c1.codcli, c1.nombre
FROM   clientes AS c1
JOIN   facturas AS f1 USING( codcli )
JOIN   lineas_fac AS lf1 USING( codfac )
WHERE  lf1.cant % 2 = 0
EXCEPT
SELECT c2.codcli, c2.nombre
FROM   clientes AS c2
JOIN   facturas AS f2 USING( codcli )
JOIN   lineas_fac AS lf2 USING( codfac )
WHERE  lf2.cant % 2 != 0;