package interfaccia.gui.controllo;

import java.awt.event.*;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.*;

import interfaccia.gui.vista.ContentPanel;
import modello.Annuncio;
import modello.Bacheca;
import modello.Utente;

public class ControlloBacheca implements ActionListener{
	private ContentPanel view;
	private Bacheca bacheca;
	private Utente user;
	
	public ControlloBacheca(ContentPanel view, Bacheca bacheca, Utente user) {
		this.view = view;
		this.bacheca = bacheca;
		this.user = user;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton) e.getSource();
		String scelta = source.getText();
		
		switch(scelta) {
			case "Nuovo annuncio":
				aggiungiAnnuncio();
				break;
			case "Cerca annuncio":
				cercaAnnuncio();
				break;
			case "Nuova parola chiave":
				aggiungiParolaChiave();
				break;
			case "Rimuovi annuncio":
				rimuoviAnnuncio();
				break;
			case "Pulisci bacheca":
				pulisciBacheca();
				break;
			default:
				break;
		}
		view.updateView();		
	}
	private void aggiungiAnnuncio() {
		JTextField nome = new JTextField(20);
		JTextField prezzo = new JTextField(20);
		JTextField tipo = new JTextField(20);
		JTextField paroleChiave = new JTextField(20);
		JTextField dataScadenza = new JTextField(20);
		
		JComponent[] inputs = new JComponent[] {
				new JLabel("Nome:"),
				nome,
				new JLabel("Prezzo:"),
				prezzo,
				new JLabel("Tipo (vendere - acquistare)"),
				tipo,
				new JLabel("Parole Chiave (Senza spazi, separate da ;)"),
				paroleChiave,
				new JLabel("Data scadenza (YYYY-MM-DD)"),
				dataScadenza
		};
		
		int show = JOptionPane.showConfirmDialog(null, inputs, "Inserimento annuncio", JOptionPane.OK_CANCEL_OPTION);
		if(show == JOptionPane.OK_OPTION) {
			try {
				String nomeIns = nome.getText();
				double prezzoIns = Double.parseDouble(prezzo.getText());
				String tipoIns = tipo.getText();
				String paroleChiaveIns = paroleChiave.getText();
				LocalDate dataScadenzaIns = null;
				if(!tipoIns.equals("acquistare")) {
					 dataScadenzaIns = LocalDate.parse(dataScadenza.getText());
				}
			
				
				Annuncio annuncio = new Annuncio(nomeIns, prezzoIns, tipoIns, paroleChiaveIns, dataScadenzaIns, user.getEmail());
				
				ArrayList<Annuncio> trovati = bacheca.nuovoAnnuncio(annuncio);
				bacheca.scriviFile("src/bacheca.txt");
				
				JOptionPane.showMessageDialog(null, "Annuncio inserito!");
				
				if(tipoIns.equals("acquistare") && trovati.size() > 0) {
					StringBuilder mostra = new StringBuilder("Potrebbero interessarti\n");
					for(Annuncio trovato : trovati) {
						mostra.append(trovato).append("\n");
					}
					JOptionPane.showMessageDialog(null, mostra.toString(), "Potrebbero interessarti", JOptionPane.INFORMATION_MESSAGE);
				}
			}catch(Exception e) {
				JOptionPane.showMessageDialog(null, "Errore inserimento " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private void rimuoviAnnuncio () {
		JTextField id = new JTextField(20);
		JComponent[] inputs = new JComponent[] {
				new JLabel("ID annuncio da rimuovere:"),
				id
		};
		
		int show = JOptionPane.showConfirmDialog(null, inputs, "Rimozione annuncio", JOptionPane.OK_CANCEL_OPTION);
		if(show == JOptionPane.OK_OPTION) {
			try {
				int idIns = Integer.parseInt(id.getText());
				bacheca.cancellaAnnuncio(idIns, user.getEmail());
				bacheca.scriviFile("src/bacheca.txt");
				
				JOptionPane.showMessageDialog(null, "Annuncio cancellato!");
				
			}catch(Exception e) {
				JOptionPane.showMessageDialog(null, "Errore cancellazione " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private void cercaAnnuncio() {
		JTextField parolaChiave = new JTextField(20);
		JComponent[] inputs = new JComponent[] {
				new JLabel("Parola da cercare"),
				parolaChiave
		};
		int show = JOptionPane.showConfirmDialog(null, inputs, "Ricerca annuncio", JOptionPane.OK_CANCEL_OPTION);
		if(show == JOptionPane.OK_OPTION) {
			try {
				String paroleChiaveIns = parolaChiave.getText();
				ArrayList<Annuncio> listaAnnunci = bacheca.cercaAnnunci(paroleChiaveIns);
				StringBuilder mostra = new StringBuilder();
				
				if(listaAnnunci.size() == 0) {
					mostra.append("Nessun annuncio trovato");
				}else {
					mostra.append("Annunci trovati\n");
					for(Annuncio annuncio : listaAnnunci) {
						mostra.append(annuncio).append("\n");
					}
				}
				JOptionPane.showMessageDialog(null, mostra.toString());
			}catch(Exception e ) {
				JOptionPane.showMessageDialog(null, "Errore ricerca " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private void aggiungiParolaChiave() {
		JTextField id = new JTextField(20);
		JTextField parolaChiave = new JTextField(20);
		JComponent[] inputs = new JComponent[] {
				new JLabel("ID da modificare:"),
				id,
				new JLabel("Parola da modificare"),
				parolaChiave
		};
		int show = JOptionPane.showConfirmDialog(null, inputs, "Aggiungi parola chiave", JOptionPane.OK_CANCEL_OPTION);
		if(show == JOptionPane.OK_OPTION) {
			try {
				int idIns = Integer.parseInt(id.getText());
				String parolaIns = parolaChiave.getText();
				
				bacheca.aggiungiParolaChiave(idIns, user.getEmail(), parolaIns);
				bacheca.scriviFile("src/bacheca.txt");
				
				JOptionPane.showMessageDialog(null, "Parola aggiunta!");
			}catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Errore aggiunta parola " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	private void pulisciBacheca() {
		try {
			bacheca.pulisciBacheca();
			bacheca.scriviFile("src/bacheca.txt");
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Errore pulisci bacheca " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		}
	}
}

