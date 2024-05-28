SELECT codcli, codart,stars,likes,dislikes
FROM valoraciones
WHERE COALESCE(stars, 0)>3 AND COALESCE(likes, 0)>25 AND COALESCE(dislikes, 0)<10
ORDER BY CASE  WHEN codcli BETWEEN 250 AND 300 THEN 0
		ELSE 1
		END ASC,codcli, codart;
