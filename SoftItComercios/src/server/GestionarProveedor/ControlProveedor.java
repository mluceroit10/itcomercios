package server.GestionarProveedor;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import persistencia.domain.FacturaProveedor;
import persistencia.domain.Localidad;
import persistencia.domain.MovimientoCaja;
import persistencia.domain.Producto;
import persistencia.domain.Proveedor;
import server.Assemblers;
import server.ManipuladorPersistencia;
import server.GestionarLocalidad.ControlLocalidad;
import server.GestionarProducto.ControlProducto;

import common.Utils;
import common.GestionarProveedor.IControlProveedor;
public class ControlProveedor implements IControlProveedor{
	
	
	public ControlProveedor() throws RemoteException{   }
	
	public boolean agregarProveedor(Proveedor p)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		ControlLocalidad cLoc = new ControlLocalidad();
		//creo objeto y seteo datos basicos
		Proveedor lnew= Assemblers.crearProveedor(p);
		if (this.existeProveedorNombre(p.getNombre()))
			return false;
		else{
			try{
				mp.initPersistencia();
				Localidad loc = cLoc.buscarLocalidadPersistentePorId(mp,p.getLocalidad().getId());
				mp.hacerPersistente(lnew);
				lnew.setLocalidad(loc);
				mp.commit();
			} finally {
				mp.rollback();
			}
			return true;
		}
	}
	
	public void eliminarProveedor(Long id)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		try {
			mp.initPersistencia();
			Proveedor Proveedor = this.buscarProveedorPersistentePorId(mp,id);
			mp.borrar(Proveedor);
			mp.commit();
		} finally {
			mp.rollback();
		}
	}
	
	public void modificarProveedor(Long id,Proveedor modificado)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		try{
			ControlLocalidad cLoc = new ControlLocalidad();
			mp.initPersistencia();
			Proveedor proveedor = this.buscarProveedorPersistentePorId(mp,id);
			Localidad loc = cLoc.buscarLocalidadPersistentePorId(mp,modificado.getLocalidad().getId());
			proveedor.setNombre(modificado.getNombre());
			proveedor.setTelefono(modificado.getTelefono());
			proveedor.setDireccion(modificado.getDireccion());
			proveedor.setCodigo(modificado.getCodigo());
			proveedor.setLocalidad(loc);
			proveedor.setDeuda(modificado.getDeuda());
			proveedor.setFechaUF(modificado.getFechaUF());
			mp.commit();
		} finally {
			mp.rollback();
		}
	}
	
	public Vector obtenerProveedores()throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Vector proveedors2 = new Vector();
		try {
			mp.initPersistencia();
			Vector Proveedors= mp.getAllOrdered(Proveedor.class,"nombre ascending");
			for(int i=0; i<Proveedors.size();i++){
				Proveedor b = (Proveedor)Proveedors.elementAt(i);
				Proveedor a = Assemblers.crearProveedor(b);
				Localidad loc= Assemblers.crearLocalidad(b.getLocalidad());
				a.setLocalidad(loc);
				proveedors2.add(a);
			}
			mp.commit();
		}  finally{
			mp.rollback();
		}
		return proveedors2;
	}
	
	public Vector obtenerProveedoresFiltros(String cod,String nombre)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Vector proveedors2 = new Vector();
		try {
			mp.initPersistencia();
			String filtro = "nombre.toLowerCase().indexOf(\""+nombre.toLowerCase()+"\")>= 0";
			Vector Proveedors= mp.getObjectsOrdered(Proveedor.class,filtro,"nombre ascending");
			for(int i=0; i<Proveedors.size();i++){
				Proveedor b = (Proveedor)Proveedors.elementAt(i);
				if(Utils.comienza(String.valueOf(b.getCodigo()),cod)){
					Proveedor a= Assemblers.crearProveedor(b);
					Localidad loc= Assemblers.crearLocalidad(b.getLocalidad());
					a.setLocalidad(loc);
					proveedors2.add(a);
				}
			}
			mp.commit();
		}  finally{
			mp.rollback();
		}
		return proveedors2;
	}
		
	public boolean existeProveedorNombre(String nombre)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		boolean existe = false;
		try {
			mp.initPersistencia();
			String filtro = "nombre == \""+nombre+"\"";
			Collection ProveedorCol= mp.getObjects(Proveedor.class,filtro);
			if (ProveedorCol.size()==1)
				existe=true;
			mp.commit();
		} finally {
			mp.rollback();
		}
		return existe;
	}
	
	public boolean existeProveedorCodigo(int codigo)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		boolean existe = false;
		try {
			mp.initPersistencia();
			String filtro = "codigo=="+codigo;
			Collection ProveedorCol= mp.getObjects(Proveedor.class,filtro);
			if (ProveedorCol.size()>=1)
				existe=true;
			mp.commit();
		} finally {
			mp.rollback();
		}
		return existe;
	}
		
	public Proveedor buscarProveedor(Long id) throws Exception {
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Proveedor a = new Proveedor();
		try {
			mp.initPersistencia();
			String filtro = "id == "+id;
			Collection ProveedorCol= mp.getObjects(Proveedor.class,filtro);
			if (ProveedorCol.size()>=1){
				Proveedor b = (Proveedor)(ProveedorCol.toArray())[0];
				a=Assemblers.crearProveedor(b);
				Localidad loc= Assemblers.crearLocalidad(b.getLocalidad());
				a.setLocalidad(loc);
			}
			mp.commit();
		} finally {
			mp.rollback();
		}
		return a;
	}
		
	public Proveedor buscarProveedorPersistentePorId(ManipuladorPersistencia mp,Long id) throws Exception {
		Proveedor obj = new Proveedor();
		String filtro = "id == "+id;
		Collection col= mp.getObjects(Proveedor.class,filtro);
		if (col.size()>=1){
			obj = (Proveedor)(col.toArray())[0];
		}
		return obj;
	}
	
	public boolean puedoEditar(Proveedor dto,Proveedor modificado)throws Exception{
		try{
			if (dto.getNombre().equals(modificado.getNombre())){
				return true;
			} else {
				if(!this.existeProveedorNombre(modificado.getNombre()))
					return true;
				else
					return false;
			}
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public int obtenerNroProveedor()throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		int cod=0;
		try{
			mp.initPersistencia();
			Vector socioCol= mp.getAllOrdered(Proveedor.class,"codigo descending");
			if(socioCol.size()==0)cod=1;
			else{
				Proveedor p = (Proveedor)socioCol.elementAt(0);
				cod=p.getCodigo()+1;
			}
			mp.commit();
			return cod;
		} finally {
			mp.rollback();
		}
	}
	
	public boolean proveedorAsociado(Long id) throws Exception {
		boolean estaAsoc = false;
		ControlProducto ctrlProd = new ControlProducto();
		try {
			Vector productos = ctrlProd.obtenerProductos();
			Producto p;
			for (int i=0; i<productos.size() && !estaAsoc; i++ ) {
				p = (Producto)productos.elementAt(i);
				if (p.getProveedor().getId().equals(id)){
					estaAsoc = true;
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			estaAsoc = true;
		}
		return estaAsoc;
    }
	
	public Vector obtenerFacturasDeProveedor(String nombre)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Vector FacturaClientes2 = new Vector();
		try {
			mp.initPersistencia();
			String filtro = " anulada==false && tipoFactura==\"FacturaProveedor\" && proveedor.nombre==\""+nombre+"\"";
			Vector facturaProveedores= mp.getObjectsOrdered(FacturaProveedor.class,filtro,"fecha ascending");
			for(int i=0; i<facturaProveedores.size();i++){
				FacturaProveedor b = (FacturaProveedor)facturaProveedores.elementAt(i);
				FacturaProveedor a= Assemblers.crearFacturaProveedor(b);
				Set movs = b.getComprobantesPago();
				Set nvmovs= new HashSet();
				for(Iterator it= movs.iterator(); it.hasNext();){
					MovimientoCaja mc = (MovimientoCaja) it.next();
					MovimientoCaja mov= Assemblers.crearMovimientoCaja(mc);
					nvmovs.add(mov);
				}
				a.setComprobantesPago(nvmovs);
				FacturaClientes2.add(a);
			}
			mp.commit();
		}  finally{
			mp.rollback();
		}
		return FacturaClientes2;
	}	
	
	public Vector obtenerMovimientosCajaDeFactura(Long id)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Vector movs = new Vector();
		try {
			mp.initPersistencia();
			String filtro="factura.id=="+id;
			Vector movscaja= mp.getObjectsOrdered(MovimientoCaja.class,filtro,"fechaMC ascending");
			for(int i=0; i<movscaja.size();i++){
				MovimientoCaja b = (MovimientoCaja)movscaja.elementAt(i);
				MovimientoCaja a= Assemblers.crearMovimientoCaja(b);
				movs.add(a);
			}
			mp.commit();
		}  finally{
			mp.rollback();
		}
		return movs;
	}
	
}
