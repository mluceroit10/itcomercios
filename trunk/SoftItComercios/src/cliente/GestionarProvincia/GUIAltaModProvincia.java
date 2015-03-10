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

import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import persistencia.domain.Provincia;

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
    	this.setSize(new java.awt.Dimension(340,175));
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
        this.setSize(new java.awt.Dimension(340,175));
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
            jpDatos = new JPanel();
            jpDatos.setLayout(null);
            jpDatos.setSize(new java.awt.Dimension(300,70));
            jpDatos.setBorder(javax.swing.BorderFactory.createTitledBorder(Utils.b, "Datos de la Provincia", 
                    javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, 
                    javax.swing.border.TitledBorder.DEFAULT_POSITION, 
                    new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), java.awt.Color.black));
            jpDatos.setBounds(new Rectangle(15,15,300,70));
            jpDatos.add(jlNombre, null);
            jpDatos.add(getJTFNombre(), null);
            if (prov!=null) {
                jtfNombre.setText(prov.getNombre());
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
    
    public JButton getJBAceptar() {
        if (jbAceptar == null) {
            jbAceptar = new JButton();
            jbAceptar.setBounds(new Rectangle(50,105,100,30));
            jbAceptar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jbAceptar.setText("Aceptar");
            jbAceptar.setInputMap(0, map);
        }
        return jbAceptar;
    }
    
    public JButton getJBCancelar() {
        if (jbCancelar == null) {
            jbCancelar = new JButton();
            jbCancelar.setBounds(new Rectangle(180,105,100,30));
            jbCancelar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jbCancelar.setText("Cancelar");
            jbCancelar.setInputMap(0, map);
        }
        return jbCancelar;
    }
    
    public void setActionListeners(ActionListener lis) {
        jbAceptar.addActionListener(lis);
        jbCancelar.addActionListener(lis);
    }
}
