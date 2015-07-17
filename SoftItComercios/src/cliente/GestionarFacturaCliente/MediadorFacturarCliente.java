package cliente.GestionarFacturaCliente;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Iterator;
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
import server.GestionarComercio.ControlComercio;
import server.GestionarFacturaCliente.ControlFacturaCliente;
import server.GestionarMovimientoCaja.ControlMovimientoCaja;
import server.GestionarProducto.ControlProducto;
import server.GestionarVencimiento.ControlVencimiento;
import cliente.GestionarCliente.MediadorGestionarCliente;
import cliente.ListarRemitosCliente.MediadorListarRemitosCliente;
import cliente.Principal.GUIReport;

import common.Utils;

public class MediadorFacturarCliente implements ActionListener,ListSelectionListener,KeyListener {

    private GUIFacturarCliente guiFacturarCte = null;
    private ControlFacturaCliente controlFactCliente;
    private ControlComercio controlComercio;
    private ControlMovimientoCaja controlMC;
    private ControlVencimiento controlVencimiento;
	private MediadorGestionarCliente medGestionarCliente;
	public Cliente cliente;
	public Vector productos = new Vector();
	public Vector cantProd = new Vector();
	public Vector kilosProd = new Vector();
	public Vector precioUnit = new Vector();
	public Vector precioTotalIt = new Vector();
	public Vector descuentos = new Vector();
	public Vector fechasVto = new Vector();
	private ControlProducto controlProducto;
	private double importeTotal=0; 
	private Vector todosProductos;
	private Comercio comercio=null;
	String tipo="";
	private boolean mostrar=false;
	String loc="";
	public FacturaCliente remito;
	private JFrame guiPadreCtrl;
	
    public MediadorFacturarCliente(JFrame guiPadre) throws Exception {
    	try{
    		controlFactCliente = new ControlFacturaCliente();
    		controlProducto = new ControlProducto();
    		controlComercio = new ControlComercio();
    		controlMC = new ControlMovimientoCaja();
    		controlVencimiento = new ControlVencimiento();
    	}catch(Exception ex){
    		Utils.manejoErrores(ex,"Error en MediadorFacturarCliente. Constructor");
    	}
    	guiPadreCtrl=guiPadre;
    	comercio=controlComercio.obtenerComercio();
    	if(comercio==null){ 
    		Utils.advertenciaUsr(guiFacturarCte,"Debe completar los datos del Comercio para Facturar.");
    	}else{	
    		Object[] valores={ "Factura A", "Factura B","Factura de Remito"};
    		Object seleccion = Utils.seleccionarOpcion(guiPadreCtrl,"Seleccione el Tipo de Factura", "Selector Tipo de Factura",valores,0);

    		if(seleccion!=null){
    			String sel=seleccion.toString();
    			if(sel.compareTo("Factura de Remito")!=0){ 
    				mostrar=true;
    				Long nroFacturaObt=new Long(0);
    				if(seleccion.toString().compareTo("Factura A")==0){
    					tipo="A";
    					nroFacturaObt=comercio.getNroFactA();
    				}
    				if(seleccion.toString().compareTo("Factura B")==0){
    					tipo="B";
    					nroFacturaObt=comercio.getNroFactB();
    				}
    				guiFacturarCte = new GUIFacturarCliente(tipo,guiPadre);
    				guiFacturarCte.getJTFBusqueda().setEnabled(false);
    				todosProductos = controlProducto.obtenerProductos();
    				guiFacturarCte.nroFactura=nroFacturaObt;
    				guiFacturarCte.getJCBFechaVto().setVisible(false);
    				guiFacturarCte.getJLFechaVto().setVisible(false);
    				guiFacturarCte.setActionListeners(this);
    				guiFacturarCte.setKeyListeners2(this);
    			}else{
    				buscarRemito();
    			}
    		}
    	}
    }
  
    public void show() {
    	if(comercio!=null && mostrar){
    		remito=null;
    		guiFacturarCte.actualizarNroFactura();
    		Utils.show(guiFacturarCte);
    	}
    }

    public void actionPerformed(ActionEvent e) {
    	Object source = e.getSource();
    	if (source == this.guiFacturarCte.getJCBCodigo()) {
    		String cod_Prod = (String)this.guiFacturarCte.getJCBCodigo().getSelectedItem();
    		guiFacturarCte.ocultarCombo();
    		this.guiFacturarCte.getJTFCodigo().setText(cod_Prod);
    		String cod=cod_Prod.substring(0,(cod_Prod.indexOf("_")-1));
    		try{
    			Producto pr= (Producto) this.controlProducto.buscarProductoCodigo(new Long(cod),null); //selecciona del combo
    			double importeProd=pr.getPrecioVentaSinIva();
    			if(tipo.compareTo("B")==0){
    				importeProd=pr.getPrecioVentaConIva();
    			}
    			this.guiFacturarCte.getJTFImporte().setText(String.valueOf(importeProd));
    			if(pr.isPrecioKilos()){
    				this.guiFacturarCte.getJTFCantidad().setText("1");
    				this.guiFacturarCte.getJTFKilos().setText("1.000");
    				this.guiFacturarCte.getJTFKilos().setEnabled(true);
    			}else{
    				this.guiFacturarCte.getJTFCantidad().setText("1");
    				this.guiFacturarCte.getJTFKilos().setText("");
    				this.guiFacturarCte.getJTFKilos().setEnabled(false);
    			}
    			if(pr.isCtrlVto()){
    				actualizarVencimientos(pr.getId());
    				guiFacturarCte.getJCBFechaVto().setVisible(true);
    				guiFacturarCte.getJLFechaVto().setVisible(true);
    			}else{
    				guiFacturarCte.getJCBFechaVto().setVisible(false);
    				guiFacturarCte.getJLFechaVto().setVisible(false);
    			}
    			
    			this.guiFacturarCte.getJBAgregarProd().setEnabled(true);
    			this.guiFacturarCte.getJBAgregarProd().requestFocus(true);
    		} catch(Exception ex) {
    			Utils.manejoErrores(ex,"Error en MediadorFacturarCliente. BuscarProductoSeleccionado");
    		}
    	}else
    	if ((((Component)e.getSource()).getName().compareTo("ConfirmarFact")) == 0) {
    		String remitoNro = guiFacturarCte.getJTFRemitoNro().getText();
    		boolean correcto=true;
    		if(remitoNro!=null && remitoNro.length()>0){
    			try {
					if(controlFactCliente.existeFacturaDeRemito(remitoNro)){
						correcto=false;
						Utils.advertenciaUsr(guiFacturarCte,"El remito seleccionado ya ha sido facturado.");
					}
				} catch (Exception ex) {
					Utils.manejoErrores(ex,"Error en MediadorFacturarCliente. ConfirmarFacturaDeRemito");
				}
    		}
    		if(correcto){
    			try {
    				java.util.Date fu=guiFacturarCte.getJDateChooserFecha().getDate();
    				String iva = guiFacturarCte.getJCTipoIva().getText();
    				String condVta = guiFacturarCte.getJCCondVta().getSelectedItem().toString();
    				
    				String ingrBrutos = guiFacturarCte.getJTFIngrBrutos().getText();
    				String ivaFact="";
    				if(tipo.compareTo("A")==0){
    					ivaFact = guiFacturarCte.getJTFImporteIva().getText();
    				}
    				Timestamp fecha= Utils.crearFechaHora(fu);
    				Date fechaPago= Utils.crearFecha(fu);
    				if (this.productos.size()==0){
    					Utils.advertenciaUsr(guiFacturarCte,"Debe agregar algún Producto para poder generar la Factura.");
    				} else if(guiFacturarCte.getJTFNombreC().getText().length()==0 ){
    					Utils.advertenciaUsr(guiFacturarCte,"Debe seleccionar el Cliente.");
    				} else if(iva.length()>=0 && iva.compareTo("Seleccione...")==0){
    					Utils.advertenciaUsr(guiFacturarCte,"Debe seleccionar el Tipo de Iva.");                	 
    				} else{
    					FacturaCliente fc= new FacturaCliente();
    					fc.setAnulada(false);
    					fc.setCliente(cliente);
    					fc.setFechaImpresion(fecha);
    					double ivaF=0;
    					if(tipo.compareTo("A")==0){
    						ivaF=Double.parseDouble(ivaFact);
    						fc.setImporteAuxIva(ivaF);
    					}
    					if(tipo.compareTo("B")==0){}
    					fc.setImporteTotal(Utils.redondear(importeTotal+ivaF,2));
    					fc.setNroFactura(guiFacturarCte.nroFactura);
    					fc.setTipoFactura("FacturaCliente-"+tipo);
    					fc.setCondVenta(condVta);
    					if(condVta.compareTo("CONTADO")==0){
    						fc.setFechaPago(fechaPago);
    	                    fc.setImporteAbonado(importeTotal);
    					}
    					fc.setIva(iva);
    					fc.setRemitoNro(remitoNro);
    					fc.setIngrBrutos(ingrBrutos);
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
    						if(fechasVto.elementAt(k)!=null)
    	                		 itNew.setFechaVto((java.sql.Date)fechasVto.elementAt(k));
    						items.add(itNew);
    					}
    					//fc.setItems(items);
    					int nroMC =controlMC.obtenerNroMovCaja();
    					this.controlFactCliente.agregarFacturaClienteTotal(fc,tipo,loc,nroMC,items);
    					this.guiFacturarCte.dispose();
    					new GUIReport(guiFacturarCte,5,productos,cantProd,kilosProd,precioUnit,descuentos,precioTotalIt,"",fecha,
    							comercio, cliente,iva,condVta,remitoNro,ingrBrutos,tipo,importeTotal,ivaFact,fc.getImporteTotal());
    				}
    			} catch(Exception ex) {
    				Utils.manejoErrores(ex,"Error en MediadorFacturarCliente. ConfirmarFactura");
    			}
    		}
        }else if ((((Component)e.getSource()).getName().compareTo("BuscarC")) == 0) {
            buscarCliente();
        }else if ((((Component)e.getSource()).getName().compareTo("AgregarProd")) == 0) {
        	 try{
                String cod_Prod = guiFacturarCte.getJTFCodigo().getText();
                String cant = guiFacturarCte.getJTFCantidad().getText();
                String kilos = guiFacturarCte.getJTFKilos().getText();
                String desc = guiFacturarCte.getJTFDescuento().getText();
                String cod=cod_Prod.substring(0,(cod_Prod.indexOf("_")-1));
                if(cod.length()>0){
                	boolean existe =  this.controlProducto.existeProductoCodigo(new Long(cod));
                	if(existe){
                		Producto pr= (Producto) this.controlProducto.buscarProductoCodigo(new Long(cod),null); //agregar prod
                		String fechaVto=(String) guiFacturarCte.getJCBFechaVto().getSelectedItem();
                		if(cant.length()==0){
                			Utils.advertenciaUsr(guiFacturarCte,"Debe ingresar una Cantidad.");
                		}else if(pr.isPrecioKilos() && kilos.length()==0){
                			Utils.advertenciaUsr(guiFacturarCte,"Debe ingresar los Kilos.");
                		}else if(pr.isPrecioKilos() && kilos.length()!=0 && !Utils.esDouble(kilos)){
                			Utils.advertenciaUsr(guiFacturarCte,"El número de Kilos ingresado no es correcto.");	 
                		}else if(pr.isCtrlVto() && fechaVto==null ){
                			Utils.advertenciaUsr(guiFacturarCte,"Debe ingresar la fecha de vencimiento.");	
                		}else if(pr.isCtrlVto() && fechaVto.length()!=0 && !Utils.esFecha(fechaVto)){
                			Utils.advertenciaUsr(guiFacturarCte,"La fecha de vencimiento ingresada no es correcta respete el formato dd/mm/aaaa.");	
                		}else{	
                		productos.add(pr);
                		if(pr.isCtrlVto()){
        					java.sql.Date fVto= Utils.strToSqlDateDB(fechaVto);
        					fechasVto.add(fVto);
        				}else{
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
                		double importeProd=pr.getPrecioVentaSinIva();
            			if(tipo.compareTo("B")==0){
            				importeProd=pr.getPrecioVentaConIva();
            			}
            			precioUnit.add(Utils.ordenarDosDecimales(importeProd));
            			
            			double prTotal=0;
            			if(pr.isPrecioKilos()){
                    		prTotal = Utils.redondear(importeProd*k,2);
                    	}else{
                    		prTotal = Utils.redondear(importeProd*c,2);
                    	}
            			double pr5=((double) d)/((double)100);
            			double importeDescontar=prTotal*pr5;
                    	prTotal =Utils.redondear(prTotal - importeDescontar,2);
                    	precioTotalIt.add(Utils.ordenarDosDecimales(prTotal));
                    	guiFacturarCte.getJTFCantidad().setText("1");
                		guiFacturarCte.getJTFCodigo().setText("");
                		guiFacturarCte.getJTFImporte().setText("");
                		guiFacturarCte.getJTFBusqueda().setText("");
                		guiFacturarCte.getJTFKilos().setText("");
                		guiFacturarCte.getJTFDescuento().setText("00");
                		cargarDatos();
                		this.guiFacturarCte.getJBAgregarProd().setEnabled(false);
                		guiFacturarCte.getJCBFechaVto().setVisible(false);
                		guiFacturarCte.getJLFechaVto().setVisible(false);
                		guiFacturarCte.getJTFBusqueda().requestFocus(true);
                		}
                	}else
                		Utils.advertenciaUsr(guiFacturarCte,"El Producto no existe.");
                }else{
                	Utils.advertenciaUsr(guiFacturarCte,"El Código correspondiente al Producto es erroneo.");
                }
             }catch(Exception ex){
            	 Utils.manejoErrores(ex,"Error en MediadorFacturarCliente. AgregarProducto");
             }
        }else if ((((Component)e.getSource()).getName().compareTo("EliminarP")) == 0) {
        	if (guiFacturarCte.tabla.getSelectedRow() == -1){
        		Utils.advertenciaUsr(guiFacturarCte,"Para poder Eliminar un Producto de la Factura debe seleccionarlo previamente.");
        	} else {
        		int posProd = guiFacturarCte.tabla.getSelectedRow();
        		int prueba=Utils.aceptarCancelarAccion(guiFacturarCte,"Esta seguro que desea eliminar el Item Número "+ (posProd+1)+" de la Factura.");
        		if (prueba == 0){
        			productos.removeElementAt(posProd);
        			cantProd.removeElementAt(posProd);
        			kilosProd.removeElementAt(posProd);
        			precioUnit.removeElementAt(posProd);
        			descuentos.removeElementAt(posProd);
        			precioTotalIt.removeElementAt(posProd);
					fechasVto.removeElementAt(posProd);
        			cargarDatos();
        		}
        	}
       }else if ((((Component)e.getSource()).getName().compareTo("Cancelar")) == 0) {
    	   guiFacturarCte.dispose();
       }
   }

   private void buscarCliente() {
        if (medGestionarCliente== null) {
        	medGestionarCliente= new MediadorGestionarCliente(this,tipo,guiFacturarCte);
        } else {
            if (!medGestionarCliente.getGUI().isVisible()) {
            	medGestionarCliente.getGUI().setVisible(true);
            }
        }
        if (cliente != null){
        	this.guiFacturarCte.getJtCuit().setText(cliente.getCuit());
            this.guiFacturarCte.getJTFNombreC().setText(cliente.getNombre());
            this.guiFacturarCte.getJTFIngrBrutos().setText(cliente.getIngBrutosCl());
            this.guiFacturarCte.getJCTipoIva().setText(cliente.getIvaCl());
            this.guiFacturarCte.getJTFBusqueda().setEnabled(true);
            this.cargarCliente(cliente);
            guiFacturarCte.getJTFBusqueda().requestFocus(true);
        }
    }
    
    private void cargarCliente(Cliente c) {
        this.cliente = c;
    }
    
    public void actualizarCliente() {
    	this.guiFacturarCte.getJtCuit().setText(cliente.getCuit());
        this.guiFacturarCte.getJTFNombreC().setText(cliente.getNombre());
        this.guiFacturarCte.getJTFIngrBrutos().setText(cliente.getIngBrutosCl());
        this.guiFacturarCte.getJCTipoIva().setText(cliente.getIvaCl());
        this.guiFacturarCte.getJTFBusqueda().setEnabled(true);
        guiFacturarCte.getJTFBusqueda().requestFocus(true);
    }
    
    public void actualizarRemito(FacturaCliente rem) {
    	remito=rem;
    	if(remito.getCliente().getIvaCl().compareTo("Resp. Inscripto")==0) tipo="A";
    	else tipo="B";
    	this.cliente = remito.getCliente();
    	loc=remito.getLugar();
    	Long nroFacturaObt=new Long(0);
    	if(tipo.compareTo("A")==0){
    		nroFacturaObt=comercio.getNroFactA();
    	}
    	if(tipo.compareTo("B")==0){
    		nroFacturaObt=comercio.getNroFactB();
		}
		guiFacturarCte = new GUIFacturarCliente(tipo,guiPadreCtrl);
		this.guiFacturarCte.getJtCuit().setText(remito.getCliente().getCuit());
        this.guiFacturarCte.getJTFNombreC().setText(remito.getCliente().getNombre());
        this.guiFacturarCte.getJTFIngrBrutos().setText(remito.getCliente().getIngBrutosCl());
        this.guiFacturarCte.getJCTipoIva().setText(remito.getCliente().getIvaCl());
        this.guiFacturarCte.getJTFRemitoNro().setText(String.valueOf(remito.getNroFactura()));
        this.guiFacturarCte.getJCCondVta().setEnabled(false);
       	java.util.Set items=remito.getItems();
		for(Iterator j=items.iterator();j.hasNext();){
			ItemFactura pr= (ItemFactura) j.next();
			productos.add(pr.getProducto());
			cantProd.add(String.valueOf(pr.getCantidad()));
			kilosProd.add(Utils.ordenarTresDecimales(pr.getKilos()));
			if(tipo.compareTo("B")==0){
			precioUnit.add(Utils.ordenarDosDecimales(pr.getPrUnit()));
			descuentos.add(String.valueOf(pr.getDescuento()));
			precioTotalIt.add(Utils.ordenarDosDecimales(pr.getPrTotal()));
			}else if(tipo.compareTo("A")==0){
				double precioSinIva= Utils.redondear(pr.getPrUnit()/1.21,2);
				precioUnit.add(Utils.ordenarDosDecimales(precioSinIva));
				descuentos.add(String.valueOf(pr.getDescuento()));
				double prTotal=0;
    			if(pr.getProducto().isPrecioKilos()){
            		prTotal = Utils.redondear(precioSinIva*pr.getKilos(),2);
            	}else{
            		prTotal = Utils.redondear(precioSinIva*pr.getCantidad(),2);
            	}
    			double pr5=((double) pr.getDescuento())/((double)100);
    			double importeDescontar=prTotal*pr5;
            	prTotal =Utils.redondear(prTotal - importeDescontar,2);
            	precioTotalIt.add(Utils.ordenarDosDecimales(prTotal));
			}
		}	
		cargarDatos();
		guiFacturarCte.nroFactura=nroFacturaObt;
		guiFacturarCte.setActionListeners(this);
	//	guiFacturarCte.setVisible(true);
		guiFacturarCte.setKeyListeners2(this);
		mostrar=true;
		this.showDeRemito();
    }
    
    public void showDeRemito() {
    	if(comercio!=null && mostrar){
    	guiFacturarCte.actualizarNroFactura();
    	guiFacturarCte.getJTFBusqueda().setEnabled(false);
		guiFacturarCte.getJBEliminarProd().setEnabled(false);
		//guiFacturarCte.getJBBuscarC().setEnabled(false);
    	}
    }
    
    public void cargarDatos() {
    	importeTotal=0;
    	try {
    		guiFacturarCte.datos = new Object[productos.size()][guiFacturarCte.titulos.length];
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
            	guiFacturarCte.datos[i] = temp;
	                i++;
            }
            if(productos.size()>0){
            	this.guiFacturarCte.getJBEliminarProd().setEnabled(true);
            	this.guiFacturarCte.getJBConfirmaFact().setEnabled(true);
            	if(productos.size()==20)
            		this.guiFacturarCte.getJTFBusqueda().setEnabled(false);
            	else
            		this.guiFacturarCte.getJTFBusqueda().setEnabled(true);
            }else{
            	this.guiFacturarCte.getJBEliminarProd().setEnabled(false);	
            	this.guiFacturarCte.getJBConfirmaFact().setEnabled(false);
            }	
    	}catch(Exception ex){
    		Utils.manejoErrores(ex,"Error en MediadorFacturarCliente. CargarDatos");
    	}
    	guiFacturarCte.tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	guiFacturarCte.actualizarTabla();
    	if(tipo.compareTo("A")==0){
    		double iva= Utils.redondear(importeTotal*0.21,2);
    		guiFacturarCte.getJTFImporteIva().setText(Utils.ordenarDosDecimales(iva));
    		double totalGral=Utils.redondear(importeTotal+iva,2);
    		guiFacturarCte.getJTFITotal().setText(Utils.ordenarDosDecimales(totalGral));
    	}else
    		guiFacturarCte.getJTFITotal().setText(Utils.ordenarDosDecimales(importeTotal));
    }

	public void valueChanged(ListSelectionEvent arg0) {
	}
	
	public void keyReleased(KeyEvent e) {
	    actualizarComboCodProd();
	}
   
	private void actualizarComboCodProd() {
		try {
			String texto = guiFacturarCte.getJTFBusqueda().getText();
			int j;
			guiFacturarCte.codProd.removeAllElements();
			String mostrados[]=new String[todosProductos.size()];
			int limMostro=0;
			for (j = 0; j< todosProductos.size(); j++) {
				Producto cte = (Producto) todosProductos.elementAt(j);
				if(Utils.comienza(String.valueOf(cte.getCodigo()), texto) || Utils.comienza(cte.getNombre(), texto)) {
					Producto p=(Producto) todosProductos.elementAt(j);
					boolean mostrado=false;
					
					for(int i=0;i<limMostro && !mostrado;i++){
						if(mostrados[i].compareTo(p.getCodigo().toString())==0){
							mostrado=true;
						}
					}
					if(!mostrado){
						mostrados[limMostro]=p.getCodigo().toString();
						limMostro++;
						guiFacturarCte.codProd.add(String.valueOf(p.getCodigo()+" _ "+p.getNombre()+" - "+p.getProveedor().getNombre()));
					}
				}
			}
			if(guiFacturarCte.codProd.size()==1){
				String cod_Prod =(String) guiFacturarCte.codProd.elementAt(0);
				String cod=cod_Prod.substring(0,(cod_Prod.indexOf("_")-1));
				Producto pr= (Producto) this.controlProducto.buscarProductoCodigo(new Long(cod),null);//busqueda q se actualiza derecho
				this.guiFacturarCte.getJTFCodigo().setText(String.valueOf(pr.getCodigo()+" _ "+pr.getNombre()+" - "+pr.getProveedor().getNombre()));
				double importeProd=pr.getPrecioVentaSinIva();
    			if(tipo.compareTo("B")==0){
    				importeProd=pr.getPrecioVentaConIva();
    			}
    			this.guiFacturarCte.getJTFImporte().setText(String.valueOf(importeProd));
    			if(pr.isPrecioKilos()){
    				this.guiFacturarCte.getJTFCantidad().setText("1");
    				this.guiFacturarCte.getJTFKilos().setText("1.000");
    				this.guiFacturarCte.getJTFKilos().setEnabled(true);
    			}else{
    				this.guiFacturarCte.getJTFCantidad().setText("1");
    				this.guiFacturarCte.getJTFKilos().setText("");
    				this.guiFacturarCte.getJTFKilos().setEnabled(false);
    			}
    			if(pr.isCtrlVto()){
    				actualizarVencimientos(pr.getId());
    				guiFacturarCte.getJCBFechaVto().setVisible(true);
    				guiFacturarCte.getJLFechaVto().setVisible(true);
    			}else{
    				guiFacturarCte.getJCBFechaVto().setVisible(false);
    				guiFacturarCte.getJLFechaVto().setVisible(false);
    			}
    			this.guiFacturarCte.getJBAgregarProd().setEnabled(true);
    			this.guiFacturarCte.getJBAgregarProd().requestFocus(true);
    			guiFacturarCte.ocultarCombo();
			}else{
				guiFacturarCte.mostrarCombo();
				this.guiFacturarCte.getJTFCodigo().setText("");
				this.guiFacturarCte.getJTFImporte().setText("");
				this.guiFacturarCte.getJTFCantidad().setText("");
				this.guiFacturarCte.getJBAgregarProd().setEnabled(false);
				guiFacturarCte.setActionListeners2(this);
			}
		} catch(Exception ex) {
			Utils.manejoErrores(ex,"Error en MediadorFacturarCliente. ActualizarTablaConCodigo");
		}
	}

	private void buscarRemito() throws Exception {
		java.util.Date hoy= new java.util.Date();
		int diaL=Utils.getDia(hoy);
		int mesL=Utils.getMes(hoy);
		int anioL=Utils.getAnio(hoy);
		new MediadorListarRemitosCliente(this,diaL,mesL,anioL,guiPadreCtrl);
		if (remito != null){
        	this.cargarRemito(remito);
        }
    }
	
	private void cargarRemito(FacturaCliente c) {
        this.remito = c;
    }
	
	public void keyTyped(KeyEvent arg0) {
	}

	public void keyPressed(KeyEvent arg0) {
	}

	private void actualizarVencimientos(Long id) throws Exception {
		Vector vencims=controlVencimiento.obtenerVencimientosDeProducto(id);
		guiFacturarCte.getJCBFechaVto().removeAllItems();
		for(int i=0;i<vencims.size();i++){
			Vencimiento vto=(Vencimiento)vencims.elementAt(i);
			guiFacturarCte.getJCBFechaVto().addItem(Utils.getStrUtilDate(vto.getFechaVto()));
		}
	}
}
