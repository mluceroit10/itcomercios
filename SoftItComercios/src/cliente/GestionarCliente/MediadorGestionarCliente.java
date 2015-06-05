package cliente.GestionarCliente;

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

import persistencia.domain.Cliente;
import persistencia.domain.Localidad;
import server.GestionarCliente.ControlCliente;
import cliente.CuentaCliente.MediadorCuentaCliente;
import cliente.GestionarFacturaCliente.MediadorFacturarCliente;
import cliente.GestionarRemitoCliente.MediadorRemitoCliente;
import cliente.ListarDeudaClientes.MediadorMostrarDeudaClientes;
import cliente.Principal.GUIReport;

import common.Utils;
import common.GestionarCliente.IControlCliente;

public class MediadorGestionarCliente implements ActionListener, ListSelectionListener, KeyListener,MouseListener {

    private GUIGestionarCliente guiCliente = null;
    protected IControlCliente controlCliente;
    public Cliente socDto=null;
    private boolean flag=false;
    private MediadorRemitoCliente medRealizRemito = null;
    private Cliente miClienteDto;
	private MediadorFacturarCliente medFecturarCliente;
	private String tipoCliente="Todos";
	
	public MediadorGestionarCliente(JFrame guiPadre) {
    	super();
        try{  
        	this.controlCliente = new ControlCliente();
        }catch(Exception ex){
        	Utils.manejoErrores(ex,"Error en MediadorGestionarCliente. Constructor");
        }
        this.guiCliente = new GUIGestionarCliente(guiPadre);
        this.guiCliente.setActionListeners(this);
        cargarDatos();
        this.guiCliente.setListSelectionListener(this);
        this.guiCliente.setKeyListener(this);
        this.flag=true;
        Utils.show(guiCliente);
    }
    
	public MediadorGestionarCliente(MediadorFacturarCliente medFC,String tipoFact,JDialog guiPadre) {
        this.medFecturarCliente = medFC;
        try{
        	this.controlCliente = new ControlCliente();
        }catch(Exception ex){
        	Utils.manejoErrores(ex,"Error en MediadorGestionarCliente. Constructor");
        }
        this.guiCliente = new GUIGestionarCliente(guiPadre);
        this.guiCliente.setActionListeners(this);
        if(tipoFact.compareTo("A")==0){
        	tipoCliente="RI";
        }else if(tipoFact.compareTo("B")==0){
        	tipoCliente="Sin_RI";
        }
        cargarDatos();
        this.guiCliente.setListSelectionListener(this);
        this.guiCliente.setKeyListener(this);
        Utils.show(guiCliente);
    }
    
    public MediadorGestionarCliente(MediadorRemitoCliente medRR,JDialog guiPadre) {
        this.medRealizRemito = medRR;
        try{
        	this.controlCliente = new ControlCliente();
        }catch(Exception ex){
        	Utils.manejoErrores(ex,"Error en MediadorGestionarCliente. Constructor");
        }
        this.guiCliente = new GUIGestionarCliente(guiPadre);
        this.guiCliente.setActionListeners(this);
        cargarDatos();
        this.guiCliente.setListSelectionListener(this);
        this.guiCliente.setKeyListener(this);
        Utils.show(guiCliente);
    }
   
	public void cargarDatos() {
    	try {
    		Vector clientes = new Vector();
    		clientes = this.controlCliente.obtenerClientes(tipoCliente);
    		guiCliente.datos = new Object[clientes.size()][guiCliente.titulos.length];
    		if(clientes!=null){
    			for (int j = 0; j < clientes.size(); j++) {
    				Cliente cte = (Cliente) clientes.elementAt(j);
    				Object[] temp = {cte.getId(),cte.getNombre(),cte.getCuit(),categoria(cte.getIvaCl()),
    						cte.getTelefono(),cte.getDireccion(),(((Localidad)cte.getLocalidad()).getNombre())};
    				guiCliente.datos[j] = temp;
    			}
    		}
    	}catch(Exception ex){
    		Utils.manejoErrores(ex,"Error en MediadorGestionarCliente. CargarDatos");
    	}
    	guiCliente.tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	guiCliente.actualizarTabla();
        this.guiCliente.setMouseListener(this);  
    }
    
	public void actionPerformed(ActionEvent e) {
		if ((((Component)e.getSource()).getName().compareTo("Alta")) == 0) {
            try{
                new MediadorAltaCliente(this,guiCliente);
            } catch (Exception ex){
            	Utils.manejoErrores(ex,"Error en MediadorGestionarCliente. ActionPerformed");
            }
        } else if ((((Component)e.getSource()).getName().compareTo("Baja")) == 0){
           eliminar();
        } else if ((((Component)e.getSource()).getName().compareTo("Modificar")) == 0){
            modificar();
        } else if ((((Component)e.getSource()).getName().compareTo("Deudas")) == 0){
            mostrarDeudas();
        } else if ((((Component)e.getSource()).getName().compareTo("Imprimir")) == 0){
        	try{
        		Vector clientes ;
        		String titulo="";
        		clientes = controlCliente.obtenerClientes(tipoCliente);
        		titulo="Listado de Clientes";
        		new GUIReport(guiCliente,12,clientes,titulo);
        	} catch (Exception ex){
        		Utils.manejoErrores(ex,"Error en MediadorGestionarCliente. Imprimir");
        	}
       }else if ((((Component)e.getSource()).getName().compareTo("Aceptar")) == 0){
           if(flag){
        	   this.guiCliente.dispose();
           }
           else{
        	   if (cargarFilaSeleccionada()) {
        		   if (medFecturarCliente != null) {
        			   medFecturarCliente.cliente = miClienteDto;
        			   medFecturarCliente.actualizarCliente();
        			   this.guiCliente.dispose();
        		   }else if (medRealizRemito != null) {
        			   medRealizRemito.cliente = miClienteDto;
        			   medRealizRemito.actualizarCliente();
        			   this.guiCliente.dispose();
        		   }
        	   }
           }
        }else if ((((Component)e.getSource()).getName().compareTo("Cuenta")) == 0){
            obtenerCuentaCliente();
         }
        else{ 
        	if((((Component)e.getSource()).getName().compareTo("Cancelar")) == 0){ 
        		guiCliente.dispose();
        	} 
        	
        }
	}
	
	public boolean cargarFilaSeleccionada() {
		boolean result=false;
    	try{
    		if (guiCliente.tabla.getSelectedRow() == -1) {
    			Utils.advertenciaUsr(guiCliente,"Debe seleccionar un Cliente.");
                result = false;
    		}else{
    			Long id = (Long)guiCliente.datos[guiCliente.tabla.getSelectedRow()][0];
    			miClienteDto = this.controlCliente.buscarCliente(id);
    			result = true;
    		}
    	} catch (Exception ex){
    		Utils.manejoErrores(ex,"Error en MediadorGestionarCliente. CargarFilaSeleccionada");
        }
    	return result;
    }
    
	private void eliminar() {
        try{
        	if ( this.controlCliente.obtenerClientes(tipoCliente).isEmpty()){
        		Utils.advertenciaUsr(guiCliente,"No hay Clientes guardados.");
            } else {
                if (guiCliente.tabla.getSelectedRow() == -1){
                	Utils.advertenciaUsr(guiCliente,"Para poder Eliminar un Cliente debe seleccionarlo previamente");
                } else {
                	Long id = (Long)guiCliente.datos[guiCliente.tabla.getSelectedRow()][0];
                	String nombre = (String)guiCliente.datos[guiCliente.tabla.getSelectedRow()][1];
                	if (controlCliente.clienteAsociado(id)) {
                		Utils.advertenciaUsr(guiCliente,"El Cliente no puede ser borrado.");
                	}else{
                		int prueba = Utils.aceptarCancelarAccion(guiCliente,"Esta seguro que desea Eliminar el Cliente \n"+ nombre);
                		if (prueba == 0)
                			this.controlCliente.eliminarCliente(id);
                	}     
                	cargarDatos();
                }
            }
        }catch(Exception ex){
        	Utils.manejoErrores(ex,"Error en MediadorGestionarCliente. Eliminar");
        }
    }

	public void modificar(){
    	try {
            if (this.controlCliente.obtenerClientes(tipoCliente).isEmpty()){
            	Utils.advertenciaUsr(guiCliente,"No hay Clientes guardados en el sistema.");

            } else if (guiCliente.tabla.getSelectedRow() == -1){
            	Utils.advertenciaUsr(guiCliente,"Para poder Modificar un Cliente debe seleccionarlo previamente.");
            } else {
            	Long id = (Long)guiCliente.datos[guiCliente.tabla.getSelectedRow()][0];
    			Cliente socDTO = (Cliente)this.controlCliente.buscarCliente(id);
                new MediadorModificarCliente(this, socDTO,guiCliente);
            }
    	}catch(Exception ex){
    		Utils.manejoErrores(ex,"Error en MediadorGestionarCliente. Modificar");
    	}
    }
   
	public void obtenerCuentaCliente(){
    	try {
            if (this.controlCliente.obtenerClientes(tipoCliente).isEmpty()){
            	Utils.advertenciaUsr(guiCliente,"No hay Clientes guardados en el sistema.");

            } else if (guiCliente.tabla.getSelectedRow() == -1){
            	Utils.advertenciaUsr(guiCliente,"Para poder verificar la Cuenta de un Cliente debe seleccionarlo previamente.");
            } else {
            	Long id = (Long)guiCliente.datos[guiCliente.tabla.getSelectedRow()][0];
    			Cliente socDTO = (Cliente)this.controlCliente.buscarCliente(id);
                new MediadorCuentaCliente(this,socDTO,guiCliente);
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
			Vector clientes = new Vector();
			clientes = this.controlCliente.obtenerClientesFiltro(tipoCliente,guiCliente.getJTFBuscador().getText());
			guiCliente.datos = new Object[clientes.size()][guiCliente.titulos.length];
			for (int j = 0; j < clientes.size(); j++) {
				Cliente cte = (Cliente) clientes.elementAt(j);
				Object[] temp = {cte.getId(),cte.getNombre(),cte.getCuit(),categoria(cte.getIvaCl()),
						cte.getTelefono(),cte.getDireccion(),(((Localidad)cte.getLocalidad()).getNombre())};
				guiCliente.datos[j] = temp;
			}
			
		} catch(Exception ex) {
			Utils.manejoErrores(ex,"Error en MediadorGestionarCliente. ActualizarTablaConNombre");
		}
		guiCliente.tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		guiCliente.actualizarTabla();
        this.guiCliente.setMouseListener(this);  
	}
	
	public GUIGestionarCliente getGUI() {
        return guiCliente;
    }
	
	private String categoria(String categ){
		String c="";
		if(categ.compareTo("Resp. Inscripto")==0)
			c = "R. Inscr.";
		if(categ.compareTo("Resp. NO Inscripto")==0)
			c = "R. NO I.";
		if(categ.compareTo("Exento")==0)
			c = "Exento";
		if(categ.compareTo("NO Resp.")==0)
			c = "No Resp.";
		if(categ.compareTo("Consumidor Final")==0)
			c = "C. Final";
		if(categ.compareTo("Monotributo")==0)
			c = "Monotrib.";
		return c;
		
	}
	

	public void mostrarDeudas(){
    	try {
    		MediadorMostrarDeudaClientes msprod = new MediadorMostrarDeudaClientes(guiCliente);
	//		msprod.show();
    	}catch(Exception ex){
    		Utils.manejoErrores(ex,"Error en MediadorGestionarCliente. Modificar");
    	}
    }
   
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
					if (medFecturarCliente != null) {
						medFecturarCliente.cliente = miClienteDto;
						medFecturarCliente.actualizarCliente();
						this.guiCliente.dispose();
					}else if (medRealizRemito != null) {
						medRealizRemito.cliente = miClienteDto;
						medRealizRemito.actualizarCliente();
						this.guiCliente.dispose();
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