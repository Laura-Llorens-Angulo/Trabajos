SELECT ROUND( AVG( precio ), 2 ) AS precio_medio
FROM   articulos
WHERE  stock > 10;
