SELECT v.codven, f.fecha
FROM vendedores as v JOIN facturas as f using(codven)
WHERE f.dto=20 --AND año pasado
ORDER BY v.codven, f.fecha;
