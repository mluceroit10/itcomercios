package common.GestionarComercio;

import persistencia.domain.Comercio;
import server.ManipuladorPersistencia;

public interface IControlComercio {
  
	public boolean agregarComercio(Comercio loc)throws Exception;
	public void modificarComercio(Long id,Comercio modificado)throws Exception;
    public Comercio obtenerComercio()throws Exception;
    public boolean existeComercioNombre(String loc)throws Exception;
    public Comercio buscarUnicaComercioPersistencia(ManipuladorPersistencia mp) throws Exception;
    public Comercio buscarComercioPersistentePorId(ManipuladorPersistencia mp,Long id) throws Exception;
  
}
