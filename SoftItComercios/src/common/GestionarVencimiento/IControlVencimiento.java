package common.GestionarVencimiento;

import java.sql.Date;
import java.util.Vector;

import persistencia.domain.Vencimiento; 
import server.ManipuladorPersistencia;

public interface IControlVencimiento {
	
	public boolean agregarVencimiento(Vencimiento pDTO)throws Exception;
	public void eliminarVencimiento(Long id)throws Exception;
	public void modificarVencimiento(Long id,Vencimiento modificado)throws Exception;
	public Vector obtenerVencimientos()throws Exception;
	public Vector obtenerVencimientosDeProducto(Long idProd)throws Exception;
	public Vencimiento buscarVencimiento(Long id) throws Exception;
	public Vencimiento buscarVencimientoProdFecha(Long idProd,Date fechaVto) throws Exception;
	public Vencimiento buscarVencimientoPersistentePorId(ManipuladorPersistencia mp,Long id) throws Exception;
	
}
