package interfaccia.cli;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import modello.*;
import modello.exceptions.*;

public class Cli {
	private Bacheca bacheca;
	private Scanner scanner;
	private Utente utente;	
	
	
	public Cli() throws Exception {
		this.bacheca = new Bacheca();
		this.scanner = new Scanner(System.in);
		
		login();
	}
	
	//richiede nome utente e mail
	private void login() throws Exception{
	    System.out.println("\n--- LOGIN ---");

	    while (true) {
	        try {
	            System.out.print("Inserisci email: ");
	            String email = scanner.next();

	            System.out.print("Inserisci nome: ");
	            scanner.nextLine();
	            String nome = scanner.nextLine();

	            utente = new Utente(email, nome);
	            System.out.println("Login effettuato con successo! Benvenuto, " + utente.getNome());
	            avvia();
	            break;
	        } catch (UtenteException e) {
	            System.out.println("Errore: " + e.getMessage() + " Riprova.");
	        }
	    }
	}

	private void avvia() throws Exception{
		int scelta = 0;
		
		bacheca.caricaFile("src/bacheca.txt");
		
		do {
			System.out.println("\n--- MENU INTERFACCIA CLI ---");
            System.out.println("1. Aggiungi un nuovo annuncio");
            System.out.println("2. Visualizza tutti gli annunci");
            System.out.println("3. Cerca un annuncio per parola chiave");
            System.out.println("4. Aggiungi parola chiave");
            System.out.println("5. Rimuovi un annuncio per ID");
            System.out.println("6. Pulisci la bacheca (annunci scaduti)");
            System.out.println("7. Salva bacheca");
            System.out.println("9. Torna al menu principale");
            System.out.print("Scelta: ");
            
            scelta = scanner.nextInt();
            
            switch(scelta) {
            	case 1:
            		nuovoAnnuncio();
            		break;
            	case 2:
            		bacheca.visualizzaAnnunci();
            		break;
            	case 3:
            		cercaAnnuncio();
            		break;
            	case 4: 
            		aggiungiParolaChiave();
            		break;
            	case 5:
            		rimuoviAnnuncio();
            		break;
            	case 6:
            		bacheca.pulisciBacheca();
            		bacheca.visualizzaAnnunci();
            		bacheca.scriviFile("src/bacheca.txt");
            		break;
            	case 7:
            		bacheca.scriviFile("src/bacheca.txt");
            }
            
		}while(scelta != 9);
	}
	
	private void nuovoAnnuncio() throws Exception {
		String nome;
		double prezzo;
		String tipo;
		String paroleChiave;
		LocalDate dataScadenza;
		while(true) {
			try {
				System.out.println("\n***INSERIMENTO ARTICOLO***\n");
				System.out.println("Nome: ");
				scanner.nextLine();
				nome = scanner.nextLine();
				System.out.println("Prezzo: ");
				prezzo = scanner.nextDouble();
				scanner.nextLine();
				System.out.println("Tipo: (vendere - acquistare)");
				tipo = scanner.nextLine();
				System.out.println("Parole chiave: (separate da ; senza spazi)");
				paroleChiave = scanner.nextLine();
				while (true) {
					System.out.print("Data scadenza (YYYY-MM-DD): ");
					String dataInput = scanner.nextLine();
					try {
						dataScadenza = LocalDate.parse(dataInput);
						break;
					} catch (Exception e) {
						System.out.println("Errore: formato data non valido. Inserire nel formato YYYY-MM-DD.");
					}
				}		
				
				Annuncio annuncio = new Annuncio(nome, prezzo, tipo, paroleChiave, dataScadenza, utente.getEmail());
				ArrayList<Annuncio> listaVendita = new ArrayList<>();
				listaVendita = bacheca.nuovoAnnuncio(annuncio);
				bacheca.scriviFile("src/bacheca.txt");
					
				if(listaVendita.size() > 0) {
					System.out.println("\nPotrebbero interessarti:\n");
					for(Annuncio annuncioTrovato : listaVendita) {
						System.out.println(annuncioTrovato.toString());
					}
				}
				break;
			}catch(Exception e) {
				System.out.println("Errore: " + e.getMessage() + " Riprova.");	
			}
		}
	}
	private void cercaAnnuncio() throws Exception {
		ArrayList<Annuncio> listaTrovati = new ArrayList<>();
		String parolaRicerca = "";
		while(true) {
			try {
				System.out.println("Parola chiave da ricercare: ");
				scanner.nextLine();
				parolaRicerca = scanner.nextLine();
				listaTrovati = bacheca.cercaAnnunci(parolaRicerca);
				if(listaTrovati.size() > 0) {
					for(Annuncio annuncioTrovato : listaTrovati) {
						System.out.println(annuncioTrovato.toString());
					}
				}
				break;
			}catch (Exception e) {
				System.out.println("Errore: " + e.getMessage() + " Riprova.");
			}
		}
	}
	
	private void aggiungiParolaChiave() throws Exception {
		int idAnnuncio = -1;
		String parolaAggiunta = "";
		while(true) {
			try {
				bacheca.visualizzaAnnunci();
				System.out.println("\nSelezionare ID da modificare\tUtente: " + utente.getEmail() + "\n9. exit");
				scanner.nextLine();
				idAnnuncio = scanner.nextInt();
				if(idAnnuncio == 9) {
					break;
				}
				System.out.println("Parola chiave da aggiungere: ");
				scanner.nextLine();
				parolaAggiunta = scanner.nextLine();
				bacheca.aggiungiParolaChiave(idAnnuncio, utente.getEmail(), parolaAggiunta);
				System.out.println("Annuncio modificato\n");
				bacheca.scriviFile("src/bacheca.txt");
				break;
			}catch (Exception e) {
				System.out.println("Errore: " + e.getMessage() + " Riprova.");
			}
		}
	}
	
	private void rimuoviAnnuncio() throws Exception {
		int idRicerca = -1;
		while(true) {
			try {
				bacheca.visualizzaAnnunci();
				System.out.println("\nSelezionare ID da eliminare\tUtente: " + utente.getEmail() + "\n9. exit");
				scanner.nextLine();
				idRicerca = scanner.nextInt();
				if(idRicerca == 9) {
					break;
				}
				if(bacheca.cancellaAnnuncio(idRicerca, utente.getEmail())) {
					System.out.println("\nEliminato con successo");
				}				
				bacheca.visualizzaAnnunci();
				bacheca.scriviFile("src/bacheca.txt");
				break;
			}catch (Exception e) {
				System.out.println("Errore: " + e.getMessage() + " Riprova.");
			}
		}
	}
}
