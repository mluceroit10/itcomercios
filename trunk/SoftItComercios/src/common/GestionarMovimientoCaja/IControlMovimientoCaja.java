package common.GestionarMovimientoCaja;

import java.util.Vector;

import persistencia.domain.MovimientoCaja;

public interface IControlMovimientoCaja {

	public boolean agregarMovimientoCaja_Fact(MovimientoCaja mc)throws Exception;
	public void eliminarMovimientoCaja(Long id)throws Exception;
	public Vector obtenerMovimientosCaja()throws Exception;
	public Vector obtenerMovimientosCajaPeriodo(int mesLI,int anioLI)throws Exception;
	public Vector obtenerMovimientosCajaFiltros(int mesLI,int anioLI,String cod,String fecha)throws Exception;
    public boolean existeMovimientoCaja(int codigo)throws Exception;
    public MovimientoCaja buscarMovimientoCaja(int codigo) throws Exception;
    public boolean puedoEditar(MovimientoCaja dto,MovimientoCaja modificado)throws Exception;
    public boolean movimientoCajaAsociado(Long id) throws Exception; 
    public int obtenerNroMovCaja()throws Exception;
    
}
