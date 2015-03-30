package cliente.CuentaCliente;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import persistencia.domain.Cliente;
import persistencia.domain.Comercio;
import persistencia.domain.FacturaCliente;
import persistencia.domain.MovimientoCaja;
import server.GestionarCliente.ControlCliente;
import server.GestionarComercio.ControlComercio;
import server.GestionarFacturaCliente.ControlFacturaCliente;
import cliente.GestionarCliente.MediadorGestionarCliente;
import cliente.Principal.GUIReport;

import common.Utils;

public class MediadorCuentaCliente implements ActionListener,ListSelectionListener {

    public ControlCliente controlCliente;
    public ControlFacturaCliente controlFactCte;
    private ControlComercio controlComercio;
    private GUICuentaCliente guiCuentaCliente=null; 
	public Cliente cliente=null;
	Vector todasFacturasCte= new Vector();
	Vector detalleIt= new Vector();
	Vector fecha = new Vector();
	Vector debe= new Vector();
	Vector haber= new Vector();
	Vector saldo= new Vector();
	Vector detalleItImpr= new Vector();
	Vector fechaImpr = new Vector();
	Vector debeImpr= new Vector();
	Vector haberImpr= new Vector();
	Vector saldoImpr= new Vector();
	double saldoI=0;
	
	public MediadorCuentaCliente(MediadorGestionarCliente mgc, Cliente cte,JDialog guiPadre) throws Exception {
       try{
    		controlCliente = new ControlCliente();
    		controlComercio = new ControlComercio();
    		controlFactCte = new ControlFacturaCliente();
    		cliente = cte;
    		todasFacturasCte=controlCliente.obtenerFacturasDeCliente(cliente.getNombre());
        }catch(Exception ex){
        	Utils.manejoErrores(ex,"Error en MediadorCuentaCliente. Constructor");
        }
        organizarDatosMostrar();
        guiCuentaCliente = new GUICuentaCliente(detalleIt,fecha,debe,haber,saldo,cliente.getNombre(),guiPadre);
        guiCuentaCliente.setActionListeners(this);
        guiCuentaCliente.setListSelectionListener(this);
        Utils.show(guiCuentaCliente);
    }
    
    public void actionPerformed(ActionEvent e) {
    	Object source = e.getSource();
    	if (source == guiCuentaCliente.getJBImprimir()) {
    		try{
    			if(cargarFilasSeleccionadas()){
    				String titulo="Estado de Cuenta del Cliente:"+cliente.getNombre();
    				Comercio dist=controlComercio.obtenerComercio();
    				String leyenda="Ante saldo negativo (-) el cliente registra deuda, de lo contrario el importe especificado es a favor del cliente";
    				new GUIReport(guiCuentaCliente, 14, dist,titulo,detalleItImpr, fechaImpr,debeImpr,haberImpr,saldoImpr,leyenda);
    			}
    		} catch(Exception ex) {
    			Utils.manejoErrores(ex,"Error en MediadorCuentaCliente. ActionPerformed");
            }
        }else if (source == guiCuentaCliente.getJCheckSelectAll() ) {
        	if(guiCuentaCliente.getJCheckSelectAll().isSelected()){
        	    guiCuentaCliente.jtDatos.selectAll();
        	}else{
        		guiCuentaCliente.jtDatos.clearSelection();
        	}	
       }else if (source == guiCuentaCliente.getJBSalir()) {
        		guiCuentaCliente.dispose();
       }
   }
 
    public boolean cargarFilasSeleccionadas() {
    	boolean result=false;
    	try{
    		if (guiCuentaCliente.jtDatos.getSelectedRow() == -1) {
    			Utils.advertenciaUsr(guiCuentaCliente,"Debe Seleccionar uno o más Movimientos.");
    			result=false;
    		}else{
    			result=true;
    			detalleItImpr= new Vector();
    			fechaImpr = new Vector();
    			debeImpr= new Vector();
    			haberImpr= new Vector();
    			saldoImpr= new Vector();
    			int primerMov=guiCuentaCliente.jtDatos.getSelectedRow();
    			int cantMovs = guiCuentaCliente.jtDatos.getSelectedRowCount();
    			for(int i=primerMov;i<(primerMov+cantMovs);i++){
    				detalleItImpr.add(detalleIt.elementAt(i));
    	    		fechaImpr.add(fecha.elementAt(i));
    	    		debeImpr.add(debe.elementAt(i));
    	    		haberImpr.add(haber.elementAt(i));
    	    		saldoImpr.add(saldo.elementAt(i));
    			}
    		}
     	} catch (Exception ex){
     		Utils.manejoErrores(ex,"Error en MediadorCuentaCliente. CargarFilasSeleccionadas");
        }
     	return result;
    }
    
    public void organizarDatosMostrar() throws Exception{
    	for(int i=0;i<todasFacturasCte.size();i++){
    		FacturaCliente fc = (FacturaCliente) todasFacturasCte.elementAt(i);
    		boolean mostrarDatos=true;
    		if(fc.getTipoFactura().compareTo("RemitoCliente")==0){
    			if(controlFactCte.existeFacturaDeRemito(String.valueOf(fc.getNroFactura())))
    				mostrarDatos=false;
    		}
    		if(mostrarDatos){
    			String remNro="";
				if(fc.getRemitoNro()!=null && fc.getRemitoNro().compareTo("")!=0)
					remNro=" Rem Nro: "+Utils.nroFact(new Long(fc.getRemitoNro()));
    			detalleIt.add(fc.getTipoFactura()+" Nro:"+Utils.nroFact(fc.getNroFactura())+remNro);
    			fecha.add(Utils.getStrUtilDate(fc.getFechaImpresion()));
    			saldoI =Utils.redondear(saldoI-fc.getImporteTotal(),2);
    			debe.add(Utils.ordenarDosDecimales(fc.getImporteTotal()));
    			haber.add(" ");
    			saldo.add(Utils.ordenarDosDecimales(saldoI));
    			if(fc.getCondVenta().compareTo("CONTADO")==0){
    				detalleIt.add("Pago Contado");
					fecha.add(Utils.getStrUtilDate(fc.getFechaImpresion()));
					haber.add(Utils.ordenarDosDecimales(fc.getImporteTotal()));
					debe.add(" ");
					saldoI =Utils.redondear(saldoI+fc.getImporteTotal(),2);
					saldo.add(Utils.ordenarDosDecimales(saldoI));
    			}else{
    				String filtroMov="";
    				Set movs= fc.getComprobantesPago();
    				if(!movs.isEmpty()){
    					for(Iterator it= movs.iterator(); it.hasNext();){
    						MovimientoCaja mc=(MovimientoCaja)it.next();
    						filtroMov+= " (codigo == "+mc.getCodigo()+")";
    						if(i!=movs.size()-1)
    							filtroMov+= " |";
    					}
    					Vector movim=controlCliente.obtenerMovimientosCajaDeFactura(fc.getId());
    					for(int j=0;j<movim.size();j++){
    						MovimientoCaja mc=(MovimientoCaja)movim.elementAt(j);
    						String detalle=" - Registra pago "+mc.getFormaPago();
    						if(mc.getFormaPago().compareTo("CHEQUE")==0) detalle += " nro: "+ mc.getNroCheque();
    						detalleIt.add(detalle);
    						fecha.add(Utils.getStrUtilDate(mc.getFecha()));
    						haber.add(Utils.ordenarDosDecimales(mc.getImporte()));
    						debe.add(" ");
    						saldoI =Utils.redondear(saldoI+mc.getImporte(),2);
    						saldo.add(Utils.ordenarDosDecimales(saldoI));
    					}	
    				}
    			}
    			detalleIt.add(" ");
    			fecha.add(" ");
    			debe.add(" ");
    			haber.add(" ");
    			saldo.add(" ");
    		}
    	}
    	detalleIt.add("SALDO ACTUAL");
    	java.util.Date hoy = new Date();
		fecha.add(Utils.getStrUtilDate(hoy));
		debe.add(" ");
		haber.add(" ");
		saldo.add(Utils.ordenarDosDecimales(saldoI));
    }
    
	public void valueChanged(ListSelectionEvent arg0) {
	}

}
