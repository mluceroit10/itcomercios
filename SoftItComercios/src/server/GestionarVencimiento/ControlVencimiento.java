package server.GestionarVencimiento;

import java.rmi.RemoteException;
import java.sql.Date;
import java.util.Collection;
import java.util.Vector;

import persistencia.domain.Producto;
import persistencia.domain.Vencimiento;
import server.Assemblers;
import server.ManipuladorPersistencia;
import server.GestionarProducto.ControlProducto;

import common.GestionarVencimiento.IControlVencimiento;

public class ControlVencimiento implements IControlVencimiento{
	
	public ControlVencimiento() throws RemoteException{   }
	
	public boolean agregarVencimiento(Vencimiento p)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		ControlProducto cProd = new ControlProducto();
		//creo objeto y seteo datos basicos
		Vencimiento lnew= Assemblers.crearVencimiento(p);		
		try{
			mp.initPersistencia();
			Producto prod = cProd.buscarProductoPersistentePorId(mp,p.getProducto().getId());
			mp.hacerPersistente(lnew);
			lnew.setProducto(prod);
			mp.commit();
		} finally {
			mp.rollback();
		}
		return true;
	}
	
	public void eliminarVencimiento(Long id)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		try {
			mp.initPersistencia();
			Vencimiento Vencimiento = this.buscarVencimientoPersistentePorId(mp,id);
			mp.borrar(Vencimiento);
			mp.commit();
		} finally {
			mp.rollback();
		}
	}
	
	public void modificarVencimiento(Long id,Vencimiento modificado)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		try{
			ControlProducto cProd = new ControlProducto();
			mp.initPersistencia();
			Producto producto = cProd.buscarProductoPersistentePorId(mp,modificado.getProducto().getId());
			Vencimiento Vencimiento = this.buscarVencimientoPersistentePorId(mp,id);
			Vencimiento.setStock(modificado.getStock());
			Vencimiento.setStockKilos(modificado.getStockKilos());
			Vencimiento.setFechaVto(modificado.getFechaVto());
			Vencimiento.setProducto(producto);
			mp.commit();
		} finally {
			mp.rollback();
		}
	}
	
	public Vector obtenerVencimientos()throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Vector Vencimientos2 = new Vector();
		try {
			mp.initPersistencia();
			Vector Vencimientos= mp.getAllOrdered(Vencimiento.class,"nombre ascending");
			for(int i=0; i<Vencimientos.size();i++){
				Vencimiento b = (Vencimiento)Vencimientos.elementAt(i);
				Vencimiento a =Assemblers.crearVencimiento(b);
				Producto p = Assemblers.crearProducto(b.getProducto());
				a.setProducto(p);
				Vencimientos2.add(a);
			}
			mp.commit();
		}  finally{
			mp.rollback();
		}
		return Vencimientos2;
	}
	
	public Vector obtenerVencimientosFiltros(String nombre)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Vector Vencimientos2 = new Vector();
		try {
			mp.initPersistencia();
			String filtro = "producto.nombre.toLowerCase().indexOf(\""+nombre.toLowerCase()+"\")>= 0";
			Vector Vencimientos= mp.getObjectsOrdered(Vencimiento.class,filtro,"nombre ascending");
			for(int i=0; i<Vencimientos.size();i++){
				Vencimiento b = (Vencimiento)Vencimientos.elementAt(i);
				Vencimiento a= Assemblers.crearVencimiento(b);
				Producto p = Assemblers.crearProducto(b.getProducto());
				a.setProducto(p);
				Vencimientos2.add(a);
			}
			mp.commit();
		}  finally{
			mp.rollback();
		}
		return Vencimientos2;
	}
		
	public boolean existeVencimientoCodigo(Long codigo)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		boolean existe = false;
		try {
			mp.initPersistencia();
			String filtro = "codigo == "+codigo;
			Collection VencimientoCol= mp.getObjects(Vencimiento.class,filtro);
			if (VencimientoCol.size()==1)
				existe=true;
			mp.commit();
		} finally {
			mp.rollback();
		}
		return existe;
	}
		
	public Vencimiento buscarVencimiento(Long id) throws Exception {
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Vencimiento a = new Vencimiento();
		try {
			mp.initPersistencia();
			String filtro = "id == "+id;
			Vector VencimientoCol= mp.getObjects(Vencimiento.class,filtro);
			if (VencimientoCol.size()>=1){
				Vencimiento b = (Vencimiento)VencimientoCol.elementAt(0);
				a=Assemblers.crearVencimiento(b);
				Producto p = Assemblers.crearProducto(b.getProducto());
				a.setProducto(p);
			}
			mp.commit();
		} finally {
			mp.rollback();
		}
		return a;
	}
	
	public Vencimiento buscarVencimientoProdFecha(Long idProd,Date fechaVto) throws Exception {
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Vencimiento a = new Vencimiento();
		try {
			mp.initPersistencia();
			String filtro = "producto.id == "+idProd;
			Vector VencimientoCol= mp.getObjects(Vencimiento.class,filtro);
			if (VencimientoCol.size()>=1){
				Vencimiento b = (Vencimiento)VencimientoCol.elementAt(0);
				a=Assemblers.crearVencimiento(b);
				Producto p = Assemblers.crearProducto(b.getProducto());
				a.setProducto(p);
			}
			mp.commit();
		} finally {
			mp.rollback();
		}
		return a;
	}
	
	public Vencimiento buscarVencimientoPersistentePorId(ManipuladorPersistencia mp,Long id) throws Exception {
		Vencimiento obj = new Vencimiento();
		String filtro = "id == "+id;
		Collection col= mp.getObjects(Vencimiento.class,filtro);
		if (col.size()>=1){
			obj = (Vencimiento)(col.toArray())[0];
		}
		return obj;
	}
		
}
