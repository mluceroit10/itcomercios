package server.RealizarPlanillaES;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import persistencia.domain.Cliente;
import persistencia.domain.Factura;
import persistencia.domain.FacturaCliente;
import persistencia.domain.FacturaProveedor;
import persistencia.domain.ItemFactura;
import persistencia.domain.Localidad;
import persistencia.domain.MovimientoCaja;
import persistencia.domain.PlanillaES;
import persistencia.domain.Producto;
import persistencia.domain.Proveedor;
import persistencia.domain.Provincia;
import server.Assemblers;
import server.ManipuladorPersistencia;
import server.GestionarFacturaCliente.ControlFacturaCliente;
import server.GestionarMovimientoCaja.ControlMovimientoCaja;

import common.Utils;
import common.RealizarPlanillaES.IControlRealizarPlanillaES;

public class ControlRealizarPlanillaES implements IControlRealizarPlanillaES{
	
	private static final long serialVersionUID = 1L;
	
	public ControlRealizarPlanillaES() throws Exception{   }
	
	public void agregarPlanillaESTotal(PlanillaES p,Vector movimientos,Vector facts,Vector remitos) throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		PlanillaES lnew= Assemblers.crearPlanillaES(p);
		ControlMovimientoCaja cMC = new ControlMovimientoCaja();
		ControlFacturaCliente cFC = new ControlFacturaCliente();
		try{
			mp.initPersistencia();
			mp.hacerPersistente(lnew);
			lnew.setPeriodo(Utils.getMes(p.getFecha())+"-"+Utils.getAnio(p.getFecha()));
			// setea movimientos
			for(Iterator i = movimientos.iterator(); i.hasNext();){
				MovimientoCaja mc=(MovimientoCaja)i.next();
				MovimientoCaja mov = cMC.buscarMovimientoCajaPersistentePorId(mp,mc.getId());
				mov.setPlanilla(lnew);
			}
			// setea facturas y remitos
			Vector facturaClienteCol=new Vector();
			facturaClienteCol.addAll(remitos);
			facturaClienteCol.addAll(facts);
			for(int j = 0; j < facturaClienteCol.size();j++){
				FacturaCliente fc=(FacturaCliente)facturaClienteCol.elementAt(j);
				FacturaCliente f = cFC.buscarFacturaClientePersistentePorId(mp,fc.getId());
				f.setPlanilla(lnew);
			}
			mp.commit();
		} finally {
			mp.rollback();
		}
	}
	
	public boolean existePlanillaESNroPlanilla(int numero)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		boolean existe = false;
		try {
			mp.initPersistencia();
			String filtro = "nroPlanilla == "+numero;
			Collection pCol= mp.getObjects(PlanillaES.class,filtro);
			if (pCol.size()==1)
				existe=true;
			mp.commit();
		} finally {
			mp.rollback();
		}
		return existe;
	}
	
	public PlanillaES buscarPlanillaESNroPlanilla(int numero) throws Exception {
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		PlanillaES p = new PlanillaES();
		try {
			mp.initPersistencia();
			String filtro = "nroPlanilla == "+numero;
			Vector pCol= mp.getObjects(PlanillaES.class,filtro);
			if (pCol.size()>=1){
				PlanillaES bm = (PlanillaES)pCol.elementAt(0);
				p = Assemblers.crearPlanillaES(bm);				
				Set movimientos=new HashSet();
				for (Iterator i = bm.getMovimientosCaja().iterator(); i.hasNext(); ) {
					MovimientoCaja b = (MovimientoCaja)i.next();
					MovimientoCaja a = Assemblers.crearMovimientoCaja(b);
					movimientos.add(a);
				}
				p.setMovimientosCaja(movimientos);
			}
			mp.commit();
		} finally {
			mp.rollback();
		}
		return p;
	}
	
	public PlanillaES buscarPlanillaESNroPlanillaPersistencia(int numero) throws Exception {
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		PlanillaES p = new PlanillaES();
		try {
			mp.initPersistencia();
			String filtro = "nroPlanilla == "+numero;
			Vector pCol= mp.getObjects(PlanillaES.class,filtro);
			if (pCol.size()>=1){
				p = (PlanillaES)pCol.elementAt(0);
			}
			mp.commit();
		} finally {
			mp.rollback();
		}
		return p;
	}
	
	public PlanillaES buscarPlanillaESPersistentePorId(ManipuladorPersistencia mp,Long id) throws Exception {
		PlanillaES obj = new PlanillaES();
		String filtro = "id == "+id;
		Collection col= mp.getObjects(PlanillaES.class,filtro);
		if (col.size()>=1){
			obj = (PlanillaES)(col.toArray())[0];
		}
		return obj;
	}
	
	public PlanillaES obtenerUltimaPlanilla()throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		PlanillaES p = new PlanillaES();
		try{
			mp.initPersistencia();
			Vector planillas= mp.getAllOrdered(PlanillaES.class,"nroPlanilla descending");
			if(planillas.size()==0){
				p.setNroPlanilla(Utils.NROPLANILLAANTERIOR);
				p.setSaldo(Utils.SALDOANTERIOR);
				p.setFecha(new Timestamp(0,0,0,0,0,0,0));
			}
			else{
				 PlanillaES b = (PlanillaES)planillas.elementAt(0);
				 p.setId(b.getId());
				 p.setFecha(b.getFecha());
				 p.setNroPlanilla(b.getNroPlanilla());
				 p.setSaldo(b.getSaldo());
			}
			mp.commit();
			return p;
		} finally {
			mp.rollback();
		}
	}
	
	public Vector obtenerMovimientosCajaParaPlanilla(Timestamp fechaH)throws Exception{
		Vector movimientos = new Vector();
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		try {
			mp.initPersistencia();
			String filtro = "planilla==null";
			Vector todosMov= mp.getObjectsOrdered(MovimientoCaja.class,filtro,"codigo ascending");
			for (Iterator i = todosMov.iterator(); i.hasNext(); ) {
				MovimientoCaja b = (MovimientoCaja)i.next();
				if((b.getFecha().before(fechaH)|| b.getFecha().equals(fechaH))){
					MovimientoCaja a = Assemblers.crearMovimientoCaja(b);
					Factura fact=null;
					if (b.isConFactura()){
						if((b.getTipoFactura().compareTo("Factura Cliente-Tipo A")==0)||(b.getTipoFactura().compareTo("Factura Cliente-Tipo B")==0)||(b.getTipoFactura().compareTo("Remito Cliente")==0)){
							fact = Assemblers.crearFacturaCliente((FacturaCliente)b.getFactura());//fc;
						}
						if(b.getTipoFactura().compareTo("Factura Proveedor")==0){
							fact = Assemblers.crearFacturaProveedor((FacturaProveedor)b.getFactura());//fp;
						}	
					}
					a.setFactura(fact);
					movimientos.add(a);
				}	
			}
			mp.commit();
		} catch(Exception e) {
			e.printStackTrace();
			mp.rollback();
		}
		return movimientos;
	}
	

	public Vector obtenerFacturasClienteParaPlanilla(Timestamp fechaH)throws Exception{
		Vector facturas = new Vector();
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		try {
			mp.initPersistencia();
			String filtro = " anulada==false && (tipoFactura==\"FacturaCliente-A\" || tipoFactura==\"FacturaCliente-B\" ) && condVenta==\"CONTADO\" && planilla==null";  
			Vector facturaClientes= mp.getObjectsOrdered(FacturaCliente.class,filtro,"fechaImpresion ascending");
			for(int i=0; i<facturaClientes.size();i++){
				FacturaCliente fc = (FacturaCliente)facturaClientes.elementAt(i);
				//verif fechas
				if(fc.getFechaImpresion().before(fechaH)|| fc.getFechaImpresion().equals(fechaH)){
					FacturaCliente fcObt= Assemblers.crearFacturaCliente(fc);
					fcObt.setCliente(Assemblers.crearCliente(fc.getCliente()));
					Set elem = fc.getItems();
					Set aelem = new HashSet();
					for(Iterator j=elem.iterator();j.hasNext();){
						ItemFactura itv= (ItemFactura) j.next();
						ItemFactura it= Assemblers.crearItemFactura(itv);
						Producto p= Assemblers.crearProducto(itv.getProducto());
						Proveedor pr = Assemblers.crearProveedor(itv.getProducto().getProveedor());
						p.setProveedor(pr);
						it.setProducto(p);
						aelem.add(it);
					}
					fcObt.setItems(aelem);
					facturas.add(fcObt);
				}
			}
			mp.commit();
		} catch(Exception e) {
			e.printStackTrace();
			mp.rollback();
		}
		return facturas;
	}
	
	public Vector obtenerRemitosClienteParaPlanilla(Timestamp fechaH)throws Exception{
		Vector facturas = new Vector();
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		try {
			mp.initPersistencia();
			String filtro = " anulada==false && tipoFactura==\"RemitoCliente\" && remitoNro!=\"Facturado\" && planilla==null";  
			Vector facturaClientes= mp.getObjectsOrdered(FacturaCliente.class,filtro,"fechaImpresion ascending");
			for(int i=0; i<facturaClientes.size();i++){
				FacturaCliente fc = (FacturaCliente)facturaClientes.elementAt(i);
				if(fc.getFechaImpresion().before(fechaH)|| fc.getFechaImpresion().equals(fechaH)){
					FacturaCliente fcObt= Assemblers.crearFacturaCliente(fc);
					fcObt.setCliente(Assemblers.crearCliente(fc.getCliente()));
					Set elem = fc.getItems();
					Set aelem = new HashSet();
					for(Iterator j=elem.iterator();j.hasNext();){
						ItemFactura itv= (ItemFactura) j.next();
						ItemFactura it= Assemblers.crearItemFactura(itv);
						Producto p= Assemblers.crearProducto(itv.getProducto());
						Proveedor pr = Assemblers.crearProveedor(itv.getProducto().getProveedor());
						p.setProveedor(pr);
						it.setProducto(p);
						aelem.add(it);
					}
					fcObt.setItems(aelem);
					facturas.add(fcObt);
				}
			}
			mp.commit();
		}catch(Exception e) {
			e.printStackTrace();
			mp.rollback();
		}
		return facturas;
	}
	
	public void eliminarPlanillaES(Long id)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		try {
			mp.initPersistencia();
			PlanillaES pl = this.buscarPlanillaESPersistentePorId(mp,id);
			for(Iterator it = pl.getMovimientosCaja().iterator(); it.hasNext();){
				MovimientoCaja mc = (MovimientoCaja) it.next();
				mc.setPlanilla(null);
			}
			mp.borrar(pl);
			mp.commit();
		}finally{
			mp.rollback();
		}
	}
		
	public Vector obtenerPlanillasES(int mesLI,int anioLI)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Vector planillas = new Vector();
		try {
			mp.initPersistencia();
			String filtro="periodo==\""+mesLI+"-"+anioLI+"\"";
			Vector planillasCol= mp.getObjectsOrdered(PlanillaES.class,filtro,"nroPlanilla ascending");
			for(int i=0; i<planillasCol.size();i++){
				PlanillaES b = (PlanillaES)planillasCol.elementAt(i);
				PlanillaES a= Assemblers.crearPlanillaES(b);
				planillas.add(a);
			}	
		}finally{
			mp.rollback();
		}
		return planillas;
	}
	
	public Vector obtenerPlanillasESFiltroFecha(int mesLI,int anioLI,String fecha)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Vector planillas = new Vector();
		try {
			mp.initPersistencia();
			String filtro="periodo==\""+mesLI+"-"+anioLI+"\"";
			Vector planillasCol= mp.getObjectsOrdered(PlanillaES.class,filtro,"nroPlanilla ascending");
			for(int i=0; i<planillasCol.size();i++){
				PlanillaES b = (PlanillaES)planillasCol.elementAt(i);
				if (Utils.comienza(common.Utils.getStrUtilDate(b.getFecha()), fecha)) {
					PlanillaES a= Assemblers.crearPlanillaES(b);
					planillas.add(a);
				}
			}	
		}finally{
			mp.rollback();
		}
		return planillas;
	}
	
	public Vector obtenerPlanillasESFiltroNroPl(int mesLI,int anioLI,String nroPl)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Vector planillas = new Vector();
		try {
			mp.initPersistencia();
			String filtro="periodo==\""+mesLI+"-"+anioLI+"\"";
			Vector planillasCol= mp.getObjectsOrdered(PlanillaES.class,filtro,"nroPlanilla ascending");
			for(int i=0; i<planillasCol.size();i++){
				PlanillaES b = (PlanillaES)planillasCol.elementAt(i);
				if(Utils.comienza(String.valueOf(b.getNroPlanilla()),nroPl)){
					PlanillaES a= Assemblers.crearPlanillaES(b);
					planillas.add(a);
				}
			}	
		}finally{
			mp.rollback();
		}
		return planillas;
	}

	public PlanillaES buscarPlanillaES(Long id) throws Exception {
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		PlanillaES p = new PlanillaES();
		try {
			mp.initPersistencia();
			String filtro = "id == "+id;
			Vector pCol= mp.getObjects(PlanillaES.class,filtro);
			if (pCol.size()>=1){
				PlanillaES bm = (PlanillaES)pCol.elementAt(0);
				p=Assemblers.crearPlanillaES(bm);
				Set movimientos=new HashSet();
				for (Iterator i = bm.getMovimientosCaja().iterator(); i.hasNext(); ) {
					MovimientoCaja b = (MovimientoCaja)i.next();
					MovimientoCaja a = Assemblers.crearMovimientoCaja(b);
					movimientos.add(a);
				}
				
				p.setMovimientosCaja(movimientos);
				Set facturas=new HashSet();
				for (Iterator i = bm.getFacturas().iterator(); i.hasNext(); ) {
					FacturaCliente b = (FacturaCliente)i.next();
					FacturaCliente a = Assemblers.crearFacturaCliente(b);
					Cliente cte= Assemblers.crearCliente(b.getCliente());
					Localidad loc= Assemblers.crearLocalidad(b.getCliente().getLocalidad());
					Provincia prv= Assemblers.crearProvincia(b.getCliente().getLocalidad().getProvincia());
					loc.setProvincia(prv);
					cte.setLocalidad(loc);
					Set elem = b.getItems();
					Set aelem = new HashSet();
					for(Iterator j=elem.iterator();j.hasNext();){
						ItemFactura itv= (ItemFactura) j.next();
						ItemFactura it= Assemblers.crearItemFactura(itv);
						Producto producto= Assemblers.crearProducto(itv.getProducto());
						Proveedor pr = Assemblers.crearProveedor(itv.getProducto().getProveedor());
						producto.setProveedor(pr);
						it.setProducto(producto);
						aelem.add(it);
					}
					a.setItems(aelem);
					a.setCliente(cte);
					facturas.add(a);
				}
				p.setFacturas(facturas);
			}
			mp.commit();
		} finally {
			mp.rollback();
		}
		return p;
	}
}
