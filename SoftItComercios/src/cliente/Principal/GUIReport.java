package cliente.Principal;

import java.sql.Date;
import java.util.Vector;

import javax.swing.JDialog;

import persistencia.domain.Cliente;
import persistencia.domain.Comercio;
import persistencia.domain.FacturaCliente;
import persistencia.domain.Proveedor;
import reports.JasperReports;

import common.Utils;

import dori.jasper.engine.JRException;
import dori.jasper.engine.JasperPrint;
import dori.jasper.view.JRViewer;

public class GUIReport extends JDialog{
	private static final long serialVersionUID = 1L;
	
	public GUIReport(JDialog parent,int codRep, Vector datos, String titulo,String dato1,String leyenda) throws Exception{
		super(parent,true);
		this.setSize(new java.awt.Dimension(700,570));
		this.setResizable(false);
		this.setLocationRelativeTo(parent);
		this.setTitle("Vista Previa de Impresión");
		JasperPrint report=null;
		String mensaje="No existen productos para efectuar la impresión";
		if(codRep==1)
			report = JasperReports.listarTodosProductosBajoStock(datos,titulo);
		else if(codRep==2)
			report = JasperReports.listarProductosProveedorBajoStock(datos,titulo);
		else if(codRep==8)
			report = JasperReports.listarTodosProductosDisponibles(datos,titulo,dato1,leyenda);
		else if(codRep==9)
			report = JasperReports.listarTodosProductosDisponiblesPrecio(datos,titulo,dato1,leyenda);
		else if(codRep==10)
			report = JasperReports.listarProductosProveedorDisponibles(datos,titulo,dato1,leyenda);
		else if(codRep==11)
			report = JasperReports.listarProductosProveedorDisponiblesPrecio(datos,titulo,dato1,leyenda);
		if(datos.size()>0){
			JRViewer jrv=null;
			try {
				jrv = new JRViewer(report);
			} catch (JRException ex) {
				Utils.manejoErrores(ex,"Error en GUIReport. Reportes 1 2 8 9 10 11");
			}
			this.getContentPane().add(jrv);
			Utils.show(this);
		}else{
			Utils.advertenciaUsr(parent,mensaje);
		}
	}

	public GUIReport(JDialog parent,int codRep,Vector datos,String titulo) throws Exception{
		super(parent,true);
		this.setSize(new java.awt.Dimension(700,570));
		this.setResizable(false);
		this.setLocationRelativeTo(parent);
		this.setTitle("Vista Previa de Impresión");
		JasperPrint report=null;
		String mensaje="";
		if(codRep==12){
			mensaje="No existen clientes para efectuar la impresión";
			report = JasperReports.listarTodosClientes(datos, titulo);
		}
		if(codRep==13){
			mensaje="No existen proveedores para efectuar la impresión";
			report = JasperReports.listarTodosProveedores(datos, titulo);
		}
		if(datos.size()>0){
			JRViewer jrv=null;
			try {
				jrv = new JRViewer(report);
			} catch (JRException ex) {
				Utils.manejoErrores(ex,"Error en GUIReport. Reportes 12 13");
			}
			this.getContentPane().add(jrv);
			Utils.show(this);
		}else{
			Utils.advertenciaUsr(parent,mensaje);
		}
	}

	public GUIReport(JDialog parent,int codRep,String nombre,String cuit,String ingBrutos,String tel,String direccion,String nLoc) throws Exception{
		super(parent,true);
		this.setSize(new java.awt.Dimension(700,570));
		this.setResizable(false);
		this.setLocationRelativeTo(parent);
		this.setTitle("Vista Previa de Impresión");
		JasperPrint report=null;
		if(codRep==15){
			report = JasperReports.tarjetaComercio(nombre, cuit,ingBrutos, tel, direccion, nLoc);
			JRViewer jrv=null;
			try {
				jrv = new JRViewer(report);
			} catch (JRException ex) {
				Utils.manejoErrores(ex,"Error en GUIReport. Reportes 15");
			}
			this.getContentPane().add(jrv);
			Utils.show(this);
		}	
	}

	public GUIReport(JDialog parent,int codRep,Vector productos, Vector cantProd, Vector kilosProd, Vector prUnit,Vector descuentos, Vector prTotal,
			String nroFact, Date fecha, Comercio distrib,Proveedor proveedor,double importeAux, double importeTotal) throws Exception{
		super(parent,true);
		this.setSize(new java.awt.Dimension(700,570));
		this.setResizable(false);
		this.setLocationRelativeTo(parent);
		this.setTitle("Vista Previa de Impresión");
		JasperPrint report=null;
		if(codRep==6){
			report = JasperReports.facturarProveedor(productos,cantProd,kilosProd,prUnit,descuentos,prTotal,nroFact,fecha,distrib,proveedor,importeAux,importeTotal);
			JRViewer jrv=null;
			try {
				jrv = new JRViewer(report);
			} catch (JRException ex) {
				Utils.manejoErrores(ex,"Error en GUIReport. Reportes 6");
			}
			this.getContentPane().add(jrv);
			Utils.show(this);
		}	
	}

	public GUIReport(JDialog parent,int codRep,Vector productos,Vector cantProd,Vector kilosProd, Vector prUnit,Vector descuentos,Vector prTotal,String nroComprob, Date fechaFact,
			Comercio dist, Cliente cte, String iva, String condVta,String remitoNro,String ingrBrutos,String tipoFact,double subtotl,String impIva,double iTotal){
		super(parent,true);
		this.setSize(new java.awt.Dimension(700,570));
		this.setResizable(false);
		this.setLocationRelativeTo(parent);
		this.setTitle("Vista Previa de Impresión");
		JasperPrint report=null;
		if(codRep==4)
			report = JasperReports.remitoCliente(productos,cantProd,kilosProd,prUnit,descuentos,prTotal,nroComprob,fechaFact,dist,cte,iTotal);
		if(codRep==5)
			report = JasperReports.facturarCliente(productos,cantProd,kilosProd,prUnit,descuentos,prTotal,fechaFact,dist,cte,iva,condVta,remitoNro,ingrBrutos,tipoFact,subtotl,impIva,iTotal);
			JRViewer jrv=null;
			try {
				jrv = new JRViewer(report);
			} catch (JRException ex) {
				Utils.manejoErrores(ex,"Error en GUIReport. Reportes 4 5");
			}
			this.getContentPane().add(jrv);
			Utils.show(this);
	}
	
	public GUIReport(JDialog parent,int codRep,Vector productos,Vector cantProd,Vector kilosProd, Vector prUnit,Vector descuentos,Vector prTotal,FacturaCliente factura,String nroComprob, Date fechaFact,
			Comercio dist, Cliente cte, double iTotal){
			super(parent,true);
			this.setSize(new java.awt.Dimension(700,570));
			this.setResizable(false);
			this.setLocationRelativeTo(parent);
			this.setTitle("Vista Previa de Impresión");
			JasperPrint report=null;
			if(codRep==3) 
				report = JasperReports.detallarCompraFactCliente(productos,cantProd,kilosProd,prUnit,descuentos,prTotal,factura,nroComprob,fechaFact,dist,cte,iTotal);
				JRViewer jrv=null;
				try {
					jrv = new JRViewer(report);
				} catch (JRException ex) {
					Utils.manejoErrores(ex,"Error en GUIReport. Reportes 3");
				}
				this.getContentPane().add(jrv);
				Utils.show(this);
		}

	public GUIReport(JDialog parent,int codRep,Vector movEntrada, Vector facts, double ingR,Vector movSalidas,int nroPlanilla, Date fecha, double saldoAnt, double saldoActual) throws Exception {
		super(parent,true);
		this.setSize(new java.awt.Dimension(700,570));
		this.setResizable(false);
		this.setLocationRelativeTo(parent);
		this.setTitle("Vista Previa de Impresión");
		JasperPrint report=null;
		if(codRep==7){
			report = JasperReports.generarBalance(movEntrada,facts,ingR,movSalidas,nroPlanilla, fecha, saldoAnt,saldoActual);
		}
		JRViewer jrv=null;
		try {
			jrv = new JRViewer(report);
		} catch (JRException ex) {
			Utils.manejoErrores(ex,"Error en GUIReport. Reportes 7");
		}
		this.getContentPane().add(jrv);
		Utils.show(this);
	}

	public GUIReport(JDialog parent,int codRep,Comercio dist, String titulo, Vector detalleItImpr,	Vector fecha, Vector debe, Vector haber,Vector saldo,String leyenda) {
		super(parent,true);
		this.setSize(new java.awt.Dimension(700,570));
		this.setResizable(false);
		this.setLocationRelativeTo(parent);
		this.setTitle("Vista Previa de Impresión");
		JasperPrint report=null;
		String mensaje="No se puede efectuar la impresión";
		if(codRep==14)
			report = JasperReports.detallarCuentaCliente(dist, titulo, detalleItImpr, fecha, debe, haber, saldo, leyenda);
		if(detalleItImpr.size()>0){
			JRViewer jrv=null;
			try {
				jrv = new JRViewer(report);
			} catch (JRException ex) {
				Utils.manejoErrores(ex,"Error en GUIReport. Reportes 14");
			}
			this.getContentPane().add(jrv);
			Utils.show(this);
		}else{
			Utils.advertenciaUsr(parent,mensaje);
		}
	}	
	
	public GUIReport(JDialog parent,int codRep,Comercio dist, String titulo, Vector clientes,Vector fechas,Vector saldoFavor,Vector adeudado) {
		super(parent,true);
		this.setSize(new java.awt.Dimension(700,570));
		this.setResizable(false);
		this.setLocationRelativeTo(parent);
		this.setTitle("Vista Previa de Impresión");
		JasperPrint report=null;
		String mensaje="No se puede efectuar la impresión";
		if(codRep==16)
			report = JasperReports.listarDeudaClientes(dist, titulo, clientes,fechas,saldoFavor,adeudado);
		if(clientes.size()>0){
			JRViewer jrv=null;
			try {
				jrv = new JRViewer(report);
			} catch (JRException ex) {
				Utils.manejoErrores(ex,"Error en GUIReport. Reportes 16");
			}
			this.getContentPane().add(jrv);
			Utils.show(this);
		}else{
			Utils.advertenciaUsr(parent,mensaje);
		}
	}	
	
	public GUIReport(JDialog parent,int codRep,Comercio dist, String titulo, String periodo, Object[][] datos,	String neto, String iva, String total) {
		super(parent,true);
		this.setSize(new java.awt.Dimension(700,570));
		this.setResizable(false);
		this.setLocationRelativeTo(parent);
		this.setTitle("Vista Previa de Impresión");
		JasperPrint report=null;
		String mensaje="No se puede efectuar la impresión";
		if(codRep==18)
			report = JasperReports.mostrarLibroIva(dist, titulo, periodo,datos, neto,iva,total);
		if(datos.length>0){
			JRViewer jrv=null;
			try {
				jrv = new JRViewer(report);
			} catch (JRException ex) {
				Utils.manejoErrores(ex,"Error en GUIReport. Reportes 18");
			}
			this.getContentPane().add(jrv);
			Utils.show(this);
		}else{
			Utils.advertenciaUsr(parent,mensaje);
		}
	}

	public GUIReport(JDialog parent,int codRep, Comercio dist, String titulo, int elems, int[] codigos, String[] productos, String[] proveedores, int[] cantidades, double[] kilos) {
		super(parent,true);
		this.setSize(new java.awt.Dimension(700,570));
		this.setResizable(false);
		this.setLocationRelativeTo(parent);
		this.setTitle("Vista Previa de Impresión");
		JasperPrint report=null;
		String mensaje="No se puede efectuar la impresión";
		if(codRep==17)
			report = JasperReports.listarProductosFacturados(dist, titulo, elems,codigos, productos,proveedores,cantidades,kilos);
		if(elems>0){
			JRViewer jrv=null;
			try {
				jrv = new JRViewer(report);
			} catch (JRException ex) {
				Utils.manejoErrores(ex,"Error en GUIReport. Reportes 17");
			}
			this.getContentPane().add(jrv);
			Utils.show(this);
		}else{
			Utils.advertenciaUsr(parent,mensaje);
		}
	}

	public GUIReport(JDialog parent,int codRep, int nroPlanilla,int cantProdEncontrados,Long[] codigos, String[] productos, String[] proveedores, int[] cantidades, double[] kilos, int[] stUnid, double[] stKilo, Date fecha) {
		super(parent,true);
		this.setSize(new java.awt.Dimension(700,570));
		this.setResizable(false);
		this.setLocationRelativeTo(parent);
		this.setTitle("Vista Previa de Impresión");
		JasperPrint report=null;
		String mensaje="No se puede efectuar la impresión, no hay productos disponibles";
		if(codRep==19)
			report = JasperReports.listarProductosFacturados(nroPlanilla,cantProdEncontrados,codigos, productos, proveedores, cantidades, kilos, stUnid, stKilo,fecha);
		if(cantProdEncontrados>0){
			JRViewer jrv=null;
			try {
				jrv = new JRViewer(report);
			} catch (JRException ex) {
				Utils.manejoErrores(ex,"Error en GUIReport. Reportes 17");
			}
			this.getContentPane().add(jrv);
			Utils.show(this);
		}else{
			Utils.advertenciaUsr(parent,mensaje);
		}
	}	
}
