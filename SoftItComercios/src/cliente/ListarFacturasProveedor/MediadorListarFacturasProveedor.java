package cliente.ListarFacturasProveedor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import persistencia.domain.Comercio;
import persistencia.domain.FacturaProveedor;
import persistencia.domain.ItemFactura;
import persistencia.domain.MovimientoCaja;
import server.GestionarComercio.ControlComercio;
import server.GestionarFacturaProveedor.ControlFacturaProveedor;
import cliente.GestionarMovimientoCaja.MediadorAltaMovimientoCaja;
import cliente.Principal.GUIReport;

import common.Utils;
  
public class MediadorListarFacturasProveedor implements ActionListener, KeyListener, ListSelectionListener, MouseListener {
    
    private GUIListarFacturasProveedor guiTodasFactProv = null;
    protected ControlFacturaProveedor controlFactProv;
    private FacturaProveedor miFactProv;
    private ControlComercio controlComercio;
	private boolean flag=false;
	private MediadorAltaMovimientoCaja medAltaMovCaja;
	private FacturaProveedor fact;
	private int mesLI;
	private int anioLI;
	
    public MediadorListarFacturasProveedor(int mes, int anio,JFrame guiPadre) {
    	try{
    		mesLI=mes;
    		anioLI=anio;
    		controlFactProv = new ControlFacturaProveedor();
    		controlComercio = new ControlComercio();
        }catch(Exception ex){
        	Utils.manejoErrores(ex,"Error en MediadorListarFacturasProveedor. Constructor");
        }
        this.guiTodasFactProv = new GUIListarFacturasProveedor(mesLI,anioLI,guiPadre);
        this.guiTodasFactProv.setActionListeners(this);
        cargarDatos();
        this.guiTodasFactProv.setListSelectionListener(this);
        this.guiTodasFactProv.setKeyListener(this);
        this.flag=true;
        Utils.show(guiTodasFactProv);
    }
    
    public MediadorListarFacturasProveedor(MediadorAltaMovimientoCaja medAMC,int mes, int anio,JDialog guiPadre) {
        this.medAltaMovCaja = medAMC;
        try{
        	mesLI=mes;
    		anioLI=anio;
        	controlFactProv = new ControlFacturaProveedor();
    		controlComercio = new ControlComercio();
        }catch(Exception ex){
        	Utils.manejoErrores(ex,"Error en MediadorListarFacturasProveedor. Constructor");
        }
        this.guiTodasFactProv = new GUIListarFacturasProveedor(mesLI,anioLI,guiPadre);
        this.guiTodasFactProv.setActionListeners(this);
        cargarDatos();
        this.guiTodasFactProv.setListSelectionListener(this);
        this.guiTodasFactProv.setKeyListener(this);
        Utils.show(guiTodasFactProv);
    }
    
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == guiTodasFactProv.getJBImprimir()) {
    		try {
    			if (guiTodasFactProv.jtDatos.getSelectedRow() == -1){
    				Utils.advertenciaUsr(guiTodasFactProv,"Para poder Imprimir una Factura debe ser previamente seleccionada.");
                }else{
                	Long id = (Long)guiTodasFactProv.datos[guiTodasFactProv.jtDatos.getSelectedRow()][0];
                	miFactProv = this.controlFactProv.buscarFacturaProveedor(id);
                	Vector productos=new Vector();
                	Vector cantProd=new Vector();
                	Vector kilos = new Vector();
                	Vector prUnit=new Vector();
                	Vector descuentos=new Vector();
                	Vector prTotal=new Vector();
                	java.util.Set items=miFactProv.getItems();
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
                	new GUIReport(guiTodasFactProv,6,productos,cantProd,kilos,prUnit,descuentos,prTotal, Utils.nroFact(miFactProv.getNroFactura()),miFactProv.getFecha(),
                			dist, miFactProv.getProveedor(),miFactProv.getImporteAuxIva(),miFactProv.getImporteTotal());
                }
    		}
    		catch(Exception ex) {
    			Utils.manejoErrores(ex,"Error en MediadorListarFacturasProveedor. ActionPerformed");
    		}
        }else if (source == guiTodasFactProv.getJBAnularFactura()){
	         anularFactura();	
        }else if (source == guiTodasFactProv.getJBCambiarPeriodo()){
        	String anioB = guiTodasFactProv.getJTFAnio().getText();
        	if(anioB.length()==0){
				Utils.advertenciaUsr(guiTodasFactProv,"Por favor ingrese el Año.");
			}else if(anioB.length()!=4){
				Utils.advertenciaUsr(guiTodasFactProv,"El año debe ser un número de 4 dígitos.");
			}else{
				anioLI= Integer.parseInt(anioB);
				mesLI = guiTodasFactProv.getJCBMes().getSelectedIndex()+1; //para que el numero del indice de con el mes sumo 1
	         	cargarDatos();
			} 	
        }else if (source == guiTodasFactProv.getJBSalir()){
	           if(flag){
	        	   this.guiTodasFactProv.dispose();
	           }
	           else{
	        	   if (cargarFilaSeleccionada()) {
	        		   if (medAltaMovCaja != null) {
	        			   medAltaMovCaja.factura = fact;
	        			   medAltaMovCaja.tipoFact = fact.getTipoFactura();
	        			   medAltaMovCaja.codProv = fact.getProveedor().getCodigo();
	        			   medAltaMovCaja.actualizarFactura();
	        			   this.guiTodasFactProv.dispose();
	        		   }
	        	   }
	           }		
        }else { 
        	guiTodasFactProv.dispose();
    	}
    }
    
    public boolean cargarFilaSeleccionada() {
		boolean result=false;
    	try{
    		if (guiTodasFactProv.jtDatos.getSelectedRow() == -1) {
    			Utils.advertenciaUsr(guiTodasFactProv,"Debe seleccionar una Factura.");
                result = false;
    		}else{
    			Long id = (Long)guiTodasFactProv.datos[guiTodasFactProv.jtDatos.getSelectedRow()][0];
    			fact = this.controlFactProv.buscarFacturaProveedor(id);
    			result = true;
    		}
    	} catch (Exception ex){
    		Utils.manejoErrores(ex,"Error en MediadorListarFacturasProveedor. CargarFilaSeleccionada");
        }
    	return result;
    }
    
    public void cargarDatos() {
        try {
        	Vector facturas = this.controlFactProv.obtenerFacturaProveedores("FacturaProveedor",mesLI,anioLI);
        	guiTodasFactProv.getJTFPeriodo().setText(mesLI+" - "+anioLI);
            guiTodasFactProv.datos = new Object[facturas.size()][guiTodasFactProv.titulos.length];
            int i = 0;
            for (int j = 0; j < facturas.size(); j++) {
            	FacturaProveedor p=(FacturaProveedor)facturas.elementAt(j);
            	String compr = "";
            	Set movs = p.getComprobantesPago();
            	double abonado=0;
				for(Iterator it= movs.iterator(); it.hasNext();){
					MovimientoCaja mc = (MovimientoCaja) it.next();
					compr +=mc.getCodigo()+"-";
					abonado = abonado + mc.getImporte();
				}
				if(compr.length()>1)
					compr=compr.substring(0,compr.length()-1);
				String anulada="";
				if(p.isAnulada())
					anulada="SI";
				Object[] temp = {p.getId(),common.Utils.getStrUtilDate(p.getFecha()),Utils.nroFact(p.getNroFactura()),p.getProveedor().getNombre(),Utils.ordenarDosDecimales(p.getImporteTotal()),Utils.ordenarDosDecimales(abonado),compr,anulada};
				
            	guiTodasFactProv.datos[i] = temp;
            	i++;
            }
    	}catch(Exception ex){
    		Utils.manejoErrores(ex,"Error en MediadorListarFacturasProveedor. CargarDatos");
    	}
    	guiTodasFactProv.jtDatos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	guiTodasFactProv.actualizarTabla();
        this.guiTodasFactProv.setMouseListener(this);
    }
    
    public void actualizarCampos() {
    	 try {
             Vector facturas = this.controlFactProv.obtenerFacturaProveedoresFiltros("FacturaProveedor",mesLI,anioLI,guiTodasFactProv.getJTFFecha().getText(),guiTodasFactProv.getJTFNro().getText(),guiTodasFactProv.getJTFProveedor().getText());
             guiTodasFactProv.datos = new Object[facturas.size()][guiTodasFactProv.titulos.length];
             for (int j = 0; j < facturas.size(); j++) {
            	 FacturaProveedor r=(FacturaProveedor)facturas.elementAt(j);
            	 String compr = "";
            	 Set movs = r.getComprobantesPago();
            	 double abonado = 0;
            	 for(Iterator it= movs.iterator(); it.hasNext();){
            		 MovimientoCaja mc = (MovimientoCaja) it.next();
            		 compr +=mc.getCodigo()+"-";
            		 abonado = abonado + mc.getImporte();
            	 }
            	 if(compr.length()>1)
            		 compr=compr.substring(0,compr.length()-1);
            	 String anulada="";
 				if(r.isAnulada())
 					anulada="SI";
            	 Object[] temp = {r.getId(),common.Utils.getStrUtilDate(r.getFecha()),Utils.nroFact(r.getNroFactura()),r.getProveedor().getNombre(),String.valueOf(r.getImporteTotal()),Utils.ordenarDosDecimales(abonado),compr,anulada};
            	 guiTodasFactProv.datos[j] = temp;
             }
     	}catch(Exception ex){
     		Utils.manejoErrores(ex,"Error en MediadorListarFacturasProveedor. ActualizarFecha");
     	}
     	guiTodasFactProv.jtDatos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
     	guiTodasFactProv.actualizarTabla();
        this.guiTodasFactProv.setMouseListener(this);
    }
    
    public GUIListarFacturasProveedor getGUI() {
        return guiTodasFactProv;
    }
    
    public void keyReleased(KeyEvent e) {
    	  actualizarCampos();
    }
    
    
    public void keyTyped(KeyEvent e) {
    }
    
    public void keyPressed(KeyEvent e) {
    }
    
    public void valueChanged(ListSelectionEvent arg0) { }

    private void anularFactura() {
        try{
                if (guiTodasFactProv.jtDatos.getSelectedRow() == -1){
                	Utils.advertenciaUsr(guiTodasFactProv,"Para poder anular una Factura debe seleccionarla previamente.");
                } else {
                	Long id = (Long)guiTodasFactProv.datos[guiTodasFactProv.jtDatos.getSelectedRow()][0];
                	String nroFactura = (String)guiTodasFactProv.datos[guiTodasFactProv.jtDatos.getSelectedRow()][2];
                	if (controlFactProv.facturaAsociada(id)) {
                    	Utils.advertenciaUsr(guiTodasFactProv,"La Factura no puede ser borrada porque registra pagos.");
                    }else{
                    	int prueba = Utils.aceptarCancelarAccion(guiTodasFactProv,"Esta seguro que desea Anular la Factura Nro: \n"+ nroFactura);
                    	if (prueba == 0){
                    		this.controlFactProv.anularFacturaProveedor(id);
                    		cargarDatos();
                    	}    
                    }
                }
           
        }catch(Exception ex){
        	Utils.manejoErrores(ex,"Error en MediadorListarFacturasCliente. anularFactura");
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
					if (medAltaMovCaja != null) {
						medAltaMovCaja.factura = fact;
						medAltaMovCaja.tipoFact = fact.getTipoFactura();
						medAltaMovCaja.codProv = fact.getProveedor().getCodigo();
						medAltaMovCaja.actualizarFactura();
						this.guiTodasFactProv.dispose();
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




