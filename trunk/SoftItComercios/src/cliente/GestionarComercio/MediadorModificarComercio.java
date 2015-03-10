package cliente.GestionarComercio;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

import javax.swing.JDialog;
import javax.swing.JFrame;

import persistencia.domain.Comercio;
import persistencia.domain.Localidad;
import server.GestionarComercio.ControlComercio;
import cliente.GestionarLocalidad.MediadorGestionarLocalidad;
import cliente.Principal.GUIReport;

import common.Utils;
import common.GestionarComercio.IControlComercio;

public class MediadorModificarComercio implements ActionListener {

	public Localidad localidad;
	private GUIAltaModComercio guiComercio = null;
    public IControlComercio controlComercio;
    private MediadorGestionarLocalidad mgLoc = null;
    public Comercio dist=null;

    public MediadorModificarComercio(JFrame guiPadre) {
      	try{
      		this.controlComercio = new ControlComercio();
      		dist = controlComercio.obtenerComercio();
        }catch(Exception ex){
        	Utils.manejoErrores(ex,"Error en MediadorModificarComercio. Constructor");
        }
        if(dist!=null){
        	localidad = dist.getLocalidad();
        	guiComercio = new GUIAltaModComercio(dist,guiPadre);
        }else{	
        	guiComercio = new GUIAltaModComercio(guiPadre);
        }	
        guiComercio.setActionListeners(this);
        Utils.show(guiComercio);
    }
    
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
    	if (source == guiComercio.getAceptar()) {
    		String nombre = Utils.mayuscula(guiComercio.getNombre().getText());
            String tel = guiComercio.getTelefono().getText();
            String cuit = guiComercio.getCuit().getText();
            String ingBru = guiComercio.getIngrBrutos().getText();
            String direccion = Utils.mayuscula(guiComercio.getDireccion().getText());
            String nLoc=guiComercio.getLocalidad().getText();
        	java.util.Date fu=guiComercio.getJDateChooserFecha().getDate();
        	String nroFactARIV= guiComercio.getProximoNroFactARIV().getText();
        	String nroFactBRIV= guiComercio.getProximoNroFactBRIV().getText();
        	String nroRemitoRIV= guiComercio.getProximoNroRemitoRIV().getText();
        	Date fecha= Utils.crearFecha(fu);
        	if (cuit.length()==0 || nombre.length()==0 || direccion.length()==0 ||nLoc.length()==0 ||nroFactARIV.length()==0 || nroFactBRIV.length()==0 ||  nroRemitoRIV.length()==0 ){
        		Utils.advertenciaUsr(guiComercio,"Alguno de los campos obligatorios esta vacio.");
        	} else {
        		if(Utils.verificarCuit(cuit)){
        			Utils.advertenciaUsr(guiComercio,"El Cuit ingresado no es correcto.");
            	}else{
        			try{
        				Comercio dNew = new Comercio();
        				dNew.setNombre(nombre);
        				dNew.setCuit(cuit);
        				dNew.setTelefono(tel);
        				dNew.setDireccion(direccion);
        				dNew.setInicioAct(fecha);
        				dNew.setCategoria(ingBru);
        				dNew.setLocalidad(localidad);
        				dNew.setNroFactA(new Long(nroFactARIV)); 
        				dNew.setNroFactB(new Long(nroFactBRIV));
        				dNew.setNroRemito(new Long(nroRemitoRIV));
        				if (dist!=null){
        					controlComercio.modificarComercio(dist.getId(),dNew);
        				}else{
        					controlComercio.agregarComercio(dNew);
        				}
        				guiComercio.dispose();
        			} catch(Exception ex) {
        				Utils.manejoErrores(ex,"Error en MediadorModificarCliente. ActionPerformed");
        			}
        		}
        	}
        } else if (source == guiComercio.getJButtonLocalidad()) {
            buscarLocalidad();
        } else if (source == guiComercio.getJButtonImprimirTarjeta()) {
    		String nombre = Utils.mayuscula(guiComercio.getNombre().getText());
            String tel = guiComercio.getTelefono().getText();
            String cuit = guiComercio.getCuit().getText();
            String ingBru = guiComercio.getIngrBrutos().getText();
            String direccion = Utils.mayuscula(guiComercio.getDireccion().getText());
            String nLoc=guiComercio.getLocalidad().getText();
            try {
				new GUIReport(guiComercio,15,nombre, cuit,ingBru, tel, direccion, nLoc);
			} catch (Exception ex) {
				Utils.manejoErrores(ex,"imprimiendo tarjeta del Comercio");
			}    
        } else if (source == guiComercio.getJButtonModificarNrosFactura()) {
            guiComercio.getProximoNroFactARIV().setEnabled(true);
            guiComercio.getProximoNroFactBRIV().setEnabled(true);
            guiComercio.getProximoNroRemitoRIV().setEnabled(true);
        } else if (source == guiComercio.getCancelar()) {    
            ((JDialog)((Component)e.getSource()).getParent().getParent().getParent().getParent()).dispose();
        }
    }
    
    private void buscarLocalidad() {
        if (mgLoc == null) {
            mgLoc = new MediadorGestionarLocalidad(this,guiComercio);
        } else {
            if (!mgLoc.getGUI().isVisible()) {
                mgLoc.getGUI().setVisible(true);
            }
        }
        if (localidad != null){
            this.guiComercio.getLocalidad().setText(localidad.getNombre());
            this.cargarLocalidad(localidad);           
        }
    }

    private void cargarLocalidad(Localidad loc) {
        this.localidad = loc;
    }
    
    public void actualizarLocalidad() {
    	guiComercio.setLocalidad(localidad.getNombre());
    }

}
