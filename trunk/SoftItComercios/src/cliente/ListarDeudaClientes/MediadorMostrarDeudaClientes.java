package cliente.ListarDeudaClientes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import persistencia.domain.Comercio;
import persistencia.domain.Localidad;
import server.GestionarCliente.ControlCliente;
import server.GestionarComercio.ControlComercio;
import server.GestionarFacturaCliente.ControlFacturaCliente;
import server.GestionarLocalidad.ControlLocalidad;
import cliente.Principal.GUIReport;

import common.Utils;

public class MediadorMostrarDeudaClientes implements ActionListener,ListSelectionListener {

    public ControlCliente controlCliente;
    public ControlFacturaCliente controlFactCte;
    private ControlComercio controlComercio;
    private ControlLocalidad controlLocalidad;
    private GUIListarDeudaClientes guiDeudaCtes=null;
	Vector todasFacturasCte= new Vector();
	Vector clientes= new Vector();
	Vector saldoFavor = new Vector();
	Vector adeudado= new Vector();
	Localidad loc = null;
	
	public MediadorMostrarDeudaClientes(Long idLoc,JDialog guiPadre) throws Exception {
       try{
    		controlCliente = new ControlCliente();
    		controlComercio = new ControlComercio();
    		controlFactCte = new ControlFacturaCliente();
    		controlLocalidad = new ControlLocalidad();
        }catch(Exception ex){
        	Utils.manejoErrores(ex,"Error en MediadorListarDeudaCliente. Constructor");
        }
        loc = controlLocalidad.buscarLocalidad(idLoc);
        guiDeudaCtes = new GUIListarDeudaClientes(loc.getNombre(),guiPadre);
        guiDeudaCtes.setActionListeners(this);
        guiDeudaCtes.setListSelectionListener(this);
    	cargarDatos();
    	Utils.show(guiDeudaCtes);
        
    }
    
    public void actionPerformed(ActionEvent e) {
    	Object source = e.getSource();
    	if (source == guiDeudaCtes.getJBImprimir()) {
    		try{
    			if(cargarFilasSeleccionadas()){
    				String titulo =" Estado de cuenta de los clientes de "+loc.getNombre();
    				Comercio dist=controlComercio.obtenerComercio();
    				new GUIReport(guiDeudaCtes,16,dist,titulo,clientes,saldoFavor,adeudado);
    			}
    		} catch(Exception ex) {
    			Utils.manejoErrores(ex,"Error en MediadorCuentaCliente. ActionPerformed");
            }
        }else if (source == guiDeudaCtes.getJCheckSelectAll() ) {
        	if(guiDeudaCtes.getJCheckSelectAll().isSelected()){
        	    guiDeudaCtes.jtDatos.selectAll();
        	}else{
        		guiDeudaCtes.jtDatos.clearSelection();
        	}	
       }else if (source == guiDeudaCtes.getJBSalir()) {
   			guiDeudaCtes.dispose();
       }
   }
 
    public boolean cargarFilasSeleccionadas() {
    	boolean result=false;
    	try{
    		if (guiDeudaCtes.jtDatos.getSelectedRow() == -1) {
    			Utils.advertenciaUsr(guiDeudaCtes,"Debe Seleccionar uno o más Clientes.");
    			result=false;
    		}else{
    			result=true;
    			clientes= new Vector();
    			saldoFavor = new Vector();
    			adeudado= new Vector();
    			int[] dattos=guiDeudaCtes.jtDatos.getSelectedRows();
    			
    			for(int i=0;i<dattos.length;i++){
    				clientes.add(guiDeudaCtes.datos[dattos[i]][0]);
    				saldoFavor.add(guiDeudaCtes.datos[dattos[i]][1]);
    				adeudado.add(guiDeudaCtes.datos[dattos[i]][2]);
    	    	}
    		}
     	} catch (Exception ex){
     		Utils.manejoErrores(ex,"Error en MediadorCuentaCliente. CargarFilasSeleccionadas");
        }
     	return result;
    }
    
    public void cargarDatos() {
    	try {
    		Vector clientesDeuda = new Vector();
    		clientesDeuda = this.controlCliente.obtenerClientesDeLocalidadYDeuda(loc.getId());
    		int ctes=clientesDeuda.size()/2;
    		guiDeudaCtes.datos = new Object[ctes][guiDeudaCtes.titulos.length];
    		int i = 0;
    		if(clientesDeuda!=null){
    			for (int j = 0; j < clientesDeuda.size(); j=j+2) {
    				String cte= (String) clientesDeuda.elementAt(j);
    				String deuda = (String) clientesDeuda.elementAt(j+1);
    				String saldoFavor="";
    				String deudaCte="";
    				if(deuda.indexOf("-")==-1)
    					deudaCte=deuda;
    				else
    					saldoFavor=deuda.substring(1);
    				Object[] temp = {cte,saldoFavor,deudaCte};
    					guiDeudaCtes.datos[i] = temp;
    					i++;
    			}
    		}
    	}catch(Exception ex){
    		Utils.manejoErrores(ex,"Error en MediadorListarDeudaCliente. CargarDatos");
    	}
    	guiDeudaCtes.jtDatos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	guiDeudaCtes.actualizarTabla();
    }
    
	public void valueChanged(ListSelectionEvent arg0) {
	}

	}
