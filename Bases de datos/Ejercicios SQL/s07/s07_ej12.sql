SELECT   DISTINCT v.codven, v.nombre
FROM     facturas AS f 
JOIN     vendedores AS v USING( codven )
GROUP BY v.codven, v.nombre, EXTRACT( month FROM f.fecha ), EXTRACT( year FROM f.fecha )
HAVING   COUNT( f.* ) > 4;

