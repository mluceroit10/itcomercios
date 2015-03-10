package cliente.GestionarComercio;

import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

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
        this.setSize(770,405);
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
        this.setSize(770,405);
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
            jpPpal = new JPanel();
            jpPpal.setLayout(null);
            jpPpal.setSize(770, 384);
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
    		jpDatosComercio = new JPanel();
    		jpDatosComercio.setLayout(null);
    		jpDatosComercio.setBorder(javax.swing.BorderFactory.createTitledBorder(Utils.b,
    				"Datos Comercio", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
    				javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
    		jpDatosComercio.setBounds(new java.awt.Rectangle(15,15,745,300));
    		jlNombre = new JLabel();
    		jlNombre.setBounds(new java.awt.Rectangle(12,30,80,15));
    		jlNombre.setText("Nombre (*)");
    		jlNombre.setHorizontalAlignment(SwingConstants.RIGHT);
    		jlCuit = new JLabel();
    		jlCuit.setBounds(new java.awt.Rectangle(12,62,80,15));
    		jlCuit.setText("Cuit");
    		jlCuit.setHorizontalAlignment(SwingConstants.RIGHT);
    		jlIngBrutos = new JLabel();
    		jlIngBrutos.setBounds(new Rectangle(225,62,80,15));
    		jlIngBrutos.setText("Ingr. Brutos");
    		jlIngBrutos.setHorizontalAlignment(SwingConstants.RIGHT);
    		jlInicioAct = new JLabel();
    		jlInicioAct.setBounds(new java.awt.Rectangle(425,62,120,15));
    		jlInicioAct.setText("Inicio de Actividades");  
    		jlInicioAct.setHorizontalAlignment(SwingConstants.RIGHT);
    		jlTel = new JLabel();
    		jlTel.setBounds(new java.awt.Rectangle(12,126,80,15));
    		jlTel.setText("Teléfono");
    		jlTel.setHorizontalAlignment(SwingConstants.RIGHT);
    		jlDireccion = new JLabel();
    		jlDireccion.setBounds(new java.awt.Rectangle(10,94,80,15));
    		jlDireccion.setText("Dirección (*)");
    		jlDireccion.setHorizontalAlignment(SwingConstants.RIGHT);
    		jlLocalidad = new JLabel();
    		jlLocalidad.setBounds(new java.awt.Rectangle(430,94,76,15));
    		jlLocalidad.setText("Localidad (*)");
    		jlLocalidad.setHorizontalAlignment(SwingConstants.RIGHT);
    		jpNrosRioIV = new JPanel();
    		jpNrosRioIV.setLayout(null);
    		jpNrosRioIV.setBorder(javax.swing.BorderFactory.createTitledBorder(Utils.b,
    				"Numeración de Comprobantes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
    				javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
    		jpNrosRioIV.setBounds(new java.awt.Rectangle(200,190,340,102));
    		jpNrosRioIV.setBackground(Utils.colorPanel);
    		
    		jlNroFactA = new JLabel();
    		jlNroFactA.setBounds(new java.awt.Rectangle(12,25,150,16));
    		jlNroFactA.setText("Próximo Nro. Factura A (*)");
    		jlNroFactA.setHorizontalAlignment(SwingConstants.RIGHT);
    		
    		jlNroFactB = new JLabel();
    		jlNroFactB.setBounds(new java.awt.Rectangle(12,50,150,15));
    		jlNroFactB.setText("Próximo Nro. Factura B (*)");
    		jlNroFactB.setHorizontalAlignment(SwingConstants.RIGHT);
    		
    		jlNroRemito = new JLabel();
    		jlNroRemito.setBounds(new java.awt.Rectangle(12,75,150,15));
    		jlNroRemito.setText("Próximo Nro. Remito (*)");
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
    		jpDatosComercio.setBackground(Utils.colorPanel);
    	}
    	return jpDatosComercio;
    }
    
    public JTextField getTelefono() {
        if (jtfTel == null) {
        	jtfTel = new JTextField();
        	jtfTel.setBounds(new java.awt.Rectangle(100,126,315,22));
        }
        return jtfTel;
    }
    
    public JDateChooser getJDateChooserFecha() {
		if (jDataCFecha == null) {
			jDataCFecha = new JDateChooser("dd - MMMMM - yyyy",false);
			jDataCFecha.setBounds(new java.awt.Rectangle(549,62,180,22));
		}
		return jDataCFecha;
	}
    
    public JTextField getCuit() {
    	if (jtfCuit == null) {
    		jtfCuit = new JTextField();
    		jtfCuit.setBounds(new java.awt.Rectangle(100,62,120,22));
    		jtfCuit.setDocument(new LimitadorNroGuion(jtfCuit));
    	}
    	return jtfCuit;
    }
    
    public JTextField getIngrBrutos() {
    	if (jtfIngBrutos == null) {
    		jtfIngBrutos = new JTextField();
    		jtfIngBrutos.setBounds(new java.awt.Rectangle(315,62,100,22));
    	}
    	return jtfIngBrutos;
    }
    
    public JTextField getNombre() {
        if (jtfNombre == null) {
            jtfNombre = new JTextField();
            jtfNombre.setBounds(new java.awt.Rectangle(100,30,630,22));
        }
        return jtfNombre;
    }
    
    public JTextField getDireccion() {
        if (jtfCalle == null) {
        	jtfCalle = new JTextField();
        	jtfCalle.setBounds(new java.awt.Rectangle(100,94,315,22));
        }
        return jtfCalle;
    }
        
    public JTextField getLocalidad() {
    	if (jtfLocalidad == null) {
    		jtfLocalidad = new JTextField();
    		jtfLocalidad.setBounds(new java.awt.Rectangle(515,94,133,22));
    		jtfLocalidad.setDisabledTextColor(Utils.colorTextoDisabled);
    		jtfLocalidad.setEnabled(false);
    	}
    	return jtfLocalidad;
    }

    public JButton getJButtonLocalidad() {
        if (jbLocalidad == null) {
            jbLocalidad = new JButton();
            jbLocalidad.setBounds(new java.awt.Rectangle(655,94,75,22));
            jbLocalidad.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jbLocalidad.setName("Buscar");
            jbLocalidad.setText("Buscar");
            jbLocalidad.setInputMap(0, map);
        }
        return jbLocalidad;
    }
    
    public JTextField getProximoNroFactARIV() {
    	if (jtfNroFactA == null) {
    		jtfNroFactA = new JTextField();
    		jtfNroFactA.setBounds(new Rectangle(170,25,150,20));
    		jtfNroFactA.setDisabledTextColor(Utils.colorTextoDisabled);
    		jtfNroFactA.setEnabled(false);
    		jtfNroFactA.setDocument(new LimitadorNroMax(jtfNroFactA,12));
    	}
    	return jtfNroFactA;
    }

    public JTextField getProximoNroFactBRIV() {
    	if (jtfNroFactB == null) {
    		jtfNroFactB = new JTextField();
    		jtfNroFactB.setBounds(new Rectangle(170,50,150,20));
    		jtfNroFactB.setDisabledTextColor(Utils.colorTextoDisabled);
    		jtfNroFactB.setEnabled(false);
    		jtfNroFactB.setDocument(new LimitadorNroMax(jtfNroFactB,12));
    	}
    	return jtfNroFactB;
    }

    public JTextField getProximoNroRemitoRIV() {
    	if (jtfNroRemito == null) {
    		jtfNroRemito = new JTextField();
    		jtfNroRemito.setBounds(new Rectangle(170,75,150,20));
    		jtfNroRemito.setDisabledTextColor(Utils.colorTextoDisabled);
    		jtfNroRemito.setEnabled(false);
    		jtfNroRemito.setDocument(new LimitadorNroMax(jtfNroRemito,12));
    	}
    	return jtfNroRemito;
    }

    public JButton getCancelar() {
    	if (jbCancelar == null) {
    		jbCancelar = new JButton();
    		jbCancelar.setBounds(new java.awt.Rectangle(405,335,100,30));
    		jbCancelar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
    		jbCancelar.setName("Cancelar");
    		jbCancelar.setText("Cancelar");
    		jbCancelar.setInputMap(0, map);
    	}
    	return jbCancelar;
    }

    public JButton getAceptar() {
        if (jbAceptar == null) {
            jbAceptar = new JButton();
            jbAceptar.setBounds(new java.awt.Rectangle(255,335,100,30));
            jbAceptar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jbAceptar.setName("Aceptar");
            jbAceptar.setText("Aceptar");
            jbAceptar.setInputMap(0, map);
        }
        return jbAceptar;
    }

    public JButton getJButtonModificarNrosFactura() {
        if (jbModNroFacts == null) {
        	jbModNroFacts = new JButton();
        	jbModNroFacts.setBounds(new java.awt.Rectangle(260,160,200,25));
        	jbModNroFacts.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        	jbModNroFacts.setName("ModNroFact");
        	jbModNroFacts.setText("Modificar Numeros");
        	jbModNroFacts.setInputMap(0, map);
        }
        return jbModNroFacts;
    }
    
    public JButton getJButtonImprimirTarjeta() {
        if (jbImprTarjeta == null) {
        	jbImprTarjeta = new JButton();
        	jbImprTarjeta.setBounds(new Rectangle(30,334,80,30));
        	jbImprTarjeta.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        	jbImprTarjeta.setName("Imprimir");
        	jbImprTarjeta.setText("Imprimir");
        	jbImprTarjeta.setInputMap(0, map);
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
