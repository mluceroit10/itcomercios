package cliente.ListarRemitosCliente;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import persistencia.domain.Cliente;
import persistencia.domain.Comercio;
import persistencia.domain.FacturaCliente;
import persistencia.domain.ItemFactura;
import persistencia.domain.MovimientoCaja;
import server.GestionarCliente.ControlCliente;
import server.GestionarComercio.ControlComercio;
import server.GestionarFacturaCliente.ControlFacturaCliente;
import cliente.GestionarFacturaCliente.MediadorFacturarCliente;
import cliente.GestionarMovimientoCaja.MediadorAltaMovimientoCaja;
import cliente.Principal.GUIReport;

import common.Utils;

public class MediadorListarRemitosCliente implements ActionListener, KeyListener, ListSelectionListener {
    
    private GUIListarRemitosCliente guiTodasFactCte = null;
    protected ControlFacturaCliente controlFactCte;
    private FacturaCliente miFactCte;
    private ControlComercio controlComercio;
	private ControlCliente controlCliente;
	private FacturaCliente fact;
	private MediadorFacturarCliente medFecturarCliente;
	private boolean flag=false;
	private MediadorAltaMovimientoCaja medAltaMovCaja;
	private boolean listarSinFact=false;
	private int diaLI;
	private int mesLI;
	private int anioLI;
	
    public MediadorListarRemitosCliente(int dia, int mes, int anio,JFrame guiPadre) {
    	try{
    		diaLI=dia;
    		mesLI=mes;
    		anioLI=anio;
    		controlFactCte = new ControlFacturaCliente();
    		controlComercio = new ControlComercio();
    		controlCliente = new ControlCliente();
        }catch(Exception ex){
        	Utils.manejoErrores(ex,"Error en MediadorListarRemitosCliente. Constructor");
        }
        listarSinFact=false;
        this.guiTodasFactCte = new GUIListarRemitosCliente(diaLI,mesLI,anioLI,guiPadre);
        this.guiTodasFactCte.setActionListeners(this);
        cargarDatos();
        this.guiTodasFactCte.setListSelectionListener(this);
        this.guiTodasFactCte.setKeyListener(this);
        this.flag=true;
        Utils.show(guiTodasFactCte);
    }
    
    public MediadorListarRemitosCliente(MediadorFacturarCliente medFC,int dia, int mes, int anio,JFrame guiPadre) {
    	this.medFecturarCliente = medFC;
    	try{
    		diaLI=dia;
    		mesLI=mes;
    		anioLI=anio;
    		controlFactCte = new ControlFacturaCliente();
    		controlComercio = new ControlComercio();
    		controlCliente = new ControlCliente();
        }catch(Exception ex){
        	Utils.manejoErrores(ex,"Error en MediadorListarRemitosCliente. Constructor");
        }
        listarSinFact=true;
        this.guiTodasFactCte = new GUIListarRemitosCliente(diaLI,mesLI,anioLI,guiPadre);
        this.guiTodasFactCte.setActionListeners(this);
        cargarDatos();
        this.guiTodasFactCte.setListSelectionListener(this);
        this.guiTodasFactCte.setKeyListener(this);
        Utils.show(guiTodasFactCte);
    }
    
    public MediadorListarRemitosCliente(MediadorAltaMovimientoCaja medAMC,int dia,int mes, int anio,JDialog guiPadre) {
    	this.medAltaMovCaja = medAMC;
    	try{
    		diaLI=dia;
    		mesLI=mes;
    		anioLI=anio;
    		controlFactCte = new ControlFacturaCliente();
    		controlComercio = new ControlComercio();
    		controlCliente = new ControlCliente();
        }catch(Exception ex){
        	Utils.manejoErrores(ex,"Error en MediadorListarRemitosCliente. Constructor");
        }
        listarSinFact=true;
        this.guiTodasFactCte = new GUIListarRemitosCliente(diaLI,mesLI,anioLI,guiPadre);
        this.guiTodasFactCte.setActionListeners(this);
        cargarDatos();
        this.guiTodasFactCte.setListSelectionListener(this);
        this.guiTodasFactCte.setKeyListener(this);
        Utils.show(guiTodasFactCte);
    }
    
    
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == guiTodasFactCte.getJBImprimir()) {
    		try {
    			if (guiTodasFactCte.jtDatos.getSelectedRow() == -1){
    				Utils.advertenciaUsr(guiTodasFactCte,"Para poder Imprimir un Remito debe ser previamente seleccionado.");
                }else{
                	Long id = (Long)guiTodasFactCte.datos[guiTodasFactCte.jtDatos.getSelectedRow()][0];
                	miFactCte = this.controlFactCte.buscarFacturaCliente(id);
                	Vector productos=new Vector();
                	Vector cantProd=new Vector();
                	Vector kilos= new Vector();
                	Vector prUnit=new Vector();
                	Vector descuentos=new Vector();
                	Vector prTotal=new Vector();
                	java.util.Set items=miFactCte.getItems();
                	for(Iterator j=items.iterator();j.hasNext();){
                		ItemFactura pr= (ItemFactura) j.next();
                		productos.add(pr.getProducto());
                		cantProd.add(String.valueOf(pr.getCantidad()));
                		kilos.add(Utils.ordenarTresDecimales(pr.getKilos()));
                		prUnit.add(Utils.ordenarDosDecimales(pr.getPrUnit()));
                		descuentos.add(String.valueOf(pr.getDescuento()));
                		prTotal.add(Utils.ordenarDosDecimales(pr.getPrTotal()));
                	}	
                	Cliente cte=controlCliente.buscarCliente(miFactCte.getCliente().getId());
                	Comercio dist=controlComercio.obtenerComercio();
                	new GUIReport(guiTodasFactCte,4,productos,cantProd,kilos,prUnit,descuentos,prTotal, Utils.nroFact(miFactCte.getNroFactura()),miFactCte.getFechaImpresion(),
                			dist, cte,"","","","","",0,"",miFactCte.getImporteTotal());
                }
    		}
    		catch(Exception ex) {
    			Utils.manejoErrores(ex,"Error en MediadorListarRemitosCliente. ActionPerformed");
    		}
        }else if (source == guiTodasFactCte.getJBAnularFactura()){
	         anularFactura();
        }else if (source == guiTodasFactCte.getJBCambiarPeriodo()){
			String anioB = guiTodasFactCte.getJTFAnio().getText();
			if(anioB.length()==0){
				Utils.advertenciaUsr(guiTodasFactCte,"Por favor ingrese el Año.");
			}else if(anioB.length()!=4){
				Utils.advertenciaUsr(guiTodasFactCte,"El año debe ser un número de 4 dígitos.");
			}else{
				anioLI= Integer.parseInt(anioB);
				mesLI = guiTodasFactCte.getJCBMes().getSelectedIndex()+1; //para que el numero del indice de con el mes sumo 1
				diaLI = guiTodasFactCte.getJCBDia().getSelectedIndex()+1; //para que el numero del indice de con el dia sumo 1
				actualizarCampos();
			} 	
        }else if (source == guiTodasFactCte.getJBCambiarPeriodo()){
        	String anioB = guiTodasFactCte.getJTFAnio().getText();
        	if(anioB.length()==0){
				Utils.advertenciaUsr(guiTodasFactCte,"Por favor ingrese el Año.");
			}else if(anioB.length()!=4){
				Utils.advertenciaUsr(guiTodasFactCte,"El año debe ser un número de 4 dígitos.");
			}else{
				anioLI= Integer.parseInt(anioB);
				mesLI = guiTodasFactCte.getJCBMes().getSelectedIndex()+1; //para que el numero del indice de con el mes sumo 1
	         	cargarDatos();
			} 		         
        }else if (source == guiTodasFactCte.getJBSalir()){
        	 if(flag){
          	   this.guiTodasFactCte.dispose();
             }
             else{
          	   if (cargarFilaSeleccionada()) {
          		   if (medFecturarCliente != null) {
          			   medFecturarCliente.actualizarRemito(fact);
          			   this.guiTodasFactCte.dispose();
          		   }
          		   if (medAltaMovCaja != null) {
          			   medAltaMovCaja.factura = fact;
          			   medAltaMovCaja.tipoFact = fact.getTipoFactura();
          			   medAltaMovCaja.actualizarFactura();
           			   this.guiTodasFactCte.dispose();
           		   }
          	   }
             }
        }else { 
        	guiTodasFactCte.dispose();
    	}
    }
    
    public boolean cargarFilaSeleccionada() {
		boolean result=false;
    	try{
    		if (guiTodasFactCte.jtDatos.getSelectedRow() == -1) {
    			Utils.advertenciaUsr(guiTodasFactCte,"Debe seleccionar un Remito.");
                result = false;
    		}else{
    			Long id = (Long)guiTodasFactCte.datos[guiTodasFactCte.jtDatos.getSelectedRow()][0];
            	fact = this.controlFactCte.buscarFacturaCliente(id);
    			result = true;
    		}
    	} catch (Exception ex){
    		Utils.manejoErrores(ex,"Error en MediadorListarRemitosCliente. CargarFilaSeleccionada");
        }
    	return result;
    }
    
    public void cargarDatos() {
        try {
            Vector facturas = this.controlFactCte.obtenerFacturaClientesPeriodo(listarSinFact,"RemitoCliente",diaLI,mesLI,anioLI);
            guiTodasFactCte.getJTFPeriodo().setText(mesLI+" - "+anioLI);
            guiTodasFactCte.datos = new Object[facturas.size()][guiTodasFactCte.titulos.length];
            int i = 0;
            for (int j = 0; j < facturas.size(); j++) {
            	FacturaCliente p=(FacturaCliente)facturas.elementAt(j);
            	String compr = "";
            	Set movs = p.getComprobantesPago();
				
				String impAbonado=Utils.ordenarDosDecimales(p.getImporteAbonado());
				Date fpago=p.getFechaPago();
				if(controlFactCte.existeFacturaDeRemito(String.valueOf(p.getNroFactura()))){
					FacturaCliente fcteRem=controlFactCte.buscarFacturaDeRemito(String.valueOf(p.getNroFactura()));
					fpago=fcteRem.getFechaPago();
					impAbonado=Utils.ordenarDosDecimales(fcteRem.getImporteAbonado());
					movs = fcteRem.getComprobantesPago();
				}
				
				for(Iterator it= movs.iterator(); it.hasNext();){
					MovimientoCaja mc = (MovimientoCaja) it.next();
					compr +=mc.getCodigo()+"-";
				}
				if(compr.length()>1)
					compr=compr.substring(0,compr.length()-1);
				String anulada="";
				if(p.isAnulada())
					anulada="SI";
            	Object[] temp = {p.getId(),common.Utils.getStrUtilDate(p.getFechaImpresion()),Utils.nroFact(p.getNroFactura()),p.getCliente().getNombre(),Utils.ordenarDosDecimales(p.getImporteTotal()),impAbonado,common.Utils.getStrUtilDate(fpago),compr,anulada};
            	guiTodasFactCte.datos[i] = temp;
            	i++;
            }
    	}catch(Exception ex){
    		Utils.manejoErrores(ex,"Error en MediadorListarRemitosCliente. CargarDatos");
    	}
    	guiTodasFactCte.jtDatos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	guiTodasFactCte.actualizarTabla();
    }
    
    public void actualizarCampos() {
    	 try {
    		 Vector facturas = this.controlFactCte.obtenerFacturaClientesPeriodoFiltros(listarSinFact,"RemitoCliente",diaLI,mesLI,anioLI, guiTodasFactCte.getJTFFecha().getText(),guiTodasFactCte.getJTFNro().getText(),guiTodasFactCte.getJTFCliente().getText());
             guiTodasFactCte.datos = new Object[facturas.size()][guiTodasFactCte.titulos.length];
             for (int j = 0; j < facturas.size(); j++) {
            	 FacturaCliente r=(FacturaCliente)facturas.elementAt(j);
            	 String compr = "";
            	 Set movs = r.getComprobantesPago();
            	 String impAbonado=Utils.ordenarDosDecimales(r.getImporteAbonado());
            	 Date fpago=r.getFechaPago();
            	 if(controlFactCte.existeFacturaDeRemito(String.valueOf(r.getNroFactura()))){
            		 FacturaCliente fcteRem=controlFactCte.buscarFacturaDeRemito(String.valueOf(r.getNroFactura()));
            		 fpago=fcteRem.getFechaPago();
            		 impAbonado=Utils.ordenarDosDecimales(fcteRem.getImporteAbonado());
            		 movs = fcteRem.getComprobantesPago();
            	 }
            	 
            	 for(Iterator it= movs.iterator(); it.hasNext();){
            		 MovimientoCaja mc = (MovimientoCaja) it.next();
            		 compr +=mc.getCodigo()+"-";
            	 }
            	 if(compr.length()>1)
            		 compr=compr.substring(0,compr.length()-1);
            	 String anulada="";
            	 if(r.isAnulada())
            		 anulada="SI";
            	 Object[] temp = {r.getId(),common.Utils.getStrUtilDate(r.getFechaImpresion()),Utils.nroFact(r.getNroFactura()),r.getCliente().getNombre(),String.valueOf(r.getImporteTotal()),impAbonado,common.Utils.getStrUtilDate(fpago),compr,anulada};
            	 guiTodasFactCte.datos[j] = temp;
             }
     	}catch(Exception ex){
     		Utils.manejoErrores(ex,"Error en MediadorListarRemitosCliente. ActualizarFecha");
     	}
     	guiTodasFactCte.jtDatos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
     	guiTodasFactCte.actualizarTabla();
    }
    
    private void anularFactura() {
        try{
                if (guiTodasFactCte.jtDatos.getSelectedRow() == -1){
                	Utils.advertenciaUsr(guiTodasFactCte,"Para poder anular un Remito debe seleccionarlo previamente.");
                } else {
                	Long id = (Long)guiTodasFactCte.datos[guiTodasFactCte.jtDatos.getSelectedRow()][0];
                	String nroFactura = (String)guiTodasFactCte.datos[guiTodasFactCte.jtDatos.getSelectedRow()][2];
                	String val[] =nroFactura.split("-");
                	String nroFacturaB=new Long(val[0])+val[1];
                    if (controlFactCte.facturaAsociada(id)) {
                    	Utils.advertenciaUsr(guiTodasFactCte,"El Remito no puede ser borrado porque registra pagos.");
                    }else if(controlFactCte.existeFacturaDeRemito(nroFacturaB)){
                    	Utils.advertenciaUsr(guiTodasFactCte,"El Remito no puede ser borrado porque se ha efectuado su factura correspondiente.");
                    }else{
                    	int prueba = Utils.aceptarCancelarAccion(guiTodasFactCte,"Esta seguro que desea Anular el Remito Nro: \n"+ nroFactura);
                    	if (prueba == 0){
                    		this.controlFactCte.anularFacturaCliente(id);
                    		cargarDatos();
                    	}    
                    }     
                    
                }
           
        }catch(Exception ex){
        	Utils.manejoErrores(ex,"Error en MediadorListarFacturasCliente. anularFactura");
        }
    }
    
    public GUIListarRemitosCliente getGUI() {
        return guiTodasFactCte;
    }
    
    public void keyReleased(KeyEvent e) {
    	actualizarCampos();
    }
    
    
    public void keyTyped(KeyEvent e) {
    }
    
    public void keyPressed(KeyEvent e) {
    }
    
    public void valueChanged(ListSelectionEvent arg0) { }

  
}




