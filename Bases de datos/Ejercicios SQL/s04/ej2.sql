--SELECT [ DISTINCT ] { * | column [ , column ]}
--FROM table 
--WHERE search_condition
--ORDER BY column [ASC | DESC ] [ , column [ASC| DESC ] ]
--FETCH FIRST n ROWS ONLY;

--Show a list of the codes and dates of the invoices done in the first 50 days of the past year and belonging to customers whose codes are between 100 and 250. The list should be ordered by the date of invoice.

SELECT f.codfac, f.fecha
FROM facturas AS f
WHERE f.codcli BETWEEN 100 AND 250
	AND EXTRACT (doy FROM f.fecha) <= 50
	AND EXTRACT (year FROM CURRENT_DATE)-1 = EXTRACT (year FROM f.fecha)
ORDER BY f.fecha;
