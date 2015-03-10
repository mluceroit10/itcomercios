package common.GestionarCliente;

import java.util.Vector;

import persistencia.domain.Cliente;
import server.ManipuladorPersistencia;

public interface IControlCliente {

	public boolean agregarCliente(Cliente cte)throws Exception;
	public void eliminarCliente(Long id)throws Exception;
	public void modificarCliente(Long id,Cliente modificado)throws Exception;
    public Vector obtenerClientes(String tipoCliente)throws Exception;
    public Vector obtenerClientesFiltro(String tipoCliente,String nombre)throws Exception;
    public boolean existeClienteNombre(String loc)throws Exception;
    public Cliente buscarCliente(Long id) throws Exception;
    public boolean puedoEditar(Cliente dto,Cliente modificado)throws Exception;
    public Cliente buscarClientePersistentePorId(ManipuladorPersistencia mp,Long id) throws Exception;
    public Vector obtenerMovimientosCajaDeFactura(Long id)throws Exception;
    public boolean clienteAsociado(Long id) throws Exception;
    public Vector obtenerFacturasDeCliente(String nombre)throws Exception;
}
