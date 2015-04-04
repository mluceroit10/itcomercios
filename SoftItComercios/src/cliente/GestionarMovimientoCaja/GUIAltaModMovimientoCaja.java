package cliente.GestionarMovimientoCaja;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Vector;

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

import persistencia.domain.MovimientoCaja;
import cliente.LimitadorNroMax;
import cliente.LimitadorPrecio;
import cliente.Imagenes.Botones.ButtonType;
import cliente.Imagenes.Botones.GlossyButton;
import cliente.Imagenes.util.Theme;
import cliente.Principal.GUIPrincipal;
import cliente.Utils.JPanel_Whit_Image;
import cliente.Utils.TransparentPanel;

import com.toedter.calendar.JDateChooser;
import common.Utils;

public class GUIAltaModMovimientoCaja extends JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel jpPpal = null;			    private JPanel jpDatos = null;  
    private JButton jbAceptar = null;	    	private JButton jbCancelar = null;
    private JButton jbFactura = null;
    private JLabel jlNroRecibo = null;		    private JLabel jlFecha = null;
	private JLabel jlTipoMov = null;			private JLabel jlImporte = null;
	private JLabel jlDescr = null;				private JLabel jLNroCheque = null;
	private JLabel jlFormaPago = null;			private JLabel jlFactura = null;
	private JLabel jlTipoFact = null;
    private JTextField jtfNroRecibo = null;	    private JTextField jtfImporte = null;
    private JTextField jTFNroCheque = null;		private JTextField jtfDescr = null;
    private JTextField jtfFactura = null;
    private JComboBox jcbTipoMovim = null;    	private JComboBox jcbConFact = null;
    private JComboBox jCBFormaPago = null;		private JComboBox jcbTipoFact = null;
    private JDateChooser jDateChooserFecha = null;
    private MovimientoCaja mcDTO = null;
    public Vector servicios;					public Vector cursos;
   	public int nroAsignado=0;
	private InputMap map = new InputMap();
   	
    public GUIAltaModMovimientoCaja(int nroCod,JDialog guiPadre) {
    	super(guiPadre);
    	nroAsignado=nroCod;
        this.setSize(new java.awt.Dimension(630,300));
        this.setTitle("Nuevo Movimiento de Caja");
        this.setLocationRelativeTo(guiPadre);
        this.setResizable(false);
        this.setContentPane(getJPPpal());
        this.setModal(true);
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "pressed");
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "released");
    }
    
    public GUIAltaModMovimientoCaja(MovimientoCaja lp,JDialog guiPadre) {
        super(guiPadre);
        this.mcDTO = lp;
        this.setSize(new java.awt.Dimension(630,300));
        this.setTitle("Modificar Movimiento de Caja");
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
            jpPpal.setSize(new java.awt.Dimension(630,300));
            jpPpal.add(getJPDatos(),null);
            jpPpal.add(getJBAceptar(), null);
            jpPpal.add(getJBCancelar(), null);
            jpPpal.setBackground(Utils.colorFondo);
        }
        return jpPpal;
    }
        
    public JPanel getJPDatos() {
        if (jpDatos == null) {
            jlNroRecibo = new JLabel("CODIGO (*)");
            jlNroRecibo.setForeground(Utils.colorTexto);
            jlNroRecibo.setBounds(new Rectangle(10,30,130,15));
            jlNroRecibo.setHorizontalAlignment(SwingConstants.RIGHT);
            jlFecha = new JLabel();
            jlFecha.setBounds(new java.awt.Rectangle(310,30,95,15));
            jlFecha.setText("FECHA (*) ");
            jlFecha.setForeground(Utils.colorTexto);
            jlFecha.setHorizontalAlignment(SwingConstants.RIGHT);
            jlTipoMov = new JLabel();
            jlTipoMov.setBounds(new java.awt.Rectangle(10,62,130,15));
            jlTipoMov.setText("TIPO MOVIMIENTO (*) ");
            jlTipoMov.setForeground(Utils.colorTexto);
            jlTipoMov.setHorizontalAlignment(SwingConstants.RIGHT);
            jlFormaPago = new JLabel();
            jlFormaPago.setBounds(new java.awt.Rectangle(10,94,130,15));
            jlFormaPago.setText("FORMA DE PAGO (*) ");
            jlFormaPago.setForeground(Utils.colorTexto);
            jlFormaPago.setHorizontalAlignment(SwingConstants.RIGHT);
            jlImporte = new JLabel();
            jlImporte.setBounds(new java.awt.Rectangle(310,62,95,15));
            jlImporte.setText("IMPORTE (*) ");
            jlImporte.setForeground(Utils.colorTexto);
            jlImporte.setHorizontalAlignment(SwingConstants.RIGHT);
            jlDescr = new JLabel();
            jlDescr.setBounds(new java.awt.Rectangle(10,126,130,15));
            jlDescr.setText("DESCRIPCION (*) ");
            jlDescr.setForeground(Utils.colorTexto);
            jlDescr.setHorizontalAlignment(SwingConstants.RIGHT);
            jLNroCheque = new JLabel();
            jLNroCheque.setBounds(new java.awt.Rectangle(310,94,95,15));
            jLNroCheque.setText("NRO. CHEQUE");
            jLNroCheque.setForeground(Utils.colorTexto);
            jLNroCheque.setHorizontalAlignment(SwingConstants.RIGHT);
            jlFactura = new JLabel();
            jlFactura.setBounds(new java.awt.Rectangle(10,158,130,15));
            jlFactura.setText("FACTURA");
            jlFactura.setForeground(Utils.colorTexto);
            jlFactura.setHorizontalAlignment(SwingConstants.RIGHT);
            jlTipoFact = new JLabel();
            jlTipoFact.setBounds(new java.awt.Rectangle(200,158,40,15));
            jlTipoFact.setText("TIPO");
            jlTipoFact.setForeground(Utils.colorTexto);
            jlTipoFact.setHorizontalAlignment(SwingConstants.RIGHT);
            jpDatos = new TransparentPanel();
            jpDatos.setLayout(null);
            jpDatos.setBorder(javax.swing.BorderFactory.createTitledBorder(Utils.b, "DATOS DEL MOVIMIENTO DE CAJA", 
                    javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, 
                    javax.swing.border.TitledBorder.DEFAULT_POSITION, 
                    new java.awt.Font("Dialog", java.awt.Font.BOLD, 14), Utils.colorTexto));
            jpDatos.setBounds(new java.awt.Rectangle(15,15,600,195));
            jpDatos.add(jlNroRecibo, null);
            jpDatos.add(jlFecha, null);
            jpDatos.add(jlTipoMov, null);
            jpDatos.add(jlImporte, null);
            jpDatos.add(jlFormaPago, null);
            jpDatos.add(jlFactura, null);
            jpDatos.add(jlDescr, null);
            jpDatos.add(jLNroCheque, null);
            jpDatos.add(jlTipoFact, null);
            jpDatos.add(getJTFCodigo(), null);
            jpDatos.add(getJCTipoMov(), null);
            jpDatos.add(getJDateChooserFecha(), null);
            jpDatos.add(getJTFImporte(), null);
            jpDatos.add(getJTFDescr(), null);
            jpDatos.add(getJTFFactura(), null);
            jpDatos.add(getJTFNroCheque(), null);
            jpDatos.add(getJCBFormaPago(), null);
            jpDatos.add(getJBBuscarFact(), null);
            jpDatos.add(getJCConFact(), null);
            jpDatos.add(getJCTipoFact(), null);
            if (mcDTO!=null) {
            	jtfNroRecibo.setText(String.valueOf(mcDTO.getCodigo()));
                jDateChooserFecha.setDate(mcDTO.getFecha());
                if(mcDTO.getTipoMovimiento()==1){
                	jcbTipoMovim.setSelectedItem("ENTRADA");
                }else{
                	jcbTipoMovim.setSelectedItem("SALIDA"); 
                }
                jCBFormaPago.setSelectedItem(mcDTO.getFormaPago());
                if(mcDTO.getFormaPago().compareTo("Cheque")==0)
                	jTFNroCheque.setText(String.valueOf(mcDTO.getNroCheque()));
                jtfImporte.setText(String.valueOf(mcDTO.getImporte()));
                jtfDescr.setText(mcDTO.getDescripcion());
                if(mcDTO.isConFactura()){
                	jcbConFact.setSelectedItem("SI");
                	jcbTipoFact.setSelectedItem(mcDTO.getTipoFactura());
                	jtfFactura.setText(Utils.nroFact(mcDTO.getFactura().getNroFactura()));	
                }else{
                	jcbConFact.setSelectedItem("NO");
                }
                jcbTipoFact.setEnabled(false);
                jcbConFact.setEnabled(false);
            }
        }
        return jpDatos;
    }

    public JTextField getJTFCodigo() {
        if (jtfNroRecibo == null) {
        	jtfNroRecibo = new JTextField();
        	jtfNroRecibo.setBounds(new java.awt.Rectangle(145,30,150,22));
        	jtfNroRecibo.setDocument(new LimitadorNroMax(jtfNroRecibo,9));
        	jtfNroRecibo.setText(String.valueOf(nroAsignado));
        }
        return jtfNroRecibo;
    }
    
    public JTextField getJTFImporte() {
        if (jtfImporte == null) {
        	jtfImporte = new JTextField();
        	jtfImporte.setBounds(new java.awt.Rectangle(415,62,170,22));
        	jtfImporte.setDocument(new LimitadorPrecio(jtfImporte));
        }
        return jtfImporte;
    }
    
    public JTextField getJTFDescr() {
        if (jtfDescr == null) {
        	jtfDescr = new JTextField();
        	jtfDescr.setBounds(new java.awt.Rectangle(145,126,440,22));
        	jtfDescr.setBackground(new Color(255,255,255));
        	jtfDescr.setForeground(java.awt.Color.black);
        }
        return jtfDescr;
    }
    
    public JTextField getJTFFactura() {
        if (jtfFactura == null) {
        	jtfFactura = new JTextField();
        	jtfFactura.setBounds(new java.awt.Rectangle(390,158,110,22));
        	jtfFactura.setBackground(new Color(255,255,255));
        	jtfFactura.setForeground(java.awt.Color.black);
        	jtfFactura.setDisabledTextColor(Utils.colorTextoDisabled);
        	jtfFactura.setEnabled(false);
        	
        }
        return jtfFactura;
    }
    
    public JButton getJBBuscarFact() {
        if (jbFactura == null) {
        	jbFactura = new GlossyButton("BUSCAR",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
        	jbFactura.setBounds(new java.awt.Rectangle(505,158,80,22));
        	jbFactura.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        	jbFactura.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/find.png")));
        	jbFactura.setEnabled(false);
        	jbFactura.setInputMap(0, map);
        }
        return jbFactura;
    }
    
    public JComboBox getJCTipoMov() {
		if (jcbTipoMovim==null){
			jcbTipoMovim=new JComboBox();
			jcbTipoMovim.setBounds(new java.awt.Rectangle(145,62,150,22));
			jcbTipoMovim.setBackground(new Color(255,255,255));
			jcbTipoMovim.setForeground(java.awt.Color.black);
			jcbTipoMovim.addItem("ENTRADA");
			jcbTipoMovim.addItem("SALIDA");
			
		}
		return jcbTipoMovim;
	}
    
    public JComboBox getJCConFact() {
		if (jcbConFact==null){
			jcbConFact=new JComboBox();
			jcbConFact.setBounds(new java.awt.Rectangle(145,158,50,22));
			jcbConFact.setBackground(new Color(255,255,255));
			jcbConFact.setForeground(java.awt.Color.black);
			jcbConFact.addItem("NO");
			jcbConFact.addItem("SI");
			
		}
		return jcbConFact;
	}
    
    public JComboBox getJCTipoFact() {
		if (jcbTipoFact==null){
			jcbTipoFact=new JComboBox();
			jcbTipoFact.setBounds(new java.awt.Rectangle(243,158,130,22));
			jcbTipoFact.setBackground(new Color(255,255,255));
			jcbTipoFact.setForeground(java.awt.Color.black);
			jcbTipoFact.setEnabled(false);
			jcbTipoFact.addItem("Factura Cliente A");
			jcbTipoFact.addItem("Factura Cliente B");
			//jcbTipoFact.addItem("Remito Cliente");
			jcbTipoFact.addItem("Factura Proveedor");
		}
		return jcbTipoFact;
	}
    
    public JDateChooser getJDateChooserFecha() {
		if (jDateChooserFecha == null) {
			jDateChooserFecha = new JDateChooser("dd - MMMMM - yyyy",false);
			jDateChooserFecha.setBounds(new java.awt.Rectangle(415,30,170,22));
		}
		return jDateChooserFecha;
	}
        
    public JButton getJBAceptar() {
        if (jbAceptar == null) {
            jbAceptar = new GlossyButton("ACEPTAR",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
            jbAceptar.setBounds(new java.awt.Rectangle(180,230,100,30));
            jbAceptar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jbAceptar.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/check.png")));
            jbAceptar.setInputMap(0, map);
        }
        return jbAceptar;
    }
    
    public JButton getJBCancelar() {
        if (jbCancelar == null) {
            jbCancelar = new GlossyButton("CANCELAR",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
            jbCancelar.setBounds(new java.awt.Rectangle(330,230,100,30));
            jbCancelar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jbCancelar.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/cancel.png")));
            jbCancelar.setInputMap(0, map);
        }
        return jbCancelar;
    }
    
    public void setActionListeners(ActionListener lis) {
        jbAceptar.addActionListener(lis);
        jbCancelar.addActionListener(lis);
        jbFactura.addActionListener(lis);
        jCBFormaPago.addActionListener(lis);
        jcbConFact.addActionListener(lis);
        jcbTipoFact.addActionListener(lis);
    }

	public JTextField getJTFNroCheque() {
		if (jTFNroCheque == null) {
			jTFNroCheque = new JTextField();
			jTFNroCheque.setBounds(new java.awt.Rectangle(415,94,170,22));
			jTFNroCheque.setDocument(new LimitadorNroMax(jTFNroCheque,10));
			jTFNroCheque.setDisabledTextColor(Utils.colorTextoDisabled);
			jTFNroCheque.setEnabled(false);
		}
		return jTFNroCheque;
	}
	
	public JComboBox getJCBFormaPago() {
		if (jCBFormaPago == null) {
			jCBFormaPago  = new JComboBox();
			jCBFormaPago.setBounds(new java.awt.Rectangle(145,94,150,22));
			jCBFormaPago.setBackground(new Color(255,255,255));
			jCBFormaPago.setForeground(java.awt.Color.black);
			jCBFormaPago.addItem("EFECTIVO");
			jCBFormaPago.addItem("TICKET");
			jCBFormaPago.addItem("CHEQUE");
		}
		return jCBFormaPago;
	}

	public void setFactura(String string) {
		 jtfFactura.setText(string);
	}
   
}