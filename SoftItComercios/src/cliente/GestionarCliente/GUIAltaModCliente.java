package cliente.GestionarCliente;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

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

import persistencia.domain.Cliente;
import cliente.LimitadorNroGuion;
import cliente.Imagenes.Botones.ButtonType;
import cliente.Imagenes.Botones.GlossyButton;
import cliente.Imagenes.util.Theme;
import cliente.Principal.GUIPrincipal;
import cliente.Utils.JPanel_Whit_Image;
import cliente.Utils.TransparentPanel;

import common.Utils;

public class GUIAltaModCliente extends JDialog {
	private static final long serialVersionUID = 1L;
    private JPanel jpPpal = null;               private JPanel jpDatosClientes = null;  
    private JButton jbCancelar = null;          private JButton jbAceptar = null;
    private JButton jbLocalidad = null;         
    private JLabel jlNombre = null;		    	private JLabel jlDireccion = null;	          
    private JLabel jlCuit = null;           	private JLabel jlTel = null;
    private JLabel jlIngrBrutos = null;           	private JLabel jlIva = null;
    private JLabel jlLocalidad = null;    
    
    private JTextField jtfNombre = null;		private JTextField jtfCuit = null;
    private JTextField jtfIngBrutos = null;		private JComboBox  jcbIvaCl = null;    
    private JTextField jtfLocalidad = null;     private JTextField jtfCalle = null;
    private JTextField jtfTel = null;
    private Cliente cte;
    private InputMap map = new InputMap();
    
    public GUIAltaModCliente(JDialog guiPadre) {
        super(guiPadre);
        this.setSize(700,430);
        this.setLocationRelativeTo(guiPadre);
        this.setResizable(false);
        this.setContentPane(getPanelPpal());
        this.setTitle("Nuevo Cliente");
        this.setModal(true);
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "pressed");
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "released");
    }

    public GUIAltaModCliente(Cliente c,JDialog guiPadre) {
        super(guiPadre);
        this.cte = c;
        this.setSize(700,430);
        this.setLocationRelativeTo(guiPadre);
        this.setResizable(false);
        this.setContentPane(getPanelPpal());
        this.setTitle("Modificar Cliente");
        this.setModal(true);
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "pressed");
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "released");;
    }

    private JPanel getPanelPpal() {
        if (jpPpal == null) {
            jpPpal= new JPanel_Whit_Image("/cliente/Imagenes/Imagenes/background.jpg");
            jpPpal.setLayout(null);
            jpPpal.setSize(700, 400);
            jpPpal.setBorder(javax.swing.BorderFactory.createCompoundBorder(null, null));
            jpPpal.setName("Gestión Cliente");
            jpPpal.add(getCancelar(), null);
            jpPpal.add(getAceptar(), null);
            jpPpal.add(getJPDatosPers(), null);
            jpPpal.setBackground(Utils.colorFondo);
        }
        return jpPpal;
    }


    private JPanel getJPDatosPers() {
    	if (jpDatosClientes == null) {
    		jpDatosClientes = new TransparentPanel();
    		jpDatosClientes.setLayout(null);
    		jpDatosClientes.setBorder(Utils.crearTituloYBorde("DATOS CLIENTE"));
    		jpDatosClientes.setBounds(new java.awt.Rectangle(15,15,665,300));
    		jlNombre = new JLabel();
    		jlNombre.setBounds(new Rectangle(12,40,210,26));
    		jlNombre.setText("NOMBRE (*)");
    		jlNombre.setForeground(Utils.colorTexto);
    		jlNombre.setFont(Utils.FuenteBasica());
    		jlNombre.setHorizontalAlignment(SwingConstants.RIGHT);
    		jlCuit = new JLabel();
    		jlCuit.setBounds(new Rectangle(12,75,210,26));
    		jlCuit.setText("CUIT");
    		jlCuit.setForeground(Utils.colorTexto);
    		jlCuit.setFont(Utils.FuenteBasica());
    		jlCuit.setHorizontalAlignment(SwingConstants.RIGHT);
    		jlIva = new JLabel();
    		jlIva.setBounds(new Rectangle(12,110,210,26));
    		jlIva.setText("IVA");
    		jlIva.setForeground(Utils.colorTexto);
    		jlIva.setFont(Utils.FuenteBasica());
    		jlIva.setHorizontalAlignment(SwingConstants.RIGHT);
    		jlIngrBrutos = new JLabel();
    		jlIngrBrutos.setBounds(new Rectangle(12,145,210,26));
    		jlIngrBrutos.setText("INGRESOS BRUTOS");
    		jlIngrBrutos.setForeground(Utils.colorTexto);
    		jlIngrBrutos.setFont(Utils.FuenteBasica());
    		jlIngrBrutos.setHorizontalAlignment(SwingConstants.RIGHT);
    		jlTel = new JLabel();
    		jlTel.setBounds(new Rectangle(12,180,210,26));
    		jlTel.setText("TELEFONO");
    		jlTel.setForeground(Utils.colorTexto);
    		jlTel.setFont(Utils.FuenteBasica());
    		jlTel.setHorizontalAlignment(SwingConstants.RIGHT);
    		jlDireccion = new JLabel();
    		jlDireccion.setBounds(new Rectangle(12,215,210,26));
    		jlDireccion.setText("DIRECCION (*)");
    		jlDireccion.setHorizontalAlignment(SwingConstants.RIGHT);
    		jlDireccion.setForeground(Utils.colorTexto);
    		jlDireccion.setFont(Utils.FuenteBasica());
    		jlLocalidad = new JLabel();
    		jlLocalidad.setBounds(new Rectangle(12,250,210,26));
    		jlLocalidad.setText("LOCALIDAD (*)");
    		jlLocalidad.setHorizontalAlignment(SwingConstants.RIGHT);
    		jlLocalidad.setForeground(Utils.colorTexto);
    		jlLocalidad.setFont(Utils.FuenteBasica());
    		jpDatosClientes.add(jlCuit, null);
    		jpDatosClientes.add(jlNombre, null);
    		jpDatosClientes.add(jlIva, null);
    		jpDatosClientes.add(jlIngrBrutos, null);
    		jpDatosClientes.add(jlTel, null);
    		jpDatosClientes.add(jlDireccion, null);
    		jpDatosClientes.add(jlLocalidad, null);
    		jpDatosClientes.add(getTelefono(), null);
    		jpDatosClientes.add(getCuit(), null);
    		jpDatosClientes.add(getJCBIvaCl(), null);
    		jpDatosClientes.add(getIngrBrutos(), null);
    		jpDatosClientes.add(getNombre(), null);
    		jpDatosClientes.add(getLocalidad(), null);
    		jpDatosClientes.add(getDireccion(), null);
    		jpDatosClientes.add(getJButtonLocalidad(), null);
    		if (cte!=null) {
    			jtfNombre.setText(cte.getNombre());
    			jtfCuit.setText(cte.getCuit());
    			jtfIngBrutos.setText(cte.getIngBrutosCl());
    			jcbIvaCl.setSelectedItem(cte.getIvaCl());
    			jtfTel.setText(cte.getTelefono());
    			jtfCalle.setText(cte.getDireccion());
    			jtfLocalidad.setText(cte.getLocalidad().getNombre());
    		}
    	}
    	return jpDatosClientes;
    }
    
    public JTextField getIngrBrutos() {
        if (jtfIngBrutos == null) {
        	jtfIngBrutos = new JTextField();
        	jtfIngBrutos.setBounds(new Rectangle(230,145,410,26));
        	jtfIngBrutos.setDocument(new LimitadorNroGuion(jtfIngBrutos));
        	jtfIngBrutos.setFont(Utils.FuenteCampos());
        }
        return jtfIngBrutos;
    }
    
    public JTextField getTelefono() {
        if (jtfTel == null) {
        	jtfTel = new JTextField();
        	jtfTel.setBounds(new Rectangle(230,180,410,26));
        	jtfTel.setFont(Utils.FuenteCampos());
        }
        return jtfTel;
    }
    
    public JTextField getCuit() {
    	if (jtfCuit == null) {
    		jtfCuit = new JTextField();
    		jtfCuit.setBounds(new Rectangle(230,75,410,26));
    		jtfCuit.setDocument(new LimitadorNroGuion(jtfCuit));
    		jtfCuit.setFont(Utils.FuenteCampos());
    	}
    	return jtfCuit;
    }
    
    public JComboBox getJCBIvaCl() {
		if (jcbIvaCl==null){
			jcbIvaCl=new JComboBox();
			jcbIvaCl.setBounds(new java.awt.Rectangle(230,110,410,26));
			jcbIvaCl.setBackground(new Color(255,255,255));
			jcbIvaCl.setForeground(java.awt.Color.black);
			jcbIvaCl.addItem("Resp. Inscripto");
			jcbIvaCl.addItem("Resp. NO Inscripto");
			jcbIvaCl.addItem("Exento");
			jcbIvaCl.addItem("NO Resp.");
			jcbIvaCl.addItem("Consumidor Final");
			jcbIvaCl.addItem("Monotributo");
			jcbIvaCl.setFont(Utils.FuenteCampos());
		}
		return jcbIvaCl;
	}
    
    public JTextField getNombre() {
        if (jtfNombre == null) {
            jtfNombre = new JTextField();
            jtfNombre.setBounds(new Rectangle(230,40,410,26));
            jtfNombre.setFont(Utils.FuenteCampos());
        }
        return jtfNombre;
    }
    
    public JTextField getDireccion() {
        if (jtfCalle == null) {
        	jtfCalle = new JTextField();
        	jtfCalle.setBounds(new Rectangle(230,215,410,26));
        	jtfCalle.setFont(Utils.FuenteCampos());
        }
        return jtfCalle;
    }
        
    public JTextField getLocalidad() {
    	if (jtfLocalidad == null) {
    		jtfLocalidad = new JTextField();
    		jtfLocalidad.setBounds(new Rectangle(230,250,250,26));
    		jtfLocalidad.setDisabledTextColor(Utils.colorTextoDisabled);
    		jtfLocalidad.setEnabled(false);
    		jtfLocalidad.setFont(Utils.FuenteCampos());
    	}
    	return jtfLocalidad;
    }

    public JButton getJButtonLocalidad() {
        if (jbLocalidad == null) {
            jbLocalidad = new GlossyButton("BUSCAR",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
            jbLocalidad.setBounds(new Rectangle(490,250,150,26));
            jbLocalidad.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jbLocalidad.setName("Buscar");
            jbLocalidad.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/find.png")));
            jbLocalidad.setInputMap(0, map);
            jbLocalidad.setFont(Utils.FuenteBotonesChicos());
        }
        return jbLocalidad;
    }
    
    public JButton getCancelar() {
    	if (jbCancelar == null) {
    		jbCancelar = new GlossyButton("CANCELAR",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
    		jbCancelar.setBounds(new java.awt.Rectangle(400,350,200,40));
    		jbCancelar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
    		jbCancelar.setName("Cancelar");
    		jbCancelar.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/cancel.png")));
    		jbCancelar.setInputMap(0, map);
    		jbCancelar.setFont(Utils.FuenteBotonesGrandes());
    	}
    	return jbCancelar;
    }

    public JButton getAceptar() {
        if (jbAceptar == null) {
            jbAceptar = new GlossyButton("ACEPTAR",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
            jbAceptar.setBounds(new java.awt.Rectangle(100,350,200,40));
            jbAceptar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jbAceptar.setName("Aceptar");
            jbAceptar.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/check.png")));
            jbAceptar.setInputMap(0, map);
            jbAceptar.setFont(Utils.FuenteBotonesGrandes());
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
