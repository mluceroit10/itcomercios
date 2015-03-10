package cliente.GestionarCliente;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

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
        this.setSize(500,365);
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
        this.setSize(500,365);
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
            jpPpal = new JPanel();
            jpPpal.setLayout(null);
            jpPpal.setSize(500, 365);
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
    		jpDatosClientes = new JPanel();
    		jpDatosClientes.setLayout(null);
    		jpDatosClientes.setBorder(javax.swing.BorderFactory.createTitledBorder(Utils.b,
    				"Datos Cliente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
    				javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
    		jpDatosClientes.setBounds(new java.awt.Rectangle(15,15,470,260));
    		jlNombre = new JLabel();
    		jlNombre.setBounds(new Rectangle(12,30,110,15));
    		jlNombre.setText("Nombre (*)");
    		jlNombre.setHorizontalAlignment(SwingConstants.RIGHT);
    		jlCuit = new JLabel();
    		jlCuit.setBounds(new Rectangle(12,62,110,15));
    		jlCuit.setText("Cuit");
    		jlCuit.setHorizontalAlignment(SwingConstants.RIGHT);
    		jlIva = new JLabel();
    		jlIva.setBounds(new Rectangle(12,94,110,15));
    		jlIva.setText("Iva");
    		jlIva.setHorizontalAlignment(SwingConstants.RIGHT);
    		jlIngrBrutos = new JLabel();
    		jlIngrBrutos.setBounds(new Rectangle(12,126,110,15));
    		jlIngrBrutos.setText("Ingresos Brutos");
    		jlIngrBrutos.setHorizontalAlignment(SwingConstants.RIGHT);
    		jlTel = new JLabel();
    		jlTel.setBounds(new Rectangle(12,158,110,15));
    		jlTel.setText("Teléfono");
    		jlTel.setHorizontalAlignment(SwingConstants.RIGHT);
    		jlDireccion = new JLabel();
    		jlDireccion.setBounds(new Rectangle(12,190,110,15));
    		jlDireccion.setText("Dirección (*)");
    		jlDireccion.setHorizontalAlignment(SwingConstants.RIGHT);
    		jlLocalidad = new JLabel();
    		jlLocalidad.setBounds(new Rectangle(12,222,110,15));
    		jlLocalidad.setText("Localidad (*)");
    		jlLocalidad.setHorizontalAlignment(SwingConstants.RIGHT);
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
    		jpDatosClientes.setBackground(Utils.colorPanel);
    	}
    	return jpDatosClientes;
    }
    
    public JTextField getIngrBrutos() {
        if (jtfIngBrutos == null) {
        	jtfIngBrutos = new JTextField();
        	jtfIngBrutos.setBounds(new Rectangle(130,126,330,22));
        	jtfIngBrutos.setDocument(new LimitadorNroGuion(jtfIngBrutos));
        }
        return jtfIngBrutos;
    }
    
    public JTextField getTelefono() {
        if (jtfTel == null) {
        	jtfTel = new JTextField();
        	jtfTel.setBounds(new Rectangle(130,158,330,22));
        }
        return jtfTel;
    }
    
    public JTextField getCuit() {
    	if (jtfCuit == null) {
    		jtfCuit = new JTextField();
    		jtfCuit.setBounds(new Rectangle(130,62,330,22));
    		jtfCuit.setDocument(new LimitadorNroGuion(jtfCuit));
    	}
    	return jtfCuit;
    }
    
    public JComboBox getJCBIvaCl() {
		if (jcbIvaCl==null){
			jcbIvaCl=new JComboBox();
			jcbIvaCl.setBounds(new java.awt.Rectangle(130,94,330,22));
			jcbIvaCl.setBackground(new Color(255,255,255));
			jcbIvaCl.setForeground(java.awt.Color.black);
			jcbIvaCl.addItem("Resp. Inscripto");
			jcbIvaCl.addItem("Resp. NO Inscripto");
			jcbIvaCl.addItem("Exento");
			jcbIvaCl.addItem("NO Resp.");
			jcbIvaCl.addItem("Consumidor Final");
			jcbIvaCl.addItem("Monotributo");
		}
		return jcbIvaCl;
	}
    
    public JTextField getNombre() {
        if (jtfNombre == null) {
            jtfNombre = new JTextField();
            jtfNombre.setBounds(new Rectangle(130,30,330,22));
        }
        return jtfNombre;
    }
    
    public JTextField getDireccion() {
        if (jtfCalle == null) {
        	jtfCalle = new JTextField();
        	jtfCalle.setBounds(new Rectangle(130,190,330,22));
        }
        return jtfCalle;
    }
        
    public JTextField getLocalidad() {
    	if (jtfLocalidad == null) {
    		jtfLocalidad = new JTextField();
    		jtfLocalidad.setBounds(new Rectangle(130,222,230,22));
    		jtfLocalidad.setDisabledTextColor(Utils.colorTextoDisabled);
    		jtfLocalidad.setEnabled(false);
    	}
    	return jtfLocalidad;
    }

    public JButton getJButtonLocalidad() {
        if (jbLocalidad == null) {
            jbLocalidad = new JButton();
            jbLocalidad.setBounds(new Rectangle(380,222,80,22));
            jbLocalidad.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jbLocalidad.setName("Buscar");
            jbLocalidad.setText("Buscar");
            jbLocalidad.setInputMap(0, map);
        }
        return jbLocalidad;
    }
    
    public JButton getCancelar() {
    	if (jbCancelar == null) {
    		jbCancelar = new JButton();
    		jbCancelar.setBounds(new java.awt.Rectangle(265,295,100,30));
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
            jbAceptar.setBounds(new java.awt.Rectangle(135,295,100,30));
            jbAceptar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jbAceptar.setName("Aceptar");
            jbAceptar.setText("Aceptar");
            jbAceptar.setInputMap(0, map);
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
