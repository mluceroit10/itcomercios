package cliente.GestionarMovimientoCaja;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

import javax.swing.JDialog;

import persistencia.domain.Factura;
import persistencia.domain.MovimientoCaja;
import server.GestionarCliente.ControlCliente;
import server.GestionarFacturaCliente.ControlFacturaCliente;
import server.GestionarMovimientoCaja.ControlMovimientoCaja;
import cliente.ListarFacturasCliente.MediadorListarFacturasCliente;
import cliente.ListarFacturasProveedor.MediadorListarFacturasProveedor;
import cliente.ListarRemitosCliente.MediadorListarRemitosCliente;

import common.Utils;
import common.GestionarCliente.IControlCliente;
import common.GestionarFacturaCliente.IControlFacturaCliente;
import common.GestionarMovimientoCaja.IControlMovimientoCaja;

public class MediadorAltaMovimientoCaja implements ActionListener {

    private GUIAltaModMovimientoCaja guiMovimientoCaja = null;
    private MediadorGestionarMovimientoCaja mgMovimientoCaja;
    public IControlMovimientoCaja controlMovimientoCaja;
    public IControlCliente controlCte;
    public IControlFacturaCliente controlFactCte;
    public String tipoFact;
	public Factura factura;
	public int codProv=0;
	private MediadorListarFacturasCliente medListarFactCte;
	private MediadorListarRemitosCliente medListarRemitosCte;
	private MediadorListarFacturasProveedor medListarFactProv;
	private java.util.Date hoy=new java.util.Date();
	
    public MediadorAltaMovimientoCaja(MediadorGestionarMovimientoCaja oldMGMovimientoCaja,JDialog guiPadre) throws Exception {
        try{
        	this.controlMovimientoCaja = new ControlMovimientoCaja();
        	this.controlCte =  new ControlCliente();
        	this.controlFactCte =  new ControlFacturaCliente();
        }catch(Exception ex){
        	Utils.manejoErrores(ex,"Error en MediadorAltaMovimientoCaja. Constructor");
        }
        int nro= this.controlMovimientoCaja.obtenerNroMovCaja();
        guiMovimientoCaja = new GUIAltaModMovimientoCaja(nro,guiPadre);
        guiMovimientoCaja.setActionListeners(this);
        mgMovimientoCaja = oldMGMovimientoCaja;
        Utils.show(guiMovimientoCaja);
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        String tipoFactB = guiMovimientoCaja.getJCTipoFact().getSelectedItem().toString();
     	if (source == guiMovimientoCaja.getJBAceptar()) {
    		String codigo = guiMovimientoCaja.getJTFCodigo().getText();
    	    String tipoMov = guiMovimientoCaja.getJCTipoMov().getSelectedItem().toString();
    	    java.util.Date fu= guiMovimientoCaja.getJDateChooserFecha().getDate();
    	    Date fecha= Utils.crearFecha(fu);
    	    String importe = guiMovimientoCaja.getJTFImporte().getText();
            String formaPago = guiMovimientoCaja.getJCBFormaPago().getSelectedItem().toString();
            String nroCheque =  guiMovimientoCaja.getJTFNroCheque().getText();
            String desc = guiMovimientoCaja.getJTFDescr().getText();
            String conFact = guiMovimientoCaja.getJCConFact().getSelectedItem().toString();
            String tipoFactura = guiMovimientoCaja.getJCTipoFact().getSelectedItem().toString();
            String nroFact = guiMovimientoCaja.getJTFFactura().getText();
            try{
    	    	if (codigo.length()==0){
    	    		Utils.advertenciaUsr(guiMovimientoCaja,"Por favor ingrese el Código del Movimiento de Caja.");
    	    	}else if (this.controlMovimientoCaja.existeMovimientoCaja(Integer.parseInt(codigo))){
    	    		Utils.advertenciaUsr(guiMovimientoCaja,"El Movimiento de Caja con ese Código ya existe.");   
    	    	}else if (tipoMov.length()==0 || importe.length()==0  || desc.length()==0 || formaPago.length()==0 || conFact.length()==0){
    	    		Utils.advertenciaUsr(guiMovimientoCaja,"Alguno de los campos obligatorios esta vacio.");
    	    	}else if((conFact.compareTo("SI")==0) && (nroFact.length()==0)  ){
    	    		Utils.advertenciaUsr(guiMovimientoCaja,"Debe cargar cargar los datos de la Factura.");
    	    	}else {
    	    		MovimientoCaja mimovDTO = new MovimientoCaja();
    	    		mimovDTO.setCodigo(Integer.parseInt(codigo));
    	    		mimovDTO.setFecha(fecha);
    	    		mimovDTO.setImporte(Double.parseDouble(importe));
    	    		mimovDTO.setDescripcion(desc);
    	    		mimovDTO.setFormaPago(formaPago);
    	    		if(formaPago.compareTo("CHEQUE")==0)
    	    		mimovDTO.setNroCheque(new Long(nroCheque));
    	    		if(conFact.compareTo("SI")==0){
    	    			mimovDTO.setConFactura(true);
    	    			mimovDTO.setTipoFactura(tipoFactura);
    	    			mimovDTO.setFactura(factura);
    	    		}else{
    	    			mimovDTO.setConFactura(false);
    	    		}
    	    		if(tipoMov.compareTo("ENTRADA")==0)
    	    			mimovDTO.setTipoMovimiento(1);
    	    		else
    	    			mimovDTO.setTipoMovimiento(0);
    	    		this.controlMovimientoCaja.agregarMovimientoCaja_Fact(mimovDTO);
 		    		guiMovimientoCaja.dispose();
    	    		mgMovimientoCaja.cargarDatos();
    	    	}
    	    } catch(Exception ex) {
    	    	Utils.manejoErrores(ex,"Error en MediadorAltaMovimientoCaja. ActionPerformed");
    	    }
    	}else if ((source == guiMovimientoCaja.getJBBuscarFact()) && (tipoFactB.compareTo("Factura Cliente A")==0)) {
    			buscarFacturaCliente("FacturaCliente-A",Utils.getMes(hoy),Utils.getAnio(hoy));//discr tipo a
		}else if ((source == guiMovimientoCaja.getJBBuscarFact()) && (tipoFactB.compareTo("Factura Cliente B")==0)) {
    		buscarFacturaCliente("FacturaCliente-B",Utils.getMes(hoy),Utils.getAnio(hoy));//discr tipo B
		}else if ((source == guiMovimientoCaja.getJBBuscarFact()) && (tipoFactB.compareTo("Remito Cliente")==0)) {
    		buscarRemitoCliente(Utils.getDia(hoy),Utils.getMes(hoy),Utils.getAnio(hoy));  //discr tipo Remito  
		}else if ((source == guiMovimientoCaja.getJBBuscarFact()) && (tipoFactB.compareTo("Factura Proveedor")==0)) {
			buscarFacturaProveedor(Utils.getMes(hoy),Utils.getAnio(hoy));  
    	} else if (source == guiMovimientoCaja.getJBCancelar()) {
            ((JDialog)((Component)e.getSource()).getParent().getParent().getParent().getParent()).dispose();
        }else{
        	if(guiMovimientoCaja.getJCBFormaPago()==source){
        		if(guiMovimientoCaja.getJCBFormaPago().getSelectedItem().toString().compareTo("CHEQUE")==0){
        			guiMovimientoCaja.getJTFNroCheque().setEnabled(true);
        		}else{
        			guiMovimientoCaja.getJTFNroCheque().setEnabled(false);
        		}
        	}
        	if(guiMovimientoCaja.getJCConFact()==source){
        		if(guiMovimientoCaja.getJCConFact().getSelectedItem().toString().compareTo("SI")==0){
        			guiMovimientoCaja.getJBBuscarFact().setEnabled(true);
        			guiMovimientoCaja.getJCTipoFact().setEnabled(true);
        		}else{
        			guiMovimientoCaja.getJBBuscarFact().setEnabled(false);
        			guiMovimientoCaja.getJCTipoFact().setEnabled(false);
        		}
        	}
        	if(guiMovimientoCaja.getJCTipoFact()==source){
        		if(tipoFactB.equals("Factura Cliente A")){
        			if(tipoFact!=null && !tipoFact.equals("FacturaCliente-A"))
        				guiMovimientoCaja.setFactura("");
        			guiMovimientoCaja.getJCTipoMov().setSelectedItem("ENTRADA");
        		}else if(tipoFactB.equals("Factura Cliente B")){
            			if(tipoFact!=null && !tipoFact.equals("FacturaCliente-B"))
            				guiMovimientoCaja.setFactura("");
            			guiMovimientoCaja.getJCTipoMov().setSelectedItem("ENTRADA");
        		}else if(tipoFactB.equals("Remito Cliente")){
        			if(tipoFact!=null && !tipoFact.equals("RemitoCliente"))
        				guiMovimientoCaja.setFactura("");
        			guiMovimientoCaja.getJCTipoMov().setSelectedItem("ENTRADA");		
        		}else if(tipoFactB.equals("Factura Proveedor")){
        			if(tipoFact!=null && !tipoFact.equals("FacturaProveedor"))
        				guiMovimientoCaja.setFactura("");
        			guiMovimientoCaja.getJCTipoMov().setSelectedItem("SALIDA");
        		}
        	}
        }
    }

    private void buscarFacturaCliente(String tipoFact,int mesL,int anioL) {
        if (medListarFactCte== null) {
        	medListarFactCte= new MediadorListarFacturasCliente(this,tipoFact,mesL,anioL,guiMovimientoCaja);
        } else {
            medListarFactCte= new MediadorListarFacturasCliente(this,tipoFact,mesL,anioL,guiMovimientoCaja);
        }
        if (factura != null){
            this.guiMovimientoCaja.getJTFFactura().setText(Utils.nroFact(factura.getNroFactura()));
            this.cargarFactura(factura);
        }
    }
    
    private void buscarRemitoCliente(int diaL,int mesL,int anioL) {
        if (medListarRemitosCte== null) {
        	medListarRemitosCte= new MediadorListarRemitosCliente(this,diaL,mesL,anioL,guiMovimientoCaja);
        } else {
        	medListarRemitosCte= new MediadorListarRemitosCliente(this,diaL,mesL,anioL,guiMovimientoCaja);
        }
        if (factura != null){
            this.guiMovimientoCaja.getJTFFactura().setText(Utils.nroFact(factura.getNroFactura()));
            this.cargarFactura(factura);
        }
    }
    
    private void buscarFacturaProveedor(int mesL,int anioL) {
        if (medListarFactProv== null) {
        	medListarFactProv= new MediadorListarFacturasProveedor(this,mesL,anioL,guiMovimientoCaja);
        } else {
            if (!medListarFactProv.getGUI().isVisible()) {
            	medListarFactProv.getGUI().setVisible(true);
            }
        }
        if (factura != null){
            this.guiMovimientoCaja.getJTFFactura().setText(Utils.nroFact(factura.getNroFactura()));
            this.cargarFactura(factura);
        }
    }
    
    public void actualizarFactura() {
    	guiMovimientoCaja.setFactura(Utils.nroFact(factura.getNroFactura()));
    }
   
    private void cargarFactura(Factura fact) {
        this.factura = fact;
    }
}

