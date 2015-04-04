package cliente.GestionarCliente;

import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;

import cliente.ModeloTabla;
import cliente.Imagenes.Botones.ButtonType;
import cliente.Imagenes.Botones.GlossyButton;
import cliente.Imagenes.util.Theme;
import cliente.Principal.GUIPrincipal;
import cliente.Utils.JPanel_Whit_Image;
import cliente.Utils.TransparentPanel;

import common.Utils;

public class GUIGestionarCliente extends JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel jpPpal = null;		    private JPanel jpDatos = null;
    private JPanel jpGestion = null;	    private JPanel jpBuscador = null;
    private JButton jbIngresar = null;		private JButton jbModif = null;
    private JButton jbEliminar = null;    	private JButton jbAceptar = null;
    private JButton jbCancelar = null;		private JButton jbImprimir;	  
    private JButton jbDeudas = null;	  
    private JLabel jlNombre;
    private JTextField jtfNombre = null;    
    private ModeloTabla modTabla = null;
    public JScrollPane jspDatos = null;
    public final String[] titulos ={"ID","Nombre","Cuit","Iva","Teléfono","Dirección", "Localidad"};
    public Object[][] datos;
    public JTable tabla;
	private JButton jbECuenta;
	private InputMap map = new InputMap();
	 	
    public GUIGestionarCliente(JFrame guiPadre) {
        super(guiPadre);
        datos = new Object[0][titulos.length];
        this.setSize(740,540);
        this.setLocationRelativeTo(guiPadre);
        this.setResizable(false);
        this.setTitle("Gestión de Clientes");
        this.setContentPane(getJPPpal());
        this.getContentPane().setName("GUIGestionarCliente");
        this.setModal(true);
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "pressed");
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "released");
    }

    public GUIGestionarCliente(JDialog guiPadre) {
        super(guiPadre);
        datos = new Object[0][titulos.length];
        this.setSize(740,540);
        this.setLocationRelativeTo(guiPadre);
        this.setResizable(false);
        this.setTitle("Gestión de Clientes");
        this.setContentPane(getJPPpal());
        this.getContentPane().setName("GUIGestionarCliente");
        this.setModal(true);
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "pressed");
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "released");
    }
    
    public JPanel getJPPpal() {
        if (jpPpal == null) {
            jpPpal= new JPanel_Whit_Image("/cliente/Imagenes/Imagenes/background.jpg");
            jpPpal.setLayout(null);
            jpPpal.add(getJPGestion(), null);
            jpPpal.add(getJPDatos(), null);
            jpPpal.add(getJPBuscador(), null);
            jpPpal.add(getJBAceptar(), null);
            jpPpal.add(getJBCancelar(), null);
            jpPpal.setBackground(Utils.colorFondo);
        }
        return jpPpal;
    }

    private JPanel getJPBuscador() {
        if (jpBuscador == null) {
            jpBuscador = new TransparentPanel();
            jlNombre = new JLabel();
            jlNombre.setBounds(new Rectangle(10,30,60,15));
            jlNombre.setText("NOMBRE:");
            jlNombre.setForeground(Utils.colorTexto);
            jpBuscador.setLayout(null);
            jpBuscador.setBorder(javax.swing.BorderFactory.createTitledBorder(Utils.b, "BUSCAR",
                    javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                    javax.swing.border.TitledBorder.DEFAULT_POSITION,
                    new java.awt.Font("Dialog", java.awt.Font.BOLD, 14), Utils.colorTexto));
            jpBuscador.setBounds(new java.awt.Rectangle(535,25,180,65));
            jpBuscador.add(jlNombre, null);
            jpBuscador.add(getJTFBuscador(), null);
        }
        return jpBuscador;
    }

    private JPanel getJPGestion() {
        if (jpGestion == null) {
            jpGestion = new TransparentPanel();
            jpGestion.setLayout(null);
            jpGestion.setBounds(new java.awt.Rectangle(15,25,510,65));
            jpGestion.setBorder(javax.swing.BorderFactory.createTitledBorder(Utils.b, "GESTION", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 14), Utils.colorTexto));
            jpGestion.add(getJBIngresar(), null);
            jpGestion.add(getJBModificar(), null);
            jpGestion.add(getJBEliminar(), null);
            jpGestion.add(getJBImprimir(), null);
            jpGestion.add(getJBECuenta(), null);
       }
        return jpGestion;
    }

    private JPanel getJPDatos() {
        if (jpDatos == null) {
            jpDatos = new TransparentPanel();
            jpDatos.setLayout(null);
            jpDatos.setBounds(new Rectangle(15,110,700,340));
            jpDatos.setBorder(javax.swing.BorderFactory.createTitledBorder(Utils.b, "LISTADO DE CLIENTES",
                    javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                    javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 14), Utils.colorTexto));
            jpDatos.add(getJSPDatos(), null);
            jpDatos.add(getJBDeudas(), null);
       }
        return jpDatos;
    }

    private JScrollPane getJSPDatos() {
        if (jspDatos == null) {
                jspDatos = new JScrollPane();
                jspDatos.setBounds(new Rectangle(10,20,680,280));
                jspDatos.setViewportView(getJTDatos());
        }
        return jspDatos;
    }

    public JTable getJTDatos() {
        if (tabla == null) {
            modTabla = new ModeloTabla(titulos, datos);
            tabla = new JTable(modTabla);
            Utils.ocultarColumnaId(tabla);
            TableColumn columna0 = tabla.getColumn("Cuit");
            columna0.setPreferredWidth(100);
			columna0.setMaxWidth(100);
			TableColumn columna1 = tabla.getColumn("Iva");
            columna1.setPreferredWidth(60);
			columna1.setMaxWidth(60);
        }
        return tabla;
    }

    public JButton getJBIngresar() {
        if (jbIngresar == null) {
            jbIngresar = new GlossyButton("INGRESAR",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
            jbIngresar.setName("Alta");
        	jbIngresar.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/add.png")));
            jbIngresar.setBounds(new java.awt.Rectangle(10,25,90,25));
            jbIngresar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jbIngresar.setInputMap(0, map);
        }
        return jbIngresar;
    }

    public JButton getJBModificar() {
        if (jbModif == null) {
            jbModif = new GlossyButton("MODIFICAR",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
            jbModif.setName("Modificar");
            jbModif.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/edit.png")));
            jbModif.setBounds(new java.awt.Rectangle(110,25,90,25));
            jbModif.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jbModif.setInputMap(0, map);
        }
        return jbModif;
    }
    
    public JTextField getJTFBuscador() {
        if (jtfNombre == null) {
            jtfNombre = new JTextField();
            jtfNombre.setBounds(new Rectangle(67,30,100,22));
        }
        return jtfNombre;
    }

    public JButton getJBEliminar() {
        if (jbEliminar == null) {
        	jbEliminar = new GlossyButton("ELIMINAR",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
        	jbEliminar.setName("Baja");
        	jbEliminar.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/delete.png")));
        	jbEliminar.setBounds(new java.awt.Rectangle(210,25,90,25));
            jbEliminar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jbEliminar.setInputMap(0, map);
        }
        return jbEliminar;
    }
    
    public JButton getJBImprimir() {
        if (jbImprimir == null) {
        	jbImprimir = new GlossyButton("IMPRIMIR",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
        	jbImprimir.setName("Imprimir");
        	jbImprimir.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/printer.png")));
        	jbImprimir.setBounds(new java.awt.Rectangle(310,25,90,25));
        	jbImprimir.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        	jbImprimir.setInputMap(0, map);
        }
        return jbImprimir;
    }
    
    public JButton getJBECuenta() {
        if (jbECuenta == null) {
        	jbECuenta = new GlossyButton("E. CUENTA",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
        	jbECuenta.setName("Cuenta");
        	jbECuenta.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/deudor.png")));
        	jbECuenta.setBounds(new java.awt.Rectangle(410,25,90,25));
        	jbECuenta.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        	jbECuenta.setInputMap(0, map);
        }
        return jbECuenta;
    }
    
    public JButton getJBDeudas() {
        if (jbDeudas == null) {
        	jbDeudas = new GlossyButton("DEUDAS",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
        	jbDeudas.setName("Deudas");
        	jbDeudas.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/cuentas.png")));
        	jbDeudas.setBounds(new java.awt.Rectangle(590,305,100,25));
        	jbDeudas.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        	jbDeudas.setInputMap(0, map);
        }
        return jbDeudas;
    }

    public JButton getJBAceptar() {
        if (jbAceptar == null) {
            jbAceptar = new GlossyButton("ACEPTAR",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
            jbAceptar.setName("Aceptar");
            jbAceptar.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/check.png")));
            jbAceptar.setBounds(new Rectangle(240,470,100,30));
            jbAceptar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jbAceptar.setInputMap(0, map);
        }
        return jbAceptar;
    }

    public JButton getJBCancelar() {
        if (jbCancelar == null) {
            jbCancelar = new GlossyButton("CANCELAR",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
            jbCancelar.setName("Cancelar");
            jbCancelar.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/cancel.png")));
            jbCancelar.setBounds(new Rectangle(390,470,100,30));
            jbCancelar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jbCancelar.setInputMap(0, map);
        }
        return jbCancelar;
    }

    public void setKeyListener(KeyListener lis) {
        jtfNombre.addKeyListener(lis);
    }

    public void setListSelectionListener(ListSelectionListener lis) {
        tabla.getSelectionModel().addListSelectionListener(lis);
    }

    public void setActionListeners (ActionListener lis) {
        jbIngresar.addActionListener(lis);
        jbModif.addActionListener(lis);
        jbEliminar.addActionListener(lis);
        jbImprimir.addActionListener(lis);
        jbAceptar.addActionListener(lis);
        jbCancelar.addActionListener(lis);
        jbECuenta.addActionListener(lis);
        jbDeudas.addActionListener(lis);
    }

    public void repaint() {
        super.repaint();
    }
    
    public void actualizarTabla(){
        jpPpal.remove(getJPDatos());
        jpDatos = null;
        tabla = null;
        modTabla = null;
        jspDatos = null;
        jpPpal.add(getJPDatos(), null);
    }
}
