Select codcli from facturas where codcli IS NOT NULL and extract(year from fecha) +1 = extract(year from current_date)
except
select codcli from facturas where COALESCE(iva,0)=0 and extract(year from fecha) +1 = extract(year from current_date) and codcli is not null
order by codcli asc