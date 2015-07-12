use softcomerciodb;
/*ya se registro esto en la crecion de las tablas*/
     /*
Deuda y fecha de ultima Facturacion al cliente y al proveedor
factura dia_buscar  */
/*alter table FACTURA
add column DIA_BUSCAR  int(11) NOT NULL default '0';

alter table FACTURA_CLIENTE
add column PLANILLA_ID bigint(20) default NULL,
add KEY F_CLIENTE_N3 (PLANILLA_ID),
add CONSTRAINT `0_1665` FOREIGN KEY (`PLANILLA_ID`) REFERENCES `PLANILLA_ES` (`PLANILLA_ES_ID`);

alter table CLIENTE
add column DEUDA  double NOT NULL default '0',
add column FECHA_UF date default NULL;

alter table PROVEEDOR
add column DEUDA  double NOT NULL default '0',
add column FECHA_UF date default NULL;*/

/*alter table FACTURA_PROVEEDOR
add column CARGA_PARCIAL tinyint(1) NOT NULL default 0;

alter table ITEM_FACTURA
add column FECHA_VTO date default NULL;     */

alter table FACTURA_CLIENTE
MODIFY FECHA_IMPRESION datetime default NULL;

alter table PLANILLA_ES
MODIFY FECHA_P datetime default NULL;