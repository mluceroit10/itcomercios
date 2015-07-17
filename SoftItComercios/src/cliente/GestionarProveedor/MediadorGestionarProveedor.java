package cliente.GestionarProveedor;

import java.awt.Component;
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
import persistencia.domain.Proveedor;
import server.GestionarProveedor.ControlProveedor;
import cliente.CuentaProveedor.MediadorCuentaProveedor;
import cliente.GestionarFacturaProveedor.MediadorFacturarProveedor;
import cliente.GestionarProducto.MediadorAltaProducto;
import cliente.GestionarProducto.MediadorModificarProducto;
import cliente.Principal.GUIReport;

import common.Utils;
import common.GestionarProveedor.IControlProveedor;

public class MediadorGestionarProveedor implements ActionListener, ListSelectionListener, KeyListener,MouseListener {

    private GUIGestionarProveedor guiProveedor = null;
    protected IControlProveedor controlProveedor;
    public Proveedor socDto=null;
    private boolean flag=false;
  	private Proveedor miProveedorDto;
    private MediadorAltaProducto medAltaProducto;
	private MediadorModificarProducto medModProducto;
	private MediadorFacturarProveedor medFactProv;
		
	public MediadorGestionarProveedor(JFrame guiPadre) {
    	super();
        try{
        	this.controlProveedor = new ControlProveedor();
        }catch(Exception ex){
        	Utils.manejoErrores(ex,"Error en MediadorGestionarProveedor. Constructor");
        }
        this.guiProveedor = new GUIGestionarProveedor(guiPadre);
        this.guiProveedor.setActionListeners(this);
        cargarDatos();
        this.guiProveedor.setListSelectionListener(this);
        this.guiProveedor.setKeyListener(this);
        this.flag=true;
        Utils.show(guiProveedor);
    }
    
	public MediadorGestionarProveedor(MediadorAltaProducto medAP,JDialog guiPadre) {
        this.medAltaProducto = medAP;
        try{
    		this.controlProveedor = new ControlProveedor();
        }catch(Exception ex){
        	Utils.manejoErrores(ex,"Error en MediadorGestionarProveedor. Constructor");
        }
        this.guiProveedor = new GUIGestionarProveedor(guiPadre);
        this.guiProveedor.setActionListeners(this);
        cargarDatos();
        this.guiProveedor.setListSelectionListener(this);
        this.guiProveedor.setKeyListener(this);
        Utils.show(guiProveedor);
    }
   
    public MediadorGestionarProveedor(MediadorModificarProducto medMP, JDialog guiPadre) {
        this.medModProducto = medMP;
        try{
    		this.controlProveedor = new ControlProveedor();
        }catch(Exception ex){
        	Utils.manejoErrores(ex,"Error en MediadorGestionarProveedor. Constructor");
        }
        this.guiProveedor = new GUIGestionarProveedor(guiPadre);
        this.guiProveedor.setActionListeners(this);
        cargarDatos();
        this.guiProveedor.setListSelectionListener(this);
        this.guiProveedor.setKeyListener(this);
        Utils.show(guiProveedor);
    }
    
    public MediadorGestionarProveedor(MediadorFacturarProveedor medFP,JDialog guiPadre) {
        this.medFactProv = medFP;
        try{
    		this.controlProveedor = new ControlProveedor();
        }catch(Exception ex){
        	Utils.manejoErrores(ex,"Error en MediadorGestionarProveedor. Constructor");
        }
        this.guiProveedor = new GUIGestionarProveedor(guiPadre);
        this.guiProveedor.setActionListeners(this);
        cargarDatos();
        this.guiProveedor.setListSelectionListener(this);
        this.guiProveedor.setKeyListener(this);
        Utils.show(guiProveedor);
    }
   
    public void cargarDatos() {
    	try {
            Vector proveedors = this.controlProveedor.obtenerProveedores();
            guiProveedor.datos = new Object[proveedors.size()][guiProveedor.titulos.length];
            int i = 0;
            if(proveedors!=null){
            	for (int j = 0; j < proveedors.size(); j++) {
            		Proveedor cte = (Proveedor) proveedors.elementAt(j);
            		Object[] temp = {cte.getId(),String.valueOf(cte.getCodigo()),cte.getNombre(),
            				cte.getTelefono(),cte.getDireccion(),
            				(((Localidad)cte.getLocalidad()).getNombre())};
            		guiProveedor.datos[i] = temp;
            		i++;
            	}
            }
    	}catch(Exception ex){
    		Utils.manejoErrores(ex,"Error en MediadorGestionarProveedor. CargarDatos");
    	}
    	guiProveedor.tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	guiProveedor.actualizarTabla();
        this.guiProveedor.setMouseListener(this);
    }
    
	public void actionPerformed(ActionEvent e) {
		if ((((Component)e.getSource()).getName().compareTo("Alta")) == 0) {
            try{
                new MediadorAltaProveedor(this,guiProveedor);
            } catch (Exception ex){
            	Utils.manejoErrores(ex,"Error en MediadorGestionarProveedor. ActionPerformed");
            }
        } else if ((((Component)e.getSource()).getName().compareTo("Baja")) == 0){
           eliminar();
        }else if ((((Component)e.getSource()).getName().compareTo("Modificar")) == 0){
            modificar();
        }else if ((((Component)e.getSource()).getName().compareTo("Imprimir")) == 0){
        	try{
        		Vector provs ;
        		String titulo="";
        		provs = controlProveedor.obtenerProveedores();
        		titulo="Listado de Proveedores";
        		new GUIReport(guiProveedor,13,provs,titulo);
        	} catch (Exception ex){
        		Utils.manejoErrores(ex,"Error en MediadorGestionarProveedor. ActionPerformed");
        	}   
       }else if ((((Component)e.getSource()).getName().compareTo("Aceptar")) == 0){
           if(flag){
        	   this.guiProveedor.dispose();
           }else{
        	   if (cargarFilaSeleccionada()) {
        		   if (medModProducto != null) {
        			   medModProducto.proveedor = miProveedorDto;
        			   medModProducto.actualizarProveedor();
        			   this.guiProveedor.dispose();
        		   }else if (medAltaProducto != null) {
        			   medAltaProducto.proveedor = miProveedorDto;
        			   medAltaProducto.actualizarProveedor();
        			   this.guiProveedor.dispose();
        		   }else if (medFactProv != null) {
        			   medFactProv.proveedor = miProveedorDto;
        			   medFactProv.actualizarProveedor();
        			   this.guiProveedor.dispose();
        		   }
        	   }
           }
       }else if ((((Component)e.getSource()).getName().compareTo("Cuenta")) == 0){
    	   obtenerCuentaProveedor();
       }else{ 
    	   if((((Component)e.getSource()).getName().compareTo("Cancelar")) == 0){ 
    		   guiProveedor.dispose();
    	   } 
    	   
       }
	}
	
	public boolean cargarFilaSeleccionada() {
		boolean result=false;
    	try{
    		if (guiProveedor.tabla.getSelectedRow() == -1) {
    			Utils.advertenciaUsr(guiProveedor,"Debe seleccionar un Proveedor.");
                result = false;
    		}else{
    			Long id = (Long)guiProveedor.datos[guiProveedor.tabla.getSelectedRow()][0];
    			miProveedorDto = this.controlProveedor.buscarProveedor(id);
    			result = true;
    		}
    	} catch (Exception ex){
    		Utils.manejoErrores(ex,"Error en MediadorGestionarProveedor. CargarFilaSeleccionada");
        }
    	return result;
    }
    
	private void eliminar() {
        try{
        	if ( this.controlProveedor.obtenerProveedores().isEmpty()){
        		Utils.advertenciaUsr(guiProveedor,"No hay Proveedores guardados.");
            } else {
                if (guiProveedor.tabla.getSelectedRow() == -1){
                	Utils.advertenciaUsr(guiProveedor,"Para poder Eliminar un Proveedor debe seleccionarlo previamente.");
                } else {
                	Long id = (Long)guiProveedor.datos[guiProveedor.tabla.getSelectedRow()][0];
                	String nombre = (String)guiProveedor.datos[guiProveedor.tabla.getSelectedRow()][2];
        			if (controlProveedor.proveedorAsociado(id)) {
                    	Utils.advertenciaUsr(guiProveedor,"El Proveedor no puede ser borrado.");
                    }else{
                    	int prueba = Utils.aceptarCancelarAccion(guiProveedor,"Esta seguro que desea Eliminar el Proveedor "+ nombre);
                    	if (prueba == 0)
                    			this.controlProveedor.eliminarProveedor(id);
                    }
                    cargarDatos();
                }
            }
        }catch(Exception ex){
        	Utils.manejoErrores(ex,"Error en MediadorGestionarProveedor. Eliminar");
        }
    }

	public void modificar(){
    	try {
            if (this.controlProveedor.obtenerProveedores().isEmpty()){
            	Utils.advertenciaUsr(guiProveedor,"No hay Proveedores guardados.");

            } else if (guiProveedor.tabla.getSelectedRow() == -1){
            	Utils.advertenciaUsr(guiProveedor,"Para poder Modificar un Proveedor debe seleccionarlo previamente.");
            } else {
            	Long id = (Long)guiProveedor.datos[guiProveedor.tabla.getSelectedRow()][0];
    			Proveedor provDTO = (Proveedor)this.controlProveedor.buscarProveedor(id);
                new MediadorModificarProveedor(this, provDTO,guiProveedor);
            }
    	}catch(Exception ex){
    		Utils.manejoErrores(ex,"Error en MediadorGestionarProveedor. Modificar");
    	}
    }
   
	public void obtenerCuentaProveedor(){
    	try {
            if (this.controlProveedor.obtenerProveedores().isEmpty()){
            	Utils.advertenciaUsr(guiProveedor,"No hay Clientes guardados en el sistema.");

            } else if (guiProveedor.tabla.getSelectedRow() == -1){
            	Utils.advertenciaUsr(guiProveedor,"Para poder verificar la Cuenta con un Proveedor debe seleccionarlo previamente.");
            } else {
            	Long id = (Long)guiProveedor.datos[guiProveedor.tabla.getSelectedRow()][0];
    			Proveedor prov = (Proveedor)this.controlProveedor.buscarProveedor(id);
            	new MediadorCuentaProveedor(this,prov,guiProveedor);
            }
    	}catch(Exception ex){
    		Utils.manejoErrores(ex,"Error en MediadorGestionarCliente. E cuenta");
    	}
    }
	
	public void valueChanged(ListSelectionEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
		 actualizarTablaConNombre();
	}
    
	private void actualizarTablaConNombre() {
		try {
			Vector proveedors = this.controlProveedor.obtenerProveedoresFiltros(guiProveedor.getJTFBuscadorCodigo().getText(),guiProveedor.getJTFBuscadorNombre().getText());
			guiProveedor.datos = new Object[proveedors.size()][guiProveedor.titulos.length];
			for (int j = 0; j < proveedors.size(); j++) {
				Proveedor cte = (Proveedor) proveedors.elementAt(j);
				Object[] temp = {cte.getId(),String.valueOf(cte.getCodigo()),cte.getNombre(),
						cte.getTelefono(),cte.getDireccion(),
						(((Localidad)cte.getLocalidad()).getNombre())};
				guiProveedor.datos[j] = temp;
			}
		} catch(Exception ex) {
			Utils.manejoErrores(ex,"Error en MediadorGestionarProveedor. ActualizarTablaConNombre");
		}
		guiProveedor.tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		guiProveedor.actualizarTabla();
        this.guiProveedor.setMouseListener(this);
	}
	
	public GUIGestionarProveedor getGUI() {
        return guiProveedor;
    }
	
	public void mouseClicked(MouseEvent arg0) {
	}

	public void mousePressed(MouseEvent arg0) {
	}

	public void mouseReleased(MouseEvent arg0) {
		if (arg0.getClickCount() == 2){
			if(!flag){
				if (cargarFilaSeleccionada()) {
					if (medModProducto != null) {
						medModProducto.proveedor = miProveedorDto;
						medModProducto.actualizarProveedor();
						this.guiProveedor.dispose();
					}else if (medAltaProducto != null) {
						medAltaProducto.proveedor = miProveedorDto;
						medAltaProducto.actualizarProveedor();
						this.guiProveedor.dispose();
					}else if (medFactProv != null) {
						medFactProv.proveedor = miProveedorDto;
						medFactProv.actualizarProveedor();
						this.guiProveedor.dispose();
					}
				}
			}
		}
	}

	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {
	}
  }