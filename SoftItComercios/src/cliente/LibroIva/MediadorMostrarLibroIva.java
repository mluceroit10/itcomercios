package cliente.LibroIva;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import persistencia.domain.Comercio;
import persistencia.domain.FacturaCliente;
import server.GestionarComercio.ControlComercio;
import server.GestionarFacturaCliente.ControlFacturaCliente;
import cliente.Principal.GUIReport;

import common.Utils;

public class MediadorMostrarLibroIva implements ActionListener,ListSelectionListener {
	
	public ControlFacturaCliente controlFacturaCliente;
    private ControlComercio controlComercio;
	private GUIMostrarLibroIva guiMostrarLibroIva=null;
	private int mesLI;
	private int anioLI;
	Vector ids= new Vector();
	
	public MediadorMostrarLibroIva(int mes, int anio,JDialog guiPadre) throws Exception {
		try{
    		this.controlFacturaCliente = new ControlFacturaCliente();
    		controlComercio = new ControlComercio();
    		mesLI=mes;
    		anioLI=anio;
    	}catch(Exception ex){
    		Utils.manejoErrores(ex,"Error en MediadorGestionarLocalidad. Constructor");
        }
    	
        this.guiMostrarLibroIva = new GUIMostrarLibroIva(mes,anio,guiPadre);
        this.guiMostrarLibroIva.setActionListeners(this);
        cargarDatos();
    	Utils.show(guiMostrarLibroIva);
   }
		
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
    	if (source == guiMostrarLibroIva.getJBImprimir()){
    		try{
    				String titulo="Libro Iva del mes: "+mesLI+" año: "+anioLI;
    				Comercio dist=controlComercio.obtenerComercio();
    				new GUIReport(guiMostrarLibroIva,18,dist,titulo,mesLI+"/"+anioLI,guiMostrarLibroIva.datos,guiMostrarLibroIva.getJTFNeto().getText(),guiMostrarLibroIva.getJTFIva().getText(),guiMostrarLibroIva.getJTFTotal().getText());
		
    		} catch(Exception ex) {
    			Utils.manejoErrores(ex,"Error en MediadorCuentaCliente. ActionPerformed");
            }
       }else if (source == guiMostrarLibroIva.getJBSalir()) {
    	   guiMostrarLibroIva.dispose();
       }
	}
	
	public void valueChanged(ListSelectionEvent arg0) {
	}
	
	public void cargarDatos() {
		double totalNeto=0;
		double totalIva=0;
		double totalTotal=0;
    	try {
    		Vector facts = new Vector();
    		facts = this.controlFacturaCliente.obtenerFacturasLibroIva(mesLI,anioLI);
    		guiMostrarLibroIva.datos = new Object[facts.size()][guiMostrarLibroIva.titulos.length];
    		int i = 0;
    		if(facts!=null){
    			for (int j = 0; j < facts.size(); j++) {
    				FacturaCliente fc = (FacturaCliente) facts.elementAt(j);
    				String cte=fc.getCliente().getNombre();
    				String categ=fc.getIva();
    				String cuit=fc.getCliente().getCuit();
    				String tipo="";
    				if(fc.getTipoFactura().compareTo("FacturaCliente-A")==0)
    					tipo="A";
    				if(fc.getTipoFactura().compareTo("FacturaCliente-B")==0)
    					tipo="B";
    				String nroFact=Utils.nroFact(fc.getNroFactura());
    				String pv = nroFact.substring(0,4);
    				String nro=  nroFact.substring(5,13);
    				String neto="";
    				String iva="";
    				String total="";
    				if(fc.isAnulada()){
    					cte="ANULADA";
    					categ="";
    					cuit="00-00000000-0";
    					neto="0";
    					if(fc.getImporteAuxIva()!=0) iva="0";
        				total="0";
    				}else{
    					if(tipo.compareTo("A")==0){
    						double netoFact=Utils.redondear(fc.getImporteTotal()-fc.getImporteAuxIva(),2);
    						totalNeto = Utils.redondear(netoFact+totalNeto,2);
    						neto=Utils.ordenarDosDecimales(netoFact);
    						iva=Utils.ordenarDosDecimales(fc.getImporteAuxIva());
    						totalIva = Utils.redondear(fc.getImporteAuxIva()+totalIva,2);	
    					}
        					
        				if(tipo.compareTo("B")==0){
        					double netoFact=Utils.redondear(fc.getImporteTotal()/1.21,2);
        					totalNeto = Utils.redondear(netoFact+totalNeto,2);
        					neto=Utils.ordenarDosDecimales(netoFact);
        					double ivaD=Utils.redondear(fc.getImporteTotal()-netoFact,2);
        					iva=Utils.ordenarDosDecimales(ivaD);
        					totalIva = Utils.redondear(ivaD+totalIva,2);
        				}
    					total=Utils.ordenarDosDecimales(fc.getImporteTotal());
        				totalTotal = Utils.redondear(fc.getImporteTotal()+totalTotal,2);
    				}
    				   				
    				Object[] temp = {Utils.getStrUtilDate(fc.getFechaImpresion()),"Fact",tipo,pv,nro,cte,categ,cuit,neto,iva,total};
    				guiMostrarLibroIva.datos[i] = temp;
    				i++;
    			}
    		}
    	}catch(Exception ex){
    		Utils.manejoErrores(ex,"Error en MediadorListarDeudaCliente. CargarDatos");
    	}
    	guiMostrarLibroIva.getJTFNeto().setText(Utils.ordenarDosDecimales(totalNeto));
    	guiMostrarLibroIva.getJTFIva().setText(Utils.ordenarDosDecimales(totalIva));
    	guiMostrarLibroIva.getJTFTotal().setText(Utils.ordenarDosDecimales(totalTotal));
    	guiMostrarLibroIva.actualizarTabla();
    }
			 
}
