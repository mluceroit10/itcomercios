package cliente.GestionarProducto;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import persistencia.domain.Producto;
import cliente.LimitadorNroGuion;
import cliente.LimitadorNroMax;
import cliente.LimitadorPrecio;
import cliente.LimitadorPrecioGuion;
import cliente.Imagenes.Botones.ButtonType;
import cliente.Imagenes.Botones.GlossyButton;
import cliente.Imagenes.util.Theme;
import cliente.Principal.GUIPrincipal;
import cliente.Utils.JPanel_Whit_Image;
import cliente.Utils.TransparentPanel;

import common.Utils;

public class GUIAltaModProducto extends JDialog {
	private static final long serialVersionUID = 1L;
    private JPanel jpPpal = null;		    		private JPanel jpDatos = null; 
    private JButton jbAceptar = null;	    		private JButton jbCancelar = null;
    private JButton jbProveedor = null;
	private JLabel jlNombre = null; 				private JTextField jtfNombre = null;
	private JLabel jlCodigo = null;					private JTextField jtfCodigo  = null;    
	private JLabel jlStockA = null;					private JTextField jtfStockA = null;
	private JLabel jlStockM = null;					private JTextField jtfStockM = null;
	private JLabel jlStKilosA = null;				private JTextField jtfStKilosA = null;
	private JLabel jlStKilosM = null;				private JTextField jtfStKilosM = null;
	private JLabel jlKilos = null;					private JComboBox jcbKilos = null;
	private JLabel jlProveedor = null;				private JTextField jtfProveedor = null;
	private JLabel jlPrecioEntrada = null;			private JTextField jtfPrecioEntrada = null;
	private JLabel jlPrecioVentaSinIva = null;		private JTextField jtfPrecioVentaSinIva = null;
	private JLabel jlMargenGanancia=null;			private JTextField jtfMargenGanancia = null;
	private JLabel jlTipoPrecioEntrada = null;		private JComboBox  jcbTipoPrecioEntrada = null;
	private JLabel jlPrecioVentaConIva = null;		private JTextField jtfPrecioVentaConIva = null;		
	private JLabel jlVto = null;					private JComboBox jcbVto = null;
	public String[] titServicio ={"Nombre"};
    private Producto pDTO = null;
   	private boolean seleccionar=true;
   	private InputMap map = new InputMap();
	
   	
    public GUIAltaModProducto(boolean selectprov,JDialog guiPadre) {
    	super(guiPadre);
    	seleccionar=selectprov;
        this.setSize(new java.awt.Dimension(1000,560));
        this.setTitle("Nuevo Producto");
        this.setLocationRelativeTo(guiPadre);
        this.setResizable(false);
        this.setContentPane(getJPPpal());
        this.setModal(true);
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "pressed");
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "released");
    }
    
    public GUIAltaModProducto(Producto p,JDialog guiPadre) {
        super(guiPadre);
        this.pDTO = p;
        this.setSize(new java.awt.Dimension(1000,560));
        this.setTitle("Modificar Producto");
        this.setLocationRelativeTo(guiPadre);
        this.setResizable(false);
        this.setContentPane(getJPPpal());
        this.setModal(true);
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "pressed");
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "released");
    }

    private JPanel getJPPpal() {
        if (jpPpal == null) {
            jpPpal= new JPanel_Whit_Image("/cliente/Imagenes/Imagenes/background.jpg");
            jpPpal.setLayout(null);
            jpPpal.setSize(new java.awt.Dimension(1000,560));
            jpPpal.add(getJPDatos(),null);
            jpPpal.add(getJBAceptar(), null);
            jpPpal.add(getJBCancelar(), null);
            jpPpal.setBackground(Utils.colorFondo);
        }
        return jpPpal;
    }
        
    private JPanel getJPDatos() {
        if (jpDatos == null) {
        	jlCodigo = new JLabel();
            jlCodigo.setBounds(new Rectangle(10,40,240,26));
            jlCodigo.setText("CODIGO (*)");
            jlCodigo.setForeground(Utils.colorTexto);
            jlCodigo.setFont(Utils.FuenteBasica());
            jlCodigo.setHorizontalAlignment(SwingConstants.RIGHT);
            jlNombre = new JLabel("NOMBRE (*)");
            jlNombre.setForeground(Utils.colorTexto);
            jlNombre.setFont(Utils.FuenteBasica());
            jlNombre.setBounds(new Rectangle(10,75,240,26));
            jlNombre.setHorizontalAlignment(SwingConstants.RIGHT);
            jlStockA = new JLabel();
            jlStockA.setBounds(new Rectangle(10,110,240,26));
            jlStockA.setText("STOCK ACTUAL UNID. (*)");
            jlStockA.setForeground(Utils.colorTexto);
            jlStockA.setFont(Utils.FuenteBasica());
            jlStockA.setHorizontalAlignment(SwingConstants.RIGHT);
            jlStockM = new JLabel();
            jlStockM.setBounds(new Rectangle(490,110,240,26));
            jlStockM.setText("STOCK MINIMO UNID. (*)");
            jlStockM.setForeground(Utils.colorTexto);
            jlStockM.setFont(Utils.FuenteBasica());
            jlStockM.setHorizontalAlignment(SwingConstants.RIGHT);
            jlKilos = new JLabel();
            jlKilos.setBounds(new Rectangle(10,145,240,26));
            jlKilos.setText("PRECIO POR KILOS?");
            jlKilos.setForeground(Utils.colorTexto);
            jlKilos.setFont(Utils.FuenteBasica());
            jlKilos.setHorizontalAlignment(SwingConstants.RIGHT);
            jlStKilosA = new JLabel();
            jlStKilosA.setBounds(new Rectangle(10,180,240,26));
            jlStKilosA.setText("STOCK ACTUAL KILOS");
            jlStKilosA.setForeground(Utils.colorTexto);
            jlStKilosA.setFont(Utils.FuenteBasica());
            jlStKilosA.setHorizontalAlignment(SwingConstants.RIGHT);
            jlStKilosM = new JLabel();
            jlStKilosM.setBounds(new Rectangle(490,180,240,26));
            jlStKilosM.setText("STOCK MINIMO KILOS");
            jlStKilosM.setForeground(Utils.colorTexto);
            jlStKilosM.setFont(Utils.FuenteBasica());
            jlStKilosM.setHorizontalAlignment(SwingConstants.RIGHT);
            jlPrecioEntrada = new JLabel();
            jlPrecioEntrada.setBounds(new Rectangle(10,215,240,26));
            jlPrecioEntrada.setText("PRECIO ENTRADA (*)");
            jlPrecioEntrada.setForeground(Utils.colorTexto);
            jlPrecioEntrada.setFont(Utils.FuenteBasica());
            jlPrecioEntrada.setHorizontalAlignment(SwingConstants.RIGHT);
            jlTipoPrecioEntrada = new JLabel();
            jlTipoPrecioEntrada.setBounds(new java.awt.Rectangle(480,215,250,26));
            jlTipoPrecioEntrada.setText("TIPO PRECIO ENTRADA (*)");
            jlTipoPrecioEntrada.setForeground(Utils.colorTexto);
            jlTipoPrecioEntrada.setFont(Utils.FuenteBasica());
            jlTipoPrecioEntrada.setHorizontalAlignment(SwingConstants.RIGHT);
			jlMargenGanancia = new JLabel();
			jlMargenGanancia.setBounds(new Rectangle(10,250,240,26));
			jlMargenGanancia.setText("GANANCIA EN % (*)");
			jlMargenGanancia.setForeground(Utils.colorTexto);
			jlMargenGanancia.setFont(Utils.FuenteBasica());
			jlMargenGanancia.setHorizontalAlignment(SwingConstants.RIGHT);
			jlPrecioVentaSinIva = new JLabel();
			jlPrecioVentaSinIva.setBounds(new Rectangle(470,285,260,26));
			jlPrecioVentaSinIva.setText("PRECIO VENTA SIN IVA (*)");
			jlPrecioVentaSinIva.setForeground(Utils.colorTexto);
			jlPrecioVentaSinIva.setFont(Utils.FuenteBasica());
			jlPrecioVentaSinIva.setHorizontalAlignment(SwingConstants.RIGHT);
			jlPrecioVentaConIva = new JLabel();
			jlPrecioVentaConIva.setBounds(new Rectangle(470,320,260,26));
			jlPrecioVentaConIva.setText("PRECIO VENTA CON IVA(*)");
			jlPrecioVentaConIva.setForeground(Utils.colorTexto);
			jlPrecioVentaConIva.setFont(Utils.FuenteBasica());
			jlPrecioVentaConIva.setHorizontalAlignment(SwingConstants.RIGHT);
            jlProveedor = new JLabel();
            jlProveedor.setBounds(new Rectangle(10,355,240,26));
            jlProveedor.setText("PROVEEDOR (*)");
            jlProveedor.setForeground(Utils.colorTexto);
            jlProveedor.setFont(Utils.FuenteBasica());
            jlProveedor.setHorizontalAlignment(SwingConstants.RIGHT);
            jlVto = new JLabel();
            jlVto.setBounds(new Rectangle(10,390,240,26));
            jlVto.setText("VENCIMIENTO (*)");
            jlVto.setForeground(Utils.colorTexto);
            jlVto.setFont(Utils.FuenteBasica());
            jlVto.setHorizontalAlignment(SwingConstants.RIGHT);
            jpDatos = new TransparentPanel();
            jpDatos.setLayout(null);
            jpDatos.setBorder(Utils.crearTituloYBorde("DATOS DEL PRODUCTO"));
            jpDatos.setBounds(new java.awt.Rectangle(15,15,965,430));
            jpDatos.add(jlNombre, null);
            jpDatos.add(jlCodigo, null);
            jpDatos.add(jlStockA, null);
            jpDatos.add(jlStockM, null);
            jpDatos.add(jlKilos, null);
            jpDatos.add(jlStKilosA, null);
            jpDatos.add(jlStKilosM, null);
            jpDatos.add(jlPrecioEntrada, null);
            jpDatos.add(jlTipoPrecioEntrada, null);
            jpDatos.add(jlMargenGanancia, null);
            jpDatos.add(jlPrecioVentaSinIva, null);
            jpDatos.add(jlPrecioVentaConIva, null);
            jpDatos.add(jlProveedor, null);
            jpDatos.add(jlVto, null);        
            jpDatos.add(getJTFNombre(), null);
            jpDatos.add(getJTFStockAct(), null);
            jpDatos.add(getJTFStockMin(), null);
            jpDatos.add(getJTFCodigo(), null);
            jpDatos.add(getJTFStKilosAct(), null);
            jpDatos.add(getJTFStKilosMin(), null);
            jpDatos.add(getJTFPrecioEntrada(), null);
            jpDatos.add(getJCBTipoPrecioEntrada(), null);
            jpDatos.add(getJTFMargenGanancia(), null);
            jpDatos.add(getJTFPrecioVentaSinIva(), null);
            jpDatos.add(getJTFPrecioVentaConIva(), null);
            jpDatos.add(getJCBPrecioKilos(), null);
            jpDatos.add(getJTFProveedor(), null);
            jpDatos.add(getJBProveedor(), null);
            jpDatos.add(getJCBCtrlVto(), null);
            if (pDTO!=null) {
                jtfNombre.setText(pDTO.getNombre());
                jtfCodigo.setText(String.valueOf(pDTO.getCodigo()));
                jtfStockM.setText(String.valueOf(pDTO.getStockMinimo()));
                jtfStockA.setText(String.valueOf(pDTO.getStockActual()));
                
                jtfStKilosM.setText(String.valueOf(pDTO.getStockKilosMin()));
                jtfStKilosA.setText(String.valueOf(pDTO.getStockKilosAct()));
                jtfPrecioEntrada.setText(Utils.ordenarDosDecimales(pDTO.getPrecioEntrada()));
                jtfMargenGanancia.setText(String.valueOf(pDTO.getGanancia()));
                jtfPrecioVentaSinIva.setText(Utils.ordenarDosDecimales(pDTO.getPrecioVentaSinIva()));
                jtfPrecioVentaConIva.setText(Utils.ordenarDosDecimales(pDTO.getPrecioVentaConIva()));
                if(pDTO.isPrecioKilos()){
                	jcbKilos.setSelectedItem("SI");
                }else{
                	jcbKilos.setSelectedItem("NO"); 
                }
                if(pDTO.isCtrlVto()){
                	jcbVto.setSelectedItem("SI");
                }else{
                	jcbVto.setSelectedItem("NO"); 
                }
                if(pDTO.isPrecioEntCIva()){
                	jcbTipoPrecioEntrada.setSelectedItem("CON IVA");
                }else{
                	jcbTipoPrecioEntrada.setSelectedItem("SIN IVA"); 
                }
                jtfProveedor.setText(pDTO.getProveedor().getNombre());
            }
        }
        return jpDatos;
    }

    public JTextField getJTFNombre() {
        if (jtfNombre == null) {
            jtfNombre = new JTextField();
            jtfNombre.setBounds(new Rectangle(260,75,680,26));
            jtfNombre.setFont(Utils.FuenteCampos());
        }
        return jtfNombre;
    }
    
    public JTextField getJTFCodigo() {
		if (jtfCodigo==null){
			jtfCodigo=new JTextField();
			jtfCodigo.setBounds(new java.awt.Rectangle(260,40,200,26));
			jtfCodigo.setBackground(new Color(255,255,255));
			jtfCodigo.setDocument(new LimitadorNroMax(jtfCodigo,13));
			jtfCodigo.setFont(Utils.FuenteCampos());
		}
		return jtfCodigo;
	}
   

    public JTextField getJTFStockAct() {
        if (jtfStockA == null) {
        	jtfStockA = new JTextField();
        	jtfStockA.setBounds(new Rectangle(260,110,200,26));
        	jtfStockA.setDocument(new LimitadorNroGuion(jtfStockA));
        	jtfStockA.setFont(Utils.FuenteCampos());
        }
        return jtfStockA;
    }
    
    public JTextField getJTFStockMin() {
        if (jtfStockM == null) {
        	jtfStockM = new JTextField();
        	jtfStockM.setBounds(new Rectangle(740,110,200,26));
        	jtfStockM.setDocument(new LimitadorNroMax(jtfStockM));
        	jtfStockM.setFont(Utils.FuenteCampos());
        }
        return jtfStockM;
    }
    
    public JComboBox getJCBPrecioKilos() {
        if (jcbKilos == null) {
        	jcbKilos  = new JComboBox();
        	jcbKilos.setBounds(new java.awt.Rectangle(260,145,200,26));
        	jcbKilos.setBackground(new Color(255,255,255));
        	jcbKilos.setForeground(java.awt.Color.black);
        	jcbKilos.addItem("NO");
        	jcbKilos.addItem("SI");
        	jcbKilos.setFont(Utils.FuenteCampos());
        	
        }
        return jcbKilos;
    }
    
    public JComboBox getJCBCtrlVto() {
        if (jcbVto == null) {
        	jcbVto  = new JComboBox();
        	jcbVto.setBounds(new java.awt.Rectangle(260,390,100,26));
        	jcbVto.setBackground(new Color(255,255,255));
        	jcbVto.setForeground(java.awt.Color.black);
        	jcbVto.addItem("NO");
        	jcbVto.addItem("SI");
        	jcbVto.setFont(Utils.FuenteCampos());
        }
        return jcbVto;
    }
    
    public JTextField getJTFStKilosAct() {
        if (jtfStKilosA == null) {
        	jtfStKilosA = new JTextField();
        	jtfStKilosA.setBounds(new Rectangle(260,180,200,26));
        	jtfStKilosA.setDocument(new LimitadorPrecioGuion(jtfStKilosA));
        	jtfStKilosA.setFont(Utils.FuenteCampos());
        }
        return jtfStKilosA;
    }
    
    public JTextField getJTFStKilosMin() {
        if (jtfStKilosM == null) {
        	jtfStKilosM = new JTextField();
        	jtfStKilosM.setBounds(new Rectangle(740,180,200,26));
        	jtfStKilosM.setDocument(new LimitadorPrecio(jtfStKilosM));
        	jtfStKilosM.setFont(Utils.FuenteCampos());
        }
        return jtfStKilosM;
    }
    
    public JTextField getJTFPrecioEntrada() {
        if (jtfPrecioEntrada == null) {
        	jtfPrecioEntrada = new JTextField();
        	jtfPrecioEntrada.setBounds(new Rectangle(260,215,200,26));
        	jtfPrecioEntrada.setDocument(new LimitadorPrecio(jtfPrecioEntrada));
        	jtfPrecioEntrada.setFont(Utils.FuenteCampos());
        }
        return jtfPrecioEntrada;
    }
    
    public JComboBox getJCBTipoPrecioEntrada() {
        if (jcbTipoPrecioEntrada == null) {
        	jcbTipoPrecioEntrada  = new JComboBox();
        	jcbTipoPrecioEntrada.setBounds(new java.awt.Rectangle(740,215,200,26));
        	jcbTipoPrecioEntrada.setBackground(new Color(255,255,255));
        	jcbTipoPrecioEntrada.setForeground(java.awt.Color.black);
        	jcbTipoPrecioEntrada.addItem("SIN IVA");
        	jcbTipoPrecioEntrada.addItem("CON IVA");
        	jcbTipoPrecioEntrada.setFont(Utils.FuenteCampos());
        }
        return jcbTipoPrecioEntrada;
    }
    
    public JTextField getJTFMargenGanancia() {
		if (jtfMargenGanancia == null) {
			jtfMargenGanancia = new JTextField();
			jtfMargenGanancia.setBounds(new Rectangle(260,250,200,26));
			jtfMargenGanancia.setDocument(new LimitadorNroMax(jtfMargenGanancia,3));
			jtfMargenGanancia.setFont(Utils.FuenteCampos());
		}
		return jtfMargenGanancia;
	}
    
    public JTextField getJTFPrecioVentaSinIva() {
        if (jtfPrecioVentaSinIva == null) {
        	jtfPrecioVentaSinIva = new JTextField();
        	jtfPrecioVentaSinIva.setBounds(new Rectangle(740,285,200,26));
        	jtfPrecioVentaSinIva.setDocument(new LimitadorPrecio(jtfPrecioVentaSinIva));
        	jtfPrecioVentaSinIva.setFont(Utils.FuenteCampos());
        }
        return jtfPrecioVentaSinIva;
    }
    
    public JTextField getJTFPrecioVentaConIva() {
        if (jtfPrecioVentaConIva == null) {
        	jtfPrecioVentaConIva = new JTextField();
        	jtfPrecioVentaConIva.setBounds(new Rectangle(740,320,200,26));
        	jtfPrecioVentaConIva.setDocument(new LimitadorPrecio(jtfPrecioVentaConIva));
        	jtfPrecioVentaConIva.setFont(Utils.FuenteCampos());
        }
        return jtfPrecioVentaConIva;
    }
    
    
    public JTextField getJTFProveedor() {
        if (jtfProveedor == null) {
        	jtfProveedor = new JTextField();
        	jtfProveedor.setBounds(new Rectangle(260,355,500,26));
        	jtfProveedor.setDisabledTextColor(Utils.colorTextoDisabled);
        	jtfProveedor.setFont(Utils.FuenteCampos());
        	jtfProveedor.setEnabled(false);
        }
        return jtfProveedor;
    }
    
    public JButton getJBAceptar() {
        if (jbAceptar == null) {
            jbAceptar = new GlossyButton("ACEPTAR",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
            jbAceptar.setBounds(new java.awt.Rectangle(250,480,200,40));
            jbAceptar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jbAceptar.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/check.png")));
            jbAceptar.setInputMap(0, map);
            jbAceptar.setFont(Utils.FuenteBotonesGrandes());
        }
        return jbAceptar;
    }
    
    public JButton getJBCancelar() {
        if (jbCancelar == null) {
            jbCancelar = new GlossyButton("CANCELAR",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
            jbCancelar.setBounds(new java.awt.Rectangle(550,480,200,40));
            jbCancelar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jbCancelar.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/cancel.png")));
            jbCancelar.setInputMap(0, map);
            jbCancelar.setFont(Utils.FuenteBotonesGrandes());
        }
        return jbCancelar;
    }
    
    public JButton getJBProveedor() {
        if (jbProveedor == null) {
        	jbProveedor = new GlossyButton("BUSCAR",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
        	jbProveedor.setBounds(new Rectangle(790,355,150,26));
        	jbProveedor.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        	jbProveedor.setName("Buscar");
        	jbProveedor.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/find.png")));
        	jbProveedor.setEnabled(seleccionar);
        	jbProveedor.setInputMap(0, map);
        	jbProveedor.setFont(Utils.FuenteBotonesChicos());
        }
        return jbProveedor;
    }
    
    public void setKeyListener(KeyListener lis) {
		jtfPrecioEntrada.addKeyListener(lis);
		jtfMargenGanancia.addKeyListener(lis);
	}
    
    public void setActionListeners(ActionListener lis) {
        jbAceptar.addActionListener(lis);
        jbCancelar.addActionListener(lis);
        jbProveedor.addActionListener(lis);
        jcbTipoPrecioEntrada.addActionListener(lis);
    }

    public void setProveedor(String string) {
    	jtfProveedor.setText(string);
    }
}