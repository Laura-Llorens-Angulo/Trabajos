SELECT codfac,linea,codart,cant,precio,(precio*cant) AS precio_total,dto,TRUNC((precio*cant)*(1-dto/100),2) AS precio_final
FROM lineas_fac
WHERE NULLIF(dto, 0) IS NOT NULL
ORDER BY codfac DESC , linea;
