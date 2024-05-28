SELECT DISTINCT a.codart, a.descrip, f.codcli
FROM articulos as a left JOIN lineas_fac as l USING(codart)
				left JOIN facturas as f USING(codfac)
ORDER BY a.descrip DESC;
