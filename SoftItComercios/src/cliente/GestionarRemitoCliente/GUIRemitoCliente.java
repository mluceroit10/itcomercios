package cliente.GestionarRemitoCliente;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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

public class GUIRemitoCliente extends JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel jpPpal = null;			private JPanel jpDatosProd = null;
    private JPanel jpDatosRemito = null;	private JPanel jpDatosItems=null;
   	private JButton jbAgregarProd = null;  	private JButton jbBuscarC=null;
    private JButton jbEliminarProd=null;	private JButton jbConfirmarRemito=null;
    private JLabel jlSelecProd = null;		private JLabel jlFechaRemito=null;
    private JLabel jlCodigo = null;	     	private JLabel jlImporte = null;
    private JLabel jlBusqueda = null;		private JLabel jlNroRemito = null;
    private JLabel jlNombreC=null;			private JLabel jlCuit = null;
    private JLabel jlCantidad = null;		private JLabel jlITotal = null;
    private JLabel jlKilos = null;
    private JTextField jtfBusqueda = null;
    private JTextField jtfCodigo = null;    private JTextField jtfImporte = null;
    private JTextField jtfCantidad = null;  private JTextField jtfITotal = null;
    private JTextField jtfNombreC=null;		private JTextField jtCuit = null;
    private JTextField jtfKilos;
    private JComboBox jcbCodigo = null;
    private JDateChooser jDataCFecha = null;
    private JScrollPane jspDatosInsc=null;
    public final String[] titulos ={"Código","Cant.","Kg.","Producto","Precio Unit.","Descuento %","Precio Total"};
    public Object[][] datos;
    public JTable tabla;					private ModeloTabla modTabla = null;
    public Long nroRemito;					private JCheckBox jCheckImprimir;
    public Vector codProd= new Vector();
	private JTextField jtfDescuento;
	private JLabel jlDescuento;
	private InputMap map = new InputMap();
	private JLabel jlVto;					 	private JComboBox jDFechaVto = null;
	
    public GUIRemitoCliente(JFrame guiPadre) {
    	super(guiPadre);
    	this.setSize(new java.awt.Dimension(740,540));
        this.setLocationRelativeTo(guiPadre);
        this.setResizable(false);
        this.setTitle("Remitos Cliente");
        this.setContentPane(getJPPpal());
        this.setModal(true);
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "pressed");
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "released");
    }

    public JPanel getJPPpal() {
        if (jpPpal == null) {
            jpPpal= new JPanel_Whit_Image("/cliente/Imagenes/Imagenes/background.jpg");
            jpPpal.setLayout(null);
            jpPpal.add(getJPDatosProducto(), null);
            jpPpal.add(getJPDatosRemito(), null);
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
        	jpDatosProd.setBounds(new java.awt.Rectangle(8,365,716,140));
        	jpDatosProd.setBorder(javax.swing.BorderFactory.createTitledBorder(Utils.b, "INGRESO DE PRODUCTOS",
                    javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                    javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 14), Utils.colorTexto));
        	jlBusqueda = new JLabel();
            jlBusqueda.setBounds(new Rectangle(10,28,120,20));
            jlBusqueda.setText("BUSCAR PRODUCTO:");
            jlBusqueda.setHorizontalAlignment(SwingConstants.RIGHT);
            jlBusqueda.setForeground(Utils.colorTexto);
        	jlSelecProd = new JLabel();
        	jlSelecProd.setBounds(new Rectangle(225,28,150,20));
        	jlSelecProd.setText("SELECCIONE PRODUCTO");
        	jlSelecProd.setForeground(Utils.colorTexto);
        	jlCodigo = new JLabel();
            jlCodigo.setBounds(new Rectangle(10,58,120,22));
            jlCodigo.setText("PRODUCTO SELEC.:");
            jlCodigo.setHorizontalAlignment(SwingConstants.RIGHT);
            jlCodigo.setForeground(Utils.colorTexto);
            jlImporte = new JLabel();
            jlImporte.setBounds(new java.awt.Rectangle(550,58,70,20));
            jlImporte.setText("IMPORTE:");
            jlImporte.setForeground(Utils.colorTexto);
            JLabel jlAgregar = new JLabel();
            jlAgregar.setBounds(new java.awt.Rectangle(15,82,685,50));
            jlAgregar.setBorder(javax.swing.BorderFactory.createTitledBorder(Utils.b, "INGRESE",
                    javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                    javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), Utils.colorTexto));
            jlCantidad = new JLabel();
            jlCantidad.setBounds(new java.awt.Rectangle(30,100,120,20));
            jlCantidad.setText("CANTIDAD:");
            jlCantidad.setForeground(Utils.colorTexto);
            jlKilos = new JLabel();
            jlKilos.setBounds(new Rectangle(160,100,90,20));
            jlKilos.setText("KG.:");
            jlKilos.setForeground(Utils.colorTexto);
            jlDescuento = new JLabel();
            jlDescuento.setBounds(new Rectangle(250,100,150,20));
            jlDescuento.setText("DESCUENTO(%):");
            jlDescuento.setForeground(Utils.colorTexto);
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
		    jlVto.setBounds(new Rectangle(380,100,90,20));
		    jlVto.setText("VTO.:");
		    jlVto.setForeground(Utils.colorTexto);
		}
		return jlVto;
	}
    
    private JPanel getJPDatosRemito() {
        if (jpDatosRemito == null) {
        	
        	jpDatosRemito = new TransparentPanel();
        	jpDatosRemito.setLayout(null);
        	jpDatosRemito.setBorder(javax.swing.BorderFactory.createTitledBorder(Utils.b, "DATOS REMITO",
                    javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                    javax.swing.border.TitledBorder.DEFAULT_POSITION,
                    new java.awt.Font("Dialog", java.awt.Font.BOLD, 14), Utils.colorTexto));
        	jpDatosRemito.setBounds(new java.awt.Rectangle(8,15,716,95));
        	jlNombreC = new JLabel();
            jlNombreC.setBounds(new Rectangle(200,30,60,22));
            jlNombreC.setHorizontalAlignment(SwingConstants.RIGHT);
            jlNombreC.setText("NOMBRE:");
            jlNombreC.setForeground(Utils.colorTexto);
            jlFechaRemito = new JLabel();
            jlFechaRemito.setBounds(new Rectangle(200,65,60,22));
            jlFechaRemito.setHorizontalAlignment(SwingConstants.RIGHT);
            jlFechaRemito.setText("FECHA:");
            jlFechaRemito.setForeground(Utils.colorTexto);
            jlCuit = new JLabel();
        	jlCuit.setBounds(new java.awt.Rectangle(490,30,70,22));
        	jlCuit.setText("CUIT:");
        	jlCuit.setForeground(Utils.colorTexto);
            jlNroRemito = new JLabel();
            jlNroRemito.setBounds(new Rectangle(490,65,190,22));
            jlNroRemito.setForeground(Utils.colorTexto);
            jpDatosRemito.add(jlNombreC, null);
            jpDatosRemito.add(jlCuit, null);
            jpDatosRemito.add(jlFechaRemito, null);
        //    jpDatosRemito.add(getJBBuscarC(), null);
            jpDatosRemito.add(getJTFNombreC(), null);
            jpDatosRemito.add(getJtCuit(), null);
            jpDatosRemito.add(getJDateChooserFecha(),null);
            jpDatosRemito.add(getJCheckImprimir(), null);
        }
        return jpDatosRemito;
    }
    
    public JDateChooser getJDateChooserFecha() {
		if (jDataCFecha == null) {
			jDataCFecha = new JDateChooser("dd - MMMMM - yyyy",false);
			jDataCFecha.setBounds(new java.awt.Rectangle(270,65,190,22));
		}
		return jDataCFecha;
	}
    
    public JButton getJBBuscarC() {
        if (jbBuscarC == null) {
            jbBuscarC = new GlossyButton("SELECCIONE EL CLIENTE",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
            jbBuscarC.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/find.png")));
            jbBuscarC.setName("BuscarC");
            jbBuscarC.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jbBuscarC.setBounds(new java.awt.Rectangle(30,30,150,22));
            jbBuscarC.setInputMap(0, map);
        }
        return jbBuscarC;
    }

    public JButton getJBAgregarProd() {
        if (jbAgregarProd == null) {
        	jbAgregarProd = new GlossyButton("AGREGAR PRODUCTO",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
        	jbAgregarProd.setName("AgregarProd");
        	jbAgregarProd.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/add.png")));
        	jbAgregarProd.setBounds(new java.awt.Rectangle(540,98,150,22));
        	jbAgregarProd.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        	jbAgregarProd.setEnabled(false);
        	jbAgregarProd.setInputMap(0, map);
        }
        return jbAgregarProd;
    }
    
    public JButton getJBEliminarProd() {
        if (jbEliminarProd == null) {
        	jbEliminarProd = new GlossyButton("ELIMINAR PRODUCTO",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
        	jbEliminarProd.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/delete.png")));
        	jbEliminarProd.setName("EliminarP");
        	jbEliminarProd.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        	jbEliminarProd.setBounds(new java.awt.Rectangle(30,175,200,22));
        	jbEliminarProd.setEnabled(false);
        	jbEliminarProd.setInputMap(0, map);
        }
        return jbEliminarProd;
    }
    
    public JButton getJBConfirmarRemito() {
        if (jbConfirmarRemito == null) {
        	jbConfirmarRemito = new GlossyButton("CONFIRMAR REMITO",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
        	jbConfirmarRemito.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/check.png")));
        	jbConfirmarRemito.setName("ConfirmarRem");
        	jbConfirmarRemito.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        	jbConfirmarRemito.setBounds(new java.awt.Rectangle(280,200,150,30));
        	jbConfirmarRemito.setEnabled(false);
        	jbConfirmarRemito.setInputMap(0, map);
        }
        return jbConfirmarRemito;
    }
    
    public JTextField getJTFCodigo() {
        if (jtfCodigo == null) {
        	jtfCodigo = new JTextField();
        	jtfCodigo.setBounds(new Rectangle(130,58,400,20));
        	jtfCodigo.setDisabledTextColor(Utils.colorTextoDisabled);
           	jtfCodigo.setEnabled(false);
        }
        return jtfCodigo;
    }
    
    public JTextField getJTFBusqueda() {
        if (jtfBusqueda == null) {
        	jtfBusqueda = new JTextField();
        	jtfBusqueda.setBounds(new Rectangle(130,28,90,20));
        }
        return jtfBusqueda;
    }
    
    public JComboBox getJCBCodigo() {
        if (jcbCodigo == null) {
        	jcbCodigo = new JComboBox();
        	jcbCodigo.setBounds(new Rectangle(370,28,330,20));
        	jcbCodigo.removeAllItems();
        	for(int i=0;i<codProd.size();i++){
				String codPr=(String)codProd.elementAt(i);
				jcbCodigo.addItem(codPr);
			}
        	jcbCodigo.setBackground(new Color(255,255,255));
        	jcbCodigo.setForeground(java.awt.Color.black);
	   }
        return jcbCodigo;
    }
    
    public void mostrarCombo(){
    	jpDatosProd.remove(getJCBCodigo());
	 	getJCBCodigo();
	 	jcbCodigo = new JComboBox();
	 	jcbCodigo.setBounds(new Rectangle(370,28,330,20));
	 	jcbCodigo.setBackground(new Color(255,255,255));
    	jcbCodigo.setForeground(java.awt.Color.black);
		for(int i=0;i<codProd.size();i++){
			String codPr=(String)codProd.elementAt(i);
			jcbCodigo.addItem(codPr);
		}
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
        	jtfImporte.setBounds(new Rectangle(610,62,90,22));
        	jtfImporte.setDisabledTextColor(Utils.colorTextoDisabled);
        	jtfImporte.setEnabled(false);
        }
        return jtfImporte;
    }
    
    public JTextField getJTFITotal() {
        if (jtfITotal == null) {
        	jtfITotal = new JTextField();
        	jtfITotal.setBounds(new Rectangle(600,200,100,30));
        	jtfITotal.setDisabledTextColor(Utils.colorTextoDisabled);
        	jtfITotal.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 18));
			jtfITotal.setHorizontalAlignment(SwingConstants.RIGHT);
        	jtfITotal.setEnabled(false);
        }
        return jtfITotal;
    }
   
    public JTextField getJTFCantidad() {
        if (jtfCantidad == null) {
        	jtfCantidad = new JTextField();
        	jtfCantidad.setBounds(new Rectangle(95,100,50,20));
        	jtfCantidad.setDocument(new LimitadorNroMax(jtfCantidad,6));
        	jtfCantidad.setText("1");
        }
        return jtfCantidad;
    }
    
    public JTextField getJTFKilos() {
        if (jtfKilos == null) {
        	jtfKilos = new JTextField();
        	jtfKilos.setBounds(new Rectangle(185,100,50,20));
        	jtfKilos.setDocument(new LimitadorPrecio(jtfKilos));
        	jtfKilos.setText("");
        }
        return jtfKilos;
    }
    
	public JTextField getJTFDescuento() {
        if (jtfDescuento == null) {
        	jtfDescuento = new JTextField();
        	jtfDescuento.setBounds(new Rectangle(345,100,25,20));
        	jtfDescuento.setDocument(new LimitadorNroMax(jtfDescuento,2));
          }
        return jtfDescuento;
    }
	
	public JComboBox getJCBFechaVto() {
		if (jDFechaVto == null) {
			jDFechaVto = new JComboBox();
			jDFechaVto.setBounds(new java.awt.Rectangle(415,100,110,20));
			jDFechaVto.setBackground(new Color(255,255,255));
			jDFechaVto.setForeground(java.awt.Color.black);
			jDFechaVto.setEditable(true);
		}
		return jDFechaVto;
	}
	
    public JTextField getJTFNombreC() {
        if (jtfNombreC == null) {
            jtfNombreC = new JTextField();
            jtfNombreC.setBounds(new Rectangle(270,30,190,22));
            jtfNombreC.setDisabledTextColor(Utils.colorTextoDisabled);
            jtfNombreC.setEnabled(false);
        }
        return jtfNombreC;
    }
    	
	public JTextField getJtCuit() {
		if (jtCuit == null) {
			jtCuit = new JTextField();
			jtCuit.setBounds(new java.awt.Rectangle(530,30,150,22));
			jtCuit.setDisabledTextColor(Utils.colorTextoDisabled);
			jtCuit.setEnabled(false);
		}
		return jtCuit;
	}
	
	private JPanel getJPDatosItems() {
		if (jpDatosItems == null) {
			jpDatosItems = new TransparentPanel();
			jpDatosItems.setLayout(null);
			jpDatosItems.setBounds(new Rectangle(8,115,716,245));
			jpDatosItems.setBorder(javax.swing.BorderFactory.createTitledBorder(Utils.b, "LISTADO DE PRODUCTOS COMPRADOS",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 14), Utils.colorTexto));
			jlITotal = new JLabel();
			jlITotal.setBounds(new java.awt.Rectangle(490,205,100,22));
			jlITotal.setHorizontalAlignment(SwingConstants.RIGHT);
			jlITotal.setText("IMPORTE TOTAL:");
			jlITotal.setForeground(Utils.colorTexto);
			jpDatosItems.add(jlITotal, null);
			jpDatosItems.add(getJTFITotal(), null);
			jpDatosItems.add(getJSPDatosI(), null);
			jpDatosItems.add(getJBEliminarProd(), null);
			jpDatosItems.add(getJBConfirmarRemito(), null);
		}
		return jpDatosItems;
	}
	
	private JScrollPane getJSPDatosI() {
		if (jspDatosInsc == null) {
			jspDatosInsc = new JScrollPane();
			jspDatosInsc.setBounds(new Rectangle(10,20,690,150));
			jspDatosInsc.setViewportView(getJTDatosI());
		}
		return jspDatosInsc;
	}
	
	public JTable getJTDatosI() {
		if (tabla == null) {
			modTabla = new ModeloTabla(titulos, datos);
			tabla = new JTable(modTabla);
			TableColumn columna0 = tabla.getColumn("Código");
			columna0.setPreferredWidth(80);
			columna0.setCellRenderer(Utils.alinearDerecha());
            TableColumn columna1 = tabla.getColumn("Cant.");
			columna1.setPreferredWidth(60);
            columna1.setMaxWidth(60); 
            columna1.setCellRenderer(Utils.alinearDerecha());
            TableColumn columna2 = tabla.getColumn("Kg.");
            columna2.setPreferredWidth(60); 
            columna2.setMaxWidth(60);
            columna2.setCellRenderer(Utils.alinearDerecha());
            TableColumn columna3 = tabla.getColumn("Precio Unit.");
            columna3.setPreferredWidth(80);
            columna3.setCellRenderer(Utils.alinearDerecha());
            TableColumn columna4 = tabla.getColumn("Descuento %");
            columna4.setPreferredWidth(90);
            columna4.setMaxWidth(90);
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
		
	public void actualizarNroRemito(){
		jlNroRemito.setText("NRO REMITO: "+Utils.nroFact(nroRemito));
		jpDatosRemito.add(jlNroRemito, null);
	}
	

	public JCheckBox getJCheckImprimir() {
		if (jCheckImprimir == null) {
			jCheckImprimir = new JCheckBox();
			jCheckImprimir.setBounds(new Rectangle(30,65,170,17));
			jCheckImprimir.setText("Imprimir Comprobante");
			jCheckImprimir.setName("Imprimir");
			jCheckImprimir.setForeground(Utils.colorTexto);
			jCheckImprimir.setOpaque(false);
		}
		return jCheckImprimir;
	}
	
	public void setListSelectionListener(ListSelectionListener lis) {
		tabla.getSelectionModel().addListSelectionListener(lis);
	}
	
	public void setActionListeners(ActionListener lis) {
		jbAgregarProd.addActionListener(lis);
	//	jbBuscarC.addActionListener(lis);
		jbEliminarProd.addActionListener(lis);
		jbConfirmarRemito.addActionListener(lis);
	}
	
	public void setActionListeners2(ActionListener lis) {
		jcbCodigo.addActionListener(lis);
	}
	
	public void setKeyListeners2(KeyListener lis) {
		jtfBusqueda.addKeyListener(lis);
	}
}