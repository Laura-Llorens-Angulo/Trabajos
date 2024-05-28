SELECT DISTINCT codart
FROM lineas_fac
WHERE codfac BETWEEN 5776 AND 5781;

--(a) What table must the sentence work on?	lineas_fac
--(b) The working table has one row for each	codigo de articulo distinto
--(c) In the result each row represents a 	un codigo de articulo de las factura
--(d) If the rows in (b) do not represent the same as the rows in (c), we have to use DISTINCT clause. Which is the case for this exercise?	Hay que usarlo
--(e) Execute the query now and check your answer.
--(f) Remove the DISTINCT clause and execute again the query. Is the number of rows different? Si, con distinct son 17 y sin distinct son 18
