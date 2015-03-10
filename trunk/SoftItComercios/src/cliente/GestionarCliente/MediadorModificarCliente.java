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

public class MediadorModificarCliente implements ActionListener {

	public Localidad localidad;
	private GUIAltaModCliente guiModCliente = null;
    public IControlCliente controlCliente;
    private MediadorGestionarCliente mgCliente;
    private MediadorGestionarLocalidad mgLoc = null;
    public Cliente cte;

    public MediadorModificarCliente(MediadorGestionarCliente oldMGCliente, Cliente cl,JDialog guiPadre) {
      	try{
      		this.controlCliente = new ControlCliente();
        }catch(Exception ex){
        	Utils.manejoErrores(ex,"Error en MediadorModificarCliente. Constructor");
        }
        guiModCliente = new GUIAltaModCliente(cl,guiPadre);
        guiModCliente.setActionListeners(this);
    	mgCliente = oldMGCliente;
        cte = cl;
        localidad = cl.getLocalidad();
        Utils.show(guiModCliente);
    }
    
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
    	if (source == guiModCliente.getAceptar()) {
    		String nombre = Utils.mayuscula(guiModCliente.getNombre().getText());
            String tel = guiModCliente.getTelefono().getText();
            String cuit = guiModCliente.getCuit().getText();
            String direccion = Utils.mayuscula(guiModCliente.getDireccion().getText());
            String nLoc=guiModCliente.getLocalidad().getText();
            String ingBrutos="";
            String iva=guiModCliente.getJCBIvaCl().getSelectedItem().toString();
        	if (nombre.length()==0 || direccion.length()==0 ||nLoc.length()==0){
        		Utils.advertenciaUsr(guiModCliente,"Alguno de los campos obligatorios esta vacio.");
        	} else {
        		if(cuit.length()!=0 &&  Utils.verificarCuit(cuit)){
        			Utils.advertenciaUsr(guiModCliente,"El Cuit ingresado no es correcto.");
            	}else{
        			try{
        				Cliente cliente = new Cliente();
            			cliente.setNombre(nombre);
            			cliente.setCuit(cuit);
            			if(cuit.length()>0){
            				ingBrutos = guiModCliente.getIngrBrutos().getText();
            			}
            			cliente.setTelefono(tel);
            			cliente.setIngBrutosCl(ingBrutos);
            			cliente.setIvaCl(iva);
            			cliente.setDireccion(direccion);
            			cliente.setLocalidad(localidad);
        				if (this.controlCliente.puedoEditar(cte,cliente)){
        					this.controlCliente.modificarCliente(cte.getId(), cliente);
        					guiModCliente.dispose();
        					mgCliente.cargarDatos();
        				} else {
        					Utils.advertenciaUsr(guiModCliente,"El Cliente que desea ingresar ya existe en el sistema.");
        				}
        			} catch(Exception ex) {
        				Utils.manejoErrores(ex,"Error en MediadorModificarCliente. ActionPerformed");
        			}
        		}
        	}
        } else if (source == guiModCliente.getJButtonLocalidad()) {
            buscarLocalidad();
        } else if (source == guiModCliente.getCancelar()) {    
            ((JDialog)((Component)e.getSource()).getParent().getParent().getParent().getParent()).dispose();
        }
    }
    
    private void buscarLocalidad() {
        if (mgLoc == null) {
            mgLoc = new MediadorGestionarLocalidad(this,guiModCliente);
        } else {
            if (!mgLoc.getGUI().isVisible()) {
                mgLoc.getGUI().setVisible(true);
            }
        }
        if (localidad != null){
            this.guiModCliente.getLocalidad().setText(localidad.getNombre());
            this.cargarLocalidad(localidad);           
        }
    }

    private void cargarLocalidad(Localidad loc) {
        this.localidad = loc;
    }
    
    public void actualizarLocalidad() {
        guiModCliente.setLocalidad(localidad.getNombre());
    }

}
