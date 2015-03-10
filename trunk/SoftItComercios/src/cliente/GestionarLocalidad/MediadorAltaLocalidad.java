package cliente.GestionarLocalidad;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

import persistencia.domain.Localidad;
import persistencia.domain.Provincia;
import server.GestionarLocalidad.ControlLocalidad;
import cliente.GestionarProvincia.MediadorGestionarProvincia;

import common.Utils;
import common.GestionarLocalidad.IControlLocalidad;

public class MediadorAltaLocalidad implements ActionListener {

    private GUIAltaModLocalidad guiAltaLocalidad = null;
    private MediadorGestionarLocalidad mgLocalidad;
    private MediadorGestionarProvincia medGestionarProvincia;
    public IControlLocalidad controlLocalidad;
    public Localidad locDTO;
    public Provincia prov;

    public MediadorAltaLocalidad(MediadorGestionarLocalidad oldMGLocalidad,JDialog guiPadre) throws Exception {
    	try{
            this.controlLocalidad = new ControlLocalidad();
    	}catch(Exception ex){
    		Utils.manejoErrores(ex,"Error en MediadorAltaLocalidad. Constructor");
        }
    	guiAltaLocalidad = new GUIAltaModLocalidad(guiPadre);
    	guiAltaLocalidad.setActionListeners(this);
        mgLocalidad = oldMGLocalidad;
        Utils.show(guiAltaLocalidad);
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
    	if (source == guiAltaLocalidad.getJBAceptar()) {
            String nombre = Utils.mayuscula(guiAltaLocalidad.getJTFNombre().getText());
            String codPostal = guiAltaLocalidad.getJTFCodPostal().getText();
            try {
            	if (nombre.length()==0){
            		Utils.advertenciaUsr(guiAltaLocalidad,"Alguno de los campos obligatorios esta vacio.");
            	}else if (this.controlLocalidad.existeLocalidadNombre(nombre)){
            		Utils.advertenciaUsr(guiAltaLocalidad,"La Localidad ingresada ya existe.");
            	}else{
            		Localidad nuevaLocalidad = new Localidad();
            		nuevaLocalidad.setNombre(nombre);
            		if(codPostal.length()>0)
            		nuevaLocalidad.setCodPostal(Integer.parseInt(codPostal));
            		nuevaLocalidad.setProvincia(prov);
            		this.controlLocalidad.agregarLocalidad(nuevaLocalidad);
            		guiAltaLocalidad.dispose();
            		mgLocalidad.cargarDatos();
            	}
            } catch(Exception ex) {
            	Utils.manejoErrores(ex,"Error en MediadorAltaLocalidad. ActionPerformed");
            }
    	}else if (source == guiAltaLocalidad.getJBProvincia()) {
            buscarProvincia();
        } else if (source == guiAltaLocalidad.getJBCancelar()) {
            ((JDialog)((Component)e.getSource()).getParent().getParent().getParent().getParent()).dispose();
       }
    }

    private void buscarProvincia() {
        if (medGestionarProvincia== null) {
        	medGestionarProvincia= new MediadorGestionarProvincia(this,guiAltaLocalidad);
        } else {
            if (!medGestionarProvincia.getGUI().isVisible()) {
            	medGestionarProvincia.getGUI().setVisible(true);
            }
        }
        if (prov != null){
            this.guiAltaLocalidad.getJTFProvincia().setText(prov.getNombre());
            this.cargarProvincia(prov);
        }
    }
    
    private void cargarProvincia(Provincia pr) {
        this.prov = pr;
    }
    
    public void actualizarProvincia() {
    	guiAltaLocalidad.setProvincia(prov.getNombre());
    }
   
}

