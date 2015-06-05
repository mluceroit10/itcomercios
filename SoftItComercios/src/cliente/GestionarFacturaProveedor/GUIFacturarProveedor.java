package cliente.GestionarFacturaProveedor;

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

public class GUIFacturarProveedor extends JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel jpPpal = null;				private JPanel jpDatosProd = null;
    private JPanel jpDatosFactura = null;		private JPanel jpDatosItems=null;
   	private JButton jbAgregarProd = null;		private JButton jbBuscarC=null;
    private JButton jbEliminarProd=null;		private JButton jbConfirmarFact=null;
    private JLabel jlSelecProd = null;			private JLabel jlFechaFactura=null;
    private JLabel jlCodigo = null;	     		private JLabel jlImporte = null;
    private JLabel jlBusqueda = null;			private JLabel jlNroFactura = null;
    private JLabel jlNombreC=null;				private JLabel jlITotal = null;
    private JLabel jlCantidad = null;			private JLabel jlKilos = null;
    private JLabel jlImpAux = null;
    private JTextField jtfBusqueda = null;		private JTextField jtNroFactura = null;
    private JTextField jtfCodigo = null;     	private JTextField jtfImporte = null;
    private JTextField jtfCantidad = null;   	private JTextField jtfITotal = null;
    private JTextField jtfNombreC=null;			private JTextField jtfImpAux = null;
    private JComboBox jcbCodigo = null;
    private JDateChooser jDataCFecha = null;
    private JScrollPane jspDatosInsc=null;
    public final String[] titulos ={"Código","Cant.","Kg.","Producto","Precio Unit.","Descuento %","Precio Total"};
    public Object[][] datos;
    public JTable tabla;					private ModeloTabla modTabla = null;
    public Vector codProd= new Vector();
    private JTextField jtfKilos;
	private JTextField jtfDescuento;
	private JLabel jlDescuento;
	private JButton jbNuevoProd=null;
	private InputMap map = new InputMap();
	private JLabel jlVto;					 	private JDateChooser jDFechaVto = null;
    
    public GUIFacturarProveedor(JFrame guiPadre) {
    	super(guiPadre);
    	this.setSize(new java.awt.Dimension(1300,640));
        this.setLocationRelativeTo(guiPadre);
        this.setResizable(false);
        this.setTitle("Facturacion Proveedor");
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
            jpDatosProd.add(getJDFechaVto(), null);
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
        	jpDatosFactura.setBorder(Utils.crearTituloYBorde("DATOS FACTURA"));
        	jpDatosFactura.setBounds(new java.awt.Rectangle(8,15,1275,100));
        	jlNombreC = new JLabel();
            jlNombreC.setBounds(new Rectangle(340,30,150,26));
            jlNombreC.setHorizontalAlignment(SwingConstants.RIGHT);
            jlNombreC.setText("NOMBRE:");
            jlNombreC.setForeground(Utils.colorTexto);
            jlNombreC.setFont(Utils.FuenteBasica());
            jlFechaFactura = new JLabel();
            jlFechaFactura.setBounds(new Rectangle(770,65,120,26));
            jlFechaFactura.setHorizontalAlignment(SwingConstants.RIGHT);
            jlFechaFactura.setText("FECHA:");
            jlFechaFactura.setForeground(Utils.colorTexto);
            jlFechaFactura.setFont(Utils.FuenteBasica());
            jlNroFactura = new JLabel();
            jlNroFactura.setText("NRO FACTURA:");
            jlNroFactura.setBounds(new Rectangle(340,65,150,26));
            jlNroFactura.setHorizontalAlignment(SwingConstants.RIGHT);
            jlNroFactura.setForeground(Utils.colorTexto);
            jlNroFactura.setFont(Utils.FuenteBasica());
            jpDatosFactura.add(jlNombreC, null);
            jpDatosFactura.add(jlFechaFactura, null);
            jpDatosFactura.add(jlNroFactura, null);
            jpDatosFactura.add(getJBBuscarC(), null);
            jpDatosFactura.add(getJTFNombreC(), null);
            jpDatosFactura.add(getJtNroFactura(), null);
            jpDatosFactura.add(getJDateChooserFecha(),null);
        }
        return jpDatosFactura;
    }
    
    public JDateChooser getJDateChooserFecha() {
		if (jDataCFecha == null) {
			jDataCFecha = new JDateChooser("dd - MMMMM - yyyy",false);
			jDataCFecha.setBounds(new java.awt.Rectangle(900,62,190,26));
		}
		return jDataCFecha;
	}
    
 
    
    public JButton getJBBuscarC() {
        if (jbBuscarC == null) {
            jbBuscarC = new GlossyButton("SELECCIONE PROVEEDOR",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
            jbBuscarC.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/find.png")));
            jbBuscarC.setName("BuscarP");
            jbBuscarC.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jbBuscarC.setBounds(new java.awt.Rectangle(25,30,300,26));
            jbBuscarC.setInputMap(0, map);
            jbBuscarC.setFont(Utils.FuenteBotonesChicos());
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
        }
        return jbAgregarProd;
    }
    
    public JButton getJBEliminarProd() {
        if (jbEliminarProd == null) {
        	jbEliminarProd = new GlossyButton("ELIMINAR PRODUCTO",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
        	jbEliminarProd.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/delete.png")));
        	jbEliminarProd.setName("EliminarP");
        	jbEliminarProd.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        	jbEliminarProd.setBounds(new java.awt.Rectangle(30,275,260,26));
        	jbEliminarProd.setEnabled(false);
        	jbEliminarProd.setInputMap(0, map);
        	jbEliminarProd.setFont(Utils.FuenteBotonesChicos());
        }
        return jbEliminarProd;
    }
    
    public JButton getJBConfirmaFact() {
        if (jbConfirmarFact == null) {
        	jbConfirmarFact = new GlossyButton("CONFIRMAR FACTURA",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
        	jbConfirmarFact.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/check.png")));
        	jbConfirmarFact.setName("ConfirmarFact");
        	jbConfirmarFact.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        	jbConfirmarFact.setBounds(new java.awt.Rectangle(500,270,300,40));
        	jbConfirmarFact.setEnabled(false);
        	jbConfirmarFact.setInputMap(0, map);
        	jbConfirmarFact.setFont(Utils.FuenteBotonesGrandes());
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
    
    public JButton getJBNuevoProducto() {
        if (jbNuevoProd == null) {
        	jbNuevoProd = new GlossyButton("",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
        	jbNuevoProd.setBounds(new Rectangle(820,30,260,26));
        	jbNuevoProd.setText("NUEVO PRODUCTO");
        	jbNuevoProd.setName("NuevoProd");
        	jbNuevoProd.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        	jbNuevoProd.setInputMap(0, map);
        	jbNuevoProd.setFont(Utils.FuenteBotonesChicos());
	   }
        return jbNuevoProd;
    }
    
    public void mostrarBotonNuevoProducto(){
    	jpDatosProd.remove(getJBNuevoProducto());
    	jpDatosProd.add(getJBNuevoProducto(), null);
		this.repaint();
    }
    
    public void ocultarBotonNuevoProducto(){
    	jpDatosProd.remove(getJBNuevoProducto());
	 	this.repaint();
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
    }
    
    public void ocultarCombo(){
    	jpDatosProd.remove(getJCBCodigo());
	 	this.repaint();
    }
    
    public JTextField getJTFImporte() {
        if (jtfImporte == null) {
        	jtfImporte = new JTextField();
        	jtfImporte.setBounds(new Rectangle(1060,65,200,26));
        	jtfImporte.setDisabledTextColor(Utils.colorTextoDisabled);
        	jtfImporte.setDocument(new LimitadorPrecio(jtfImporte));
        	jtfImporte.setFont(Utils.FuenteCampos());
        	jtfImporte.setHorizontalAlignment(SwingConstants.RIGHT);
        }
        return jtfImporte;
    }
    
    public JTextField getJTFImpAuxiliar() {
        if (jtfImpAux == null) {
        	jtfImpAux = new JTextField();
        	jtfImpAux.setBounds(new Rectangle(1060,250,200,26));
        	jtfImpAux.setDisabledTextColor(Utils.colorTextoDisabled);
        	jtfImpAux.setDocument(new LimitadorPrecio(jtfImpAux));
        	jtfImpAux.setEnabled(false);
        	jtfImpAux.setFont(Utils.FuenteCampos());
        	jtfImpAux.setHorizontalAlignment(SwingConstants.RIGHT);
        }
        return jtfImpAux;
    }
    
    public JTextField getJTFITotal() {
        if (jtfITotal == null) {
        	jtfITotal = new JTextField();
        	jtfITotal.setBounds(new Rectangle(1060,280,200,26));
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
    
	public JDateChooser getJDFechaVto() {
		if (jDFechaVto == null) {
			jDFechaVto = new JDateChooser("dd/MM/yyyy",false);
			jDFechaVto.setBounds(new java.awt.Rectangle(785,115,180,26));
		}
		return jDFechaVto;
	}
	
    public JTextField getJTFNombreC() {
        if (jtfNombreC == null) {
            jtfNombreC = new JTextField();
            jtfNombreC.setBounds(new Rectangle(500,30,300,26));
            jtfNombreC.setDisabledTextColor(Utils.colorTextoDisabled);
            jtfNombreC.setEnabled(false);
            jtfNombreC.setFont(Utils.FuenteCampos());
        }
        return jtfNombreC;
    }
    	
	public JTextField getJtNroFactura() {
		if (jtNroFactura == null) {
			jtNroFactura = new JTextField();
			jtNroFactura.setBounds(new java.awt.Rectangle(500,65,300,26));
			jtNroFactura.setDocument(new LimitadorNroMax(jtNroFactura,12));
			jtNroFactura.setFont(Utils.FuenteCampos());
		}
		return jtNroFactura;
	}
	
	private JPanel getJPDatosItems() {
		if (jpDatosItems == null) {
			jpDatosItems = new TransparentPanel();
			jpDatosItems.setLayout(null);
			jpDatosItems.setBounds(new Rectangle(8,115,1275,320));
			jpDatosItems.setBorder(Utils.crearTituloYBorde("LISTADO DE PRODUCTOS COMPRADOS"));
			jlITotal = new JLabel();
			jlITotal.setBounds(new java.awt.Rectangle(870,280,180,26));
			jlITotal.setHorizontalAlignment(SwingConstants.RIGHT);
			jlITotal.setText("IMPORTE TOTAL:");
			jlITotal.setForeground(Utils.colorTexto);
			jlITotal.setFont(Utils.FuenteBasica());
			jlImpAux = new JLabel();
			jlImpAux.setBounds(new java.awt.Rectangle(830,250,220,26));
			jlImpAux.setHorizontalAlignment(SwingConstants.RIGHT);
			jlImpAux.setText("IMPORTE ADICIONAL:");
			jlImpAux.setForeground(Utils.colorTexto);
			jlImpAux.setFont(Utils.FuenteBasica());
			jpDatosItems.add(jlITotal, null);
			jpDatosItems.add(jlImpAux, null);
			jpDatosItems.add(getJTFITotal(), null);
			jpDatosItems.add(getJSPDatosI(), null);
			jpDatosItems.add(getJTFImpAuxiliar(), null);
			jpDatosItems.add(getJBEliminarProd(), null);
			jpDatosItems.add(getJBConfirmaFact(), null);
		}
		return jpDatosItems;
	}
	
	
	private JScrollPane getJSPDatosI() {
		if (jspDatosInsc == null) {
			jspDatosInsc = new JScrollPane();
			jspDatosInsc.setBounds(new Rectangle(10,30,1250,210));
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
			TableColumn columna0 = tabla.getColumn("Código");
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
	
	public void setActionListeners3(ActionListener lis) {
		jbNuevoProd.addActionListener(lis);
		jcbCodigo.removeActionListener(lis);
	}
		
	public void setKeyListeners2(KeyListener lis) {
		jtfBusqueda.addKeyListener(lis);
	}
	   
}

