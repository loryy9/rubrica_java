package modello;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

import modello.exceptions.BachecaException;

public class Bacheca implements Iterable<Annuncio> { 

	private ArrayList<Annuncio> listaAnnunci;
	
	// COSTRUTTORE BACHECA
	public Bacheca() {
		this.listaAnnunci = new ArrayList<>();
	}
	
	
	@Override
	public Iterator<Annuncio> iterator() {
		return new Iterator<Annuncio>() {
			private final Iterator<Annuncio> iter = listaAnnunci.iterator();
			@Override
			public boolean hasNext() {				
				return iter.hasNext();
			}

			@Override
			public Annuncio next() {
				return iter.next();
			}		
		};
	}
	
	
	//dichiarazione tipo ArrayList per poter tornare la lista di annunci con parole chiave comuni nel caso di acquisto
	public ArrayList<Annuncio> nuovoAnnuncio(Annuncio annuncio) throws BachecaException {
		//se l'annuncio è già presente segnalazione errore
		if ( listaAnnunci.contains(annuncio) ) {
			throw new BachecaException("Annuncio già presente");
		}
		
		//se l'annuncio non è già stato inserito, viene inserito
		listaAnnunci.add(annuncio);		
		
		//controllo se l'annuncio è di tipo acquisto
		//in tal caso viene restituita la lista di annunci con parole chiave comuni
		if(annuncio.getTipo().equals("acquistare")) {
			return elencaAnnunciVendita(annuncio.getParoleChiave());
		}
		
		//se l'annuncio è invece di tipo vendita ritorna una lista vuota
		return new ArrayList<>();
	}
	
	// restituisce una lista di annunci tipo "vendere" con parole chiave uguali a quella passata come parametro
	public ArrayList<Annuncio> elencaAnnunciVendita(String paroleChiave){
		ArrayList<Annuncio> elencoRisultante = new ArrayList<>();
		
		String[] parole = paroleChiave.split(";"); //SPLIT delle parole chiave per poterle ricercare una ad una, con carattere delimitatore ";"
		
		//ciclo per controllare se l'annuncio è di tipo "vendere", e se contiene le parole chiave
		Iterator<Annuncio> iter = listaAnnunci.iterator();
		while(iter.hasNext()) {		
			Annuncio annuncio = iter.next();
			for(String parola : parole) {
				if(annuncio.getParoleChiave().contains(parola) && annuncio.getTipo().contains("vendere")) {					
					elencoRisultante.add(annuncio);
					break;
				}
			}
		}		
		return elencoRisultante;
	}
	
	// restituisce la lista completa di tutti gli annunci
	public void visualizzaAnnunci() {
		Iterator<Annuncio> iter = listaAnnunci.iterator();
		while(iter.hasNext()) {	
			Annuncio annuncio = iter.next();
			System.out.println(annuncio.toString());
		}
	}
	
	//restituisce una lista di annunci che contengono la paola chiave passata per parametro
	public ArrayList<Annuncio> cercaAnnunci (String parola) {
		ArrayList<Annuncio> trovati = new ArrayList<>();
		
		//ciclo per controllare se l'annuncio la parola chiave
		Iterator<Annuncio> iter = listaAnnunci.iterator();
		while(iter.hasNext()) {	
			Annuncio annuncio = iter.next();
			if(annuncio.getParoleChiave().contains(parola)) {
				trovati.add(annuncio);
			}
		}		
		return trovati;
	}
	
	
	// aggiunge una parola chiave all'elenco delle parole di un annuncio
	public boolean aggiungiParolaChiave(int id, String user, String parolaNew) throws BachecaException {
		Iterator<Annuncio> iter = listaAnnunci.iterator();
		while(iter.hasNext()) {	
			Annuncio annuncio = iter.next();
			if(annuncio.getId() == id) {  // ricerca dell'annuncio con l'ID passato per parametro
				if(annuncio.getUserCreatore().equals(user)) {	//verifica se l'utente che effettua la modifica è lo stesso che ha creato l'annuncio
					String paroleOld = annuncio.getParoleChiave();
					annuncio.setParoleChiave(paroleOld+ parolaNew + ";"); // aggiunge ala lista di parole chiave la parola passata come parametro
					return true;
				}else {
					throw new BachecaException("Utente non corretto"); // eccezione generata se l'utente non è lo stesso che ha creato l'annuncio
				}
			}
		}		
		throw new BachecaException("Annuncio non trovato"); // eccezione generata se l'annuncio non è presente
	}	
	
	// rimuove l'annuncio dalla bacheca
	public boolean cancellaAnnuncio(int id, String user) throws BachecaException {
		Iterator<Annuncio> iter = listaAnnunci.iterator();
		while(iter.hasNext()) {	
			Annuncio annuncio = iter.next();
			if(annuncio.getId() == id) { // ricerca dell'annuncio con l'ID passato per parametro
				if(annuncio.getUserCreatore().equals(user)) { //verifica se l'utente che effettua la cancellazione è lo stesso che ha creato l'annuncio
					listaAnnunci.remove(annuncio); // rimuove l'annuncio dalla lista
					return true;
				}else {
					throw new BachecaException("Utente non corretto"); // eccezione generata se l'utente non è lo stesso che ha creato l'annuncio
				}
			}
		}		
		throw new BachecaException("Annuncio non trovato"); // eccezione generata se l'annuncio non è presente
	}	
	

	// rimuove dalla bacheca gli annunci scaduti
	public void pulisciBacheca() {
		ArrayList<Annuncio> annunciScaduti = new ArrayList<>();
		Iterator<Annuncio> iter = listaAnnunci.iterator();
		while(iter.hasNext()) {	
			Annuncio annuncio = iter.next();
			if(annuncio.scaduto()) {
				annunciScaduti.add(annuncio);
			}
		}
		listaAnnunci.removeAll(annunciScaduti);
	}
	
	// scrive su file, passato come parametro, la lista di annunci 
	public void scriviFile(String path) throws IOException {
		PrintWriter out = null;
	    try {
	        out = new PrintWriter(new FileWriter(path));   // apertura del file passato come parametro in scrittura

	        Iterator<Annuncio> iter = listaAnnunci.iterator();
	        while (iter.hasNext()) {    
	            Annuncio annuncio = iter.next();
	            // scrittura dei dati dell'annuncio nel file, separandoli con "|"
	            out.printf("%d|%s|%.2f|%s|%s|%s|%s|\n",
	                        annuncio.getId(),
	                        annuncio.getNome(),
	                        annuncio.getPrezzo(),
	                        annuncio.getTipo(),
	                        annuncio.getParoleChiave(),
	                        annuncio.getData() == null ? "null" : annuncio.getData().toString(),
	                        annuncio.getUserCreatore());
	        }
	    } catch (IOException e) {
	        throw new IOException("Errore salvataggio file"); 
	    } finally {
	        if (out != null) {
	            out.close();  
	        }
	    }
	}
	
	// carica da file, passato come parametro, la lista di annunci 
	public void caricaFile(String path) throws IOException {
		 BufferedReader reader = null;
		    try {
		        reader = new BufferedReader(new FileReader(path)); // apertura file in lettura
		        String line;
		        
		        while ((line = reader.readLine()) != null) {
		            String[] parts = line.split("\\|");  
		            if (parts.length == 7) {
		                
		            	//impostazione delle variabili da passare al costruttore annuncio
		                int id = Integer.parseInt(parts[0]);
		                String nome = parts[1];
		                double prezzo = Double.parseDouble(parts[2].replace(",", "."));
		                String tipo = parts[3];
		                String paroleChiave = parts[4];
		                LocalDate data = null;
		                if (!parts[5].equals("null")) {
		                    data = LocalDate.parse(parts[5]);  
		                }
		                String userCreatore = parts[6];

		                listaAnnunci.add( new Annuncio(id, nome, prezzo, tipo, paroleChiave, data, userCreatore));
		            }else {
		            	throw new IOException("Elemento mancante");
		            }
		        }
		    } catch (Exception e) {
		        throw new IOException("Errore" + e);		    
		    } finally {
		    	if (reader != null) {
		    		reader.close();  
		        }		        
		    }
	}
	
	//-----
	public int getSize() {
		return listaAnnunci.size();
	}
	
	
	public ArrayList<Annuncio> getListaAnnunci(){
		return listaAnnunci;
	}
	
	//---
	@Override
	public String toString() {
		StringBuilder bachecaLista = new StringBuilder();
		for(Annuncio annuncio : listaAnnunci) {
			bachecaLista.append(annuncio).append("\n");
		}
		return bachecaLista.toString();
	}
}

