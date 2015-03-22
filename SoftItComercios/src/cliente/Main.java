package cliente;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import cliente.Principal.MediadorPrincipal;

import common.Utils;

public class Main {    
  
    public static void main(String[] args) throws Exception {
        String conf = "";
        if (args.length > 0) {
            conf = args[0];
        }    
        common.RootAndIp.setConf(conf);
        final JPasswordField jpf = new JPasswordField();
        
		JLabel titulo = new JLabel ("Ingrese su contrase�a");
		JOptionPane.showConfirmDialog (null, new Object[]{titulo, jpf}, "Inicio de sesi�n", JOptionPane.OK_CANCEL_OPTION);
		char p[] = jpf.getPassword();
		String pass = new String(p);
		if(pass.compareTo("it10")==0){
			MediadorPrincipal mp = new MediadorPrincipal();
			mp.show();
		}else{
			Utils.advertenciaUsr(null,"La Contrase�a ingresada no es correcta.");
		}
    }  
  
}
