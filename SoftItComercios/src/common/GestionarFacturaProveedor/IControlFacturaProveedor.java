package common.GestionarFacturaProveedor;

import java.util.Vector;

import persistencia.domain.FacturaProveedor;

public interface IControlFacturaProveedor {

	public double agregarFacturaProveedorTotal(FacturaProveedor p,Vector items,Vector cambioPrecio,Vector prEntrConIva, Vector ganancias,Vector nuevoPrecioVtaSinIva,Vector nuevoPrecioVtaConIva,Vector ctrlVto,Vector fechasVto)throws Exception;
	public void eliminarFacturaProveedor(FacturaProveedor loc)throws Exception;
	public Vector obtenerFacturaProveedores(String tipoF,int mesLI,int anioLI)throws Exception;
	public Vector obtenerFacturaProveedoresFiltros(String tipoF,int mesLI,int anioLI,String fecha,String numero,String proveedor)throws Exception;
    public boolean existeFacturaProveedorNroTipo(Long nroF, String tipoF, int cod)throws Exception;
    public FacturaProveedor buscarFacturaProveedor(Long id) throws Exception;
    public boolean facturaAsociada(Long id) throws Exception;    
}
