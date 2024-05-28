SELECT f.*, c.nombre
FROM facturas as f JOIN clientes as c USING(codcli) 
		   JOIN  pueblos as p USING(codpue)
WHERE p.codpro = '12'
ORDER BY c.codcli, f.fecha;
