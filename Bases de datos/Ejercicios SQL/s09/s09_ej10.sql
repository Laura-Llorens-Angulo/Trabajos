SELECT c.*
FROM   ( SELECT codart
	 FROM   articulos
	 WHERE  precio > 2 ) AS t
JOIN   lineas_fac AS lf USING( codart )
JOIN   facturas AS f USING( codfac )
JOIN   clientes AS c USING( codcli )
EXCEPT
SELECT c.*
FROM   ( SELECT codart
	 FROM   articulos
	 WHERE  precio <= 2 ) AS t
JOIN   lineas_fac AS lf USING( codart )
JOIN   facturas AS f USING( codfac )
JOIN   clientes AS c USING( codcli );