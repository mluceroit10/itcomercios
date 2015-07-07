package cliente.GestionarProvincia;

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

import persistencia.domain.Provincia;
import server.GestionarProvincia.ControlProvincia;
import cliente.GestionarLocalidad.MediadorAltaLocalidad;
import cliente.GestionarLocalidad.MediadorModificarLocalidad;

import common.Utils;
import common.GestionarProvincia.IControlProvincia;

public class MediadorGestionarProvincia implements ActionListener, KeyListener, ListSelectionListener,MouseListener {
    
    private GUIGestionarProvincia guiProvincia = null;
    protected IControlProvincia controlProvincia;
    public Provincia miProv;
    private MediadorAltaLocalidad medAltaLocalidad = null;
    private MediadorModificarLocalidad medModLocalidad = null;
    private boolean flag=false;
    
    public MediadorGestionarProvincia(JFrame guiPadre) {
    	try{
    		this.controlProvincia = new ControlProvincia();
        }catch(Exception ex){
        	Utils.manejoErrores(ex,"Error en MediadorGestionarProvincia. Constructor");
        }
        this.guiProvincia = new GUIGestionarProvincia(guiPadre);
        this.guiProvincia.setActionListeners(this);
        cargarDatos();
        this.guiProvincia.setListSelectionListener(this);
        this.guiProvincia.setKeyListener(this);
        this.flag=true;
        Utils.show(guiProvincia);
    }
    
    public MediadorGestionarProvincia(MediadorAltaLocalidad medAltaLoc,JDialog guiPadre) {
        medAltaLocalidad = medAltaLoc;
    	try{
    		this.controlProvincia = new ControlProvincia();
        }catch(Exception ex){
        	Utils.manejoErrores(ex,"Error en MediadorGestionarProvincia. Constructor");
        }
        this.guiProvincia = new GUIGestionarProvincia(guiPadre);
        this.guiProvincia.setActionListeners(this);
        cargarDatos();
        this.guiProvincia.setListSelectionListener(this);
        this.guiProvincia.setKeyListener(this);
        Utils.show(guiProvincia);
    }
    
    public MediadorGestionarProvincia(MediadorModificarLocalidad medModLoc,JDialog guiPadre) {
        medModLocalidad = medModLoc;
        try{
        	this.controlProvincia = new ControlProvincia();
        }catch(Exception ex){
        	Utils.manejoErrores(ex,"Error en MediadorGestionarProvincia. Constructor");
        }
        this.guiProvincia = new GUIGestionarProvincia(guiPadre);
        this.guiProvincia.setActionListeners(this);
        cargarDatos();
        this.guiProvincia.setListSelectionListener(this);
        this.guiProvincia.setKeyListener(this);
        Utils.show(guiProvincia);
    }
    
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
    	if (source == guiProvincia.getJBCargar()) {
            try {
                new MediadorAltaProvincia(this,guiProvincia);
            } catch (Exception ex) {
            	Utils.manejoErrores(ex,"Error en MediadorGestionarProvincia. ActionPerformed");
            }
        } else if (source == guiProvincia.getJBMod()) {
            modificar();
        } else if (source == guiProvincia.getJBCancelar()) {
            guiProvincia.dispose();
        } else if (source == guiProvincia.getJBBorrar()) {
            eliminar();
        } else if (source == guiProvincia.getJBAceptar()) {
        	if(flag){
         	   this.guiProvincia.dispose();
            }
        	else{
        		if (cargarFilaSeleccionada()) {
        			if (medModLocalidad != null) {
        				medModLocalidad.prov = miProv;
        				medModLocalidad.actualizarProvincia();
        				this.guiProvincia.dispose();
        			} else if (medAltaLocalidad != null) {
        				medAltaLocalidad.prov = miProv;
        				medAltaLocalidad.actualizarProvincia();
        				this.guiProvincia.dispose();
        			} 
        		}
        	}
        }
    }
    
    public boolean cargarFilaSeleccionada() {
    	boolean result=false;
    	try{
    		if (guiProvincia.jtDatos.getSelectedRow() == -1) {
    			Utils.advertenciaUsr(guiProvincia,"Debe seleccionar una Provincia.");
                result = false;
    		}else{
    			Long id = (Long)guiProvincia.datos[guiProvincia.jtDatos.getSelectedRow()][0];
    			miProv = this.controlProvincia.buscarProvincia(id);
    			result = true;
    		}
    	} catch (Exception ex){
        	Utils.manejoErrores(ex,"Error en MediadorGestionarProvincia. CargarFilaSeleccionada");
        }
    	return result;
    }
    
    public void cargarDatos() {
        try {
            Vector provincias = this.controlProvincia.obtenerProvincias();
            guiProvincia.datos = new Object[provincias.size()][guiProvincia.titulos.length];
            int i = 0;
            for (int j = 0; j < provincias.size(); j++) {
            	    Object[] temp = {((Provincia) provincias.elementAt(j)).getId(),((Provincia) provincias.elementAt(j)).getNombre()};
	                guiProvincia.datos[i] = temp;
	                i++;
            }
    	}catch(Exception ex){
        	Utils.manejoErrores(ex,"Error en MediadorGestionarProvincia. CargarDatos");
    	}
    	guiProvincia.jtDatos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	guiProvincia.actualizarTabla();
    	this.guiProvincia.setMouseListener(this); 
    }
    
    private void actualizarTabla() {
    	try {
            Vector provincias = this.controlProvincia.obtenerProvinciasFiltro(guiProvincia.getJTFBuscador().getText());
            guiProvincia.datos = new Object[provincias.size()][guiProvincia.titulos.length];
            for (int j = 0; j < provincias.size(); j++) {
            	    	Object[] temp = {((Provincia) provincias.elementAt(j)).getId(),((Provincia) provincias.elementAt(j)).getNombre()};
            	    	guiProvincia.datos[j] = temp;
            }
    	} catch(Exception ex) {
        	Utils.manejoErrores(ex,"Error en MediadorGestionarProvincia. ActualizarTabla");
    	}
	    guiProvincia.jtDatos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        guiProvincia.actualizarTabla();
        this.guiProvincia.setMouseListener(this);  
    }
    
    private void modificar() {
        try {
            if (this.controlProvincia.obtenerProvincias().isEmpty()){
            	Utils.advertenciaUsr(guiProvincia,"No hay Provincias guardadas.");
            } else if (guiProvincia.jtDatos.getSelectedRow() == -1){
            	Utils.advertenciaUsr(guiProvincia,"Para poder Modificar una Provincia debe ser previamente seleccionada.");
            } else {
                Long id = (Long)guiProvincia.datos[guiProvincia.jtDatos.getSelectedRow()][0];
                Provincia pr = (Provincia)this.controlProvincia.buscarProvincia(id);
                new MediadorModificarProvincia(this, pr,guiProvincia);
            }
    	}catch(Exception ex){
    		Utils.manejoErrores(ex,"Error en MediadorGestionarProvincia. Modificar");
        }
    }
    
    private void eliminar() {
        try{
            if ( this.controlProvincia.obtenerProvincias().isEmpty()){
            	Utils.advertenciaUsr(guiProvincia,"No hay Provincies guardadas.");
            } else {
                if (guiProvincia.jtDatos.getSelectedRow() == -1){
                	Utils.advertenciaUsr(guiProvincia,"Para poder Eliminar una Provincia debe seleccionarla previamente.");
                } else {
                	Long id = (Long)guiProvincia.datos[guiProvincia.jtDatos.getSelectedRow()][0];
                    String nombre = (String)guiProvincia.datos[guiProvincia.jtDatos.getSelectedRow()][1];
                    if (controlProvincia.provinciaAsociada(id)) {
                    	Utils.advertenciaUsr(guiProvincia,"La Provincia no puede ser borrada.");
                        }else{
                            int prueba = Utils.aceptarCancelarAccion(guiProvincia,"Esta seguro que desea Eliminar la Provincia "+ nombre);
                            if (prueba == 0)
                                this.controlProvincia.eliminarProvincia(id);
                        }     
                    cargarDatos();
                    }
            }
        }catch(Exception ex){
        	Utils.manejoErrores(ex,"Error en MediadorGestionarProvincia. Eliminar");
        }
    }

    public GUIGestionarProvincia getGUI() {
        return guiProvincia;
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
					if (medModLocalidad != null) {
						medModLocalidad.prov = miProv;
						medModLocalidad.actualizarProvincia();
						this.guiProvincia.dispose();
					} else if (medAltaLocalidad != null) {
						medAltaLocalidad.prov = miProv;
						medAltaLocalidad.actualizarProvincia();
						this.guiProvincia.dispose();
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




