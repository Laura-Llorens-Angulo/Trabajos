SELECT UPPER(SUBSTR(nombre, POSITION(',' IN nombre)+2) || ' ' || SUBSTR(nombre, 1, POSITION(',' IN nombre)-1)) AS nombre
FROM clientes
WHERE codcli < 50
ORDER BY nombre
FETCH FIRST 10 ROWS ONLY;
