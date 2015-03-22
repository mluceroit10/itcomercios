package server.GestionarProducto;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Vector;

import persistencia.domain.ItemFactura;
import persistencia.domain.Producto;
import persistencia.domain.Proveedor;
import server.Assemblers;
import server.ManipuladorPersistencia;
import server.GestionarProveedor.ControlProveedor;

import common.Utils;
import common.GestionarProducto.IControlProducto;

public class ControlProducto implements IControlProducto{
	
	
	public ControlProducto() throws RemoteException{   }
	
	public boolean agregarProducto(Producto p)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		ControlProveedor cProv = new ControlProveedor();
		//creo objeto y seteo datos basicos
		Producto lnew= Assemblers.crearProducto(p);		
		if (this.existeProductoCodigo(p.getCodigo()))
			return false;
		else{
			try{
				mp.initPersistencia();
				Proveedor prov = cProv.buscarProveedorPersistentePorId(mp,p.getProveedor().getId());
				mp.hacerPersistente(lnew);
				lnew.setProveedor(prov);
				mp.commit();
			} finally {
				mp.rollback();
			}
			return true;
		}
	}
	
	public void eliminarProducto(Long id)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		try {
			mp.initPersistencia();
			Producto producto = this.buscarProductoPersistentePorId(mp,id);
			mp.borrar(producto);
			mp.commit();
		} finally {
			mp.rollback();
		}
	}
	
	public void modificarProducto(Long id,Producto modificado)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		try{
			ControlProveedor cProv = new ControlProveedor();
			mp.initPersistencia();
			Proveedor proveedor = cProv.buscarProveedorPersistentePorId(mp,modificado.getProveedor().getId());
			Producto producto = this.buscarProductoPersistentePorId(mp,id);
			producto.setCodigo(modificado.getCodigo());
			producto.setPrecioKilos(modificado.isPrecioKilos());
			producto.setPrecioEntrada(modificado.getPrecioEntrada());
			producto.setPrecioVentaSinIva(modificado.getPrecioVentaSinIva());
			producto.setPrecioVentaConIva(modificado.getPrecioVentaConIva());
			producto.setPrecioEntCIva(modificado.isPrecioEntCIva());
			producto.setNombre(modificado.getNombre());
			producto.setStockActual(modificado.getStockActual());
			producto.setStockMinimo(modificado.getStockMinimo());
			producto.setStockKilosAct(modificado.getStockKilosAct());
			producto.setStockKilosMin(modificado.getStockKilosMin());
			producto.setGanancia(modificado.getGanancia());
			producto.setProveedor(proveedor);
			mp.commit();
		} finally {
			mp.rollback();
		}
	}
	
	public Vector obtenerProductos()throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Vector productos2 = new Vector();
		try {
			mp.initPersistencia();
			Vector productos= mp.getAllOrdered(Producto.class,"nombre ascending");
			for(int i=0; i<productos.size();i++){
				Producto b = (Producto)productos.elementAt(i);
				Producto a =Assemblers.crearProducto(b);
				Proveedor p = Assemblers.crearProveedor(b.getProveedor());
				a.setProveedor(p);
				productos2.add(a);
			}
			mp.commit();
		}  finally{
			mp.rollback();
		}
		return productos2;
	}
	
	public Vector obtenerProductosFiltros(String cod,String nombre)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Vector productos2 = new Vector();
		try {
			mp.initPersistencia();
			String filtro = "nombre.toLowerCase().indexOf(\""+nombre.toLowerCase()+"\")>= 0";
			Vector productos= mp.getObjectsOrdered(Producto.class,filtro,"nombre ascending");
			for(int i=0; i<productos.size();i++){
				Producto b = (Producto)productos.elementAt(i);
				if(Utils.comienza(String.valueOf(b.getCodigo()),cod)){
					Producto a= Assemblers.crearProducto(b);
					Proveedor p = Assemblers.crearProveedor(b.getProveedor()); 
					a.setProveedor(p);
					productos2.add(a);
				}
			}
			mp.commit();
		}  finally{
			mp.rollback();
		}
		return productos2;
	}
		
	public boolean existeProductoCodigo(Long codigo)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		boolean existe = false;
		try {
			mp.initPersistencia();
			String filtro = "codigo == "+codigo;
			Collection productoCol= mp.getObjects(Producto.class,filtro);
			if (productoCol.size()==1)
				existe=true;
			mp.commit();
		} finally {
			mp.rollback();
		}
		return existe;
	}
		
	public Producto buscarProducto(Long id) throws Exception {
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Producto a = new Producto();
		try {
			mp.initPersistencia();
			String filtro = "id == "+id;
			Vector productoCol= mp.getObjects(Producto.class,filtro);
			if (productoCol.size()>=1){
				Producto b = (Producto)productoCol.elementAt(0);
				a=Assemblers.crearProducto(b);
				Proveedor p = Assemblers.crearProveedor(b.getProveedor());
				a.setProveedor(p);
			}
			mp.commit();
		} finally {
			mp.rollback();
		}
		return a;
	}
	
	public Producto buscarProductoCodigo(Long codigo) throws Exception {
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Producto a = new Producto();
		try {
			mp.initPersistencia();
			String filtro = "codigo == "+codigo;
			Vector productoCol= mp.getObjects(Producto.class,filtro);
			if (productoCol.size()>=1){
				Producto b = (Producto)productoCol.elementAt(0);
				a=Assemblers.crearProducto(b);
				Proveedor p = Assemblers.crearProveedor(b.getProveedor());
				a.setProveedor(p);
			}
			mp.commit();
		} finally {
			mp.rollback();
		}
		return a;
	}
	
	public Producto buscarProductoPersistentePorId(ManipuladorPersistencia mp,Long id) throws Exception {
		Producto obj = new Producto();
		String filtro = "id == "+id;
		Collection col= mp.getObjects(Producto.class,filtro);
		if (col.size()>=1){
			obj = (Producto)(col.toArray())[0];
		}
		return obj;
	}
	
	public boolean puedoEditar(Producto dto,Producto modificado)throws Exception{
		try{
			if (dto.getCodigo().equals(modificado.getCodigo())){
				return true;
			} else {
				if(!this.existeProductoCodigo(modificado.getCodigo()))
					return true;
				else
					return false;
			}
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Vector obtenerStockTodosProductos(String atOrden)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Vector productos2 = new Vector();
		try {
			mp.initPersistencia();
			Vector productos= mp.getAllOrdered(Producto.class,atOrden+" ascending");
			for(int i=0; i<productos.size();i++){
				Producto b = (Producto)productos.elementAt(i);
				if(b.getStockActual()<=b.getStockMinimo()){
					Producto a= Assemblers.crearProducto(b);
					Proveedor p = Assemblers.crearProveedor(b.getProveedor());
					a.setProveedor(p);
					productos2.add(a);
				}else if(b.isPrecioKilos() && b.getStockKilosAct()<=b.getStockKilosMin()){
					Producto a= Assemblers.crearProducto(b);
					Proveedor p = Assemblers.crearProveedor(b.getProveedor());
					a.setProveedor(p);
					productos2.add(a);
				}
			}
			mp.commit();
		}  finally{
			mp.rollback();
		}
		return productos2;
	}
	
	public Vector obtenerStockProductosProveedor(String proveedor,String atOrden)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Vector productos2 = new Vector();
		try {
			mp.initPersistencia();
			String filtro="proveedor.nombre==\""+proveedor+"\"";
			Vector productos= mp.getObjectsOrderedSubc(Producto.class,filtro,atOrden+" ascending");
			for(int i=0; i<productos.size();i++){
				Producto b = (Producto)productos.elementAt(i);
				if(b.getStockActual()<=b.getStockMinimo()){
					Producto a= Assemblers.crearProducto(b);
					Proveedor p = Assemblers.crearProveedor(b.getProveedor());
					a.setProveedor(p);
					productos2.add(a);
				}
				else if(b.isPrecioKilos() && b.getStockKilosAct()<=b.getStockKilosMin()){
					Producto a= Assemblers.crearProducto(b);
					Proveedor p = Assemblers.crearProveedor(b.getProveedor());
					a.setProveedor(p);
					productos2.add(a);
				}
			}
			mp.commit();
		}  finally{
			mp.rollback();
		}
		return productos2;
	}
	
	public Vector obtenerProductosProveedor(String proveedor)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Vector productos2 = new Vector();
		try {
			mp.initPersistencia();
			String filtro="proveedor.nombre==\""+proveedor+"\"";
			Vector productos= mp.getObjectsOrderedSubc(Producto.class,filtro,"nombre ascending");
			for(int i=0; i<productos.size();i++){
				Producto b = (Producto)productos.elementAt(i);
				Producto a= Assemblers.crearProducto(b);
				Proveedor p = Assemblers.crearProveedor(b.getProveedor());
				a.setProveedor(p);
				productos2.add(a);
				
			}
			mp.commit();
		}  finally{
			mp.rollback();
		}
		return productos2;
	}
	
	public boolean productoAsociado(Long id) throws Exception {
		boolean estaAsoc = false;
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		try {
			mp.initPersistencia();
			String filtro="producto.id=="+id;
			Vector productos= mp.getObjects(ItemFactura.class,filtro);
			if(productos.size()>0)
					estaAsoc = true;
			mp.commit();
		}  finally{
			mp.rollback();
		}
		return estaAsoc;
    }
	
}
