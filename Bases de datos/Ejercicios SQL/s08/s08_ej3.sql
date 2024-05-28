SELECT   f.*
FROM     facturas AS f 
JOIN     lineas_fac AS lf USING( codfac )
WHERE    lf.codart IN ( SELECT   codart
		        FROM     articulos
		        ORDER BY precio DESC
		        FETCH FIRST 1 ROWS ONLY )
	            
ORDER BY f.fecha;

