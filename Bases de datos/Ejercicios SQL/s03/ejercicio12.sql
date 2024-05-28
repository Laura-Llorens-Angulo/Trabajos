SELECT codart, descrip, stock_min
FROM articulos
WHERE precio>1 AND (stock IS NULL OR stock_min IS NULL) 
ORDER BY descrip;
