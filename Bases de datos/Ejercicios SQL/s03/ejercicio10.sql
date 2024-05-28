SELECT codart,descrip, (precio*2) AS precio,stock,stock_min,dto--,codart_equiv,precio
FROM articulos
WHERE precio<0.05
ORDER BY codart;
