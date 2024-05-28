SELECT MAX( stock ) AS max_stock, MIN( stock ) AS min_stock, ROUND( ( MAX(stock) + MIN(stock) ) / 2, 2 ) AS media
FROM   articulos
WHERE  precio BETWEEN 1 AND 50;
