package common.GestionarFacturaCliente;

import java.sql.Date;
import java.util.Vector;

import persistencia.domain.FacturaCliente;

public interface IControlFacturaCliente {

	public double agregarFacturaClienteTotal(FacturaCliente fc, String tipo, String loc,int nroMC,Vector items)throws Exception;
	public void anularFacturaCliente(Long idF)throws Exception;
	public boolean existenFacturasDeCliente(Long id)throws Exception;
    public Vector obtenerFacturaClientesPeriodo(boolean listarRemitosSinFact, String tipoF,int diaLI,int mesLI,int anioLI)throws Exception;
    public Vector obtenerFacturaClientesPeriodoFiltros(boolean listarRemitosSinFact,String tipoF,int diaLI,int mesLI,int anioLI,String fecha,String numero,String cliente)throws Exception;
    public Vector obtenerFacturasClienteFechaLoc(Date fecha,Long idLoc)throws Exception;
    public Vector obtenerListadoProductosFacturados(Vector ids)throws Exception;
    public boolean existeFacturaClienteNroTipo(Long nroF, String tipoF)throws Exception;
    public boolean existeFacturaDeRemito(String nroRemito)throws Exception;
    public FacturaCliente buscarFacturaDeRemito(String nroRemito) throws Exception;
    public FacturaCliente buscarFacturaCliente(Long id) throws Exception;
    public FacturaCliente buscarFacturaClientePersistencia(Long nroF, String tipoF) throws Exception;
    public Vector obtenerFacturasLibroIva(int mesLI, int anioLI) throws Exception;
       
}
