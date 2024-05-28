SELECT DISTINCT EXTRACT(month FROM fecha) AS mes, TO_CHAR ( fecha , LOWER('MONTH')) AS mesLetras
FROM facturas
WHERE codcli IN (45,54,87,102)
	AND EXTRACT ( year FROM CURRENT_DATE )-EXTRACT ( year FROM fecha ) = 1;
