package cliente.GestionarComercio;

import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import persistencia.domain.Comercio;
import cliente.LimitadorNroGuion;
import cliente.LimitadorNroMax;
import cliente.Imagenes.Botones.ButtonType;
import cliente.Imagenes.Botones.GlossyButton;
import cliente.Imagenes.util.Theme;
import cliente.Principal.GUIPrincipal;
import cliente.Utils.JPanel_Whit_Image;
import cliente.Utils.TransparentPanel;

import com.toedter.calendar.JDateChooser;
import common.Utils;

public class GUIAltaModComercio extends JDialog {
	private static final long serialVersionUID = 1L;
    private JPanel jpPpal = null;               private JPanel jpDatosComercio = null;  //  @jve:decl-index=0:visual-constraint="10,58"
    private JButton jbCancelar = null;          private JButton jbAceptar = null;
    private JButton jbLocalidad = null;         private JButton jbImprTarjeta = null;     
    private JLabel jlNombre = null;		    	private JLabel jlDireccion = null;	          
    private JLabel jlCuit = null;           	private JLabel jlTel = null;
    private JLabel jlInicioAct = null;			private JLabel jlIngBrutos = null;
    private JLabel jlLocalidad = null;
    private JTextField jtfNombre = null;		private JTextField jtfCuit = null;       	    
    private JTextField jtfLocalidad = null;     private JTextField jtfCalle = null;
    private JTextField jtfTel = null;			private JTextField jtfIngBrutos = null;
    private JDateChooser jDataCFecha = null;
    private JLabel jlNroFactA = null;			private JTextField jtfNroFactA = null;
    private JLabel jlNroFactB = null;			private JTextField jtfNroFactB = null;
    private JLabel jlNroRemito = null;			private JTextField jtfNroRemito = null;
    private Comercio comercio;
	private JButton jbModNroFacts;
	private JPanel jpNrosRioIV=null;			
	private InputMap map = new InputMap();
	
    public GUIAltaModComercio(JFrame guiPadre) {
        super(guiPadre);
        this.setSize(1100,510);
    	this.setLocationRelativeTo(guiPadre);
        this.setResizable(false);
        this.setContentPane(getPanelPpal());
        this.setTitle("Datos Comercio");
        this.setModal(true);
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "pressed");
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "released");
    }

    public GUIAltaModComercio(Comercio c,JFrame guiPadre) {
        super(guiPadre);
        this.comercio = c;
        this.setSize(1100,510);
    	this.setLocationRelativeTo(guiPadre);
        this.setResizable(false);
        this.setContentPane(getPanelPpal());
        this.setTitle("Datos Comercio");
        this.setModal(true);
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "pressed");
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "released");
    }

    private JPanel getPanelPpal() {
        if (jpPpal == null) {
            jpPpal = new JPanel_Whit_Image("/cliente/Imagenes/Imagenes/background.jpg");
            jpPpal.setLayout(null);
            jpPpal.setSize(1100, 510);
            jpPpal.setBorder(javax.swing.BorderFactory.createCompoundBorder(null, null));
            jpPpal.setName("Gestión Cliente");
            jpPpal.add(getCancelar(), null);
            jpPpal.add(getAceptar(), null);
            jpPpal.add(getJPDatosPers(), null);
            jpPpal.add(getJButtonImprimirTarjeta(), null);
            jpPpal.setBackground(Utils.colorFondo);
        }
        return jpPpal;
    }


    private JPanel getJPDatosPers() {
    	if (jpDatosComercio == null) {
    	    jpDatosComercio = new TransparentPanel();
    		jpDatosComercio.setLayout(null);
    		jpDatosComercio.setForeground(Utils.colorTexto);
    		jpDatosComercio.setFont(Utils.FuenteBasica());
    		jpDatosComercio.setBorder(Utils.crearTituloYBorde("DATOS COMERCIO"));
    		jpDatosComercio.setBounds(new java.awt.Rectangle(15,15,1065,380));
    		jlNombre = new JLabel();
    		jlNombre.setBounds(new java.awt.Rectangle(12,40,150,26));
    		jlNombre.setText("NOMBRE (*)");
    		jlNombre.setForeground(Utils.colorTexto);
    		jlNombre.setFont(Utils.FuenteBasica());
    		jlNombre.setHorizontalAlignment(SwingConstants.RIGHT);
    		jlCuit = new JLabel();
    		jlCuit.setBounds(new java.awt.Rectangle(12,75,150,26));
    		jlCuit.setText("CUIT");
    		jlCuit.setForeground(Utils.colorTexto);
    		jlCuit.setFont(Utils.FuenteBasica());
    		jlCuit.setHorizontalAlignment(SwingConstants.RIGHT);
    		jlIngBrutos = new JLabel();
    		jlIngBrutos.setBounds(new java.awt.Rectangle(390,75,140,26));
    		jlIngBrutos.setText("INGR. BRUTOS");
    		jlIngBrutos.setForeground(Utils.colorTexto);
    		jlIngBrutos.setFont(Utils.FuenteBasica());
    		jlIngBrutos.setHorizontalAlignment(SwingConstants.RIGHT);
    		jlInicioAct = new JLabel();
    		jlInicioAct.setBounds(new java.awt.Rectangle(660,75,180,26));
    		jlInicioAct.setText("INICIO DE ACTIV.");  
    		jlInicioAct.setForeground(Utils.colorTexto);
    		jlInicioAct.setFont(Utils.FuenteBasica());
    		jlInicioAct.setHorizontalAlignment(SwingConstants.RIGHT);
    		jlTel = new JLabel();
    		jlTel.setBounds(new java.awt.Rectangle(12,145,150,26));
    		jlTel.setText("TELEFONO");
    		jlTel.setForeground(Utils.colorTexto);
    		jlTel.setFont(Utils.FuenteBasica());
    		jlTel.setHorizontalAlignment(SwingConstants.RIGHT);
    		jlDireccion = new JLabel();
    		jlDireccion.setBounds(new java.awt.Rectangle(10,110,150,26));
    		jlDireccion.setText("DIRECCION (*)");
    		jlDireccion.setForeground(Utils.colorTexto);
    		jlDireccion.setFont(Utils.FuenteBasica());
    		jlDireccion.setHorizontalAlignment(SwingConstants.RIGHT);
    		jlLocalidad = new JLabel();
    		jlLocalidad.setBounds(new java.awt.Rectangle(570,110,150,26));
    		jlLocalidad.setText("LOCALIDAD (*)");
    		jlLocalidad.setForeground(Utils.colorTexto);
    		jlLocalidad.setFont(Utils.FuenteBasica());
    		jlLocalidad.setHorizontalAlignment(SwingConstants.RIGHT);
    		jpNrosRioIV = new TransparentPanel();
    		jpNrosRioIV.setLayout(null);
    		jpNrosRioIV.setBounds(new java.awt.Rectangle(200,220,600,140));
    		jpNrosRioIV.setForeground(Utils.colorTexto);
    		jpNrosRioIV.setFont(Utils.FuenteBasica());
    		jpNrosRioIV.setBorder(Utils.crearTituloYBorde("NUMERACION DE COMPROBANTES"));
    		jlNroFactA = new JLabel();
    		jlNroFactA.setBounds(new java.awt.Rectangle(12,35,300,26));
    		jlNroFactA.setText("PROXIMO NRO. FACTURA A (*)");
    		jlNroFactA.setForeground(Utils.colorTexto);
    		jlNroFactA.setFont(Utils.FuenteBasica());
    		jlNroFactA.setHorizontalAlignment(SwingConstants.RIGHT);
    		
    		jlNroFactB = new JLabel();
    		jlNroFactB.setBounds(new java.awt.Rectangle(12,70,300,26));
    		jlNroFactB.setText("PROXIMO NRO. FACTURA B (*)");
    		jlNroFactB.setForeground(Utils.colorTexto);
    		jlNroFactB.setFont(Utils.FuenteBasica());
    		jlNroFactB.setHorizontalAlignment(SwingConstants.RIGHT);
    		
    		jlNroRemito = new JLabel();
    		jlNroRemito.setBounds(new java.awt.Rectangle(12,105,300,26));
    		jlNroRemito.setText("PROXIMO NRO. REMITO (*)");
    		jlNroRemito.setForeground(Utils.colorTexto);
    		jlNroRemito.setFont(Utils.FuenteBasica());
    		jlNroRemito.setHorizontalAlignment(SwingConstants.RIGHT);
    		
    		jpDatosComercio.add(jlCuit, null);
    		jpDatosComercio.add(jlIngBrutos, null);
    		jpDatosComercio.add(jlInicioAct, null);
    		jpDatosComercio.add(jlNombre, null);
    		jpDatosComercio.add(jlTel, null);
    		jpDatosComercio.add(jlDireccion, null);
    		jpDatosComercio.add(jlLocalidad, null);
    		jpNrosRioIV.add(jlNroFactA, null);
    		jpNrosRioIV.add(jlNroFactB, null);
    		jpNrosRioIV.add(jlNroRemito, null);
    		jpDatosComercio.add(jpNrosRioIV, null);
    		jpDatosComercio.add(getTelefono(), null);
    		jpDatosComercio.add(getIngrBrutos(), null);
    		jpDatosComercio.add(getCuit(), null);
    		jpDatosComercio.add(getNombre(), null);
    		jpDatosComercio.add(getLocalidad(), null);
    		jpDatosComercio.add(getDireccion(), null);
    		jpDatosComercio.add(getJButtonLocalidad(), null);
    		jpDatosComercio.add(getJDateChooserFecha(), null);
    		jpNrosRioIV.add(getProximoNroFactARIV(), null);
    		jpNrosRioIV.add(getProximoNroFactBRIV(), null);
    		jpNrosRioIV.add(getProximoNroRemitoRIV(), null);
    		jpDatosComercio.add(getJButtonModificarNrosFactura(),null);
    		if (comercio!=null) {
    			jtfNombre.setText(comercio.getNombre());
    			jtfCuit.setText(comercio.getCuit());
    			jtfIngBrutos.setText(comercio.getCategoria());
    			jtfTel.setText(comercio.getTelefono());
    			jtfCalle.setText(comercio.getDireccion());
    			jtfLocalidad.setText(comercio.getLocalidad().getNombre());
    			jDataCFecha.setDate(comercio.getInicioAct());
    			jtfNroFactA.setText(String.valueOf(comercio.getNroFactA()));
    			jtfNroFactB.setText(String.valueOf(comercio.getNroFactB()));
    			jtfNroRemito.setText(String.valueOf(comercio.getNroRemito()));
    		}
    	}
    	return jpDatosComercio;
    }
    
    public JTextField getTelefono() {
        if (jtfTel == null) {
        	jtfTel = new JTextField();
        	jtfTel.setBounds(new java.awt.Rectangle(170,145,375,26));
        	jtfTel.setFont(Utils.FuenteCampos());
        }
        return jtfTel;
    }
    
    public JDateChooser getJDateChooserFecha() {
		if (jDataCFecha == null) {
			jDataCFecha = new JDateChooser("dd - MMM - yyyy",false);
			jDataCFecha.getSpinner().setFont(Utils.FuenteFechas());
			jDataCFecha.setBounds(new java.awt.Rectangle(840,75,210,26));
		}
		return jDataCFecha;
	}
    
    public JTextField getCuit() {
    	if (jtfCuit == null) {
    		jtfCuit = new JTextField();
    		jtfCuit.setBounds(new java.awt.Rectangle(170,75,200,26));
    		jtfCuit.setDocument(new LimitadorNroGuion(jtfCuit));
    		jtfCuit.setFont(Utils.FuenteCampos());
    	}
    	return jtfCuit;
    }
    
    public JTextField getIngrBrutos() {
    	if (jtfIngBrutos == null) {
    		jtfIngBrutos = new JTextField();
    		jtfIngBrutos.setBounds(new java.awt.Rectangle(535,75,120,26));
    		jtfIngBrutos.setFont(Utils.FuenteCampos());
    	}
    	return jtfIngBrutos;
    }
    
    public JTextField getNombre() {
        if (jtfNombre == null) {
            jtfNombre = new JTextField();
            jtfNombre.setBounds(new java.awt.Rectangle(170,40,880,26));
            jtfNombre.setFont(Utils.FuenteCampos());
        }
        return jtfNombre;
    }
    
    public JTextField getDireccion() {
        if (jtfCalle == null) {
        	jtfCalle = new JTextField();
        	jtfCalle.setBounds(new java.awt.Rectangle(170,110,375,26));
        	jtfCalle.setFont(Utils.FuenteCampos());
        }
        return jtfCalle;
    }
        
    public JTextField getLocalidad() {
    	if (jtfLocalidad == null) {
    		jtfLocalidad = new JTextField();
    		jtfLocalidad.setBounds(new java.awt.Rectangle(730,110,150,26));
    		jtfLocalidad.setDisabledTextColor(Utils.colorTextoDisabled);
    		jtfLocalidad.setEnabled(false);
    		jtfLocalidad.setFont(Utils.FuenteCampos());
    	}
    	return jtfLocalidad;
    }

    public JButton getJButtonLocalidad() {
        if (jbLocalidad == null) {
            jbLocalidad = new GlossyButton("BUSCAR",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
            jbLocalidad.setBounds(new java.awt.Rectangle(890,110,150,26));
            jbLocalidad.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jbLocalidad.setName("Buscar");
            jbLocalidad.setFont(Utils.FuenteBotonesChicos());
            jbLocalidad.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/find.png")));
            jbLocalidad.setInputMap(0, map);
            jbLocalidad.setMnemonic('B');
        }  
        return jbLocalidad;
    }
    
    public JTextField getProximoNroFactARIV() {
    	if (jtfNroFactA == null) {
    		jtfNroFactA = new JTextField();
    		jtfNroFactA.setBounds(new Rectangle(320,35,250,26));
    		jtfNroFactA.setDisabledTextColor(Utils.colorTextoDisabled);
    		jtfNroFactA.setEnabled(false);
    		jtfNroFactA.setDocument(new LimitadorNroMax(jtfNroFactA,12));
    		jtfNroFactA.setFont(Utils.FuenteCampos());
    	}
    	return jtfNroFactA;
    }

    public JTextField getProximoNroFactBRIV() {
    	if (jtfNroFactB == null) {
    		jtfNroFactB = new JTextField();
    		jtfNroFactB.setBounds(new Rectangle(320,70,250,26));
    		jtfNroFactB.setDisabledTextColor(Utils.colorTextoDisabled);
    		jtfNroFactB.setEnabled(false);
    		jtfNroFactB.setDocument(new LimitadorNroMax(jtfNroFactB,12));
    		jtfNroFactB.setFont(Utils.FuenteCampos());
    	}
    	return jtfNroFactB;
    }

    public JTextField getProximoNroRemitoRIV() {
    	if (jtfNroRemito == null) {
    		jtfNroRemito = new JTextField();
    		jtfNroRemito.setBounds(new Rectangle(320,105,250,26));
    		jtfNroRemito.setDisabledTextColor(Utils.colorTextoDisabled);
    		jtfNroRemito.setEnabled(false);
    		jtfNroRemito.setDocument(new LimitadorNroMax(jtfNroRemito,12));
    		jtfNroRemito.setFont(Utils.FuenteCampos());
    	}
    	return jtfNroRemito;
    }

    public JButton getCancelar() {
    	if (jbCancelar == null) {
    		jbCancelar = new GlossyButton("CANCELAR",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
    		jbCancelar.setBounds(new java.awt.Rectangle(600,430,200,40));
    		jbCancelar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
    		jbCancelar.setName("Cancelar");
    		jbCancelar.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/cancel.png")));
    		jbCancelar.setInputMap(0, map);
    		jbCancelar.setFont(Utils.FuenteBotonesGrandes());
    		jbCancelar.setMnemonic('N');
    	}
    	return jbCancelar;
    }

    public JButton getAceptar() {
        if (jbAceptar == null) {
            jbAceptar = new GlossyButton("ACEPTAR",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
            jbAceptar.setBounds(new java.awt.Rectangle(300,430,200,40));
            jbAceptar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jbAceptar.setName("Aceptar");
            jbAceptar.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/check.png")));
            jbAceptar.setInputMap(0, map);
            jbAceptar.setFont(Utils.FuenteBotonesGrandes());
            jbAceptar.setMnemonic('A');
        }
        return jbAceptar;
    }

    public JButton getJButtonModificarNrosFactura() {
        if (jbModNroFacts == null) {
        	jbModNroFacts = new GlossyButton("MODIFICAR NUMEROS",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
        	jbModNroFacts.setBounds(new java.awt.Rectangle(350,180,300,26));
        	jbModNroFacts.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        	jbModNroFacts.setName("ModNroFact");
        	jbModNroFacts.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/edit.png")));
        	jbModNroFacts.setInputMap(0, map);
        	jbModNroFacts.setFont(Utils.FuenteBotonesChicos());
        	jbModNroFacts.setMnemonic('M');
        }
        return jbModNroFacts;
    }
    
    public JButton getJButtonImprimirTarjeta() {
        if (jbImprTarjeta == null) {
        	jbImprTarjeta = new GlossyButton("IMPRIMIR",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
        	jbImprTarjeta.setBounds(new Rectangle(30,430,150,40));
        	jbImprTarjeta.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        	jbImprTarjeta.setName("Imprimir");
        	jbImprTarjeta.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/printer.png")));
        	jbImprTarjeta.setInputMap(0, map);
        	jbImprTarjeta.setFont(Utils.FuenteBotonesGrandes());
        	jbImprTarjeta.setMnemonic('P');
        }
        return jbImprTarjeta;
    }
    
    public void setActionListeners(ActionListener lis){
        this.jbCancelar.addActionListener(lis);
        this.jbAceptar.addActionListener(lis);
        this.jbLocalidad.addActionListener(lis);
        this.jbImprTarjeta.addActionListener(lis);
        this.jbModNroFacts.addActionListener(lis);
    }

    public void setLocalidad(String string) {
        jtfLocalidad.setText(string);
    }
    
}
