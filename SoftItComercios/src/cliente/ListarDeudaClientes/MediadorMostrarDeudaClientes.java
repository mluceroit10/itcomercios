package cliente.ListarDeudaClientes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import persistencia.domain.Cliente;
import persistencia.domain.Comercio;
import persistencia.domain.Localidad;
import server.GestionarCliente.ControlCliente;
import server.GestionarComercio.ControlComercio;
import server.GestionarFacturaCliente.ControlFacturaCliente;
import cliente.Principal.GUIReport;

import common.Utils;

public class MediadorMostrarDeudaClientes implements ActionListener,ListSelectionListener {

    public ControlCliente controlCliente;
    public ControlFacturaCliente controlFactCte;
    private ControlComercio controlComercio;
    private GUIListarDeudaClientes guiDeudaCtes=null;
	Vector todasFacturasCte= new Vector();
	Vector clientes= new Vector();
	Vector fechasUF = new Vector();
	Vector saldoFavor = new Vector();
	Vector adeudado= new Vector();
	Localidad loc = null;  
	
	public MediadorMostrarDeudaClientes(JDialog guiPadre) throws Exception {
       try{
    		controlCliente = new ControlCliente();
    		controlComercio = new ControlComercio();
    		controlFactCte = new ControlFacturaCliente();
        }catch(Exception ex){
        	Utils.manejoErrores(ex,"Error en MediadorListarDeudaCliente. Constructor");
        }
        guiDeudaCtes = new GUIListarDeudaClientes(guiPadre);
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
    				String titulo =" Estado de cuenta de los clientes ";
    				Comercio dist=controlComercio.obtenerComercio();
    				new GUIReport(guiDeudaCtes,16,dist,titulo,clientes,fechasUF,saldoFavor,adeudado);
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
    			fechasUF= new Vector();
    			saldoFavor = new Vector();
    			adeudado= new Vector();
    			int[] dattos=guiDeudaCtes.jtDatos.getSelectedRows();
    			
    			for(int i=0;i<dattos.length;i++){
    				clientes.add(guiDeudaCtes.datos[dattos[i]][0]);
    				fechasUF.add(guiDeudaCtes.datos[dattos[i]][1]);
    				saldoFavor.add(guiDeudaCtes.datos[dattos[i]][2]);
    				adeudado.add(guiDeudaCtes.datos[dattos[i]][3]);
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
    		clientesDeuda = this.controlCliente.obtenerClientesDeuda();
    		guiDeudaCtes.datos = new Object[clientesDeuda.size()][guiDeudaCtes.titulos.length];
    		int i = 0;
    		for (int j = 0; j < clientesDeuda.size(); j++) {
    			Cliente cte = (Cliente) clientesDeuda.elementAt(j);
    			String fecha = Utils.getStrUtilDate(cte.getFechaUF());
    			double deuda = cte.getDeuda();
				String saldoFavor="";
				String deudaCte="";
				if(deuda>0)// es nro positivo - adeuda
					deudaCte=Utils.ordenarDosDecimales(deuda);
				else					  //es nro negativo - a favor
					saldoFavor=Utils.ordenarDosDecimales(-deuda);
				
    			Object[] temp = {cte.getNombre(),fecha,saldoFavor,deudaCte};
    			guiDeudaCtes.datos[j] = temp;
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
