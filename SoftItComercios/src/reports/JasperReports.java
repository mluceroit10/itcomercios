package reports;
import java.util.Date;
import java.util.Vector;

import persistencia.domain.Cliente;
import persistencia.domain.Comercio;
import persistencia.domain.Factura;
import persistencia.domain.FacturaCliente;
import persistencia.domain.MovimientoCaja;
import persistencia.domain.Producto;
import persistencia.domain.Proveedor;

import common.Utils;

import dori.jasper.engine.JRException;
import dori.jasper.engine.JasperCompileManager;
import dori.jasper.engine.JasperExportManager;
import dori.jasper.engine.JasperFillManager;
import dori.jasper.engine.JasperPrint;
import dori.jasper.engine.JasperReport;

public class JasperReports{
	
	public static String PATH_REPORT_XML = common.RootAndIp.getExtras()+"/reports/";
	public static String PATH_REPORT_PDF = common.RootAndIp.getArchivos();
	
	public static JasperPrint listarTodosProductosBajoStock(Vector productos, String titulo) throws Exception{//1 
		JasperPrint jasperPrint;
		try{
			Date fecha= new Date();
			System.setProperty("org.xml.sax.driver","org.apache.xerces.parsers.SAXParser");
			Object[][] param = { {"Titulo",titulo},{"Fecha",Utils.getStrUtilDate(fecha)},{"Institucion",Utils.Institucion()}};
			Object[][] values = new Object[productos.size()][4];;
			for (int i = 0; i < productos.size(); i++) {
				Producto prod= (Producto)productos.elementAt(i);
				int restan= prod.getStockMinimo() - prod.getStockActual();
				String resto = String.valueOf(restan);
				if(prod.isPrecioKilos()){
					if(prod.getStockKilosAct()<=prod.getStockKilosMin()){
						double diff=Utils.redondear((prod.getStockKilosMin()-prod.getStockKilosAct()),3);
						resto+=" - "+diff+" Kg.";
					}
				}
				Object[] temp = {String.valueOf(prod.getCodigo()),
						prod.getNombre(),
						prod.getProveedor().getNombre(),
						resto
				};
				values[i] = temp;
			}
			if(productos.size()==0){
				values = new Object[1][4];;
				Object[] temp ={"","NO se encontraron productos en bajo stock","",""};
				values[0] = temp;
			}
			String[] fieldXml = { "Codigo","Prod_Kilos","Proveedor","Resto"};
			jasperPrint=generarReporte("ListadoTodosProductosBajoStock", param, fieldXml, values);
			return jasperPrint;
		}catch (Exception ex){
			Utils.manejoErrores(ex,"Error en JasperReports. ListadoTodosProductosBajoStock.");
			return null;
		}
	}
	
	public static JasperPrint listarProductosProveedorBajoStock(Vector productos, String titulo) throws Exception{ //2
		JasperPrint jasperPrint;
		try{
			Date fecha= new Date();
			System.setProperty("org.xml.sax.driver","org.apache.xerces.parsers.SAXParser");
			Object[][] param = { {"Titulo",titulo},{"Fecha",Utils.getStrUtilDate(fecha)},{"Institucion",Utils.Institucion()}};
			Object[][] values = new Object[productos.size()][3];;
			for (int i = 0; i < productos.size(); i++) {
				Producto prod= (Producto)productos.elementAt(i);
				int restan= prod.getStockMinimo() - prod.getStockActual();
				String resto = String.valueOf(restan);
				if(prod.isPrecioKilos()){
					if(prod.getStockKilosAct()<=prod.getStockKilosMin()){
						double diff=Utils.redondear((prod.getStockKilosMin()-prod.getStockKilosAct()),3);
						resto+=" - "+diff+" Kg.";
					}
				}
				Object[] temp = {String.valueOf(prod.getCodigo()),
						prod.getNombre(),
						resto};
				values[i] = temp;
			}
			if(productos.size()==0){
				values = new Object[1][3];;
				Object[] temp ={"","NO se encontraron productos en bajo stock",""};
				values[0] = temp;
			}
			String[] fieldXml = { "Codigo","Prod_Kilos","Resto"};
			jasperPrint=generarReporte("ListadoProductosBajoStockProveedor", param, fieldXml, values);
			return jasperPrint;
		}catch (Exception ex){
			Utils.manejoErrores(ex,"Error en JasperReports. ListadoProductosBajoStockProveedor.");
			return null;
		}
	}	
	
	public static JasperPrint detallarCompraFactCliente(Vector productos,Vector cantidades, Vector kilos, Vector precioUnit,Vector descuentos,Vector prTotIt,FacturaCliente factura, String nroFact,Date fechaFact,
			Comercio dist, Cliente cte,double iTotal){ //3
		JasperPrint jasperPrint;
		try{
			String cpost="";
			if(dist.getLocalidad().getCodPostal()!=0) 
				cpost="("+dist.getLocalidad().getCodPostal()+")";
			String dir = dist.getDireccion()+" "+dist.getLocalidad().getNombre()+cpost;
			if(cte.getLocalidad().getCodPostal()!=0) 
				cpost="("+cte.getLocalidad().getCodPostal()+")";
			String dirCte = cte.getDireccion()+" "+cte.getLocalidad().getNombre()+cpost;
			//estado de cuenta pos  neg deuda -  a favor
			String detalleIva="";
			String importeIva = "";
			
			String tipoFact="";
			if(factura.getTipoFactura().compareTo("FacturaCliente-A")==0){
				tipoFact="A";
				detalleIva="Iva:";
				importeIva = Utils.ordenarDosDecimales(factura.getImporteAuxIva());
			}
			if(factura.getTipoFactura().compareTo("FacturaCliente-B")==0){
				tipoFact="B";
			}
			String remitoNro="";
			if(factura.getRemitoNro().compareTo("")!=0)
				remitoNro=Utils.nroFact(new Long(factura.getRemitoNro()));
			System.setProperty("org.xml.sax.driver","org.apache.xerces.parsers.SAXParser");
			Object[][] param = { {"Institucion",dist.getNombre()},{"InstDomicilio",dir},{"InstTel",dist.getTelefono()},
					             {"nroFact",nroFact},{"FechaFact",Utils.getStrUtilDate(fechaFact)},
					             {"CteNombre",cte.getNombre()},{"CteDomicilio",dirCte},{"CteCuit",cte.getCuit()},
					             {"Iva",factura.getIva()},{"CondVta",factura.getCondVenta()},
								 {"RemitoNro",remitoNro},{"IngBrutos",factura.getIngrBrutos()},
								 {"TotalFact",Utils.ordenarDosDecimales(iTotal)},
					             {"detalleIva",detalleIva},
					             {"importeIva",importeIva},
					             {"tipoFact",tipoFact}
			};
			Object[][] values = new Object[productos.size()][4];;
			for (int i = 0; i < productos.size(); i++) {
				Producto prod= (Producto)productos.elementAt(i);
				String cant=(String) cantidades.elementAt(i);
				String kiloPr=(String) kilos.elementAt(i);
				String prUnit=(String) precioUnit.elementAt(i);
				String dto=(String) descuentos.elementAt(i);
				String prtotal=(String) prTotIt.elementAt(i);
				String kgs="";
				if(prod.isPrecioKilos()){
            		kgs=kiloPr;
            	}
				if(dto.compareTo("0")==0)dto="";
				else dto=dto+" %";
				String descr=prod.getCodigo()+"-"+prod.getNombre();
				Object[] temp = {cant,kgs,descr,prUnit,dto,prtotal};
				values[i] = temp;
			}
			String[] fieldXml = { "Cant","Kilos","Producto","PUnit","Desc","PTotal"};
			jasperPrint=generarReporte("DetalleCompraFactCliente", param, fieldXml, values);
			return jasperPrint;
		}catch (Exception ex){
			Utils.manejoErrores(ex,"Error en JasperReports. DetalleCompraFactCliente.");
			return null;
		}
	}
	
	public static JasperPrint remitoCliente(Vector productos,Vector cantidades, Vector kilos,Vector precioUnit,Vector descuentos,Vector prTotIt, String nroRemito,Date fechaFact,
			Comercio dist, Cliente cte,double iTotal){ //4
		JasperPrint jasperPrint;
		try{
			String fecha=Utils.getStrUtilDate(fechaFact);
			String cpost="";
			if(dist.getLocalidad().getCodPostal()!=0) 
				cpost="("+dist.getLocalidad().getCodPostal()+")";
			String dir = dist.getDireccion()+" "+dist.getLocalidad().getNombre()+cpost;
			if(cte.getLocalidad().getCodPostal()!=0) 
				cpost="("+cte.getLocalidad().getCodPostal()+")";
			String dirCte = cte.getDireccion()+" "+cte.getLocalidad().getNombre()+cpost;
			System.setProperty("org.xml.sax.driver","org.apache.xerces.parsers.SAXParser");
			Object[][] param = { {"Institucion",dist.getNombre()},{"InstDomicilio",dir},{"InstTel",dist.getTelefono()},
								 {"nroRemito",nroRemito},{"FechaFact",fecha},
					  			 {"CteNombre",cte.getNombre()},{"CteDomicilio",dirCte},
					             {"TotalFact",Utils.ordenarDosDecimales(iTotal)}
			};
			Object[][] values = new Object[productos.size()][4];;
			for (int i = 0; i < productos.size(); i++) {
				Producto prod= (Producto)productos.elementAt(i);
				String cant=(String) cantidades.elementAt(i);
				String kiloPr=(String) kilos.elementAt(i);
				String prUnit=(String) precioUnit.elementAt(i);
				String dto=(String) descuentos.elementAt(i);
				String prtotal=(String) prTotIt.elementAt(i);
				String kgs="";
				if(prod.isPrecioKilos()){
            		kgs=kiloPr;
            	}
				if(dto.compareTo("0")==0)dto="";
				else dto=dto+" %";
				String descr=prod.getCodigo()+"-"+prod.getNombre();
				Object[] temp = {cant,kgs,descr,prUnit,dto,prtotal};
				values[i] = temp;
			}
			String[] fieldXml = { "Cant","Kilos","Producto","PUnit","Desc","PTotal"};
			jasperPrint=generarReporte("RemitoCliente", param, fieldXml, values);
			return jasperPrint;
		}catch (Exception ex){
			Utils.manejoErrores(ex,"Error en JasperReports. RemitoCliente.");
			return null;
		}
	}
	
	public static JasperPrint facturarCliente(Vector productos,Vector cantidades,Vector kilos, Vector precioUnit,Vector descuentos,Vector prTotIt, Date fechaFact,
			Comercio dist, Cliente cte, String iva, String condVta,String remitoNro,String ingrBrutos,String tipoFact,double subtotl,String impIva,double iTotal){//5
		JasperPrint jasperPrint;
		try{
			String conSubtotal="";
			String importeSubtotal="";
			String conIva="";
			if(tipoFact.compareTo("A")==0){
				conSubtotal="Subtotal: $";
				importeSubtotal=Utils.ordenarDosDecimales(subtotl);
				conIva="I.V.A.: 21%";
			}
			if(remitoNro.compareTo("")!=0)
				remitoNro=Utils.nroFact(new Long(remitoNro));
			System.setProperty("org.xml.sax.driver","org.apache.xerces.parsers.SAXParser");
			Object[][] param = { 
					             {"FechaFact",Utils.getStrUtilDate(fechaFact)},
					             {"CteNombre",cte.getNombre()},
					             {"CteDomicilio",cte.getDireccion()},{"CteLoc",cte.getLocalidad().getNombre()},{"CteProv",cte.getLocalidad().getProvincia().getNombre()},
					             {"CteCuit",cte.getCuit()},
					             {"Iva",iva},{"CondVta",condVta},{"RemitoNro",remitoNro},{"IngBrutos",ingrBrutos},
					             {"ConSubtotal",conSubtotal},{"ImporteSubtotal",importeSubtotal},
								 {"ConIva",conIva},{"TotalIva",impIva},
								 {"TotalFact",Utils.ordenarDosDecimales(iTotal)}
									};
			Object[][] values = new Object[20][4];;
			for (int i = 0; i < productos.size(); i++) {
				Producto prod= (Producto)productos.elementAt(i);
				String cant=(String) cantidades.elementAt(i);
				String kiloPr=(String) kilos.elementAt(i);
				String prUnit=(String) precioUnit.elementAt(i);
				String dto=(String) descuentos.elementAt(i);
				String prtotal=(String) prTotIt.elementAt(i);
				String kgs="";
				if(prod.isPrecioKilos()){
            		kgs=kiloPr;
            	}
				if(dto.compareTo("0")==0)dto="";
				else dto=dto+" %";
				String descr=prod.getCodigo()+"-"+prod.getNombre();
				Object[] temp = {cant,kgs,descr,prUnit,dto,prtotal};
				values[i] = temp;
			}
			if(productos.size()<20){
				for (int j = productos.size(); j < 20; j++) {
					Object[] temp = {"","","","","",""};
					values[j] = temp;
				}
			}
			String[] fieldXml = { "Cant","Kilos","Producto","PUnit","Desc","PTotal"};
			jasperPrint=generarReporte("FacturaCliente", param, fieldXml, values);
			return jasperPrint;
		}catch (Exception ex){
			Utils.manejoErrores(ex,"Error en JasperReports. FacturaCliente.");
			return null;
		}
	}
	
	public static JasperPrint facturarProveedor(Vector productos,Vector cantidades,Vector kilos, Vector precioUnit, Vector descuentos,Vector prTotIt,String nroFact,Date fechaFact,
			Comercio dist, Proveedor prov,double impAux,double iTotal){ //6
		JasperPrint jasperPrint;
		try{
			String cpost="";
			if(dist.getLocalidad().getCodPostal()!=0) 
				cpost="("+dist.getLocalidad().getCodPostal()+")";
			String dir = dist.getDireccion()+" "+dist.getLocalidad().getNombre()+cpost;
			System.setProperty("org.xml.sax.driver","org.apache.xerces.parsers.SAXParser");
			Object[][] param = { {"nroFact",nroFact},{"FechaFact",Utils.getStrUtilDate(fechaFact)},
					{"ProvNombre",prov.getNombre()},
					{"Institucion",dist.getNombre()},{"InstDomicilio",dir},{"InstCuit",dist.getCuit()},
					{"ImpAux",Utils.ordenarDosDecimales(impAux)},{"TotalFact",Utils.ordenarDosDecimales(iTotal)}
			};
			Object[][] values = new Object[productos.size()][4];;
			for (int i = 0; i < productos.size(); i++) {
				Producto prod= (Producto)productos.elementAt(i);
				String cant=(String) cantidades.elementAt(i);
				String kiloPr=(String) kilos.elementAt(i);
				String prUnit=(String) precioUnit.elementAt(i);
				String dto=(String) descuentos.elementAt(i);
				String prtotal=(String) prTotIt.elementAt(i);
				String kgs="";
				if(prod.isPrecioKilos()){
            		kgs=kiloPr;
            	}
				if(dto.compareTo("0")==0)dto="";
				else dto=dto+" %";
				String descr=prod.getCodigo()+"-"+prod.getNombre();
				Object[] temp = {cant,kgs,descr,prUnit,dto,prtotal};
				values[i] = temp;
			}
			String[] fieldXml = { "Cant","Kilos","Producto","PUnit","Desc","PTotal"};
			jasperPrint=generarReporte("FacturaProveedor", param, fieldXml, values);
			return jasperPrint;
		}catch (Exception ex){
			Utils.manejoErrores(ex,"Error en JasperReports. FacturaProveedor.");
			return null;
		}
	}
	
	public static JasperPrint generarBalance(Vector movEntrada, Vector facts, double ingR,Vector movSalidas,int nroPl,Date fecha,double saldoAnt,double saldoNuevo) throws Exception{//7
		JasperPrint jasperPrint;
		try{
			double totalE=ingR;
			double totalS=0; 
			int capacidad=movEntrada.size()+facts.size()+movSalidas.size()+6; //6=tit-rem-subt-esp-tit-sub
			int indRenglon=0;
			Object[][] values = new Object[capacidad][2];
			Object[] tempTE = {"DETALLE DE ENTRADAS","TOTALES"};
			values[indRenglon] = tempTE;
			indRenglon++;
			Object[] tempRemitos = {"Total de ventas",Utils.ordenarDosDecimales(ingR)};
			values[indRenglon] = tempRemitos;
			indRenglon++;
			for (int i = 0; i < facts.size(); i++) {
				String importeE="";
				FacturaCliente fc= (FacturaCliente)facts.elementAt(i);
				totalE +=fc.getImporteTotal();
				importeE=Utils.ordenarDosDecimales(fc.getImporteTotal());
				String nroFact="";
				if(fc.getTipoFactura().compareTo("FacturaCliente-A")==0)
					nroFact=" FC tipo A Nº: "+Utils.nroFact(fc.getNroFactura());
				if(fc.getTipoFactura().compareTo("FacturaCliente-B")==0)
					nroFact=" FC tipo B Nº: "+Utils.nroFact(fc.getNroFactura());
				Object[] tempE = {Utils.getStrUtilDate(fc.getFechaImpresion()) +" "+ fc.getCliente().getNombre()+nroFact,importeE};
				values[indRenglon] = tempE;
				indRenglon++;
			}
			
			for (int i = 0; i < movEntrada.size(); i++) {
				String importeE="";
				MovimientoCaja mE=(MovimientoCaja)movEntrada.elementAt(i);
				totalE +=mE.getImporte();
				importeE=Utils.ordenarDosDecimales(mE.getImporte());
				String nroFact="";
				if(mE.isConFactura()) {
					if(((Factura)mE.getFactura()).getTipoFactura().compareTo("FacturaCliente-A")==0)
						nroFact=" FC tipo A Nº: "+Utils.nroFact(mE.getFactura().getNroFactura());
					if(((Factura)mE.getFactura()).getTipoFactura().compareTo("FacturaCliente-B")==0)
						nroFact=" FC tipo B Nº: "+Utils.nroFact(mE.getFactura().getNroFactura());
				/*	if(mE.getFactura().getTipoFactura().compareTo("RemitoCliente")==0)
						nroFact=" RC Nº: "+Utils.nroFact(mE.getFactura().getNroFactura());*/
				}
				Object[] tempE = {Utils.getStrUtilDate(mE.getFecha()) +" "+ mE.getDescripcion() + nroFact,importeE};
				values[indRenglon] = tempE;
				indRenglon++;
			}
			
			Object[] tempFE = {"TOTAL DE ENTRADAS",Utils.ordenarDosDecimales(Utils.redondear(totalE,2))};
			values[indRenglon] = tempFE;
			indRenglon++;
			Object[] temp = {"",""};
			values[indRenglon] = temp;
			indRenglon++;
			
			Object[] tempTS = {"DETALLE DE SALIDAS","TOTALES"};
			values[indRenglon] = tempTS;
			indRenglon++;
			for (int i = 0; i < movSalidas.size(); i++) {
				String importeS="";
				MovimientoCaja mS= (MovimientoCaja)movSalidas.elementAt(i);
				totalS +=mS.getImporte();
				importeS=Utils.ordenarDosDecimales(mS.getImporte());
				String nroFact="";
				if(mS.isConFactura()) {
					if(mS.getFactura().getTipoFactura().compareTo("FacturaProveedor")==0)
						nroFact=" FP Nº: "+Utils.nroFact(mS.getFactura().getNroFactura());
				}
				Object[] tempS = {Utils.getStrUtilDate(mS.getFecha()) +" "+ mS.getDescripcion() + nroFact,importeS};
				values[indRenglon] = tempS;
				indRenglon++;
			}
			Object[] tempFS = {"TOTAL DE SALIDAS",Utils.ordenarDosDecimales(Utils.redondear(totalS,2))};
			values[indRenglon] = tempFS;
			
			
			String[] fieldXml = { "Descripcion","Monto"};
			double suma1=saldoAnt+totalE;
			String fe = Utils.getStrUtilDate(fecha);
			String dia=fe.substring(0,2);
			String mes=fe.substring(3,5);
			String anio=fe.substring(6,10);
			Object[][] param = { {"NroPlanilla",String.valueOf(nroPl)},
					{"Dia",dia},{"Mes",mes},{"Anio",anio},
					{"TotalI" ,Utils.ordenarDosDecimales(totalE)},{"TotalE",Utils.ordenarDosDecimales(totalS)},
					{"SaldoAnt",Utils.ordenarDosDecimales(saldoAnt)},{"Suma1",Utils.ordenarDosDecimales(suma1)},
					{"Suma2",Utils.ordenarDosDecimales(saldoNuevo)},{"Institucion",Utils.Institucion()}};
			jasperPrint=generarReporte("PlanillaDeCaja", param, fieldXml, values);
			return jasperPrint;
		}catch (Exception ex){
			Utils.manejoErrores(ex,"Error en JasperReports. PlanillaDeCaja.");
			return null;
		}
	} 
	
	public static JasperPrint listarTodosProductosDisponibles(Vector productos, String titulo,String precioVta,String leyenda) throws Exception{ //8
		JasperPrint jasperPrint;
		try{
			Date fecha= new Date();
			System.setProperty("org.xml.sax.driver","org.apache.xerces.parsers.SAXParser");
			Object[][] param = { {"Titulo",titulo},{"Fecha",Utils.getStrUtilDate(fecha)},{"Institucion",Utils.Institucion()},{"Leyenda",leyenda}};
			Object[][] values = new Object[productos.size()][5];;
			for (int i = 0; i < productos.size(); i++) {
				Producto prod= (Producto)productos.elementAt(i);
				String pent = Utils.ordenarDosDecimales(prod.getPrecioEntrada());
				String prEV="";
				if(precioVta.compareTo("CON IVA")==0)
					prEV = Utils.ordenarDosDecimales(prod.getPrecioVentaConIva());
				if(precioVta.compareTo("SIN IVA")==0)
					prEV = Utils.ordenarDosDecimales(prod.getPrecioVentaSinIva());
				String kilos="";
				if (prod.isPrecioKilos()) kilos = " - precio por KG";
				Object[] temp = {String.valueOf(prod.getCodigo()),
						prod.getNombre()+kilos,
						prod.getProveedor().getNombre(),
						pent,prEV};
				
				values[i] = temp;
			}
			if(productos.size()==0){
				values = new Object[1][5];;
				Object[] temp ={"","NO se encontraron productos registrados","","",""};
				values[0] = temp;
			}
			String[] fieldXml = { "Codigo","Prod_Kilos","Proveedor","PrecioEntrada","PrecioVenta"};
			jasperPrint=generarReporte("ListadoTodosProductos", param, fieldXml, values);
			return jasperPrint;
		}catch (Exception ex){
			Utils.manejoErrores(ex,"Error en JasperReports. ListadoTodosProductos.");
			return null;
		}
	}
	
	public static JasperPrint listarTodosProductosDisponiblesPrecio(Vector productos, String titulo, String precio,String leyenda) throws Exception{ //9
		JasperPrint jasperPrint;
		try{
			Date fecha= new Date();
			System.setProperty("org.xml.sax.driver","org.apache.xerces.parsers.SAXParser");
			Object[][] param = { {"Titulo",titulo},{"Fecha",Utils.getStrUtilDate(fecha)},{"Institucion",Utils.Institucion()},{"Precio","$ "+precio},{"Leyenda",leyenda}};
			Object[][] values = new Object[productos.size()][4];;
			for (int i = 0; i < productos.size(); i++) {
				Producto prod= (Producto)productos.elementAt(i);
				String prEV="";
				if(precio.compareTo("ENTRADA")==0)
					prEV = Utils.ordenarDosDecimales(prod.getPrecioEntrada());
				if(precio.compareTo("VENTA CON IVA")==0)
					prEV = Utils.ordenarDosDecimales(prod.getPrecioVentaConIva());
				if(precio.compareTo("VENTA SIN IVA")==0)
					prEV = Utils.ordenarDosDecimales(prod.getPrecioVentaSinIva());
				String kilos="";
				if (prod.isPrecioKilos()) kilos = " - precio por KG";
				Object[] temp = {String.valueOf(prod.getCodigo()),prod.getNombre()+kilos,prod.getProveedor().getNombre(),prEV};
				values[i] = temp;
			}
			if(productos.size()==0){
				values = new Object[1][4];;
				Object[] temp ={"","NO se encontraron productos registrados","","",""};
				values[0] = temp;
			}
			String[] fieldXml = { "Codigo","Prod_Kilos","Proveedor","PrecioEV"};
			jasperPrint=generarReporte("ListadoTodosProductosPrecio", param, fieldXml, values);
			return jasperPrint;
		}catch (Exception ex){
			Utils.manejoErrores(ex,"Error en JasperReports. ListadoTodosProductosPrecio.");
			return null;
		}
	}
	
	public static JasperPrint listarProductosProveedorDisponibles(Vector productos, String titulo,String precioVta,String leyenda) throws Exception{//10 
		JasperPrint jasperPrint;
		try{
			Date fecha= new Date();
			System.setProperty("org.xml.sax.driver","org.apache.xerces.parsers.SAXParser");
			Object[][] param = { {"Titulo",titulo},{"Fecha",Utils.getStrUtilDate(fecha)},{"Institucion",Utils.Institucion()},{"Leyenda",leyenda}};
			Object[][] values = new Object[productos.size()][4];;
			for (int i = 0; i < productos.size(); i++) {
				Producto prod= (Producto)productos.elementAt(i);
				String pent = Utils.ordenarDosDecimales(prod.getPrecioEntrada());
				String prEV="";
				if(precioVta.compareTo("CON IVA")==0)
					prEV = Utils.ordenarDosDecimales(prod.getPrecioVentaConIva());
				if(precioVta.compareTo("SIN IVA")==0)
					prEV = Utils.ordenarDosDecimales(prod.getPrecioVentaSinIva());
				String kilos="";
				if (prod.isPrecioKilos()) kilos = " - precio por KG";
				Object[] temp = {String.valueOf(prod.getCodigo()),
						prod.getNombre()+kilos,
						pent,prEV};
				values[i] = temp;
			}
			if(productos.size()==0){
				values = new Object[1][4];;
				Object[] temp ={"","NO se encontraron productos registrados","",""};
				values[0] = temp;
			}
			String[] fieldXml = { "Codigo","Prod_Kilos","PrecioEntrada","PrecioVenta"};
			jasperPrint=generarReporte("ListadoProductosProveedor", param, fieldXml, values);
			return jasperPrint;
		}catch (Exception ex){
			Utils.manejoErrores(ex,"Error en JasperReports. ListadoProductosProveedor.");
			return null;
		}
	}
	
	public static JasperPrint listarProductosProveedorDisponiblesPrecio(Vector productos, String titulo,String precio,String leyenda) throws Exception{//11 
		JasperPrint jasperPrint;
		try{
			Date fecha= new Date();
			System.setProperty("org.xml.sax.driver","org.apache.xerces.parsers.SAXParser");
			Object[][] param = { {"Titulo",titulo},{"Fecha",Utils.getStrUtilDate(fecha)},{"Institucion",Utils.Institucion()},{"Precio","$ "+precio},{"Leyenda",leyenda}};
			Object[][] values = new Object[productos.size()][3];;
			for (int i = 0; i < productos.size(); i++) {
				Producto prod= (Producto)productos.elementAt(i);
				String prEV="";
				if(precio.compareTo("ENTRADA")==0)
					prEV = Utils.ordenarDosDecimales(prod.getPrecioEntrada());
				if(precio.compareTo("VENTA CON IVA")==0)
					prEV = Utils.ordenarDosDecimales(prod.getPrecioVentaConIva());
				if(precio.compareTo("VENTA SIN IVA")==0)
					prEV = Utils.ordenarDosDecimales(prod.getPrecioVentaSinIva());
				String kilos="";
				if (prod.isPrecioKilos()) kilos = " - precio por KG";
				Object[] temp = {String.valueOf(prod.getCodigo()),
						prod.getNombre()+kilos,
						prEV};
				values[i] = temp;
			}
			if(productos.size()==0){
				values = new Object[1][3];;
				Object[] temp ={"","NO se encontraron productos registrados","",""};
				values[0] = temp;
			}
			String[] fieldXml = { "Codigo","Prod_Kilos","PrecioEV"};
			jasperPrint=generarReporte("ListadoProductosProveedorPrecio", param, fieldXml, values);
			return jasperPrint;
		}catch (Exception ex){
			Utils.manejoErrores(ex,"Error en JasperReports. ListadoProductosProveedorPrecio.");
			return null;
		}
	}
	
	public static JasperPrint listarTodosClientes(Vector clientes, String titulo) throws Exception{ //12
		JasperPrint jasperPrint;
		try{
			Date fecha= new Date();
			System.setProperty("org.xml.sax.driver","org.apache.xerces.parsers.SAXParser");
			Object[][] param = { {"Titulo",titulo},{"Fecha",Utils.getStrUtilDate(fecha)},{"Institucion",Utils.Institucion()}};
			Object[][] values = new Object[clientes.size()][5];;
			for (int i = 0; i < clientes.size(); i++) {
				Cliente cte= (Cliente)clientes.elementAt(i);
				String ib="";
				if(cte.getIngBrutosCl().compareTo("")!=0)ib="\r\nI.B. "+cte.getIngBrutosCl();
				Object[] temp = {cte.getNombre(),cte.getIvaCl()+"\r\n"+cte.getCuit()+ib,
						cte.getDireccion(),cte.getLocalidad().getNombre(),cte.getTelefono()};
				values[i] = temp;
			}
			if(clientes.size()==0){
				values = new Object[1][5];;
				Object[] temp ={"","NO se encontraron clientes registrados","","",""};
				values[0] = temp;
			}
			String[] fieldXml = { "Cliente","Cuit","Direccion","Localidad","Telefono"};
			jasperPrint=generarReporte("ListadoClientes", param, fieldXml, values);
			return jasperPrint;
		}catch (Exception ex){
			Utils.manejoErrores(ex,"Error en JasperReports. ListadoClientes.");
			return null;
		}
	}	
	
	public static JasperPrint listarTodosProveedores(Vector proveedores, String titulo) throws Exception{ //13
		JasperPrint jasperPrint;
		try{
			Date fecha= new Date();
			System.setProperty("org.xml.sax.driver","org.apache.xerces.parsers.SAXParser");
			Object[][] param = { {"Titulo",titulo},{"Fecha",Utils.getStrUtilDate(fecha)},{"Institucion",Utils.Institucion()}};
			Object[][] values = new Object[proveedores.size()][5];;
			for (int i = 0; i < proveedores.size(); i++) {
				Proveedor prov= (Proveedor)proveedores.elementAt(i);
				Object[] temp = {String.valueOf(prov.getCodigo()),prov.getNombre(),
						prov.getDireccion(),prov.getLocalidad().getNombre(),prov.getTelefono()};
				values[i] = temp;
			}
			if(proveedores.size()==0){
				values = new Object[1][5];;
				Object[] temp ={"","NO se encontraron proveedores registrados","","",""};
				values[0] = temp;
			}
			String[] fieldXml = { "Codigo","Proveedor","Direccion","Localidad","Telefono"};
			jasperPrint=generarReporte("ListadoProveedores", param, fieldXml, values);
			return jasperPrint;
		}catch (Exception ex){
			Utils.manejoErrores(ex,"Error en JasperReports. ListadoProveedores.");
			return null;
		}
	}	
	
	public static JasperPrint detallarCuentaCliente(Comercio dist,String titulo, Vector detalle,Vector fecha, Vector debe, Vector haber, Vector saldo,String leyendaPie){ //14 
		JasperPrint jasperPrint;
		try{
			Date hoy= new Date();
			String fechaHoy=Utils.getStrUtilDate(hoy);
			//estado de cuenta neg deuda - pos a favor
			System.setProperty("org.xml.sax.driver","org.apache.xerces.parsers.SAXParser");
			Object[][] param = { {"Institucion",dist.getNombre()},{"Titulo",titulo},{"FechaHoy",fechaHoy},{"LeyendaPie",leyendaPie}};
			Object[][] values = new Object[detalle.size()][5];;
			for (int i = 0; i < detalle.size(); i++) {
				String det = (String)detalle.elementAt(i);
				String fe = (String)fecha.elementAt(i);
				String de = (String)debe.elementAt(i);
				String ha = (String)haber.elementAt(i);
				String sa = (String)saldo.elementAt(i);
				Object[] temp = {det,fe,de,ha,sa};
				values[i] = temp;
			}
			String[] fieldXml = {"DetalleIt","Fecha","Debe","Haber","Saldo"};
			jasperPrint=generarReporte("DetalleCuentaCliente", param, fieldXml, values);
			return jasperPrint;
		}catch (Exception ex){
			Utils.manejoErrores(ex,"Error en JasperReports. DetalleCuentaCliente.");
			return null;
		}
	}
	
	public static JasperPrint tarjetaComercio(String nombre, String cuit,String ingBrutos, String tel, String dir,String loc){//15 
		JasperPrint jasperPrint;
		try{
			System.setProperty("org.xml.sax.driver","org.apache.xerces.parsers.SAXParser");
			Object[][] param = { {"Nombre",nombre},{"Cuit",cuit},{"IngBrutos",ingBrutos},{"Tel",tel},{"Dir",dir},{"Loc",loc}};
			Object[][] values = new Object[1][0];
			String[] fieldXml = {};
			jasperPrint=generarReporte("TarjetaComercio", param, fieldXml, values);
			return jasperPrint;
		}catch (Exception ex){
			Utils.manejoErrores(ex,"Error en JasperReports. TarjetaComercio.");
			return null;
		}
	}
	
	public static JasperPrint listarDeudaClientes(Comercio dist,String titulo, Vector cliente,Vector fechas, Vector favor, Vector debe){//16 
		JasperPrint jasperPrint;
		try{
			Date hoy= new Date();
			String fechaHoy=Utils.getStrUtilDate(hoy);
			//estado de cuenta neg deuda - pos a favor
			System.setProperty("org.xml.sax.driver","org.apache.xerces.parsers.SAXParser");
			Object[][] param = { {"Institucion",dist.getNombre()},{"Titulo",titulo},{"FechaHoy",fechaHoy}};
			Object[][] values = new Object[cliente.size()][5];;
			for (int i = 0; i < cliente.size(); i++) {
				String cte = (String)cliente.elementAt(i);
				String fuf = (String)fechas.elementAt(i);
				String fav = (String)favor.elementAt(i);
				String deb = (String)debe.elementAt(i);
				Object[] temp = {cte,fuf,fav,deb};
				values[i] = temp;
			}
			String[] fieldXml = {"Cliente","FUltFact","Favor","Debe"};
			jasperPrint=generarReporte("ListadoDeudaclientes", param, fieldXml, values);
			return jasperPrint;
		}catch (Exception ex){
			Utils.manejoErrores(ex,"Error en JasperReports. ListadoDeudaclientes.");
			return null;
		}
	}
	
	public static JasperPrint listarProductosFacturados(Comercio dist, String titulo,int elems, int[] codigos, String[] productos,String[] proveedores, int[] cantidades, double[] kilos) {//17
		JasperPrint jasperPrint;
		try{
			Date hoy= new Date();
			String fechaHoy=Utils.getStrUtilDate(hoy);
			//estado de cuenta neg deuda - pos a favor
			System.setProperty("org.xml.sax.driver","org.apache.xerces.parsers.SAXParser");
			Object[][] param = { {"Institucion",dist.getNombre()},{"Titulo",titulo},{"Fecha",fechaHoy}};
			Object[][] values = new Object[elems][5];;
			for (int i = 0; i < elems; i++) {
				String kilo="";
				if(kilos[i]>0) kilo=Utils.ordenarTresDecimales(kilos[i]);
				Object[] temp = {String.valueOf(codigos[i]),productos[i],proveedores[i],String.valueOf(cantidades[i]),kilo};
				values[i] = temp;
			}
			String[] fieldXml = {"Codigo","Prod_Kilos","Proveedor","Cantidad","Kilos"};
			jasperPrint=generarReporte("ListadoProductosFacturados", param, fieldXml, values);
			return jasperPrint;
		}catch (Exception ex){
			Utils.manejoErrores(ex,"Error en JasperReports. ListadoProductosFacturados.");
			return null;
		}		
	}

	public static JasperPrint mostrarLibroIva(Comercio dist, String titulo,String periodo, Object[][] datos, String neto, String iva, String total) {//18
		JasperPrint jasperPrint;
		try{
			//estado de cuenta neg deuda - pos a favor
			System.setProperty("org.xml.sax.driver","org.apache.xerces.parsers.SAXParser");
			String cpost="";
			if(dist.getLocalidad().getCodPostal()!=0) 
				cpost="("+dist.getLocalidad().getCodPostal()+")";
			String dir = dist.getDireccion()+" - "+dist.getLocalidad().getNombre()+cpost;
			Object[][] param = { {"Institucion",dist.getNombre()},{"InstDireccion",dir},{"InstIva","Resp. Iscripto - CUIT: "+dist.getCuit()},
					{"Titulo",titulo},{"FechaLI",periodo},{"TotalNeto",neto},{"TotalIva",iva},{"TotalTotal",total}};
			Object[][] values = datos;
			String[] fieldXml = {"Fecha","Tipo","L","PV","Nro","Cliente","Categ","Cuit","Neto","Iva","Total"};
			jasperPrint=generarReporte("LibroIva", param, fieldXml, values);
			return jasperPrint;
		}catch (Exception ex){
			Utils.manejoErrores(ex,"Error en JasperReports. LibroIva.");
			return null;
		}
	}
	
	private static JasperPrint generarReporte(String report, Object[][] param, String[] fieldXml, Object[][] values){
		JasperReport jasperReport;
		JasperPrint jasperPrint; 
		try {
			java.util.HashMap parameters = new java.util.HashMap();
			for (int i = 0; i < param.length; i++) {
				parameters.put(param[i][0], param[i][1]);
			}
			java.util.Hashtable ht = new java.util.Hashtable();
			for (int i = 0; i < fieldXml.length; i++) {
				ht.put(fieldXml[i], new Integer(i));
			}
			DataSourceJasper data = new DataSourceJasper(values, ht);
			String fileXML = PATH_REPORT_XML + report + ".xml";
			String fileJRPRINT = PATH_REPORT_PDF + report + ".pdf";
			// 1-Compilamos el archivo XML y lo cargamos en memoria			
			jasperReport = JasperCompileManager.compileReport(fileXML);
			// 2-Llenamos el reporte con la información y parámetros necesarios 
			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,data);
			JasperExportManager.exportReportToPdfFile(jasperPrint,fileJRPRINT);
			return jasperPrint;
		} catch (JRException ex) {
			Utils.manejoErrores(ex,"Error en JasperReports. generarReporte.");
			return null;
		}
	}
	
	public static void main(String[] args){
		;
	}

	public static JasperPrint listarProductosFacturados(int nroPlanilla,int cantProdEncontrados, Long[] codigos, String[] productos, String[] proveedores, int[] cantidades, double[] kilos, int[] stUnid, double[] stKilo, java.sql.Date fecha) {
			JasperPrint jasperPrint;
			try{
				//estado de cuenta neg deuda - pos a favor
				System.setProperty("org.xml.sax.driver","org.apache.xerces.parsers.SAXParser");
				Object[][] param = { {"Institucion",Utils.Institucion()},{"Titulo","Detalle de Stock Cierre de caja Nº "+nroPlanilla+" - "+Utils.getStrUtilDate(fecha)},{"Fecha",Utils.getStrUtilDate(fecha)}};  //dist.getNombre()
				Object[][] values = new Object[cantProdEncontrados][5];
				for (int i = 0; i < cantProdEncontrados; i++) {
					String comprado=String.valueOf(cantidades[i])+" Un.";
					String stock=String.valueOf(stUnid[i])+" Un.";
					if(kilos[i]>0){
						comprado=Utils.ordenarTresDecimales(kilos[i])+" Kg.";
						stock=Utils.ordenarTresDecimales(stKilo[i])+" Kg.";
					}
					Object[] temp = {String.valueOf(codigos[i]),productos[i],proveedores[i],comprado,stock};
					values[i] = temp;
				}
				String[] fieldXml = {"Codigo","Prod_Kilos","Proveedor","Cantidad","Kilos"};
				jasperPrint=generarReporte("ListadoProductosFacturados", param, fieldXml, values);
				return jasperPrint;
			}catch (Exception ex){
				Utils.manejoErrores(ex,"Error en JasperReports. ListadoProductosFacturados.");
				return null;
			}		
		}

	
	
}