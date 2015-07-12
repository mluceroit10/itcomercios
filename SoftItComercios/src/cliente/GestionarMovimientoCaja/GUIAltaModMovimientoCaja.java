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
        this.setSize(new java.awt.Dimension(855,390));
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
        this.setSize(new java.awt.Dimension(855,390));
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
            jpPpal.setSize(new java.awt.Dimension(855,390));
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
            jlNroRecibo.setFont(Utils.FuenteBasica());
            jlNroRecibo.setBounds(new Rectangle(10,40,220,26));
            jlNroRecibo.setHorizontalAlignment(SwingConstants.RIGHT);
            jlFecha = new JLabel();
            jlFecha.setBounds(new java.awt.Rectangle(440,40,140,26));
            jlFecha.setText("FECHA (*)");
            jlFecha.setForeground(Utils.colorTexto);
            jlFecha.setFont(Utils.FuenteBasica());  
            jlFecha.setHorizontalAlignment(SwingConstants.RIGHT);
            jlTipoMov = new JLabel();
            jlTipoMov.setBounds(new java.awt.Rectangle(10,75,220,26));
            jlTipoMov.setText("TIPO MOVIMIENTO (*)");
            jlTipoMov.setForeground(Utils.colorTexto);
            jlTipoMov.setFont(Utils.FuenteBasica());
            jlTipoMov.setHorizontalAlignment(SwingConstants.RIGHT);
            jlFormaPago = new JLabel();
            jlFormaPago.setBounds(new java.awt.Rectangle(10,110,220,26));
            jlFormaPago.setText("FORMA DE PAGO (*)");
            jlFormaPago.setForeground(Utils.colorTexto);
            jlFormaPago.setFont(Utils.FuenteBasica());
            jlFormaPago.setHorizontalAlignment(SwingConstants.RIGHT);
            jlImporte = new JLabel();
            jlImporte.setBounds(new java.awt.Rectangle(440,75,140,26));
            jlImporte.setText("IMPORTE (*)");
            jlImporte.setForeground(Utils.colorTexto);
            jlImporte.setFont(Utils.FuenteBasica());
            jlImporte.setHorizontalAlignment(SwingConstants.RIGHT);
            jlDescr = new JLabel();
            jlDescr.setBounds(new java.awt.Rectangle(10,145,220,26));
            jlDescr.setText("DESCRIPCION (*)");
            jlDescr.setForeground(Utils.colorTexto);
            jlDescr.setFont(Utils.FuenteBasica());
            jlDescr.setHorizontalAlignment(SwingConstants.RIGHT);
            jLNroCheque = new JLabel();
            jLNroCheque.setBounds(new java.awt.Rectangle(440,110,140,26));
            jLNroCheque.setText("NRO. CHEQUE");
            jLNroCheque.setForeground(Utils.colorTexto);
            jLNroCheque.setFont(Utils.FuenteBasica());
            jLNroCheque.setHorizontalAlignment(SwingConstants.RIGHT);
            jlFactura = new JLabel();
            jlFactura.setBounds(new java.awt.Rectangle(10,180,220,26));
            jlFactura.setText("FACTURA");
            jlFactura.setForeground(Utils.colorTexto);
            jlFactura.setFont(Utils.FuenteBasica());
            jlFactura.setHorizontalAlignment(SwingConstants.RIGHT);
            jlTipoFact = new JLabel();
            jlTipoFact.setBounds(new java.awt.Rectangle(10,215,220,26));
            jlTipoFact.setText("TIPO");
            jlTipoFact.setForeground(Utils.colorTexto);
            jlTipoFact.setFont(Utils.FuenteBasica());
            jlTipoFact.setHorizontalAlignment(SwingConstants.RIGHT);
            jpDatos = new TransparentPanel();
            jpDatos.setLayout(null);
            jpDatos.setBorder(Utils.crearTituloYBorde("DATOS DEL MOVIMIENTO DE CAJA"));
            jpDatos.setBounds(new java.awt.Rectangle(15,15,820,260));
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
        	jtfNroRecibo.setBounds(new java.awt.Rectangle(240,40,200,26));
        	jtfNroRecibo.setDocument(new LimitadorNroMax(jtfNroRecibo,9));
        	jtfNroRecibo.setText(String.valueOf(nroAsignado));
        	jtfNroRecibo.setFont(Utils.FuenteCampos());
        }
        return jtfNroRecibo;
    }
    
    public JTextField getJTFImporte() {
        if (jtfImporte == null) {
        	jtfImporte = new JTextField();
        	jtfImporte.setBounds(new java.awt.Rectangle(590,75,210,26));
        	jtfImporte.setDocument(new LimitadorPrecio(jtfImporte));
        	jtfImporte.setFont(Utils.FuenteCampos());
        }
        return jtfImporte;
    }
    
    public JTextField getJTFDescr() {
        if (jtfDescr == null) {
        	jtfDescr = new JTextField();
        	jtfDescr.setBounds(new java.awt.Rectangle(240,145,560,26));
        	jtfDescr.setBackground(new Color(255,255,255));
        	jtfDescr.setForeground(java.awt.Color.black);
        	jtfDescr.setFont(Utils.FuenteCampos());
        }
        return jtfDescr;
    }
    
    public JTextField getJTFFactura() {
        if (jtfFactura == null) {
        	jtfFactura = new JTextField();
        	jtfFactura.setBounds(new java.awt.Rectangle(330,180,200,26));
        	jtfFactura.setBackground(new Color(255,255,255));
        	jtfFactura.setForeground(java.awt.Color.black);
        	jtfFactura.setDisabledTextColor(Utils.colorTextoDisabled);
        	jtfFactura.setEnabled(false);
        	jtfFactura.setFont(Utils.FuenteCampos());
        	
        }
        return jtfFactura;
    }
    
    public JButton getJBBuscarFact() {
        if (jbFactura == null) {
        	jbFactura = new GlossyButton("BUSCAR",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
        	jbFactura.setBounds(new java.awt.Rectangle(570,215,150,26));
        	jbFactura.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        	jbFactura.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/find.png")));
        	jbFactura.setEnabled(false);
        	jbFactura.setInputMap(0, map);
        	jbFactura.setFont(Utils.FuenteBotonesChicos());
        	jbFactura.setMnemonic('B');
        }
        return jbFactura;
    }
    
    public JComboBox getJCTipoMov() {
		if (jcbTipoMovim==null){
			jcbTipoMovim=new JComboBox();
			jcbTipoMovim.setBounds(new java.awt.Rectangle(240,75,200,26));
			jcbTipoMovim.setBackground(new Color(255,255,255));
			jcbTipoMovim.setForeground(java.awt.Color.black);
			jcbTipoMovim.addItem("ENTRADA");
			jcbTipoMovim.addItem("SALIDA");
			jcbTipoMovim.setFont(Utils.FuenteCampos());
		}
		return jcbTipoMovim;
	}
    
    public JComboBox getJCConFact() {
		if (jcbConFact==null){
			jcbConFact=new JComboBox();
			jcbConFact.setBounds(new java.awt.Rectangle(240,180,70,26));
			jcbConFact.setBackground(new Color(255,255,255));
			jcbConFact.setForeground(java.awt.Color.black);
			jcbConFact.addItem("NO");
			jcbConFact.addItem("SI");
			jcbConFact.setFont(Utils.FuenteCampos());
		}
		return jcbConFact;
	}
    
    public JComboBox getJCTipoFact() {
		if (jcbTipoFact==null){
			jcbTipoFact=new JComboBox();
			jcbTipoFact.setBounds(new java.awt.Rectangle(240,215,310,26));
			jcbTipoFact.setBackground(new Color(255,255,255));
			jcbTipoFact.setForeground(java.awt.Color.black);
			jcbTipoFact.setEnabled(false);
			jcbTipoFact.addItem("Factura Cliente-Tipo A");
			jcbTipoFact.addItem("Factura Cliente-Tipo B");
			//jcbTipoFact.addItem("Remito Cliente");
			jcbTipoFact.addItem("Factura Proveedor");
			jcbTipoFact.setFont(Utils.FuenteCampos());
		}
		return jcbTipoFact;
	}
    
    public JDateChooser getJDateChooserFecha() {
		if (jDateChooserFecha == null) {
			jDateChooserFecha = new JDateChooser("dd - MMM - yyyy",false);
			jDateChooserFecha.setBounds(new java.awt.Rectangle(590,40,210,26));
			jDateChooserFecha.getSpinner().setFont(Utils.FuenteFechas());
		}
		return jDateChooserFecha;
	}
        
    public JButton getJBAceptar() {
        if (jbAceptar == null) {
            jbAceptar = new GlossyButton("ACEPTAR",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
            jbAceptar.setBounds(new java.awt.Rectangle(177,310,200,40));
            jbAceptar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jbAceptar.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/check.png")));
            jbAceptar.setInputMap(0, map);
            jbAceptar.setFont(Utils.FuenteBotonesGrandes());
            jbAceptar.setMnemonic('A');
        }
        return jbAceptar;
    }
    
    public JButton getJBCancelar() {
        if (jbCancelar == null) {
            jbCancelar = new GlossyButton("CANCELAR",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);;
            jbCancelar.setBounds(new java.awt.Rectangle(477,310,200,40));
            jbCancelar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jbCancelar.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/cancel.png")));
            jbCancelar.setInputMap(0, map);
            jbCancelar.setFont(Utils.FuenteBotonesGrandes());
            jbCancelar.setMnemonic('N');
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
			jTFNroCheque.setBounds(new java.awt.Rectangle(590,110,210,26));
			jTFNroCheque.setDocument(new LimitadorNroMax(jTFNroCheque,10));
			jTFNroCheque.setDisabledTextColor(Utils.colorTextoDisabled);
			jTFNroCheque.setEnabled(false);
			jTFNroCheque.setFont(Utils.FuenteCampos());
		}
		return jTFNroCheque;
	}
	
	public JComboBox getJCBFormaPago() {
		if (jCBFormaPago == null) {
			jCBFormaPago  = new JComboBox();
			jCBFormaPago.setBounds(new java.awt.Rectangle(240,110,200,26));
			jCBFormaPago.setBackground(new Color(255,255,255));
			jCBFormaPago.setForeground(java.awt.Color.black);
			jCBFormaPago.addItem("EFECTIVO");
			jCBFormaPago.addItem("TICKET");
			jCBFormaPago.addItem("CHEQUE");
			jCBFormaPago.setFont(Utils.FuenteCampos());
		}
		return jCBFormaPago;
	}

	public void setFactura(String string) {
		 jtfFactura.setText(string);
	}
   
}