package prueba;

import javax.swing.JFrame;


public class prueba {
	public static void main(String [] arg){
		GUIPrincipalP gui=null;
	//	guia.show();
		try {
			 gui = new GUIPrincipalP();//new JFrame());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gui.show();
	}
}
