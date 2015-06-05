package cliente.GestionarLocalidad;

import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import persistencia.domain.Localidad;
import cliente.LimitadorNroMax;
import cliente.Imagenes.Botones.ButtonType;
import cliente.Imagenes.Botones.GlossyButton;
import cliente.Imagenes.util.Theme;
import cliente.Principal.GUIPrincipal;
import cliente.Utils.JPanel_Whit_Image;
import cliente.Utils.TransparentPanel;

import common.Utils;

public class GUIAltaModLocalidad extends JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel jpPpal = null;			    private JPanel jpDatos = null;
	private JButton jbAceptar = null;		    private JButton jbCancelar = null;
	private JButton jbProvincia = null;
	private JLabel jlNombre = null;			    private JLabel jlCodPostal = null;
	private JLabel jlProvincia = null;
	private JTextField jtfNombre = null;	    private JTextField jtfCodPostal = null;
    private JTextField jtfProvincia = null;
    private Localidad loc = null;
    private InputMap map = new InputMap();
        
    public GUIAltaModLocalidad(JDialog guiPadre) {
    	super(guiPadre);
        this.setSize(new java.awt.Dimension(540,280));
        this.setTitle("Nueva Localidad");
        this.setLocationRelativeTo(guiPadre);
        this.setResizable(false);
        this.setContentPane(getJPPpal());
        this.setModal(true);
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "pressed");
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "released");
    }
    
    public GUIAltaModLocalidad(Localidad p,JDialog guiPadre) {
        super(guiPadre);
        this.loc = p;
        this.setSize(new java.awt.Dimension(540,280));
        this.setTitle("Modificar Localidad");
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
            jpPpal.add(getJPDatos(),null);
            jpPpal.add(getJBAceptar(), null);
            jpPpal.add(getJBCancelar(), null);
            jpPpal.setBackground(Utils.colorFondo);
        }
        return jpPpal;
    }
        
    private JPanel getJPDatos() {
        if (jpDatos == null) {
            jlNombre = new JLabel("NOMBRE  (*)");
            jlNombre.setBounds(new Rectangle(10,40,150,26));
            jlNombre.setHorizontalAlignment(SwingConstants.RIGHT);
            jlNombre.setForeground(Utils.colorTexto);
            jlNombre.setFont(Utils.FuenteBasica());
            jlCodPostal = new JLabel("COD. POSTAL");
            jlCodPostal.setForeground(Utils.colorTexto);
            jlCodPostal.setFont(Utils.FuenteBasica());
            jlCodPostal.setBounds(new Rectangle(10,75,150,26));
            jlCodPostal.setHorizontalAlignment(SwingConstants.RIGHT);
            jlProvincia = new JLabel("PROVINCIA (*)");
            jlProvincia.setForeground(Utils.colorTexto);
            jlProvincia.setFont(Utils.FuenteBasica());
            jlProvincia.setBounds(new Rectangle(10,110,150,26));
            jlProvincia.setHorizontalAlignment(SwingConstants.RIGHT);
            jpDatos = new TransparentPanel();
            jpDatos.setLayout(null);
            jpDatos.setBorder(Utils.crearTituloYBorde("DATOS DE LA LOCALIDAD"));
            jpDatos.setBounds(new Rectangle(15,15,500,150));
            jpDatos.add(jlNombre, null);
            jpDatos.add(jlCodPostal, null);
            jpDatos.add(jlProvincia, null);
            jpDatos.add(getJTFNombre(), null);
            jpDatos.add(getJTFCodPostal(), null);
            jpDatos.add(getJTFProvincia(), null);
            jpDatos.add(getJBProvincia(), null);
            if (loc!=null) {
                jtfNombre.setText(loc.getNombre());
                jtfCodPostal.setText(String.valueOf(loc.getCodPostal()));
                jtfProvincia.setText(loc.getProvincia().getNombre());
            }
        }
        return jpDatos;
    }

    public JTextField getJTFNombre() {
        if (jtfNombre == null) {
            jtfNombre = new JTextField();
            jtfNombre.setBounds(new Rectangle(170,40,310,26));
            jtfNombre.setFont(Utils.FuenteCampos());
        }
        return jtfNombre;
    }
    
    public JTextField getJTFCodPostal() {
        if (jtfCodPostal == null) {
            jtfCodPostal = new JTextField();
            jtfCodPostal.setBounds(new Rectangle(170,75,310,26));
            jtfCodPostal.setDocument(new LimitadorNroMax(jtfCodPostal,6));
            jtfCodPostal.setFont(Utils.FuenteCampos());
        }
        return jtfCodPostal;
    }
    
    public JTextField getJTFProvincia() {
        if (jtfProvincia == null) {
        	jtfProvincia = new JTextField();
        	jtfProvincia.setBounds(new java.awt.Rectangle(170,110,150,26));
        	jtfProvincia.setDisabledTextColor(Utils.colorTextoDisabled);
        	jtfProvincia.setEnabled(false);
        	jtfProvincia.setFont(Utils.FuenteCampos());
        }
        return jtfProvincia;
    }
    
    public JButton getJBProvincia() {
        if (jbProvincia == null) {
        	jbProvincia = new GlossyButton("BUSCAR",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
        	jbProvincia.setBounds(new Rectangle(330,110,150,26));
        	jbProvincia.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        	jbProvincia.setName("Buscar");
        	jbProvincia.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/find.png")));
        	jbProvincia.setInputMap(0, map);
        	jbProvincia.setFont(Utils.FuenteBotonesChicos());
        }
        return jbProvincia;
    }
    
    public JButton getJBAceptar() {
        if (jbAceptar == null) {
            jbAceptar = new GlossyButton("ACEPTAR",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
            jbAceptar.setBounds(new Rectangle(35,200,200,40));
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
            jbCancelar.setBounds(new Rectangle(305,200,200,40));
            jbCancelar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jbCancelar.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/cancel.png")));
            jbCancelar.setInputMap(0, map);
            jbCancelar.setFont(Utils.FuenteBotonesGrandes());
        }
        return jbCancelar;
    }
    
    public void setActionListeners(ActionListener lis) {
        jbAceptar.addActionListener(lis);
        jbCancelar.addActionListener(lis);
        jbProvincia.addActionListener(lis);
    }
    
    public void setProvincia(String string) {
    	jtfProvincia.setText(string);
    }
}