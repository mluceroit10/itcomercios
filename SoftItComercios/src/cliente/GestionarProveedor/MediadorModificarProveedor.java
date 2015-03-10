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

public class MediadorModificarProveedor implements ActionListener {

	public Localidad localidad;
	private GUIAltaModProveedor guiModProveedor = null;
    public IControlProveedor controlProveedor;
    private MediadorGestionarProveedor mgProveedor;
    private MediadorGestionarLocalidad mgLoc = null;
    public Proveedor prov;
    private int codProv;

    public MediadorModificarProveedor(MediadorGestionarProveedor oldMGProveedor, Proveedor pr,JDialog guiPadre) {
      	try{
      		this.controlProveedor = new ControlProveedor();
        }catch(Exception ex){
        	Utils.manejoErrores(ex,"Error en MediadorModificarProveedor. Constructor");
        }
        guiModProveedor = new GUIAltaModProveedor(pr,guiPadre);
        guiModProveedor.setActionListeners(this);
    	mgProveedor = oldMGProveedor;
    	prov = pr;
    	codProv = pr.getCodigo();
        localidad = pr.getLocalidad();
        Utils.show(guiModProveedor);
    }
    
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
    	if (source == guiModProveedor.getAceptar()) {
    		String nombre = Utils.mayuscula(guiModProveedor.getNombre().getText());
            String tel = guiModProveedor.getTelefono().getText();
            String codigo = guiModProveedor.getCodigo().getText();
            String direccion = Utils.mayuscula(guiModProveedor.getDireccion().getText());
            String nLoc=guiModProveedor.getLocalidad().getText();
            try{
            	if (nombre.length()==0 || codigo.length()==0 || tel.length()==0 || direccion.length()==0 ||nLoc.length()==0){
            		Utils.advertenciaUsr(guiModProveedor,"Alguno de los campos obligatorios esta vacio.");
            	}else if(Integer.parseInt(codigo) != codProv && this.controlProveedor.existeProveedorCodigo(Integer.parseInt(codigo))){
            		Utils.advertenciaUsr(guiModProveedor,"El Proveedor que desea ingresar ya existe en el sistema con ese Código.");	
            	}else{
            		Proveedor proveedor = new Proveedor();
            		proveedor.setNombre(nombre);
            		proveedor.setCodigo(Integer.parseInt(codigo));
            		proveedor.setTelefono(tel);
            		proveedor.setDireccion(direccion);
            		proveedor.setLocalidad(localidad);
            		if (this.controlProveedor.puedoEditar(prov,proveedor)){
            			this.controlProveedor.modificarProveedor(prov.getId(), proveedor);
            			guiModProveedor.dispose();
            			mgProveedor.cargarDatos();
            		} else {
            			Utils.advertenciaUsr(guiModProveedor,"El Proveedor que desea ingresar ya existe en el sistema.");
            		}
            		
            	}
            } catch(Exception ex) {
            	Utils.manejoErrores(ex,"Error en MediadorModificarProveedor. ActionPerformed");
            }
        } else if (source == guiModProveedor.getJButtonLocalidad()) {
            buscarLocalidad();
        } else if (source == guiModProveedor.getCancelar()) {    
            ((JDialog)((Component)e.getSource()).getParent().getParent().getParent().getParent()).dispose();
        }
    }
    
    private void buscarLocalidad() {
        if (mgLoc == null) {
            mgLoc = new MediadorGestionarLocalidad(this,guiModProveedor);
        } else {
            if (!mgLoc.getGUI().isVisible()) {
                mgLoc.getGUI().setVisible(true);
            }
        }
        if (localidad != null){
            this.guiModProveedor.getLocalidad().setText(localidad.getNombre());
            this.cargarLocalidad(localidad);           
        }
    }

    private void cargarLocalidad(Localidad loc) {
        this.localidad = loc;
    }
    
    public void actualizarLocalidad() {
        guiModProveedor.setLocalidad(localidad.getNombre());
    }

}
