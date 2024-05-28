SELECT COUNT( COALESCE( stock, 0 ) ) AS num_articulos_sin_stock
FROM   articulos
WHERE  COALESCE( stock, 0 ) = 0;
