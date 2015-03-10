package cliente.GestionarProveedor;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

import persistencia.domain.Localidad;
import persistencia.domain.Proveedor;
import server.GestionarProveedor.ControlProveedor;
import cliente.GestionarLocalidad.MediadorGestionarLocalidad;

import common.Utils;
import common.GestionarProveedor.IControlProveedor;

public class MediadorAltaProveedor implements ActionListener {

    private GUIAltaModProveedor guiAltaProveedor = null;
    public IControlProveedor controlProveedor;
    private MediadorGestionarProveedor mgc;
    private MediadorGestionarLocalidad medGestionarLoc;
    public Localidad localidad;
    public Proveedor socioDTO;
    
    public MediadorAltaProveedor(MediadorGestionarProveedor oldMGC,JDialog guiPadre) throws Exception {
    	try{
    	    this.controlProveedor = new ControlProveedor();
        }catch(Exception ex){
        	Utils.manejoErrores(ex,"Error en MediadorAltaProveedor. Constructor");
        }
        int nro= this.controlProveedor.obtenerNroProveedor();
        guiAltaProveedor = new GUIAltaModProveedor(nro,guiPadre);
        guiAltaProveedor.setActionListeners(this);
        mgc = oldMGC;
        Utils.show(guiAltaProveedor);
    }

    public void actionPerformed(ActionEvent e) {
    	if ((((Component)e.getSource()).getName().compareTo("Aceptar")) == 0) {
            String nombre = Utils.mayuscula(guiAltaProveedor.getNombre().getText());
            String tel = guiAltaProveedor.getTelefono().getText();
            String codigo = guiAltaProveedor.getCodigo().getText();
            String direccion = Utils.mayuscula(guiAltaProveedor.getDireccion().getText());
            String nLoc=guiAltaProveedor.getLocalidad().getText();
            try{
            	if (nombre.length()==0 || codigo.length()==0){
            		Utils.advertenciaUsr(guiAltaProveedor,"Por favor ingrese el Nombre y Código del Proveedor.");
            	}else if(this.controlProveedor.existeProveedorCodigo(Integer.parseInt(codigo))){
            		Utils.advertenciaUsr(guiAltaProveedor,"El Proveedor que desea ingresar ya existe en el sistema con ese Código.");
            	}else if(this.controlProveedor.existeProveedorNombre(nombre)){
            		Utils.advertenciaUsr(guiAltaProveedor,"El Proveedor que desea ingresar ya existe en el sistema con ese Nombre.");
            	}else if (nombre.length()==0 || codigo.length()==0 || tel.length()==0 || direccion.length()==0 ||nLoc.length()==0){
            		Utils.advertenciaUsr(guiAltaProveedor,"Alguno de los campos obligatorios esta vacio.");
            	} else{
            			Proveedor proveedor = new Proveedor();
            			proveedor.setNombre(nombre);
            			proveedor.setCodigo(Integer.parseInt(codigo));
            			proveedor.setTelefono(tel);
            			proveedor.setDireccion(direccion);
            			proveedor.setLocalidad(localidad);
            			this.controlProveedor.agregarProveedor(proveedor);
            			guiAltaProveedor.dispose();
            			mgc.cargarDatos();
            	}
            } catch(Exception ex) {
            	Utils.manejoErrores(ex,"Error en MediadorAltaProveedor. ActionPerformed");
            }
    	}else if ((((Component)e.getSource()).getName().compareTo("Buscar")) == 0) {
            buscarLocalidad();
        }else if ((((Component)e.getSource()).getName().compareTo("Cancelar")) == 0) {
            ((JDialog)((Component)e.getSource()).getParent().getParent().getParent().getParent()).dispose();
       }
    }

    private void buscarLocalidad() {
        if (medGestionarLoc== null) {
        	medGestionarLoc= new MediadorGestionarLocalidad(this,guiAltaProveedor);
        } else {
            if (!medGestionarLoc.getGUI().isVisible()) {
            	medGestionarLoc.getGUI().setVisible(true);
            }
        }
        if (localidad != null){
            this.guiAltaProveedor.getLocalidad().setText(localidad.getNombre());
            this.cargarLocalidad(localidad);
        }
    }
    
    public void actualizarLocalidad() {
        guiAltaProveedor.setLocalidad(localidad.getNombre());
    }

    private void cargarLocalidad(Localidad loc) {
        this.localidad = loc;
    }
    
}