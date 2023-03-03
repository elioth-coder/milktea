package milkytea;

import javax.swing.JOptionPane;

public class MessageBox {
	public static void show(String message, String type) {
		int messageType = JOptionPane.INFORMATION_MESSAGE;
		
		if(type.equals("error"))   
			messageType = JOptionPane.ERROR_MESSAGE;
		if(type.equals("info"))    
			messageType = JOptionPane.INFORMATION_MESSAGE;
		if(type.equals("plain"))   
			messageType = JOptionPane.PLAIN_MESSAGE;
		if(type.equals("warning")) 
			messageType = JOptionPane.WARNING_MESSAGE;
		
	    JOptionPane.showMessageDialog(null, message, "System Message", messageType);
	}
}
