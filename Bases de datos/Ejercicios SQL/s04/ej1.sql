--SELECT [ DISTINCT ] { * | column [ , column ]}
--FROM table 
--WHERE search_condition
--ORDER BY column [ASC | DESC ] [ , column [ASC| DESC ] ]
--FETCH FIRST n ROWS ONLY;

--Show a list of the codes and dates of the invoices done during the month of March of any of last three years (the past year and the previous two years). Order the results by descending date.

SELECT codfac, fecha
FROM facturas
WHERE EXTRACT(month FROM fecha) = 3
	AND EXTRACT ( year FROM CURRENT_DATE )-EXTRACT ( year FROM fecha ) <= 3
ORDER BY fecha DESC;

