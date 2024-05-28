--Does this foreign key accept nulls?
--Acepta nulos, de hecho la regla de borrado es set null

SELECT codfac
FROM facturas
WHERE codcli IS NULL;
