package common.GestionarProvincia;

import java.util.Vector;

import persistencia.domain.Provincia;
import server.ManipuladorPersistencia;

public interface IControlProvincia {
	
	public boolean agregarProvincia(Provincia pDTO)throws Exception;
	public void eliminarProvincia(Long id)throws Exception;
	public void modificarProvincia(Long id,Provincia modificado)throws Exception;
	public Vector obtenerProvincias()throws Exception;
	public Vector obtenerProvinciasFiltro(String nombre)throws Exception;
	public boolean existeProvinciaNombre(String nombre)throws Exception;
	public Provincia buscarProvincia(Long id) throws Exception;
	public boolean puedoEditar(Provincia dto,Provincia modificado)throws Exception;
	public boolean provinciaAsociada(Long id) throws Exception;
	public Provincia buscarProvinciaPersistentePorId(ManipuladorPersistencia mp,Long id) throws Exception; 
}
