package common.GestionarLocalidad;

import java.util.Vector;

import persistencia.domain.Localidad;
import server.ManipuladorPersistencia;

public interface IControlLocalidad {

	public boolean agregarLocalidad(Localidad loc)throws Exception;
	public void eliminarLocalidad(Long id)throws Exception;
	public void modificarLocalidad(Long id,Localidad modificado)throws Exception;
    public Vector obtenerLocalidades()throws Exception;
    public Vector obtenerLocalidadesFiltro(String nombre)throws Exception;
    public boolean existeLocalidadNombre(String loc)throws Exception;
    public Localidad buscarLocalidad(Long id) throws Exception;
    public boolean puedoEditar(Localidad dto,Localidad modificado)throws Exception;
    public boolean localidadAsociada(Long id) throws Exception;
    public Localidad buscarLocalidadPersistentePorId(ManipuladorPersistencia mp,Long id) throws Exception;
    
} 
