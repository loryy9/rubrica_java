package interfaccia.gui;

import javax.swing.*;

import interfaccia.gui.vista.BachecaPanel;
import modello.Bacheca;
import modello.Utente;

@SuppressWarnings("serial")
public class Gui extends JFrame{
	private Bacheca bacheca;
	private Utente user;
	
	public Gui() {
		this.bacheca = new Bacheca();
		
		logIn();
		
		caricaFile();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100,100,1000,300);
		setTitle("Bacheca annunci");
		
		JPanel bachecaPanel = new BachecaPanel(bacheca, user);
		setContentPane(bachecaPanel);
		setVisible(true);
	}
	
	private void logIn() {
		JTextField email = new JTextField(20);
		JTextField nome = new JTextField(20);
		
		JComponent[] inputs = new JComponent[] {
				new JLabel("Inserire la mail:"), 
				email,
				new JLabel("Inserire il nome:"),
				nome		
		};
		
		while(true) {
			int show = JOptionPane.showConfirmDialog(null, inputs, "LogIn", JOptionPane.OK_CANCEL_OPTION);
			
			if(show == JOptionPane.OK_OPTION) {
				try {
					user = new Utente(email.getText(), nome.getText());
					break;
				}catch(Exception e) {
					JOptionPane.showMessageDialog(this, "Errore creazione utente " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
				}
			}else {
				System.exit(0);
			}
		}
	}
	
	private void caricaFile() {
		try {
			bacheca.caricaFile("src/bacheca.txt");
		}catch(Exception e) {
			JOptionPane.showMessageDialog(this, "Errore caricamento annunci " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}
}
