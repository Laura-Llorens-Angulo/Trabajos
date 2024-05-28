--SELECT [ DISTINCT ] { * | column [ , column ]}
--FROM table 
--WHERE search_condition
--ORDER BY column [ASC | DESC ] [ , column [ASC| DESC ] ]
--FETCH FIRST n ROWS ONLY;

--Show a list of codes and dates of the invoices that belong to customers with codes 90 or 99, in which no discount has been applied or which do not have VAT. Show also the discount and the VAT. Keep in mind that null in the discount and VAT columns can also be interpreted as zero. The list should be ordered by the date of invoice.

SELECT codfac, fecha, COALESCE(iva,0) AS iva, COALESCE(dto,0) AS dto
FROM facturas
WHERE codcli IN (90, 99) 
	AND (COALESCE(dto,0) = 0 OR COALESCE(iva,0)=0)
ORDER BY fecha;

