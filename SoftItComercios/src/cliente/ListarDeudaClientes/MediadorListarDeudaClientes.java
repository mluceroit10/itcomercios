package cliente.ListarDeudaClientes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.event.ListSelectionEvent;

import persistencia.domain.Localidad;
import server.GestionarCliente.ControlCliente;
import server.GestionarFacturaCliente.ControlFacturaCliente;
import server.GestionarLocalidad.ControlLocalidad;

import common.Utils;

public class MediadorListarDeudaClientes implements ActionListener {

    public ControlCliente controlCliente;
    public ControlFacturaCliente controlFactCte;
    private GUIFiltroLocalidadDeudaClientes guiDeudaCtes=null;
	Vector idLocs= new Vector();
	
	public MediadorListarDeudaClientes(JFrame guiPadre) throws Exception {
        guiDeudaCtes = new GUIFiltroLocalidadDeudaClientes(guiPadre);
        ControlLocalidad cloc;
		try {
			cloc = new ControlLocalidad();
			Vector localidades = cloc.obtenerLocalidades();
			for(int i=0;i<localidades.size();i++){
				Localidad lo=(Localidad) localidades.elementAt(i);
				guiDeudaCtes.localidades.add(lo.getNombre());
				idLocs.add(lo.getId());
			}
			
		} catch (Exception ex) {
			Utils.manejoErrores(ex,"Error en MediadorListarDeudacliente. Constructor");
		}
		guiDeudaCtes.setActionListeners(this);
    }
    
    public void show() {
    	guiDeudaCtes.actualizarBusqueda();
        Utils.show(guiDeudaCtes);
    }

    public void actionPerformed(ActionEvent e) {
    	Object source = e.getSource();
    	if (source == guiDeudaCtes.getJBImprimir()) {
    		try{
    			
    			int loc=guiDeudaCtes.getJCLocalidades().getSelectedIndex();
				Long idLoc=(Long)idLocs.elementAt(loc);
				new MediadorMostrarDeudaClientes(idLoc,guiDeudaCtes);
    		} catch(Exception ex) {
    			Utils.manejoErrores(ex,"Error en MediadorCuentaCliente. ActionPerformed");
            }
       }else if (source == guiDeudaCtes.getJBSalir()) {
   			guiDeudaCtes.dispose();
       }
   }
    
	public void valueChanged(ListSelectionEvent arg0) {
	}

}
