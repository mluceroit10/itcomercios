package server.GestionarMovimientoCaja;

import java.rmi.RemoteException;
import java.sql.Date;
import java.util.Collection;
import java.util.Vector;

import persistencia.domain.Factura;
import persistencia.domain.FacturaCliente;
import persistencia.domain.FacturaProveedor;
import persistencia.domain.MovimientoCaja;
import persistencia.domain.PlanillaES;
import server.Assemblers;
import server.ManipuladorPersistencia;

import common.Utils;
import common.GestionarMovimientoCaja.IControlMovimientoCaja;

public class ControlMovimientoCaja implements IControlMovimientoCaja{
	
	
	public ControlMovimientoCaja() throws RemoteException{   }
	
	public boolean agregarMovimientoCaja_Fact(MovimientoCaja mc)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		//creo objeto y seteo datos basicos
		MovimientoCaja lnew= Assemblers.crearMovimientoCaja(mc);
		//busco los objetos asociados pero persistentemente
		Factura fact= null;
		if (this.existeMovimientoCaja(mc.getCodigo()))
			return false;
		else{ 
			try{
				mp.initPersistencia();
				mp.hacerPersistente(lnew);
				lnew.setPeriodo(Utils.getMes(mc.getFecha())+"-"+Utils.getAnio(mc.getFecha()));
				if (mc.isConFactura()){
					String filtro = "id == " +mc.getFactura().getId();
					Vector facturaClienteCol=mp.getObjectsSubc(Factura.class,filtro);
					fact =(Factura) facturaClienteCol.elementAt(0);
					lnew.setFactura(fact);
					if(mc.getTipoFactura().compareTo("Factura Cliente-Tipo A")==0 || mc.getTipoFactura().compareTo("Factura Cliente-Tipo B")==0
							|| mc.getTipoFactura().compareTo("Remito Cliente")==0){
						FacturaCliente fcMod= (FacturaCliente) fact;
						fcMod.setFechaPago(mc.getFecha());
    	    			fcMod.setImporteAbonado(mc.getImporte()+fcMod.getImporteAbonado());
//    	    			 SALDO CLIENTE CASO 8 (-)
    	    			fcMod.getCliente().setDeuda(Utils.redondear(fcMod.getCliente().getDeuda()-mc.getImporte(),2));
					}
					if(mc.getTipoFactura().compareTo("Factura Proveedor")==0){
						FacturaProveedor fpMod= (FacturaProveedor) fact;
						// SALDO PROVEEDOR CASO 5 (-)
    	    			fpMod.getProveedor().setDeuda(Utils.redondear(fpMod.getProveedor().getDeuda()-mc.getImporte(),2));
					}
				}
				mp.commit();
			} finally {
				mp.rollback();
			}
			return true;
		}
	}
	
	public void eliminarMovimientoCaja(Long id)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		try {
			mp.initPersistencia();
			MovimientoCaja mcaja = this.buscarMovimientoCajaPersistentePorId(mp,id);
			if(mcaja.isConFactura()){
				if(mcaja.getTipoFactura().compareTo("Factura Cliente-Tipo A")==0 || mcaja.getTipoFactura().compareTo("Factura Cliente-Tipo B")==0
						|| mcaja.getTipoFactura().compareTo("Remito Cliente")==0){
					String filtroF = "id == " +mcaja.getFactura().getId();
					Vector facturaClienteCol=mp.getObjectsSubc(Factura.class,filtroF);
					FacturaCliente fact =(FacturaCliente) facturaClienteCol.elementAt(0);
					double imp=fact.getImporteAbonado()-mcaja.getImporte();
					if( imp < 0) imp=0;
					fact.setImporteAbonado(imp);
					String filtroMC = "factura.id == "+fact.getId();
					Vector movscaja= mp.getObjectsOrdered(MovimientoCaja.class,filtroMC,"fechaMC descending");
					Date fecha = null;
					if(movscaja.size()>1){
						MovimientoCaja mcAnt = (MovimientoCaja) movscaja.elementAt(1);
						fecha = Utils.crearFecha2(mcAnt.getFecha());
					}
					fact.setFechaPago(fecha);
					 //SALDO CLIENTE CASO 9 (-)
						fact.getCliente().setDeuda(Utils.redondear(fact.getCliente().getDeuda()+mcaja.getImporte(),2));
				}
				if(mcaja.getTipoFactura().compareTo("Factura Proveedor")==0){
					String filtroF = "id == " +mcaja.getFactura().getId();
					Vector facturaClienteCol=mp.getObjectsSubc(Factura.class,filtroF);
					FacturaProveedor fact =(FacturaProveedor) facturaClienteCol.elementAt(0);
					// SALDO PROVEEDOR CASO 6 (-)
					fact.getProveedor().setDeuda(Utils.redondear(fact.getProveedor().getDeuda()+mcaja.getImporte(),2));
				}
			}
			mp.borrar(mcaja);
			mp.commit();
		} finally {
			mp.rollback();
		}
	}
			
	public Vector obtenerMovimientosCaja()throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Vector movs = new Vector();
		try {
			mp.initPersistencia();
			Vector movscaja= mp.getAllOrdered(MovimientoCaja.class,"codigo ascending");
			for(int i=0; i<movscaja.size();i++){
				MovimientoCaja b = (MovimientoCaja)movscaja.elementAt(i);
				MovimientoCaja a= Assemblers.crearMovimientoCaja(b);
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
				if(b.getPlanilla()!=null){
					PlanillaES plv= (PlanillaES) b.getPlanilla();
					PlanillaES pl= Assemblers.crearPlanillaES(plv);
					a.setPlanilla(pl);
				}
				movs.add(a);
			}
			mp.commit();
		}  finally{
			mp.rollback();
		}
		return movs;
	}
	
	public Vector obtenerMovimientosCajaPeriodo(int mesLI,int anioLI)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Vector movs = new Vector();
		try {
			mp.initPersistencia();
			String filtro="periodo==\""+mesLI+"-"+anioLI+"\"";
			Vector movscaja= mp.getObjectsOrdered(MovimientoCaja.class,filtro,"codigo ascending");
			for(int i=0; i<movscaja.size();i++){
				MovimientoCaja b = (MovimientoCaja)movscaja.elementAt(i);
				MovimientoCaja a= Assemblers.crearMovimientoCaja(b);
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
				if(b.getPlanilla()!=null){
					PlanillaES plv= (PlanillaES) b.getPlanilla();
					PlanillaES pl= Assemblers.crearPlanillaES(plv);
					a.setPlanilla(pl);
				}
				movs.add(a);
			}
			mp.commit();
		}  finally{
			mp.rollback();
		}
		return movs;
	}
		
	public Vector obtenerMovimientosCajaFiltros(int mesLI,int anioLI,String cod,String fecha)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Vector movs = new Vector();
		try {
			mp.initPersistencia();
			String filtro="periodo==\""+mesLI+"-"+anioLI+"\"";
			Vector movscaja= mp.getObjectsOrdered(MovimientoCaja.class,filtro,"codigo ascending");
			for(int i=0; i<movscaja.size();i++){
				MovimientoCaja b = (MovimientoCaja)movscaja.elementAt(i);
				if(Utils.comienza(String.valueOf(b.getCodigo()),cod) && Utils.comienza(common.Utils.getStrUtilDate(b.getFecha()), fecha)){
					MovimientoCaja a= Assemblers.crearMovimientoCaja(b);
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
					if(b.getPlanilla()!=null){
						PlanillaES plv= (PlanillaES) b.getPlanilla();
						PlanillaES pl= Assemblers.crearPlanillaES(plv);
						a.setPlanilla(pl);
					}
					movs.add(a);
				}
			}
			mp.commit();
		}  finally{
			mp.rollback();
		}
		return movs;
	}
	
	public boolean existeMovimientoCaja(int codigo)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		boolean existe = false;
		try {
			mp.initPersistencia();
			String filtro = "codigo == "+codigo;
			Collection movs= mp.getObjects(MovimientoCaja.class,filtro);
			if (movs.size()==1)
				existe=true;
			mp.commit();
		} finally {
			mp.rollback();
		}
		return existe;
	}
		
	public MovimientoCaja buscarMovimientoCaja(int codigo) throws Exception {
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		MovimientoCaja a = new MovimientoCaja();
		try {
			mp.initPersistencia();
			String filtro = "codigo == "+codigo;
			Collection movscaja= mp.getObjects(MovimientoCaja.class,filtro);
			if (movscaja.size()>=1){
				MovimientoCaja b = (MovimientoCaja)(movscaja.toArray())[0];
				a = Assemblers.crearMovimientoCaja(b);
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
			}
			mp.commit();
		} finally {
			mp.rollback();
		}
		return a;
	}
	
	public MovimientoCaja buscarMovimientoCajaPersistentePorId(ManipuladorPersistencia mp,Long id) throws Exception {
		MovimientoCaja obj = new MovimientoCaja();
		String filtro = "id == "+id;
		Collection col= mp.getObjects(MovimientoCaja.class,filtro);
		if (col.size()>=1){
			obj = (MovimientoCaja)(col.toArray())[0];
		}
		return obj;
	}
	
	public boolean puedoEditar(MovimientoCaja dto,MovimientoCaja modificado)throws Exception{
		try{
			if (dto.getCodigo()==modificado.getCodigo()){
				return true;
			} else {
				if(!this.existeMovimientoCaja(modificado.getCodigo()))
					return true;
				else
					return false;
			}
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean movimientoCajaAsociado(Long id) throws Exception {
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		boolean estaAsoc = false;
		MovimientoCaja b = new MovimientoCaja();
		try {
			mp.initPersistencia();
			String filtro = "id == "+id;
			Collection movs= mp.getObjects(MovimientoCaja.class,filtro);
			if (movs.size()>=1){
				b = (MovimientoCaja)(movs.toArray())[0];
				estaAsoc=(b.getPlanilla()!=null);
			}
			mp.commit();
		} finally {
			mp.rollback();
		}
		return estaAsoc;
   }

	public int obtenerNroMovCaja() throws Exception {
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		int cod=0;
		try{
			mp.initPersistencia();
			Vector socioCol= mp.getAllOrdered(MovimientoCaja.class,"codigo descending");
			if(socioCol.size()==0)cod=1;
			else{
				MovimientoCaja p = (MovimientoCaja)socioCol.elementAt(0);
				cod=p.getCodigo()+1;
			}
			mp.commit();
			return cod;
		} finally {
			mp.rollback();
		}
	}	
}
