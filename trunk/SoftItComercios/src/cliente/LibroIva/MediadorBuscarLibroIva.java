package cliente.LibroIva;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.event.ListSelectionEvent;

import common.Utils;

public class MediadorBuscarLibroIva implements ActionListener {
	
	private GUIBuscarLibroIva guiBuscarLibroIva = null;
	
	public MediadorBuscarLibroIva(JFrame guiPadre){
        this.guiBuscarLibroIva = new GUIBuscarLibroIva(guiPadre);
        this.guiBuscarLibroIva.setActionListeners(this);
        Utils.show(guiBuscarLibroIva);
  	}
	
	public void actionPerformed(ActionEvent e) {
		if ((((Component)e.getSource()).getName().compareTo("Verificar Facturas")) == 0) {
			try{
				int mesL = guiBuscarLibroIva.getJCBMes().getSelectedIndex()+1; //para que el numero del indice de con el mes sumo 1
				String anio =guiBuscarLibroIva.getJTFAnio().getText();
				if(anio.length()==0){
					Utils.advertenciaUsr(guiBuscarLibroIva,"Por favor ingrese el Año.");
				}else if(anio.length()!=4){
					Utils.advertenciaUsr(guiBuscarLibroIva,"El año debe ser un número de 4 dígitos.");
				}else{
					int anioL= Integer.parseInt(anio);
					new MediadorMostrarLibroIva(mesL,anioL,guiBuscarLibroIva);
					guiBuscarLibroIva.dispose();
				}
			} catch(Exception ex) {
				Utils.manejoErrores(ex,"Error en MediadorListarTurnos. ActionPerformed");
			}
		}else if ((((Component)e.getSource()).getName().compareTo("Salir")) == 0) {
			guiBuscarLibroIva.dispose();
		}
	}
	
		
	public void valueChanged(ListSelectionEvent arg0) {
	}
	
	 
}
