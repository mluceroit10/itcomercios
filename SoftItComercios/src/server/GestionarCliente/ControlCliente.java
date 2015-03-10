package server.GestionarCliente;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import persistencia.domain.Cliente;
import persistencia.domain.FacturaCliente;
import persistencia.domain.Localidad;
import persistencia.domain.MovimientoCaja;
import persistencia.domain.Provincia;
import server.Assemblers;
import server.ManipuladorPersistencia;
import server.GestionarFacturaCliente.ControlFacturaCliente;
import server.GestionarLocalidad.ControlLocalidad;

import common.Utils;
import common.GestionarCliente.IControlCliente;
public class ControlCliente implements IControlCliente{
	
	
	public ControlCliente() throws RemoteException{   }
	
	public boolean agregarCliente(Cliente p)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		ControlLocalidad cLoc = new ControlLocalidad();
		//creo objeto y seteo datos basicos
		Cliente lnew= Assemblers.crearCliente(p);
		if (this.existeClienteNombre(p.getNombre()))
			return false;
		else{
			try{
				mp.initPersistencia();
				Localidad loc = cLoc.buscarLocalidadPersistentePorId(mp,p.getLocalidad().getId());
				mp.hacerPersistente(lnew);
				lnew.setLocalidad(loc);
				mp.commit();
			} finally {
				mp.rollback();
			}
			return true;
		}
	}
	
	public void eliminarCliente(Long id)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		try {
			mp.initPersistencia();
			Cliente cliente = this.buscarClientePersistentePorId(mp,id);
			mp.borrar(cliente);
			mp.commit();
		} finally {
			mp.rollback();
		}
	}
	
	public void modificarCliente(Long id,Cliente modificado)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		try{
			ControlLocalidad cLoc = new ControlLocalidad();
			mp.initPersistencia();
			Cliente cliente = this.buscarClientePersistentePorId(mp,id);
			Localidad loc = cLoc.buscarLocalidadPersistentePorId(mp,modificado.getLocalidad().getId());
			cliente.setNombre(modificado.getNombre());
			cliente.setTelefono(modificado.getTelefono());
			cliente.setDireccion(modificado.getDireccion());
			cliente.setCuit(modificado.getCuit());
			cliente.setIngBrutosCl(modificado.getIngBrutosCl());
			cliente.setIvaCl(modificado.getIvaCl());
			cliente.setLocalidad(loc);
			mp.commit();
		} finally {
			mp.rollback();
		}
	}
	
	public Vector obtenerClientes(String tipoCliente)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Vector Clientes2 = new Vector();
		try {
			mp.initPersistencia();
			Vector clientes=null;
			if(tipoCliente.compareTo("Todos")==0){
				clientes = mp.getAllOrdered(Cliente.class,"nombre ascending");
			}else if(tipoCliente.compareTo("RI")==0){
    			String filtro = "ivaCl == \"Resp. Inscripto\"";
    			clientes = mp.getObjectsOrdered(Cliente.class,filtro,"nombre ascending");
    		}else if(tipoCliente.compareTo("Sin_RI")==0){
    			String filtro = "ivaCl != \"Resp. Inscripto\"";
    			clientes= mp.getObjectsOrdered(Cliente.class,filtro,"nombre ascending");
    		}
			for(int i=0; i<clientes.size();i++){
				Cliente b = (Cliente)clientes.elementAt(i);
				Cliente a= Assemblers.crearCliente(b);
				Localidad loc= Assemblers.crearLocalidad(b.getLocalidad());
				a.setLocalidad(loc);
				Clientes2.add(a);
			}
			mp.commit();
		}  finally{
			mp.rollback();
		}
		return Clientes2;
	}
	
	public Vector obtenerClientesFiltro(String tipoCliente,String nombre)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Vector Clientes2 = new Vector();
		try {
			mp.initPersistencia();
			Vector clientes=null;
			String filtro="nombre.toLowerCase().indexOf(\""+nombre.toLowerCase()+"\")>= 0";
			if(tipoCliente.compareTo("RI")==0){
    			filtro += " && ivaCl == \"Resp. Inscripto\"";
    		}else if(tipoCliente.compareTo("Sin_RI")==0){
    			filtro += " && ivaCl != \"Resp. Inscripto\"";
    		}
			clientes= mp.getObjectsOrdered(Cliente.class,filtro,"nombre ascending");
			for(int i=0; i<clientes.size();i++){
				Cliente b = (Cliente)clientes.elementAt(i);
				Cliente a= Assemblers.crearCliente(b);
				Localidad loc= Assemblers.crearLocalidad(b.getLocalidad());
				a.setLocalidad(loc);
				Clientes2.add(a);
			}
			mp.commit();
		}  finally{
			mp.rollback();
		}
		return Clientes2;
	}
	
	public Vector obtenerClientesDeLocalidadYDeuda(Long idLoc)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Vector Clientes2 = new Vector();
		try {
			mp.initPersistencia();
			String filtro = "localidad.id == "+idLoc;
			Vector Clientes= mp.getObjectsOrdered(Cliente.class,filtro,"nombre ascending");
			for(int i=0; i<Clientes.size();i++){
				Cliente b = (Cliente)Clientes.elementAt(i);
				String filtroFC = " anulada==false && (tipoFactura==\"FacturaCliente-A\" || tipoFactura==\"FacturaCliente-B\" || tipoFactura==\"RemitoCliente\") && cliente.id=="+b.getId();
				Vector facturaClientes= mp.getObjectsOrdered(FacturaCliente.class,filtroFC,"fechaImpresion ascending");
				double saldoI=0;
				for(int j=0;j<facturaClientes.size();j++){
		    		FacturaCliente fc = (FacturaCliente) facturaClientes.elementAt(j);
		    		boolean calcular=true;
		    		// verifica si existeFacturaDeRemito para calc a partir de la fact y no del remito
		    		if(fc.getTipoFactura().compareTo("RemitoCliente")==0){
		    			String filtroRem = " anulada==false && remitoNro == \"" +fc.getNroFactura()+ "\"";
		    			Vector facturaClienteRem= mp.getObjects(FacturaCliente.class,filtroRem);
		    			if (facturaClienteRem.size()==1)
		    				calcular=false;
		    		}
		    		if(calcular){
		    			saldoI =Utils.redondear(saldoI+fc.getImporteTotal(),2);
		    			Set movs= fc.getComprobantesPago();
		    			if(!movs.isEmpty()){
		    				for(Iterator it= movs.iterator(); it.hasNext();){
		    					MovimientoCaja mc=(MovimientoCaja)it.next();
		    					saldoI =Utils.redondear(saldoI-mc.getImporte(),2);
		    				}
		    			}
		    		}
		    	}
				if(saldoI!=0){
					Clientes2.add(b.getNombre());
					Clientes2.add(String.valueOf(saldoI));
				}
			}
			mp.commit();
		}  finally{
			mp.rollback();
		}
		return Clientes2;
	}
		
	public boolean existeClienteNombre(String nombre)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		boolean existe = false;
		try {
			mp.initPersistencia();
			String filtro = "nombre == \""+nombre+"\"";
			Collection ClienteCol= mp.getObjects(Cliente.class,filtro);
			if (ClienteCol.size()==1)
				existe=true;
			mp.commit();
		} finally {
			mp.rollback();
		}
		return existe;
	}
		
	public Cliente buscarCliente(Long id) throws Exception {
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Cliente a = new Cliente();
		try {
			mp.initPersistencia();
			String filtro = "id == "+id;
			Collection ClienteCol= mp.getObjects(Cliente.class,filtro);
			if (ClienteCol.size()>=1){
				Cliente b = (Cliente)(ClienteCol.toArray())[0];
				a= Assemblers.crearCliente(b);
				Localidad loc= Assemblers.crearLocalidad(b.getLocalidad());
				a.setLocalidad(loc);
				Provincia prov= Assemblers.crearProvincia(b.getLocalidad().getProvincia());
				loc.setProvincia(prov);
			}
			mp.commit();
		} finally {
			mp.rollback();
		}
		return a;
	}
	
	public Cliente buscarClientePersistentePorId(ManipuladorPersistencia mp,Long id) throws Exception {
		Cliente obj = new Cliente();
		String filtro = "id == "+id;
		Collection col= mp.getObjects(Cliente.class,filtro);
		if (col.size()>=1){
			obj = (Cliente)(col.toArray())[0];
		}
		return obj;
	}
	
	public boolean puedoEditar(Cliente dto,Cliente modificado)throws Exception{
		try{
			if (dto.getNombre().equals(modificado.getNombre())){
				return true;
			} else {
				if(!this.existeClienteNombre(modificado.getNombre()))
					return true;
				else
					return false;
			}
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
		
	public Vector obtenerMovimientosCajaDeFactura(Long id)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Vector movs = new Vector();
		try {
			mp.initPersistencia();
			String filtro="factura.id=="+id;
			Vector movscaja= mp.getObjectsOrdered(MovimientoCaja.class,filtro,"fechaMC ascending");
			for(int i=0; i<movscaja.size();i++){
				MovimientoCaja b = (MovimientoCaja)movscaja.elementAt(i);
				MovimientoCaja a= Assemblers.crearMovimientoCaja(b);
				movs.add(a);
			}
			mp.commit();
		}  finally{
			mp.rollback();
		}
		return movs;
	}
	
	public boolean clienteAsociado(Long id) throws Exception {
		boolean estaAsoc = false;
		ControlFacturaCliente ctrlFCte = new ControlFacturaCliente();
		try {
			estaAsoc = ctrlFCte.existenFacturasDeCliente(id);
		} catch(Exception e) {
			e.printStackTrace();
			estaAsoc = true;
		}
		return estaAsoc;
    }
	
	public Vector obtenerFacturasDeCliente(String nombre)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Vector FacturaClientes2 = new Vector();
		try {
			mp.initPersistencia();
			String filtro = " anulada==false && (tipoFactura==\"FacturaCliente-A\" || tipoFactura==\"FacturaCliente-B\" || tipoFactura==\"RemitoCliente\") && cliente.nombre==\""+nombre+"\"";
			Vector FacturaClientes= mp.getObjectsOrdered(FacturaCliente.class,filtro,"fechaImpresion ascending");
			for(int i=0; i<FacturaClientes.size();i++){
				FacturaCliente b = (FacturaCliente)FacturaClientes.elementAt(i);
				FacturaCliente a= Assemblers.crearFacturaCliente(b); 
				Cliente cte= Assemblers.crearCliente(b.getCliente());
				a.setCliente(cte);
				Set movs = b.getComprobantesPago();
				Set nvmovs= new HashSet();
				for(Iterator it= movs.iterator(); it.hasNext();){
					MovimientoCaja mc = (MovimientoCaja) it.next();
					MovimientoCaja mov= Assemblers.crearMovimientoCaja(mc);
					nvmovs.add(mov);
				}
				a.setComprobantesPago(nvmovs);
				FacturaClientes2.add(a);
			}
			mp.commit();
		}  finally{
			mp.rollback();
		}
		return FacturaClientes2;
	}	
}
