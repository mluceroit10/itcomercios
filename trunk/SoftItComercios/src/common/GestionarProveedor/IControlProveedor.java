package common.GestionarProveedor;

import java.util.Vector;

import persistencia.domain.Proveedor;
import server.ManipuladorPersistencia;

public interface IControlProveedor {

	public boolean agregarProveedor(Proveedor loc)throws Exception;
	public void eliminarProveedor(Long id)throws Exception;
	public void modificarProveedor(Long id,Proveedor modificado)throws Exception;
    public Vector obtenerProveedores()throws Exception;
    public Vector obtenerProveedoresFiltros(String cod,String nombre)throws Exception;
    public boolean existeProveedorNombre(String loc)throws Exception;
    public boolean existeProveedorCodigo(int codigo)throws Exception;
    public Proveedor buscarProveedor(Long id) throws Exception;
    public boolean puedoEditar(Proveedor dto,Proveedor modificado)throws Exception;
    public Proveedor buscarProveedorPersistentePorId(ManipuladorPersistencia mp,Long id) throws Exception;
    public int obtenerNroProveedor()throws Exception;
    public boolean proveedorAsociado(Long id) throws Exception;
}
