SELECT COUNT(DISTINCT codpue) AS ciudades
FROM   clientes
WHERE  codpostal LIKE('12%');
