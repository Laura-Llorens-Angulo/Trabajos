Select sum(cant) as suma 
from lineas_fac 
where codart in (
select codart from lineas_fac
Except
select l.codart from lineas_fac as l join facturas as f using(codfac) where extract(year from f.fecha)!=2021)
 