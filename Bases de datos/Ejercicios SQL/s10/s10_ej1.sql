Select f.codcli, UPPER(c.nombre), Count(*) as nfact, p.codpro
from facturas as f join clientes as c using(codcli) join pueblos as p using(codpue)
where p.codpro IN('03', '12', '46') and 1000 < (Select sum (l.cant*l.precio) from lineas_fac as l join facturas as f2 using(codfac) where f.codcli=f2.codcli)
group by f.codcli, c.nombre, p.codpro
Having Count(*)>10
order by f.codcli