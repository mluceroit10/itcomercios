package cliente.Principal;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import cliente.Imagenes.Botones.ButtonType;
import cliente.Imagenes.Botones.GlossyButton;
import cliente.Imagenes.util.Theme;
import cliente.Utils.JPanel_Whit_Image;
import cliente.Utils.TransparentPanel;

import common.Utils;

public class GUIPrincipal extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;
	//PANEL PRINCIPAL
	private JPanel jContentPane = null;  
	private JButton jButtonSalir = null;
	//BARRA MENU
	private JMenuBar menuPrinc=null;
	private JMenu archivo=null;
	private JMenu gestionar=null;
	private JMenu baseDatos=null;
	
	private JMenuItem archivoSalir=null;
	private JMenuItem infoProg=null;
	private JMenuItem gestionarProvincia=null;  
	private JMenuItem gestionarLocalidad=null;
	private JMenuItem backupBD=null;
	private JMenuItem modoAvanzado=null;
	private JMenuItem infoComercio=null;
	
	private Border b= javax.swing.BorderFactory.createLineBorder(new Color(104,34,139),2);
	//PANEL ACCESOS RAPIDOS
	private JPanel jPanelAccesosProductos=null;
	private JPanel jPanelAccesosCliente=null;
	private JPanel jPanelAccesosFactProv=null;
	private JPanel jPanelAccesosContables=null;
	private JPanel jPanelAccesosRemitoCte=null; 
	private JPanel jPanelAccesosFactCte=null;
	private JPanel jPanelLibroIva=null;
	
	private JButton jBProductos=null;
	private JButton jBGestionProveedores=null;
	private JButton jBGestionClientes=null;
	private JButton jBFacturaCliente=null;
	private JButton jBRemitoCliente=null;
	private JButton jBGestionarMC=null;
	private JButton jBPlanillaES=null;
	private JButton jBTodasFactProv=null;
	private JButton jBTodosRemitosCliente=null;
	private JButton jBFacturaProveedor=null;
	private JButton jBTodasFactCliente=null;
	private JButton jBLibroIva=null;
	Color colorFuenteMenu = new Color(0,0,128);
	private InputMap map = new InputMap();
	
	public GUIPrincipal() throws Exception{
		super();
		setSize(new java.awt.Dimension(1350,670));
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle("Sistema Informático ''itComercios''");
		setIconImage(Toolkit.getDefaultToolkit().getImage(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/itcomercio.png")));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setContentPane(getJContentPane());
		
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "pressed");
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "released");
		init();
	}

	private JPanel getJContentPane() {
		jContentPane = new JPanel_Whit_Image("/cliente/Imagenes/Imagenes/background.jpg");
		jContentPane.setBackground(Utils.colorFondo);
		jContentPane.setLayout(null);
		jContentPane.add(getJPanelAccesosProductos(), null);
		
		jContentPane.add(getJPanelAccesosContables(), null);
		jContentPane.add(getJPanelAccesosFactProveedor(), null);
		jContentPane.add(getJPanelAccesosRemitoCliente(), null);
		
		jContentPane.add(getJPanelAccesosFactCliente(), null);
		jContentPane.add(getJPanelLibroIva(),null);
		jContentPane.add(getJPanelAccesosClientes(), null);
		jContentPane.add(getJButtonSalir(),null);
		setJMenuBar(getJBarraMenu());
		return jContentPane;
	}

	private JMenuBar getJBarraMenu(){
		if (menuPrinc==null){
			menuPrinc = new JMenuBar();
			archivo = new JMenu("Información        ");
			archivo.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/informacion.png")));
			archivo.setForeground(colorFuenteMenu);
			archivo.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 28));
			archivo.setMnemonic('I');
			gestionar = new JMenu("Gestionar             ");
			gestionar.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/reclamo.png")));
	        gestionar.setForeground(colorFuenteMenu);
	        gestionar.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 28));
	        gestionar.setMnemonic('G');
	        baseDatos = new JMenu("Datos         ");
	        baseDatos.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/data.png")));
	        baseDatos.setForeground(colorFuenteMenu);
	        baseDatos.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 28));
	        baseDatos.setMnemonic('D');
	        menuPrinc.add(archivo);
	        menuPrinc.add(gestionar);
	        menuPrinc.add(baseDatos);
	        infoComercio= new JMenuItem("Del Comercio");
	        infoComercio.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/about_comerce.png")));
	        infoComercio.setForeground(colorFuenteMenu);
	        infoComercio.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 26));
	        infoProg = new JMenuItem("Del Programa        ");
	        infoProg.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/about_program.png")));
			infoProg.setForeground(colorFuenteMenu);
			infoProg.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 26));
	        archivoSalir= new JMenuItem("Salir        ");
	        archivoSalir.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/exit.png")));
	        archivoSalir.setForeground(colorFuenteMenu);
	        archivoSalir.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 26));
	        gestionarProvincia= new JMenuItem("Provincia");
	        gestionarProvincia.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/province.png")));
	        gestionarProvincia.setForeground(colorFuenteMenu);
	        gestionarProvincia.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 26));
	        gestionarLocalidad= new JMenuItem("Localidad");
	        gestionarLocalidad.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/city.png")));
	        gestionarLocalidad.setForeground(colorFuenteMenu);
	        gestionarLocalidad.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 26));
	        backupBD= new JMenuItem("BackUP de Base de Datos");
	        backupBD.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/database.png")));
	        backupBD.setForeground(colorFuenteMenu);
	        backupBD.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 26));
	        modoAvanzado= new JMenuItem("Modo Avanzado");
	        modoAvanzado.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/edit_notificaciones.png")));
	        modoAvanzado.setForeground(colorFuenteMenu);
	        modoAvanzado.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 26));
	        archivo.add(infoComercio);
	        archivo.add(infoProg);
	        archivo.add(archivoSalir);
	        gestionar.add(gestionarProvincia);
	        gestionar.add(gestionarLocalidad);
	        baseDatos.add(backupBD);
	        baseDatos.add(modoAvanzado);
		}
		return menuPrinc;
	}

	private JPanel getJPanelAccesosProductos(){
		if (jPanelAccesosProductos==null){
			jPanelAccesosProductos = new TransparentPanel();
			jPanelAccesosProductos.setLayout(null);
			jPanelAccesosProductos.setBackground(Utils.colorPanel);
			jPanelAccesosProductos.setBorder(new LineBorder(new Color(104, 34, 139), 5, true));
			jPanelAccesosProductos.setBounds(new Rectangle(700, 160, 600, 140));
			JLabel titulosoc= new JLabel("PROVEEDORES - PRODUCTOS");
			titulosoc.setForeground(Utils.colorTexto);
			titulosoc.setBounds(new java.awt.Rectangle(0,5,600,35));
			titulosoc.setHorizontalAlignment(SwingConstants.CENTER);
			titulosoc.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 32));
			jPanelAccesosProductos.add(titulosoc, null);
			jPanelAccesosProductos.add(getJButtonProveedores(), null);
			JLabel gestion= new JLabel("PROVEEDORES");
			gestion.setForeground(Utils.colorTexto);
			gestion.setBounds(new java.awt.Rectangle(0,110,300,25));
			gestion.setHorizontalAlignment(SwingConstants.CENTER);
			gestion.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 24));
			jPanelAccesosProductos.add(gestion, null);
			jPanelAccesosProductos.add(getJButtonProductos(), null);
			JLabel gestionPr= new JLabel("PRODUCTOS");
			gestionPr.setForeground(Utils.colorTexto);
			gestionPr.setBounds(new java.awt.Rectangle(300,110,300,25));
			gestionPr.setHorizontalAlignment(SwingConstants.CENTER);
			gestionPr.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 24));
			jPanelAccesosProductos.add(gestionPr, null);
		}
		return jPanelAccesosProductos;
	}
		
	public JPanel getJPanelLibroIva(){
		if (jPanelLibroIva==null){
			jPanelLibroIva = new TransparentPanel();
			jPanelLibroIva.setLayout(null);
			jPanelLibroIva.setBackground(Utils.colorPanel);
			jPanelLibroIva.setBorder(new LineBorder(new Color(104, 34, 139), 5, true));
			jPanelLibroIva.setBounds(new Rectangle(1025, 310, 275, 140));
			jPanelLibroIva.add(getJButtonLibroIva(), null);
			JLabel stock= new JLabel("LIBRO IVA");
			stock.setForeground(Utils.colorTexto);
			stock.setBounds(new java.awt.Rectangle(0,110,275,25));
			stock.setHorizontalAlignment(SwingConstants.CENTER);
			stock.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD,24));
			jPanelLibroIva.add(stock, null);
		}
		return jPanelLibroIva;
	}
	
	public JButton getJButtonLibroIva() {
		if (jBLibroIva == null) {
			jBLibroIva= new GlossyButton("L",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);
			jBLibroIva.setBounds(new java.awt.Rectangle(90,40,90,70));
			jBLibroIva.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/libroIva.png")));
			jBLibroIva.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
			jBLibroIva.setInputMap(0, map);
			jBLibroIva.setMnemonic('L');
			jBLibroIva.setFont(Utils.FuenteTablasSimple());
		}
		return jBLibroIva;
	}
		
	public JButton getJButtonProductos() {
		if (jBProductos == null) {
			jBProductos= new GlossyButton("8",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);
			jBProductos.setBounds(new java.awt.Rectangle(405,40,90,70));
			jBProductos.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/productos.png")));
			jBProductos.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
			jBProductos.setInputMap(0, map);
			jBProductos.setMnemonic('8');
			jBProductos.setFont(Utils.FuenteTablasSimple());
		}
		return jBProductos;
	}
	
	public JButton getJButtonSalir() {
		if (jButtonSalir == null) {
			jButtonSalir = new GlossyButton("\nSALIR",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_BLACK_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);
			jButtonSalir.setBounds(new java.awt.Rectangle(1110,463,170,100));
			jButtonSalir.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/salir.png")));
			jButtonSalir.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
			jButtonSalir.setInputMap(0, map);
			jButtonSalir.setFont(Utils.FuenteTablasSimple());
			jButtonSalir.setMnemonic('S');
		}
		return jButtonSalir;
	}

	public JButton getJButtonProveedores() {
		if (jBGestionProveedores == null) {
			jBGestionProveedores= new GlossyButton("7",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);
			jBGestionProveedores.setBounds(new java.awt.Rectangle(105,40,90,70));  
			jBGestionProveedores.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/proveedores.png")));
			jBGestionProveedores.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
			jBGestionProveedores.setInputMap(0, map);
			jBGestionProveedores.setMnemonic('7');
			jBGestionProveedores.setFont(Utils.FuenteTablasSimple());
		}
		return jBGestionProveedores;
	}
	
	public JPanel getJPanelAccesosClientes(){
		if (jPanelAccesosCliente==null){
			jPanelAccesosCliente = new TransparentPanel();
			jPanelAccesosCliente.setLayout(null);
			jPanelAccesosCliente.setBackground(Utils.colorPanel);
			jPanelAccesosCliente.setBorder(new LineBorder(new Color(104, 34, 139), 5, true));
			jPanelAccesosCliente.setBounds(new Rectangle(700, 310, 275, 140));
			JLabel titulosoc= new JLabel("CLIENTES");
			titulosoc.setForeground(Utils.colorTexto);
			titulosoc.setBounds(new java.awt.Rectangle(0,5,275,35));
			titulosoc.setHorizontalAlignment(SwingConstants.CENTER);
			titulosoc.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 32));
			jPanelAccesosCliente.add(titulosoc, null);
			jPanelAccesosCliente.add(getJButtonClientes(), null);
			JLabel gestion= new JLabel("GESTION");
			gestion.setForeground(Utils.colorTexto);
			gestion.setBounds(new java.awt.Rectangle(0,110,275,25));
			gestion.setHorizontalAlignment(SwingConstants.CENTER);
			gestion.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 24));
			jPanelAccesosCliente.add(gestion, null);
		}
		return jPanelAccesosCliente;
	}
	
	public JButton getJButtonClientes() {
		if (jBGestionClientes == null) {
			jBGestionClientes= new GlossyButton("C",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);
			jBGestionClientes.setBounds(new java.awt.Rectangle(90,40,90,70));
			jBGestionClientes.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/socios.png")));
			jBGestionClientes.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
			jBGestionClientes.setInputMap(0, map);
			jBGestionClientes.setMnemonic('C');
			jBGestionClientes.setFont(Utils.FuenteTablasSimple());
		}
		return jBGestionClientes;
	}
	
	private JPanel getJPanelAccesosContables(){
		if (jPanelAccesosContables==null){
			jPanelAccesosContables = new TransparentPanel();
			jPanelAccesosContables.setForeground(new Color(106, 90, 205));
			jPanelAccesosContables.setLayout(null);
			jPanelAccesosContables.setBackground(Utils.colorPanel);
			jPanelAccesosContables.setBorder(new LineBorder(new Color(104, 34, 139), 5, true));
			jPanelAccesosContables.setBounds(new Rectangle(40, 160, 600, 140));
			JLabel titulosoc= new JLabel("GESTION CONTABLE");
			titulosoc.setForeground(Utils.colorTexto);
			titulosoc.setBounds(new java.awt.Rectangle(0,5,600,35));
			titulosoc.setHorizontalAlignment(SwingConstants.CENTER);
			titulosoc.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 32));
			jPanelAccesosContables.add(titulosoc, null);
			jPanelAccesosContables.add(getJButtonGestionarMC(), null);
				JLabel movCaja= new JLabel("MOVIMIENTO DE CAJA");
				movCaja.setForeground(Utils.colorTexto);
				movCaja.setBounds(new java.awt.Rectangle(0,110,300,25));
				movCaja.setHorizontalAlignment(SwingConstants.CENTER);
				movCaja.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 24));
				jPanelAccesosContables.add(movCaja, null);
				jPanelAccesosContables.add(getJButtonPlanillaES(), null);
				JLabel plES= new JLabel("PLANILLA DE E/S");
				plES.setForeground(Utils.colorTexto);
				plES.setBounds(new java.awt.Rectangle(300,110,300,25));
				plES.setHorizontalAlignment(SwingConstants.CENTER);
				plES.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 24));
				jPanelAccesosContables.add(plES, null);
		}
		return jPanelAccesosContables;
	}

	public JButton getJButtonGestionarMC() {
		if (jBGestionarMC == null) {
			jBGestionarMC= new GlossyButton("5",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);
			jBGestionarMC.setBounds(new java.awt.Rectangle(105,40,90,70));
			jBGestionarMC.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/movCaja.png")));
			jBGestionarMC.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
			jBGestionarMC.setInputMap(0, map);
			jBGestionarMC.setFont(Utils.FuenteTablasSimple());
			jBGestionarMC.setMnemonic('5');
		}
		return jBGestionarMC;
	}

	public JButton getJButtonPlanillaES() {
		if (jBPlanillaES == null) {
			jBPlanillaES= new GlossyButton("6",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);
			jBPlanillaES.setBounds(new java.awt.Rectangle(405,40,90,70));
			jBPlanillaES.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/planillaES.png")));
			jBPlanillaES.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
			jBPlanillaES.setInputMap(0, map);
			jBPlanillaES.setFont(Utils.FuenteTablasSimple());
			jBPlanillaES.setMnemonic('6');
		}
		return jBPlanillaES;
	}
	
	private JPanel getJPanelAccesosFactProveedor(){
		if (jPanelAccesosFactProv==null){
			jPanelAccesosFactProv = new TransparentPanel();
			jPanelAccesosFactProv.setLayout(null);
			jPanelAccesosFactProv.setBackground(Utils.colorPanel);
			jPanelAccesosFactProv.setBorder(new LineBorder(new Color(104, 34, 139), 5, true));
			jPanelAccesosFactProv.setBounds(new Rectangle(700, 10, 600, 140));
			JLabel titulosoc= new JLabel("FACTURACION - PROVEEDOR");
			titulosoc.setForeground(Utils.colorTexto);
			titulosoc.setBounds(new java.awt.Rectangle(0,5,600,35));
			titulosoc.setHorizontalAlignment(SwingConstants.CENTER);
			titulosoc.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 32));
			jPanelAccesosFactProv.add(titulosoc, null);
			jPanelAccesosFactProv.add(getJButtonFacturaProveedor(), null);
				JLabel nFact= new JLabel("NUEVA FACTURA");
				nFact.setForeground(Utils.colorTexto);
				nFact.setBounds(new java.awt.Rectangle(0,110,300,25));
				nFact.setHorizontalAlignment(SwingConstants.CENTER);
				nFact.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 24));
				jPanelAccesosFactProv.add(nFact, null);
				jPanelAccesosFactProv.add(getJButtonTodasFactProveedor(), null);
				JLabel factGen= new JLabel("FACTURAS GENERADAS");
				factGen.setForeground(Utils.colorTexto);
				factGen.setBounds(new java.awt.Rectangle(300,110,300,25));
				factGen.setHorizontalAlignment(SwingConstants.CENTER);
				factGen.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 24));
				jPanelAccesosFactProv.add(factGen, null);
		}
		return jPanelAccesosFactProv;
	}

	public JButton getJButtonFacturaCliente() {
		if (jBFacturaCliente == null) {
			jBFacturaCliente= new GlossyButton("F",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);
			jBFacturaCliente.setBounds(new java.awt.Rectangle(105,40,90,70));
			jBFacturaCliente.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/facturaC.png")));
			jBFacturaCliente.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
			jBFacturaCliente.setInputMap(0, map);
			jBFacturaCliente.setFont(Utils.FuenteTablasSimple());
			jBFacturaCliente.setMnemonic('F');
		}
		return jBFacturaCliente;
	}

	public JButton getJButtonRemitoCliente() {
		if (jBRemitoCliente == null) {
			jBRemitoCliente = new GlossyButton("1",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);
			jBRemitoCliente.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/remito.png")));
			jBRemitoCliente.setBounds(new java.awt.Rectangle(105,40,90,70));
			jBRemitoCliente.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED)); 
			jBRemitoCliente.setInputMap(0, map);
			jBRemitoCliente.setFont(Utils.FuenteTablasSimple());
			jBRemitoCliente.setMnemonic('1');
		}
		return jBRemitoCliente;
	}
	
	public JPanel getJPanelAccesosFactCliente(){
		if (jPanelAccesosFactCte==null){
			jPanelAccesosFactCte = new TransparentPanel();
			jPanelAccesosFactCte.setLayout(null);
			jPanelAccesosFactCte.setBackground(Utils.colorPanel);
			jPanelAccesosFactCte.setBorder(new LineBorder(new Color(104, 34, 139), 5, true));
			jPanelAccesosFactCte.setBounds(new Rectangle(40, 310, 600, 140));
			JLabel titulosoc= new JLabel("FACTURACION - CLIENTE");
			titulosoc.setForeground(Utils.colorTexto);
			titulosoc.setBounds(new java.awt.Rectangle(0,5,600,35));
			titulosoc.setHorizontalAlignment(SwingConstants.CENTER);
			titulosoc.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 32));
			jPanelAccesosFactCte.add(titulosoc, null);
			jPanelAccesosFactCte.add(getJButtonFacturaCliente(), null);
				JLabel nFact= new JLabel("NUEVA FACTURA");
				nFact.setForeground(Utils.colorTexto);
				nFact.setBounds(new java.awt.Rectangle(0,110,300,25));
				nFact.setHorizontalAlignment(SwingConstants.CENTER);
				nFact.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 24));
				jPanelAccesosFactCte.add(nFact, null);
				jPanelAccesosFactCte.add(getJButtonTodasFactCliente(), null);
				JLabel factGen= new JLabel("FACTURAS GENERADAS");
				factGen.setForeground(Utils.colorTexto);
				factGen.setBounds(new java.awt.Rectangle(300,110,300,25));
				factGen.setHorizontalAlignment(SwingConstants.CENTER);
				factGen.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 24));
				jPanelAccesosFactCte.add(factGen, null);
		}
		return jPanelAccesosFactCte;
	}

	public JButton getJButtonFacturaProveedor() {
		if (jBFacturaProveedor== null) {
			jBFacturaProveedor= new GlossyButton("3",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);
			jBFacturaProveedor.setBounds(new java.awt.Rectangle(105,40,90,70));
			jBFacturaProveedor.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/facturaP.png")));
			jBFacturaProveedor.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
			jBFacturaProveedor.setInputMap(0, map);
			jBFacturaProveedor.setFont(Utils.FuenteTablasSimple());
			jBFacturaProveedor.setMnemonic('3');
		}
		return jBFacturaProveedor;
	}

	public JButton getJButtonTodasFactCliente() {
		if (jBTodasFactCliente == null) {
			jBTodasFactCliente= new GlossyButton("T",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);
			jBTodasFactCliente.setBounds(new java.awt.Rectangle(405,40,90,70));
			jBTodasFactCliente.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/tFactCte.png")));
			jBTodasFactCliente.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
			jBTodasFactCliente.setInputMap(0, map);
			jBTodasFactCliente.setMnemonic('T');
			jBTodasFactCliente.setFont(Utils.FuenteTablasSimple());
		}
		return jBTodasFactCliente;
	}
	
	private JPanel getJPanelAccesosRemitoCliente(){
		if (jPanelAccesosRemitoCte==null){
			jPanelAccesosRemitoCte = new TransparentPanel();
			jPanelAccesosRemitoCte.setLayout(null);
			jPanelAccesosRemitoCte.setBackground(Utils.colorPanel);
			jPanelAccesosRemitoCte.setBorder(new LineBorder(new Color(104, 34, 139), 5, true));
			jPanelAccesosRemitoCte.setBounds(new Rectangle(40, 10, 600, 140));
			JLabel titulosoc= new JLabel("REMITOS - CLIENTE");
			titulosoc.setForeground(Utils.colorTexto);
			titulosoc.setBounds(new java.awt.Rectangle(0,5,600,35));
			titulosoc.setHorizontalAlignment(SwingConstants.CENTER);
			titulosoc.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 32));
			jPanelAccesosRemitoCte.add(titulosoc, null);
			jPanelAccesosRemitoCte.add(getJButtonRemitoCliente(), null);
				JLabel nRem= new JLabel("NUEVO REMITO");
				nRem.setForeground(Utils.colorTexto);
				nRem.setBounds(new java.awt.Rectangle(0,110,300,25));
				nRem.setHorizontalAlignment(SwingConstants.CENTER);
				nRem.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 24));
				jPanelAccesosRemitoCte.add(nRem, null);
				jPanelAccesosRemitoCte.add(getJButtonTodosRemitosCliente(), null);
				JLabel remGen= new JLabel("REMITOS GENERADOS");
				remGen.setForeground(Utils.colorTexto);
				remGen.setBounds(new java.awt.Rectangle(300,110,300,25));
				remGen.setHorizontalAlignment(SwingConstants.CENTER);
				remGen.setFont(new java.awt.Font(Utils.tipoLetra, java.awt.Font.BOLD, 24));
				jPanelAccesosRemitoCte.add(remGen, null);
		}
		return jPanelAccesosRemitoCte;
	}

	public JButton getJButtonTodasFactProveedor() {
		if (jBTodasFactProv== null) {
			jBTodasFactProv= new GlossyButton("4",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);
			jBTodasFactProv.setBounds(new java.awt.Rectangle(405,40,90,70));
			jBTodasFactProv.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/tFactProv.png")));
			jBTodasFactProv.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
			jBTodasFactProv.setInputMap(0, map);
			jBTodasFactProv.setMnemonic('4');
			jBTodasFactProv.setFont(Utils.FuenteTablasSimple());
		}
		return jBTodasFactProv;
	}

	public JButton getJButtonTodosRemitosCliente() {
		if (jBTodosRemitosCliente == null) {
			jBTodosRemitosCliente= new GlossyButton("2",ButtonType.BUTTON_ROUNDED_RECTANGLUR,Theme.GLOSSY_METALLICGRAY_THEME,Theme.GLOSSY_ORANGE_THEME,Theme.GLOSSY_BLACK_THEME);
			jBTodosRemitosCliente.setBounds(new java.awt.Rectangle(405,40,90,70));
			jBTodosRemitosCliente.setIcon(new ImageIcon(GUIPrincipal.class.getResource("/cliente/Imagenes/Iconos/tRemitosCte.png")));
			jBTodosRemitosCliente.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
			jBTodosRemitosCliente.setInputMap(0, map);
			jBTodosRemitosCliente.setFont(Utils.FuenteTablasSimple());
			jBTodosRemitosCliente.setMnemonic('2');
		}
		return jBTodosRemitosCliente;
	}
	
	public void setActionListeners(ActionListener lis) {
		this.jButtonSalir.addActionListener(lis);
		this.infoComercio.addActionListener(lis);
		this.infoProg.addActionListener(lis);
		this.archivoSalir.addActionListener(lis);
		this.gestionarProvincia.addActionListener(lis);
		this.gestionarLocalidad.addActionListener(lis);
		this.jBGestionarMC.addActionListener(lis);
		this.jBPlanillaES.addActionListener(lis);
		
		this.jBGestionProveedores.addActionListener(lis);
		this.jBProductos.addActionListener(lis);
		this.jBFacturaProveedor.addActionListener(lis);
		this.jBTodasFactProv.addActionListener(lis);
		this.jBRemitoCliente.addActionListener(lis);
		this.jBTodosRemitosCliente.addActionListener(lis);
		this.backupBD.addActionListener(lis);
		this.modoAvanzado.addActionListener(lis);
		
		this.jBLibroIva.addActionListener(lis);
		this.jBFacturaCliente.addActionListener(lis);
		this.jBTodasFactCliente.addActionListener(lis);
		this.jBGestionClientes.addActionListener(lis);
	}

	public JMenuItem getArchivoInfoComercio() {
		return infoComercio;
	}
	
	public JMenuItem getArchivoSalir() {
		return archivoSalir;
	}
		
	public JMenuItem getGestionarProvincia() {
		return gestionarProvincia;
	}

	public JMenuItem getGestionarLocalidad() {
		return gestionarLocalidad;
	}
	
	public JMenuItem getBaseDatos() {
		return backupBD;
	}
	
	public JMenuItem getModoAvanzado() {
		return modoAvanzado;
	}

	public JMenuItem getInfoProg() {
		return infoProg;
	}
	
	private int d,m,a,hora, minutos, segundos;
	private int dia;
	JLabel label;
	Calendar calendario;
	Thread h1;

	public void init() {
		label = new JLabel(" ");
		label.setFont(new Font( Utils.tipoLetra, Font.BOLD, 40));
		label.setBounds(new Rectangle(15,500,900,40));
		label.setForeground(Utils.colorTexto);
		this.jContentPane.add(label,null);
		h1 = new Thread(this);
		h1.start();
	}

	public void calcula () {
		Calendar calendario = new GregorianCalendar();
		a=calendario.get(Calendar.YEAR);
		m=calendario.get(Calendar.MONTH)+1;
		d=calendario.get(Calendar.DATE);
		dia=calendario.get(Calendar.DAY_OF_WEEK);
		hora = calendario.get(Calendar.HOUR_OF_DAY);
		minutos = calendario.get(Calendar.MINUTE);
		segundos = calendario.get(Calendar.SECOND); 
	}
	
	private String nombreDia(int diaSemana){
		if(diaSemana==1) return "DOMINGO";
		if(diaSemana==2) return "LUNES";
		if(diaSemana==3) return "MARTES";
		if(diaSemana==4) return "MIERCOLES";
		if(diaSemana==5) return "JUEVES";
		if(diaSemana==6) return "VIERNES";
		if(diaSemana==7) return "SABADO";
		return "";
	}
	
	public void run() {
		Thread ct = Thread.currentThread();
		while(ct == h1) {   
			calcula();
			String min=String.valueOf(minutos);
			if(min.length()==1) min ="0"+min;
			String seg=String.valueOf(segundos);
			if(seg.length()==1) seg ="0"+seg;
			label.setText("  "+nombreDia(dia)+"  "+d+"/"+m+"/"+a+" - "+hora + ":" + min + ":" + seg);
			try {
				Thread.sleep(1000);
			}
			catch(InterruptedException e) {}
		}
	}

	public void stop() {
		h1 = null;
	}
}
