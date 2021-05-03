TRUNCATE TABLE TBL_CONTRIBUYENTES;
ALTER TABLE tbl_asientos DROP COLUMN id_user; 
ALTER TABLE tbl_asientos ADD COLUMN id_user INTEGER; 
ALTER TABLE tbl_autofacturas DROP COLUMN id_user; 
ALTER TABLE tbl_autofacturas ADD COLUMN id_user INTEGER; 
ALTER TABLE tbl_entidades DROP COLUMN id_user; 
ALTER TABLE tbl_entidades ADD COLUMN id_user INTEGER; 
ALTER TABLE tbl_evento_cuotas DROP COLUMN id_user; 
ALTER TABLE tbl_evento_cuotas ADD COLUMN id_user INTEGER; 
ALTER TABLE tbl_evento_detalle DROP COLUMN id_user; 
ALTER TABLE tbl_evento_detalle ADD COLUMN id_user INTEGER; 
ALTER TABLE tbl_eventos DROP COLUMN id_user; 
ALTER TABLE tbl_eventos ADD COLUMN id_user INTEGER; 
ALTER TABLE tbl_eventos DROP COLUMN id_grupo;
ALTER TABLE tbl_facturas DROP COLUMN id_user; 
ALTER TABLE tbl_facturas ADD COLUMN id_user INTEGER; 
ALTER TABLE tbl_facturas_compra  DROP COLUMN id_user; 
ALTER TABLE tbl_facturas_compra  ADD COLUMN id_user INTEGER; 
ALTER TABLE tbl_miembros_relaciones DROP COLUMN id_user; 
ALTER TABLE tbl_miembros_relaciones ADD COLUMN id_user INTEGER; 
ALTER TABLE tbl_notas_de_credito  DROP COLUMN id_user; 
ALTER TABLE tbl_notas_de_credito  ADD COLUMN id_user INTEGER; 
ALTER TABLE tbl_notas_de_credito_compras DROP COLUMN id_user; 
ALTER TABLE tbl_notas_de_credito_compras ADD COLUMN id_user INTEGER; 
ALTER TABLE tbl_recibos DROP COLUMN id_user; 
ALTER TABLE tbl_recibos ADD COLUMN id_user INTEGER; 
ALTER TABLE tbl_recibos_compra DROP COLUMN id_user; 
ALTER TABLE tbl_recibos_compra ADD COLUMN id_user INTEGER; 
ALTER TABLE tbl_timbrados DROP COLUMN id_user; 
ALTER TABLE tbl_timbrados ADD COLUMN id_user INTEGER; 
ALTER TABLE tbl_timbrados_autofacturas DROP COLUMN id_user; 
ALTER TABLE tbl_timbrados_autofacturas ADD COLUMN id_user INTEGER; 
ALTER TABLE tbl_timbrados_compras DROP COLUMN id_user; 
ALTER TABLE tbl_timbrados_compras ADD COLUMN id_user INTEGER; 
ALTER TABLE tbl_timbrados_notas_de_credito DROP COLUMN id_user; 
ALTER TABLE tbl_timbrados_notas_de_credito ADD COLUMN id_user INTEGER; 
ALTER TABLE tbl_transferencias DROP COLUMN id_user; 
ALTER TABLE tbl_transferencias ADD COLUMN id_user INTEGER; 
DROP TABLE tbl_roles_users;
DROP TABLE tbl_grupos_users;
DROP TABLE tbl_grupos;
DROP TABLE tbl_roles;
DROP TABLE tbl_users;
DROP TABLE tbl_database_updates;