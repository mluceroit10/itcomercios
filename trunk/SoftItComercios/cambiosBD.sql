use softcomerciodb;

alter table producto
add column STOCK_KILOS_ACT double NOT NULL default '0',
add column STOCK_KILOS_MIN double NOT NULL default '0';
/*ya se registro esto en la crecion de las tablas*/