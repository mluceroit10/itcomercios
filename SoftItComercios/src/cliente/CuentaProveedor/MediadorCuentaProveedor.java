package cliente.CuentaProveedor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import persistencia.domain.Comercio;
import persistencia.domain.FacturaProveedor;
import persistencia.domain.MovimientoCaja;
import persistencia.domain.Proveedor;
import server.GestionarComercio.ControlComercio;
import server.GestionarFacturaCliente.ControlFacturaCliente;
import server.GestionarProveedor.ControlProveedor;
import cliente.GestionarProveedor.MediadorGestionarProveedor;
import cliente.Principal.GUIReport;

import common.Utils;

public class MediadorCuentaProveedor implements ActionListener,ListSelectionListener {

    public ControlProveedor controlProveedor;
    public ControlFacturaCliente controlFactCte;
    private ControlComercio controlComercio;
    private GUICuentaProveedor guiCuentaProveedor=null;
	public Proveedor proveedor=null;
	Vector todasFacturasCte= new Vector();  
	Vector detalleIt= new Vector();
	Vector fecha = new Vector();
	Vector debe= new Vector();
	Vector haber= new Vector();
	Vector saldoF= new Vector();
	Vector saldoT= new Vector();
	Vector detalleItImpr= new Vector();
	Vector fechaImpr = new Vector();
	Vector debeImpr= new Vector();
	Vector haberImpr= new Vector();
	Vector saldoImprF= new Vector();
	Vector saldoImprT= new Vector();
	double saldoI=0;
	
	public MediadorCuentaProveedor(MediadorGestionarProveedor mgp, Proveedor prov,JDialog guiPadre) throws Exception {
       try{
    	    controlProveedor = new ControlProveedor();
    		controlComercio = new ControlComercio();
    		controlFactCte = new ControlFacturaCliente();
    		proveedor = prov;
    		todasFacturasCte=controlProveedor.obtenerFacturasDeProveedor(prov.getNombre());
        }catch(Exception ex){
        	Utils.manejoErrores(ex,"Error en MediadorCuentaCliente. Constructor");
        }
        organizarDatosMostrar();
        guiCuentaProveedor = new GUICuentaProveedor(detalleIt,fecha,debe,haber,saldoF,saldoT,proveedor.getNombre(),guiPadre);
        guiCuentaProveedor.setActionListeners(this);
        guiCuentaProveedor.setListSelectionListener(this);
        Utils.show(guiCuentaProveedor);
    }
    
    public void actionPerformed(ActionEvent e) {
    	Object source = e.getSource();
    	if (source == guiCuentaProveedor.getJBImprimir()) {
    		try{
    			if(cargarFilasSeleccionadas()){
    				String titulo="Estado de Cuenta con el Proveedor:"+proveedor.getNombre();
    				Object[] valoresPosibles = {"Saldo por Factura", "Saldo Total con el Proveedor"};
    				String prov = Utils.seleccionarOpcion(guiCuentaProveedor,"Seleccione Tipo de Saldo", "Criterios de Impresión",valoresPosibles,0);
        			if(prov!=null){
        				Vector saldoImpr=null;
        				if(prov.compareTo("Saldo por Factura")==0)
        					saldoImpr=saldoImprF;
        				else if(prov.compareTo("Saldo Total con el Proveedor")==0)
        					saldoImpr=saldoImprT;
        				Comercio dist=controlComercio.obtenerComercio();
        				String leyenda="Ante saldo negativo (-) se registra deuda con el Proveedor, de lo contrario el importe especificado es a favor.";
        				new GUIReport(guiCuentaProveedor, 14,dist,titulo,detalleItImpr, fechaImpr,debeImpr,haberImpr,saldoImpr,leyenda);
        			}
    			}
    		} catch(Exception ex) {
    			Utils.manejoErrores(ex,"Error en MediadorCuentaCliente. ActionPerformed");
            }
        }else if (source == guiCuentaProveedor.getJCheckSelectAll() ) {
        	if(guiCuentaProveedor.getJCheckSelectAll().isSelected()){
        	    guiCuentaProveedor.jtDatos.selectAll();
        	}else{
        		guiCuentaProveedor.jtDatos.clearSelection();
        	}	
       }else if (source == guiCuentaProveedor.getJBSalir()) {
        		guiCuentaProveedor.dispose();
       }
   }
 
    public boolean cargarFilasSeleccionadas() {
    	boolean result=false;
    	try{
    		if (guiCuentaProveedor.jtDatos.getSelectedRow() == -1) {
    			Utils.advertenciaUsr(guiCuentaProveedor,"Debe Seleccionar uno o más Movimientos.");
    			result=false;
    		}else{
    			result=true;
    			detalleItImpr= new Vector();
    			fechaImpr = new Vector();
    			debeImpr= new Vector();
    			haberImpr= new Vector();
    			saldoImprF= new Vector();
    			saldoImprT= new Vector();
    			int primerMov=guiCuentaProveedor.jtDatos.getSelectedRow();
    			int cantMovs = guiCuentaProveedor.jtDatos.getSelectedRowCount();
    			for(int i=primerMov;i<(primerMov+cantMovs);i++){
    				detalleItImpr.add(detalleIt.elementAt(i));
    	    		fechaImpr.add(fecha.elementAt(i));
    	    		debeImpr.add(debe.elementAt(i));
    	    		haberImpr.add(haber.elementAt(i));
    	    		saldoImprF.add(saldoF.elementAt(i));
    	    		saldoImprT.add(saldoT.elementAt(i));
    			}
    		}
     	} catch (Exception ex){
     		Utils.manejoErrores(ex,"Error en MediadorCuentaProveedor. CargarFilasSeleccionadas");
        }
     	return result;
    }
    
    public void organizarDatosMostrar() throws Exception{
    	for(int i=0;i<todasFacturasCte.size();i++){
    		FacturaProveedor fc = (FacturaProveedor) todasFacturasCte.elementAt(i);
    		String verifCargaParcial="";
    		if(fc.isCargaParcial())
    			verifCargaParcial="(C. Parcial) ";
    		detalleIt.add(verifCargaParcial+"Fact. Nro:"+Utils.nroFact(fc.getNroFactura()));
    		fecha.add(Utils.getStrUtilDate(fc.getFecha()));
    		saldoI =Utils.redondear(saldoI-fc.getImporteTotal(),2);
    		debe.add(Utils.ordenarDosDecimales(fc.getImporteTotal()));
    		haber.add(" ");
    		double saldoProv=-fc.getImporteTotal();
    		saldoF.add(String.valueOf(saldoProv));
    		saldoT.add(Utils.ordenarDosDecimales(saldoI));
    		String filtroMov="";
    		Set movs= fc.getComprobantesPago();
    		if(!movs.isEmpty()){
    			for(Iterator it= movs.iterator(); it.hasNext();){
    				MovimientoCaja mc=(MovimientoCaja)it.next();
    				filtroMov+= " (codigo == "+mc.getCodigo()+")";
    				if(i!=movs.size()-1)
    					filtroMov+= " |";
    			}
    			Vector movim=controlProveedor.obtenerMovimientosCajaDeFactura(fc.getId());
    			for(int j=0;j<movim.size();j++){
    				MovimientoCaja mc=(MovimientoCaja)movim.elementAt(j);
    				String detalle=" - Registra pago "+mc.getFormaPago();
    				if(mc.getFormaPago().compareTo("CHEQUE")==0) detalle += " nro: "+ mc.getNroCheque();
    				detalleIt.add(detalle);
    				fecha.add(Utils.getStrUtilDate(mc.getFecha()));
    				haber.add(Utils.ordenarDosDecimales(mc.getImporte()));
    				debe.add(" ");
    				saldoI =Utils.redondear(saldoI+mc.getImporte(),2);
    				saldoProv=Utils.redondear(saldoProv+mc.getImporte(),2);
    				saldoF.add(Utils.ordenarDosDecimales(saldoProv));
    				saldoT.add(Utils.ordenarDosDecimales(saldoI));
    			
    			}	
    		}
    		detalleIt.add(" ");
    		fecha.add(" ");
    		debe.add(" ");
    		haber.add(" ");
    		saldoF.add(" ");
    		saldoT.add(" ");
    	}
    	detalleIt.add("SALDO ACTUAL");
    	java.util.Date hoy = new Date();
		fecha.add(Utils.getStrUtilDate(hoy));
		debe.add(" ");
		haber.add(" ");
		saldoF.add(" ");
		saldoT.add(Utils.ordenarDosDecimales(saldoI));
    }
    
	public void valueChanged(ListSelectionEvent arg0) {
	}

}
