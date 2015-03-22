package cliente.GestionarProducto;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Vector;

import javax.swing.ButtonModel;
import javax.swing.JDialog;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import persistencia.domain.Proveedor;
import server.GestionarProducto.ControlProducto;
import server.GestionarProveedor.ControlProveedor;
import cliente.Principal.GUIReport;

import common.Utils;
import common.GestionarProducto.IControlProducto;
import common.GestionarProveedor.IControlProveedor;

public class MediadorStockProductos  implements ActionListener, ListSelectionListener {

	public Color blanco  = new Color(255,255,255);
	public Color negro = new Color(0,0,0);
	private GUIStockProductos guiImprSC = null;
	public IControlProducto controlProducto;
	public IControlProveedor controlProveedor;
	public Date fecha;
	public Vector productos=new Vector();
	public String titulo="";
	public boolean todos=true;
	
	public MediadorStockProductos(JDialog guiPadre) throws Exception {
    	try{
        	controlProducto = new ControlProducto();
        	controlProveedor = new ControlProveedor();
        }catch(Exception ex){
        	Utils.manejoErrores(ex,"Error en MediadorStockProducto. Constructor");
        }
        this.guiImprSC = new GUIStockProductos(guiPadre);
        this.guiImprSC.setActionListeners(this);
        try {
			Vector prvs = controlProveedor.obtenerProveedores();
			for(int i=0;i<prvs.size();i++){
				Proveedor p=(Proveedor) prvs.elementAt(i);
				guiImprSC.proveedores.add(p.getNombre());
			}
		} catch (Exception ex) {
			Utils.manejoErrores(ex,"Error en MediadorStockProducto. Constructor");
		}
    }
	
	public void show() throws Exception {
		guiImprSC.actualizarTabla();
		Utils.show(guiImprSC);
    }
	
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == guiImprSC.getJBContinuar()) {
			ButtonModel tipoProveedor=guiImprSC.opcTipoProv().getSelection();
			String orden=guiImprSC.getJCOrdenListado().getSelectedItem().toString();
			String tipoProv =tipoProveedor.getActionCommand();
			String atOrden;
			if(orden.compareTo("Codigo del Producto")==0){
				atOrden = "codigo";
			}else{
				atOrden = "nombre";
			}
			try {
				fecha=new Date();
				if(tipoProv.compareTo("Todos")==0){
					productos = controlProducto.obtenerStockTodosProductos(atOrden);
					titulo="Todos los productos en bajo stock";
					new GUIReport(guiImprSC,1,productos,titulo,"","");
				}
				if(tipoProv.compareTo("unProv")==0){
					String nombProv=guiImprSC.getJCProveedores().getSelectedItem().toString();
					productos = controlProducto.obtenerStockProductosProveedor(nombProv,atOrden);
					titulo="Todos los productos en bajo stock del proveedor "+nombProv;
					new GUIReport(guiImprSC,2,productos,titulo,"","");
				}
			} catch (Exception ex) {
				Utils.manejoErrores(ex,"Error en MediadorStockProducto. ActionPerformed");
			}
		} else { 
			guiImprSC.dispose();
		}
	}

	public void valueChanged(ListSelectionEvent arg0) {
	}

}
