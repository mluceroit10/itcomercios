package server.GestionarProvincia;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import persistencia.domain.Localidad;
import persistencia.domain.Provincia;
import server.Assemblers;
import server.ManipuladorPersistencia;
import server.GestionarLocalidad.ControlLocalidad;

import common.GestionarProvincia.IControlProvincia;

public class ControlProvincia implements IControlProvincia{
	
	
	public ControlProvincia() throws RemoteException{   }
	
	public boolean agregarProvincia(Provincia p)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Provincia pnew= Assemblers.crearProvincia(p);
		if (this.existeProvinciaNombre(p.getNombre()))
			return false;
		else{
			try{
				mp.initPersistencia();
				mp.hacerPersistente(pnew);
				mp.commit();
			} finally {
				mp.rollback();
			}
			return true;
		}
	}
	
	public void eliminarProvincia(Long id)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		try {
			mp.initPersistencia();
			Provincia prov = this.buscarProvinciaPersistentePorId(mp,id);
			mp.borrar(prov);
			mp.commit();
		} finally {
			mp.rollback();
		}
	}
	
	public void modificarProvincia(Long id,Provincia modificado)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		try{
			mp.initPersistencia();
			Provincia prov = this.buscarProvinciaPersistentePorId(mp,id);
			prov.setNombre(modificado.getNombre());
			mp.commit();
		} finally {
			mp.rollback();
		}
	}
	
	public Vector obtenerProvincias()throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Vector Provincias2 = new Vector();
		try {
			mp.initPersistencia();
			Vector Provincias= mp.getAllOrdered(Provincia.class,"nombre ascending");
			for(int i=0; i<Provincias.size();i++){
				Provincia b = (Provincia)Provincias.elementAt(i);
				Provincia a= Assemblers.crearProvincia(b);
				Provincias2.add(a);
			}
			mp.commit();
		}  finally{
			mp.rollback();
		}
		return Provincias2;
	}
	
	public Vector obtenerProvinciasFiltro(String nombre)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Vector Provincias2 = new Vector();
		try {
			mp.initPersistencia();
			String filtro="nombre.toLowerCase().indexOf(\""+nombre.toLowerCase()+"\")>= 0";
			Vector Provincias= mp.getObjectsOrdered(Provincia.class,filtro,"nombre ascending");
			for(int i=0; i<Provincias.size();i++){
				Provincia b = (Provincia)Provincias.elementAt(i);
				Provincia a= Assemblers.crearProvincia(b);
				Provincias2.add(a);
			}
			mp.commit();
		}  finally{
			mp.rollback();
		}
		return Provincias2;
	}
		
	public boolean existeProvinciaNombre(String nombre)throws Exception{
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		boolean existe = false;
		try {
			mp.initPersistencia();
			String filtro = "nombre == \""+nombre+"\"";
			Collection ProvinciaCol= mp.getObjects(Provincia.class,filtro);
			if (ProvinciaCol.size()==1)
				existe=true;
			mp.commit();
		} finally {
			mp.rollback();
		}
		return existe;
	}
		
	public Provincia buscarProvincia(Long id) throws Exception {
		ManipuladorPersistencia mp=new ManipuladorPersistencia();
		Provincia a = new Provincia();
		try {
			mp.initPersistencia();
			String filtro = "id == "+id;
			Collection ProvinciaCol= mp.getObjects(Provincia.class,filtro);
			if (ProvinciaCol.size()>=1){
				Provincia b = (Provincia)(ProvinciaCol.toArray())[0];
				a= Assemblers.crearProvincia(b);
			}
			mp.commit();
		} finally {
			mp.rollback();
		}
		return a;
	}
	
	public Provincia buscarProvinciaPersistentePorId(ManipuladorPersistencia mp,Long id) throws Exception {
		Provincia obj = new Provincia();
		String filtro = "id == "+id;
		Collection col= mp.getObjects(Provincia.class,filtro);
		if (col.size()>=1){
			obj = (Provincia)(col.toArray())[0];
		}
		return obj;
	}
	
	public boolean puedoEditar(Provincia dto,Provincia modificado)throws Exception{
		try{
			if (dto.getNombre().equals(modificado.getNombre())){
				return true;
			} else {
				if(!this.existeProvinciaNombre(modificado.getNombre()))
					return true;
				else
					return false;
			}
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean provinciaAsociada(Long id) throws Exception {
        ControlLocalidad ctrlLoc = new ControlLocalidad();
        boolean estaAsoc = false;
        try {
            Vector locsCol = ctrlLoc.obtenerLocalidades();
            Localidad loc;
            for (Iterator i = locsCol.iterator(); i.hasNext() && !estaAsoc;) {
                loc = (Localidad)i.next();
                if (loc.getProvincia().getId().equals(id)){
                	estaAsoc = true;
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
            estaAsoc = true;
        }
        return estaAsoc;
    }
		
}
