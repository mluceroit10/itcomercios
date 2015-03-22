package cliente.GestionarProducto;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JDialog;

import persistencia.domain.Producto;
import persistencia.domain.Proveedor;
import server.GestionarProducto.ControlProducto;
import cliente.GestionarProveedor.MediadorGestionarProveedor;

import common.Utils;
import common.GestionarProducto.IControlProducto;

public class MediadorModificarProducto implements ActionListener,KeyListener {

    private GUIAltaModProducto guiProducto = null;
    private MediadorGestionarProducto mgProducto;
    private IControlProducto controlProducto;
    private Producto prodDTO;
    public Proveedor proveedor;
	private MediadorGestionarProveedor medGestionarProveedor;
    
    public MediadorModificarProducto(MediadorGestionarProducto oldMGProducto, Producto prod,JDialog guiPadre) {
    	try{
    		this.controlProducto = new ControlProducto();	
        }catch(Exception ex){
        	Utils.manejoErrores(ex,"Error en MediadorModificarProducto. Constructor");
        }
        guiProducto = new GUIAltaModProducto(prod,guiPadre);
        guiProducto.setActionListeners(this);
        guiProducto.setKeyListener(this);
        mgProducto = oldMGProducto;
        prodDTO =  prod;
        proveedor = prod.getProveedor();
        Utils.show(guiProducto);
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == guiProducto.getJBAceptar()) {
        	String codigo = guiProducto.getJTFCodigo().getText();
    		String nombre = guiProducto.getJTFNombre().getText();
    		String prKilos = guiProducto.getJCBPrecioKilos().getSelectedItem().toString();
    		String stockM = guiProducto.getJTFStockMin().getText();
            String stockA = guiProducto.getJTFStockAct().getText();
            String prEntr = guiProducto.getJTFPrecioEntrada().getText();
            String mGanancia = guiProducto.getJTFMargenGanancia().getText();
            String nuevoPrVtaSinIva=guiProducto.getJTFPrecioVentaSinIva().getText();
      	    String nuevoPrVtaConIva=guiProducto.getJTFPrecioVentaConIva().getText();
      	    String tipoPrecioEntr=(String) guiProducto.getJCBTipoPrecioEntrada().getSelectedItem();
            String prov = guiProducto.getJTFProveedor().getText();
            String stockMinK = guiProducto.getJTFStKilosMin().getText();
            String stockActK = guiProducto.getJTFStKilosAct().getText();
            
            if (codigo.length()==0 || nombre.length()==0 || stockM.length()==0 || stockA.length()==0 || prEntr.length()==0 || nuevoPrVtaSinIva.length()==0 || nuevoPrVtaConIva.length()==0|| prov.length()==0 ){
            	Utils.advertenciaUsr(guiProducto,"Alguno de los campos obligatorios esta vacio.");
            }else if (prKilos.compareTo("SI")==0 && (stockMinK.length()==0 || stockActK.length()==0)){
        		Utils.advertenciaUsr(guiProducto,"Ingrese el stock en kilos.");
        	}else if (!Utils.esDouble(stockMinK) || !Utils.esDouble(stockActK)){
        		Utils.advertenciaUsr(guiProducto,"Ingrese correctamente el stock en kilos.");
        	}else {
                try {
                	boolean kilosD=false;
                	double stKilosAct = 0;
            		double stKilosMin = 0;
                    if(prKilos.compareTo("SI")==0){
                    	kilosD = true;
                    	stKilosAct = Double.parseDouble(stockActK);
                        stKilosMin = Double.parseDouble(stockMinK);
                    }
                    double prEntrada = Double.parseDouble(prEntr);
                    double prVentaSinIva = Double.parseDouble(nuevoPrVtaSinIva);
                    double prVentaConIva = Double.parseDouble(nuevoPrVtaConIva);
                    boolean conIva=false;
                    if( tipoPrecioEntr.compareTo("CON IVA")==0)
                    	conIva=true;
                    
                 //   int cod = Integer.parseInt(codigo);
            		Producto prod = new Producto();
            		prod.setCodigo(new Long(codigo));
            		prod.setNombre(nombre);
            		prod.setPrecioKilos(kilosD);
            		prod.setStockMinimo(Integer.parseInt(stockM));
            		prod.setStockActual(Integer.parseInt(stockA));
            		prod.setStockKilosAct(stKilosAct);
            		prod.setStockKilosMin(stKilosMin);
            		prod.setPrecioEntrada(prEntrada);
            		prod.setGanancia(Integer.parseInt(mGanancia));
            		prod.setPrecioVentaSinIva(prVentaSinIva);
            		prod.setPrecioEntCIva(conIva);
            		prod.setPrecioVentaConIva(prVentaConIva);
            		prod.setProveedor(proveedor);
                    if (this.controlProducto.puedoEditar(prodDTO,prod)){
                        this.controlProducto.modificarProducto(prodDTO.getId(), prod);
                        guiProducto.dispose();
                        mgProducto.cargarDatos();
                    } else {
                    	Utils.advertenciaUsr(guiProducto,"El Producto que desea ingresar ya existe.");
                    }
                } catch(Exception ex) {
                	Utils.manejoErrores(ex,"Error en MediadorModificarProducto. ActionPerformed");
                }
            }
        }else if (source == guiProducto.getJBProveedor()) {
        	buscarProveedor();
        } else if (source == guiProducto.getJCBTipoPrecioEntrada()){	
        	cambioDePrecios();	
        } else if (source == guiProducto.getJBCancelar()) {   
        	guiProducto.dispose();
        } 
    }

    private void buscarProveedor() {
        if (medGestionarProveedor== null) {
        	medGestionarProveedor= new MediadorGestionarProveedor(this,guiProducto);
        } else {
            if (!medGestionarProveedor.getGUI().isVisible()) {
            	medGestionarProveedor.getGUI().setVisible(true);
            }
        }
        if (proveedor != null){
            this.guiProducto.getJTFProveedor().setText(proveedor.getNombre());
            this.cargarProveedor(proveedor);
        }
    }
    
    private void cargarProveedor(Proveedor pr) {
        this.proveedor = pr;
    }
    
    public void actualizarProveedor() {
    	guiProducto.setProveedor(proveedor.getNombre());
    }

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void keyReleased(KeyEvent e) {  
		cambioDePrecios();
	}
	
	public void cambioDePrecios(){
    	String prEntr = this.guiProducto.getJTFPrecioEntrada().getText();
		String mGanancia = this.guiProducto.getJTFMargenGanancia().getText();
		String tipoPEntr = (String) this.guiProducto.getJCBTipoPrecioEntrada().getSelectedItem();
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
				this.guiProducto.getJTFPrecioVentaSinIva().setText(prSinIVA);
				this.guiProducto.getJTFPrecioVentaConIva().setText(prConIVA);
			}
		}
    }
}

