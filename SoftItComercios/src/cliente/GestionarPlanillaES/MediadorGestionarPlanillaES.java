package cliente.GestionarPlanillaES;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import persistencia.domain.FacturaCliente;
import persistencia.domain.ItemFactura;
import persistencia.domain.MovimientoCaja;
import persistencia.domain.PlanillaES;
import server.GestionarMovimientoCaja.ControlMovimientoCaja;
import server.RealizarPlanillaES.ControlRealizarPlanillaES;
import cliente.Principal.GUIPrincipal;
import cliente.Principal.GUIReport;

import common.Utils;
import common.GestionarMovimientoCaja.IControlMovimientoCaja;
import common.RealizarPlanillaES.IControlRealizarPlanillaES;

public class MediadorGestionarPlanillaES implements ActionListener, KeyListener, ListSelectionListener {
    
    private GUIGestionarPlanillaES guiImprimirPlanillaES = null;
    protected IControlRealizarPlanillaES controlRealizarPlanillaES;
    protected IControlMovimientoCaja controlMovimientoCaja;
    private PlanillaES miPESDto;
    private int mesLI;
	private int anioLI;
	Long [] codigos;
	String [] productos;
	String [] proveedores;
	int [] cantidades;
	double [] kilos;
	int [] stUnid;
	double [] stKilo;
	int cantProdEncontrados=0;
	
    public MediadorGestionarPlanillaES(int mes, int anio,JFrame guiPadre) throws Exception {
    	try{
    		mesLI=mes;
    		anioLI=anio;
    		this.controlRealizarPlanillaES = new ControlRealizarPlanillaES();
    		this.controlMovimientoCaja = new ControlMovimientoCaja();
        }catch(Exception ex){
        	Utils.manejoErrores(ex,"Error en MediadorGestionarPlanillaES. Constructor");
        }
        this.guiImprimirPlanillaES = new GUIGestionarPlanillaES(mesLI,anioLI,guiPadre);
        this.guiImprimirPlanillaES.setActionListeners(this);
        cargarDatos();
        this.guiImprimirPlanillaES.setListSelectionListener(this);
        this.guiImprimirPlanillaES.setKeyListener(this);
        Utils.show(guiImprimirPlanillaES);
    }
    
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == guiImprimirPlanillaES.getJBImprimir()) {
    		try {
    			if (guiImprimirPlanillaES.jtDatos.getSelectedRow() == -1){
    				Utils.advertenciaUsr(guiImprimirPlanillaES,"Para poder Imprimir una Planilla E/S debe ser previamente seleccionada.");
                }else{
                	Long id = (Long)guiImprimirPlanillaES.datos[guiImprimirPlanillaES.jtDatos.getSelectedRow()][0];
                	miPESDto = this.controlRealizarPlanillaES.buscarPlanillaES(id);
                	int nro=miPESDto.getNroPlanilla();
                	PlanillaES anterior = new PlanillaES();
                	if(nro>(Utils.NROPLANILLAANTERIOR+1)) anterior=this.controlRealizarPlanillaES.buscarPlanillaESNroPlanilla(nro-1);
                	else anterior.setSaldo(0);
                 	Vector entr = this.entradas(miPESDto.getMovimientosCaja());
                	Vector sal = this.salidas(miPESDto.getMovimientosCaja());
                	Set factsRem = miPESDto.getFacturas();
                	Vector fact=new Vector();
                	Vector remitos=new Vector();
    				for (Iterator i = factsRem.iterator(); i.hasNext(); ) {
    					FacturaCliente fc = (FacturaCliente)i.next();
    					if(fc.getTipoFactura().compareTo("RemitoCliente")==0){
    						remitos.add(fc);
    					}else{
    						fact.add(fc);
    					}
    				}
    				double ingR=this.totalIngresosRemitos(remitos);
                	new GUIReport(guiImprimirPlanillaES,7,entr,fact,ingR,sal,miPESDto.getNroPlanilla(),miPESDto.getFecha(),anterior.getSaldo(),miPESDto.getSaldo());
                	
        		 	int prueba = Utils.aceptarCancelarAccion(guiImprimirPlanillaES,"Desea obtener el detalle de stock",new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/productosFacturados.png")));
            		if( prueba==0 ){
            	 		this.organizarProductos(remitos,fact);
            			new GUIReport(guiImprimirPlanillaES,19,miPESDto.getNroPlanilla(),cantProdEncontrados,codigos, productos, proveedores, cantidades, kilos, stUnid, stKilo,miPESDto.getFecha());
            		}    
            		
                }
    		} catch(Exception ex) {
    			Utils.manejoErrores(ex,"Error en MediadorGestionarPlanillaES. ActionPerformed");
    		}
        }else if (source == guiImprimirPlanillaES.getJBCargar()) {
    		java.util.Date fe = guiImprimirPlanillaES.getJDateChooserFecha().getDate();
    		Date fecha = Utils.crearFecha(fe);//new Date(fe.getYear(),fe.getMonth(),fe.getDate());
    		try {
    			PlanillaES ultima=controlRealizarPlanillaES.obtenerUltimaPlanilla();
    			PlanillaES miplDTO = new PlanillaES();
    			miplDTO.setNroPlanilla(ultima.getNroPlanilla()+1);
    			miplDTO.setFecha(fecha);
    			Vector mov=controlRealizarPlanillaES.obtenerMovimientosCajaParaPlanilla(fecha);
				Vector remitos=controlRealizarPlanillaES.obtenerRemitosClienteParaPlanilla(fecha); //aca se deben obtener solo remitos no facturados
				Vector fact=controlRealizarPlanillaES.obtenerFacturasClienteParaPlanilla(fecha);//aca se deben obtener solo fact cte de pago contado, incluir fact de remito
				double egrCD=this.totalEgresosMovs(mov);
				double ingFM=this.totalIngresosFactsMovs(fact,mov);
				double ingR=this.totalIngresosRemitos(remitos);
				double ingCD=Utils.redondear(ingFM+ingR,2);
				double impCDiaria=generarSaldoCaja(ultima.getSaldo(),ingCD,egrCD);
				miplDTO.setSaldo(impCDiaria);
    			this.controlRealizarPlanillaES.agregarPlanillaESTotal(miplDTO,mov,fact,remitos);
    			Vector entr = this.entradas(mov);
    			Vector sal = this.salidas(mov);
    			new GUIReport(guiImprimirPlanillaES,7,entr,fact,ingR,sal,ultima.getNroPlanilla()+1,fecha,ultima.getSaldo(),miplDTO.getSaldo());
    			int prueba = Utils.aceptarCancelarAccion(guiImprimirPlanillaES,"Desea obtener el detalle de stock",new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/productosFacturados.png")));
        		if( prueba==0 ){
        	 		this.organizarProductos(remitos,fact);
        			new GUIReport(guiImprimirPlanillaES,19,ultima.getNroPlanilla()+1,cantProdEncontrados,codigos, productos, proveedores, cantidades, kilos, stUnid, stKilo,fecha);
        		}    
    			guiImprimirPlanillaES.dispose();
    		}
    		catch(Exception ex) {
    			Utils.manejoErrores(ex,"Error en MediadorGestionarPlanillaES. ActionPerformed");
    		}
        }else if (source == guiImprimirPlanillaES.getJBBorrar()) {
        	 try{
                 if ( this.controlRealizarPlanillaES.obtenerPlanillasES(mesLI,anioLI).isEmpty()){
                	 Utils.advertenciaUsr(guiImprimirPlanillaES,"No hay Planillas guardadas.");
                 } else {
                	 if (guiImprimirPlanillaES.jtDatos.getSelectedRow() == -1){
                		 Utils.advertenciaUsr(guiImprimirPlanillaES,"Para poder Eliminar una Planilla debe seleccionarla previamente.");
                	 } else {
                		 int sel=guiImprimirPlanillaES.jtDatos.getSelectedRow();
                		 if(sel==(guiImprimirPlanillaES.datos.length -1)){
                			 Long id = (Long)guiImprimirPlanillaES.datos[guiImprimirPlanillaES.jtDatos.getSelectedRow()][0];
                			 String numero = (String)guiImprimirPlanillaES.datos[guiImprimirPlanillaES.jtDatos.getSelectedRow()][1];
                			 int prueba = Utils.aceptarCancelarAccion(guiImprimirPlanillaES,"Esta seguro que desea Eliminar la Planilla Nro: " + numero);
                			 if (prueba == 0){
                				 this.controlRealizarPlanillaES.eliminarPlanillaES(id);
                			 }
                			 cargarDatos();
                		 }else{
                			 Utils.advertenciaUsr(guiImprimirPlanillaES,"Solo es posible eliminar el último cierre de caja");
                		 }
                	 }
                 }
             }catch(Exception ex){
            	 Utils.manejoErrores(ex,"Error en MediadorGestionarPlanillaES. Eliminar");
             }
        } else if (source == guiImprimirPlanillaES.getJBSalir()) {
        	guiImprimirPlanillaES.dispose();	
        }else if (source == guiImprimirPlanillaES.getJBCambiarPeriodo()){
        	String anioB = guiImprimirPlanillaES.getJTFAnio().getText();
        	if(anioB.length()==0){
				Utils.advertenciaUsr(guiImprimirPlanillaES,"Por favor ingrese el Año.");
			}else if(anioB.length()!=4){
				Utils.advertenciaUsr(guiImprimirPlanillaES,"El año debe ser un número de 4 dígitos.");
			}else{
				anioLI= Integer.parseInt(anioB);
				mesLI = guiImprimirPlanillaES.getJCBMes().getSelectedIndex()+1; //para que el numero del indice de con el mes sumo 1
	         	cargarDatos();
			}	
    	} else if ((((Component)e.getSource()).getName().compareTo("combo")) == 0){
    		if (((String)(((JComboBox)e.getSource()).getSelectedItem())).compareTo("Fecha")==0) {
    			guiImprimirPlanillaES.mostrarJTFFecha();
    		}
    		if (((String)(((JComboBox)e.getSource()).getSelectedItem())).compareTo("Nro Planilla")==0) {
    			guiImprimirPlanillaES.mostrarJTFNro();
    		}
    	} else { 
        	guiImprimirPlanillaES.dispose();
    	}
    }
    
    private void organizarProductos(Vector remitos, Vector fact) {
    	Vector facturaClienteCol=remitos;
		facturaClienteCol.addAll(fact);
    	codigos= new Long [20*facturaClienteCol.size()];
		productos= new String [20*facturaClienteCol.size()];
		proveedores= new String [20*facturaClienteCol.size()];
		cantidades= new int [20*facturaClienteCol.size()];
		kilos= new double [20*facturaClienteCol.size()];
		stUnid=new int [20*facturaClienteCol.size()];
		stKilo=new double [20*facturaClienteCol.size()];
		
		cantProdEncontrados = 0;
		for(int i=0;i<facturaClienteCol.size();i++){
			FacturaCliente fc= (FacturaCliente)facturaClienteCol.elementAt(i);
			Set prodFact = fc.getItems();
			for(Iterator it=prodFact.iterator(); it.hasNext(); ){
				ItemFactura item = (ItemFactura) it.next();
				boolean encontrado=false;
				int posEncontrado=0;
				for(int k=0; k<cantProdEncontrados; k++){
					if(codigos[k].equals(item.getProducto().getCodigo())){
						encontrado=true;
						posEncontrado=k;
					}
				}
				if(encontrado){
					cantidades[posEncontrado]=cantidades[posEncontrado]+item.getCantidad();
					kilos[posEncontrado]=kilos[posEncontrado]+item.getKilos();
				}else{
					codigos[cantProdEncontrados]=item.getProducto().getCodigo();
					productos[cantProdEncontrados]=item.getProducto().getNombre();
					proveedores[cantProdEncontrados]=item.getProducto().getProveedor().getNombre();
					stUnid[cantProdEncontrados]=item.getProducto().getStockActual();
					stKilo[cantProdEncontrados]=item.getProducto().getStockKilosAct();
					cantidades[cantProdEncontrados]=item.getCantidad();
					kilos[cantProdEncontrados]=item.getKilos();
					cantProdEncontrados++;
				}
			}
		}
	}

	public void cargarDatos() {
        try {
            Vector planillas = this.controlRealizarPlanillaES.obtenerPlanillasES(mesLI,anioLI);
            guiImprimirPlanillaES.getJTFPeriodo().setText(mesLI+" - "+anioLI);
            guiImprimirPlanillaES.datos = new Object[planillas.size()][guiImprimirPlanillaES.titulos.length];
            int i = 0;
            for (int j = 0; j < planillas.size(); j++) {
            	PlanillaES p=(PlanillaES)planillas.elementAt(j);
            	Object[] temp = {p.getId(),String.valueOf(p.getNroPlanilla()),common.Utils.getStrUtilDate(p.getFecha())};
            	guiImprimirPlanillaES.datos[i] = temp;
            	i++;
            }
    	}catch(Exception ex){
    		Utils.manejoErrores(ex,"Error en MediadorGestionarPlanillaES. CargarDatos");
    	}
    	guiImprimirPlanillaES.jtDatos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	guiImprimirPlanillaES.actualizarTabla();
    }
    
    public void actualizarFecha() {
    	 try {
             Vector planillas = this.controlRealizarPlanillaES.obtenerPlanillasESFiltroFecha(mesLI,anioLI,guiImprimirPlanillaES.getJTFFecha().getText());
             guiImprimirPlanillaES.datos = new Object[planillas.size()][guiImprimirPlanillaES.titulos.length];
             for (int j = 0; j < planillas.size(); j++) {
            	 PlanillaES p=(PlanillaES)planillas.elementAt(j);
            	 Object[] temp = {p.getId(),String.valueOf(p.getNroPlanilla()),common.Utils.getStrUtilDate(p.getFecha())};
            	 guiImprimirPlanillaES.datos[j] = temp;
             }
     	}catch(Exception ex){
     		Utils.manejoErrores(ex,"Error en MediadorGestionarPlanillaES. ActualizarFecha");
     	}
     	guiImprimirPlanillaES.jtDatos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
     	guiImprimirPlanillaES.actualizarTabla();
    }
    
    public void actualizarNroPlanilla() {
        try {
            Vector planillas = this.controlRealizarPlanillaES.obtenerPlanillasESFiltroNroPl(mesLI,anioLI,guiImprimirPlanillaES.getJTFNro().getText());
            guiImprimirPlanillaES.datos = new Object[planillas.size()][guiImprimirPlanillaES.titulos.length];
            for (int j = 0; j < planillas.size(); j++) {
            	PlanillaES p=(PlanillaES)planillas.elementAt(j);
            	Object[] temp = {p.getId(),String.valueOf(p.getNroPlanilla()),common.Utils.getStrUtilDate(p.getFecha())};
            	guiImprimirPlanillaES.datos[j] = temp;
            }
    	}catch(Exception ex){
    		Utils.manejoErrores(ex,"Error en MediadorGestionarPlanillaES. ActualizarNroPlanilla");
    	}
    	guiImprimirPlanillaES.jtDatos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	guiImprimirPlanillaES.actualizarTabla();
    }
    
    public GUIGestionarPlanillaES getGUI() {
        return guiImprimirPlanillaES;
    }
    
    public void keyReleased(KeyEvent e) {
    	Object source = e.getSource();
    	  if (source == this.guiImprimirPlanillaES.getJTFNro()) {
	    	  actualizarNroPlanilla();
	      }else
	    	  actualizarFecha();
    }
        
    public void keyTyped(KeyEvent e) {
    }
    
    public void keyPressed(KeyEvent e) {
    }
    
    public void valueChanged(ListSelectionEvent arg0) { }

    private double generarSaldo(Vector movs,double saldo){
    	double total=saldo;
    	for(int i=0;i<movs.size();i++){
    		MovimientoCaja m= (MovimientoCaja)movs.elementAt(i);
    		if(m.getTipoMovimiento()==1) //entrada
    			total += m.getImporte();
    		else //salida	
    			total -= m.getImporte();
    	}
    	return total;
    }
    
    private Vector entradas(Vector movs) throws Exception{
    	Vector entr=new Vector();
    	for(int i=0;i<movs.size();i++){
    		MovimientoCaja m= (MovimientoCaja)movs.elementAt(i);
    		if(m.getTipoMovimiento()==1){ //entrada
    			MovimientoCaja mov=controlMovimientoCaja.buscarMovimientoCaja(m.getCodigo());
    			entr.add(mov);
    		}	
    	}
    	return entr;
    }
    
    private Vector salidas(Vector movs) throws Exception{
    	Vector sal=new Vector();
    	for(int i=0;i<movs.size();i++){
    		MovimientoCaja m= (MovimientoCaja)movs.elementAt(i);
    		if(m.getTipoMovimiento()!=1){ //salida
    			MovimientoCaja mov=controlMovimientoCaja.buscarMovimientoCaja(m.getCodigo());
    			sal.add(mov);
    		}	
    	}
    	return sal;
    }
    
    private Vector entradas(Set set) throws Exception{
    	Vector entr=new Vector();
    	for(Iterator i=set.iterator();i.hasNext();){
    		MovimientoCaja m= (MovimientoCaja)i.next();
    		if(m.getTipoMovimiento()==1){ //entrada
    			MovimientoCaja mov=controlMovimientoCaja.buscarMovimientoCaja(m.getCodigo());
    			entr.add(mov);
    		}	
    	}
    	return entr;
    }
    
    private Vector salidas(Set set) throws Exception{
    	Vector sal=new Vector();
    	for(Iterator i=set.iterator();i.hasNext();){
    		MovimientoCaja m= (MovimientoCaja)i.next();
    		if(m.getTipoMovimiento()!=1){ //salida
    			MovimientoCaja mov=controlMovimientoCaja.buscarMovimientoCaja(m.getCodigo());
    			sal.add(mov);
    		}	
    	}
    	return sal;
    }
    
	private double generarSaldoCaja(double saldo,double ingresosCD, double egresosCD){
		double total= Utils.redondear((saldo+ingresosCD) - egresosCD, 2);
		return total;
	}

	private double totalIngresosFactsMovs(Vector fact,Vector movs){
		double total=0;
		for(int i=0;i<fact.size();i++){
			FacturaCliente f= (FacturaCliente)fact.elementAt(i);
			double numero=total+f.getImporteTotal();
			total = Utils.redondear(numero, 2);
		}
		for(int j=0;j<movs.size();j++){
			MovimientoCaja m= (MovimientoCaja)movs.elementAt(j);
			if(m.getTipoMovimiento()==1){ //entrada
				double numero=total+m.getImporte();
				total = Utils.redondear(numero, 2);
			}
		}
		return total;
	}
	
	private double totalIngresosRemitos(Vector remitos){
		double total=0;
		for(int i=0;i<remitos.size();i++){
			FacturaCliente f= (FacturaCliente)remitos.elementAt(i);
			double numero=total+f.getImporteTotal();
			total = Utils.redondear(numero, 2);
		}
		return total;
	}

	private double totalEgresosMovs(Vector movs){
		double total=0;
		for(int j=0;j<movs.size();j++){
			MovimientoCaja m= (MovimientoCaja)movs.elementAt(j);
			if(m.getTipoMovimiento()!=1){ //salida
					double numero=total+m.getImporte();
					total = Utils.redondear(numero, 2);
			}
		}
		return total;
	}

}




