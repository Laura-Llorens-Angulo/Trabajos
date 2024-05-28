SELECT DISTINCT COALESCE ( iva ,0) --as iva
FROM facturas
--ORDER BY iva
;

--Para que salga ordenado ascendente hay que hacer ORDER BY iva y añadir as IVA en COALESCE para ponerle una etiqueta
--Hay una linea por cada iva distinto
