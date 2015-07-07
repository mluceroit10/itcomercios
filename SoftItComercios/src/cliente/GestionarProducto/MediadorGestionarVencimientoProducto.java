package cliente.GestionarProducto;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Date;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import persistencia.domain.Producto;
import persistencia.domain.Vencimiento;
import server.GestionarVencimiento.ControlVencimiento;

import common.Utils;
import common.GestionarVencimiento.IControlVencimiento;

public class MediadorGestionarVencimientoProducto implements ActionListener, KeyListener, ListSelectionListener {
    
    private GUIGestionarVencimientoProducto guiVencimiento = null;
    public IControlVencimiento controlVencimiento;
    public Vector productos=new Vector();
	public String titulo="";
	
    public MediadorGestionarVencimientoProducto(JDialog guiPadre) {
    	try{
        	this.controlVencimiento = new ControlVencimiento();
        }catch(Exception ex){
        	Utils.manejoErrores(ex,"Error en MediadorGestionarProducto. Constructor");
        }
        this.guiVencimiento = new GUIGestionarVencimientoProducto(guiPadre);
        this.guiVencimiento.setActionListeners(this);
        actualizarTabla();
        this.guiVencimiento.setListSelectionListener(this);
        this.guiVencimiento.setKeyListener(this);
        Utils.show(guiVencimiento);
    }
    
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
    	if (source == guiVencimiento.getJBBorrar()) {
            eliminar();
        } else if (source == guiVencimiento.getJBImprimir()) {
        	try{
        		guiVencimiento.jtDatos.print();
        	} catch (Exception ex) {
        		ex.printStackTrace();
        	}
        	
        } else if (source == guiVencimiento.getControlStock()) {
        	actualizarTabla();
        } else if (source == guiVencimiento.getJBSalir()) {
        	guiVencimiento.dispose();
        }
    }
    
    private void actualizarTabla() {
    	try {
            Vector vencimientos = this.controlVencimiento.obtenerVencimientosFiltros(guiVencimiento.getJTFBuscadorCodigo().getText(),guiVencimiento.getJTFBuscadorNombre().getText(),guiVencimiento.getControlStock().isSelected());
            guiVencimiento.datos = new Object[vencimientos.size()][guiVencimiento.titulos.length];
            Date hoy=new Date();
            for (int j = 0; j < vencimientos.size(); j++) {
            	Vencimiento v=(Vencimiento)vencimientos.elementAt(j);
            	String kilos="NO";
            	String stockKilos="";
            	Producto p=v.getProducto();
            	if(p.isPrecioKilos()){
            		kilos="SI";
            		stockKilos= Utils.ordenarTresDecimales(v.getStockKilos());
            	}
            	
	            Long diff= Long.valueOf((v.getFechaVto().getTime()-hoy.getTime())/(24*60*60*1000));
	            
            	Object[] temp = {v.getId(),String.valueOf(p.getCodigo()),p.getNombre(),p.getProveedor().getNombre(),kilos,
            			String.valueOf(v.getStock()),stockKilos,Utils.getStrUtilDate(v.getFechaVto()),diff.toString()};
            	guiVencimiento.datos[j] = temp; 
            }
    	} catch(Exception ex) {
    		Utils.manejoErrores(ex,"Error en MediadorGestionarProducto. ActualizarTabla");
    	}
    	guiVencimiento.jtDatos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	guiVencimiento.actualizarTabla();
    }
    
    private void eliminar() {
        try{
            if ( this.controlVencimiento.obtenerVencimientos().isEmpty()){
            	Utils.advertenciaUsr(guiVencimiento,"No hay Vencimientos guardados.");
            } else {
                if (guiVencimiento.jtDatos.getSelectedRow() == -1){
                	Utils.advertenciaUsr(guiVencimiento,"Para poder Eliminar un Vencimiento debe seleccionarlo previamente.");
                } else {
                	String nombre = (String)guiVencimiento.datos[guiVencimiento.jtDatos.getSelectedRow()][2];
                	Long id = (Long)guiVencimiento.datos[guiVencimiento.jtDatos.getSelectedRow()][0];
                	int prueba = Utils.aceptarCancelarAccion(guiVencimiento,"Esta seguro que desea Eliminar el Producto "+ nombre);
                	if (prueba == 0)
                		this.controlVencimiento.eliminarVencimiento(id);
                	actualizarTabla();
                }
            }
        }catch(Exception ex){
        	Utils.manejoErrores(ex,"Error en MediadorGestionarProducto. Eliminar");
        }
    }
    
    public GUIGestionarVencimientoProducto getGUI() {
        return guiVencimiento;
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




