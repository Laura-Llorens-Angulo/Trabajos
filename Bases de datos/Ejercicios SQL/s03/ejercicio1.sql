SELECT codfac , fecha , codcli ,
 COALESCE ( iva , 0 ) AS iva , iva AS iva_null ,
 COALESCE ( dto , 0 ) AS dto
 FROM facturas
 WHERE codcli < 50
 AND ( iva = 0 OR iva IS NULL ) ;

--En iva las filas con null apareceran como 0 y en iva_null no aparecerá ningún valor.

