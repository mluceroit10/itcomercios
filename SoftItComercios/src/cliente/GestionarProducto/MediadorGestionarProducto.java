package cliente.GestionarProducto;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import persistencia.domain.Producto;
import persistencia.domain.Proveedor;
import server.GestionarProducto.ControlProducto;
import server.GestionarProveedor.ControlProveedor;
import cliente.Principal.GUIReport;

import common.Utils;
import common.GestionarProducto.IControlProducto;

public class MediadorGestionarProducto implements ActionListener, KeyListener, ListSelectionListener {
    
    private GUIGestionarProducto guiProducto = null;
    protected IControlProducto controlProducto;
    public Vector productos=new Vector();
	public String titulo="";
	
    public MediadorGestionarProducto(JFrame guiPadre) {
    	try{
        	this.controlProducto = new ControlProducto();
        }catch(Exception ex){
        	Utils.manejoErrores(ex,"Error en MediadorGestionarProducto. Constructor");
        }
        this.guiProducto = new GUIGestionarProducto(guiPadre);
        this.guiProducto.setActionListeners(this);
        cargarDatos();
        this.guiProducto.setListSelectionListener(this);
        this.guiProducto.setKeyListener(this);
        Utils.show(guiProducto);
    }
    
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
    	if (source == guiProducto.getJBCargar()) {
            try {
                new MediadorAltaProducto(this,guiProducto);
            } catch (Exception ex) { 
            	Utils.manejoErrores(ex,"Error en MediadorGestionarProducto. ActionPerformed");
            }
        } else if (source == guiProducto.getJBMod()) {
            modificar();
        } else if (source == guiProducto.getJBBorrar()) {
            eliminar();
        }else if(source == guiProducto.getJBControlStock()){
    		try{
    			MediadorStockProductos msprod = new MediadorStockProductos(guiProducto);
    			msprod.show();
    		}catch (Exception p){
    			p.printStackTrace();
    		}	
        } else if (source == guiProducto.getJBImprimir()) {
        	try{
        		if (this.controlProducto.obtenerProductos().isEmpty()){
        			Utils.advertenciaUsr(guiProducto,"No hay Productos guardados.");
        		} else{
        			Object[] valoresPosibles = {"Todos los Proveedores", "Por Proveedor"};
        			String prov = (String)JOptionPane.showInputDialog(guiProducto,
        					"Seleccione Proveedor", "Criterios de Impresión",
        					JOptionPane. QUESTION_MESSAGE, null,
        					valoresPosibles, valoresPosibles[0]);
        			if(prov!=null){
        				if(prov.compareTo("Todos los Proveedores")==0){
        					productos = controlProducto.obtenerProductos();
        					Object[] valoresImpr = {"Precio Entrada y Salida con IVA","Precio Entrada y Salida sin IVA", "Precio Entrada", "Precio Salida con IVA", "Precio Salida sin IVA"};
                			String impr = (String)JOptionPane.showInputDialog(guiProducto,
                					"Precios a imprimir", "Criterios de Impresión",
                					JOptionPane. QUESTION_MESSAGE, null,
                					valoresImpr, valoresImpr[0]);
                			
                			titulo="Todos los productos";
                			if(impr!=null){
                				String leyenda="El precio de venta NO incluye IVA";
                				if(impr.compareTo("Precio Entrada y Salida con IVA")==0){
                					leyenda="El precio de venta SI incluye IVA";
                					new GUIReport(guiProducto, 8,productos,titulo,"CON IVA",leyenda);
                				}
                				if(impr.compareTo("Precio Entrada y Salida sin IVA")==0){
                					new GUIReport(guiProducto, 8,productos,titulo,"SIN IVA",leyenda);
                				}
                				if(impr.compareTo("Precio Entrada")==0){
                					leyenda="";
                					new GUIReport(guiProducto, 9,productos,titulo,"ENTRADA",leyenda);
                				}
                				if(impr.compareTo("Precio Salida con IVA")==0){
                					leyenda="El precio de venta SI incluye IVA";
                					new GUIReport(guiProducto, 9,productos,titulo,"VENTA CON IVA",leyenda);
                				}
                				if(impr.compareTo("Precio Salida sin IVA")==0){
                					new GUIReport(guiProducto, 9,productos,titulo,"VENTA SIN IVA",leyenda);
                				}
                			}
        				}
        				if(prov.compareTo("Por Proveedor")==0){
        					ControlProveedor cp = new ControlProveedor();
        					Vector provs = cp.obtenerProveedores();
        					Object[] provsPosibles= new Object[provs.size()];
        					for (int k=0;k<provs.size();k++){
        						provsPosibles[k]=((Proveedor)provs.elementAt(k)).getNombre();
        					}
        					String unprov = (String)JOptionPane.showInputDialog(guiProducto,
        							"Seleccione el Proveedor", "Criterios de Impresión",
        							JOptionPane. QUESTION_MESSAGE, null,
        							provsPosibles, provsPosibles[0]);
        					productos = controlProducto.obtenerProductosProveedor(unprov);
        					titulo="Todos los productos del proveedor "+unprov;
        					Object[] valoresImpr = {"Precio Entrada y Salida con IVA","Precio Entrada y Salida sin IVA", "Precio Entrada", "Precio Salida con IVA", "Precio Salida sin IVA"};
                			String impr = (String)JOptionPane.showInputDialog(guiProducto,
                					"Precios a imprimir", "Criterios de Impresión",
                					JOptionPane. QUESTION_MESSAGE, null,
                					valoresImpr, valoresImpr[0]);
                			if(impr!=null){
                				if(impr!=null){
                					String leyenda="El precio de venta NO incluye IVA";
                    				if(impr.compareTo("Precio Entrada y Salida con IVA")==0){
                    					leyenda="El precio de venta SI incluye IVA";
                    					new GUIReport(guiProducto, 10,productos,titulo,"CON IVA",leyenda);
                    				}
                    				if(impr.compareTo("Precio Entrada y Salida sin IVA")==0){
                    					new GUIReport(guiProducto, 10,productos,titulo,"SIN IVA",leyenda);
                    				}
                    				if(impr.compareTo("Precio Entrada")==0){
                    					leyenda="";
                    					new GUIReport(guiProducto, 11,productos,titulo,"ENTRADA",leyenda);
                    				}
                    				if(impr.compareTo("Precio Salida con IVA")==0){
                    					leyenda="El precio de venta SI incluye IVA";
                    					new GUIReport(guiProducto, 11,productos,titulo,"VENTA CON IVA",leyenda);
                    				}
                    				if(impr.compareTo("Precio Salida sin IVA")==0){
                    					new GUIReport(guiProducto, 11,productos,titulo,"VENTA SIN IVA",leyenda);
                    				}
                    			}
                				
                				
                			}
        				}	
        			}
        		}
        	} catch (Exception ex) {
        		ex.printStackTrace();
        	}
        } else if (source == guiProducto.getJBAceptar()) {
            guiProducto.dispose();
        } else if (source == guiProducto.getJBCancelar()) {
            guiProducto.dispose();
        }
    }
    
    public void cargarDatos() {
        try {

        	Vector productos = this.controlProducto.obtenerProductos();
        	guiProducto.datos = new Object[productos.size()][guiProducto.titulos.length];
            int i = 0;
            for (int j = 0; j < productos.size(); j++) {
            	Producto p=(Producto)productos.elementAt(j);
            	String kilos="NO";
            	String actKilos="";
            	String minKilos="";
            	if(p.isPrecioKilos()){
            		kilos="SI";
            		actKilos= Utils.ordenarTresDecimales(p.getStockKilosAct());
                	minKilos= Utils.ordenarTresDecimales(p.getStockKilosMin());
            	}
            	Object[] temp = {p.getId(),String.valueOf(p.getCodigo()),p.getNombre(),kilos,
            			         String.valueOf(p.getStockActual()),String.valueOf(p.getStockMinimo()),
            			         actKilos,minKilos,
            			         Utils.ordenarDosDecimales(p.getPrecioEntrada()),p.getGanancia()+" %",Utils.ordenarDosDecimales(p.getPrecioVentaSinIva()),Utils.ordenarDosDecimales(p.getPrecioVentaConIva()),
            			         p.getProveedor().getNombre()};
            	guiProducto.datos[i] = temp;
            	i++;
            }
       }catch(Exception ex){
    	   Utils.manejoErrores(ex,"Error en MediadorGestionarProducto. CargarDatos");
    	}
    	guiProducto.jtDatos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        guiProducto.actualizarTabla();
    }
    
    private void actualizarTabla() {
    	try {
            Vector productos = this.controlProducto.obtenerProductosFiltros(guiProducto.getJTFBuscadorCodigo().getText(),guiProducto.getJTFBuscadorNombre().getText());
            guiProducto.datos = new Object[productos.size()][guiProducto.titulos.length];
            for (int j = 0; j < productos.size(); j++) {
            	Producto p=(Producto)productos.elementAt(j);
            	String kilos="NO";
            	String actKilos="";
            	String minKilos="";
            	if(p.isPrecioKilos()){
            		kilos="SI";
            		actKilos= Utils.ordenarTresDecimales(p.getStockKilosAct());
                	minKilos= Utils.ordenarTresDecimales(p.getStockKilosMin());
            	}
            	Object[] temp = {p.getId(),String.valueOf(p.getCodigo()),p.getNombre(),kilos,
            			String.valueOf(p.getStockActual()),String.valueOf(p.getStockMinimo()),
            			actKilos,minKilos,
            			String.valueOf(p.getPrecioEntrada()),p.getGanancia()+" %",Utils.ordenarDosDecimales(p.getPrecioVentaSinIva()),Utils.ordenarDosDecimales(p.getPrecioVentaConIva()),
            			p.getProveedor().getNombre()};
            	guiProducto.datos[j] = temp; 
            }
    	} catch(Exception ex) {
    		Utils.manejoErrores(ex,"Error en MediadorGestionarProducto. ActualizarTabla");
    	}
	    guiProducto.jtDatos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        guiProducto.actualizarTabla();
    }
    
    private void modificar() {
        try {
            if (this.controlProducto.obtenerProductos().isEmpty()){
            	Utils.advertenciaUsr(guiProducto,"No hay Productos guardados.");
            } else if (guiProducto.jtDatos.getSelectedRow() == -1){
            	Utils.advertenciaUsr(guiProducto,"Para poder Modificar un Producto debe ser previamente seleccionado.");
            } else {
                Long id = (Long)guiProducto.datos[guiProducto.jtDatos.getSelectedRow()][0];
                Producto prod = (Producto)this.controlProducto.buscarProducto(id);
                new MediadorModificarProducto(this,prod,guiProducto);
            }
    	}catch(Exception ex){
    		Utils.manejoErrores(ex,"Error en MediadorGestionarProducto. Modificar");
        }
    }
    
    private void eliminar() {
        try{
            if ( this.controlProducto.obtenerProductos().isEmpty()){
            	Utils.advertenciaUsr(guiProducto,"No hay Productos guardados.");
            } else {
                if (guiProducto.jtDatos.getSelectedRow() == -1){
                	Utils.advertenciaUsr(guiProducto,"Para poder Eliminar un Producto debe seleccionarlo previamente.");
                } else {
                	String nombre = (String)guiProducto.datos[guiProducto.jtDatos.getSelectedRow()][2];
                	Long id = (Long)guiProducto.datos[guiProducto.jtDatos.getSelectedRow()][0];
                	if (controlProducto.productoAsociado(id)) {
                		Utils.advertenciaUsr(guiProducto,"El Producto no puede ser borrado.");
                	}else{
                		int prueba = Utils.aceptarCancelarAccion(guiProducto,"Esta seguro que desea Eliminar el Producto \n"+ nombre);
                		if (prueba == 0)
                			this.controlProducto.eliminarProducto(id);
                	}    
                	cargarDatos();
                }
            }
        }catch(Exception ex){
        	Utils.manejoErrores(ex,"Error en MediadorGestionarProducto. Eliminar");
        }
    }
    
    public GUIGestionarProducto getGUI() {
        return guiProducto;
    }
    
    public void keyReleased(KeyEvent e) {
        actualizarTabla();
    }
    
    public void keyTyped(KeyEvent e) {
    }
    public void keyPressed(KeyEvent e) {
    }
    public void valueChanged(ListSelectionEvent arg0) { }
}




