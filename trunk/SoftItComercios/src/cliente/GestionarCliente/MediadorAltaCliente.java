package cliente.GestionarCliente;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

import persistencia.domain.Cliente;
import persistencia.domain.Localidad;
import server.GestionarCliente.ControlCliente;
import cliente.GestionarLocalidad.MediadorGestionarLocalidad;

import common.Utils;
import common.GestionarCliente.IControlCliente;

public class MediadorAltaCliente implements ActionListener {

    private GUIAltaModCliente guiAltaCliente = null;
    public IControlCliente controlCliente;
    private MediadorGestionarCliente mgc;
    private MediadorGestionarLocalidad medGestionarLoc;
    public Localidad localidad;
    public Cliente socioDTO;
    
    public MediadorAltaCliente(MediadorGestionarCliente oldMGC,JDialog guiPadre) throws Exception {
    	try{
    	    this.controlCliente = new ControlCliente();
        }catch(Exception ex){
        	Utils.manejoErrores(ex,"Error en MediadorAltaCliente. Constructor");
        }
        guiAltaCliente = new GUIAltaModCliente(guiPadre);
        guiAltaCliente.setActionListeners(this);
        mgc = oldMGC;
        Utils.show(guiAltaCliente);
    }

    public void actionPerformed(ActionEvent e) {
    	if ((((Component)e.getSource()).getName().compareTo("Aceptar")) == 0) {
            String nombre = Utils.mayuscula(guiAltaCliente.getNombre().getText());
            String tel = guiAltaCliente.getTelefono().getText();
            String cuit = guiAltaCliente.getCuit().getText();
            String direccion = Utils.mayuscula(guiAltaCliente.getDireccion().getText());
            String nLoc=guiAltaCliente.getLocalidad().getText();
            String ingBrutos="";
            String iva=guiAltaCliente.getJCBIvaCl().getSelectedItem().toString();
            try{ 
            	if (nombre.length()==0){
            		Utils.advertenciaUsr(guiAltaCliente,"Por favor ingrese el Nombre.");
            	}else if(this.controlCliente.existeClienteNombre(nombre)){
            		Utils.advertenciaUsr(guiAltaCliente,"El Cliente que desea ingresar ya existe en el sistema.");
            	}else if (nombre.length()==0 || direccion.length()==0 ||nLoc.length()==0){
            		Utils.advertenciaUsr(guiAltaCliente,"Alguno de los campos obligatorios esta vacio.");
            	} else if(cuit.length()!=0 &&  Utils.verificarCuit(cuit)){
            		Utils.advertenciaUsr(guiAltaCliente,"El Cuit ingresado no es correcto.");
            	}else{
            			Cliente cliente = new Cliente();
            			cliente.setNombre(nombre);
            			cliente.setCuit(cuit);
            			if(cuit.length()>0){
            				 ingBrutos = guiAltaCliente.getIngrBrutos().getText();
            			}
            			cliente.setTelefono(tel);
            			cliente.setDireccion(direccion);
            			cliente.setIngBrutosCl(ingBrutos);
            			cliente.setIvaCl(iva);
            			cliente.setLocalidad(localidad);
            			this.controlCliente.agregarCliente(cliente);
            			guiAltaCliente.dispose();
            			mgc.cargarDatos();
            	}
            } catch(Exception ex) {
            	Utils.manejoErrores(ex,"Error en MediadorAltaCliente. Agregar");
            }
    	}else if ((((Component)e.getSource()).getName().compareTo("Buscar")) == 0) {
            buscarLocalidad();
        }else if ((((Component)e.getSource()).getName().compareTo("Cancelar")) == 0) {
            ((JDialog)((Component)e.getSource()).getParent().getParent().getParent().getParent()).dispose();
       }
   }

    private void buscarLocalidad() {
        if (medGestionarLoc== null) {
        	medGestionarLoc= new MediadorGestionarLocalidad(this,guiAltaCliente);
        } else {
            if (!medGestionarLoc.getGUI().isVisible()) {
            	medGestionarLoc.getGUI().setVisible(true);
            }
        }
        if (localidad != null){
            this.guiAltaCliente.getLocalidad().setText(localidad.getNombre());
            this.cargarLocalidad(localidad);
        }
    }
    
    public void actualizarLocalidad() {
        guiAltaCliente.setLocalidad(localidad.getNombre());
    }

    private void cargarLocalidad(Localidad loc) {
        this.localidad = loc;
    }
    
}