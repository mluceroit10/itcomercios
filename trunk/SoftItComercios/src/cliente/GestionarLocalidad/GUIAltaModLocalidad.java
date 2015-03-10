package cliente.GestionarLocalidad;

import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

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
        this.setSize(new java.awt.Dimension(340,235));
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
        this.setSize(new java.awt.Dimension(340,235));
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
            jpPpal = new JPanel();
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
            jlNombre = new JLabel("Nombre  (*)    ");
            jlNombre.setBounds(new Rectangle(10,30,100,15));
            jlNombre.setHorizontalAlignment(SwingConstants.RIGHT);
            jlCodPostal = new JLabel("Cod. Postal    ");
            jlCodPostal.setBounds(new Rectangle(10,62,100,15));
            jlCodPostal.setHorizontalAlignment(SwingConstants.RIGHT);
            jlProvincia = new JLabel("Provincia(*)   ");
            jlProvincia.setBounds(new Rectangle(10,94,100,15));
            jlProvincia.setHorizontalAlignment(SwingConstants.RIGHT);
            jpDatos = new JPanel();
            jpDatos.setLayout(null);
            jpDatos.setSize(new java.awt.Dimension(300,130));
            jpDatos.setBorder(javax.swing.BorderFactory.createTitledBorder(Utils.b, "Datos de la Localidad", 
                    javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, 
                    javax.swing.border.TitledBorder.DEFAULT_POSITION, 
                    new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), java.awt.Color.black));
            jpDatos.setBounds(new Rectangle(15,15,300,130));
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
            jpDatos.setBackground(Utils.colorPanel);
        }
        return jpDatos;
    }

    public JTextField getJTFNombre() {
        if (jtfNombre == null) {
            jtfNombre = new JTextField();
            jtfNombre.setBounds(new Rectangle(107,30,180,22));
        }
        return jtfNombre;
    }
    
    public JTextField getJTFCodPostal() {
        if (jtfCodPostal == null) {
            jtfCodPostal = new JTextField();
            jtfCodPostal.setBounds(new Rectangle(107,62,180,22));
            jtfCodPostal.setDocument(new LimitadorNroMax(jtfCodPostal,6));
        }
        return jtfCodPostal;
    }
    
    public JTextField getJTFProvincia() {
        if (jtfProvincia == null) {
        	jtfProvincia = new JTextField();
        	jtfProvincia.setBounds(new java.awt.Rectangle(107,94,95,22));
        	jtfProvincia.setDisabledTextColor(Utils.colorTextoDisabled);
        	jtfProvincia.setEnabled(false);
        }
        return jtfProvincia;
    }
    
    public JButton getJBProvincia() {
        if (jbProvincia == null) {
        	jbProvincia = new JButton();
        	jbProvincia.setBounds(new Rectangle(210,94,80,22));
        	jbProvincia.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        	jbProvincia.setName("Buscar");
        	jbProvincia.setText("Buscar");
        	jbProvincia.setInputMap(0, map);
        }
        return jbProvincia;
    }
    
    public JButton getJBAceptar() {
        if (jbAceptar == null) {
            jbAceptar = new JButton();
            jbAceptar.setBounds(new Rectangle(50,165,100,30));
            jbAceptar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jbAceptar.setText("Aceptar");
            jbAceptar.setInputMap(0, map);
        }
        return jbAceptar;
    }
    
    public JButton getJBCancelar() {
        if (jbCancelar == null) {
            jbCancelar = new JButton();
            jbCancelar.setBounds(new Rectangle(180,165,100,30));
            jbCancelar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jbCancelar.setText("Cancelar");
            jbCancelar.setInputMap(0, map);
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