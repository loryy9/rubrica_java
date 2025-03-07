package interfaccia.gui.vista;

import java.awt.*;
import javax.swing.*;

import interfaccia.gui.controllo.ControlloBacheca;
import modello.Bacheca;
import modello.Utente;

@SuppressWarnings("serial")
public class BachecaPanel extends JPanel{
	public BachecaPanel(Bacheca bacheca, Utente user) {
		setLayout(new BorderLayout());
		ContentPanel container = new ContentPanel(bacheca, user);
		ControlloBacheca controller = new ControlloBacheca(container, bacheca, user);
		OpsPanel opzioni = new OpsPanel(controller);
		
		add(opzioni, BorderLayout.NORTH);
		add(container, BorderLayout.CENTER);
	}
}
