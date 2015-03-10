package cliente.GestionarProvincia; 

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

import persistencia.domain.Provincia;
import server.GestionarProvincia.ControlProvincia;

import common.Utils;
import common.GestionarProvincia.IControlProvincia;

public class MediadorAltaProvincia implements ActionListener {

    private GUIAltaModProvincia guiProvincia = null;
    private MediadorGestionarProvincia mgProvincia;
    public IControlProvincia controlProvincia;
    
    public MediadorAltaProvincia(MediadorGestionarProvincia oldMGProvincia,JDialog guiPadre) throws Exception {
        try{
        	this.controlProvincia = new ControlProvincia();
        }catch(Exception ex){
        	Utils.manejoErrores(ex,"Error en MediadorAltaProvincia. Constructor");
        }
        guiProvincia = new GUIAltaModProvincia(guiPadre);
        guiProvincia.setActionListeners(this);
        mgProvincia = oldMGProvincia;
        Utils.show(guiProvincia);
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
    	if (source == guiProvincia.getJBAceptar()) {
            String nombre = Utils.mayuscula(guiProvincia.getJTFNombre().getText());
            try {
            	if (nombre.length()==0){
            		Utils.advertenciaUsr(guiProvincia,"Alguno de los campos obligatorios esta vacio.");
            	}else if (this.controlProvincia.existeProvinciaNombre(nombre)){
            		Utils.advertenciaUsr(guiProvincia,"La Provincia ingresada ya existe.");
            	}else{
            		Provincia miProvincia = new Provincia();
            		miProvincia.setNombre(nombre);
            		this.controlProvincia.agregarProvincia(miProvincia);
            		guiProvincia.dispose();
            		mgProvincia.cargarDatos();
            	}
            } catch(Exception ex) {
            	Utils.manejoErrores(ex,"Error en MediadorAltaProvincia. ActionPerformed");
            }
    	} else if (source == guiProvincia.getJBCancelar()) {
            ((JDialog)((Component)e.getSource()).getParent().getParent().getParent().getParent()).dispose();
       }
    }
   
}

