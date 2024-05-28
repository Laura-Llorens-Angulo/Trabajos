SELECT codart, descrip, COALESCE(stock_min,0)-COALESCE(stock,0)
FROM articulos
WHERE COALESCE(stock,0)<COALESCE(stock_min,0)
ORDER BY descrip;
