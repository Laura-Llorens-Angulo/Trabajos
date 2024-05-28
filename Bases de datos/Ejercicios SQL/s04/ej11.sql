SELECT nombre, direccion, codpostal,
	CASE WHEN LTRIM(codpro,LENGTH(string)-1)= '9' THEN PREFERENTE
	     WHEN LTRIM(codpro,LENGTH(string)-1)= '6' THEN ODINARY
	     ELSE NULL
	
FROM clientes
WHERE LTRIM(codpro,1,2) = '12';
