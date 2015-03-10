package common.GestionarProducto;

import java.util.Vector;

import persistencia.domain.Producto;
import server.ManipuladorPersistencia;

public interface IControlProducto {
	
	public boolean agregarProducto(Producto pDTO)throws Exception;
	public void eliminarProducto(Long id)throws Exception;
	public void modificarProducto(Long id,Producto modificado)throws Exception;
	public Vector obtenerProductos()throws Exception;
	public Vector obtenerProductosFiltros(String cod,String nombre)throws Exception;
	public boolean existeProductoCodigo(Long codigo) throws Exception;
	public Producto buscarProducto(Long id) throws Exception;
	public Producto buscarProductoCodigo(int codigo) throws Exception;
	public boolean puedoEditar(Producto dto,Producto modificado)throws Exception;
	public Producto buscarProductoPersistentePorId(ManipuladorPersistencia mp,Long id) throws Exception;
	public Vector obtenerStockTodosProductos(String atOrden)throws Exception;
	public Vector obtenerStockProductosProveedor(String proveedor,String atOrden)throws Exception;
	public Vector obtenerProductosProveedor(String proveedor)throws Exception;
	public boolean productoAsociado(Long id) throws Exception;
}
