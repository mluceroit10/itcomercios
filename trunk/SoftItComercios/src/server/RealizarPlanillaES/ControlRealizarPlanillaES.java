package server.RealizarPlanillaES;

import java.sql.Date;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import persistencia.domain.MovimientoCaja;
import persistencia.domain.PlanillaES;
import server.Assemblers;
import server.ManipuladorPersistencia;
import server.GestionarMovimientoCaja.ControlMovimientoCaja;

import common.Utils;
import common.RealizarPlanillaES.IControlRealizarPlanillaES;

public class ControlRealizarPlanillaES implements IControlRealizarPlanillaES{
	
	private static final long serialVersionUID = 1L;
	
	public ControlRealizarPlanillaES() throws Exception{   }
	
	public void agregarPlanillaESTotal(PlanillaES p,Vector movimientos) throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		PlanillaES lnew= Assemblers.crearPlanillaES(p);
		ControlMovimientoCaja cMC = new ControlMovimientoCaja();
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
				p.setFecha(new Date(0,0,0));
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
	
	public Vector obtenerMovimientosCajaParaPlanilla(Date fechaD, Date fechaH)throws Exception{
		Vector movimientos = new Vector();
		ControlMovimientoCaja cMov= new ControlMovimientoCaja();
		Vector todosMov=cMov.obtenerMovimientosCaja();
		try {
			for (Iterator i = todosMov.iterator(); i.hasNext(); ) {
				MovimientoCaja b = (MovimientoCaja)i.next();
				if( (b.getPlanilla()==null) && (b.getFecha().before(fechaH)|| b.getFecha().equals(fechaH))){
					MovimientoCaja a = Assemblers.crearMovimientoCaja(b);
					movimientos.add(a);
				}	
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return movimientos;
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
			}
			mp.commit();
		} finally {
			mp.rollback();
		}
		return p;
	}
}
