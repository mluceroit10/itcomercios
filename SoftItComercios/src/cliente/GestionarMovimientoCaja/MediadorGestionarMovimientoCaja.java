package cliente.GestionarMovimientoCaja;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import persistencia.domain.MovimientoCaja;
import server.GestionarMovimientoCaja.ControlMovimientoCaja;

import common.Utils;
import common.GestionarMovimientoCaja.IControlMovimientoCaja;

public class MediadorGestionarMovimientoCaja implements ActionListener, KeyListener, ListSelectionListener {
    
    private GUIGestionarMovimientoCaja guiMovimientoCaja = null;
    protected IControlMovimientoCaja controlMovimientoCaja;
    private int mesLI;
	private int anioLI;
    
    public MediadorGestionarMovimientoCaja(int mes, int anio,JFrame guiPadre) {
    	try{
    		mesLI=mes;
    		anioLI=anio;
    		this.controlMovimientoCaja = new ControlMovimientoCaja();
        }catch(Exception ex){
        	Utils.manejoErrores(ex,"Error en MediadorGestionarMovimientoCaja. Constructor");
        }
        this.guiMovimientoCaja = new GUIGestionarMovimientoCaja(mesLI,anioLI,guiPadre);
        this.guiMovimientoCaja.setActionListeners(this);
        cargarDatos();
        this.guiMovimientoCaja.setListSelectionListener(this);
        this.guiMovimientoCaja.setKeyListener(this);
        Utils.show(guiMovimientoCaja);
    }
    
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
    	if (source == guiMovimientoCaja.getJBCargar()) {
            try {
                new MediadorAltaMovimientoCaja(this,guiMovimientoCaja);
            } catch (Exception ex) {
            	Utils.manejoErrores(ex,"Error en MediadorGestionarMovimientoCaja. ActionPerformed");
            }
        } else if (source == guiMovimientoCaja.getJBBorrar()) {
            eliminar();
        }else if (source == guiMovimientoCaja.getJBCambiarPeriodo()){
        	String anioB = guiMovimientoCaja.getJTFAnio().getText();
        	if(anioB.length()==0){
				Utils.advertenciaUsr(guiMovimientoCaja,"Por favor ingrese el A�o.");
			}else if(anioB.length()!=4){
				Utils.advertenciaUsr(guiMovimientoCaja,"El a�o debe ser un n�mero de 4 d�gitos.");
			}else{
				anioLI= Integer.parseInt(anioB);
				mesLI = guiMovimientoCaja.getJCBMes().getSelectedIndex()+1; //para que el numero del indice de con el mes sumo 1
	         	cargarDatos();
			} 	
        } else if (source == guiMovimientoCaja.getJBAceptar()) {
        	guiMovimientoCaja.dispose();
        }	
    }
    
    public void cargarDatos() {
        try {
            Vector movsCaja = this.controlMovimientoCaja.obtenerMovimientosCajaPeriodo(mesLI,anioLI);
            guiMovimientoCaja.getJTFPeriodo().setText(mesLI+" - "+anioLI);
            guiMovimientoCaja.datos = new Object[movsCaja.size()][guiMovimientoCaja.titulos.length];
            int i = 0;
            for (int j = 0; j < movsCaja.size(); j++) {
            	MovimientoCaja mc=(MovimientoCaja)movsCaja.elementAt(j);
            	String tipoMov="SALIDA";
        		if(mc.getTipoMovimiento()==1){
        			tipoMov="ENTRADA";
        		}
        		String tipoFactNro="";
        		if(mc.isConFactura()){
        			tipoFactNro = mc.getTipoFactura()+" - "+ Utils.nroFact(mc.getFactura().getNroFactura());
        		}
        		String fp = mc.getFormaPago();
        		if (mc.getNroCheque()!=null) fp +=" N�: "+mc.getNroCheque();
        		Object[] temp = {mc.getId(),String.valueOf(mc.getCodigo()),common.Utils.getStrUtilDate(mc.getFecha()),tipoMov,Utils.ordenarDosDecimales(mc.getImporte()),fp,mc.getDescripcion(),tipoFactNro};
            		guiMovimientoCaja.datos[i] = temp;
                	i++;
            }
    	}catch(Exception ex){
    		Utils.manejoErrores(ex,"Error en MediadorGestionarMovimientoCaja. CargarDatos");
    	}
    	guiMovimientoCaja.jtDatos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        guiMovimientoCaja.actualizarTabla();
    }
    
    private void actualizarTablaCodFecha() {
    	try {
            Vector movsCaja = this.controlMovimientoCaja.obtenerMovimientosCajaFiltros(mesLI,anioLI,guiMovimientoCaja.getJTFBuscadorCodigo().getText(),guiMovimientoCaja.getJTFBuscadorFecha().getText());
            guiMovimientoCaja.datos = new Object[movsCaja.size()][guiMovimientoCaja.titulos.length];
            for (int j = 0; j < movsCaja.size(); j++) {
            	MovimientoCaja mc=(MovimientoCaja)movsCaja.elementAt(j);
            	String tipoMov="SALIDA";
            	if(mc.getTipoMovimiento()==1){
            		tipoMov="ENTRADA";
            	}
            	String tipoFactNro="";
            	if(mc.isConFactura()){
            		tipoFactNro = mc.getTipoFactura()+" - "+ Utils.nroFact(mc.getFactura().getNroFactura());
            	}
            	String fp = mc.getFormaPago();
            	if (mc.getNroCheque()!=null) fp +=" N�: "+mc.getNroCheque();
            	Object[] temp = {mc.getId(),String.valueOf(mc.getCodigo()),common.Utils.getStrUtilDate(mc.getFecha()),tipoMov,String.valueOf(mc.getImporte()),fp,mc.getDescripcion(),tipoFactNro};
            	guiMovimientoCaja.datos[j] = temp;	
            }
    	} catch(Exception ex) {
    		Utils.manejoErrores(ex,"Error en MediadorGestionarMovimientoCaja. ActualizarTablaFechaMov");
    	}
	    guiMovimientoCaja.jtDatos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        guiMovimientoCaja.actualizarTabla();
    }
    
    private void eliminar() {
        try{
            if ( this.controlMovimientoCaja.obtenerMovimientosCajaPeriodo(mesLI,anioLI).isEmpty()){
            	Utils.advertenciaUsr(guiMovimientoCaja,"No hay Movimientos de Caja guardados.");
            } else {
                if (guiMovimientoCaja.jtDatos.getSelectedRow() == -1){
                	Utils.advertenciaUsr(guiMovimientoCaja,"Para poder Eliminar un Movimiento de Caja debe seleccionarlo previamente.");
                } else {
                	Long id = (Long)guiMovimientoCaja.datos[guiMovimientoCaja.jtDatos.getSelectedRow()][0];
                	String cod = (String)guiMovimientoCaja.datos[guiMovimientoCaja.jtDatos.getSelectedRow()][1];
                    if (controlMovimientoCaja.movimientoCajaAsociado(id)) {
                    	Utils.advertenciaUsr(guiMovimientoCaja,"El Movimiento de Caja no puede ser borrado.");
                    }else{
                    	int prueba = Utils.aceptarCancelarAccion(guiMovimientoCaja,"Esta seguro que desea Eliminar el Movimiento de Caja "+ cod);
                    	if (prueba == 0){
                    		this.controlMovimientoCaja.eliminarMovimientoCaja(id);
                    	}    
                    }     
                    cargarDatos();
                }
            }
        }catch(Exception ex){
        	Utils.manejoErrores(ex,"Error en MediadorGestionarMovimientoCaja. Eliminar");
        }
    }
    
    public GUIGestionarMovimientoCaja getGUI() {
        return guiMovimientoCaja;
    }
    
    public void keyReleased(KeyEvent e) {
    	actualizarTablaCodFecha();
    }
    
    public void keyTyped(KeyEvent e) {
    }
    
    public void keyPressed(KeyEvent e) {
    }
    
    public void valueChanged(ListSelectionEvent arg0) { }
}




