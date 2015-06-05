package cliente.GestionarRemitoCliente;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Date;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import persistencia.domain.Cliente;
import persistencia.domain.Comercio;
import persistencia.domain.FacturaCliente;
import persistencia.domain.ItemFactura;
import persistencia.domain.Producto;
import persistencia.domain.Vencimiento;
import server.GestionarCliente.ControlCliente;
import server.GestionarComercio.ControlComercio;
import server.GestionarFacturaCliente.ControlFacturaCliente;
import server.GestionarProducto.ControlProducto;
import server.GestionarVencimiento.ControlVencimiento;
import cliente.GestionarCliente.MediadorGestionarCliente;
import cliente.Principal.GUIReport;

import common.Utils;

public class MediadorRemitoCliente implements ActionListener,ListSelectionListener,KeyListener {

    private GUIRemitoCliente guiRemitoCte = null;
    private ControlFacturaCliente controlFactCliente;
    private ControlComercio controlComercio;
	private MediadorGestionarCliente medGestionarCliente;
	private ControlCliente controlCliente;
	private ControlVencimiento controlVencimiento;
	public Cliente cliente;
	public Vector productos = new Vector();
	public Vector cantProd = new Vector();
	public Vector kilosProd = new Vector();
	public Vector precioUnit = new Vector();
	public Vector precioTotalIt = new Vector();
	public Vector descuentos = new Vector();
	public Vector ctrlVto = new Vector();
	public Vector fechasVto = new Vector();
	private ControlProducto controlProducto;
	private double importeTotal=0; 
	private Vector todosProductos;
	private Comercio dist=null;
	private boolean mostrar=false;
	String loc="";
	JFrame guiPpalPadre=null;
	
    public MediadorRemitoCliente(JFrame guiPadre) throws Exception {
    	try{
    		controlFactCliente = new ControlFacturaCliente();
    		controlProducto = new ControlProducto();
    		controlComercio = new ControlComercio();
    		controlCliente = new ControlCliente();
    		controlVencimiento = new ControlVencimiento();
    	}catch(Exception ex){
    		Utils.manejoErrores(ex,"Error en MediadorRemitoCliente. Constructor");
    	}
    	dist=controlComercio.obtenerComercio();
    	if(dist==null){ 
    		Utils.advertenciaUsr(guiRemitoCte,"Debe completar los datos del Comercio para Facturar.");
    	}else{
    		mostrar=true;
    		guiPpalPadre=guiPadre;
    		Long nroFacturaObt=dist.getNroRemito();
    		guiRemitoCte = new GUIRemitoCliente(guiPadre);
    		cliente=controlCliente.obtenerClienteDefecto();
    		this.actualizarCliente();
    	//	guiRemitoCte.getJTFBusqueda().setEnabled(false);
    		todosProductos = controlProducto.obtenerProductos();
    		guiRemitoCte.nroRemito=nroFacturaObt;
    		guiRemitoCte.getJCBFechaVto().setVisible(false);
    		guiRemitoCte.getJLFechaVto().setVisible(false);
    		guiRemitoCte.setActionListeners(this);
    		guiRemitoCte.setKeyListeners2(this); 		
    	}
    }
  
    public void show() {
    	if(dist!=null && mostrar){
    	guiRemitoCte.actualizarNroRemito();
    	Utils.show(guiRemitoCte);
    	}
    }

    public void actionPerformed(ActionEvent e) {
    	Object source = e.getSource();
    	if (source == this.guiRemitoCte.getJCBCodigo()) {
    		String cod_Prod = (String)this.guiRemitoCte.getJCBCodigo().getSelectedItem();
    		guiRemitoCte.ocultarCombo();
    		this.guiRemitoCte.getJTFCodigo().setText(cod_Prod);
    		String cod=cod_Prod.substring(0,(cod_Prod.indexOf("_")-1));
    		try{
    			Producto pr= (Producto) this.controlProducto.buscarProductoCodigo(new Long(cod));
    			double importeProd=pr.getPrecioVentaConIva();
    			this.guiRemitoCte.getJTFImporte().setText(String.valueOf(importeProd));
    			if(pr.isPrecioKilos()){
    				this.guiRemitoCte.getJTFCantidad().setText("1");
    				this.guiRemitoCte.getJTFKilos().setText("1.000");
    				this.guiRemitoCte.getJTFKilos().setEnabled(true);
    			}else{
    				this.guiRemitoCte.getJTFCantidad().setText("1");
    				this.guiRemitoCte.getJTFKilos().setText("");
    				this.guiRemitoCte.getJTFKilos().setEnabled(false);
    			}
    			if(pr.isCtrlVto()){
    				actualizarVencimientos(pr.getId());
    				guiRemitoCte.getJCBFechaVto().setVisible(true);
    				guiRemitoCte.getJLFechaVto().setVisible(true);
    			}else{
    				guiRemitoCte.getJCBFechaVto().setVisible(false);
    				guiRemitoCte.getJLFechaVto().setVisible(false);
    			}
    			this.guiRemitoCte.getJBAgregarProd().setEnabled(true);
    			this.guiRemitoCte.getJBAgregarProd().requestFocus(true);
    		} catch(Exception ex) {
    			Utils.manejoErrores(ex,"Error en MediadorRemitoCliente. CargarProductoSeleccionado");
    		}
    		
    	}else
    	if ((((Component)e.getSource()).getName().compareTo("ConfirmarRem")) == 0) {
    		try {
    			java.util.Date fu=guiRemitoCte.getJDateChooserFecha().getDate();
    			Date fecha= Utils.crearFecha(fu);
    			if (this.productos.size()==0){
    				Utils.advertenciaUsr(guiRemitoCte,"Debe agregar algún Producto para poder generar el Remito.");
    			} else if(guiRemitoCte.getJTFNombreC().getText().length()==0 ){
    				Utils.advertenciaUsr(guiRemitoCte,"Debe seleccionar el Cliente.");
    			} else{
                	 FacturaCliente fc= new FacturaCliente();
                	 fc.setAnulada(false);
                     fc.setCliente(cliente);
                     fc.setFechaImpresion(fecha);
                     fc.setFechaPago(fecha);
                     fc.setImporteTotal(importeTotal);
                     fc.setImporteAbonado(importeTotal);
                     fc.setNroFactura(guiRemitoCte.nroRemito);
                     fc.setTipoFactura("RemitoCliente");
                     fc.setCondVenta("CONTADO");
                     fc.setIva("");
                     fc.setRemitoNro("");
                     fc.setIngrBrutos("");
                     fc.setLugar(loc);
                     fc.setDiaBuscar(fecha.getDate());
                     Vector items= new Vector();
                	 for(int k=0;k<productos.size();k++){
                		 ItemFactura itNew = new ItemFactura();
                		 itNew.setFactura(fc);
                		 int cantpr=Integer.parseInt((String)cantProd.elementAt(k));
                		 itNew.setCantidad(cantpr);
                		 double kgpr=Double.parseDouble((String)kilosProd.elementAt(k));
                		 itNew.setKilos(kgpr);
                		 int descpr=Integer.parseInt((String)descuentos.elementAt(k));
                		 itNew.setDescuento(descpr);
                		 Producto pr=(Producto)productos.elementAt(k);
                		 itNew.setProducto(pr);
                		 itNew.setPrUnit(Double.parseDouble((String)precioUnit.elementAt(k)));
                		 double prTotIt=Double.parseDouble((String)precioTotalIt.elementAt(k));
                		 itNew.setPrTotal(prTotIt);
                		 items.add(itNew);
                	 }
                	 //fc.setItems(items);
                	 this.controlFactCliente.agregarFacturaClienteTotal(fc,"Remito",loc,0,items,ctrlVto,fechasVto);
                     this.guiRemitoCte.dispose();
                     if(guiRemitoCte.getJCheckImprimir().isSelected()){
                    	 new GUIReport(guiRemitoCte,4,productos,cantProd,kilosProd,precioUnit,descuentos,precioTotalIt,Utils.nroFact(guiRemitoCte.nroRemito),fecha,
                    			 dist, cliente,"","","","","",0,"",importeTotal);
                     }
                     MediadorRemitoCliente msprod = new MediadorRemitoCliente(guiPpalPadre);
                     msprod.show();
                 }
             } catch(Exception ex) {
            	 Utils.manejoErrores(ex,"Error en MediadorRemitoCliente. Confirmar Remito");
             }
        }else if ((((Component)e.getSource()).getName().compareTo("BuscarC")) == 0) {
            buscarCliente();
        }else if ((((Component)e.getSource()).getName().compareTo("AgregarProd")) == 0) {
        	 try{
                String cod_Prod = guiRemitoCte.getJTFCodigo().getText();
                String cant = guiRemitoCte.getJTFCantidad().getText();
                String kilos = guiRemitoCte.getJTFKilos().getText();
                String desc = guiRemitoCte.getJTFDescuento().getText();
                String cod=cod_Prod.substring(0,(cod_Prod.indexOf("_")-1));
                if(cod.length()>0){
                	boolean existe =  this.controlProducto.existeProductoCodigo(new Long(cod));
                	if(existe){
                		Producto pr= (Producto) this.controlProducto.buscarProductoCodigo(new Long(cod));
                		String fechaVto=(String) guiRemitoCte.getJCBFechaVto().getSelectedItem();
                		if(cant.length()==0){
                			Utils.advertenciaUsr(guiRemitoCte,"Debe ingresar una Cantidad.");
                		}else if(pr.isPrecioKilos() && kilos.length()==0){
                			Utils.advertenciaUsr(guiRemitoCte,"Debe ingresar los Kilos.");
                		}else if(pr.isPrecioKilos() && kilos.length()!=0 && !Utils.esDouble(kilos)){
                			Utils.advertenciaUsr(guiRemitoCte,"El número de Kilos ingresado no es correcto.");	
                		}else if(pr.isCtrlVto() && fechaVto.length()==0 ){
                			Utils.advertenciaUsr(guiRemitoCte,"Debe ingresar la fecha de vencimiento.");	
                		}else if(pr.isCtrlVto() && fechaVto.length()!=0 && !Utils.esFecha(fechaVto)){
                			Utils.advertenciaUsr(guiRemitoCte,"La fecha de vencimiento ingresada no es correcta respete el formato dd/mm/aaaa.");	
                		}else{	
                			productos.add(pr);
                			if(pr.isCtrlVto()){
            					ctrlVto.add("SI");
            					
            					java.sql.Date fVto= Utils.strToSqlDateDB(fechaVto);
            					fechasVto.add(fVto);
            				}else{
            					ctrlVto.add("NO");
            					fechasVto.add(null);
            				}
                			int c=Integer.parseInt(cant);
                			cantProd.add(String.valueOf(c));
                			int d=0;
                			if(desc.length()>0)
                				d=Integer.parseInt(desc);
                			descuentos.add(String.valueOf(d));
                			double k=0;
                			if(kilos.length()>0)
                				k=Double.parseDouble(kilos);
                			kilosProd.add(Utils.ordenarTresDecimales(k));
                			//	precioUnit.add(Utils.ordenarDosDecimales(pr.getPrecioVenta()));
                			double importeProd=pr.getPrecioVentaConIva();
                			precioUnit.add(Utils.ordenarDosDecimales(importeProd));
                			double prTotal=0;
                			if(pr.isPrecioKilos()){
                				prTotal = Utils.redondear(importeProd*k,2);
                			}else{
                				prTotal = Utils.redondear(importeProd*c,2);
                			}
                			double pr5=((double) d)/((double)100);
                			double importeDescontar=prTotal*pr5;
                			prTotal = Utils.redondear(prTotal - importeDescontar,2);
                			precioTotalIt.add(Utils.ordenarDosDecimales(prTotal));
                			guiRemitoCte.getJTFCantidad().setText("1");
                			guiRemitoCte.getJTFCodigo().setText("");
                			guiRemitoCte.getJTFImporte().setText("");
                			guiRemitoCte.getJTFBusqueda().setText("");
                			guiRemitoCte.getJTFKilos().setText("");
                			guiRemitoCte.getJTFDescuento().setText("00");
                			cargarDatos();
                			this.guiRemitoCte.getJBAgregarProd().setEnabled(false);
                			guiRemitoCte.getJCBFechaVto().setVisible(false);
                			guiRemitoCte.getJLFechaVto().setVisible(false);
                		}
                	}else
                		Utils.advertenciaUsr(guiRemitoCte,"El Producto no existe.");
                }else{
                	Utils.advertenciaUsr(guiRemitoCte,"El Código correspondiente al Producto es erroneo.");
                }
        	 }catch(Exception ex){
        		 Utils.manejoErrores(ex,"Error en MediadorRemitoCliente. AgregarProducto");
        	 }
        }else if ((((Component)e.getSource()).getName().compareTo("EliminarP")) == 0) {
        	if (guiRemitoCte.tabla.getSelectedRow() == -1){
        		Utils.advertenciaUsr(guiRemitoCte,"Para poder Eliminar un Producto del Remito debe seleccionarlo previamente.");
        	} else {
        		int posProd = guiRemitoCte.tabla.getSelectedRow();
        		int prueba=Utils.aceptarCancelarAccion(guiRemitoCte,"Esta seguro que desea eliminar el Item Número "+ (posProd+1)+" de la Factura.");
        		if (prueba == 0){
        			productos.removeElementAt(posProd);
        			cantProd.removeElementAt(posProd);
        			kilosProd.removeElementAt(posProd);
        			precioUnit.removeElementAt(posProd);
        			descuentos.removeElementAt(posProd);
        			precioTotalIt.removeElementAt(posProd);
        			ctrlVto.removeElementAt(posProd);
					fechasVto.removeElementAt(posProd);
        			cargarDatos();
        		}
        	}
       }else if ((((Component)e.getSource()).getName().compareTo("Cancelar")) == 0) {
    	   guiRemitoCte.dispose();
       }
   }

  

private void buscarCliente() {
        if (medGestionarCliente== null) {
        	medGestionarCliente= new MediadorGestionarCliente(this,guiRemitoCte);
        } else {
            if (!medGestionarCliente.getGUI().isVisible()) {
            	medGestionarCliente.getGUI().setVisible(true);
            }
        }
        if (cliente != null){
            this.guiRemitoCte.getJTFNombreC().setText(cliente.getNombre());
            this.guiRemitoCte.getJTFBusqueda().setEnabled(true);
            this.cargarCliente(cliente);
        }
    }
    
    private void cargarCliente(Cliente c) {
        this.cliente = c;
    }
    
    public void actualizarCliente() {
        this.guiRemitoCte.getJTFNombreC().setText(cliente.getNombre());
        this.guiRemitoCte.getJTFBusqueda().setEnabled(true);
    }
    
    public void cargarDatos() {
    	importeTotal=0;
    	try {
    		guiRemitoCte.datos = new Object[productos.size()][guiRemitoCte.titulos.length];
            int i = 0;
            for (int j = 0; j < productos.size(); j++) {
            	Producto pr= (Producto) productos.elementAt(j);
            	String prUnit= (String)precioUnit.elementAt(j); 
            	String cantidad=(String)cantProd.elementAt(j);
            	String kilos="";
            	if(pr.isPrecioKilos()){
            		kilos=(String)kilosProd.elementAt(j);
            	}
            	String descuento=(String)descuentos.elementAt(j);
            	String precioTotal=(String)precioTotalIt.elementAt(j);
            	double prTotal=Double.parseDouble(precioTotal);
            	importeTotal = Utils.redondear(importeTotal+prTotal,2);
            	Object[] temp = {(String.valueOf(pr.getCodigo())),cantidad,kilos,
	            				  pr.getNombre()+" - "+pr.getProveedor().getNombre(),
	            				  prUnit,descuento, precioTotal};
            	guiRemitoCte.datos[i] = temp;
	                i++;
            }
            if(productos.size()>0){
            	this.guiRemitoCte.getJBEliminarProd().setEnabled(true);
            	this.guiRemitoCte.getJBConfirmarRemito().setEnabled(true);
            	if(productos.size()==20)
            		this.guiRemitoCte.getJTFBusqueda().setEnabled(false);
            	else
            		this.guiRemitoCte.getJTFBusqueda().setEnabled(true);
            }else{
            	this.guiRemitoCte.getJBEliminarProd().setEnabled(false);	
            	this.guiRemitoCte.getJBConfirmarRemito().setEnabled(false);
            }	
    	}catch(Exception ex){
    		Utils.manejoErrores(ex,"Error en MediadorRemitoCliente. CargarDatos");
    	}
    	guiRemitoCte.tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	guiRemitoCte.actualizarTabla();
    	guiRemitoCte.getJTFITotal().setText(Utils.ordenarDosDecimales(importeTotal));
    }

	public void valueChanged(ListSelectionEvent arg0) {
	}
	
	public void keyReleased(KeyEvent e) {
	    actualizarComboCodProd();
	}
   
	private void actualizarComboCodProd() {
		try {
			String texto = guiRemitoCte.getJTFBusqueda().getText();
			int j;
			guiRemitoCte.codProd.removeAllElements();
			for (j = 0; j< todosProductos.size(); j++) {
				Producto cte = (Producto) todosProductos.elementAt(j);
				if(Utils.comienza(String.valueOf(cte.getCodigo()), texto) || Utils.comienza(cte.getNombre(), texto)) {
					Producto p=(Producto) todosProductos.elementAt(j);
					guiRemitoCte.codProd.add(String.valueOf(p.getCodigo()+" _ "+p.getNombre()+" - "+p.getProveedor().getNombre()));
				}
			}
			if(guiRemitoCte.codProd.size()==1){
				String cod_Prod =(String) guiRemitoCte.codProd.elementAt(0);
				String cod=cod_Prod.substring(0,(cod_Prod.indexOf("_")-1));
				Producto pr= (Producto) this.controlProducto.buscarProductoCodigo(new Long(cod));
				this.guiRemitoCte.getJTFCodigo().setText(cod_Prod);
				double importeProd=pr.getPrecioVentaConIva();
    			this.guiRemitoCte.getJTFImporte().setText(String.valueOf(importeProd));
    			
    			if(pr.isPrecioKilos()){
    				this.guiRemitoCte.getJTFCantidad().setText("1");
    				this.guiRemitoCte.getJTFKilos().setText("1.000");
    				this.guiRemitoCte.getJTFKilos().setEnabled(true);
    			}else{
    				this.guiRemitoCte.getJTFCantidad().setText("1");
    				this.guiRemitoCte.getJTFKilos().setText("");
    				this.guiRemitoCte.getJTFKilos().setEnabled(false);
    			}
    			
    			if(pr.isCtrlVto()){
    				actualizarVencimientos(pr.getId());
    				guiRemitoCte.getJCBFechaVto().setVisible(true);
    				guiRemitoCte.getJLFechaVto().setVisible(true);
    			}else{
    				guiRemitoCte.getJCBFechaVto().setVisible(false);
    				guiRemitoCte.getJLFechaVto().setVisible(false);
    			}
    			
    			this.guiRemitoCte.getJBAgregarProd().setEnabled(true);
    			this.guiRemitoCte.getJBAgregarProd().requestFocus(true);
    			guiRemitoCte.ocultarCombo();
			}else{
				guiRemitoCte.mostrarCombo();
				this.guiRemitoCte.getJTFCodigo().setText("");
				this.guiRemitoCte.getJTFImporte().setText("");
				this.guiRemitoCte.getJTFCantidad().setText("");
				this.guiRemitoCte.getJBAgregarProd().setEnabled(false);
				guiRemitoCte.setActionListeners2(this);
			}
		} catch(Exception ex) {
			Utils.manejoErrores(ex,"Error en MediadorRemitoCliente. ActualizarTablaConCodigo");
		}
	}

	public void keyTyped(KeyEvent arg0) {
	}

	public void keyPressed(KeyEvent arg0) {
	}

	private void actualizarVencimientos(Long id) throws Exception {
		Vector vencims=controlVencimiento.obtenerVencimientosDeProducto(id);
		guiRemitoCte.getJCBFechaVto().removeAllItems();
		for(int i=0;i<vencims.size();i++){
			Vencimiento vto=(Vencimiento)vencims.elementAt(i);
			guiRemitoCte.getJCBFechaVto().addItem(Utils.getStrUtilDate(vto.getFechaVto()));
		}
	}
}
