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
INSERT INTO jdo_table VALUES (4,1,'persistencia.domain.Distribuidora','DISTRIBUIDORA');
INSERT INTO jdo_table VALUES (5,4,'persistencia.domain.Cliente','CLIENTE');
INSERT INTO jdo_table VALUES (6,9,'persistencia.domain.Producto','PRODUCTO');
INSERT INTO jdo_table VALUES (7,4,'persistencia.domain.Proveedor','PROVEEDOR');
INSERT INTO jdo_table VALUES (8,7,'persistencia.domain.PlanillaES','PLANILLA_ES');
INSERT INTO jdo_table VALUES (9,16,'persistencia.domain.MovimientoCaja','MOVIMIENTO_CAJA');
INSERT INTO jdo_table VALUES (10,20,'persistencia.domain.ItemFactura','ITEM_FACTURA');
INSERT INTO jdo_table VALUES (11,1,'persistencia.domain.Factura','FACTURA');
INSERT INTO jdo_table VALUES (12,16,'persistencia.domain.FacturaCliente','FACTURA_CLIENTE');
INSERT INTO jdo_table VALUES (13,14,'persistencia.domain.FacturaProveedor','FACTURA_PROVEEDOR');
INSERT INTO jdo_table VALUES (14,2,'persistencia.domain.Comercio','COMERCIO');

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

INSERT INTO oid VALUES (35184372088832,107);

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
INSERT INTO provincia VALUES (105553116299264,196611,'Córdoba');

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
  CONSTRAINT `0_2017` FOREIGN KEY (`PROVINCIA_ID`) REFERENCES `provincia` (`PROVINCIA_ID`)
) TYPE=InnoDB;

--
-- Dumping data for table `localidad`
--

INSERT INTO localidad VALUES (70368744177664,5800,131077,'Río Cuarto',105553116266496);
INSERT INTO localidad VALUES (70368744177665,5847,131078,'Coronel Moldes',105553116266496);
INSERT INTO localidad VALUES (70368744177666,2301,131080,'Ramona',105553116266498);
INSERT INTO localidad VALUES (70368744177667,2550,131079,'Bell Ville',105553116266496);
INSERT INTO localidad VALUES (70368744210432,5800,196616,'Rio Cuarto',105553116299264);
INSERT INTO localidad VALUES (70368744243200,0,1012519,'Brinkmann',105553116266496);
INSERT INTO localidad VALUES (70368744243201,0,1012604,'Rosario',105553116266498);
INSERT INTO localidad VALUES (70368744275968,5800,2755243,'Cordoba Capital',105553116266496);
INSERT INTO localidad VALUES (70368744308736,5800,3475091,'Higueras',105553116266496);
INSERT INTO localidad VALUES (70368744308737,1440,3486957,'Capital Federal',105553116266497);
INSERT INTO localidad VALUES (70368744341504,0,6502247,'Malena',105553116266496);

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
  PRIMARY KEY  (CLIENTE_ID),
  KEY CLIENTE_N1 (LOCALIDAD_ID),
  CONSTRAINT `0_2019` FOREIGN KEY (`LOCALIDAD_ID`) REFERENCES `localidad` (`LOCALIDAD_ID`)
) TYPE=InnoDB;

--
-- Dumping data for table `cliente`
--

INSERT INTO cliente VALUES (175921860444161,'27-14940329-4','Av 25 de Mayo y Utreras',131085,'','Resp. Inscripto',70368744177665,'Heredia Norma Beatriz','');
INSERT INTO cliente VALUES (175921860444162,'27-12556368-1','Santa María de Oro 142',131086,'209277905','Resp. Inscripto',70368744177665,'Zanotti Patricia Mabel','');
INSERT INTO cliente VALUES (175921860444163,'27-05816129-8','Moreno 16',131087,'','Resp. Inscripto',70368744177665,'Villegas Rosa','');
INSERT INTO cliente VALUES (175921860444164,'20-06647447-0','Ameghino y Dixon',131088,'','Resp. Inscripto',70368744177665,'Morano Federico Alfredo','');
INSERT INTO cliente VALUES (175921860444165,'20-16404004-7','Roque Saenz Peña 289',131089,'202981244','Resp. Inscripto',70368744177665,'Angeli Sergio Javier','');
INSERT INTO cliente VALUES (175921860444167,'20-08008991-1','Aristizabal 572',131091,'209342308','Resp. Inscripto',70368744177665,'Moiso Juan Carlos','');
INSERT INTO cliente VALUES (175921860444168,'20-06647447-0','Sarmiento y 9 de Julio',131092,'0209038125','Resp. Inscripto',70368744177665,'Morano Federico Alfredo_','');
INSERT INTO cliente VALUES (175921860444169,'27-22347367-4','Roque Saenz Peña 320',131093,'280265527','Monotributo',70368744177665,'López Mónica María','');
INSERT INTO cliente VALUES (175921860444170,'20-18046657-7','Mitre 787',131094,'','Monotributo',70368744177665,'Airaudo Miguel Ceferino','');
INSERT INTO cliente VALUES (175921860444171,'27-11347521-3','Cabrera 24',131095,'','Monotributo',70368744177665,'Cabral María del Carmen','');
INSERT INTO cliente VALUES (175921860444172,'27-16121932-6','Almirante Brown 317',131096,'','Monotributo',70368744177665,'Moyano Lucia','');
INSERT INTO cliente VALUES (175921860444173,'27-29484837-6','Saavedra 235',131097,'','Monotributo',70368744177665,'Furlan Mariela','');
INSERT INTO cliente VALUES (175921860444174,'20-12556343-1','Pje. Arcomano 48',131098,'','Monotributo',70368744177665,'Villarruel Raúl Enrique','');
INSERT INTO cliente VALUES (175921860444175,'27-05450717-3','Mitre Y Cincuentenario',131099,'209443392','Resp. Inscripto',70368744177665,'Besso Nori Esther','');
INSERT INTO cliente VALUES (175921860444177,'23-26925798-9','Juan Jose Paso Y Albertio',131101,'','Monotributo',70368744177665,'Vasquez Martin Amadeo','');
INSERT INTO cliente VALUES (175921860444178,'23-14864994-4','Av. 9 De Julio 699',131102,'','Monotributo',70368744177665,'Dalio Maria Silvia','');
INSERT INTO cliente VALUES (175921860444179,'','',131103,'','Consumidor Final',70368744177665,'Achabal Carmen','');
INSERT INTO cliente VALUES (175921860444180,'','',131104,'','Consumidor Final',70368744177665,'Gregorat','');
INSERT INTO cliente VALUES (175921860444181,'','',131105,'','Consumidor Final',70368744177665,'Valderas Walter','');
INSERT INTO cliente VALUES (175921860444182,'20-13078898-0','9 De Julio 368',131106,'','Monotributo',70368744177665,'Paschetta Javier Ignacio','');
INSERT INTO cliente VALUES (175921860444183,'20-17485133-7','Monteagudo 128',131107,'','Monotributo',70368744177665,'Airaudo Julio','');
INSERT INTO cliente VALUES (175921860444184,'','',131108,'','Consumidor Final',70368744177665,'Dominguez Miroslava','');
INSERT INTO cliente VALUES (175921860444185,'','',131109,'','Consumidor Final',70368744177665,'Roggero','');
INSERT INTO cliente VALUES (175921860444186,'27-26210442-2','Santa Maria De Oro Y Cabrera',131110,'','Monotributo',70368744177665,'Arocena Nancy Verónica','');
INSERT INTO cliente VALUES (175921860444190,'20-22077512-8','Ameguino 794',131114,'','Resp. Inscripto',70368744177665,'Rostagño Ariel Antonio','');
INSERT INTO cliente VALUES (175921860444192,'','',131116,'','Consumidor Final',70368744177665,'Nicolay Cacho','');
INSERT INTO cliente VALUES (175921860444193,'','',131117,'','Consumidor Final',70368744177665,'Gastaldi','');
INSERT INTO cliente VALUES (175921860444194,'','',131118,'','Consumidor Final',70368744177665,'Escudero Basilio','');
INSERT INTO cliente VALUES (175921860444196,'27-17160850-9','Santa Maria De Oro 555',131120,'','Resp. Inscripto',70368744177665,'Centeno Gabriela Alejandra','');
INSERT INTO cliente VALUES (175921860444198,'20-13344933-8','Chacabuco 98',131122,'','Monotributo',70368744177664,'Doblas Jose Osmar','');
INSERT INTO cliente VALUES (175921860444199,'','Tucumán 1050',131123,'','Consumidor Final',70368744177664,'Sanchez Omar','');
INSERT INTO cliente VALUES (175921860444202,'27-18559031-9','Luis Pasteur 1411',131126,'','Monotributo',70368744177664,'Strazzi Gabriela Beatriz','');
INSERT INTO cliente VALUES (175921860444203,'27-24352484-4','Tucumán 1650',131127,'','Monotributo',70368744177664,'Irusta Patricia Rosana','');
INSERT INTO cliente VALUES (175921860444204,'','Entre Rios',131128,'','Consumidor Final',70368744177664,'Moreno Claudia','');
INSERT INTO cliente VALUES (175921860444205,'20-12281513-4','Fray Benitto Tessitore 160',131129,'','Monotributo',70368744177664,'Macho Miguel Angel','');
INSERT INTO cliente VALUES (175921860444206,'','Pirovano',131130,'','Monotributo',70368744177664,'Valfre Luis','');
INSERT INTO cliente VALUES (175921860444207,'','Pirovano',131131,'','Consumidor Final',70368744177664,'Doponso Maria Luis','');
INSERT INTO cliente VALUES (175921860444208,'','Rafael Obligado 1165',131132,'','Consumidor Final',70368744177664,'Ruiz Marcela','');
INSERT INTO cliente VALUES (175921860444209,'','Rafael Obligado',131133,'','Consumidor Final',70368744177664,'Gomez','');
INSERT INTO cliente VALUES (175921860444210,'','Salta',131134,'','Consumidor Final',70368744177664,'Pizzería Belen','');
INSERT INTO cliente VALUES (175921860444211,'23-32015494-4','Yapeyu 120',131135,'','Monotributo',70368744177664,'Giraudo Natalia Cecilia','');
INSERT INTO cliente VALUES (175921860444212,'','Las Heras y Corrientes',131136,'','Consumidor Final',70368744177664,'Pizzería La Sabrosona','');
INSERT INTO cliente VALUES (175921860444213,'','Aristobulo Del Valle 441',131137,'','Consumidor Final',70368744177664,'Varela Carolina Mirta','');
INSERT INTO cliente VALUES (175921860444214,'27-10566212-8','H Irigoyen 235',131138,'','Monotributo',70368744177664,'Milanessio Mirta Rosa','');
INSERT INTO cliente VALUES (175921860444216,'','Ituzaingo y Paunero',131140,'','Consumidor Final',70368744177664,'Rotisería','');
INSERT INTO cliente VALUES (175921860444217,'','Santiago del Estero',131141,'','Consumidor Final',70368744177664,'Pizería Taty','');
INSERT INTO cliente VALUES (175921860444219,'20-06641523-7','Rio Colorado 511',131143,'','Monotributo',70368744177664,'Elaskar Walter Juan','');
INSERT INTO cliente VALUES (175921860444221,'27-22843735-8','Estrada Norte 357',131145,'','Monotributo',70368744177664,'Alfonso Ester Lidia','');
INSERT INTO cliente VALUES (175921860444222,'','Dinkeldein',131146,'','Monotributo',70368744177664,'Solardo Leonor','');
INSERT INTO cliente VALUES (175921860444223,'','',131147,'','Consumidor Final',70368744177664,'Moreira Ivana','');
INSERT INTO cliente VALUES (175921860444224,'27-12467858-2','Roberto Payro 913',131148,'','Monotributo',70368744177664,'Sosa Nilda Ramona','');
INSERT INTO cliente VALUES (175921860444225,'20-16486067-2','Roberto Pairo 1290',131149,'','Monotributo',70368744177664,'Pereyra Luis','');
INSERT INTO cliente VALUES (175921860444227,'','Calle Publica',131151,'','Consumidor Final',70368744177664,'Zabala','');
INSERT INTO cliente VALUES (175921860444228,'','Vuelta de Obligado',131152,'','Consumidor Final',70368744177664,'Ponce','');
INSERT INTO cliente VALUES (175921860444229,'27-21013846-9','Cardarelli 2302',131153,'','Monotributo',70368744177664,'Sosa Gladys Isabel','');
INSERT INTO cliente VALUES (175921860444230,'27-28716672-4','Gobernador Bustos Y Juarez Celman',131154,'','Monotributo',70368744177664,'Esmoris Valeria Noemi','');
INSERT INTO cliente VALUES (175921860444231,'27-26163823-7','M T de Alvear 1598',131155,'','Monotributo',70368744177664,'Girardi Romina Pamela','');
INSERT INTO cliente VALUES (175921860444233,'20-25136638-2','M T de Alvear 1994',131157,'','Monotributo',70368744177664,'Caceres Jorge Eduardo','');
INSERT INTO cliente VALUES (175921860476928,'20-06649564-8','Rosso Pierino 705',788885,'','Monotributo',70368744177664,'Aguirre Luis Alberto','');
INSERT INTO cliente VALUES (175921860476929,'27-05495828-0','Victor Hugo 1400',789505,'','Monotributo',70368744177664,'Aguayo Heddy Rosa','');
INSERT INTO cliente VALUES (175921860476930,'27-32906663-6','Paso De Los Andes 1224',789672,'','Monotributo',70368744177664,'Arias Carina','');
INSERT INTO cliente VALUES (175921860476931,'27-29402478-1','La Rioja 2269',789842,'','Monotributo',70368744177664,'Abregu Malvina Soledad','');
INSERT INTO cliente VALUES (175921860476932,'20-16121931-3','Hipolito Irugoyen 464',790037,'','Resp. Inscripto',70368744210432,'Aguilera Gerardo Eduardo','');
INSERT INTO cliente VALUES (175921860476933,'27-28579813-8','Geronimo Del Barco 2516',790215,'','Monotributo',70368744177664,'Angonova Sandra','');
INSERT INTO cliente VALUES (175921860476934,'20-08401140-2','Montevideo 1329',790392,'','Monotributo',70368744177664,'Norabueno Alfredo Armando','');
INSERT INTO cliente VALUES (175921860476935,'27-22190196-2','Ituzaingo 1162',791226,'','Monotributo',70368744177664,'Barrios Maria Ester','');
INSERT INTO cliente VALUES (175921860476936,'20-21407548-3','San Martin 40',791408,'','Monotributo',70368744177665,'Chiecher Roberto','');
INSERT INTO cliente VALUES (175921860476937,'20-31451257-0','Catamarca 634',791591,'','Monotributo',70368744177664,'Cipriani Mauricio German','');
INSERT INTO cliente VALUES (175921860476938,'30-70965800-6','Saavedra 27',793603,'','Resp. Inscripto',70368744177665,'Cartuja S.a.','');
INSERT INTO cliente VALUES (175921860476939,'20-06651486-3','Republica Arabe Siria 735',793790,'','Monotributo',70368744177664,'Cattaneo Hugo Ramon','');
INSERT INTO cliente VALUES (175921860476940,'30-71110095-0','Dante Aluguieri 581',796190,'','Resp. Inscripto',70368744177665,'Carnes El Pichon S.a.','');
INSERT INTO cliente VALUES (175921860476941,'20-06655201-3','Hernandes 547',797931,'','Monotributo',70368744177665,'Crespi Miguel Angel','');
INSERT INTO cliente VALUES (175921860476942,'20-06614559-0','Suipacha 136',798124,'','Monotributo',70368744177664,'Castro Jose','');
INSERT INTO cliente VALUES (175921860476943,'27-26175739-2','Pje. Antonio Saenz 3132',799200,'','Monotributo',70368744177664,'Colombo Natalia','');
INSERT INTO cliente VALUES (175921860476944,'20-21416977-1','Lopez De Vega 1471',800645,'','Monotributo',70368744177664,'Castagneris Jorge','');
INSERT INTO cliente VALUES (175921860476945,'23-20080150-4','Roma 1701',800844,'','Monotributo',70368744177664,'Coria Claudia','');
INSERT INTO cliente VALUES (175921860476946,'27-23913498-5','Pringles 547',801046,'','Monotributo',70368744177664,'Conde Veronica','');
INSERT INTO cliente VALUES (175921860476947,'27-23899138-8','Eduardo Jenner 1336',801251,'','Monotributo',70368744177664,'Chiaramello Liliana','');
INSERT INTO cliente VALUES (175921860476948,'23-13949772-4','Lopez De Vega 1470',804435,'','Monotributo',70368744177664,'Cabrera Elba','');
INSERT INTO cliente VALUES (175921860476949,'20-12630550-9','Martin Fierro 474',804644,'','Monotributo',70368744177664,'Constantino Ricardo','');
INSERT INTO cliente VALUES (175921860476950,'27-12762712-1','Ranqueles 930',807143,'','Monotributo',70368744177664,'Cuello Monica Noemi','');
INSERT INTO cliente VALUES (175921860476951,'27-21638738-6','Moreno 560',807354,'','Monotributo',70368744210432,'Cornejo Maria Rosana','');
INSERT INTO cliente VALUES (175921860476952,'30-71092941-2','Av. Italia 1321',807568,'','Resp. Inscripto',70368744177664,'Dos Banderas S.r.l.','');
INSERT INTO cliente VALUES (175921860476953,'20-08556193-7','Luis Sahores 232',807786,'','Resp. Inscripto',70368744210432,'Destassi Luis','');
INSERT INTO cliente VALUES (175921860476954,'20-26385487-0','Antonio Lucero 1330',808006,'','Monotributo',70368744177664,'Davis Matias','');
INSERT INTO cliente VALUES (175921860476955,'27-21507260-1','San Martin 176',812186,'','Resp. Inscripto',70368744177664,'El Toboso','');
INSERT INTO cliente VALUES (175921860476956,'30-70760498-7','Av. Italia Esq. Aramburu',813789,'','Resp. Inscripto',70368744177664,'El Lomo Y Cia. S.r.l.','');
INSERT INTO cliente VALUES (175921860476957,'23-24096928-9','Tristan Burgos 396',815625,'','Resp. Inscripto',70368744177664,'Farias Santiago','');
INSERT INTO cliente VALUES (175921860476958,'27-27630626-5','Santa Maria De Oro 878',816654,'','Monotributo',70368744177664,'Flesia Alejandra','');
INSERT INTO cliente VALUES (175921860476959,'20-29114162-6','9 De Julio',816877,'','Resp. Inscripto',70368744210432,'Fiori Franco','');
INSERT INTO cliente VALUES (175921860476960,'27-17733523-7','Moreno 2415',817103,'','Monotributo',70368744177664,'Funes Maria Eusebia','');
INSERT INTO cliente VALUES (175921860476961,'20-25349516-3','9 De Julio 681',822117,'','Resp. Inscripto',70368744177665,'Goicochea Juan Jose','');
INSERT INTO cliente VALUES (175921860476962,'27-11860505-0','Antonio Lucero 1381',828024,'','Monotributo',70368744177664,'Gauchat Maria Rosa','');
INSERT INTO cliente VALUES (175921860476963,'20-06562490-0','Chile 214',828255,'','Monotributo',70368744177664,'Gaspero Juan Carlos','');
INSERT INTO cliente VALUES (175921860476964,'27-21969008-3','Julio Roca Esq. Italia',830630,'','Monotributo',70368744210432,'Gil Claudia','');
INSERT INTO cliente VALUES (175921860476965,'17-21153703-0','A. Rodriguez 563',832377,'','Monotributo',70368744210432,'Gonzales Liliana','');
INSERT INTO cliente VALUES (175921860476966,'27-29176607-8','Perez Bulnes 2063',834801,'','Monotributo',70368744177664,'Gonzales Lorena','');
INSERT INTO cliente VALUES (175921860476967,'20-21013357-8','La Rioja 1743',835040,'','Monotributo',70368744177664,'Gonzales Walter','');
INSERT INTO cliente VALUES (175921860476968,'20-216945501-9','Laprida Y Pte. Peron',835281,'','Monotributo',70368744177664,'Gnc Altamirano','');
INSERT INTO cliente VALUES (175921860476969,'27-24442816-4','Colombia 531',835525,'','Monotributo',70368744177664,'Gatto Daniela Carina','');
INSERT INTO cliente VALUES (175921860476970,'27-25671709-9','Julio Roca 553',840064,'','Monotributo',70368744210432,'Insaurralde Elisa','');
INSERT INTO cliente VALUES (175921860476971,'27-10437037-9','Entre Rios 1100',840311,'','Monotributo',70368744177664,'Jaime Nelida','');
INSERT INTO cliente VALUES (175921860476972,'20-27695321-5','Morelli 180',842630,'','Monotributo',70368744210432,'Jaurejiberry Ignacio','');
INSERT INTO cliente VALUES (175921860476973,'27-22347367-4','Roque Saenz Peña 320',846338,'','Monotributo',70368744177665,'Lopez Monica Maria','');
INSERT INTO cliente VALUES (175921860476974,'27-10525683-9','Pje. La Forgee 3051',846588,'','Monotributo',70368744177664,'Lencina Ana','');
INSERT INTO cliente VALUES (175921860476975,'20-17562911-5','San Martin 1047',848927,'','Monotributo',70368744210432,'Lopez Ceferino','');
INSERT INTO cliente VALUES (175921860476976,'27-14876788-8','Gral. Bustos 2979',849181,'','Monotributo',70368744177664,'Lopez Monica','');
INSERT INTO cliente VALUES (175921860476977,'27-13955217-8','La Rioja 2050',850380,'','Monotributo',70368744177664,'Luna Silvia Beatriz','');
INSERT INTO cliente VALUES (175921860476978,'27-94009928-0','San Martin 420',852303,'','Resp. Inscripto',70368744210432,'Li Saihua','');
INSERT INTO cliente VALUES (175921860476979,'20-27695333-9','Hipolito Irigoyen 263',853042,'','Resp. Inscripto',70368744210432,'Lucero Carlos Adrian','');
INSERT INTO cliente VALUES (175921860476980,'27-24464954-4','Alfonso Coronel 1384',862029,'','Monotributo',70368744210432,'Mercado Marisa','');
INSERT INTO cliente VALUES (175921860476981,'27-27695400-3','A. Rodriguez',864000,'','Monotributo',70368744210432,'Mercado Gabriela','');
INSERT INTO cliente VALUES (175921860476983,'20-05528599-4','Belgrano 1142',865763,'','Resp. Inscripto',70368744210432,'Montiel Hector Ramón','');
INSERT INTO cliente VALUES (175921860476984,'27-21999024-9','Alberto Soria 350',867284,'','Monotributo',70368744177664,'Noriega Silvia','');
INSERT INTO cliente VALUES (175921860476985,'27-17576973-6','Julio Borda 715',872091,'','Monotributo',70368744177664,'Palomino Bibiana','');
INSERT INTO cliente VALUES (175921860476986,'27-14365017-6','Yapeyu 1412',872364,'','Monotributo',70368744177664,'Paez Noelia','');
INSERT INTO cliente VALUES (175921860476987,'30-71115811-8','Sto. Cabral 1091',875712,'','Monotributo',70368744177664,'Pizza Sport S.a.','');
INSERT INTO cliente VALUES (175921860476988,'27-18511786-9','Marcelo T. Alvear 1936',875989,'','Monotributo',70368744177664,'Pendito Estella Maris','');
INSERT INTO cliente VALUES (175921860476989,'27-16991606-9','Publicitarios Argentinos 551',884595,'','Monotributo',70368744177664,'Rama Maria Alejandra','');
INSERT INTO cliente VALUES (175921860476990,'20-27296505-7','Av. Ide Y Jorge Newbery',886710,'','Monotributo',70368744210432,'Riera Lucas','');
INSERT INTO cliente VALUES (175921860476991,'20-10724240-7','Señorita Cejas Y Sta. Maria De Oro',890425,'','Monotributo',70368744177664,'Rojo Victor','');
INSERT INTO cliente VALUES (175921860476992,'20-33269135-0','Yamanas 2310',890712,'','Monotributo',70368744177664,'Rojo Ariel','');
INSERT INTO cliente VALUES (175921860476993,'27-06173637-4','Rio Grande 607',891000,'','Monotributo',70368744177664,'Rincon Miriam','');
INSERT INTO cliente VALUES (175921860476994,'20-24464985-9','Pascual Biseglia 790',895071,'','Monotributo',70368744210432,'Riera Mauro Alberto','');
INSERT INTO cliente VALUES (175921860476995,'27-30658503-2','Geronimo Del Barco 2358',904345,'','Monotributo',70368744177664,'Serradell Ivana','');
INSERT INTO cliente VALUES (175921860476996,'20-26417371-0','Dr. Carlos Rodriguez 2036',906557,'','Monotributo',70368744177664,'Sosa Carlos','');
INSERT INTO cliente VALUES (175921860476997,'30-71135791-9','San Martin Esq. Vicente Baggione',906853,'','Resp. Inscripto',70368744177665,'Super El Flaco','');
INSERT INTO cliente VALUES (175921860476998,'27-12630563-5','Salta 56',914624,'','Monotributo',70368744177664,'Salvi Marta Beatriz','');
INSERT INTO cliente VALUES (175921860476999,'27-24557844-5','Amadeo Sabattini 894',916870,'','Monotributo',70368744210432,'Torres Deolinda Maria','');
INSERT INTO cliente VALUES (175921860477000,'20-20786888-5','Juana M. Gorritti 996',918291,'','Monotributo',70368744177664,'Virga Marcelo Oscar','');
INSERT INTO cliente VALUES (175921860477001,'20-16020533-5','Colon 1500',931297,'','Monotributo',70368744210432,'Viotti Dionicio','');
INSERT INTO cliente VALUES (175921860477002,'20-94745143-0','9 De Julio 526',932452,'','Resp. Inscripto',70368744177665,'Yao Zongrui','');
INSERT INTO cliente VALUES (175921860477004,'','Santa Maria De Oro 450',936938,'','Monotributo',70368744177664,'Paez Mario','');
INSERT INTO cliente VALUES (175921860477005,'','Santa Maria De Oro Esq. Señorita Cejas',938551,'','Consumidor Final',70368744177664,'Tisera Jorge','');
INSERT INTO cliente VALUES (175921860477006,'','Belisario Roldan 1305',942366,'','Consumidor Final',70368744177664,'Burgos Jesica','');
INSERT INTO cliente VALUES (175921860477007,'','Montevideo 1300',943268,'','Consumidor Final',70368744177664,'Boriglio Patricia','');
INSERT INTO cliente VALUES (175921860477008,'','Santa Maria De Oro 1000',943583,'','Consumidor Final',70368744177664,'Puig','');
INSERT INTO cliente VALUES (175921860477009,'','Santa Maria De Oro 830',943901,'','Consumidor Final',70368744177664,'Guall Alejandro','');
INSERT INTO cliente VALUES (175921860477010,'','Adelia Maria 730',944220,'','Consumidor Final',70368744177664,'Lujan Miriam','');
INSERT INTO cliente VALUES (175921860477011,'','Catamarca 887',944541,'','Consumidor Final',70368744177664,'Tejeda Edit','');
INSERT INTO cliente VALUES (175921860477012,'','Salta 910',944864,'','Consumidor Final',70368744177664,'Aguilera','');
INSERT INTO cliente VALUES (175921860477013,'','Intendente Daguerre 911',945190,'','Consumidor Final',70368744177664,'Curdi','');
INSERT INTO cliente VALUES (175921860509696,'','Ranqueles 930',1248467,'','Consumidor Final',70368744177664,'Dulcich','0358-154181736');
INSERT INTO cliente VALUES (175921860509697,'','Av. Argentina 600',1250043,'','Consumidor Final',70368744177664,'Irusta Marcela','');
INSERT INTO cliente VALUES (175921860509699,'','Francisco Zarco 1005',1267342,'','Consumidor Final',70368744177664,'Cuello Antonio','');
INSERT INTO cliente VALUES (175921860509700,'','Quenón 1843',1272739,'','Consumidor Final',70368744177664,'Constantino Ruben','');
INSERT INTO cliente VALUES (175921860509701,'','Marcos Lloveras Esq. Perez Bulnes',1275627,'','Consumidor Final',70368744177664,'Torres','');
INSERT INTO cliente VALUES (175921860509702,'','Blas Parera 1464',1282692,'','Monotributo',70368744177664,'Busso Silvia','');
INSERT INTO cliente VALUES (175921860509703,'','Comisario Consigli 544',1298022,'','Consumidor Final',70368744177664,'Sosa Angela','');
INSERT INTO cliente VALUES (175921860509704,'','Dr. Carlos Rodriguez 2030',1300315,'','Consumidor Final',70368744177664,'Perez Fanny','');
INSERT INTO cliente VALUES (175921860509705,'23-35276107-9','Alejandro Aguado 1076',1305556,'','Monotributo',70368744177664,'Godoy Jonattan David','');
INSERT INTO cliente VALUES (175921860509706,'','Guardias Nacionales Esq. Com. Consigli',1308533,'','Consumidor Final',70368744177664,'Santamarino','');
INSERT INTO cliente VALUES (175921860509707,'23-94778633-4','Leyes Obreras 485',1320115,'','Monotributo',70368744177664,'Funes Maritza','');
INSERT INTO cliente VALUES (175921860509709,'','Ficco 439',1323327,'','Consumidor Final',70368744177664,'Barbero Rosana','');
INSERT INTO cliente VALUES (175921860509710,'','Quenna 954',1325711,'','Consumidor Final',70368744177664,'Lucero Gladys','');
INSERT INTO cliente VALUES (175921860509711,'','Rafael Obligado 1527',1344503,'','Consumidor Final',70368744177664,'Diaz Sandra','');
INSERT INTO cliente VALUES (175921860509712,'','Anchorena 14',1349119,'','Consumidor Final',70368744177664,'Moyano Jorge','');
INSERT INTO cliente VALUES (175921860509714,'27-17866891-4','Pje. Ms. De Loria',1355695,'','Monotributo',70368744177664,'Moreno Delia Isabel','');
INSERT INTO cliente VALUES (175921860509715,'','Chile 1100',1359527,'','Consumidor Final',70368744177664,'Diaz Silvana','');
INSERT INTO cliente VALUES (175921860509716,'','Berta Perazzi 1221',1361991,'','Consumidor Final',70368744177664,'Rocco Andrea','');
INSERT INTO cliente VALUES (175921860509717,'','Malabia 620',1387742,'','Consumidor Final',70368744177664,'Giordana Ana','');
INSERT INTO cliente VALUES (175921860509718,'27-12767694-7','Rafael Obligado 1200',1389528,'','Monotributo',70368744177664,'Castro Dolores','');
INSERT INTO cliente VALUES (175921860509719,'','Tucuman 1000',1407268,'','Consumidor Final',70368744177664,'Tobares Olga','');
INSERT INTO cliente VALUES (175921860509721,'','Chacabuco 300',1411961,'','Consumidor Final',70368744177664,'Travaglia Norma','');
INSERT INTO cliente VALUES (175921860509722,'','Baigorria 1069',1417208,'','Consumidor Final',70368744177664,'Marquez Liso','');
INSERT INTO cliente VALUES (175921860509723,'','Colombia 478',1424480,'','Consumidor Final',70368744177664,'Vieyra','');
INSERT INTO cliente VALUES (175921860509724,'','Pje. Chasque 200',1427054,'','Consumidor Final',70368744177664,'Benitez Pedro','');
INSERT INTO cliente VALUES (175921860509725,'','Cuba 1041',1447582,'','Consumidor Final',70368744177664,'Lopez Carolina','');
INSERT INTO cliente VALUES (175921860509726,'20-268661589-0','Pje. Chasque 1756',1450919,'','Monotributo',70368744177664,'Cobos Leandro Jonas','');
INSERT INTO cliente VALUES (175921860509727,'27-32392108-9','Costa Rica 550',1452426,'','Monotributo',70368744177664,'Zarate Daniela','');
INSERT INTO cliente VALUES (175921860509728,'27-16856216-6','Calle Publica',1466593,'','Monotributo',70368744177664,'Romero Susana','');
INSERT INTO cliente VALUES (175921860509729,'','I.p.v. Roberto Payro 1240',1476763,'','Consumidor Final',70368744177664,'Bellandi Miguel','');
INSERT INTO cliente VALUES (175921860509730,'','Antartida Argentina 890',1484321,'','Consumidor Final',70368744177664,'Peñaloza Veatriz','');
INSERT INTO cliente VALUES (175921860509731,'','Isabel La Catolica 880',1487749,'','Consumidor Final',70368744177664,'Lopez Analia','');
INSERT INTO cliente VALUES (175921860509732,'','Isabel La Catolica 1018',1489297,'','Consumidor Final',70368744177664,'Omisolo Veronica','');
INSERT INTO cliente VALUES (175921860509733,'23-21013275-9','Rio Segundo 845',1493144,'','Monotributo',70368744177664,'Navarro Claudio Gustavo','');
INSERT INTO cliente VALUES (175921860509734,'','Guardias Nacionales',1497779,'','Consumidor Final',70368744177664,'Arias Marcelo','');
INSERT INTO cliente VALUES (175921860509735,'','Rio Segundo 1937',1510935,'','Consumidor Final',70368744177664,'Suarez','');
INSERT INTO cliente VALUES (175921860509736,'','Tristan Burgos 450',1518728,'','Consumidor Final',70368744177664,'Rodriguez Maria','');
INSERT INTO cliente VALUES (175921860509737,'27-18061261-6','Juarez Celman 725',1532601,'','Monotributo',70368744177664,'Repezza Elena','');
INSERT INTO cliente VALUES (175921860509738,'20-17370744-5','Gregorio Ramirez 1075',1548313,'','Monotributo',70368744210432,'Escudero Juan Carlos','');
INSERT INTO cliente VALUES (175921860509739,'27-17370709-1','San Martin 1455',1550309,'','Monotributo',70368744210432,'Etchetto Mabel','');
INSERT INTO cliente VALUES (175921860542464,'','Av. 25 De Mayo',2122538,'','Consumidor Final',70368744177665,'Pizzarro','');
INSERT INTO cliente VALUES (175921860542465,'','Av. 25 De Mayo',2122962,'','Consumidor Final',70368744177665,'Ceballos Walter','');
INSERT INTO cliente VALUES (175921860542466,'','S/n',2241726,'','Consumidor Final',70368744177665,'Cardetti','');
INSERT INTO cliente VALUES (175921860542467,'30-71191653-5','Pje. Tomas Garzón 780',2265370,'','Resp. Inscripto',70368744210432,'Furmar S.r.l.','');
INSERT INTO cliente VALUES (175921860542468,'','Pje. Chamorro',2268997,'','Consumidor Final',70368744177665,'Pergolini','');
INSERT INTO cliente VALUES (175921860542469,'','S/n',2307669,'','Consumidor Final',70368744210432,'Sosa Ricardo','');
INSERT INTO cliente VALUES (175921860542470,'','S/n',2310234,'','Consumidor Final',70368744210432,'Bazan Leonel','');
INSERT INTO cliente VALUES (175921860542471,'','S/n',2313207,'','Consumidor Final',70368744210432,'Gimenez Elba','');
INSERT INTO cliente VALUES (175921860542472,'','S/n',2330572,'','Consumidor Final',70368744210432,'Debia Luisa','');
INSERT INTO cliente VALUES (175921860542473,'','S/n',2336508,'','Consumidor Final',70368744210432,'Panaderia Ortiz','');
INSERT INTO cliente VALUES (175921860542474,'','S/n',2340348,'','Consumidor Final',70368744210432,'Garoni Diego','');
INSERT INTO cliente VALUES (175921860542475,'','S/n',2342149,'','Consumidor Final',70368744210432,'Kiosco Favio','');
INSERT INTO cliente VALUES (175921860542476,'20-22077502-0','Roque Saenz Peña 342',2344331,'','Monotributo',70368744177665,'Zufiaurre Mariano German','');
INSERT INTO cliente VALUES (175921860575232,'','S/n',2363847,'','Consumidor Final',70368744210432,'Juarez Alberto','');
INSERT INTO cliente VALUES (175921860608000,'','Humahuaca 860',2566761,'','Consumidor Final',70368744177664,'Lucero Paola','');
INSERT INTO cliente VALUES (175921860640768,'','S/ N',3020493,'','Consumidor Final',70368744177665,'Arocena Maria','');
INSERT INTO cliente VALUES (175921860640769,'','S/n',3040237,'','Consumidor Final',70368744177665,'Condorito','');
INSERT INTO cliente VALUES (175921860640770,'','S/n',3075944,'','Consumidor Final',70368744177665,'Nolazco','');
INSERT INTO cliente VALUES (175921860640771,'','S/ N',3087234,'','Exento',70368744177665,'Hogar De Ancianos','');
INSERT INTO cliente VALUES (175921860640772,'','S/ N',3125747,'','Consumidor Final',70368744210432,'Manfredy Carlos','');
INSERT INTO cliente VALUES (175921860640773,'','S/n',3131950,'','Consumidor Final',70368744210432,'Ceballos Sergio','');
INSERT INTO cliente VALUES (175921860640774,'','S/n',3155173,'','Consumidor Final',70368744210432,'Alturria','');
INSERT INTO cliente VALUES (175921860640775,'','S/n',3161758,'','Consumidor Final',70368744210432,'Bassan Leonor','');
INSERT INTO cliente VALUES (175921860640776,'','S/n',3180163,'','Consumidor Final',70368744210432,'Delfino Norma','');
INSERT INTO cliente VALUES (175921860640777,'','S/ N',3184385,'','Consumidor Final',70368744210432,'Etchenique Luis','');
INSERT INTO cliente VALUES (175921860640778,'','S/ N',3220141,'','Consumidor Final',70368744210432,'Alfonso Carmen','');
INSERT INTO cliente VALUES (175921860640779,'','S/n',3230766,'','Consumidor Final',70368744210432,'Avila Adriana','');
INSERT INTO cliente VALUES (175921860640780,'','S/n',3238011,'','Consumidor Final',70368744210432,'Paredes Osvaldo','');
INSERT INTO cliente VALUES (175921860640781,'','S/n',3250199,'','Consumidor Final',70368744177665,'Budini Mariela','');
INSERT INTO cliente VALUES (175921860673536,'','Hipolito Hirigoyen 1380',3475119,'','Consumidor Final',70368744308736,'Gutierrez Jimena','');
INSERT INTO cliente VALUES (175921860673537,'20-21998426-0','Quirico Porreca 785',3475607,'','Monotributo',70368744177664,'Depetriz Celso','');
INSERT INTO cliente VALUES (175921860673538,'','Lavalle 361',3476096,'','Consumidor Final',70368744308736,'Sosa Graciela','');
INSERT INTO cliente VALUES (175921860673539,'','Pje. Amadeo Escudero Casa 55',3476818,'','Consumidor Final',70368744308736,'Ferraro Olga','');
INSERT INTO cliente VALUES (175921860673540,'','Isabel La Catolica 963',3477533,'','Consumidor Final',70368744177664,'Lazaro Daniela','');
INSERT INTO cliente VALUES (175921860673541,'','Colombia 827',3478251,'','Consumidor Final',70368744177664,'Niz Erica','');
INSERT INTO cliente VALUES (175921860673542,'','Mozart Y Soco Soco',3478743,'','Consumidor Final',70368744177664,'Calderon','');
INSERT INTO cliente VALUES (175921860673543,'','Republica Iran 10',3479236,'','Consumidor Final',70368744308736,'Sainz Natalia','');
INSERT INTO cliente VALUES (175921860673544,'','Eduardo Jenner 1328',3479732,'','Consumidor Final',70368744177664,'Garcia','');
INSERT INTO cliente VALUES (175921860673545,'','Cardarelli 2255',3480231,'','Consumidor Final',70368744177664,'Cuello Samuel','');
INSERT INTO cliente VALUES (175921860673546,'','Laguna Blanca 267',3480733,'','Consumidor Final',70368744177664,'Guayanes Daniela','');
INSERT INTO cliente VALUES (175921860673547,'20-38018043-0','Garibaldi Y R. Pairo',3481243,'','Monotributo',70368744177664,'Delgado Facundo','');
INSERT INTO cliente VALUES (175921860673548,'','Brochero 673',3481752,'','Consumidor Final',70368744177664,'Allende Karina','');
INSERT INTO cliente VALUES (175921860673549,'','León Pallere 555',3649936,'','Consumidor Final',70368744177664,'Amiri Carina','');
INSERT INTO cliente VALUES (175921860673550,'','Chacabuco 220',3681624,'','Consumidor Final',70368744177664,'Ronco Noelia','');
INSERT INTO cliente VALUES (175921860673551,'','---',3772522,'','Consumidor Final',70368744177664,'Llanos','');
INSERT INTO cliente VALUES (175921860706304,'','Muñiz 1507',4300159,'','Consumidor Final',70368744177664,'Cabral Walter','');
INSERT INTO cliente VALUES (175921860706305,'','Sabattini 3900',4305708,'','Consumidor Final',70368744177664,'Torres Humberto','');
INSERT INTO cliente VALUES (175921860706306,'','Sarmiento 3265',4309831,'','Consumidor Final',70368744177664,'Moreno Marcela','');
INSERT INTO cliente VALUES (175921860706307,'','Laprida 1103',4316169,'','Consumidor Final',70368744177664,'Barbero Miguel','');
INSERT INTO cliente VALUES (175921860706308,'','Isabel De Guevara 449',4320260,'','Consumidor Final',70368744177664,'Sta. Rita','');
INSERT INTO cliente VALUES (175921860706309,'','Salta 56',4324143,'','Consumidor Final',70368744177664,'Rufino','');
INSERT INTO cliente VALUES (175921860706310,'','Laprida 906',4328503,'','Consumidor Final',70368744177664,'Rafael','');
INSERT INTO cliente VALUES (175921860706311,'','Isla De Los Estados 233',4332910,'','Consumidor Final',70368744177664,'Barrera Maria','');
INSERT INTO cliente VALUES (175921860706312,'','C. Balmaceda Y Las Postas',4337835,'','Consumidor Final',70368744177664,'Martinez Carina','');
INSERT INTO cliente VALUES (175921860706313,'27-11689784-4','Leopoldo Lugones 2721',4342496,'','Monotributo',70368744177664,'Guardia Graciela','');
INSERT INTO cliente VALUES (175921860706314,'','Moreno Y Juan De Garay',4350929,'','Consumidor Final',70368744177664,'Gatica Maria Elsa','');
INSERT INTO cliente VALUES (175921860706315,'','J. De Dios Lopez 140',4358978,'','Consumidor Final',70368744177664,'Calderon Micaela','');
INSERT INTO cliente VALUES (175921860706316,'','Fernando Fade (c) 116',4370163,'','Consumidor Final',70368744177664,'Rovetta Romina','');
INSERT INTO cliente VALUES (175921860706317,'','Union De Los Argentinos Y San Lorenzo',4375157,'','Consumidor Final',70368744177664,'Andrada Rosana','');
INSERT INTO cliente VALUES (175921860706318,'','Fray Ludovico Cuaranta 373',4375965,'','Consumidor Final',70368744177664,'Gladys','');
INSERT INTO cliente VALUES (175921860706319,'','Union De Los Argentinos 21',4383639,'','Consumidor Final',70368744177664,'Gonzales','');
INSERT INTO cliente VALUES (175921860706320,'27-11311039-0','Gobernador Guzman 1673',4386382,'','Monotributo',70368744177664,'Vilma Miranda','');
INSERT INTO cliente VALUES (175921860706321,'','Union De Los Argentinos 31',4401292,'','Consumidor Final',70368744177664,'Palacios Graciela','');
INSERT INTO cliente VALUES (175921860739072,'27-14132497-2','Paso De Los Andes 689',4593391,'','Monotributo',70368744177664,'Arias Marta','');
INSERT INTO cliente VALUES (175921860739073,'20-17314115-8','Buenos Aires 645',4606298,'','Resp. Inscripto',70368744177664,'Arias Walter Luis','');
INSERT INTO cliente VALUES (175921860739074,'27-30766630-3','Vuelta De Obligado 900',4812391,'','Monotributo',70368744177664,'Romero Anabela Soledad','');
INSERT INTO cliente VALUES (175921860739075,'27-22580207-1','Republica Arabe De Siria 2224',4859505,'','Monotributo',70368744177664,'Martinez Sandra Fabiana','');
INSERT INTO cliente VALUES (175921860739076,'20-22104124-1','Thomson Y Cuba',4864115,'','Monotributo',70368744177664,'Giacinto Gustavo','');
INSERT INTO cliente VALUES (175921860739077,'20-06563040-1','Francisco Muñiz 804',4867717,'','Monotributo',70368744177664,'Cornejo Ramón','');
INSERT INTO cliente VALUES (175921860739078,'20-13221899-5','Francisco Muñiz 1732',4871640,'','Monotributo',70368744177664,'Laurenco Daniel','');
INSERT INTO cliente VALUES (175921860739079,'','Achalay 1847',4875755,'','Consumidor Final',70368744177664,'Portales Liliana','');
INSERT INTO cliente VALUES (175921860739080,'27-12326075-4','Francisco Muñiz 1364',4880501,'','Monotributo',70368744177664,'Gimenez Olga','');
INSERT INTO cliente VALUES (175921860739081,'20-13955277-7','Francisco Muñiz 1211',4884913,'','Monotributo',70368744177664,'Sarmiento Paolo','');
INSERT INTO cliente VALUES (175921860771840,'','Rotonda Seminario',4917811,'','Consumidor Final',70368744177664,'Jorge Alba','');
INSERT INTO cliente VALUES (175921860771841,'20-16164145-7','Suipacha Y Estrada',4922799,'','Monotributo',70368744177664,'Ferrero Sergio','154232511');
INSERT INTO cliente VALUES (175921860771842,'','Goudar Y Lugones',4929170,'','Consumidor Final',70368744177664,'Roco Patricia','');
INSERT INTO cliente VALUES (175921860771843,'','Juana Azurduy Y Padilla',4935878,'','Consumidor Final',70368744177664,'Hernandez','');
INSERT INTO cliente VALUES (175921860771844,'','Trejo Y Sanabria 955',4940369,'','Consumidor Final',70368744177664,'Luna Elsa','');
INSERT INTO cliente VALUES (175921860771845,'','Perón Y Roque Saenz Peña',4947107,'','Consumidor Final',70368744177664,'Milanecio Jorge','');
INSERT INTO cliente VALUES (175921860771846,'','12 De Agosto 976',4952513,'','Consumidor Final',70368744177664,'Garay Sandra','');
INSERT INTO cliente VALUES (175921860771847,'','Pje. Publico 3575',4964717,'','Consumidor Final',70368744177664,'Sosa Yohama','');
INSERT INTO cliente VALUES (175921860771848,'','Guodar 3387',4976468,'','Consumidor Final',70368744177664,'Gimenez Damian','');
INSERT INTO cliente VALUES (175921860771849,'','Remedios De Escalada Y Dilkendein',4985053,'','Consumidor Final',70368744177664,'Diaz Silvana (2)','');
INSERT INTO cliente VALUES (175921860804608,'20-26546551-0','Pizzarro 625',5579651,'','Monotributo',70368744177664,'Luque Alejandro','03583-482012');
INSERT INTO cliente VALUES (175921860804609,'','Tupungato 346',5596446,'','Consumidor Final',70368744177664,'Pinto Vilma','');
INSERT INTO cliente VALUES (175921860804610,'','Diego Velazquez 7',5602128,'','Consumidor Final',70368744177664,'Dardanelli Valeria','');
INSERT INTO cliente VALUES (175921860804611,'20-338859983-0','Cungreso Tucuman 349',5622496,'','Monotributo',70368744308736,'Mercadito Esteban','');
INSERT INTO cliente VALUES (175921860804612,'20-30030003-1','Las Heras 132',5632515,'','Monotributo',70368744308736,'Gomez Victor Angel','0358-4167784');
INSERT INTO cliente VALUES (175921860804613,'','Piñero 1148',5663588,'','Consumidor Final',70368744177664,'Flores Estella Maris','');
INSERT INTO cliente VALUES (175921860804614,'','Martin Fierro 250',5669399,'','Consumidor Final',70368744308736,'Camallo Fernando','');
INSERT INTO cliente VALUES (175921860804615,'','Ruben Dario 207',5701448,'','Consumidor Final',70368744177664,'Quispe Erica','');
INSERT INTO cliente VALUES (175921860804616,'','Jose Deramo 416',5720099,'','Consumidor Final',70368744177664,'Romero Leo','');
INSERT INTO cliente VALUES (175921860804617,'','Ypv- Bda Norte. Mb: H Dto:2 Pb.',5745928,'','Consumidor Final',70368744177664,'Cufre Rodolfo','');
INSERT INTO cliente VALUES (175921860804618,'20-08625256-3','Pte. Peron 412',5854628,'','Monotributo',70368744177664,'Gatica Roque Antonio','');
INSERT INTO cliente VALUES (175921860804619,'','720',6059912,'','Consumidor Final',70368744177664,'Avila Roberto','');
INSERT INTO cliente VALUES (175921860837376,'27-20808620-6','Santa Maria De Oro 142',6220816,'','Monotributo',70368744177665,'Lerda Sonia','');
INSERT INTO cliente VALUES (175921860837377,'20-94206784-5','San Martin 675',6263220,'','Resp. Inscripto',70368744177664,'Cheng Peng','');
INSERT INTO cliente VALUES (175921860837378,'20-94032697-7','O\'higgins 2453',6263833,'','Resp. Inscripto',70368744177664,'Cheng Ciyeng','');
INSERT INTO cliente VALUES (175921860837379,'23-25471260-4','Buenos Aires Y Cabrera',6264457,'','Resp. Inscripto',70368744177664,'Tabares Patricia','');
INSERT INTO cliente VALUES (175921860837380,'20-21864173-4','Goudard Y Biassi',6273742,'','Monotributo',70368744177664,'Allendez Eduardo','');
INSERT INTO cliente VALUES (175921860870144,'','Maipu Y Molina',6293603,'','Consumidor Final',70368744177664,'Gianini Jose','');
INSERT INTO cliente VALUES (175921860870145,'','Ruta Nac. 8',6297883,'','Consumidor Final',70368744177664,'Rondan Maria Francisca','');
INSERT INTO cliente VALUES (175921860870146,'27-14370843-3','Colon 1276',6419031,'','Resp. Inscripto',70368744210432,'Drago Elsa','');
INSERT INTO cliente VALUES (175921860870147,'27-23939361-1','Augusto Cacciavillianni S/n',6502273,'','Monotributo',70368744210432,'Gallardo Nelida Beatriz','');
INSERT INTO cliente VALUES (175921860902912,'','S/n',6574417,'','Consumidor Final',70368744210432,'Tapia Silvina','');
INSERT INTO cliente VALUES (175921860935680,'','Andres Terzaga 945',6712545,'','Consumidor Final',70368744177664,'Paez Sergio','');
INSERT INTO cliente VALUES (175921860935681,'','Pje. Aconquija 3475',6729488,'','Consumidor Final',70368744177664,'Noelia','');
INSERT INTO cliente VALUES (175921860935682,'','Santa Rosa 364',6753672,'','Consumidor Final',70368744177664,'Correa Carolina','');
INSERT INTO cliente VALUES (175921860935683,'','Liniers 1969',6758011,'','Consumidor Final',70368744177664,'Jorge Fabro','154179460');
INSERT INTO cliente VALUES (175921860935684,'','Cuaranta 373',6788834,'','Consumidor Final',70368744177664,'Canciano','154280332');
INSERT INTO cliente VALUES (175921860935685,'','Almirante Brown 1463',6808560,'','Consumidor Final',70368744177664,'Gonzales Cesar','');
INSERT INTO cliente VALUES (175921860935686,'','Sta. Maria De Oro 59',6820350,'','Consumidor Final',70368744177664,'Juan Carlos','');
INSERT INTO cliente VALUES (175921860935687,'','Perito Moreno 2720',6836492,'','Consumidor Final',70368744177664,'Veronica Ochoa','154360212');
INSERT INTO cliente VALUES (175921860935688,'','Roma 2360',6853659,'','Consumidor Final',70368744177664,'Varela Paulina','154174508');
INSERT INTO cliente VALUES (175921860935689,'27-31705104-8','Mosconi Esq. Jose Hernandez',6874576,'','Monotributo',70368744177664,'Vila Veronica','');
INSERT INTO cliente VALUES (175921860968448,'','Leopoldo Lugones 1585',7461449,'','Consumidor Final',70368744177664,'Piola Gloria','');
INSERT INTO cliente VALUES (175921860968449,'','Moreno 1594',7480045,'','Consumidor Final',70368744177664,'Quiroga Juana','');
INSERT INTO cliente VALUES (175921860968450,'','Pje Chasque 1324',7619729,'','Consumidor Final',70368744177664,'Amaya Dayana','');
INSERT INTO cliente VALUES (175921860968451,'','Av. Marcelo T. De Alvear 781',7632491,'','Consumidor Final',70368744177664,'Campo Celeste','');
INSERT INTO cliente VALUES (175921860968452,'','Antartida Argentina 1095',7648608,'','Consumidor Final',70368744177664,'Benitez Melina','');
INSERT INTO cliente VALUES (175921860968453,'27-21407004-4','Av. Marcelo T. De Alvear 1789',7670865,'','Monotributo',70368744177664,'Bringas Maria','');
INSERT INTO cliente VALUES (175921861001216,'','27 De Noviembre Y Guemez',8127170,'','Consumidor Final',70368744308736,'Arias Nelida','');
INSERT INTO cliente VALUES (175921861033984,'20-16655699-7','Intendente Juan Sosa 136',8577297,'','Monotributo',70368744177665,'Arocena Rafael David','');
INSERT INTO cliente VALUES (175921861033985,'','Alberdi',8647293,'','Consumidor Final',70368744177665,'Piovera- El Paso','');
INSERT INTO cliente VALUES (175921861066752,'20-06655358-3','Av. Carlos Yde 1850',8913106,'','Monotributo',70368744210432,'Campos Cosme Miguel','');
INSERT INTO cliente VALUES (175921861066753,'','---',8936167,'','Consumidor Final',70368744210432,'Abuelo Jose','');
INSERT INTO cliente VALUES (175921861066754,'','--',8980267,'','Consumidor Final',70368744210432,'Lucero Liliana','');
INSERT INTO cliente VALUES (175921861099520,'','Montevideo 1776',9192266,'','Consumidor Final',70368744177664,'Arevalo Juan','');
INSERT INTO cliente VALUES (175921861099521,'20-12863138-1','Elias Moyano 1813',9204653,'','Monotributo',70368744177664,'Farias Raul','');
INSERT INTO cliente VALUES (175921861099522,'','Maipu Esq. Molina',9222294,'','Consumidor Final',70368744177664,'Jose','');
INSERT INTO cliente VALUES (175921861099523,'20-17356075-4','Coronel Olmedo 2915',9257654,'','Monotributo',70368744177664,'Fernandez Daniela','');
INSERT INTO cliente VALUES (175921861099524,'','Pje. Drago 3125',9263594,'','Consumidor Final',70368744177664,'Rapachi Sosa','');
INSERT INTO cliente VALUES (175921861099525,'','Jose Biassi 1026',9272222,'','Consumidor Final',70368744177664,'Luna Estefania','154162747');
INSERT INTO cliente VALUES (175921861099526,'','Union De Lo Argentinos 21',9294053,'','Consumidor Final',70368744177664,'Patricia Rosana','');
INSERT INTO cliente VALUES (175921861099527,'','Moreno Esq. Juan De Garay',9319643,'','Consumidor Final',70368744177664,'Gatica','');
INSERT INTO cliente VALUES (175921861132288,'','Tucuman 650',9948075,'','Consumidor Final',70368744177664,'Sanches Omar 2','');
INSERT INTO cliente VALUES (175921861165056,'27-30029686-1','Marcelo T. De Alvear 1799',10513158,'','Monotributo',70368744177664,'Perez Jimena','');
INSERT INTO cliente VALUES (175921861165057,'','Piñero Werfiel 1148',10526557,'','Consumidor Final',70368744177664,'Torres Victor','');
INSERT INTO cliente VALUES (175921861165058,'','Pje. Juarez Avila 889',10537983,'','Consumidor Final',70368744177664,'Romero Ariel','');
INSERT INTO cliente VALUES (175921861165059,'20-13738621-7','Muñiz 815',10544067,'','Monotributo',70368744177664,'Basilico Daniel Omar','');
INSERT INTO cliente VALUES (175921861165060,'27-27018180-0','Av. Argentina 49',10544768,'','Monotributo',70368744177664,'Adorno Alicia','');
INSERT INTO cliente VALUES (175921861197824,'20-16991735-4','Pje. Decouvette 1452',13856858,'','Monotributo',70368744177664,'Garcia Raul','');
INSERT INTO cliente VALUES (175921861197825,'','Adelia Maria 132',13912946,'','Consumidor Final',70368744177664,'Cisneros Dario','');
INSERT INTO cliente VALUES (175921861197826,'','Velez Sarfield Y San Martin',14010185,'','Consumidor Final',70368744308736,'Canigianni Vanina','');
INSERT INTO cliente VALUES (175921861197827,'','---',14355785,'','Consumidor Final',70368744210432,'Balmaceda Hector','');
INSERT INTO cliente VALUES (175921861230592,'20-10821774-6','Potrerillo 612',16756044,'','Monotributo',70368744177664,'Giacomuzzi Pedro','');
INSERT INTO cliente VALUES (175921861230593,'','Roma 1334',16775916,'','Consumidor Final',70368744177664,'Lorena Farino','');
INSERT INTO cliente VALUES (175921861263360,'','Remedios De Escalada 1130',17060343,'','Consumidor Final',70368744177664,'Moreno Gabriela','');
INSERT INTO cliente VALUES (175921861263361,'','Leopoldo Lugones 2647',17070699,'','Consumidor Final',70368744177664,'Palliero Elba','');
INSERT INTO cliente VALUES (175921861263362,'20-16650828-3','Roberto Pairo 1089',17303062,'','Monotributo',70368744177664,'Bufarini Eduardo','');
INSERT INTO cliente VALUES (175921861263363,'','Colombia 1196',17424516,'','Consumidor Final',70368744177664,'Noriega Maria Ines','');
INSERT INTO cliente VALUES (175921861263364,'','Padilla',17454414,'','Consumidor Final',70368744177664,'Fernandez','');
INSERT INTO cliente VALUES (175921861263365,'','Brochero 776',17517323,'','Consumidor Final',70368744177664,'Pereyra Ana Maria','');
INSERT INTO cliente VALUES (175921861263366,'','Pampa 2300',17672224,'','Consumidor Final',70368744177664,'Luna','');
INSERT INTO cliente VALUES (175921861296128,'','Los Ceibos S/n',22406729,'','Consumidor Final',70368744308736,'Quiroga Macarena','154319520');
INSERT INTO cliente VALUES (175921861296129,'','Paul Groussac Y Laguna Blanca',22453096,'','Consumidor Final',70368744177664,'Despensa Mili','');
INSERT INTO cliente VALUES (175921861296130,'20-17576707-0','La Pampa 736',22539958,'','Monotributo',70368744177664,'Cipriani Victor','');
INSERT INTO cliente VALUES (175921861296131,'20-94026504-6','Av. Marcelo T. De Alvear 1378',22583060,'','Resp. Inscripto',70368744177664,'Lin Weiqin','');
INSERT INTO cliente VALUES (175921861296132,'','Garibaldi 504',22651097,'','Consumidor Final',70368744177664,'Corbalan Manuel','');
INSERT INTO cliente VALUES (175921861328896,'27-21694008-9','Laprida 1787',27551372,'','Monotributo',70368744177664,'Cejas Andrea','');
INSERT INTO cliente VALUES (175921861328897,'','--',27842674,'','Consumidor Final',70368744210432,'Cabanay','');
INSERT INTO cliente VALUES (175921861361664,'','Tejerina Norte 440',29120540,'','Consumidor Final',70368744177664,'Principe Maximiliano','');
INSERT INTO cliente VALUES (175921861361665,'','Guardias Nacionales 1950',29121263,'','Consumidor Final',70368744177664,'Gigena Maria','');
INSERT INTO cliente VALUES (175921861361666,'27-177733915-5','Pje. Esperanza 2026',29121997,'','Monotributo',70368744177664,'Ferraro Adriana','');
INSERT INTO cliente VALUES (175921861361667,'','Rio Colorado 475',29122972,'','Consumidor Final',70368744177664,'Leyria Nicolas','');
INSERT INTO cliente VALUES (175921861394432,'27-30477163-7','Eduardo Jenner 810',29596890,'','Monotributo',70368744177664,'Canton Ivana','');
INSERT INTO cliente VALUES (175921861394433,'','Segurola 1017',29685472,'','Consumidor Final',70368744177664,'Humero Paola','');
INSERT INTO cliente VALUES (175921861394434,'','Mariano Fragueiro 1200- I.p.v.',29705482,'','Consumidor Final',70368744177664,'Ledesma Yamila','');
INSERT INTO cliente VALUES (175921861427200,'','Egidio Sosa 489',31157758,'','Consumidor Final',70368744177664,'Muñoz Graciela','');
INSERT INTO cliente VALUES (175921861427201,'','Remedios De Escalada 1184',31164895,'','Consumidor Final',70368744177664,'Machado Jose','');
INSERT INTO cliente VALUES (175921861427202,'27-94658534-9','Sarmiento 2273',31215210,'','Resp. Inscripto',70368744177664,'Lin Yioafang','');
INSERT INTO cliente VALUES (175921861459968,'20-11865208-9','San Luis Y Uruguay',33852988,'','Monotributo',70368744177664,'Campo Roberto','');
INSERT INTO cliente VALUES (175921861459969,'','Calle 3: 681 (unrc)',33938190,'','Consumidor Final',70368744177664,'Aliberti Ana','');
INSERT INTO cliente VALUES (175921861459970,'23-26163715-4','Colombia 1025',34035147,'','Monotributo',70368744177664,'Aguero Eloisa','');
INSERT INTO cliente VALUES (175921861492736,'','Coronel Videla 916',34810364,'','Consumidor Final',70368744177664,'Lucero Yanina','');
INSERT INTO cliente VALUES (175921861492737,'27-18823870-5','Entre Rios',34817858,'','Resp. Inscripto',70368744177664,'Lin Xuixiang','');
INSERT INTO cliente VALUES (175921861492738,'27-16274158-1','Guayaquil 190',34825923,'','Monotributo',70368744177664,'Liviero Adriana Sara','');
INSERT INTO cliente VALUES (175921861525504,'','Nahuel Huapi 1880',36781907,'','Consumidor Final',70368744177664,'Alaniz Yesica','');
INSERT INTO cliente VALUES (175921861525505,'','Rio Juramento 585',36835326,'','Consumidor Final',70368744177664,'Guardia Elena','');
INSERT INTO cliente VALUES (175921861525506,'','Rosario Sta Fe 414',36839002,'','Consumidor Final',70368744177664,'Darosa Marta','');
INSERT INTO cliente VALUES (175921861525507,'27-12762721-0','Rio Segundo 713',36845608,'','Monotributo',70368744177664,'Cruceño Susana','');
INSERT INTO cliente VALUES (175921861525508,'27-18609808-6','Suipacha 357',36852964,'','Monotributo',70368744177664,'Liviero Laura','');
INSERT INTO cliente VALUES (175921861558272,'27-27502850-4','Felipe Neri Guerra 550',37474501,'','Monotributo',70368744177664,'Gonzales Mirta Noemi','');
INSERT INTO cliente VALUES (175921861558273,'','Juan Brandino 887',37524586,'','Consumidor Final',70368744177664,'Celalles Lucas','');
INSERT INTO cliente VALUES (175921861558274,'','Mariano Fragueiro 1200- Ipv',37585840,'','Consumidor Final',70368744177664,'Ledesma 2','');
INSERT INTO cliente VALUES (175921861591040,'','Trabajo Y Prevision 1026',39526710,'','Consumidor Final',70368744177664,'Cabañas Natalia','');
INSERT INTO cliente VALUES (175921861591041,'27-31705119-6','Alte. Brown 1871',39704602,'','Monotributo',70368744177664,'Avila Rita Vanesa','');
INSERT INTO cliente VALUES (175921861591042,'','Guraldes Ricardo 1895',39712801,'','Consumidor Final',70368744177664,'Rivero Ivana','');
INSERT INTO cliente VALUES (175921861591043,'','Pje. Aconquija 3582',39718046,'','Consumidor Final',70368744177664,'Casteig Analia','');
INSERT INTO cliente VALUES (175921861591044,'','Pje. Drego 3551',39727035,'','Consumidor Final',70368744177664,'Urquiza Silvia','');
INSERT INTO cliente VALUES (175921861623808,'','Juan Brandino 905',42178977,'','Consumidor Final',70368744177664,'Avila Cecilia','');
INSERT INTO cliente VALUES (175921861623809,'','Nicolas Falcone 1385',42196217,'','Consumidor Final',70368744177664,'Susana Guides','');
INSERT INTO cliente VALUES (175921861623810,'','Segurola 1017',42306867,'','Consumidor Final',70368744177664,'Humere','');
INSERT INTO cliente VALUES (175921861623811,'','Pampas 2300',42357287,'','Consumidor Final',70368744177664,'Perez Carlos','');
INSERT INTO cliente VALUES (175921861656576,'','Fray Donatti 365',42735333,'','Consumidor Final',70368744177664,'Digenaro Erica','');
INSERT INTO cliente VALUES (175921861656577,'','Alem 1400',42755783,'','Consumidor Final',70368744177664,'Becerra Debora','');
INSERT INTO cliente VALUES (175921861656578,'','Cuaranta 202',42811155,'','Consumidor Final',70368744177664,'Vega','');
INSERT INTO cliente VALUES (175921861656579,'20-18840203-9','Pedro Goyena 364',42945286,'','Resp. Inscripto',70368744177664,'Cheng Xingfa','');
INSERT INTO cliente VALUES (175921861656580,'','Cm.',43663891,'','Consumidor Final',70368744177665,'Bar El Divorcio','');
INSERT INTO cliente VALUES (175921861689344,'','Roma 1516',45096504,'','Consumidor Final',70368744177664,'Salvi Yanina','');
INSERT INTO cliente VALUES (175921861722112,'','Colombia 1196',46955324,'','Consumidor Final',70368744177664,'Muñoz Julia','');
INSERT INTO cliente VALUES (175921861754880,'27-03495910-8','Matias Nolazco 49',48069007,'','Monotributo',70368744177664,'Pascualina Rinaldi','');
INSERT INTO cliente VALUES (175921861754881,'','Paso De Los Andes 1224',48109749,'','Consumidor Final',70368744177664,'Frencia Mayco','154314769');
INSERT INTO cliente VALUES (175921861787648,'','S',54708707,'','Resp. Inscripto',70368744177664,'Cristian','');
INSERT INTO cliente VALUES (175921861787649,'20-26462482-8','Independencia 403',54842208,'','Monotributo',70368744177665,'Gonzales Adrian','');
INSERT INTO cliente VALUES (175921861820416,'23-31744889-9','Av. Ide Y Jorge Newbery',55277650,'','Resp. Inscripto',70368744210432,'Mobilia Gonzalo','');
INSERT INTO cliente VALUES (175921861853184,'23-26925817-9','Arturo M. Bas 1238',55707728,'','Monotributo',70368744177664,'Romanello Ariel','');
INSERT INTO cliente VALUES (175921861853185,'','Firtroy 1086',55719443,'','Consumidor Final',70368744177664,'Carballo Cecilia','');
INSERT INTO cliente VALUES (175921861853186,'','Islas Giorgias 150',55809305,'','Consumidor Final',70368744177664,'Allende Susana','');
INSERT INTO cliente VALUES (175921861853187,'27-94013689-5','Estrada 1974',55843321,'','Resp. Inscripto',70368744177664,'Li Jinhua','');
INSERT INTO cliente VALUES (175921861885952,'','Antartida Arg. Y Guemez',57363800,'','Consumidor Final',70368744308736,'Serpez Alejandro','');
INSERT INTO cliente VALUES (175921861918720,'','Serrano',58931600,'','Consumidor Final',70368744177664,'Macho Noelia','');
INSERT INTO cliente VALUES (175921861951488,'','Adelia Maria 356',59939850,'','Consumidor Final',70368744177664,'Rojo Natalia','');
INSERT INTO cliente VALUES (175921861984256,'','Miguel Angel 888',61165678,'','Consumidor Final',70368744177664,'Abregu Carla','');
INSERT INTO cliente VALUES (175921861984257,'','Mariano Soler 544',61242191,'','Consumidor Final',70368744308736,'Garavaglia Ruben','');
INSERT INTO cliente VALUES (175921862017024,'','Av. Argentina 436',61356936,'','Consumidor Final',70368744177664,'Escudero Oscar','');
INSERT INTO cliente VALUES (175921862049792,'','S/n',62787828,'','Consumidor Final',70368744210432,'Pazzini Margarita','');
INSERT INTO cliente VALUES (175921862049793,'','Sobremonte Y Sucre',62877406,'','Consumidor Final',70368744177664,'Rotiseria','');
INSERT INTO cliente VALUES (175921862049794,'','---',62945393,'','Consumidor Final',70368744210432,'Avila Horacio','');
INSERT INTO cliente VALUES (175921862082560,'27-23436338-2','Pte. Peron Oeste 1832',63282862,'','Monotributo',70368744177664,'Llabres Mariela','');
INSERT INTO cliente VALUES (175921862082561,'','Indio Felipe Rosas Y Boedo',63291903,'','Consumidor Final',70368744177664,'Villarruel Yanina','');
INSERT INTO cliente VALUES (175921862082562,'','Tejerina 623',63298131,'','Consumidor Final',70368744177664,'Flores Veronica','');

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
  PRIMARY KEY  (PROVEEDOR_ID),
  KEY PROVEEDOR_N1 (LOCALIDAD_ID),
  CONSTRAINT `0_2021` FOREIGN KEY (`LOCALIDAD_ID`) REFERENCES `localidad` (`LOCALIDAD_ID`)
) TYPE=InnoDB;

--
-- Dumping data for table `proveedor`
--

INSERT INTO proveedor VALUES (246290604621824,1,'Zona Rural',131159,70368744177666,'Lacteos Ramolac','03492-496013/496123');
INSERT INTO proveedor VALUES (246290604621825,2,'Guemes Y Marmol',131184,70368744243200,'Fiambre Piamontesa','03562-480142');
INSERT INTO proveedor VALUES (246290604621826,3,'Pedro Goyena 1150',131201,70368744177664,'Pastas El Manolito','0358-4662905');
INSERT INTO proveedor VALUES (246290604621827,4,'Rio Negro 67',131219,70368744177667,'Dulces Beltop','03534-425103/415103');
INSERT INTO proveedor VALUES (246290604621828,5,'Dr. V Vaggione 16',131231,70368744177665,'Fiambre Don Juan','03582-482376/482311');
INSERT INTO proveedor VALUES (246290604654592,6,'Santiago 29',1012629,70368744243201,'Lacteos Verónica','439-6332');
INSERT INTO proveedor VALUES (246290604687360,7,'Parque Industrial',2248467,70368744177664,'Anibal Barbero','');
INSERT INTO proveedor VALUES (246290604687361,8,'Mitre 900',2295035,70368744177664,'Cirletti','');
INSERT INTO proveedor VALUES (246290604720128,9,'Cordoba Capital',2755265,70368744275968,'Lacteos La Lacteo','');
INSERT INTO proveedor VALUES (246290604752896,11,'Bregado 6759',3486983,70368744308737,'El Bierzo','011- 46878787');

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
  CONSTRAINT `0_2023` FOREIGN KEY (`PROVEEDOR_ID`) REFERENCES `proveedor` (`PROVEEDOR_ID`)
) TYPE=InnoDB;

--
-- Dumping data for table `producto`
--

INSERT INTO producto VALUES (211106232532992,1001,131161,'Leche entera Ramolac x 1 lts',3.6,0,4.35,246290604621824,-4686,1,20,0,4.6,-80,0);
INSERT INTO producto VALUES (211106232532993,1002,131162,'Yogur frutilla entero x 1 lts',4.4,0,5.5,246290604621824,-915,1,25,0,5.56,0,0);
INSERT INTO producto VALUES (211106232532994,1003,131163,'Yogur frutilla x 125 grs',1.155,0,1.65,246290604621824,-513,1,40,0,5.56,0,0);
INSERT INTO producto VALUES (211106232532995,1004,131164,'Yogur c/cereal x 165 grs',2.2,0,2.8,246290604621824,-5125,1,26,0,5.56,0,0);
INSERT INTO producto VALUES (211106232532996,1005,131165,'Manteca x 100 grs',2.51,0,3.2,246290604621824,-3153,1,27,0,5.56,0,0);
INSERT INTO producto VALUES (211106232532997,1006,131166,'Manteca x 200 grs',5.02,0,6.3,246290604621824,-959,1,25,0,5.56,0,0);
INSERT INTO producto VALUES (211106232532998,1007,131167,'Crema x 200 grs',4.22,0,5.3,246290604621824,-6179,1,25,0,5.56,0,0);
INSERT INTO producto VALUES (211106232532999,1008,131168,'Crema x 350 grs',6.43,0,7.75,246290604621824,-935,1,20,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533000,1009,131169,'Crema x 2 kg',37.16,0,46.45,246290604621824,-188,1,25,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533001,1010,131170,'Crema x 5 kg',92.92,0,116.15,246290604621824,-1,1,25,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533002,1011,131171,'Dulce de leche x 200 grs',2.86,0,3.6,246290604621824,-579,1,25,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533003,1012,131172,'Dulce de leche x 400 grs',4.95,0,6.2,246290604621824,-362,1,25,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533004,1014,131173,'Dulce de leche x 3 kg',33,0,40.3,246290604621824,-20,1,22,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533005,1015,131174,'Dulce de leche x 5 kg',55,0,67.1,246290604621824,10,1,22,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533006,1016,131175,'Queso cremoso',20.59,1,25.95,246290604621824,-624,1,26,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533007,1017,131176,'Queso cremoso x 1/2 pieza',20.59,1,25.95,246290604621824,15,1,26,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533008,1018,131177,'Queso Barra',27.6,1,34.5,246290604621824,26,1,25,0,5.56,125.5,5);
INSERT INTO producto VALUES (211106232533010,1019,131179,'Queso Sardo estacionado',41.8,1,54.35,246290604621824,-82,1,30,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533011,1020,131180,'Queso Sardo Estancia Amanecer',24.8,1,34.75,246290604621824,-175,1,40,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533015,2001,131185,'Mortadela cilindro 808',13.29,1,17.28,246290604621825,-53,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533016,2002,131186,'Mortadela bocha',22.12,1,32.15,246290604621825,1,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533017,2003,131187,'Salame tipo milan',30.8,1,43.75,246290604621825,0,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533018,2004,131188,'Salame colonia x 1/2 metro',37.12,1,48.69,246290604621825,-28,0,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533019,2005,131189,'Bondiola',56.7,1,63.5,246290604621825,-18,2,12,0,76.85,-90,5);
INSERT INTO producto VALUES (211106232533020,2006,131190,'Panceta en plancha',1,1,35.46,246290604621825,-4,0,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533021,2007,131191,'Panceta arrollada',48.84,1,64.4,246290604621825,-15,0,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533022,2008,131192,'Jamón crudo',71.6,1,90.85,246290604621825,-11,0,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533024,2010,131194,'Paleta cocida 131',16,1,21.41,246290604621825,-71,0,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533029,2015,131199,'Salchichas Panchin',2.96,0,3.65,246290604621825,-10128,0,23,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533031,3001,131202,'Ravioles de pollo x 2 pl',7.02,0,9.84,246290604621826,10,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533032,3002,131203,'Ravioles de pollo x 3 pl',8.88,0,13.65,246290604621826,-1,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533033,3006,131204,'Ravioles de ricota x 2 pl',7.02,0,9.84,246290604621826,10,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533034,3004,131205,'Sorrentino x 660 gr',7.02,0,9.84,246290604621826,10,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533035,3005,131206,'Capelletis x 500 gr',4.96,0,5.5,246290604621826,10,1,10,0,6.6,0,0);
INSERT INTO producto VALUES (211106232533037,3007,131208,'Agnolottis x 500 gr',8.49,0,8.1,246290604621826,6,1,15,0,9.8,0,0);
INSERT INTO producto VALUES (211106232533038,3008,131209,'Ñoquis x 500 gr',4.55,0,6.37,246290604621826,9,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533039,3009,131210,'Fideos finos al huevo x 500 gr',3.51,0,4.96,246290604621826,8,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533040,3010,131211,'Fideos anchos al huevo x 500 gr',3.51,0,4.96,246290604621826,-7,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533041,3011,131212,'Fideos de espinaca x 500 gr',4.34,0,6.11,246290604621826,10,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533042,3012,131213,'Fideos de morron x 500 gr',4.34,0,6.11,246290604621826,10,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533043,3013,131214,'Masa para empanadas de freir',2.81,0,3.97,246290604621826,4,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533044,3014,131215,'Masa para empanadas de horno',2.81,0,3.97,246290604621826,4,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533045,3015,131216,'Masa para empanadas arabes',3.51,0,4.96,246290604621826,10,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533046,3016,131217,'Masa para pasteles',3.39,0,4.75,246290604621826,6,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533047,3017,131218,'Masa para Pascualina',3.51,0,4.96,246290604621826,0,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533048,4010,131220,'Dulce de membrillo x 5 kg en cajón',28.6,0,36.4,246290604621827,-47,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533049,4005,131221,'Dulce de batata x 5 kg en cajón',28.6,0,36.4,246290604621827,2,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533050,4003,131222,'Dulce de batata con chocolate x 5 kg en cajón',28.6,0,36.4,246290604621827,1,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533051,4001,131223,'Dulce de batata con cerezas x 5 kg cajón',42.4,0,51.7,246290604621827,-2,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533052,4008,131224,'Dulce de membrillo con nuez x 5 kg en cajón',46,0,54.96,246290604621827,-5,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533053,4011,131225,'Dulce de membrillo x 5 kg en lata',43,0,51.7,246290604621827,2,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533054,4006,131226,'Dulce de batata x 5 kg en lata',43,0,51.7,246290604621827,-4,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533055,4004,131227,'Dulce de batata con chocolate x 5 kg en lata',43,0,51.7,246290604621827,-1,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533056,4002,131228,'Dulce de batata con cerezas x 5 kg en lata',52.05,0,62.81,246290604621827,-2,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533057,4009,131229,'Dulce de membrillo con nuez x 5 kg en lata',52.05,0,62.81,246290604621827,2,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533058,4013,131230,'Postre con chocolate x 5 kg',59,0,72.73,246290604621827,1,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533059,50001,131232,'Salame crespon',26.45,1,37.11,246290604621828,-11,2,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533060,50002,131233,'Salame colonial',1.21,1,2,246290604621828,10,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533061,50003,131234,'Salame col al vacio',1,1,2,246290604621828,10,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533062,50004,131235,'Salame curvos',1,1,2,246290604621828,10,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533063,50005,131236,'Salame picado grueso',28.52,1,37.98,246290604621828,-141,5,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533064,50006,131237,'Salame picado fino',1,1,2,246290604621828,10,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533065,50007,131238,'Salame p fino 254',21.49,1,30.54,246290604621828,10,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533066,50008,131239,'Salame por metro',28.52,1,37.98,246290604621828,-64,5,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533067,50009,131240,'Salame milan 1º',26.45,1,37.11,246290604621828,-12,3,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533068,50010,131241,'Salame milan p fino',1,1,2,246290604621828,10,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533069,50011,131242,'Salame milan 3',16.95,1,24.75,246290604621828,-134,6,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533070,50012,131243,'Salame cantipalo',1,1,2,246290604621828,10,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533071,50013,131244,'Bondiola',53.72,1,60.7,246290604621828,9,3,13,0,73.45,44,15);
INSERT INTO producto VALUES (211106232533072,50014,131245,'Jamón crudo tipo serrano',53.72,1,74.3,246290604621828,-1,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533074,50016,131247,'Mortadela familiar',4.67,0,5.75,246290604621828,-546,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533075,50017,131248,'Mortadela bologna',12.5,1,16.25,246290604621828,6,3,30,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533076,50018,131249,'Mortadela 254',9.5,1,14.8,246290604621828,-163,6,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533077,50019,131250,'Mortadela bocha',11.16,1,15.62,246290604621828,-46,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533078,50020,131251,'Paleta de cerdo',13.92,1,18.1,246290604621828,-2,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533079,50021,131252,'Paleta sandwichera',11.16,1,15.62,246290604621828,-191,8,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533080,50022,131253,'Salchichón con jamón',10.58,1,15.62,246290604621828,-27,5,47,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533081,50023,131254,'Salchichón primavera',10.58,1,15.62,246290604621828,-46,5,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533082,50024,131255,'Queso de cerdo',11.16,1,15.62,246290604621828,-35,5,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533083,50025,131256,'Morcillon',1,1,2,246290604621828,10,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533084,50026,131257,'Chorizos españoles',1,1,1.15,246290604621828,10,1,15,0,1.4,50,5);
INSERT INTO producto VALUES (211106232533085,50027,131258,'Panceta en plancha',1,1,2,246290604621828,10,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533086,50028,131259,'Jamón cocido linea Dorada',21.48,1,28,246290604621828,-2,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533087,50029,131260,'Patita de cerdo',1,1,2,246290604621828,10,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533088,50030,131261,'Salames puros',1,1,2,246290604621828,10,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232533089,50031,131262,'Salame cantipalo por 1/2',1,1,2,246290604621828,10,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232565760,3003,988607,'Ravioles 4 quesos x2 pl',7.02,0,9.84,246290604621826,10,10,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232565761,3018,1009935,'Ravioles x 500 gr',4.75,0,6.7,246290604621826,10,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232565762,3019,1011144,'Fideos en bandejas x 500 gr',4.34,0,6.12,246290604621826,10,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232565763,1013,1024897,'Dulce de leche Rep. x 400 gr',5.99,0,6.2,246290604621824,81,1,25,0,5.56,0,0);
INSERT INTO producto VALUES (211106232598528,4012,1153908,'Dulce de membrillo x 500 gr',3.6,0,4.34,246290604621827,63,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232598529,4007,1155137,'Dulce de batata x 500 gr',3.6,0,4.34,246290604621827,-2,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232598530,6001,1170158,'Leche L.V. entera x 1 lt',4.4,0,4.69,246290604654592,-580,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232598531,6002,1170385,'Leche L.V. desc x 1 lt',4.4,0,4.69,246290604654592,211,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232598532,6003,1170820,'Leche L.V. entera x 1 lt c/FE,A y D',4.4,0,4.69,246290604654592,0,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232598533,6004,1171051,'Leche L.V. chocolatada x 1 lt',5.25,0,5.74,246290604654592,-2435,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232598534,6005,1171494,'Leche L.V. chocolatada x 250 ml',2.45,0,2.73,246290604654592,-3546,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232598535,6006,1171729,'Ricotta Porcion',11.9,1,12.65,246290604654592,0,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232598536,6007,1171966,'Ricotta',11.9,1,13.14,246290604654592,-1,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232598537,6008,1172206,'Crema veronica x 200 cm3',3.53,0,4.85,246290604654592,57,1,37,0,5.56,0,0);
INSERT INTO producto VALUES (211106232598538,6009,1172665,'Crema veronica x 360 cm3',6,0,6.7,246290604654592,0,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232598539,6010,1174449,'Crema veronica x 5 lt',104.67,0,97.52,246290604654592,30,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232631296,6011,1581982,'Crema Veronica x 1 kg',18.3,0,20.25,246290604654592,-1,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232631297,6012,1582228,'Crema Veronica x 10 kg',143,0,161.2,246290604654592,-2,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232631298,6013,1582479,'Cuartirolo Veronica',26.34,1,32.95,246290604654592,5,1,25,0,5.56,0,0);
INSERT INTO producto VALUES (211106232631299,6014,1582726,'Por Salut Vacio',26.5,1,28.3,246290604654592,0,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232631300,6015,1583213,'Cuartirolo Vacio Redondo',26,1,28.03,246290604654592,-2,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232631301,6016,1583464,'Descremado s/sal',28.5,1,30.35,246290604654592,0,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232631302,6017,1583717,'Descremado Vacio',28.5,1,30.35,246290604654592,0,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232631303,6018,1583978,'Por Salut x 1/2',27.5,1,34.15,246290604654592,0,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232631304,6019,1584235,'Por Salut s/sal x 1/2',28,1,35,246290604654592,0,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232631305,6020,1586920,'Cuartirolo Cuadrado Veronica x 1/2',26.34,1,32.9,246290604654592,-1,1,0,0,5.56,-1,0);
INSERT INTO producto VALUES (211106232631306,6021,1588407,'Cuartirolo Redondo x 1/2',26.5,1,28.3,246290604654592,0,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232631307,6022,1589168,'Descremado s/sal x 1/2',28.82,1,36.02,246290604654592,0,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232631308,6023,1590686,'Queso Pategras',30.5,1,38.23,246290604654592,-86,2,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232631309,6024,1590961,'Parrillero',33.41,1,41.74,246290604654592,0,2,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232631310,6025,1597574,'Barra Tybo Veronica',28,1,31.65,246290604654592,4,2,13,0,38.3,36,10);
INSERT INTO producto VALUES (211106232631311,6026,1597850,'Mozzarella Barra',22.32,1,27.9,246290604654592,-8,2,25,0,5.56,0,0);
INSERT INTO producto VALUES (211106232631312,6027,1601717,'Fymbo Vacio',30.5,1,38.23,246290604654592,0,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232631313,6028,1604841,'Queso Gouda',34.5,1,37.93,246290604654592,-23,2,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232631314,6029,1605124,'Queso Azul Veronica',39.78,1,47.77,246290604654592,-59,3,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232631315,6030,1606986,'Barra Danbo Pergamino',27.8,1,30.6,246290604654592,1,1,10,0,37,23.7,5);
INSERT INTO producto VALUES (211106232631316,6031,1607267,'Fontina Veronica',48.26,1,60.32,246290604654592,0,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232631317,6032,1608088,'Fontina x 1/2',48.6,1,51.69,246290604654592,0,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232631318,6033,1608915,'Queso Colonia',48.6,1,51.69,246290604654592,0,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232631319,6034,1609202,'Colonia Vacio',60.5,1,57.5,246290604654592,23,1,15,0,69.6,45,3);
INSERT INTO producto VALUES (211106232631320,6035,1609491,'Gruyerito',50.12,1,63.15,246290604654592,0,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232631321,6036,1610062,'Gruyerito x 1/2',50.12,1,63.15,246290604654592,1,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232631322,6037,1610355,'Pepato Chico',52,1,56.3,246290604654592,2,2,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232631323,6038,1610934,'Pepato',51.62,1,64.55,246290604654592,-1,1,25,0,5.56,0,0);
INSERT INTO producto VALUES (211106232631324,6039,1611231,'Sbrinz',52,1,56.3,246290604654592,0,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232631325,6040,1611536,'Reggianito',51.62,1,64.53,246290604654592,4,2,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232631326,6041,1611844,'Reggianito x 1/2',51.62,1,64.53,246290604654592,0,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232631327,6042,1612147,'Sardo Veronica',51.62,1,64.46,246290604654592,0,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232631328,6043,1612468,'Provolone',51.62,1,64.53,246290604654592,0,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232631329,6044,1613356,'Provolone x 1/2',53,1,57.36,246290604654592,1,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232631330,6045,1613681,'Sardo Veronica Fresco',52,1,56.3,246290604654592,0,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232631331,6046,1614586,'Rallado Display 100 x 8 gr',54,0,60.35,246290604654592,1,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232631332,6047,1615504,'Rallado 40 gr',2.89,0,3.5,246290604654592,-2803,4,20,0,5.56,0,0);
INSERT INTO producto VALUES (211106232631333,6048,1615825,'Rallado Suelto x 2 kg',107.84,0,129.45,246290604654592,-62,1,20,0,5.56,0,0);
INSERT INTO producto VALUES (211106232631334,6049,1616748,'Rallado x 120 gr Light',8.3,0,9,246290604654592,0,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232631335,6050,1617990,'Rallado x 120 gr',7.5,0,8.2,246290604654592,0,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232631336,6051,1619550,'Manteca Veronica x 100 gr',2.4,0,3.2,246290604654592,0,10,32,0,5.56,0,0);
INSERT INTO producto VALUES (211106232631337,6052,1619896,'Manteca Veronica x 200 gr',4.8,0,5.55,246290604654592,0,10,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232631338,6053,1620530,'Manteca Pergamino x 100 gr',2.35,0,2.57,246290604654592,0,10,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232631339,6054,1621502,'Manteca Pergamino x 200 gr',4.7,0,5.25,246290604654592,0,10,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232631340,6055,1622461,'Manteca Pergamino x 5 kg',111.5,0,120.5,246290604654592,0,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232631341,6056,1623426,'Manteca Block x 25 kg',557.5,0,597.1,246290604654592,0,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664064,6057,1647166,'Manteca Veronica 144 x 10 gr',44,0,49.51,246290604654592,3,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664065,6058,1647666,'Manteca Veronica x 500 gr',11.6,0,12.82,246290604654592,0,10,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664066,6059,1650251,'Manteca Pergamino x 500 gr',11.6,0,12.82,246290604654592,0,10,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664067,6060,1651563,'Dulce de leche Veronica x 250 gr',3.5,0,3.88,246290604654592,0,6,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664068,6061,1651913,'Dulce de leche Veronica x 1 kg',12.5,0,13.65,246290604654592,0,2,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664069,6062,1652760,'Dulce de leche x 2 kg',25,0,27.2,246290604654592,0,2,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664070,6063,1653930,'Dulce de leche Veronica x 5 kg',57,0,61.9,246290604654592,0,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664071,6064,1655107,'Dulce de leche Veronica Rep. x 5 kg',57,0,61.9,246290604654592,0,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664072,6065,1656128,'Dulce de leche Veronica Hel.x 10 kg',114,0,121.5,246290604654592,0,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664073,6066,1657824,'Dulce de leche Veronica Alf.x 10 kg',114,0,121.5,246290604654592,-1,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664074,6067,1658186,'Dulce de leche Pergamino x 5 kg',55,0,59.2,246290604654592,0,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664075,6068,1659226,'Dulce de leche Pergamino x 10 kg',110,0,119.8,246290604654592,0,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664076,6069,1660278,'Dulce de leche Pergamino Rep.x 10 kg',110,0,119.8,246290604654592,-1,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664077,6070,1662011,'Dulce de leche Pergamino Rep.x 5 kg',55,0,59.2,246290604654592,0,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664078,6071,1663756,'Dulce de leche Veronica Past.x 10 kg',114,0,121.5,246290604654592,0,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664079,6072,1664126,'Dulce de leche Veronica x 400 gr',5,0,5.75,246290604654592,0,6,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664080,6073,1664845,'Dulce de leche Veronica Rep.x 400 gr',5.3,0,6,246290604654592,0,6,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664081,6074,1665219,'Dulce de leche Veronica 108 x 25 gr',45,0,49.5,246290604654592,2,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664082,6075,1665948,'Dulce de leche Pergamino x 400 gr',4.42,0,5.75,246290604654592,120,6,30,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664083,6076,1667745,'Cuartirolo Trozado',26.34,1,32.98,246290604654592,5,2,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664084,6077,1668129,'Descremado Trozado',29,1,37.15,246290604654592,-6,2,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664085,6078,1671019,'Por Salut s/sal Trozado',29.34,1,36.77,246290604654592,-2,2,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664086,6079,1672124,'Por Salut Trozado',27.59,1,34.5,246290604654592,-11,2,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664087,6080,1672512,'Pategras Trozado',34.03,1,42.56,246290604654592,9,4,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664088,6081,1672899,'Holandita',34.03,1,42.56,246290604654592,-32,4,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664089,6082,1673289,'Mini Fymbo',33.42,1,41.74,246290604654592,0,4,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664090,6083,1673683,'Tybo Porcion',33.6,1,36.79,246290604654592,0,2,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664091,6084,1674080,'Tybo Redondo',33.6,1,36.79,246290604654592,0,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664092,6085,1674476,'Parrillero Trozado',39.78,1,50,246290604654592,-29,2,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664094,6087,1675473,'Reggianito Trozado',51.62,1,64.53,246290604654592,0,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664095,6088,1686268,'Mini Queso Azul',40.22,1,50,246290604654592,-83,6,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664096,6089,1686671,'Mni Minifymbo',33.6,1,36.79,246290604654592,-1,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664097,6090,1687079,'Barra Feteada',38.18,1,42.8,246290604654592,1,1,12,0,51.75,17.8,5);
INSERT INTO producto VALUES (211106232664098,6091,1687683,'Mozzarella Trozada',33.76,1,42.15,246290604654592,0,2,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664099,6092,1688290,'Mozzarella Magro s/sal Trozada',37,1,40.46,246290604654592,0,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664100,6093,1688905,'Cuartirolo Pergamino',26,1,28.52,246290604654592,0,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664101,6094,1689518,'Fundido Pergamino',19.44,1,25.5,246290604654592,-144,4,31,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664102,6095,1690135,'Queso Untable Clas x 190 gr',5.6,0,5.95,246290604654592,-120,8,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664103,6096,1690753,'Queso Untable Desc.x190 gr',5.6,0,5.95,246290604654592,-70,8,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664104,6097,1691375,'Queso Untable Azul x 190 gr',5.6,0,5.95,246290604654592,-128,8,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664105,6098,1692000,'Queso Untable Gruy.x190 gr',5.6,0,5.95,246290604654592,-24,8,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664106,6099,1692628,'Queso Untable Parm.x 190 gr',5.6,0,5.95,246290604654592,-43,8,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664107,6100,1693259,'Queso Untable Chedd.x 190 gr',5.6,0,5.95,246290604654592,9,8,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664108,6101,1693893,'Queso Untable Cibou.x 190 gr',5.6,0,5.95,246290604654592,0,8,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664109,6102,1694530,'Queso Untable Chedd.x 3 kg',74.69,0,93.37,246290604654592,0,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664110,6103,1703536,'Queso Untable Desc. 108 x 20 gr',54,0,61.15,246290604654592,2,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664111,6104,1704381,'Queso Untable 108 x 20 gr',54,0,61.15,246290604654592,2,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664112,6105,1704818,'Queso Crema s/sal x 3 kg',48.5,0,53.65,246290604654592,0,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664113,6106,1705465,'Queso Crema s/sal x 250 gr',5.3,0,5.78,246290604654592,8,6,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664114,6107,1706950,'Queso Blanco x 250 gr',5.3,0,5.85,246290604654592,8,4,10,0,7.05,0,0);
INSERT INTO producto VALUES (211106232664115,6109,1707809,'Mermelada Durazno 108 x 25 gr',41,0,45.39,246290604654592,1,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664116,6110,1708254,'Leche Polvo Ent. x 800 gr',21.5,0,23.89,246290604654592,0,2,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664117,6111,1709548,'Leche Polvo Desc.x 800 gr',21.5,0,23.89,246290604654592,-1,2,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664118,6112,1710419,'Leche Polvo Ent.x 25 kg',21.5,1,23.89,246290604654592,0,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664119,6113,1710872,'Leche Desc.x 25 kg',21.5,1,23.89,246290604654592,0,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664120,6114,1714325,'Leche Polvo Ent.x 5 kg',21.5,1,23.89,246290604654592,0,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664121,6115,1714998,'Leche Polvo Desc.x 5 kg',21.5,1,23.89,246290604654592,0,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664122,6116,1715674,'Leche Polvo Ent.x 400 gr',10.75,0,14.1,246290604654592,0,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664123,6117,1717226,'Suero en Polvo x 5 kg',6.3,1,7.02,246290604654592,0,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664124,6118,1728181,'Suero en Polvo x 25 kg',6.3,1,7.02,246290604654592,0,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664125,6119,1728642,'Suero en Polvo x 30 kg',6.3,1,7.02,246290604654592,0,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232664126,6120,1729330,'Concentrado de Proteinas x 2 kg',16.5,1,19,246290604654592,-2,1,15,0,23,1,2);
INSERT INTO producto VALUES (211106232696832,7001,2248519,'Queso de cerdo AB',12.06,1,20.5,246290604687360,-36,3,70,0,5.56,0,0);
INSERT INTO producto VALUES (211106232696833,7002,2248982,'Bondiola AB',48.76,1,59.5,246290604687360,17,6,22,0,72,83,30);
INSERT INTO producto VALUES (211106232696834,8003,2295056,'Chisitos',10.35,0,13.5,246290604687361,-9,1,30,0,5.56,0,0);
INSERT INTO producto VALUES (211106232696835,8001,2296425,'Tutucas',14.9,0,19.4,246290604687361,-8,1,30,0,5.56,0,0);
INSERT INTO producto VALUES (211106232696836,8002,2296896,'Papitas Fritas',31.4,0,44.55,246290604687361,-26,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232696837,8004,2297825,'Palitos Salados',10.75,0,13.97,246290604687361,-48,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232729600,4014,2481323,'Dulce de leche Beltop x 400',3.72,0,4.88,246290604621827,-19,12,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232729601,50032,2527425,'combo surtido',11.61,1,13.96,246290604621828,-21,20,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232729602,1023,2561285,'Leche Descremada x 1 lt.',3.6,0,4.35,246290604621824,-740,24,20,0,5.56,0,0);
INSERT INTO producto VALUES (211106232762368,1024,2700371,'Yogur vainilla entero x 1 lt',4.4,0,5.5,246290604621824,-386,24,25,0,5.56,0,0);
INSERT INTO producto VALUES (211106232762369,1025,2700861,'Yogur durazno entero x 1 lt',4.4,0,5.5,246290604621824,-201,24,25,0,5.56,0,0);
INSERT INTO producto VALUES (211106232762370,1026,2703213,'Yogur entero x 1 lt (surt.)',4.4,0,5.5,246290604621824,-6391,56,25,0,5.56,0,0);
INSERT INTO producto VALUES (211106232762371,1027,2708837,'Yogur vainilla x 125 gr',1.155,0,1.65,246290604621824,-210,36,40,0,5.56,0,0);
INSERT INTO producto VALUES (211106232762372,1028,2724776,'Yogur descremado x 1 lt',4.4,0,5.5,246290604621824,-295,12,25,0,5.56,0,0);
INSERT INTO producto VALUES (211106232762373,7003,2748353,'Salame p/ Grueso AB',40.5,1,50.65,246290604687360,-107,2,25,0,5.56,0,0);
INSERT INTO producto VALUES (211106232762374,9001,2755302,'Leche entera La Lacteo x 1 lt',3.25,0,4.1,246290604720128,-315,16,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232762375,9002,2756471,'Leche descremada x 1 lt',3.25,0,4.1,246290604720128,-37,16,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232762376,9003,2756959,'Leche L.V. ent x 1 lt',3.69,0,4.69,246290604720128,-86,60,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232762377,9004,2759296,'Leche L.V. descremada x 1 lt',3.69,0,4.69,246290604720128,-28,60,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232762378,9005,2759789,'UAT entera Fortificada c/Calcio',3.4,0,4.1,246290604720128,0,10,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232762379,9006,2760749,'UAT desc. Fortificada c/Calcio',3.4,0,4.1,246290604720128,-1,10,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232762380,9007,2761248,'Yogur frutilla x 160',1.57,0,1.99,246290604720128,0,20,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232762381,9008,2761751,'Yogur vainilla x 160 gr',1.57,0,1.99,246290604720128,0,24,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232762382,9009,2763207,'Yogur durazno x 160 gr',1.57,0,1.99,246290604720128,0,24,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232762383,9010,2763953,'Yogur dulce de leche x 160 gr',1.57,0,1.99,246290604720128,0,24,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232762384,9011,2764705,'Yogur frutilla x 125',1.34,0,1.74,246290604720128,0,24,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232762385,9012,2766226,'Yogur vainilla x 125 gr',1.34,0,1.74,246290604720128,0,24,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232762386,9013,2766807,'Yogur durazno x 125 gr',1.34,0,1.74,246290604720128,-10,24,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232762387,9014,2768046,'Frutado Frutilla x 180 gr',2.8,0,3.52,246290604720128,-188,24,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232762388,9015,2768560,'Frutado durazno x 180 gr',2.8,0,3.52,246290604720128,-122,24,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232762389,9016,2769077,'Yogur c/Cereal x 125 gr',2.36,0,2.85,246290604720128,0,20,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232762390,9017,2769597,'Yogur frut entero x 1 lt',4.45,0,5.56,246290604720128,-41,24,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232762391,9018,2770119,'Yogur vai. entero x 1 lt',4.45,0,5.56,246290604720128,-36,20,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232762392,9019,2771133,'Yogur dur. entero x 1 lt',4.45,0,5.56,246290604720128,-10,20,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232762393,9020,2771661,'Yogur D. de leche entero x 1 lt',3.92,0,5.37,246290604720128,-1,20,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232762394,9021,2772193,'Yogur anana entero x 1 lt',4.45,0,5.56,246290604720128,-6,20,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232762395,9022,2772974,'Yogur multifruta entero x 1 lt',4.45,0,5.56,246290604720128,-19,20,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232762396,9023,2773502,'Yogur frutilla descremado x 1 lt',4.45,0,5.56,246290604720128,-11,12,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232762397,9024,2775032,'Yogur vainilla descremado x 1lt',4.45,0,5.56,246290604720128,-8,12,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232762398,9025,2775568,'Bebible frutilla x 190 g',2.45,0,2.8,246290604720128,-120,20,13,0,3.35,0,0);
INSERT INTO producto VALUES (211106232762399,9026,2778629,'Bebible vainilla x 190 gr',2.45,0,2.7,246290604720128,-338,20,10,0,3.3,0,0);
INSERT INTO producto VALUES (211106232762400,9027,2779165,'Dulce de leche x 250 gr',3.39,0,4.25,246290604720128,-125,24,25,0,5.56,0,0);
INSERT INTO producto VALUES (211106232762401,9028,2779704,'Dulce de leche x 400 gr',4.92,0,6.15,246290604720128,-266,12,25,0,5.56,0,0);
INSERT INTO producto VALUES (211106232762402,2029,2780758,'Dulce de leche x 1 kg',8.55,0,12.81,246290604720128,-3,6,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232762403,9030,2781563,'Queso Cremoso',22.88,1,28.01,246290604720128,-37,6,20,0,5.56,0,0);
INSERT INTO producto VALUES (211106232762404,9029,2784168,'Queso Por Salut',21,1,27.2,246290604720128,0,4,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232762405,903,2784715,'Queso tybo al vacio',29.458,1,36.25,246290604720128,-111,6,23,0,5.56,0,0);
INSERT INTO producto VALUES (211106232762406,9032,2785266,'Queso Sardo La Lacteo',37.838,1,47.3,246290604720128,-22,4,25,0,5.56,0,0);
INSERT INTO producto VALUES (211106232762407,9033,2785819,'Queso Fymbo',27.85,1,34.8,246290604720128,0,2,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232762408,9044,2786375,'Flan x 130 gr',1.92,0,2.39,246290604720128,-40,24,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232762409,9034,2786935,'Flan c/Dulce de leche x 130 gr',1.92,0,2.39,246290604720128,-12,24,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232762410,9035,2787499,'Postre vainilla x 110 gr',1.92,0,2.39,246290604720128,-94,20,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232762411,9036,2788328,'Postre dulce de leche x 110 gr',1.92,0,2.39,246290604720128,-28,20,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232762412,9037,2788889,'Postre chocolate x 110 gr',1.92,0,2.39,246290604720128,-4,20,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232795136,9038,2842713,'Postre x 110 gr',1.92,0,2.39,246290604720128,-2121,20,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232795137,9039,2845947,'Flan x 130 gr',1.92,0,2.39,246290604720128,-2393,20,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232795138,6025,3014851,'Barra Tybo Veronica',33.88,1,30.8,246290604654592,4,2,10,0,37.3,80,10);
INSERT INTO producto VALUES (211106232827904,2009,3044172,'Jamón Cocido',38.2,1,45.39,246290604621825,-1,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232827905,8005,3113322,'Puflitos',15.84,0,20.59,246290604687361,-3,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232827906,9040,3213309,'Pategras',31.15,1,37.95,246290604720128,-4,4,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232860672,1101,3487024,'Milan Premium',32.6,1,42.57,246290604752896,-21,5,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232860673,1102,3489250,'Milan',28.02,1,36.28,246290604752896,-68,4,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232860674,1103,3490401,'Salame Crespon Premium',32.6,1,42.57,246290604752896,-18,3,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232860675,1104,3490993,'Lomo de Cerdo Ahumado',39.87,1,47.85,246290604752896,4,4,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232860676,1105,3492133,'Jamon Cocido Villa Franca',38.46,1,48.1,246290604752896,-9,4,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232860677,1106,3492720,'Jamon Cocido mediano c/tocino',31.29,1,39.1,246290604752896,-13,4,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232860678,1107,3493311,'Fiambre p/ Emp.',15.34,1,19.17,246290604752896,-35,4,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232860679,1108,3493902,'Salchichon c/ jamon',15.62,1,19.76,246290604752896,-6,4,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232860680,1109,3495617,'Salchichon c/ Verduras',15.62,1,19.76,246290604752896,-12,4,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232860681,1110,3497339,'Mortadela Bocha',18.28,1,23.06,246290604752896,-80,4,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232860682,6121,3619947,'Ricotta De la Zona',8.89,1,10.74,246290604687361,-3,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232893440,8006,3894047,'Mani saborizado',11.79,0,15.35,246290604687361,-6,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232926208,1111,4558698,'Chacarero p/ fino',40.65,1,45.55,246290604752896,12,4,12,0,55.1,67,20);
INSERT INTO producto VALUES (211106232926209,1112,4560441,'Chacarero p/ grueso',40.65,1,46.75,246290604752896,-8,4,15,0,56.6,40,20);
INSERT INTO producto VALUES (211106232926210,1113,4561038,'Fuet Bastón',44.78,1,55.97,246290604752896,13,10,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232958976,9042,5370510,'Reggianito',37.67,1,47.08,246290604720128,2,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232958977,9043,5372847,'Reggianito Trozado',51.62,1,64.53,246290604720128,0,2,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106232991744,9041,6092548,'Frutado x 180 gr',2.8,0,3.52,246290604720128,-358,100,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106233024512,8007,6182142,'Pasta Roquefort',20.82,0,27.1,246290604687361,-44,0,30,0,5.56,0,0);
INSERT INTO producto VALUES (211106233024513,8008,6241147,'Crocantes Palito',4.14,0,5.37,246290604687361,-49,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106233024514,8009,6242348,'Crocantes Bolita',4.14,0,5.4,246290604687361,-119,1,30,0,5.56,0,0);
INSERT INTO producto VALUES (211106233057280,8010,6687648,'P. Rallado',3.3,1,3.72,246290604687361,-20,2,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106233090048,1114,7637773,'Salame p/ Grueso Bierzo',40.65,1,48.76,246290604752896,-4,2,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106233122816,8011,8427263,'Mortadela Pal.',33.87,1,42.35,246290604687361,-9,1,25,0,5.56,0,0);
INSERT INTO producto VALUES (211106233155584,8012,9074677,'Lomo Calchaqui',53.38,1,65.15,246290604687361,-6,1,22,0,5.56,0,0);
INSERT INTO producto VALUES (211106233188352,1030,12885121,'Leche sachet x 1 lt.',3.6,0,4.35,246290604621824,-2902,0,20,0,5.56,0,0);
INSERT INTO producto VALUES (211106233221120,1180,14441498,'Fiambres y lacteos',1,0,0.82,246290604720128,-1859,0,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106233253888,1031,29778170,'Yogur dulce de leche x 1 lt.',4.4,0,5.5,246290604621824,30,12,25,0,5.56,0,0);
INSERT INTO producto VALUES (211106233286656,1116,43998144,'Pastron',38,1,47.53,246290604752896,13,1,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106233319424,6122,47653303,'Queso Untable Surt. x 190 gr',5.6,0,5.95,246290604654592,-5,20,0,0,5.56,0,0);
INSERT INTO producto VALUES (211106233352192,9045,71638183,'Bebible x 190',2.45,0,3.05,246290604720128,-2,20,24,0,3.7,0,0);
INSERT INTO producto VALUES (211106233384960,8013,72901672,'Nachos',11.98,0,15.6,246290604687361,-1,1,30,0,5.56,0,0);

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
  PRIMARY KEY  (FACTURA_ID)
) TYPE=InnoDB;

--
-- Dumping data for table `factura`
--

INSERT INTO factura VALUES (422212465295360,3343414,9.65,55.61,4,'',0,'FacturaCliente-A','3-2013');
INSERT INTO factura VALUES (422212465328128,3474465,0.57,3.27,5,'',0,'FacturaCliente-A','3-2013');
INSERT INTO factura VALUES (422212465328129,3475523,3.99,22.99,6,'',0,'FacturaCliente-A','3-2013');
INSERT INTO factura VALUES (422212465328130,3477576,0,204.85,4,'',0,'FacturaCliente-B','3-2013');
INSERT INTO factura VALUES (422212465328131,3479621,0,9.8,5,'',0,'FacturaCliente-B','3-2013');
INSERT INTO factura VALUES (422212465328132,3481663,0,3.69,6,'',0,'FacturaCliente-B','3-2013');
INSERT INTO factura VALUES (422212465328133,3483823,0,9.8,6,'',0,'RemitoCliente','3-2013');
INSERT INTO factura VALUES (422212465328134,3483849,0,9.8,7,'',0,'FacturaCliente-B','3-2013');
INSERT INTO factura VALUES (422212465360896,3540604,6.43,37.03,7,'',0,'FacturaCliente-A','3-2013');
INSERT INTO factura VALUES (422212465360897,3542652,0,195.05,8,'',0,'FacturaCliente-B','3-2013');
INSERT INTO factura VALUES (422212465360898,3545307,0,320.7,9,'',0,'FacturaCliente-B','3-2013');
INSERT INTO factura VALUES (422212465393664,6172593,11.63,67.03,8,'',0,'FacturaCliente-A','6-2013');
INSERT INTO factura VALUES (422212465393665,6174763,0,9.8,7,'',0,'RemitoCliente','6-2013');
INSERT INTO factura VALUES (422212465426432,6314331,5.1,29.4,9,'',0,'FacturaCliente-A','3-2013');
INSERT INTO factura VALUES (422212465459200,6498566,25.52,147.02,10,'',0,'FacturaCliente-A','3-2013');
INSERT INTO factura VALUES (422212465491968,6557194,0,51.79,8,'',0,'RemitoCliente','1-2014');
INSERT INTO factura VALUES (422212465491969,6558259,1.7,9.8,11,'',0,'FacturaCliente-A','1-2014');
INSERT INTO factura VALUES (422212465524736,6632318,1.7,9.8,12,'',0,'FacturaCliente-A','1-2014');
INSERT INTO factura VALUES (422212465557504,7014648,6.43,37.03,13,'',0,'FacturaCliente-A','7-2014');
INSERT INTO factura VALUES (422212465557505,7032317,6.43,37.03,14,'',0,'FacturaCliente-A','7-2014');
INSERT INTO factura VALUES (457396837515264,6174883,10.5,265.2,230,NULL,0,'FacturaProveedor','6-2013');
INSERT INTO factura VALUES (457396837548032,6314996,15.65,529.9,12032030,NULL,0,'FacturaProveedor','3-2013');
INSERT INTO factura VALUES (457396837580800,6499234,30.5,3139.2,120350480,NULL,0,'FacturaProveedor','3-2013');

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
  PRIMARY KEY  (FACTURA_CLIENTE_ID),
  KEY FACTURA_CLIENTE_N1 (CLIENTE_ID),
  CONSTRAINT `0_2026` FOREIGN KEY (`CLIENTE_ID`) REFERENCES `cliente` (`CLIENTE_ID`),
  CONSTRAINT `0_2027` FOREIGN KEY (`FACTURA_CLIENTE_ID`) REFERENCES `factura` (`FACTURA_ID`)
) TYPE=InnoDB;

--
-- Dumping data for table `factura_cliente`
--

INSERT INTO factura_cliente VALUES (422212465295360,175921860837377,'CONTADO','2013-03-25','2013-03-25',55.61,'','Resp. Inscripto','');
INSERT INTO factura_cliente VALUES (422212465328128,175921860476940,'CTA. CTE.','2013-03-25',NULL,0,'','Resp. Inscripto','');
INSERT INTO factura_cliente VALUES (422212465328129,175921861787648,'CONTADO','2013-03-25','2013-03-25',22.99,'','Resp. Inscripto','');
INSERT INTO factura_cliente VALUES (422212465328130,175921860476928,'CTA. CTE.','2013-03-25',NULL,0,'','Monotributo','');
INSERT INTO factura_cliente VALUES (422212465328131,175921860444170,'CONTADO','2013-03-20','2013-03-20',9.8,'','Monotributo','');
INSERT INTO factura_cliente VALUES (422212465328132,175921861525504,'CONTADO','2013-03-25','2013-03-25',3.69,'','Consumidor Final','');
INSERT INTO factura_cliente VALUES (422212465328133,175921861459970,'','2013-03-25',NULL,0,'','','');
INSERT INTO factura_cliente VALUES (422212465328134,175921861459970,'CONTADO','2013-03-20','2013-03-20',9.8,'','Monotributo','6');
INSERT INTO factura_cliente VALUES (422212465360896,175921860476938,'CONTADO','2013-03-25','2013-03-25',37.03,'','Resp. Inscripto','');
INSERT INTO factura_cliente VALUES (422212465360897,175921860444183,'CONTADO','2013-03-25','2013-03-25',195.05,'','Monotributo','');
INSERT INTO factura_cliente VALUES (422212465360898,175921860444170,'CTA. CTE.','2013-03-25','0000-00-00',0,'','Monotributo','');
INSERT INTO factura_cliente VALUES (422212465393664,175921860476938,'CTA. CTE.','2013-06-05',NULL,0,'','Resp. Inscripto','');
INSERT INTO factura_cliente VALUES (422212465393665,175921860444179,'','2013-06-05',NULL,0,'','','');
INSERT INTO factura_cliente VALUES (422212465426432,175921860476940,'CTA. CTE.','2013-03-20',NULL,0,'','Resp. Inscripto','');
INSERT INTO factura_cliente VALUES (422212465459200,175921860476938,'CTA. CTE.','2013-03-27','2013-03-27',50,'','Resp. Inscripto','');
INSERT INTO factura_cliente VALUES (422212465491968,175921861459970,'','2014-01-29',NULL,0,'','','');
INSERT INTO factura_cliente VALUES (422212465491969,175921860837377,'CONTADO','2014-01-29','2014-01-29',9.8,'','Resp. Inscripto','');
INSERT INTO factura_cliente VALUES (422212465524736,175921860444175,'CONTADO','2014-01-29','2014-01-29',9.8,'209443392','Resp. Inscripto','');
INSERT INTO factura_cliente VALUES (422212465557504,175921860444165,'CONTADO','2014-07-30','2014-07-30',137.03,'202981244','Resp. Inscripto','');
INSERT INTO factura_cliente VALUES (422212465557505,175921860444175,'CONTADO','2014-07-30','2014-07-30',37.03,'209443392','Resp. Inscripto','');

--
-- Table structure for table `factura_proveedor`
--

CREATE TABLE factura_proveedor (
  FACTURA_PROVEEDOR_ID bigint(20) NOT NULL default '0',
  FECHA date default NULL,
  PROVEEDOR_ID bigint(20) default NULL,
  PRIMARY KEY  (FACTURA_PROVEEDOR_ID),
  KEY FACTURA_PROVEEDOR_N1 (PROVEEDOR_ID),
  CONSTRAINT `0_2029` FOREIGN KEY (`FACTURA_PROVEEDOR_ID`) REFERENCES `factura` (`FACTURA_ID`),
  CONSTRAINT `0_2030` FOREIGN KEY (`PROVEEDOR_ID`) REFERENCES `proveedor` (`PROVEEDOR_ID`)
) TYPE=InnoDB;

--
-- Dumping data for table `factura_proveedor`
--

INSERT INTO factura_proveedor VALUES (457396837515264,'2013-03-05',246290604621826);
INSERT INTO factura_proveedor VALUES (457396837548032,'2013-03-19',246290604654592);
INSERT INTO factura_proveedor VALUES (457396837580800,'2013-03-27',246290604654592);

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

INSERT INTO planilla_es VALUES (281474976808963,'2013-03-12',6305937,1,0,'3-2013');
INSERT INTO planilla_es VALUES (281474976874496,'2013-03-27',6499422,2,350.48,'3-2013');
INSERT INTO planilla_es VALUES (281474976907264,'2014-01-29',6563700,3,360.28,'1-2014');

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
  CONSTRAINT `0_2033` FOREIGN KEY (`PLANILLA_ID`) REFERENCES `planilla_es` (`PLANILLA_ES_ID`),
  CONSTRAINT `0_2034` FOREIGN KEY (`FACTURA_ID`) REFERENCES `factura` (`FACTURA_ID`)
) TYPE=InnoDB;

--
-- Dumping data for table `movimiento_caja`
--

INSERT INTO movimiento_caja VALUES (316659349028864,5,1,'pago contado',422212465295360,'2013-03-25','EFECTIVO',3343427,55.61,NULL,281474976874496,'Factura Cliente-Tipo A',1,'3-2013');
INSERT INTO movimiento_caja VALUES (316659349061632,6,1,'pago contado',422212465328129,'2013-03-25','EFECTIVO',3475531,22.99,NULL,281474976874496,'Factura Cliente-Tipo A',1,'3-2013');
INSERT INTO movimiento_caja VALUES (316659349061633,7,1,'pago contado',422212465328131,'2013-03-25','EFECTIVO',3479630,9.8,NULL,281474976874496,'Factura Cliente-Tipo B',1,'3-2013');
INSERT INTO movimiento_caja VALUES (316659349061634,9,1,'pago factura',422212465360898,'2013-03-27','EFECTIVO',3481673,150,NULL,281474976874496,'Factura Cliente-Tipo B',1,'3-2013');
INSERT INTO movimiento_caja VALUES (316659349061635,8,1,'pago factura',422212465360898,'2013-03-26','EFECTIVO',3483861,100,NULL,281474976874496,'Factura Cliente-Tipo B',1,'3-2013');
INSERT INTO movimiento_caja VALUES (316659349094400,4,1,'pago contado',422212465360896,'2013-03-25','EFECTIVO',3540619,37.03,NULL,281474976874496,'Factura Cliente-Tipo A',1,'3-2013');
INSERT INTO movimiento_caja VALUES (316659349094401,3,1,'pago contado',422212465360897,'2013-03-25','EFECTIVO',3542665,195.05,NULL,281474976874496,'Factura Cliente-Tipo B',1,'3-2013');
INSERT INTO movimiento_caja VALUES (316659349159936,1,1,'pago proveedor',457396837515264,'2013-03-08','EFECTIVO',6364959,150,NULL,281474976874496,'Factura Proveedor',0,'3-2013');
INSERT INTO movimiento_caja VALUES (316659349159937,2,1,'2 pago proveedor',457396837515264,'2013-03-15','EFECTIVO',6364992,120,NULL,281474976874496,'Factura Proveedor',0,'3-2013');
INSERT INTO movimiento_caja VALUES (316659349192704,10,1,'pago',422212465459200,'2013-03-27','EFECTIVO',6499384,50,NULL,281474976874496,'Factura Cliente-Tipo A',1,'3-2013');
INSERT INTO movimiento_caja VALUES (316659349225472,11,1,'pago contado',422212465491969,'2014-01-29','EFECTIVO',6558276,9.8,NULL,281474976907264,'Factura Cliente-Tipo A',1,'1-2014');
INSERT INTO movimiento_caja VALUES (316659349258240,12,1,'pago contado',422212465524736,'2014-01-29','EFECTIVO',6632337,9.8,NULL,NULL,'Factura Cliente-Tipo A',1,'1-2014');
INSERT INTO movimiento_caja VALUES (316659349291008,13,1,'pago contado',422212465557504,'2014-07-30','EFECTIVO',7014671,37.03,NULL,NULL,'Factura Cliente-Tipo A',1,'7-2014');
INSERT INTO movimiento_caja VALUES (316659349291009,14,1,'pago',422212465557504,'2014-07-30','EFECTIVO',7021722,100,NULL,NULL,'Factura Cliente-Tipo A',1,'7-2014');
INSERT INTO movimiento_caja VALUES (316659349291010,15,1,'pago contado',422212465557505,'2014-07-30','EFECTIVO',7032336,37.03,NULL,NULL,'Factura Cliente-Tipo A',1,'7-2014');

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
  CONSTRAINT `0_2036` FOREIGN KEY (`PRODUCTO_ID`) REFERENCES `producto` (`PRODUCTO_ID`),
  CONSTRAINT `0_2037` FOREIGN KEY (`FACTURA_ID`) REFERENCES `factura` (`FACTURA_ID`)
) TYPE=InnoDB;

--
-- Dumping data for table `item_factura`
--

INSERT INTO item_factura VALUES (351843721248768,1,0,422212465295360,3343417,1,32.9,32.9,211106232631305);
INSERT INTO item_factura VALUES (351843721248769,1,0,422212465295360,3343416,0,8.1,8.1,211106232533037);
INSERT INTO item_factura VALUES (351843721248770,1,0,422212465295360,3343415,0,4.96,4.96,211106232533039);
INSERT INTO item_factura VALUES (351843721281536,1,0,422212465328128,3474467,0,2.7,2.7,211106232762399);
INSERT INTO item_factura VALUES (351843721281537,1,0,422212465328129,3475524,1,19,19,211106232664126);
INSERT INTO item_factura VALUES (351843721281538,1,0,422212465328130,3477578,0,9.8,9.8,211106232533037);
INSERT INTO item_factura VALUES (351843721281539,1,0,422212465328130,3477577,0,195.05,195.05,211106232631297);
INSERT INTO item_factura VALUES (351843721281540,1,0,422212465328131,3479622,0,9.8,9.8,211106232533037);
INSERT INTO item_factura VALUES (351843721281541,1,0,422212465328132,3481664,0,3.69,3.69,211106233352192);
INSERT INTO item_factura VALUES (351843721281542,1,0,422212465328133,3483824,0,9.8,9.8,211106232533037);
INSERT INTO item_factura VALUES (351843721281543,1,0,422212465328134,3483850,0,9.8,9.8,211106232533037);
INSERT INTO item_factura VALUES (351843721314304,1,0,422212465360896,3540606,1,30.6,30.6,211106232631315);
INSERT INTO item_factura VALUES (351843721314305,1,0,422212465360897,3542653,0,195.05,195.05,211106232631297);
INSERT INTO item_factura VALUES (351843721314306,1,0,422212465360898,3545308,0,3.69,3.69,211106233352192);
INSERT INTO item_factura VALUES (351843721347072,2,10,422212465393664,6172594,1.2,46.22,42.8,211106232664097);
INSERT INTO item_factura VALUES (351843721347073,1,0,422212465393664,6172595,0.3,9.18,30.6,211106232631315);
INSERT INTO item_factura VALUES (351843721347074,1,0,422212465393665,6174764,0,9.8,9.8,211106232533037);
INSERT INTO item_factura VALUES (351843721347075,30,0,457396837515264,6174884,0,254.7,8.49,211106232533037);
INSERT INTO item_factura VALUES (351843721379840,3,0,422212465426432,6314332,0,24.3,8.1,211106232533037);
INSERT INTO item_factura VALUES (351843721379841,10,0,457396837548032,6314997,8.5,514.25,60.5,211106232631319);
INSERT INTO item_factura VALUES (351843721412608,15,0,422212465459200,6498567,0,121.5,8.1,211106232533037);
INSERT INTO item_factura VALUES (351843721412609,30,1,457396837580800,6499235,0,3108.7,104.67,211106232598539);
INSERT INTO item_factura VALUES (351843721445376,1,0,422212465491968,6557196,1,51.79,51.79,211106232664097);
INSERT INTO item_factura VALUES (351843721445377,1,0,422212465491969,6558260,0,8.1,8.1,211106232533037);
INSERT INTO item_factura VALUES (351843721478144,1,0,422212465524736,6632319,0,8.1,8.1,211106232533037);
INSERT INTO item_factura VALUES (351843721510912,1,0,422212465557504,7014650,1,30.6,30.6,211106232631315);
INSERT INTO item_factura VALUES (351843721510913,1,0,422212465557505,7032318,1,30.6,30.6,211106232631315);

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
  CONSTRAINT `0_2039` FOREIGN KEY (`LOCALIDAD_ID`) REFERENCES `localidad` (`LOCALIDAD_ID`)
) TYPE=InnoDB;

--
-- Dumping data for table `comercio`
--

INSERT INTO comercio VALUES (492581209276416,'-','30-30310394-0','Muñiz 888',196625,'2013-03-06',70368744210432,'Fiambreria 828',15,10,9,'-');

