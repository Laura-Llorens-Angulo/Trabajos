SELECT codcli, nombre
FROM   ( SELECT   c.codcli, c.nombre
         FROM     clientes AS c 
         JOIN     facturas AS f USING( codcli )
	 JOIN     lineas_fac AS lf USING( codfac )
         GROUP BY c.codcli
         ORDER BY SUM( lf.cant ) DESC 
         FETCH FIRST 1 ROWS ONLY ) AS t;
