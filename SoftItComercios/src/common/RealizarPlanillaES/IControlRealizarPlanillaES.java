package common.RealizarPlanillaES;

import java.sql.Date;
import java.util.Vector;

import persistencia.domain.PlanillaES;
import server.ManipuladorPersistencia;

public interface IControlRealizarPlanillaES {

	public void agregarPlanillaESTotal(PlanillaES p,Vector movimientos,Vector facts,Vector remitos) throws Exception;
	public void eliminarPlanillaES(Long id)throws Exception;
	public Vector obtenerPlanillasES(int mesLI,int anioLI)throws Exception;
	public Vector obtenerPlanillasESFiltroFecha(int mesLI,int anioLI,String fecha)throws Exception;
	public Vector obtenerPlanillasESFiltroNroPl(int mesLI,int anioLI,String nroPl)throws Exception;
	public boolean existePlanillaESNroPlanilla(int numero)throws Exception;
	public PlanillaES buscarPlanillaES(Long id) throws Exception;
	public PlanillaES buscarPlanillaESNroPlanilla(int numero) throws Exception;
	public PlanillaES buscarPlanillaESPersistentePorId(ManipuladorPersistencia mp,Long id) throws Exception;
	public PlanillaES buscarPlanillaESNroPlanillaPersistencia(int numero) throws Exception;
	public PlanillaES obtenerUltimaPlanilla()throws Exception;
	public Vector obtenerMovimientosCajaParaPlanilla(Date fechaH)throws Exception;
	public Vector obtenerFacturasClienteParaPlanilla(Date fechaH)throws Exception;
	public Vector obtenerRemitosClienteParaPlanilla(Date fechaH)throws Exception;
	
}
