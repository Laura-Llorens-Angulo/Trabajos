SELECT COUNT(*) AS maximo_n_facturas
FROM   facturas
WHERE  codcli = ( SELECT   codcli
		  FROM     facturas
		  WHERE    codcli IS NOT NULL
		  GROUP BY codcli
		  ORDER BY COUNT(*) DESC
		  FETCH FIRST 1 ROWS ONLY );
