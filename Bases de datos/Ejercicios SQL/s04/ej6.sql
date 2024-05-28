--SELECT [ DISTINCT ] { * | column [ , column ]}
--FROM table 
--WHERE search_condition
--ORDER BY column [ASC | DESC ] [ , column [ASC| DESC ] ]
--FETCH FIRST n ROWS ONLY;

SELECT codart,SUBSTR(LTRIM(valoracion), 1, 30) AS valoracion
FROM valoraciones
WHERE ABS(COALESCE(likes,0) - COALESCE(dislikes, 0)) > 20
ORDER BY codart;

--Falta q sea primera reseña y no segunda
