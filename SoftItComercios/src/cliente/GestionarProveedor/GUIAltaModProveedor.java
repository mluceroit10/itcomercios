package cliente.GestionarProveedor;

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

import persistencia.domain.Proveedor;
import cliente.LimitadorNroMax;
import cliente.Imagenes.Botones.ButtonType;
import cliente.Imagenes.Botones.GlossyButton;
import cliente.Imagenes.util.Theme;
import cliente.Principal.GUIPrincipal;
import cliente.Utils.JPanel_Whit_Image;
import cliente.Utils.TransparentPanel;

import common.Utils;

public class GUIAltaModProveedor extends JDialog {
	private static final long serialVersionUID = 1L;
    private JPanel jpPpal = null;               private JPanel jpDatosProveedor = null; 
    private JButton jbCancelar = null;          private JButton jbAceptar = null;
    private JButton jbLocalidad = null;         
    private JLabel jlNombre = null;		    	private JLabel jlDireccion = null;	          
    private JLabel jlCodigo = null;           	private JLabel jlTel = null;
    private JLabel jlLocalidad = null;    
    private JTextField jtfNombre = null;    
    private JTextField jtfCodigo = null;       	private JTextField jtfTel = null;
    private JTextField jtfLocalidad = null;     private JTextField jtfCalle = null;
    private Proveedor prov;
    private int nroAsignado=0;
    private InputMap map = new InputMap();
    
    public GUIAltaModProveedor(int nroCod,JDialog guiPadre) {
        super(guiPadre);
        nroAsignado=nroCod;
        this.setSize(700,350);
        this.setLocationRelativeTo(guiPadre);
        this.setResizable(false);
        this.setContentPane(getPanelPpal());
        this.setModal(true);
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "pressed");
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "released");
        this.setTitle("Nuevo Proveedor");
    }

    public GUIAltaModProveedor(Proveedor p,JDialog guiPadre) {
        super(guiPadre);
        this.prov = p;
        this.setSize(700,350);
        this.setLocationRelativeTo(guiPadre);
        this.setResizable(false);
        this.setContentPane(getPanelPpal());
        this.setModal(true);
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "pressed");
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "released");
        this.setTitle("Modificar Proveedor");
        
    }

    private JPanel getPanelPpal() {
        if (jpPpal == null) {
            jpPpal= new JPanel_Whit_Image("/cliente/Imagenes/Imagenes/background.jpg");
            jpPpal.setLayout(null);
            jpPpal.setSize(700, 350);
            jpPpal.setBorder(javax.swing.BorderFactory.createCompoundBorder(null, null));
            jpPpal.setName("Gestión Proveedor");
            jpPpal.add(getCancelar(), null);
            jpPpal.add(getAceptar(), null);
            jpPpal.add(getJPDatosPers(), null);
            jpPpal.setBackground(Utils.colorFondo);
        }
        return jpPpal;
    }

    private JPanel getJPDatosPers() {
    	if (jpDatosProveedor == null) {
    		jpDatosProveedor = new TransparentPanel();
    		jpDatosProveedor.setLayout(null);
    		jpDatosProveedor.setBorder(Utils.crearTituloYBorde("DATOS PROVEEDOR"));
    		jpDatosProveedor.setBounds(new java.awt.Rectangle(15,15,665,220));
    		jlCodigo = new JLabel();
    		jlCodigo.setBounds(new Rectangle(12,40,150,26));
    		jlCodigo.setText("CODIGO (*)");
    		jlCodigo.setHorizontalAlignment(SwingConstants.RIGHT);
    		jlCodigo.setForeground(Utils.colorTexto);
    		jlCodigo.setFont(Utils.FuenteBasica());
    		jlNombre = new JLabel();
    		jlNombre.setBounds(new Rectangle(12,75,150,26));
    		jlNombre.setText("NOMBRE (*)");
    		jlNombre.setHorizontalAlignment(SwingConstants.RIGHT);
    		jlNombre.setForeground(Utils.colorTexto);
    		jlNombre.setFont(Utils.FuenteBasica());
    		jlTel = new JLabel();
    		jlTel.setBounds(new Rectangle(12,110,150,26));
    		jlTel.setText("TELEFONO (*)");
    		jlTel.setForeground(Utils.colorTexto);
    		jlTel.setFont(Utils.FuenteBasica());
    		jlTel.setHorizontalAlignment(SwingConstants.RIGHT);
    		jlLocalidad = new JLabel();
    		jlLocalidad.setBounds(new Rectangle(12,180,150,26));
    		jlLocalidad.setText("LOCALIDAD (*)");
    		jlLocalidad.setForeground(Utils.colorTexto);
    		jlLocalidad.setFont(Utils.FuenteBasica());
    		jlLocalidad.setHorizontalAlignment(SwingConstants.RIGHT);
    		jlDireccion = new JLabel();
    		jlDireccion.setBounds(new Rectangle(12,145,150,26));
    		jlDireccion.setText("DIRECCION (*)");
    		jlDireccion.setForeground(Utils.colorTexto);
    		jlDireccion.setFont(Utils.FuenteBasica());
    		jlDireccion.setHorizontalAlignment(SwingConstants.RIGHT);
    		jpDatosProveedor.add(jlCodigo, null);
    		jpDatosProveedor.add(jlNombre, null);
    		jpDatosProveedor.add(jlTel, null);
    		jpDatosProveedor.add(jlDireccion, null);
    		jpDatosProveedor.add(jlLocalidad, null);
    		jpDatosProveedor.add(getTelefono(), null);
    		jpDatosProveedor.add(getCodigo(), null);
    		jpDatosProveedor.add(getNombre(), null);
    		jpDatosProveedor.add(getLocalidad(), null);
    		jpDatosProveedor.add(getDireccion(), null);
    		jpDatosProveedor.add(getJButtonLocalidad(), null);
    		if (prov!=null) {
    			jtfNombre.setText(prov.getNombre());
    			jtfCodigo.setText(String.valueOf(prov.getCodigo()));
    			jtfTel.setText(prov.getTelefono());
    			jtfCalle.setText(prov.getDireccion());
    			jtfLocalidad.setText(prov.getLocalidad().getNombre());
    		}
    	}
    	return jpDatosProveedor;
    }
     
    public JTextField getTelefono() {
        if (jtfTel == null) {
        	jtfTel = new JTextField();
        	jtfTel.setBounds(new Rectangle(170,110,470,26));
        	jtfTel.setFont(Utils.FuenteCampos());
        }
        return jtfTel;
    }
    
    public JTextField getCodigo() {
    	if (jtfCodigo == null) {
    		jtfCodigo = new JTextField();
    		jtfCodigo.setBounds(new Rectangle(170,40,470,26));
    		jtfCodigo.setDocument(new LimitadorNroMax(jtfCodigo,6));
    		jtfCodigo.setText(String.valueOf(nroAsignado));
    		jtfCodigo.setFont(Utils.FuenteCampos());
    	}
    	return jtfCodigo;
    }
    
    public JTextField getNombre() {
        if (jtfNombre == null) {
            jtfNombre = new JTextField();
            jtfNombre.setBounds(new Rectangle(170,75,470,26));
            jtfNombre.setFont(Utils.FuenteCampos());
        }
        return jtfNombre;
    }
    
    public JTextField getDireccion() {
        if (jtfCalle == null) {
        	jtfCalle = new JTextField();
        	jtfCalle.setBounds(new Rectangle(170,145,470,26));
        	jtfCalle.setFont(Utils.FuenteCampos());
        }
        return jtfCalle;
    }
        
    public JTextField getLocalidad() {
    	if (jtfLocalidad == null) {
    		jtfLocalidad = new JTextField();
    		jtfLocalidad.setBounds(new Rectangle(170,180,300,26));
    		jtfLocalidad.setDisabledTextColor(Utils.colorTextoDisabled);
    		jtfLocalidad.setEnabled(false);
    		jtfLocalidad.setFont(Utils.FuenteCampos());
    	}
    	return jtfLocalidad;
    }

    public JButton getJButtonLocalidad() {
        if (jbLocalidad == null) {
            jbLocalidad = new GlossyButton("BUSCAR",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
            jbLocalidad.setBounds(new Rectangle(490,180,150,26));
            jbLocalidad.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jbLocalidad.setName("Buscar");
            jbLocalidad.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/find.png")));
            jbLocalidad.setInputMap(0, map);
            jbLocalidad.setFont(Utils.FuenteBotonesChicos());
            jbLocalidad.setMnemonic('B');
        }
        return jbLocalidad;
    }
    
    public JButton getCancelar() {
    	if (jbCancelar == null) {
    		jbCancelar = new GlossyButton("CANCELAR",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
    		jbCancelar.setBounds(new java.awt.Rectangle(400,270,200,40));
    		jbCancelar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
    		jbCancelar.setName("Cancelar");
    		jbCancelar.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/cancel.png")));
    		jbCancelar.setInputMap(0, map);
    		jbCancelar.setFont(Utils.FuenteBotonesGrandes());
    		jbCancelar.setMnemonic('C');
    	}
    	return jbCancelar;
    }

    public JButton getAceptar() {
        if (jbAceptar == null) {
            jbAceptar = new GlossyButton("ACEPTAR",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
            jbAceptar.setBounds(new java.awt.Rectangle(100,270,200,40));
            jbAceptar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jbAceptar.setName("Aceptar");
            jbAceptar.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/check.png")));
            jbAceptar.setInputMap(0, map);
            jbAceptar.setFont(Utils.FuenteBotonesGrandes());
            jbAceptar.setMnemonic('A');
        }
        return jbAceptar;
    }

    public void setActionListeners(ActionListener lis){
        this.jbCancelar.addActionListener(lis);
        this.jbAceptar.addActionListener(lis);
        this.jbLocalidad.addActionListener(lis);
    }

    public void setLocalidad(String string) {
        jtfLocalidad.setText(string);
    }
    
}
