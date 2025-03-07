package interfaccia.gui.vista;

import java.awt.*;

import javax.swing.*;

import interfaccia.gui.controllo.ControlloBacheca;

@SuppressWarnings("serial")
public class OpsPanel extends JPanel{
	public OpsPanel (ControlloBacheca controller) {
		setLayout(new FlowLayout());
		JButton addAnnuncio = new JButton("Nuovo annuncio");
		JButton cercaAnnuncio = new JButton("Cerca annuncio");
		JButton addParola = new JButton("Nuova parola chiave");
		JButton rimuoviAnnuncio = new JButton("Rimuovi annuncio");
		JButton pulisciBacheca = new JButton("Pulisci bacheca");
		
		
		addAnnuncio.addActionListener(controller);
		cercaAnnuncio.addActionListener(controller);
		addParola.addActionListener(controller);
		rimuoviAnnuncio.addActionListener(controller);
		pulisciBacheca.addActionListener(controller);
		
		
		add(addAnnuncio);
		add(cercaAnnuncio);
		add(addParola);
		add(rimuoviAnnuncio);
		add(pulisciBacheca);

	}
}
