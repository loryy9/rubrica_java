package interfaccia.gui.vista;

import java.awt.*;

import javax.swing.*;

import modello.Bacheca;
import modello.Utente;

@SuppressWarnings("serial")
public class ContentPanel extends JPanel{
	private Bacheca model;
	private JTextArea bacheca;
	
	public ContentPanel(Bacheca model, Utente user) {
		this.model = model;
		setLayout(new BorderLayout());
		bacheca = new JTextArea(10,50);
		bacheca.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		JLabel label = new JLabel("Bacheca      Utente: " + user.getEmail());
		add(label, BorderLayout.NORTH);
		add(bacheca, BorderLayout.CENTER);
		updateView();
	}
	
	public void updateView() {
		bacheca.setText(model.toString());
	}
}
