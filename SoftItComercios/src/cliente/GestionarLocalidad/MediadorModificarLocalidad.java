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

public class MediadorModificarLocalidad implements ActionListener {

    private GUIAltaModLocalidad guiLocalidad = null;
    private MediadorGestionarLocalidad mgLocalidad;
    private MediadorGestionarProvincia medGestionarProvincia;
    private IControlLocalidad controlLocalidad;
    private Localidad locDTO;
    public Provincia prov;

    public MediadorModificarLocalidad(MediadorGestionarLocalidad oldMGLocalidad, Localidad loc,JDialog guiPadre) {
        try{
        	this.controlLocalidad = new ControlLocalidad();
        }catch(Exception ex){
        	Utils.manejoErrores(ex,"Error en MediadorModificarLocalidad. Constructor");
        }
        guiLocalidad = new GUIAltaModLocalidad(loc,guiPadre);
        guiLocalidad.setActionListeners(this);
        mgLocalidad = oldMGLocalidad;
        locDTO = loc;
        prov = loc.getProvincia();
        Utils.show(guiLocalidad);
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == guiLocalidad.getJBAceptar()) {
            String nombre = Utils.mayuscula(guiLocalidad.getJTFNombre().getText());
            String codPostal = guiLocalidad.getJTFCodPostal().getText();
            if (nombre.length()==0){
            	Utils.advertenciaUsr(guiLocalidad,"Alguno de los campos obligatorios esta vacio.");
            } else {
                try {
                    Localidad modLocalidad = new Localidad();
                    modLocalidad.setNombre(nombre);
                    if(codPostal.length()>0)
                    modLocalidad.setCodPostal(Integer.parseInt(codPostal));
                    modLocalidad.setProvincia(prov);
                    if (this.controlLocalidad.puedoEditar(locDTO,modLocalidad)){
                        this.controlLocalidad.modificarLocalidad(locDTO.getId(), modLocalidad);
                        guiLocalidad.dispose();
                        mgLocalidad.cargarDatos();
                    } else {
                    	Utils.advertenciaUsr(guiLocalidad,"La Localidad que desea Ingresar ya existe.");
                    }
                } catch(Exception ex) {
                	Utils.manejoErrores(ex,"Error en MediadorModificarLocalidad. ActionPerformed");
                }
            }
        }else if (source == guiLocalidad.getJBProvincia()) {
            buscarProvincia();
        }else if (source == guiLocalidad.getJBCancelar()) {   
            ((JDialog)((Component)e.getSource()).getParent().getParent().getParent().getParent()).dispose();
        }
    }
    private void buscarProvincia() {
        if (medGestionarProvincia== null) {
        	medGestionarProvincia= new MediadorGestionarProvincia(this,guiLocalidad);
        } else {
            if (!medGestionarProvincia.getGUI().isVisible()) {
            	medGestionarProvincia.getGUI().setVisible(true);
            }
        }
        if (prov != null){
            this.guiLocalidad.getJTFProvincia().setText(prov.getNombre());
            this.cargarProvincia(prov);
        }
    }
    
    private void cargarProvincia(Provincia pr) {
        this.prov = pr;
    }
    
    public void actualizarProvincia() {
    	guiLocalidad.setProvincia(prov.getNombre());
    }
        
}

