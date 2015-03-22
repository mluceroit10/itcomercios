package cliente.ListarFacturasCliente;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
import cliente.GestionarMovimientoCaja.MediadorAltaMovimientoCaja;
import cliente.Principal.GUIReport;

import common.Utils;

public class MediadorListarFacturasCliente implements ActionListener, KeyListener, ListSelectionListener {
    
    private GUIListarFacturasCliente guiTodasFactCte = null;
    protected ControlFacturaCliente controlFactCte;
    private FacturaCliente miFactCte;
    private ControlComercio controlComercio;
	private ControlCliente controlCliente;
	private boolean flag=false;
	private MediadorAltaMovimientoCaja medAltaMovCaja;
	private FacturaCliente fact;
	private String tiposListar="todos";
	private int mesLI;
	private int anioLI;
	
    public MediadorListarFacturasCliente(int mes, int anio,JFrame guiPadre) {
    	try{
    		mesLI=mes;
    		anioLI=anio;
    		controlFactCte = new ControlFacturaCliente();
    		controlComercio = new ControlComercio();
    		controlCliente = new ControlCliente();
        }catch(Exception ex){
        	Utils.manejoErrores(ex,"Error en MediadorListarFacturasCliente. Constructor");
        }
        this.guiTodasFactCte = new GUIListarFacturasCliente(mesLI,anioLI,guiPadre);
        this.guiTodasFactCte.setActionListeners(this);
        cargarDatos();
        this.guiTodasFactCte.setListSelectionListener(this);
        this.guiTodasFactCte.setKeyListener(this);
        this.flag=true;
        Utils.show(guiTodasFactCte);
    }
    
    public MediadorListarFacturasCliente(MediadorAltaMovimientoCaja medAMC,String tipoFactListar,int mes, int anio,JDialog guiPadre) {
        this.medAltaMovCaja = medAMC;
        try{
        	mesLI=mes;
    		anioLI=anio;
        	controlFactCte = new ControlFacturaCliente();
        	controlComercio = new ControlComercio();
    		controlCliente = new ControlCliente();
        }catch(Exception ex){
        	Utils.manejoErrores(ex,"Error en MediadorListarFacturasCliente. Constructor");
        }
        tiposListar=tipoFactListar;
        this.guiTodasFactCte = new GUIListarFacturasCliente(mesLI,anioLI,guiPadre);
        this.guiTodasFactCte.setActionListeners(this);
        cargarDatos();
        this.guiTodasFactCte.setListSelectionListener(this);
        this.guiTodasFactCte.setKeyListener(this);
        Utils.show(guiTodasFactCte);
    }
    
    public boolean cargarFilaSeleccionada() {
		boolean result=false;
    	try{
    		if (guiTodasFactCte.jtDatos.getSelectedRow() == -1) {
    			Utils.advertenciaUsr(guiTodasFactCte,"Debe Seleccionar una Factura.");
                result = false;
    		}else{
    			Long id = (Long)guiTodasFactCte.datos[guiTodasFactCte.jtDatos.getSelectedRow()][0];
    			fact = this.controlFactCte.buscarFacturaCliente(id);
    			result = true;
    		}
    	} catch (Exception ex){
    		Utils.manejoErrores(ex,"Error en MediadorListarFacturasCliente. CargarFilaSeleccionada");
        }
    	return result;
    }
    
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == guiTodasFactCte.getJBImprimir()) {
    		try {
    			if (guiTodasFactCte.jtDatos.getSelectedRow() == -1){
    				Utils.advertenciaUsr(guiTodasFactCte,"Para poder Imprimir una Factura debe ser previamente seleccionada.");
                }else{
                	Long id = (Long)guiTodasFactCte.datos[guiTodasFactCte.jtDatos.getSelectedRow()][0];
                	miFactCte = this.controlFactCte.buscarFacturaCliente(id);
                	Vector productos=new Vector();
                	Vector cantProd=new Vector();
                	Vector kilos=new Vector();
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
                	Comercio dist=controlComercio.obtenerComercio();
                	Cliente cte=controlCliente.buscarCliente(miFactCte.getCliente().getId());
                	new GUIReport(guiTodasFactCte,3,productos,cantProd,kilos,prUnit,descuentos,prTotal,miFactCte, Utils.nroFact(miFactCte.getNroFactura()),miFactCte.getFechaImpresion(),
                			dist, cte,miFactCte.getImporteTotal());  
                }
    		}
    		catch(Exception ex) {
    			ex.printStackTrace();
    			Utils.manejoErrores(ex,"Error en MediadorListarFacturasCliente. ActionPerformed");
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
	         	cargarDatos();
			} 	
        }else if (source == guiTodasFactCte.getJBSalir()){
    	           if(flag){
    	        	   this.guiTodasFactCte.dispose();
    	           }
    	           else{
    	        	   if (cargarFilaSeleccionada()) {
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
    
    public void cargarDatos() {
        try {
            Vector facturasA = this.controlFactCte.obtenerFacturaClientesPeriodo(false,"FacturaCliente-A",0,mesLI,anioLI);
            Vector facturasB = this.controlFactCte.obtenerFacturaClientesPeriodo(false,"FacturaCliente-B",0,mesLI,anioLI);
            Vector facturas=new Vector();
            if(tiposListar.compareTo("todos")==0){
            	facturas=facturasA;
            	facturas.addAll(facturasB);
            }
            if(tiposListar.compareTo("FacturaCliente-A")==0){
            	facturas=facturasA;
            }
            if(tiposListar.compareTo("FacturaCliente-B")==0){
            	facturas=facturasB;
            }
            guiTodasFactCte.getJTFPeriodo().setText(mesLI+" - "+anioLI);
            guiTodasFactCte.datos = new Object[facturas.size()][guiTodasFactCte.titulos.length];
            int i = 0;
            for (int j = 0; j < facturas.size(); j++) {
            	FacturaCliente p=(FacturaCliente)facturas.elementAt(j);
            	String tipoF="";
            	if(p.getTipoFactura().compareTo("FacturaCliente-A")==0) tipoF="A";
        		if(p.getTipoFactura().compareTo("FacturaCliente-B")==0) tipoF="B";
            	String compr = "";
            	Set movs = p.getComprobantesPago();
				for(Iterator it= movs.iterator(); it.hasNext();){
					MovimientoCaja mc = (MovimientoCaja) it.next();
					compr +=mc.getCodigo()+"-";
				}
				if(compr.length()>1)
					compr=compr.substring(0,compr.length()-1);
				String remNro="";
				if(p.getRemitoNro()!=null && p.getRemitoNro().compareTo("")!=0)
					remNro=" - "+Utils.nroFact(new Long(p.getRemitoNro()));
				String anulada="";
				if(p.isAnulada())
					anulada="SI";
			 	Object[] temp = {p.getId(),common.Utils.getStrUtilDate(p.getFechaImpresion()),tipoF,Utils.nroFact(p.getNroFactura())+remNro,p.getCliente().getNombre(),Utils.ordenarDosDecimales(p.getImporteTotal()),Utils.ordenarDosDecimales(p.getImporteAbonado()),common.Utils.getStrUtilDate(p.getFechaPago()),compr,anulada};
            	guiTodasFactCte.datos[i] = temp;
            	i++;
            }
    	}catch(Exception ex){
    		ex.printStackTrace();
    		Utils.manejoErrores(ex,"Error en MediadorListarFacturasCliente. CargarDatos");
    	}
    	guiTodasFactCte.jtDatos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	guiTodasFactCte.actualizarTabla();
    }
    
    public void actualizarCampos() {
    	 try {
    		 Vector facturasA = this.controlFactCte.obtenerFacturaClientesPeriodoFiltros(false,"FacturaCliente-A",0,mesLI,anioLI, guiTodasFactCte.getJTFFecha().getText(),guiTodasFactCte.getJTFNro().getText(),guiTodasFactCte.getJTFCliente().getText());
             Vector facturasB = this.controlFactCte.obtenerFacturaClientesPeriodoFiltros(false,"FacturaCliente-B",0,mesLI,anioLI, guiTodasFactCte.getJTFFecha().getText(),guiTodasFactCte.getJTFNro().getText(),guiTodasFactCte.getJTFCliente().getText());
             Vector facturas=new Vector();
             if(tiposListar.compareTo("todos")==0){
             	facturas=facturasA;
             	facturas.addAll(facturasB);
             }
             if(tiposListar.compareTo("FacturaCliente-A")==0){
             	facturas=facturasA;
             }
             if(tiposListar.compareTo("FacturaCliente-B")==0){
             	facturas=facturasB;
             }
             guiTodasFactCte.datos = new Object[facturas.size()][guiTodasFactCte.titulos.length];
             for (int j = 0; j < facturas.size(); j++) {
            	 FacturaCliente r=(FacturaCliente)facturas.elementAt(j);
            	 String compr = "";
            	 String tipoF="";
            	 if(r.getTipoFactura().compareTo("FacturaCliente-A")==0) tipoF="A";
            	 if(r.getTipoFactura().compareTo("FacturaCliente-B")==0) tipoF="B";
            	 Set movs = r.getComprobantesPago();
            	 for(Iterator it= movs.iterator(); it.hasNext();){
            		 MovimientoCaja mc = (MovimientoCaja) it.next();
            		 compr +=mc.getCodigo()+"-";
            	 }
            	 if(compr.length()>1)
            		 compr=compr.substring(0,compr.length()-1);
            	 String remNro="";
            	 if(r.getRemitoNro()!=null && r.getRemitoNro().compareTo("")!=0)
            		 remNro=" - "+Utils.nroFact(new Long(r.getRemitoNro()));
            	 String anulada="";
            	 if(r.isAnulada())
            		 anulada="SI";
            	 Object[] temp = {r.getId(),common.Utils.getStrUtilDate(r.getFechaImpresion()),tipoF,Utils.nroFact(r.getNroFactura())+remNro,r.getCliente().getNombre(),String.valueOf(r.getImporteTotal()),String.valueOf(r.getImporteAbonado()),common.Utils.getStrUtilDate(r.getFechaPago()),compr,anulada};
            	 guiTodasFactCte.datos[j] = temp;
             }
     	}catch(Exception ex){
     		Utils.manejoErrores(ex,"Error en MediadorListarFacturasCliente. ActualizarFecha");
     	}
     	guiTodasFactCte.jtDatos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
     	guiTodasFactCte.actualizarTabla();
    }
    
    private void anularFactura() {
        try{
                if (guiTodasFactCte.jtDatos.getSelectedRow() == -1){
                	Utils.advertenciaUsr(guiTodasFactCte,"Para poder anular una Factura debe seleccionarla previamente.");
                } else {
                	Long id = (Long)guiTodasFactCte.datos[guiTodasFactCte.jtDatos.getSelectedRow()][0];
                	String nroFactura = (String)guiTodasFactCte.datos[guiTodasFactCte.jtDatos.getSelectedRow()][3];
                	if (controlFactCte.facturaAsociada(id)) {
                    	Utils.advertenciaUsr(guiTodasFactCte,"La Factura no puede ser borrada porque registra pagos.");
                    }else if(nroFactura.indexOf("-",5)!= -1){
                    	Utils.advertenciaUsr(guiTodasFactCte,"La Factura no puede ser borrada por ser una Factura de Remito.");
                    }else{
                    	int prueba = Utils.aceptarCancelarAccion(guiTodasFactCte,"Esta seguro que desea Anular la Factura Nro: \n"+ nroFactura);
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
    
    public GUIListarFacturasCliente getGUI() {
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




