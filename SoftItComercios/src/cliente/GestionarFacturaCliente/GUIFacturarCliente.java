package cliente.GestionarFacturaCliente;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import cliente.LimitadorNroMax;
import cliente.LimitadorPrecio;
import cliente.ModeloTabla;
import cliente.Imagenes.Botones.ButtonType;
import cliente.Imagenes.Botones.GlossyButton;
import cliente.Imagenes.util.Theme;
import cliente.Principal.GUIPrincipal;
import cliente.Utils.JPanel_Whit_Image;
import cliente.Utils.TransparentPanel;

import com.toedter.calendar.JDateChooser;
import common.Utils;

public class GUIFacturarCliente extends JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel jpPpal = null;				private JPanel jpDatosProd = null;
    private JPanel jpDatosFactura = null;		private JPanel jpDatosItems=null;
   	private JButton jbAgregarProd = null;  	 	private JButton jbBuscarC=null;
    private JButton jbEliminarProd=null;		private JButton jbConfirmarFact=null;
    private JLabel jlSelecProd = null;			private JLabel jlFechaFactura=null;
    private JLabel jlCodigo = null;	     		private JLabel jlImporte = null;
    private JLabel jlBusqueda = null;			private JLabel jlNroFactura = null;
    private JLabel jlNombreC=null;				private JLabel jlCuit = null;
    private JLabel jlCantidad = null;			private JLabel jlITotal = null;
    private JLabel jlKilos = null;				private JLabel jlImporteIva = null;
    private JLabel jlDescuento = null;
    private JTextField jtfBusqueda = null;
    private JTextField jtfCodigo = null;     	private JTextField jtfImporte = null;
    private JTextField jtfCantidad = null;   	private JTextField jtfITotal = null;
    private JTextField jtfNombreC=null;			private JTextField jtCuit = null;
    private JTextField jtfIngrBrutos;			private JTextField jtfRemitoNro;
    private JTextField jtfDescuento = null;
    private JTextField jtfImporteIva = null;
    private JComboBox jcbCodigo = null;
    private JDateChooser jDataCFecha = null;
    private JScrollPane jspDatosInsc=null;
    public final String[] titulos ={"C�digo","Cant.","Kg.","Producto","Precio Unit.","Descuento %","Precio Total"};
    public Object[][] datos;
    public JTable tabla;					
    public ModeloTabla modTabla = null;
    public Long nroFactura;
    public Vector codProd= new Vector();
	private JTextField jcbTipoIva;
	private JComboBox jcbCondVta;
	private JLabel jlCondVta=null;
	private JLabel jlIva=null;
	private JLabel jlRemNro;
	private JLabel jlIngrBr;
	private JTextField jtfKilos;
	private String tipoF;
	private InputMap map = new InputMap();
    
	private JLabel jlVto;					 	private JComboBox jDFechaVto = null;
	
    public GUIFacturarCliente(String tipo,JFrame guiPadre) {
    	super(guiPadre);
    	tipoF=tipo;
    	this.setSize(new java.awt.Dimension(1300,640));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("Facturacion Cliente - Factura Tipo "+tipoF);
        this.setContentPane(getJPPpal());
        this.setModal(true);
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "pressed");
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "released");
    }

    public JPanel getJPPpal() {
        if (jpPpal == null) {
            jpPpal= new JPanel_Whit_Image("/cliente/Imagenes/Imagenes/background.jpg");
            jpPpal.setLayout(null);
            jpPpal.setSize(new java.awt.Dimension(1300,640));
            jpPpal.add(getJPDatosProducto(), null);
            jpPpal.add(getJPDatosFactura(), null);
            Object[] temp  = {" "," "," "," "," "," "," "};
            datos = new Object[1][titulos.length];
            datos[0]=temp;
            jpPpal.add(getJPDatosItems(), null);
            jpPpal.setBackground(Utils.colorFondo);
        }
        return jpPpal;
    }

    private JPanel getJPDatosProducto() {
        if (jpDatosProd == null) {
        	jpDatosProd = new TransparentPanel();
        	jpDatosProd.setLayout(null);
        	jpDatosProd.setBounds(new java.awt.Rectangle(8,440,1275,160));
        	jpDatosProd.setBorder(Utils.crearTituloYBorde("INGRESO DE PRODUCTOS"));
        	jlBusqueda = new JLabel();
            jlBusqueda.setBounds(new Rectangle(10,30,210,26));
            jlBusqueda.setText("BUSCAR PRODUCTO:");
            jlBusqueda.setHorizontalAlignment(SwingConstants.RIGHT);
            jlBusqueda.setForeground(Utils.colorTexto);
            jlBusqueda.setFont(Utils.FuenteBasica());
        	jlSelecProd = new JLabel();
        	jlSelecProd.setBounds(new Rectangle(400,30,240,26));
        	jlSelecProd.setText("SELECCIONE PRODUCTO");
        	jlSelecProd.setForeground(Utils.colorTexto);
        	jlSelecProd.setFont(Utils.FuenteBasica());
        	jlCodigo = new JLabel();
            jlCodigo.setBounds(new Rectangle(10,65,210,26));
            jlCodigo.setText("PRODUCTO SELEC.:");
            jlCodigo.setHorizontalAlignment(SwingConstants.RIGHT);
            jlCodigo.setForeground(Utils.colorTexto);
            jlCodigo.setFont(Utils.FuenteBasica());
            jlImporte = new JLabel();
            jlImporte.setBounds(new java.awt.Rectangle(960,65,100,26));
            jlImporte.setText("IMPORTE:");
            jlImporte.setForeground(Utils.colorTexto);
            jlImporte.setFont(Utils.FuenteBasica());
            JLabel jlAgregar = new JLabel();
            jlAgregar.setBounds(new java.awt.Rectangle(15,90,1245,60));
            jlAgregar.setBorder(Utils.crearTituloYBordeMini("INGRESE"));
            jlCantidad = new JLabel();
            jlCantidad.setBounds(new java.awt.Rectangle(30,115,130,26));
            jlCantidad.setText("CANTIDAD:");
            jlCantidad.setForeground(Utils.colorTexto);
            jlCantidad.setFont(Utils.FuenteBasica());
            jlKilos = new JLabel();
            jlKilos.setBounds(new Rectangle(280,115,90,20));
            jlKilos.setText("KG.:");
            jlKilos.setForeground(Utils.colorTexto);
            jlKilos.setFont(Utils.FuenteBasica());
            jlDescuento = new JLabel();
            jlDescuento.setBounds(new Rectangle(470,115,180,20));
            jlDescuento.setText("DESCUENTO(%):");
            jlDescuento.setForeground(Utils.colorTexto);
            jlDescuento.setFont(Utils.FuenteBasica());
            jpDatosProd.add(jlSelecProd, null);
            jpDatosProd.add(jlCodigo, null);
            jpDatosProd.add(jlBusqueda, null);
            jpDatosProd.add(jlImporte, null);
            jpDatosProd.add(jlCantidad, null);
            jpDatosProd.add(jlKilos, null);
            jpDatosProd.add(jlDescuento, null);
            jpDatosProd.add(getJLFechaVto(), null);
            jpDatosProd.add(jlAgregar, null);
            jpDatosProd.add(getJTFCodigo(), null);
            jpDatosProd.add(getJTFImporte(), null);
            jpDatosProd.add(getJTFBusqueda(), null);
            jpDatosProd.add(getJTFCantidad(), null);
            jpDatosProd.add(getJTFKilos(), null);
            jpDatosProd.add(getJTFDescuento(), null);
            jpDatosProd.add(getJBAgregarProd(), null);
            jpDatosProd.add(getJCBFechaVto(), null);
        }
        return jpDatosProd;
    }
    
    public JLabel getJLFechaVto() {
		if (jlVto == null) {
			jlVto = new JLabel();
		    jlVto.setBounds(new Rectangle(720,115,90,26));
		    jlVto.setText("VTO.:");
		    jlVto.setForeground(Utils.colorTexto);
		    jlVto.setFont(Utils.FuenteBasica());
		}
		return jlVto;
	}
    
    private JPanel getJPDatosFactura() {
        if (jpDatosFactura == null) {
        	jpDatosFactura = new TransparentPanel();
        	jpDatosFactura.setLayout(null);
        	jpDatosFactura.setBorder(Utils.crearTituloYBorde("DATOS DE FACTURA TIPO "+tipoF));
        	jpDatosFactura.setBounds(new java.awt.Rectangle(8,15,1275,135));
        	jlNombreC = new JLabel();
            jlNombreC.setBounds(new Rectangle(340,30,150,26));
            jlNombreC.setHorizontalAlignment(SwingConstants.RIGHT);
            jlNombreC.setText("NOMBRE:");
            jlNombreC.setForeground(Utils.colorTexto);
            jlNombreC.setFont(Utils.FuenteBasica());
            jlCuit = new JLabel();
        	jlCuit.setBounds(new java.awt.Rectangle(790,30,140,26));
        	jlCuit.setHorizontalAlignment(SwingConstants.RIGHT);
        	jlCuit.setText("CUIT:");
        	jlCuit.setForeground(Utils.colorTexto);
        	jlCuit.setFont(Utils.FuenteBasica());
        	jlFechaFactura = new JLabel();
            jlFechaFactura.setBounds(new Rectangle(10,100,80,26));
            jlFechaFactura.setHorizontalAlignment(SwingConstants.RIGHT);
            jlFechaFactura.setText("FECHA:");
            jlFechaFactura.setForeground(Utils.colorTexto);
            jlFechaFactura.setFont(Utils.FuenteBasica());
            jlIva = new JLabel();
            jlIva.setBounds(new Rectangle(340,65,150,26));
            jlIva.setHorizontalAlignment(SwingConstants.RIGHT);
            jlIva.setText("IVA:");
            jlIva.setForeground(Utils.colorTexto);
            jlIva.setFont(Utils.FuenteBasica());
            jlCondVta = new JLabel();
            jlCondVta.setBounds(new Rectangle(340,100,150,26));
            jlCondVta.setHorizontalAlignment(SwingConstants.RIGHT);
            jlCondVta.setText("COND. VENTA:");
            jlCondVta.setForeground(Utils.colorTexto);
            jlCondVta.setFont(Utils.FuenteBasica());
            jlRemNro = new JLabel();
            jlRemNro.setBounds(new Rectangle(790,100,140,26));
            jlRemNro.setHorizontalAlignment(SwingConstants.RIGHT);
            jlRemNro.setText("REMITO N�:");
            jlRemNro.setForeground(Utils.colorTexto);
            jlRemNro.setFont(Utils.FuenteBasica());
            jlIngrBr = new JLabel();
            jlIngrBr.setBounds(new Rectangle(790,65,140,26));
            jlIngrBr.setHorizontalAlignment(SwingConstants.RIGHT);
            jlIngrBr.setText("ING. BRUTOS:");
            jlIngrBr.setForeground(Utils.colorTexto);
            jlIngrBr.setFont(Utils.FuenteBasica());
            jlNroFactura = new JLabel();
            jlNroFactura.setBounds(new Rectangle(25,65,300,26));
            jlNroFactura.setForeground(Utils.colorTexto);
            jlNroFactura.setFont(Utils.FuenteBasica());
            jpDatosFactura.add(jlNombreC, null);
            jpDatosFactura.add(jlCuit, null);
            jpDatosFactura.add(jlFechaFactura, null);
            jpDatosFactura.add(jlIva, null);
            jpDatosFactura.add(jlCondVta, null);
            jpDatosFactura.add(jlRemNro, null);
            jpDatosFactura.add(jlIngrBr, null);
            jpDatosFactura.add(getJBBuscarC(), null);
            jpDatosFactura.add(getJTFNombreC(), null);
            jpDatosFactura.add(getJtCuit(), null);
            jpDatosFactura.add(getJDateChooserFecha(),null);
            jpDatosFactura.add(getJCCondVta(),null);
            jpDatosFactura.add(getJCTipoIva(),null);
            jpDatosFactura.add(getJTFRemitoNro(),null);
            jpDatosFactura.add(getJTFIngrBrutos(),null);
        }
        return jpDatosFactura;
    }
    
    public JDateChooser getJDateChooserFecha() {
		if (jDataCFecha == null) {
			jDataCFecha = new JDateChooser("dd - MMM - yyyy",false);
			jDataCFecha.getSpinner().setFont(Utils.FuenteFechas());
			jDataCFecha.setBounds(new java.awt.Rectangle(100,100,210,26));
		}
		return jDataCFecha;
	}
    
    public JButton getJBBuscarC() {
        if (jbBuscarC == null) {
            jbBuscarC = new GlossyButton("SELECCIONE EL CLIENTE",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
            jbBuscarC.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/find.png")));
            jbBuscarC.setName("BuscarC");
            jbBuscarC.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jbBuscarC.setBounds(new java.awt.Rectangle(25,30,300,26));
            jbBuscarC.setInputMap(0, map);
            jbBuscarC.setFont(Utils.FuenteBotonesChicos());
            jbBuscarC.setMnemonic('S');
        }
        return jbBuscarC;
    }

    public JButton getJBAgregarProd() {
        if (jbAgregarProd == null) {
        	jbAgregarProd = new GlossyButton("AGREGAR PRODUCTO",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
        	jbAgregarProd.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/add.png")));
        	jbAgregarProd.setName("AgregarProd");
        	jbAgregarProd.setBounds(new java.awt.Rectangle(990,115,260,26));
        	jbAgregarProd.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        	jbAgregarProd.setEnabled(false);
        	jbAgregarProd.setInputMap(0, map);
        	jbAgregarProd.setFont(Utils.FuenteBotonesChicos());
        	jbAgregarProd.setMnemonic('A');
        }
        return jbAgregarProd;
    }
    
    public JButton getJBEliminarProd() {
        if (jbEliminarProd == null) {
        	jbEliminarProd = new GlossyButton("ELIMINAR PRODUCTO",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
        	jbEliminarProd.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/delete.png")));
        	jbEliminarProd.setName("EliminarP");
        	jbEliminarProd.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        	jbEliminarProd.setBounds(new java.awt.Rectangle(30,245,260,26));
        	jbEliminarProd.setEnabled(false);
        	jbEliminarProd.setInputMap(0, map);
        	jbEliminarProd.setFont(Utils.FuenteBotonesChicos());
        	jbEliminarProd.setMnemonic('E');
        }
        return jbEliminarProd;
    }
    
    public JButton getJBConfirmaFact() {
        if (jbConfirmarFact == null) {
        	jbConfirmarFact = new GlossyButton("CONFIRMAR FACTURA",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
        	jbConfirmarFact.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/check.png")));
        	jbConfirmarFact.setName("ConfirmarFact");
        	jbConfirmarFact.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        	jbConfirmarFact.setBounds(new java.awt.Rectangle(500,235,300,40));
        	jbConfirmarFact.setEnabled(false);
        	jbConfirmarFact.setInputMap(0, map);
        	jbConfirmarFact.setFont(Utils.FuenteBotonesGrandes());
        	jbConfirmarFact.setMnemonic('F');
        }
        return jbConfirmarFact;
    }
    
    public JTextField getJTFCodigo() {
        if (jtfCodigo == null) {
        	jtfCodigo = new JTextField();
        	jtfCodigo.setBounds(new Rectangle(230,65,700,26));
        	jtfCodigo.setDisabledTextColor(Utils.colorTextoDisabled);
           	jtfCodigo.setEnabled(false);
           	jtfCodigo.setFont(Utils.FuenteCampos());
        }
        return jtfCodigo;
    }
    
    public JTextField getJTFBusqueda() {
        if (jtfBusqueda == null) {
        	jtfBusqueda = new JTextField();
        	jtfBusqueda.setBounds(new Rectangle(230,30,150,26));
        	jtfBusqueda.setFont(Utils.FuenteCampos());
        }
        return jtfBusqueda;
    }
    
    public JComboBox getJCBCodigo() {
        if (jcbCodigo == null) {
        	jcbCodigo = new JComboBox();
        	jcbCodigo.setBounds(new Rectangle(645,30,615,26));
        	jcbCodigo.removeAllItems();
        	for(int i=0;i<codProd.size();i++){
				String codPr=(String)codProd.elementAt(i);
				jcbCodigo.addItem(codPr);
			}
        	jcbCodigo.setFont(Utils.FuenteCampos());
        	jcbCodigo.setBackground(new Color(255,255,255));
        	jcbCodigo.setForeground(java.awt.Color.black);
	   }
        return jcbCodigo;
    }
    
    public void mostrarCombo(){
    	jpDatosProd.remove(getJCBCodigo());
	 	getJCBCodigo();
	 	jcbCodigo = new JComboBox();
	 	jcbCodigo.setBounds(new Rectangle(645,30,615,26));
	 	jcbCodigo.setBackground(new Color(255,255,255));
    	jcbCodigo.setForeground(java.awt.Color.black);
		for(int i=0;i<codProd.size();i++){
			String codPr=(String)codProd.elementAt(i);
			jcbCodigo.addItem(codPr);
		}
		jcbCodigo.setFont(Utils.FuenteCampos());
		jpDatosProd.add(getJCBCodigo(), null);
		this.repaint();
    	repaint();
    }
    
    public void ocultarCombo(){
    	jpDatosProd.remove(getJCBCodigo());
	 	this.repaint();
    	repaint();
    }
    
    public JTextField getJTFImporte() {
        if (jtfImporte == null) {
        	jtfImporte = new JTextField();
        	jtfImporte.setBounds(new Rectangle(1060,65,200,26));
        	jtfImporte.setDisabledTextColor(Utils.colorTextoDisabled);
        	jtfImporte.setEnabled(false);
        	jtfImporte.setFont(Utils.FuenteCampos());
        	jtfImporte.setHorizontalAlignment(SwingConstants.RIGHT);
        }
        return jtfImporte;
    }
    
    public JTextField getJTFImporteIva() {
        if (jtfImporteIva == null) {
        	jtfImporteIva = new JTextField();
        	jtfImporteIva.setBounds(new Rectangle(1060,205,200,26));
        	jtfImporteIva.setDisabledTextColor(Utils.colorTextoDisabled);
        	jtfImporteIva.setEnabled(false);
        	jtfImporteIva.setFont(Utils.FuenteCampos());
        	jtfImporteIva.setHorizontalAlignment(SwingConstants.RIGHT);
        }
        return jtfImporteIva;
    }
    
    public JTextField getJTFITotal() {
        if (jtfITotal == null) {
        	jtfITotal = new JTextField();
        	jtfITotal.setBounds(new Rectangle(1060,240,200,26));
        	jtfITotal.setDisabledTextColor(Utils.colorTextoDisabled);
        	jtfITotal.setEnabled(false);
        	jtfITotal.setFont(Utils.FuenteCampos());
        	jtfITotal.setHorizontalAlignment(SwingConstants.RIGHT);
        }
        return jtfITotal;
    }
   
    public JTextField getJTFCantidad() {
        if (jtfCantidad == null) {
        	jtfCantidad = new JTextField();
        	jtfCantidad.setBounds(new Rectangle(155,115,100,26));
        	jtfCantidad.setDocument(new LimitadorNroMax(jtfCantidad,6));
        	jtfCantidad.setText("1");
        	jtfCantidad.setFont(Utils.FuenteCampos());
        }
        return jtfCantidad;
    }
    
    public JTextField getJTFKilos() {
        if (jtfKilos == null) {
        	jtfKilos = new JTextField();
        	jtfKilos.setBounds(new Rectangle(335,115,100,26));
        	jtfKilos.setDocument(new LimitadorPrecio(jtfKilos));
        	jtfKilos.setText("");
        	jtfKilos.setFont(Utils.FuenteCampos());
        }
        return jtfKilos;
    }
    
    public JTextField getJTFDescuento() {
        if (jtfDescuento == null) {
        	jtfDescuento = new JTextField();
        	jtfDescuento.setBounds(new Rectangle(635,115,50,26));
        	jtfDescuento.setDocument(new LimitadorNroMax(jtfDescuento,2));
        	jtfDescuento.setFont(Utils.FuenteCampos());
        }
        return jtfDescuento;
    }

	public JComboBox getJCBFechaVto() {
		if (jDFechaVto == null) {
			jDFechaVto = new JComboBox();
			jDFechaVto.setBounds(new java.awt.Rectangle(785,115,180,26));
			jDFechaVto.setBackground(new Color(255,255,255));
			jDFechaVto.setForeground(java.awt.Color.black);
			jDFechaVto.setEditable(true);
			jDFechaVto.setFont(Utils.FuenteCampos());
		}
		return jDFechaVto;
	}
	
    public JTextField getJTFNombreC() {
        if (jtfNombreC == null) {
            jtfNombreC = new JTextField();
            jtfNombreC.setBounds(new Rectangle(500,30,280,26));
            jtfNombreC.setDisabledTextColor(Utils.colorTextoDisabled);
            jtfNombreC.setEnabled(false);
            jtfNombreC.setFont(Utils.FuenteCampos());
        }
        return jtfNombreC;
    }
    	
	public JTextField getJtCuit() {
		if (jtCuit == null) {
			jtCuit = new JTextField();
			jtCuit.setBounds(new java.awt.Rectangle(940,30,280,26));
			jtCuit.setDisabledTextColor(Utils.colorTextoDisabled);
			jtCuit.setEnabled(false);
			jtCuit.setFont(Utils.FuenteCampos());
		}
		return jtCuit;
	}
	
	public JTextField getJTFRemitoNro() {
        if (jtfRemitoNro == null) {
        	jtfRemitoNro = new JTextField();
        	jtfRemitoNro.setBounds(new Rectangle(940,100,280,26));
        	jtfRemitoNro.setDisabledTextColor(Utils.colorTextoDisabled);
        	jtfRemitoNro.setEnabled(false);
        	jtfRemitoNro.setFont(Utils.FuenteCampos());
        }
        return jtfRemitoNro;
    }
	
	public JTextField getJTFIngrBrutos() {
        if (jtfIngrBrutos == null) {
        	jtfIngrBrutos = new JTextField();
        	jtfIngrBrutos.setBounds(new Rectangle(940,65,280,26));
        	jtfIngrBrutos.setEnabled(false);
        	jtfIngrBrutos.setDisabledTextColor(Utils.colorTextoDisabled);
        	jtfIngrBrutos.setFont(Utils.FuenteCampos());
        }
        return jtfIngrBrutos;
    }
	
	public JTextField getJCTipoIva() {
        if (jcbTipoIva == null) {
        	jcbTipoIva = new JTextField();
        	jcbTipoIva.setBounds(new Rectangle(500,65,280,26));
        	jcbTipoIva.setEnabled(false);
        	jcbTipoIva.setDisabledTextColor(Utils.colorTextoDisabled);
        	jcbTipoIva.setFont(Utils.FuenteCampos());
	   }
        return jcbTipoIva;
    }
	
	public JComboBox getJCCondVta() {
        if (jcbCondVta == null) {
        	jcbCondVta = new JComboBox();
        	jcbCondVta.setBounds(new Rectangle(500,100,280,26));
        	jcbCondVta.addItem("CONTADO");
        	jcbCondVta.addItem("CTA. CTE.");
        	jcbCondVta.setBackground(new Color(255,255,255));
        	jcbCondVta.setForeground(java.awt.Color.black);
        	jcbCondVta.setFont(Utils.FuenteCampos());
	   }
        return jcbCondVta;
    }
	
	private JPanel getJPDatosItems() {
		if (jpDatosItems == null) {
			jpDatosItems = new TransparentPanel();
			jpDatosItems.setLayout(null);
			jpDatosItems.setBounds(new Rectangle(8,150,1275,285));//229
			jpDatosItems.setBorder(Utils.crearTituloYBorde("LISTADO DE PRODUCTOS COMPRADOS"));
			jlITotal = new JLabel();
			jlITotal.setBounds(new java.awt.Rectangle(850,240,200,26));
			jlITotal.setHorizontalAlignment(SwingConstants.RIGHT);
			jlITotal.setText("IMPORTE TOTAL:");
			jlITotal.setForeground(Utils.colorTexto);
			jlITotal.setFont(Utils.FuenteBasica());
			jlImporteIva = new JLabel();
			jlImporteIva.setBounds(new java.awt.Rectangle(850,205,200,26));
			jlImporteIva.setHorizontalAlignment(SwingConstants.RIGHT);
			jpDatosItems.add(jlITotal, null);
			jpDatosItems.add(getJTFITotal(), null);
			jpDatosItems.add(getJSPDatosI(), null);
			if(tipoF.compareTo("A")==0){
				jlImporteIva.setText("% IVA:");
				jlImporteIva.setForeground(Utils.colorTexto);
				jlImporteIva.setFont(Utils.FuenteBasica());
				jpDatosItems.add(jlImporteIva, null);
				jpDatosItems.add(getJTFImporteIva(), null);
			}
			jpDatosItems.add(getJBEliminarProd(), null);
			jpDatosItems.add(getJBConfirmaFact(), null);
		}
		return jpDatosItems;
	}
	
	private JScrollPane getJSPDatosI() {
		if (jspDatosInsc == null) {
			jspDatosInsc = new JScrollPane();
			jspDatosInsc.setBounds(new Rectangle(10,30,1250,170));
			jspDatosInsc.setViewportView(getJTDatosI());
		}
		return jspDatosInsc;
	}
	
	public JTable getJTDatosI() {
		if (tabla == null) {
			modTabla = new ModeloTabla(titulos, datos);
			tabla = new JTable(modTabla);
			tabla.setFont(Utils.FuenteTablasSimple());
			JTableHeader titTabla = tabla.getTableHeader();
			titTabla.setFont(Utils.FuenteTablasSimple());
			TableColumn columna0 = tabla.getColumn("C�digo");
			columna0.setPreferredWidth(140);
			columna0.setMaxWidth(140); 
			columna0.setCellRenderer(Utils.alinearDerecha());
            TableColumn columna1 = tabla.getColumn("Cant.");
			columna1.setPreferredWidth(100);
            columna1.setMaxWidth(100); 
            columna1.setCellRenderer(Utils.alinearDerecha());
            TableColumn columna2 = tabla.getColumn("Kg.");
            columna2.setPreferredWidth(100); 
            columna2.setMaxWidth(100);
            columna2.setCellRenderer(Utils.alinearDerecha());
            TableColumn columna3 = tabla.getColumn("Precio Unit.");
            columna3.setPreferredWidth(80);
            columna3.setCellRenderer(Utils.alinearDerecha());
            TableColumn columna4 = tabla.getColumn("Descuento %");
            columna4.setPreferredWidth(120);
            columna4.setMaxWidth(120);
            columna4.setCellRenderer(Utils.alinearDerecha());
            TableColumn columna5 = tabla.getColumn("Precio Total");
            columna5.setPreferredWidth(80);
            columna5.setCellRenderer(Utils.alinearDerecha());
		}
		return tabla;
	}
	 
	public void actualizarTabla(){
		jpPpal.remove(getJPDatosItems());
		jpDatosItems = null;
		tabla = null;
		modTabla = null;
		jspDatosInsc = null;
		jpPpal.add(getJPDatosItems(), null);
	}
	 
	public void actualizarNroFactura(){
		jlNroFactura.setText("NRO FACTURA: "+Utils.nroFact(nroFactura));
		jpDatosFactura.add(jlNroFactura, null);
		
	}
	
	
	public void setListSelectionListener(ListSelectionListener lis) {
		tabla.getSelectionModel().addListSelectionListener(lis);
	}
	
	public void setActionListeners(ActionListener lis) {
		jbAgregarProd.addActionListener(lis);
		jbBuscarC.addActionListener(lis);
		jbEliminarProd.addActionListener(lis);
		jbConfirmarFact.addActionListener(lis);
	}
	
	public void setActionListeners2(ActionListener lis) {
		jcbCodigo.addActionListener(lis);
	}
	
	public void setKeyListeners2(KeyListener lis) {
		jtfBusqueda.addKeyListener(lis);
	}
	
}

