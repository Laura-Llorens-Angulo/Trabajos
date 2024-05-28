SELECT *
 FROM articulos
 WHERE stock > stock_min * 3
 AND precio > 6
 ORDER BY codart ;
 
 --Muestra todos los datos de los articulos en los que el stock es 3 veces mayor al minimo y el precio es mayor de 6, ademas ordena por codart(esto esta mal porq en stock y stock_min hay que tener en cuenta que puede ser null)
