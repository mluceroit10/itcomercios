package cliente.ListarProductosFacturados;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import persistencia.domain.Comercio;
import persistencia.domain.FacturaCliente;
import persistencia.domain.Localidad;
import server.GestionarComercio.ControlComercio;
import server.GestionarFacturaCliente.ControlFacturaCliente;
import server.GestionarLocalidad.ControlLocalidad;
import cliente.Principal.GUIReport;

import common.Utils;

public class MediadorVistaFacturasGeneradas implements ActionListener,ListSelectionListener {
	
	public ControlFacturaCliente controlFacturaCliente;
    private ControlComercio controlComercio;
    private ControlLocalidad controlLocalidad;
	private GUIVistaFacturasGeneradas guiVistaFG=null;
	private Date fecha;
	Vector ids= new Vector();
	Localidad loc = null;
	
	public MediadorVistaFacturasGeneradas(java.util.Date f,Long idLoc,JDialog guiPadre) throws Exception {
		try{
    		this.controlFacturaCliente = new ControlFacturaCliente();
    		controlComercio = new ControlComercio();
    		controlLocalidad = new ControlLocalidad();
    		fecha= Utils.crearFecha(f);
    	}catch(Exception ex){
    		Utils.manejoErrores(ex,"Error en MediadorGestionarLocalidad. Constructor");
        }
    	 loc = controlLocalidad.buscarLocalidad(idLoc);
        this.guiVistaFG = new GUIVistaFacturasGeneradas(common.Utils.getStrUtilDate(f),loc.getNombre(),guiPadre);
        this.guiVistaFG.setActionListeners(this);
        this.guiVistaFG.setListSelectionListener(this);
        cargarDatos();
        Utils.show(guiVistaFG);
   }
		
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
    	if (source == guiVistaFG.getJBImprimir()) {
    		try{
    			if(cargarFilasSeleccionadas()){
    				String titulo="Listado de Productos Facturados el "+common.Utils.getStrUtilDate(fecha)+" para "+loc.getNombre();
    				Comercio dist=controlComercio.obtenerComercio();
    				Vector datos= new Vector(); //elem 1 vector de productos Codigo elem 2 vector + nombre producto vector 3 cantidad.
    					datos=controlFacturaCliente.obtenerListadoProductosFacturados(ids);
    					int elems = Integer.parseInt((String)datos.elementAt(0));
    					int [] codigos= (int[]) datos.elementAt(1);
    					String [] productos= (String[]) datos.elementAt(2);
    					String[] proveedores = (String[]) datos.elementAt(3);
    					int [] cantidades= (int[]) datos.elementAt(4);
    					double [] kilos= (double[]) datos.elementAt(5);
    				new GUIReport(guiVistaFG,17,dist,titulo,elems,codigos, productos,proveedores,cantidades,kilos);
    			}
    		} catch(Exception ex) {
    			Utils.manejoErrores(ex,"Error en MediadorCuentaCliente. ActionPerformed");
            }
        }else if (source == guiVistaFG.getJCheckSelectAll() ) {
        	if(guiVistaFG.getJCheckSelectAll().isSelected()){
        	    guiVistaFG.jtDatos.selectAll();
        	}else{
        		guiVistaFG.jtDatos.clearSelection();
        	}	
       }else if (source == guiVistaFG.getJBSalir()) {
   			guiVistaFG.dispose();
       }
	}
	
	public boolean cargarFilasSeleccionadas() {
    	boolean result=false;
    	try{
    		if (guiVistaFG.jtDatos.getSelectedRow() == -1) {
    			Utils.advertenciaUsr(guiVistaFG,"Debe Seleccionar una o más Facturas.");
    			result=false;
    		}else{
    			result=true;
    			ids= new Vector();
    			int[] dattos=guiVistaFG.jtDatos.getSelectedRows();
    			for(int i=0;i<dattos.length;i++){
    				ids.add(guiVistaFG.datos[dattos[i]][0]);
    			}
    		}
     	} catch (Exception ex){
     		Utils.manejoErrores(ex,"Error en MediadorCuentaCliente. CargarFilasSeleccionadas");
        }
     	return result;
    }

	public void valueChanged(ListSelectionEvent arg0) {
	}
	
	public void cargarDatos() {
    	try {
    		Vector facts = new Vector();
    		facts = this.controlFacturaCliente.obtenerFacturasClienteFechaLoc(fecha,loc.getId());
    		
    		guiVistaFG.datos = new Object[facts.size()][guiVistaFG.titulos.length];
    		int i = 0;
    		if(facts!=null){
    			for (int j = 0; j < facts.size(); j++) {
    				FacturaCliente fc = (FacturaCliente) facts.elementAt(j);
    				Object[] temp = {fc.getId(),
    						fc.getTipoFactura(),
    						Utils.nroFact(fc.getNroFactura()),
    						fc.getCliente().getNombre()};
    				guiVistaFG.datos[i] = temp;
    				i++;
    			}
    		}
    	}catch(Exception ex){
    		Utils.manejoErrores(ex,"Error en MediadorListarDeudaCliente. CargarDatos");
    	}
    	guiVistaFG.jtDatos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	guiVistaFG.actualizarTabla();
    }
		 
}
