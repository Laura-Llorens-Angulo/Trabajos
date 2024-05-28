--SELECT [ DISTINCT ] { * | column [ , column ]}
--FROM table 
--WHERE search_condition
--ORDER BY column [ASC | DESC ] [ , column [ASC| DESC ] ]
--FETCH FIRST n ROWS ONLY;

--Show a list of the invoice code, date and customer code of the invoices done in the last quarter of the past year and belonging to customers whose codes are between 50 and 100. The list must be ordered by the date of invoice.

SELECT codfac, fecha, codcli
FROM facturas
WHERE codcli BETWEEN 50 AND 100
	AND EXTRACT (quarter FROM fecha) = 4
	AND EXTRACT ( year FROM CURRENT_DATE )-EXTRACT ( year FROM fecha ) = 1
ORDER BY fecha;
