DELETE from comercio;
DELETE from cliente;
DELETE from localidad;
DELETE from provincia;
-- MySQL dump 9.09
--
-- Host: localhost    Database: softcomerciodb
---------------------------------------------------------
-- Server version	4.0.15-nt

--
-- Table structure for table `jdo_table`
--

CREATE TABLE jdo_table (
  TABLE_ID int(11) NOT NULL default '0',
  NEXT_ID int(11) NOT NULL default '0',
  JAVA_NAME varchar(128) binary NOT NULL default '',
  TABLE_NAME varchar(64) binary NOT NULL default '',
  PRIMARY KEY  (TABLE_ID),
  UNIQUE KEY JAVA_NAME (JAVA_NAME),
  UNIQUE KEY TABLE_NAME (TABLE_NAME)
) TYPE=InnoDB;

--
-- Dumping data for table `jdo_table`
--

INSERT INTO jdo_table VALUES (1,2,'persistencia.domain.Oid','OID');
INSERT INTO jdo_table VALUES (2,2,'persistencia.domain.Localidad','LOCALIDAD');
INSERT INTO jdo_table VALUES (3,2,'persistencia.domain.Provincia','PROVINCIA');
INSERT INTO jdo_table VALUES (4,2,'persistencia.domain.Comercio','COMERCIO');
INSERT INTO jdo_table VALUES (5,2,'persistencia.domain.Cliente','CLIENTE');
INSERT INTO jdo_table VALUES (6,1,'persistencia.domain.Producto','PRODUCTO');
INSERT INTO jdo_table VALUES (7,1,'persistencia.domain.Proveedor','PROVEEDOR');
INSERT INTO jdo_table VALUES (8,2,'persistencia.domain.PlanillaES','PLANILLA_ES');
INSERT INTO jdo_table VALUES (9,1,'persistencia.domain.MovimientoCaja','MOVIMIENTO_CAJA');
INSERT INTO jdo_table VALUES (10,1,'persistencia.domain.ItemFactura','ITEM_FACTURA');
INSERT INTO jdo_table VALUES (11,1,'persistencia.domain.Factura','FACTURA');
INSERT INTO jdo_table VALUES (12,1,'persistencia.domain.FacturaCliente','FACTURA_CLIENTE');
INSERT INTO jdo_table VALUES (13,1,'persistencia.domain.FacturaProveedor','FACTURA_PROVEEDOR');

--
-- Table structure for table `oid`
--

CREATE TABLE oid (
  OID_ID bigint(20) NOT NULL default '0',
  OID bigint(20) default NULL,
  PRIMARY KEY  (OID_ID)
) TYPE=InnoDB;

--
-- Dumping data for table `oid`
--

INSERT INTO oid VALUES (35184372088832,5);

--
-- Table structure for table `provincia`
--

CREATE TABLE provincia (
  PROVINCIA_ID bigint(20) NOT NULL default '0',
  ID bigint(20) default NULL,
  NOMBRE varchar(20) binary default NULL,
  PRIMARY KEY  (PROVINCIA_ID)
) TYPE=InnoDB;

--
-- Dumping data for table `provincia`
--

INSERT INTO provincia VALUES (105553116266496,131073,'Córdoba');
INSERT INTO provincia VALUES (105553116266497,131074,'Buenos Aires');
INSERT INTO provincia VALUES (105553116266498,131075,'Santa Fe');
INSERT INTO provincia VALUES (105553116332032,778058109,'Tucuman');

--
-- Table structure for table `localidad`
--

CREATE TABLE localidad (
  LOCALIDAD_ID bigint(20) NOT NULL default '0',
  COD_POSTAL int(11) NOT NULL default '0',
  ID bigint(20) default NULL,
  NOMBRE varchar(255) binary default NULL,
  PROVINCIA_ID bigint(20) default NULL,
  PRIMARY KEY  (LOCALIDAD_ID),
  KEY LOCALIDAD_N1 (PROVINCIA_ID),
  CONSTRAINT `0_3250` FOREIGN KEY (`PROVINCIA_ID`) REFERENCES `provincia` (`PROVINCIA_ID`)
) TYPE=InnoDB;

--
-- Dumping data for table `localidad`
--

INSERT INTO localidad VALUES (70368744177664,5800,131077,'Rio Cuarto',105553116266496);
INSERT INTO localidad VALUES (70368744177665,5847,131078,'Coronel Moldes',105553116266496);
INSERT INTO localidad VALUES (70368744177666,2301,131080,'Ramona',105553116266498);
INSERT INTO localidad VALUES (70368744177667,2550,131079,'Bell Ville',105553116266496);
INSERT INTO localidad VALUES (70368744210432,0,790019,'Vicuña Mackenna',105553116266496);
INSERT INTO localidad VALUES (70368744243200,0,1012519,'Brinkmann',105553116266496);
INSERT INTO localidad VALUES (70368744243201,0,1012604,'Rosario',105553116266498);
INSERT INTO localidad VALUES (70368744275968,5800,2755243,'Cordoba Capital',105553116266496);
INSERT INTO localidad VALUES (70368744308736,5800,3475091,'Higueras',105553116266496);
INSERT INTO localidad VALUES (70368744308737,1440,3486957,'Capital Federal',105553116266497);
INSERT INTO localidad VALUES (70368744341504,0,6502247,'Malena',105553116266496);
INSERT INTO localidad VALUES (70368744374272,0,190258336,'Tosquita',105553116266496);
INSERT INTO localidad VALUES (70368744407040,5900,202673351,'Villa Maria',105553116266496);
INSERT INTO localidad VALUES (70368744439808,2677,219322255,'Ucacha',105553116266496);
INSERT INTO localidad VALUES (70368744472576,2664,220400689,'Benjamín Gould',105553116266496);
INSERT INTO localidad VALUES (70368744505344,0,247905811,'San Justo',105553116266497);
INSERT INTO localidad VALUES (70368744538112,0,273426048,'Sampacho',105553116266496);
INSERT INTO localidad VALUES (70368744570880,0,279579081,'Las Vertientes',105553116266496);
INSERT INTO localidad VALUES (70368744603648,0,312911087,'Holmberg',105553116266496);
INSERT INTO localidad VALUES (70368744636416,5800,364506086,'Bulnes',105553116266496);
INSERT INTO localidad VALUES (70368744701952,2421,549063998,'Morteros',105553116266496);
INSERT INTO localidad VALUES (70368744701953,0,549089898,'Buenos Aires',105553116266497);
INSERT INTO localidad VALUES (70368744734720,0,622593651,'Adelia Maria',105553116266496);
INSERT INTO localidad VALUES (70368744734721,0,622594245,'Monte De Los Gauchos',105553116266496);
INSERT INTO localidad VALUES (70368744734722,0,622600176,'Huanchilla',105553116266496);
INSERT INTO localidad VALUES (70368744734723,0,622608088,'San Basilio',105553116266496);
INSERT INTO localidad VALUES (70368744767488,6070,633286510,'Lincoln',105553116266497);
INSERT INTO localidad VALUES (70368744800256,6120,667895666,'Laboulaye',105553116266496);
INSERT INTO localidad VALUES (70368744833024,2432,696058604,'Arroyito',105553116266496);
INSERT INTO localidad VALUES (70368744865792,5848,778049866,'Las Acequias',105553116266496);
INSERT INTO localidad VALUES (70368744865793,0,778058110,'Concepcion',105553116332032);
INSERT INTO localidad VALUES (70368744898560,0,1022951100,'Navarro',105553116266497);

--
-- Table structure for table `cliente`
--

CREATE TABLE cliente (
  CLIENTE_ID bigint(20) NOT NULL default '0',
  CUIT varchar(255) binary default NULL,
  DIRECCION varchar(255) binary default NULL,
  ID bigint(20) default NULL,
  ING_BRUTOS_CL varchar(255) binary default NULL,
  IVA_CL varchar(255) binary default NULL,
  LOCALIDAD_ID bigint(20) default NULL,
  NOMBRE varchar(255) binary default NULL,
  TELEFONO varchar(255) binary default NULL,
  DEUDA double NOT NULL default '0',
  FECHA_UF date default NULL,
  PRIMARY KEY  (CLIENTE_ID),
  KEY CLIENTE_N1 (LOCALIDAD_ID),
  CONSTRAINT `0_3252` FOREIGN KEY (`LOCALIDAD_ID`) REFERENCES `localidad` (`LOCALIDAD_ID`)
) TYPE=InnoDB;

--
-- Dumping data for table `cliente`
--

INSERT INTO cliente VALUES (175921860444160,'','-',131080,'','Consumidor Final',70368744177664,'-','',0,NULL);
INSERT INTO cliente VALUES (175921860444161,'27-14940329-4','Av 25 De Mayo Y Utreras',131085,'','Resp. Inscripto',70368744177665,'Heredia Norma Beatriz','',0,NULL);
INSERT INTO cliente VALUES (175921860444167,'20-08008991-1','Aristizabal 572',131091,'209342308','Resp. Inscripto',70368744177665,'Moiso Juan Carlos','',0,NULL);
INSERT INTO cliente VALUES (175921860444168,'20-06647447-0','Sarmiento Y 9 De Julio',131092,'0209038125','Resp. Inscripto',70368744177665,'Morano Federico Alfredo_','',0,NULL);
INSERT INTO cliente VALUES (175921860444170,'20-18046657-7','Mitre 787',131094,'','Monotributo',70368744177665,'Airaudo Miguel Ceferino','',0,NULL);
INSERT INTO cliente VALUES (175921860444172,'27-16121932-6','Almirante Brown 317',131096,'','Monotributo',70368744177665,'Moyano Lucia','',0,NULL);
INSERT INTO cliente VALUES (175921860444173,'27-29484837-6','Saavedra 235',131097,'','Monotributo',70368744177665,'Furlan Mariela','',0,NULL);
INSERT INTO cliente VALUES (175921860444174,'20-12556343-1','Pje. Arcomano 48',131098,'','Monotributo',70368744177665,'Villarruel Raúl Enrique','',0,NULL);
INSERT INTO cliente VALUES (175921860444175,'27-05450717-3','Mitre Y Cincuentenario',131099,'209443392','Resp. Inscripto',70368744177665,'Besso Nori Esther','',0,NULL);
INSERT INTO cliente VALUES (175921860444177,'23-26925798-9','Juan Jose Paso Y Albertio',131101,'','Monotributo',70368744177665,'Vasquez Martin Amadeo','',0,NULL);
INSERT INTO cliente VALUES (175921860444178,'23-14864994-4','Av. 9 De Julio 699',131102,'','Monotributo',70368744177665,'Dalio Maria Silvia','',0,NULL);
INSERT INTO cliente VALUES (175921860444179,'','----',131103,'','Consumidor Final',70368744177665,'Achabal Carmen','',0,NULL);
INSERT INTO cliente VALUES (175921860444180,'20-21040658-2','--',131104,'','Monotributo',70368744177665,'Gregorat','',0,NULL);
INSERT INTO cliente VALUES (175921860444182,'20-13078898-0','9 De Julio 368',131106,'','Monotributo',70368744177665,'Paschetta Javier Ignacio','',0,NULL);
INSERT INTO cliente VALUES (175921860444183,'20-25796967-4','Belgrano 418',131107,'','Resp. Inscripto',70368744177665,'Gariboldi Diego Ruben','',0,NULL);
INSERT INTO cliente VALUES (175921860444184,'','..',131108,'','Consumidor Final',70368744177665,'Dominguez Miroslava','',0,NULL);
INSERT INTO cliente VALUES (175921860444186,'27-26210442-2','Santa Maria De Oro Y Cabrera',131110,'','Monotributo',70368744177665,'Arocena Nancy Verónica','',0,NULL);
INSERT INTO cliente VALUES (175921860444190,'20-36679983-5','Ameguino 794',131114,'','Monotributo',70368744177665,'Rosales Lucas Matias','',0,NULL);
INSERT INTO cliente VALUES (175921860444194,'','--',131118,'','Consumidor Final',70368744177665,'Escudero Basilio','',0,NULL);
INSERT INTO cliente VALUES (175921860444196,'27-17160850-9','Santa Maria De Oro 555',131120,'','Resp. Inscripto',70368744177665,'Centeno Gabriela Alejandra','',0,NULL);
INSERT INTO cliente VALUES (175921860444198,'20-13344933-8','Chacabuco 98',131122,'','Monotributo',70368744177664,'Doblas Jose Osmar','154293166',0,NULL);
INSERT INTO cliente VALUES (175921860444199,'27-21999115-6','Tucumán 1050',131123,'','Monotributo',70368744177664,'Rama Analia','3585132477',0,NULL);
INSERT INTO cliente VALUES (175921860444202,'27-18559031-9','Luis Pasteur 1411',131126,'','Monotributo',70368744177664,'Strazzi Gabriela Beatriz','4661074',0,NULL);
INSERT INTO cliente VALUES (175921860444203,'27-24352484-4','Tucumán 1650',131127,'','Monotributo',70368744177664,'Irusta Patricia Rosana','4662241',0,NULL);
INSERT INTO cliente VALUES (175921860444204,'','Entre Rios',131128,'','Consumidor Final',70368744177664,'Moreno Claudia','',0,NULL);
INSERT INTO cliente VALUES (175921860444205,'20-12281513-4','Fray Benitto Tessitore 160',131129,'','Monotributo',70368744177664,'Macho Miguel Angel','',0,NULL);
INSERT INTO cliente VALUES (175921860444206,'','Pirovano 146',131130,'','Consumidor Final',70368744177664,'Valfre Luis','',0,NULL);
INSERT INTO cliente VALUES (175921860444209,'','Rafael Obligado',131133,'','Consumidor Final',70368744177664,'Gomez','',0,NULL);
INSERT INTO cliente VALUES (175921860444210,'','Salta',131134,'','Consumidor Final',70368744177664,'Pizzería Belen','',0,NULL);
INSERT INTO cliente VALUES (175921860444211,'23-32015494-4','Yapeyu 120',131135,'','Monotributo',70368744177664,'Giraudo Natalia Cecilia','',0,NULL);
INSERT INTO cliente VALUES (175921860444213,'','Aristobulo Del Valle 441',131137,'','Consumidor Final',70368744177664,'Varela Carolina Mirta','4652398',0,NULL);
INSERT INTO cliente VALUES (175921860444217,'','Santiago del Estero',131141,'','Consumidor Final',70368744177664,'Pizería Taty','',0,NULL);
INSERT INTO cliente VALUES (175921860444221,'27-22843735-8','Estrada Norte 357',131145,'','Monotributo',70368744177664,'Alfonso Ester Lidia','',0,NULL);
INSERT INTO cliente VALUES (175921860444223,'','Lago Nahuel Huapi 1800',131147,'','Consumidor Final',70368744177664,'Moreira Ivana','154012623',0,NULL);
INSERT INTO cliente VALUES (175921860444225,'20-16486067-2','Roberto Pairo 1290',131149,'','Monotributo',70368744177664,'Pereyra Luis','156548490',0,NULL);
INSERT INTO cliente VALUES (175921860444230,'','Marcelo T. De Alvear',131154,'','Consumidor Final',70368744177664,'Esmoris Valeria Noemi','',0,NULL);
INSERT INTO cliente VALUES (175921860444231,'20-23030361-5','M T De Alvear 1598',131155,'','Monotributo',70368744177664,'Moreno Anibal','',0,NULL);
INSERT INTO cliente VALUES (175921860444233,'20-25136638-2','M T De Alvear 1994',131157,'','Monotributo',70368744177664,'Caceres Jorge Eduardo','155093651',0,NULL);
INSERT INTO cliente VALUES (175921860476928,'20-06649564-8','Rosso Pierino 705',788885,'','Monotributo',70368744177664,'Aguirre Luis Alberto','',0,NULL);
INSERT INTO cliente VALUES (175921860476931,'27-29402478-1','La Rioja 2269',789842,'','Monotributo',70368744177664,'Abregu Malvina Soledad','',0,NULL);
INSERT INTO cliente VALUES (175921860476932,'20-16121931-3','Hipolito Irugoyen 464',790037,'','Resp. Inscripto',70368744210432,'Aguilera Gerardo Eduardo','',0,NULL);
INSERT INTO cliente VALUES (175921860476934,'20-08401140-2','Montevideo 1329',790392,'','Monotributo',70368744177664,'Norabueno Alfredo Armando','4663545',0,NULL);
INSERT INTO cliente VALUES (175921860476936,'20-21407548-3','San Martin 40',791408,'','Monotributo',70368744177665,'Chiecher Roberto','',0,NULL);

--
-- Table structure for table `proveedor`
--

CREATE TABLE proveedor (
  PROVEEDOR_ID bigint(20) NOT NULL default '0',
  CODIGO int(11) NOT NULL default '0',
  DIRECCION varchar(255) binary default NULL,
  ID bigint(20) default NULL,
  LOCALIDAD_ID bigint(20) default NULL,
  NOMBRE varchar(255) binary default NULL,
  TELEFONO varchar(255) binary default NULL,
  DEUDA double NOT NULL default '0',
  FECHA_UF date default NULL,
  PRIMARY KEY  (PROVEEDOR_ID),
  KEY PROVEEDOR_N1 (LOCALIDAD_ID),
  CONSTRAINT `0_3254` FOREIGN KEY (`LOCALIDAD_ID`) REFERENCES `localidad` (`LOCALIDAD_ID`)
) TYPE=InnoDB;

--
-- Dumping data for table `proveedor`
--

INSERT INTO proveedor VALUES (246290604621824,1,'Zona Rural',131159,70368744177666,'Lacteos Ramolac','03492-496013/496123',0,NULL);
INSERT INTO proveedor VALUES (246290604621825,2,'Guemes Y Marmol',131184,70368744243200,'Fiambres Piamontesa','03562-480142',0,NULL);
INSERT INTO proveedor VALUES (246290604621826,3,'Pedro Goyena 1150',131201,70368744177664,'Pastas El Manolito','0358-4662905',0,NULL);
INSERT INTO proveedor VALUES (246290604621827,4,'Rio Negro 67',131219,70368744177667,'Dulces Beltop','03537-425103/415103',0,NULL);
INSERT INTO proveedor VALUES (246290604621828,5,'Dr. V Vaggione 16',131231,70368744177665,'Fiambre Don Juan','03582-482376/482311',0,NULL);
INSERT INTO proveedor VALUES (246290604654592,6,'Santiago 29',1012629,70368744243201,'Lacteos Veronica','439-6332',0,NULL);
INSERT INTO proveedor VALUES (246290604687360,7,'Parque Industrial',2248467,70368744177664,'Anibal Barbero','',0,NULL);
INSERT INTO proveedor VALUES (246290604687361,8,'Mitre 900',2295035,70368744177664,'Cirletti','',0,NULL);
INSERT INTO proveedor VALUES (246290604720128,9,'Cordoba Capital',2755265,70368744275968,'Lacteos Manfrey','0351-4951500',0,NULL);
INSERT INTO proveedor VALUES (246290604752896,11,'Bregado 6759',3486983,70368744308737,'El Bierzo','011- 46878787',0,NULL);
INSERT INTO proveedor VALUES (246290604785664,12,'--',95419034,70368744177665,'Roggero','--',0,NULL);
INSERT INTO proveedor VALUES (246290604818432,13,'-',149170461,70368744177664,'Litinense','-',0,NULL);
INSERT INTO proveedor VALUES (246290604851200,14,'Lavalle 1430-piso 3',168302695,70368744308737,'Recreo','0342-4905088',0,NULL);
INSERT INTO proveedor VALUES (246290604883968,15,'Santa Fe 2337',202673383,70368744407040,'Doña Elsa','0353-4524162',0,NULL);
INSERT INTO proveedor VALUES (246290604916736,16,'Bedoya 840- Zona Rural Ucacha',219322289,70368744439808,'Savaz S.r.l.','4861521',0,NULL);
INSERT INTO proveedor VALUES (246290604916737,17,'Chaco 6349',219324517,70368744243201,'Tacural','0342-4891307',0,NULL);
INSERT INTO proveedor VALUES (246290604949504,18,'Gral. Paz 682',220400725,70368744472576,'Quesada','03463-494013',0,NULL);
INSERT INTO proveedor VALUES (246290604982272,19,'Jose Marmol 3749',247905849,70368744505344,'Limonar','54-11-44826761',0,NULL);
INSERT INTO proveedor VALUES (246290605015040,20,'Villa Gob. Galves',310177973,70368744243201,'Paladini','0341-4921801',0,NULL);
INSERT INTO proveedor VALUES (246290605047808,10,'Asamblea 300',373644867,70368744308737,'Danica','011-42393600',0,NULL);

--
-- Table structure for table `producto`
--

CREATE TABLE producto (
  PRODUCTO_ID bigint(20) NOT NULL default '0',
  CODIGO bigint(20) default '0',
  ID bigint(20) default NULL,
  NOMBRE varchar(255) binary default NULL,
  PRECIO_ENTRADA double NOT NULL default '0',
  PRECIO_KILOS tinyint(1) NOT NULL default '0',
  PRECIO_VENTA_SIN_IVA double NOT NULL default '0',
  PROVEEDOR_ID bigint(20) default NULL,
  STOCK_ACTUAL int(11) NOT NULL default '0',
  STOCK_MINIMO int(11) NOT NULL default '0',
  GANANCIA int(11) NOT NULL default '0',
  PRECIO_ENT_CIVA tinyint(1) NOT NULL default '0',
  PRECIO_VENTA_CON_IVA double NOT NULL default '0',
  STOCK_KILOS_ACT double NOT NULL default '0',
  STOCK_KILOS_MIN double NOT NULL default '0',
  PRIMARY KEY  (PRODUCTO_ID),
  KEY PRODUCTO_N1 (PROVEEDOR_ID),
  CONSTRAINT `0_3256` FOREIGN KEY (`PROVEEDOR_ID`) REFERENCES `proveedor` (`PROVEEDOR_ID`)
) TYPE=InnoDB;

--
-- Dumping data for table `producto`
--

INSERT INTO producto VALUES (211106232532992,1001,131161,'Leche entera Ramolac x 1 lts',3.6,0,4.35,246290604621824,0,1,20,1,5.25,0,0);
INSERT INTO producto VALUES (211106232532993,1002,131162,'Yogur frutilla entero x 1 lts',4,0,5.65,246290604621824,-3,12,41,1,6.85,0,0);
INSERT INTO producto VALUES (211106232532994,1003,131163,'Yogur frutilla x 125 grs',1.1,0,1.65,246290604621824,0,1,50,1,2,0,0);
INSERT INTO producto VALUES (211106232532995,1004,131164,'Yogur c/cereal x 165 grs Ramolac',3.2,0,4.5,246290604621824,498,24,40,0,5.45,0,0);
INSERT INTO producto VALUES (211106232532996,1005,131165,'Manteca x 100 grs',4.15,0,5.4,246290604621824,-1585,1,30,0,6.55,0,0);
INSERT INTO producto VALUES (211106232532997,1006,131166,'Manteca x 200 grs',8.3,0,10.8,246290604621824,-163,1,30,0,13.05,0,0);
INSERT INTO producto VALUES (211106232532998,1007,131167,'Crema x 200 grs Ramolac',6.2,0,8.4,246290604621824,-205,1,35,0,10.15,0,0);
INSERT INTO producto VALUES (211106232532999,1008,131168,'Crema x 350 grs Ramolac',11,0,13.75,246290604621824,182,1,25,0,16.65,0,0);
INSERT INTO producto VALUES (211106232533000,1009,131169,'Crema x 2 kg Ramolac',56.6,0,71.9,246290604621824,-2,1,27,0,87,0,0);
INSERT INTO producto VALUES (211106232533001,1010,131170,'Crema x 5 kg Ramolac',138.5,0,171.75,246290604621824,5,1,24,0,207.85,0,0);
INSERT INTO producto VALUES (211106232533002,1011,131171,'Dulce de leche x 200 grs Ramolac',3.81,0,5.05,246290604621824,-4,1,32,0,6.1,0,0);
INSERT INTO producto VALUES (211106232533003,1012,131172,'Dulce de leche x 400 grs Ramolac',6.36,0,8.65,246290604621824,4,1,36,0,10.5,0,0);
INSERT INTO producto VALUES (211106232533004,1014,131173,'Dulce de leche x 3 kg Ramolac',49.6,0,67,246290604621824,-3,1,35,0,81.05,0,0);
INSERT INTO producto VALUES (211106232533005,1015,131174,'Dulce de leche x 5 kg Ramolac',71.94,0,89.95,246290604621824,0,1,25,0,108.85,0,0);
INSERT INTO producto VALUES (211106232533006,1016,131175,'Queso cremoso Ramolac',33,1,44.55,246290604621824,144,1,35,0,53.95,0,0);
INSERT INTO producto VALUES (211106232533007,1017,131176,'Queso cremoso x 1/2 pieza Ramolac',33.28,1,45.95,246290604621824,-183,1,38,0,55.6,0,0);
INSERT INTO producto VALUES (211106232533008,1018,131177,'Queso Barra Ramolac',42,1,54.6,246290604621824,371,1,30,0,66.1,0,0);
INSERT INTO producto VALUES (211106232533010,1019,131179,'Queso Sardo estacionado',59,1,77.9,246290604621824,53,1,32,0,94.25,0,0);
INSERT INTO producto VALUES (211106232533011,1020,131180,'Queso Sardo Estancia Amanecer',35.54,1,49.4,246290604785664,34,1,39,0,58.9,0,0);
INSERT INTO producto VALUES (211106232533015,2001,131185,'Mortadela cilindro 808',24.9,1,33.85,246290604621825,3,1,30,0,39.2,0,0);
INSERT INTO producto VALUES (211106232533016,2002,131186,'Mortadela bocha vejiga  Piamontesa',42.33,1,52.95,246290604621825,-5,1,25,0,64.05,0,0);
INSERT INTO producto VALUES (211106232533017,2003,131187,'Salame tipo milan Piamontesa',61.49,1,79.95,246290604621825,-45,1,30,0,96.75,0,0);
INSERT INTO producto VALUES (211106232533018,2004,131188,'Salame colonia x 1/2 metro',72.37,1,97,246290604621825,5,0,34,0,117.35,0,0);
INSERT INTO producto VALUES (211106232533019,2005,131189,'Bondiola Piamontesa',120.17,1,150.25,246290604621825,-29,0,25,0,181.8,0,0);
INSERT INTO producto VALUES (211106232533021,2007,131191,'Panceta arrollada Piamontesa',91.67,1,119.2,246290604621825,29,0,30,0,144.2,0,0);
INSERT INTO producto VALUES (211106232533022,2008,131192,'Jamon crudo Pie del monte',131,1,163.75,246290604621825,-1,0,25,0,198.15,0,0);
INSERT INTO producto VALUES (211106232533024,2010,131194,'Paleta cocida 131',31.57,1,39.5,246290604621825,-672,4,25,0,47.75,0,0);
INSERT INTO producto VALUES (211106232533029,2015,131199,'Salchichas Panchin',5.1,0,6.65,246290604621825,693,0,30,0,8.05,0,0);
INSERT INTO producto VALUES (211106232533032,3002,131203,'Ravioles de pollo x 3 pl',8.88,0,8.1,246290604621826,0,1,10,1,9.8,0,0);
INSERT INTO producto VALUES (211106232533037,3007,131208,'Agnolottis x 500 gr',7.02,0,6.4,246290604621826,1,1,10,1,7.75,0,0);
INSERT INTO producto VALUES (211106232533040,3010,131211,'Fideos anchos al huevo x 500 gr',3.51,0,3.2,246290604621826,0,1,10,1,3.9,0,0);
INSERT INTO producto VALUES (211106232533043,3013,131214,'Masa para empanadas de freir',2.81,0,2.55,246290604621826,4,1,10,1,3.1,0,0);
INSERT INTO producto VALUES (211106232533044,3014,131215,'Masa para empanadas de horno',2.81,0,2.55,246290604621826,4,1,10,1,3.1,0,0);
INSERT INTO producto VALUES (211106232533046,3016,131217,'Masa para pasteles',3.39,0,3.1,246290604621826,6,1,10,1,3.75,0,0);
INSERT INTO producto VALUES (211106232533047,3017,131218,'Masa para Pascualina',3.51,0,3.2,246290604621826,0,1,10,1,3.9,0,0);

--
-- Table structure for table `factura`
--

CREATE TABLE factura (
  FACTURA_ID bigint(20) NOT NULL default '0',
  ID bigint(20) default NULL,
  IMPORTE_AUX_IVA double NOT NULL default '0',
  IMPORTE_TOTAL double NOT NULL default '0',
  NRO_FACTURA bigint(20) default NULL,
  LUGAR varchar(255) default '',
  ANULADA tinyint(1) NOT NULL default '0',
  TIPO_FACTURA varchar(255) binary default NULL,
  PERIODO varchar(255) binary default NULL,
  DIA_BUSCAR int(11) NOT NULL default '0',
  PRIMARY KEY  (FACTURA_ID)
) TYPE=InnoDB;

--
-- Dumping data for table `factura`
--


--
-- Table structure for table `factura_cliente`
--

CREATE TABLE factura_cliente (
  FACTURA_CLIENTE_ID bigint(20) NOT NULL default '0',
  CLIENTE_ID bigint(20) default NULL,
  COND_VENTA varchar(255) binary default NULL,
  FECHA_IMPRESION date default NULL,
  FECHA_PAGO date default NULL,
  IMPORTE_ABONADO double NOT NULL default '0',
  INGR_BRUTOS varchar(255) binary default NULL,
  IVA_F varchar(255) binary default NULL,
  REMITO_NRO varchar(255) binary default NULL,
  PLANILLA_ID bigint(20) default NULL,
  PRIMARY KEY  (FACTURA_CLIENTE_ID),
  KEY FACTURA_CLIENTE_N1 (CLIENTE_ID),
  KEY F_CLIENTE_N3 (PLANILLA_ID),
  CONSTRAINT `0_3259` FOREIGN KEY (`CLIENTE_ID`) REFERENCES `cliente` (`CLIENTE_ID`),
  CONSTRAINT `0_3260` FOREIGN KEY (`FACTURA_CLIENTE_ID`) REFERENCES `factura` (`FACTURA_ID`),
  CONSTRAINT `0_3275` FOREIGN KEY (`PLANILLA_ID`) REFERENCES `planilla_es` (`PLANILLA_ES_ID`)
) TYPE=InnoDB;

--
-- Dumping data for table `factura_cliente`
--


--
-- Table structure for table `factura_proveedor`
--

CREATE TABLE factura_proveedor (
  FACTURA_PROVEEDOR_ID bigint(20) NOT NULL default '0',
  FECHA date default NULL,
  PROVEEDOR_ID bigint(20) default NULL,
  PRIMARY KEY  (FACTURA_PROVEEDOR_ID),
  KEY FACTURA_PROVEEDOR_N1 (PROVEEDOR_ID),
  CONSTRAINT `0_3262` FOREIGN KEY (`FACTURA_PROVEEDOR_ID`) REFERENCES `factura` (`FACTURA_ID`),
  CONSTRAINT `0_3263` FOREIGN KEY (`PROVEEDOR_ID`) REFERENCES `proveedor` (`PROVEEDOR_ID`)
) TYPE=InnoDB;

--
-- Dumping data for table `factura_proveedor`
--


--
-- Table structure for table `planilla_es`
--

CREATE TABLE planilla_es (
  PLANILLA_ES_ID bigint(20) NOT NULL default '0',
  FECHA_P date default NULL,
  ID bigint(20) default NULL,
  NRO_PLANILLA int(11) NOT NULL default '0',
  SALDO double NOT NULL default '0',
  PERIODO varchar(255) binary default NULL,
  PRIMARY KEY  (PLANILLA_ES_ID)
) TYPE=InnoDB;

--
-- Dumping data for table `planilla_es`
--


--
-- Table structure for table `movimiento_caja`
--

CREATE TABLE movimiento_caja (
  MOVIMIENTO_CAJA_ID bigint(20) NOT NULL default '0',
  CODIGO int(11) NOT NULL default '0',
  CON_FACTURA tinyint(1) NOT NULL default '0',
  DESCRIPCION varchar(255) binary default NULL,
  FACTURA_ID bigint(20) default NULL,
  FECHA_MC date default NULL,
  FORMA_PAGO varchar(255) binary default NULL,
  ID bigint(20) default NULL,
  IMPORTE double NOT NULL default '0',
  NRO_CHEQUE bigint(20) default NULL,
  PLANILLA_ID bigint(20) default NULL,
  TIPO_FACTURA varchar(255) binary default NULL,
  TIPO_MOVIMIENTO int(11) NOT NULL default '0',
  PERIODO varchar(255) binary default NULL,
  PRIMARY KEY  (MOVIMIENTO_CAJA_ID),
  KEY MOVIMIENTO_CAJA_N2 (PLANILLA_ID),
  KEY MOVIMIENTO_CAJA_N1 (FACTURA_ID),
  CONSTRAINT `0_3266` FOREIGN KEY (`PLANILLA_ID`) REFERENCES `planilla_es` (`PLANILLA_ES_ID`),
  CONSTRAINT `0_3267` FOREIGN KEY (`FACTURA_ID`) REFERENCES `factura` (`FACTURA_ID`)
) TYPE=InnoDB;

--
-- Dumping data for table `movimiento_caja`
--


--
-- Table structure for table `item_factura`
--

CREATE TABLE item_factura (
  ITEM_FACTURA_ID bigint(20) NOT NULL default '0',
  CANTIDAD int(11) NOT NULL default '0',
  DESCUENTO int(11) NOT NULL default '0',
  FACTURA_ID bigint(20) default NULL,
  ID bigint(20) default NULL,
  KILOS double NOT NULL default '0',
  PR_TOTAL double NOT NULL default '0',
  PR_UNIT double NOT NULL default '0',
  PRODUCTO_ID bigint(20) default NULL,
  PRIMARY KEY  (ITEM_FACTURA_ID),
  KEY ITEM_FACTURA_N1 (PRODUCTO_ID),
  KEY ITEM_FACTURA_N2 (FACTURA_ID),
  CONSTRAINT `0_3269` FOREIGN KEY (`PRODUCTO_ID`) REFERENCES `producto` (`PRODUCTO_ID`),
  CONSTRAINT `0_3270` FOREIGN KEY (`FACTURA_ID`) REFERENCES `factura` (`FACTURA_ID`)
) TYPE=InnoDB;

--
-- Dumping data for table `item_factura`
--


--
-- Table structure for table `comercio`
--

CREATE TABLE comercio (
  COMERCIO_ID bigint(20) NOT NULL default '0',
  CATEGORIA varchar(255) binary default NULL,
  CUIT varchar(255) binary default NULL,
  DIRECCION varchar(255) binary default NULL,
  ID bigint(20) default NULL,
  INICIO_ACT date default NULL,
  LOCALIDAD_ID bigint(20) default NULL,
  NOMBRE varchar(255) binary default NULL,
  NRO_FACT_A bigint(20) default NULL,
  NRO_FACT_B bigint(20) default NULL,
  NRO_REMITO bigint(20) default NULL,
  TELEFONO varchar(255) binary default NULL,
  PRIMARY KEY  (COMERCIO_ID),
  KEY COMERCIO_N1 (LOCALIDAD_ID),
  CONSTRAINT `0_3272` FOREIGN KEY (`LOCALIDAD_ID`) REFERENCES `localidad` (`LOCALIDAD_ID`)
) TYPE=InnoDB;

--
-- Dumping data for table `comercio`
--

INSERT INTO comercio VALUES (140737488355328,'','20-28650842-2','-',131082,'2011-05-23',70368744177665,'-',1,1,1,'');

