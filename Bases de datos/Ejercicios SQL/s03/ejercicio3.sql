SELECT COALESCE ( stock , stock_min , -1 ) FROM articulos ;

--Muestra el primer valor no null de stock y stock_min y si ambos son null muestra -1
