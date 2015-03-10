package cliente.ListarProductosFacturados;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.event.ListSelectionEvent;

import persistencia.domain.Localidad;
import server.GestionarLocalidad.ControlLocalidad;

import common.Utils;

public class MediadorListarProductosFacturados implements ActionListener {
	
	private GUIListarProductosFacturados guiListarPrFact = null;
	Vector idLocs= new Vector();
	
	public MediadorListarProductosFacturados(JFrame guiPadre) throws Exception {
        this.guiListarPrFact = new GUIListarProductosFacturados(guiPadre);
        ControlLocalidad cloc;
		try {
			cloc = new ControlLocalidad();
			Vector localidades = cloc.obtenerLocalidades();
			for(int i=0;i<localidades.size();i++){
				Localidad lo=(Localidad) localidades.elementAt(i);
				guiListarPrFact.localidades.add(lo.getNombre());
				idLocs.add(lo.getId());
			}
			
		} catch (Exception ex) {
			Utils.manejoErrores(ex,"Error en MediadorListarDeudacliente. Constructor");
		}
        this.guiListarPrFact.setActionListeners(this);
  	}
	
	public void actionPerformed(ActionEvent e) {
		if ((((Component)e.getSource()).getName().compareTo("Verificar Facturas")) == 0) {
			try{
				Date fe= guiListarPrFact.getJDateChooserIngreso().getDate();
				int loc=guiListarPrFact.getJCLocalidades().getSelectedIndex();
				Long idLoc=(Long)idLocs.elementAt(loc);
				new MediadorVistaFacturasGeneradas(fe,idLoc,guiListarPrFact);
				guiListarPrFact.dispose();
			} catch(Exception ex) {
				Utils.manejoErrores(ex,"Error en MediadorListarTurnos. ActionPerformed");
			}
		}else if ((((Component)e.getSource()).getName().compareTo("Salir")) == 0) {
			guiListarPrFact.dispose();
		}
	}
	
		
	public void valueChanged(ListSelectionEvent arg0) {
	}
	
	 public void show() {
		 guiListarPrFact.actualizarBusqueda();
		 Utils.show(guiListarPrFact);
	    }
}
