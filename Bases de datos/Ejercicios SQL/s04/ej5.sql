--SELECT [ DISTINCT ] { * | column [ , column ]}
--FROM table 
--WHERE search_condition
--ORDER BY column [ASC | DESC ] [ , column [ASC| DESC ] ]
--FETCH FIRST n ROWS ONLY;

--What names of villages in the Valencian Community end with the syllable ’bo’? The codes of the provinces of the Valencian Community are ’03’ (Alicante), ’12’ (Castellón), and ’46’ (Valencia). The result should be ordered by the villages’s name and must also show the code of the province.

SELECT codpro,nombre
FROM provincias 
WHERE SUBSTR(codpro,1,2) IN ('03','12','46')
	AND nombre LIKE '%bo'
ORDER BY nombre;



