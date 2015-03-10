package server.GestionarComercio;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Vector;

import persistencia.domain.Comercio;
import persistencia.domain.Localidad;
import server.Assemblers;
import server.ManipuladorPersistencia;
import server.GestionarLocalidad.ControlLocalidad;

import common.GestionarComercio.IControlComercio;
public class ControlComercio implements IControlComercio{
	
	
	public ControlComercio() throws RemoteException{   }
	
	public boolean agregarComercio(Comercio p)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		ControlLocalidad cLoc = new ControlLocalidad();
		//creo objeto y seteo datos basicos
		Comercio lnew= Assemblers.crearComercio(p);
		if (this.existeComercioNombre(p.getNombre()))
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
	
	public void eliminarComercio(Comercio p)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		try {
			mp.initPersistencia();
			String filtro = "nombre == \""+p.getNombre()+"\"";
			Vector ComercioCol=mp.getObjects(Comercio.class,filtro);
			if (ComercioCol.size()==1){
				Comercio Comercio = (Comercio) ComercioCol.elementAt(0);
				mp.borrar(Comercio);
				mp.commit();
			}
		} catch(Exception e) {
			e.printStackTrace();
			mp.rollback();
		}
	}
	
	public void modificarComercio(Long id,Comercio modificado)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		try{
			ControlLocalidad cLoc = new ControlLocalidad();
			mp.initPersistencia();
			Comercio Comercio = this.buscarComercioPersistentePorId(mp,id);
			Localidad loc = cLoc.buscarLocalidadPersistentePorId(mp,modificado.getLocalidad().getId());
			Comercio.setNombre(modificado.getNombre());
			Comercio.setTelefono(modificado.getTelefono());
			Comercio.setDireccion(modificado.getDireccion());
			Comercio.setCuit(modificado.getCuit());
			Comercio.setCategoria(modificado.getCategoria());
			Comercio.setInicioAct(modificado.getInicioAct());
			Comercio.setLocalidad(loc);
			Comercio.setNroFactA(modificado.getNroFactA());
			Comercio.setNroFactB(modificado.getNroFactB());
			Comercio.setNroRemito(modificado.getNroRemito());
			mp.commit();
		} finally {
			mp.rollback();
		}
	}
	
	public Comercio obtenerComercio()throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Comercio dR = null;
		try {
			mp.initPersistencia();
			Vector Comercios= mp.getAll(Comercio.class);
			if(Comercios.size()>0){
				Comercio b = (Comercio)Comercios.elementAt(0);
				Comercio a = Assemblers.crearComercio(b);
				Localidad loc = Assemblers.crearLocalidad(b.getLocalidad());
				a.setLocalidad(loc);
				dR=a;
			}
			mp.commit();
		}  finally{
			mp.rollback();
		}
		return dR;
	}
		
	public Comercio buscarComercioPersistentePorId(ManipuladorPersistencia mp,Long id) throws Exception {
		Comercio obj = new Comercio();
		String filtro = "id == "+id;
		Collection col= mp.getObjects(Comercio.class,filtro);
		if (col.size()>=1){
			obj = (Comercio)(col.toArray())[0];
		}
		return obj;
	}
	
	public boolean existeComercioNombre(String nombre)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		boolean existe = false;
		try {
			mp.initPersistencia();
			String filtro = "nombre == \""+nombre+"\"";
			Collection ComercioCol= mp.getObjects(Comercio.class,filtro);
			if (ComercioCol.size()==1)
				existe=true;
			mp.commit();
		} finally {
			mp.rollback();
		}
		return existe;
	}
		
	public Comercio buscarUnicaComercioPersistencia(ManipuladorPersistencia mp) throws Exception {
		Comercio b = new Comercio();
		Collection ComercioCol= mp.getAll(Comercio.class);
		if (ComercioCol.size()>=1){
			b = (Comercio)(ComercioCol.toArray())[0];
		}
		return b;
	}
	
	public boolean puedoEditar(Comercio dto,Comercio modificado)throws Exception{
		try{
			if (dto.getNombre().equals(modificado.getNombre())){
				return true;
			} else {
				if(!this.existeComercioNombre(modificado.getNombre()))
					return true;
				else
					return false;
			}
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
		
}
