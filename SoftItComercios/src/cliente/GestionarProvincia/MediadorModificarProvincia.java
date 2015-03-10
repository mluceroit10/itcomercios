package cliente.GestionarProvincia;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

import persistencia.domain.Provincia;
import server.GestionarProvincia.ControlProvincia;

import common.Utils;
import common.GestionarProvincia.IControlProvincia;

public class MediadorModificarProvincia implements ActionListener {

    private GUIAltaModProvincia guiProvincia = null;
    private MediadorGestionarProvincia mgProvincia;
    private IControlProvincia controlProvincia;
    public Provincia prov;

    public MediadorModificarProvincia(MediadorGestionarProvincia oldMGProvincia, Provincia p,JDialog guiPadre) {
        try{
    		this.controlProvincia = new ControlProvincia();
        }catch(Exception ex){
        	Utils.manejoErrores(ex,"Error en MediadorModificarProvincia. Constructor");
        }
        guiProvincia = new GUIAltaModProvincia(p,guiPadre);
        guiProvincia.setActionListeners(this);
        mgProvincia = oldMGProvincia;
        prov = p;
        Utils.show(guiProvincia);
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == guiProvincia.getJBAceptar()) {
            String nombre = Utils.mayuscula(guiProvincia.getJTFNombre().getText());
            if (nombre.length()==0){
            	Utils.advertenciaUsr(guiProvincia,"Alguno de los campos obligatorios esta vacio.");
            } else {
                try {
                    Provincia miProvincia = new Provincia();
                    miProvincia.setNombre(nombre);
                    if (this.controlProvincia.puedoEditar(prov,miProvincia)){
                        this.controlProvincia.modificarProvincia(prov.getId(), miProvincia);
                        guiProvincia.dispose();
                        mgProvincia.cargarDatos();
                    } else {
                    	Utils.advertenciaUsr(guiProvincia,"La Provincia que desea ingresar ya existe.");
                    }
                } catch(Exception ex) {
                	Utils.manejoErrores(ex,"Error en MediadorModificarProvincia. ActionPerformed");
                }
            }
        } else if (source == guiProvincia.getJBCancelar()) {   
            ((JDialog)((Component)e.getSource()).getParent().getParent().getParent().getParent()).dispose();
        }
    }
        
}

