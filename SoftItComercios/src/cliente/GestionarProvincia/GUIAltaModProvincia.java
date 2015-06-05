/*
 * GUIAltaModProvincia.java
 *
 * Created on 7 de marzo de 2007, 19:29
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package cliente.GestionarProvincia;

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

import persistencia.domain.Provincia;
import cliente.Imagenes.Botones.ButtonType;
import cliente.Imagenes.Botones.GlossyButton;
import cliente.Imagenes.util.Theme;
import cliente.Principal.GUIPrincipal;
import cliente.Utils.JPanel_Whit_Image;
import cliente.Utils.TransparentPanel;

import common.Utils;

public class GUIAltaModProvincia extends JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel jpPpal = null;			    private JPanel jpDatos = null;
	private JButton jbAceptar = null;		    private JButton jbCancelar = null;
	private JLabel jlNombre = null;			    
    private JTextField jtfNombre = null;	   
    private Provincia prov = null;
    private InputMap map = new InputMap();
        
    public GUIAltaModProvincia(JDialog guiPadre) {
    	super(guiPadre);
    	this.setSize(new java.awt.Dimension(540,180));
        this.setTitle("Nueva Provincia");
        this.setLocationRelativeTo(guiPadre);
        this.setResizable(false);
        this.setContentPane(getJPPpal());
        this.setModal(true);
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "pressed");
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "released");
    }
    
    public GUIAltaModProvincia(Provincia p,JDialog guiPadre) {
        super(guiPadre);
        this.prov = p;
        this.setSize(new java.awt.Dimension(540,180));
        this.setTitle("Modificar Provincia");
        this.setLocationRelativeTo(guiPadre);
        this.setResizable(false);
        this.setContentPane(getJPPpal());
        this.setModal(true);
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "pressed");
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "released");
    }
   
    private JPanel getJPPpal() {
        if (jpPpal == null) {
            jpPpal = new JPanel_Whit_Image("/cliente/Imagenes/Imagenes/background.jpg");
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
            jlNombre.setBounds(new Rectangle(10,40,140,26));
            jlNombre.setHorizontalAlignment(SwingConstants.RIGHT);
            jlNombre.setForeground(Utils.colorTexto);
            jlNombre.setFont(Utils.FuenteBasica());
            jpDatos = new TransparentPanel();
            jpDatos.setLayout(null);
            jpDatos.setBorder(Utils.crearTituloYBorde("DATOS DE LA PROVINCIA"));
            jpDatos.setBounds(new Rectangle(15,15,500,80));
            jpDatos.add(jlNombre, null);
            jpDatos.add(getJTFNombre(), null);
            if (prov!=null) {
                jtfNombre.setText(prov.getNombre());
            }
          
        }
        return jpDatos;
    }

    public JTextField getJTFNombre() {
        if (jtfNombre == null) {
            jtfNombre = new JTextField();
            jtfNombre.setBounds(new Rectangle(160,40,320,26));
            jtfNombre.setFont(Utils.FuenteCampos());
        }
        return jtfNombre;
    }
    
    public JButton getJBAceptar() {
        if (jbAceptar == null) {
            jbAceptar = new GlossyButton("ACEPTAR",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
            jbAceptar.setBounds(new Rectangle(35,105,200,40));
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
            jbCancelar.setBounds(new Rectangle(305,105,200,40));
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
    }
}
