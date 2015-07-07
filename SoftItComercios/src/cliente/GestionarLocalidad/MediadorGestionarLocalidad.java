package cliente.GestionarLocalidad;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import persistencia.domain.Localidad;
import server.GestionarLocalidad.ControlLocalidad;
import cliente.GestionarCliente.MediadorAltaCliente;
import cliente.GestionarCliente.MediadorModificarCliente;
import cliente.GestionarComercio.MediadorModificarComercio;
import cliente.GestionarProveedor.MediadorAltaProveedor;
import cliente.GestionarProveedor.MediadorModificarProveedor;

import common.Utils;
import common.GestionarLocalidad.IControlLocalidad;

public class MediadorGestionarLocalidad implements ActionListener, KeyListener, ListSelectionListener,MouseListener {
    
    private GUIGestionarLocalidad guiLocalidad = null;
    protected IControlLocalidad controlLocalidad;
    private Localidad miLoc;
    private MediadorAltaCliente medAltaCliente = null;
    private MediadorModificarCliente medModCliente = null;
    private MediadorAltaProveedor medAltaProveedor = null;
    private MediadorModificarProveedor medModProveedor = null;
	private MediadorModificarComercio medModComercio;
	private boolean flag=false;
    
    public MediadorGestionarLocalidad(JFrame guiPadre) {
    	try{
    		this.controlLocalidad = new ControlLocalidad();
    	}catch(Exception ex){
    		Utils.manejoErrores(ex,"Error en MediadorGestionarLocalidad. Constructor");
        }
        this.guiLocalidad = new GUIGestionarLocalidad(guiPadre);
        this.guiLocalidad.setActionListeners(this);
        cargarDatos();
        this.guiLocalidad.setListSelectionListener(this);
        this.guiLocalidad.setKeyListener(this);
        this.flag=true;
        Utils.show(guiLocalidad);
    }
    
    public MediadorGestionarLocalidad(MediadorAltaCliente medAltaCliente,JDialog guiPadre) {
        this.medAltaCliente = medAltaCliente;
        try{
        	this.controlLocalidad = new ControlLocalidad();
        }catch(Exception ex){
        	Utils.manejoErrores(ex,"Error en MediadorGestionarLocalidad. Constructor");
        }
        this.guiLocalidad = new GUIGestionarLocalidad(guiPadre);
        this.guiLocalidad.setActionListeners(this);
        cargarDatos();
        this.guiLocalidad.setListSelectionListener(this);
        this.guiLocalidad.setKeyListener(this);
        Utils.show(guiLocalidad);
    }
    
    public MediadorGestionarLocalidad(MediadorModificarCliente medModCliente,JDialog guiPadre) {
        this.medModCliente = medModCliente;
        try{
    		this.controlLocalidad = new ControlLocalidad();
        }catch(Exception ex){
        	Utils.manejoErrores(ex,"Error en MediadorGestionarLocalidad. Constructor");
        }
        this.guiLocalidad = new GUIGestionarLocalidad(guiPadre);
        this.guiLocalidad.setActionListeners(this);
        cargarDatos();
        this.guiLocalidad.setListSelectionListener(this);
        this.guiLocalidad.setKeyListener(this);
        Utils.show(guiLocalidad);
    }
    
    public MediadorGestionarLocalidad(MediadorAltaProveedor medAltaProveedor,JDialog guiPadre) {
        this.medAltaProveedor = medAltaProveedor;
        try{
    		this.controlLocalidad = new ControlLocalidad();
        }catch(Exception ex){
        	Utils.manejoErrores(ex,"Error en MediadorGestionarLocalidad. Constructor");
        }
        this.guiLocalidad = new GUIGestionarLocalidad(guiPadre);
        this.guiLocalidad.setActionListeners(this);
        cargarDatos();
        this.guiLocalidad.setListSelectionListener(this);
        this.guiLocalidad.setKeyListener(this); 
        Utils.show(guiLocalidad);
    }
    public MediadorGestionarLocalidad(MediadorModificarProveedor medModProveedor,JDialog guiPadre) {
        this.medModProveedor = medModProveedor;
        try{
        	this.controlLocalidad = new ControlLocalidad();
        }catch(Exception ex){
        	Utils.manejoErrores(ex,"Error en MediadorGestionarLocalidad. Constructor");
        }
        this.guiLocalidad = new GUIGestionarLocalidad(guiPadre);
        this.guiLocalidad.setActionListeners(this);
        cargarDatos();
        this.guiLocalidad.setListSelectionListener(this);
        this.guiLocalidad.setKeyListener(this); 
        Utils.show(guiLocalidad);
    }
    public MediadorGestionarLocalidad(MediadorModificarComercio medModComercio,JDialog guiPadre) {
        this.medModComercio = medModComercio;
        try{
        	this.controlLocalidad = new ControlLocalidad();
        }catch(Exception ex){
        	Utils.manejoErrores(ex,"Error en MediadorGestionarLocalidad. Constructor");
        }
        this.guiLocalidad = new GUIGestionarLocalidad(guiPadre);
        this.guiLocalidad.setActionListeners(this);
        cargarDatos();
        this.guiLocalidad.setListSelectionListener(this);
        this.guiLocalidad.setKeyListener(this);
        Utils.show(guiLocalidad);
    }
    
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
    	if (source == guiLocalidad.getJBCargar()) {
            try {
                new MediadorAltaLocalidad(this,guiLocalidad);
            } catch (Exception ex) {
            	Utils.manejoErrores(ex,"Error en MediadorGestionarLocalidad. ActionPerformed");
            }
        } else if (source == guiLocalidad.getJBMod()) {
            modificar();
        } else if (source == guiLocalidad.getJBCancelar()) {
            guiLocalidad.dispose();
        } else if (source == guiLocalidad.getJBBorrar()) {
            eliminar();
        } else if (source == guiLocalidad.getJBAceptar()) {
        	if(flag){
          	   this.guiLocalidad.dispose();
            } else {
        		if (cargarFilaSeleccionada()) {
        			if (medModCliente != null) {
        				medModCliente.localidad = miLoc;
        				medModCliente.actualizarLocalidad();
        				this.guiLocalidad.dispose();
        			} else if (medAltaCliente != null) {
        				medAltaCliente.localidad = miLoc;
        				medAltaCliente.actualizarLocalidad();
        				this.guiLocalidad.dispose();
        			} else if (medAltaProveedor != null) {
        				medAltaProveedor.localidad = miLoc;
        				medAltaProveedor.actualizarLocalidad();
        				this.guiLocalidad.dispose();
        			} else if (medModProveedor != null) {
        				medModProveedor.localidad = miLoc;
        				medModProveedor.actualizarLocalidad();
        				this.guiLocalidad.dispose();
        			} else if (medModComercio != null) {
        				medModComercio.localidad = miLoc;
        				medModComercio.actualizarLocalidad();
        				this.guiLocalidad.dispose();
        			}
        		}
        	}
        }
    }
    
    public boolean cargarFilaSeleccionada() {
    	boolean result=false;
    	try{
    		if (guiLocalidad.jtDatos.getSelectedRow() == -1) {
    			Utils.advertenciaUsr(guiLocalidad,"Debe seleccionar una Localidad.");
                result = false;
    		}else{
    			Long id = (Long)guiLocalidad.datos[guiLocalidad.jtDatos.getSelectedRow()][0];
    			miLoc = this.controlLocalidad.buscarLocalidad(id);
    			result = true;
    		}
    	} catch (Exception ex){
    		Utils.manejoErrores(ex,"Error en MediadorGestionarLocalidad. CargarFilaSeleccionada");
        }
    	return result;
    }
    
    public void cargarDatos() {
        try {
            Vector localidades = this.controlLocalidad.obtenerLocalidades();
            guiLocalidad.datos = new Object[localidades.size()][guiLocalidad.titulos.length];
            int i = 0;
            for (int j = 0; j < localidades.size(); j++) {
            	Localidad loc=(Localidad) localidades.elementAt(j);
            	    Object[] temp = {loc.getId(),loc.getNombre(),
	                        (String.valueOf(loc.getCodPostal())),loc.getProvincia().getNombre()};
	                guiLocalidad.datos[i] = temp;
	                i++;
            }
    	}catch(Exception ex){
    		Utils.manejoErrores(ex,"Error en MediadorGestionarLocalidad. CargarDatos");
    	}
    	guiLocalidad.jtDatos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	guiLocalidad.actualizarTabla();
        this.guiLocalidad.setMouseListener(this);  
    }
    
    private void actualizarTabla() {
    	try {
            Vector localidades = this.controlLocalidad.obtenerLocalidadesFiltro(guiLocalidad.getJTFBuscador().getText());
            guiLocalidad.datos = new Object[localidades.size()][guiLocalidad.titulos.length];
            for (int j = 0; j < localidades.size(); j++) {
            	Localidad loc=(Localidad) localidades.elementAt(j);
            	Object[] temp = {loc.getId(),loc.getNombre(),
            			(String.valueOf(loc.getCodPostal())),loc.getProvincia().getNombre()};
            	guiLocalidad.datos[j] = temp;
            }
    	} catch(Exception ex) {
    		Utils.manejoErrores(ex,"Error en MediadorGestionarLocalidad. ActualizarTabla");
    	}
	    guiLocalidad.jtDatos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        guiLocalidad.actualizarTabla();
        this.guiLocalidad.setMouseListener(this);  
    }
    
    private void modificar() {
        try {
            if (this.controlLocalidad.obtenerLocalidades().isEmpty()){
            	Utils.advertenciaUsr(guiLocalidad,"No hay Localidades guardadas.");
            } else if (guiLocalidad.jtDatos.getSelectedRow() == -1){
            	Utils.advertenciaUsr(guiLocalidad,"Para poder Modificar un Localidad debe ser previamente seleccionada.");
            } else {
                Long id = (Long)guiLocalidad.datos[guiLocalidad.jtDatos.getSelectedRow()][0];
                Localidad locDTO = (Localidad)this.controlLocalidad.buscarLocalidad(id);
                new MediadorModificarLocalidad(this, locDTO,guiLocalidad);
            }
    	}catch(Exception ex){
    		Utils.manejoErrores(ex,"Error en MediadorGestionarLocalidad. Modificar");
        }
    }
    
    private void eliminar() {
        try{
            if ( this.controlLocalidad.obtenerLocalidades().isEmpty()){
            	Utils.advertenciaUsr(guiLocalidad,"No hay Localidades guardadas.");
            } else {
                if (guiLocalidad.jtDatos.getSelectedRow() == -1){
                	Utils.advertenciaUsr(guiLocalidad,"Para poder Eliminar una Localidad debe seleccionarla previamente.");
                } else {
                    Long id = (Long)guiLocalidad.datos[guiLocalidad.jtDatos.getSelectedRow()][0];
                    String nombre = (String)guiLocalidad.datos[guiLocalidad.jtDatos.getSelectedRow()][1];
                    if (controlLocalidad.localidadAsociada(id)) {
                    	Utils.advertenciaUsr(guiLocalidad,"La Localidad no puede ser borrada.");
                        }else{
                            int prueba = Utils.aceptarCancelarAccion(guiLocalidad,"Esta seguro que desea Eliminar la Localidad "+ nombre);
                            if (prueba == 0)
                                this.controlLocalidad.eliminarLocalidad(id);
                        }     
                    cargarDatos();
                    }
            }
        }catch(Exception ex){
        	Utils.manejoErrores(ex,"Error en MediadorGestionarLocalidad. Eliminar");
        }
    }

    public GUIGestionarLocalidad getGUI() {
        return guiLocalidad;
    }
    
    public void keyReleased(KeyEvent e) {
        actualizarTabla();
    }
    
    public void keyTyped(KeyEvent e) {
    }
    
    public void keyPressed(KeyEvent e) {
    }
    
    public void valueChanged(ListSelectionEvent arg0) { }
    
    public void mouseClicked(MouseEvent arg0) {
		//System.out.println("mouse clicked");
	}

	public void mousePressed(MouseEvent arg0) {
		//System.out.println("mouse pressed");
	}

	public void mouseReleased(MouseEvent arg0) {
		//System.out.println("mouse Released");
		if (arg0.getClickCount() == 2){
			//System.out.println("dobleclick");
			if(!flag){
				if (cargarFilaSeleccionada()) {
					if (medModCliente != null) {
						medModCliente.localidad = miLoc;
						medModCliente.actualizarLocalidad();
						this.guiLocalidad.dispose();
					} else if (medAltaCliente != null) {
						medAltaCliente.localidad = miLoc;
						medAltaCliente.actualizarLocalidad();
						this.guiLocalidad.dispose();
					} else if (medAltaProveedor != null) {
						medAltaProveedor.localidad = miLoc;
						medAltaProveedor.actualizarLocalidad();
						this.guiLocalidad.dispose();
					} else if (medModProveedor != null) {
						medModProveedor.localidad = miLoc;
						medModProveedor.actualizarLocalidad();
						this.guiLocalidad.dispose();
					} else if (medModComercio != null) {
						medModComercio.localidad = miLoc;
						medModComercio.actualizarLocalidad();
						this.guiLocalidad.dispose();
					}
				}
			}
		}
	}

	public void mouseEntered(MouseEvent arg0) {
		//System.out.println("mouse entered");
	}

	public void mouseExited(MouseEvent arg0) {
		//System.out.println("mouse exited");
	}
}




