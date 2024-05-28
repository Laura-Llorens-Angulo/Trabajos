SELECT nombre 
FROM provincias 
WHERE LTRIM(codpro,1,2) IN ('03','12','46')
ORDER BY nombre;
