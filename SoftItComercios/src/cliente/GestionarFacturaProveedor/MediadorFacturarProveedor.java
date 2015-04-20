package cliente.GestionarFacturaProveedor;

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

import persistencia.domain.Comercio;
import persistencia.domain.FacturaProveedor;
import persistencia.domain.ItemFactura;
import persistencia.domain.Producto;
import persistencia.domain.Proveedor;
import server.GestionarComercio.ControlComercio;
import server.GestionarFacturaProveedor.ControlFacturaProveedor;
import server.GestionarProducto.ControlProducto;
import cliente.GestionarProducto.MediadorAltaProducto;
import cliente.GestionarProveedor.MediadorGestionarProveedor;
import cliente.Principal.GUIReport;

import common.Utils;

public class MediadorFacturarProveedor implements ActionListener,ListSelectionListener,KeyListener {

    private GUIFacturarProveedor guiFacturarProv = null;
    private ControlFacturaProveedor controlFactProveedor;
    private ControlComercio controlComercio;
	private MediadorGestionarProveedor medGestionarProveedor;
	public Proveedor proveedor;
	public Vector productos = new Vector();
	public Vector cantProd = new Vector();
	public Vector kilosProd = new Vector();
	public Vector precioUnit = new Vector();
	public Vector precioTotalIt = new Vector();
	public Vector descuentos = new Vector();
	public Vector cambioPrecio = new Vector();
	public Vector prEntrConIva = new Vector();
	public Vector margenGanancia = new Vector();
	public Vector nuevoPrecioVtaSinIva = new Vector();
	public Vector nuevoPrecioVtaConIva = new Vector();
	public Vector ctrlVto = new Vector();
	public Vector fechasVto = new Vector();
	private ControlProducto controlProducto;
	private double importeTotal=0; 
	private Vector todosProductos;
	private Comercio dist=null;
	private double impAuxiliar=0;
	private GUICambiarPrecios guiCP=null;
	private boolean actionl3=false;
	
    public MediadorFacturarProveedor(JFrame guiPadre) throws Exception {
    	try{
    		controlFactProveedor = new ControlFacturaProveedor();
    		controlProducto = new ControlProducto();
    		controlComercio = new ControlComercio();
    	}catch(Exception ex){
    		Utils.manejoErrores(ex,"Error en MediadorFacturarProveedor. Constructor");
    	}
    	dist=controlComercio.obtenerComercio();
    	if(dist==null){ 
    		Utils.advertenciaUsr(guiFacturarProv,"Debe completar los datos del Comercio para Facturar.");
    	}else{	
    		guiFacturarProv = new GUIFacturarProveedor(guiPadre);
    		guiFacturarProv.getJTFBusqueda().setEnabled(false);
    		guiFacturarProv.getJDFechaVto().setVisible(false);
    		guiFacturarProv.getJLFechaVto().setVisible(false);
    		guiFacturarProv.setActionListeners(this);
    		guiFacturarProv.setKeyListeners2(this);
    		Utils.show(guiFacturarProv);
    	}
    }
  
    public void actionPerformed(ActionEvent e) {
    	Object source = e.getSource();
    	if (source == this.guiFacturarProv.getJCBCodigo()) {
    		String cod_Prod = (String)this.guiFacturarProv.getJCBCodigo().getSelectedItem();
    		guiFacturarProv.ocultarCombo();
    		this.guiFacturarProv.getJTFCodigo().setText(cod_Prod);
    		String cod=cod_Prod.substring(0,(cod_Prod.indexOf("_")-1));
    		try{
    			Producto pr= (Producto) this.controlProducto.buscarProductoCodigo(new Long(cod));
    			this.guiFacturarProv.getJTFImporte().setText(String.valueOf(pr.getPrecioEntrada()));
    			if(pr.isPrecioKilos()){
    				this.guiFacturarProv.getJTFCantidad().setText("1");
    				this.guiFacturarProv.getJTFKilos().setText("1.000");
    				this.guiFacturarProv.getJTFKilos().setEnabled(true);
    			}else{
    				this.guiFacturarProv.getJTFCantidad().setText("1");
    				this.guiFacturarProv.getJTFKilos().setText("");
    				this.guiFacturarProv.getJTFKilos().setEnabled(false);
    			}
    			if(pr.isCtrlVto()){
    				guiFacturarProv.getJDFechaVto().setVisible(true);
    	    		guiFacturarProv.getJLFechaVto().setVisible(true);
    			}else{
    				guiFacturarProv.getJDFechaVto().setVisible(false);
    	    		guiFacturarProv.getJLFechaVto().setVisible(false);
    			}
    			this.guiFacturarProv.getJBAgregarProd().setEnabled(true);
    		} catch(Exception ex) {
    			Utils.manejoErrores(ex,"Error en MediadorFacturarProveedor. CargarProductoSeleccionado");
    		}
    	}else
    	if ((((Component)e.getSource()).getName().compareTo("ConfirmarFact")) == 0) {
    		try {
    			java.util.Date fu=guiFacturarProv.getJDateChooserFecha().getDate();
    			String nroFact=guiFacturarProv.getJtNroFactura().getText();
    			Date fecha= Utils.crearFecha(fu);
    			String impAux=guiFacturarProv.getJTFImpAuxiliar().getText();
    			  if (this.productos.size()==0){
    				  Utils.advertenciaUsr(guiFacturarProv,"Debe agregar algún Producto para poder generar la Factura.");
                 }else if(guiFacturarProv.getJTFNombreC().getText().length()==0 ){
                	 Utils.advertenciaUsr(guiFacturarProv,"Debe seleccionar el Proveedor.");
                 }else if(nroFact.length()==0){
                	 Utils.advertenciaUsr(guiFacturarProv,"Debe agregar el Número de Factura.");
                 } else if(controlFactProveedor.existeFacturaProveedorNroTipo(new Long(nroFact),"FacturaProveedor",proveedor.getCodigo())){
                	 Utils.advertenciaUsr(guiFacturarProv,"Para este Proveedor ya existe una Factura con dicho número.");
                 }else if(impAux.length()==0){
                	 Utils.advertenciaUsr(guiFacturarProv,"Debe ingresar un importe auxiliar.");
                 }else if(!Utils.esDouble(impAux)){
                	 Utils.advertenciaUsr(guiFacturarProv,"El Importe Adicional ingresado no es correcto.");	 	
                 } else {
                	 impAuxiliar=Double.parseDouble(impAux);
                	 FacturaProveedor fp= new FacturaProveedor();
                	 fp.setAnulada(false);
                	 fp.setProveedor(proveedor);
                	 fp.setFecha(fecha);
                	 fp.setImporteTotal(Utils.redondear(importeTotal+impAuxiliar,2));
                	 fp.setImporteAuxIva(impAuxiliar);
                	 fp.setNroFactura(new Long(nroFact));
                     fp.setTipoFactura("FacturaProveedor");
                     fp.setDiaBuscar(fecha.getDate());
                     Vector items = new Vector();
                	 for(int k=0;k<productos.size();k++){
                		 ItemFactura itNew = new ItemFactura();
                		 itNew.setFactura(fp);
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
                	 this.controlFactProveedor.agregarFacturaProveedorTotal(fp,items,cambioPrecio,prEntrConIva,margenGanancia,nuevoPrecioVtaSinIva,nuevoPrecioVtaConIva,ctrlVto,fechasVto);
                     this.guiFacturarProv.dispose();
                     new GUIReport(guiFacturarProv,6,productos,cantProd,kilosProd,precioUnit,descuentos,precioTotalIt, Utils.nroFact(fp.getNroFactura()),fp.getFecha(),
         				dist, proveedor,fp.getImporteAuxIva(),fp.getImporteTotal());
                 }
    	    } catch(Exception ex) {
    	    	Utils.manejoErrores(ex,"Error en MediadorFacturarProveedor. ConfirmarFactura");
             }
        }else if ((((Component)e.getSource()).getName().compareTo("BuscarP")) == 0) {
            buscarProveedor();
        }else if ((((Component)e.getSource()).getName().compareTo("AgregarProd")) == 0) {
        	 try{
                String cod_Prod = guiFacturarProv.getJTFCodigo().getText();
                String cant = guiFacturarProv.getJTFCantidad().getText();
                String kilos = guiFacturarProv.getJTFKilos().getText();
                String desc = guiFacturarProv.getJTFDescuento().getText();
                String cod=cod_Prod.substring(0,(cod_Prod.indexOf("_")-1));
                String impAux=guiFacturarProv.getJTFImpAuxiliar().getText();
                String precioEntrada=guiFacturarProv.getJTFImporte().getText();
                if(cod.length()>0){
                	boolean existe =  this.controlProducto.existeProductoCodigo(new Long(cod));
                	if(existe){
                		Producto pr= (Producto) this.controlProducto.buscarProductoCodigo(new Long(cod));
                		if(cant.length()==0){
                			Utils.advertenciaUsr(guiFacturarProv,"Debe ingresar una Cantidad.");
                		}else if(precioEntrada.length()==0){
                			Utils.advertenciaUsr(guiFacturarProv,"Debe ingresar el Precio del Producto.");
                		}else if(!Utils.esDouble(precioEntrada)){
                			Utils.advertenciaUsr(guiFacturarProv,"El Precio del Producto ingresado no es correcto.");	
                		}else if(pr.isPrecioKilos() && kilos.length()==0){
                			Utils.advertenciaUsr(guiFacturarProv,"Debe ingresar los Kilos.");
                		}else if(pr.isPrecioKilos() && kilos.length()!=0 && !Utils.esDouble(kilos)){
                			Utils.advertenciaUsr(guiFacturarProv,"El número de Kilos ingresado no es correcto.");	 
                		}else if(impAux.length()!=0 && !Utils.esDouble(impAux)){
                			Utils.advertenciaUsr(guiFacturarProv,"El Importe Adicional ingresado no es correcto.");	 	
                		}else{
                			if(precioEntrada.compareTo(String.valueOf(pr.getPrecioEntrada()))==0){   //sin cambio de precios
                				productos.add(pr);
                				if(pr.isCtrlVto()){
                					ctrlVto.add("SI");
                					java.util.Date fVto=guiFacturarProv.getJDFechaVto().getDate();
                					fechasVto.add(fVto);
                				}else{
                					ctrlVto.add("NO");
                					fechasVto.add(null);
                				}
                				cambioPrecio.add("NO");
                				prEntrConIva.add(null);
                				margenGanancia.add(null);
                				nuevoPrecioVtaSinIva.add(null);
                				nuevoPrecioVtaConIva.add(null);
                    			int c=Integer.parseInt(cant);
                        		cantProd.add(String.valueOf(c));  
                        		int d=0;
                        		if(desc.length()>0)
                        			d=Integer.parseInt(desc);
                        		descuentos.add(String.valueOf(d));
                        		double k=0;
                        		if(kilos.length()>0)
                        			k=Double.parseDouble(kilos);
                        		if(impAux.length()>0)
                        			impAuxiliar=Double.parseDouble(impAux);
                        		kilosProd.add(Utils.ordenarTresDecimales(k));
                        		double precEntrada=Double.parseDouble(precioEntrada);
                        		if(!pr.isPrecioEntCIva())
                        			precEntrada = Utils.incrementarIVA(precEntrada);
                        		precioUnit.add(Utils.ordenarDosDecimales(precEntrada));
                        		double prTotal=0;
                    			if(pr.isPrecioKilos()){
                            		prTotal = Utils.redondear(precEntrada*k,2);
                            	}else{
                            		prTotal = Utils.redondear(precEntrada*c,2);
                            	}
                    			double pr5=((double) d)/((double)100);
                    			double importeDescontar=prTotal*pr5;
                            	prTotal = Utils.redondear(prTotal - importeDescontar,2);
                            	precioTotalIt.add(Utils.ordenarDosDecimales(prTotal));
                            	guiFacturarProv.getJTFCantidad().setText("1");
                    			guiFacturarProv.getJTFCodigo().setText("");
                    			guiFacturarProv.getJTFImporte().setText("");
                    			guiFacturarProv.getJTFBusqueda().setText("");
                    			guiFacturarProv.getJTFKilos().setText("");
                    			guiFacturarProv.getJTFDescuento().setText("00");
                    			cargarDatos();
                    			this.guiFacturarProv.getJBAgregarProd().setEnabled(false);
                			}else{
                				guiCP = new GUICambiarPrecios(guiFacturarProv,pr,precioEntrada);
                				guiCP.setActionListenersCPrecios(this);
                				Utils.show(guiCP);
                			}	
                			guiFacturarProv.getJDFechaVto().setVisible(false);
                			guiFacturarProv.getJLFechaVto().setVisible(false);
                		}
                	}else
                		Utils.advertenciaUsr(guiFacturarProv,"El Producto no existe.");
                }else{
                	Utils.advertenciaUsr(guiFacturarProv,"El Código correspondiente al Producto es erroneo.");
                }
             }catch(Exception ex){
            	 Utils.manejoErrores(ex,"Error en MediadorFacturarProveedor. AgregarProducto");
             }
        }else if ((((Component)e.getSource()).getName().compareTo("EliminarP")) == 0) {
        	if (guiFacturarProv.tabla.getSelectedRow() == -1){
        		Utils.advertenciaUsr(guiFacturarProv,"Para poder Eliminar un Producto de la Factura debe seleccionarlo previamente.");
        	} else {
        		int posProd = guiFacturarProv.tabla.getSelectedRow();
        		int prueba=Utils.aceptarCancelarAccion(guiFacturarProv,"Esta seguro que desea eliminar el Item Número "+ (posProd+1)+" de la Factura.");
        		if (prueba == 0){
        			productos.removeElementAt(posProd);
        			cambioPrecio.removeElementAt(posProd);
        			margenGanancia.removeElementAt(posProd);
        			prEntrConIva.removeElementAt(posProd);
        			nuevoPrecioVtaSinIva.removeElementAt(posProd);
        			nuevoPrecioVtaConIva.removeElementAt(posProd);
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
       }else if ((((Component)e.getSource()).getName().compareTo("AceptarCP")) == 0) {
    	   String cod_Prod = guiFacturarProv.getJTFCodigo().getText();
    	   String cant = guiFacturarProv.getJTFCantidad().getText();
           String kilos = guiFacturarProv.getJTFKilos().getText();
           String desc = guiFacturarProv.getJTFDescuento().getText();
           String cod=cod_Prod.substring(0,(cod_Prod.indexOf("_")-1));
           String impAux=guiFacturarProv.getJTFImpAuxiliar().getText();
           String precioEntrada=guiFacturarProv.getJTFImporte().getText();
           String margenG=guiCP.getJTFMargenGanancia().getText();
     	   String nuevoPrVtaSinIva=guiCP.getJTFPrecioVentaSinIva().getText();
     	   String nuevoPrVtaConIva=guiCP.getJTFPrecioVentaConIva().getText();
     	   String tipoPrecioEntr=(String) guiCP.getJCBTipoPrecioEntrada().getSelectedItem();
     	   
     	   try {
     		   Producto pr= (Producto) this.controlProducto.buscarProductoCodigo(new Long(cod));
     		   productos.add(pr);
     		   if(pr.isCtrlVto()){
     			   ctrlVto.add("SI");
     			   java.util.Date fVto=guiFacturarProv.getJDFechaVto().getDate();
     			   fechasVto.add(fVto);
     		   }else{
     			   ctrlVto.add("NO");
     			   fechasVto.add(null);
     		   }
     		   cambioPrecio.add("SI");
     		   prEntrConIva.add(tipoPrecioEntr);// poner q tipo de precioentr seleccionó
     		   margenGanancia.add(margenG);
			   nuevoPrecioVtaSinIva.add(nuevoPrVtaSinIva);
			   nuevoPrecioVtaConIva.add(nuevoPrVtaConIva);
     		   int c=Integer.parseInt(cant);
     		   cantProd.add(String.valueOf(c));
     		   int d=0;
     		   if(desc.length()>0)
     			   d=Integer.parseInt(desc);
     		   descuentos.add(String.valueOf(d));
     		   double k=0;
     		   if(kilos.length()>0)
     			   k=Double.parseDouble(kilos);
     		   if(impAux.length()>0)
     			   impAuxiliar=Double.parseDouble(impAux);
     		   kilosProd.add(Utils.ordenarTresDecimales(k));
     		   double precEntrada=Double.parseDouble(precioEntrada);
     		   if(tipoPrecioEntr.compareTo("SIN IVA")==0)
     			   precEntrada = Utils.incrementarIVA(precEntrada);
     		   precioUnit.add(Utils.ordenarDosDecimales(precEntrada));
     		   double prTotal=0;
     		   if(pr.isPrecioKilos()){
     			   prTotal = Utils.redondear(precEntrada*k,2);
     		   }else{
     			   prTotal = Utils.redondear(precEntrada*c,2);
     		   }
     		   double pr5=((double) d)/((double)100);
     		   double importeDescontar=prTotal*pr5;
     		   prTotal = Utils.redondear(prTotal - importeDescontar,2);
     		   precioTotalIt.add(Utils.ordenarDosDecimales(prTotal));
     		   guiFacturarProv.getJTFCantidad().setText("1");
     		   guiFacturarProv.getJTFCodigo().setText("");
     		   guiFacturarProv.getJTFImporte().setText("");
     		   guiFacturarProv.getJTFBusqueda().setText("");
     		   guiFacturarProv.getJTFKilos().setText("");
     		   guiFacturarProv.getJTFDescuento().setText("00");
     		   cargarDatos();
     		   this.guiFacturarProv.getJBAgregarProd().setEnabled(false);
     		   guiCP.dispose();
     	   }catch(Exception ex){
     		   Utils.manejoErrores(ex,"Error en MediadorFacturarProveedor. AgregarProducto");
     	   }
       }else if ((((Component)e.getSource()).getName().compareTo("CalcularPV")) == 0) {//recalcular
    	   cambioDePrecios();
       }else if ((((Component)e.getSource()).getName().compareTo("CancelarCP")) == 0) {
    	   guiCP.dispose();
       }else if ((((Component)e.getSource()).getName().compareTo("NuevoProd")) == 0) {
    	   try {
    		   new MediadorAltaProducto(this,proveedor,guiFacturarProv);
  		   } catch (Exception ex) {
    		   Utils.manejoErrores(ex,"Error al invocar la creacion de un nuevo producto");
    	   }  
       }else if ((((Component)e.getSource()).getName().compareTo("Cancelar")) == 0) {
    	   guiFacturarProv.dispose();
       }
   }

   private void buscarProveedor() {
        if (medGestionarProveedor== null) {
        	medGestionarProveedor= new MediadorGestionarProveedor(this,guiFacturarProv);
        } else {
            if (!medGestionarProveedor.getGUI().isVisible()) {
            	medGestionarProveedor.getGUI().setVisible(true);
            }
        }
        if (proveedor != null){
           this.guiFacturarProv.getJTFNombreC().setText(proveedor.getNombre());
           this.guiFacturarProv.getJTFBusqueda().setEnabled(true);
           this.guiFacturarProv.getJTFImpAuxiliar().setEnabled(true);
           try {
        	   todosProductos = controlProducto.obtenerProductosProveedor(proveedor.getNombre());
           } catch (Exception e) {
        	   e.printStackTrace();
           }
           this.cargarProveedor(proveedor);
        }
    }
    
    private void cargarProveedor(Proveedor c) {
        this.proveedor = c;
    }
    
    public void actualizarProveedor() {
    	  this.guiFacturarProv.getJTFNombreC().setText(proveedor.getNombre());
    	  this.guiFacturarProv.getJTFBusqueda().setEnabled(true);
    	  this.guiFacturarProv.getJTFImpAuxiliar().setEnabled(true);
    	  try {
       	   todosProductos = controlProducto.obtenerProductosProveedor(proveedor.getNombre());
          } catch (Exception e) {
       	   e.printStackTrace();
          }
    }
    
    public void cargarDatos() {
    	importeTotal=0;
    	try {
    		guiFacturarProv.datos = new Object[productos.size()][guiFacturarProv.titulos.length];
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
            	guiFacturarProv.datos[i] = temp;
	                i++;
            }
            if(productos.size()>0){
            	this.guiFacturarProv.getJBEliminarProd().setEnabled(true);
            	this.guiFacturarProv.getJBConfirmaFact().setEnabled(true);
            }else{
            	this.guiFacturarProv.getJBEliminarProd().setEnabled(false);	
            	this.guiFacturarProv.getJBConfirmaFact().setEnabled(false);
            }	
    	}catch(Exception ex){
    		Utils.manejoErrores(ex,"Error en MediadorFacturarProveedor. CargarDatos");
    	}
    	guiFacturarProv.tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	guiFacturarProv.actualizarTabla();
    	guiFacturarProv.getJTFImpAuxiliar().setText(Utils.ordenarDosDecimales(impAuxiliar));
    	guiFacturarProv.getJTFITotal().setText(Utils.ordenarDosDecimales(Utils.redondear(importeTotal+impAuxiliar,2)));
    }

	public void valueChanged(ListSelectionEvent arg0) {
	}
	
	public void keyReleased(KeyEvent e) {
	    actualizarComboCodProd();
	}
   
	private void actualizarComboCodProd() {
		try {
			String texto = guiFacturarProv.getJTFBusqueda().getText();
			int j;
            guiFacturarProv.codProd.removeAllElements();
			for (j = 0; j< todosProductos.size(); j++) {
				Producto cte = (Producto) todosProductos.elementAt(j);
				if(Utils.comienza(String.valueOf(cte.getCodigo()), texto) || Utils.comienza(cte.getNombre(), texto)) {
					Producto p=(Producto) todosProductos.elementAt(j);
					guiFacturarProv.codProd.add(String.valueOf(p.getCodigo()+" _ "+p.getNombre()+" - "+p.getProveedor().getNombre()));
				}
			}
			if(guiFacturarProv.codProd.size()==1){
				String cod_Prod =(String) guiFacturarProv.codProd.elementAt(0);
				String cod=cod_Prod.substring(0,(cod_Prod.indexOf("_")-1));
				Producto pr= (Producto) this.controlProducto.buscarProductoCodigo(new Long(cod));
				this.guiFacturarProv.getJTFCodigo().setText(cod_Prod);
				this.guiFacturarProv.getJTFImporte().setText(String.valueOf(pr.getPrecioEntrada()));
    			
    			if(pr.isPrecioKilos()){
    				this.guiFacturarProv.getJTFCantidad().setText("1");
    				this.guiFacturarProv.getJTFKilos().setText("1.000");
    				this.guiFacturarProv.getJTFKilos().setEnabled(true);
    			}else{
    				this.guiFacturarProv.getJTFCantidad().setText("1");
    				this.guiFacturarProv.getJTFKilos().setText("");
    				this.guiFacturarProv.getJTFKilos().setEnabled(false);
    			}
    			if(pr.isCtrlVto()){
    				guiFacturarProv.getJDFechaVto().setVisible(true);
    	    		guiFacturarProv.getJLFechaVto().setVisible(true);
    			}else{
    				guiFacturarProv.getJDFechaVto().setVisible(false);
    	    		guiFacturarProv.getJLFechaVto().setVisible(false);
    			}
    			this.guiFacturarProv.getJBAgregarProd().setEnabled(true);
    			guiFacturarProv.ocultarCombo();
    			guiFacturarProv.ocultarBotonNuevoProducto();
			}else{
				if(guiFacturarProv.codProd.size()==0){
					guiFacturarProv.ocultarCombo();
					guiFacturarProv.mostrarBotonNuevoProducto();
					if(!actionl3){
					guiFacturarProv.setActionListeners3(this);
					actionl3=true;
					}
				}else{
					guiFacturarProv.ocultarBotonNuevoProducto();
					guiFacturarProv.mostrarCombo();
					this.guiFacturarProv.getJTFCodigo().setText("");
					this.guiFacturarProv.getJTFImporte().setText("");
					this.guiFacturarProv.getJTFCantidad().setText("");
					this.guiFacturarProv.getJBAgregarProd().setEnabled(false);
					//if(!actionl2){
					guiFacturarProv.setActionListeners2(this);
					//actionl2=true;
					//}
				}
			}
		} catch(Exception ex) {
			Utils.manejoErrores(ex,"Error en MediadorFacturarProveedor. ActualizarTablaConCodigo");
		}
	}

	public void keyTyped(KeyEvent arg0) {
	}

	public void keyPressed(KeyEvent arg0) {
	}
	
	public void cambioDePrecios(){
    	String prEntr = this.guiCP.getJTFPrecioEntrada().getText();
		String mGanancia = this.guiCP.getJTFMargenGanancia().getText();
		String tipoPEntr = (String) this.guiCP.getJCBTipoPrecioEntrada().getSelectedItem();
		String prConIVA="";
		String prSinIVA="";
		if(prEntr.length()!=0 && mGanancia.length()!=0){
			if(Utils.esDouble(prEntr)){
				double prEntrada=Double.parseDouble(prEntr);
				double mg=Double.parseDouble(mGanancia);
				double pESinIva=0;
				double pEConIva=0;
				if(tipoPEntr.compareTo("CON IVA")==0){
					pESinIva=Utils.decrementarIVA(prEntrada);
					pEConIva=prEntrada;
				}else if(tipoPEntr.compareTo("SIN IVA")==0){
					pESinIva=prEntrada;
					pEConIva=Utils.incrementarIVA(prEntrada);
				}
				
				double precio1=Utils.incrementarPorcentajeAPrecio(pESinIva,mg);
				prSinIVA=Utils.redondearCentavos(precio1);
				double precio2=Utils.incrementarPorcentajeAPrecio(pEConIva,mg);
				prConIVA=Utils.redondearCentavos(precio2);
				this.guiCP.getJTFPrecioVentaSinIva().setText(prSinIVA);
				this.guiCP.getJTFPrecioVentaConIva().setText(prConIVA);
			}
		}
    }

}
