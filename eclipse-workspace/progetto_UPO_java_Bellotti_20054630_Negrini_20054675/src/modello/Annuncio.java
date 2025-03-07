package modello;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Random;


import modello.exceptions.AnnuncioException;

public class Annuncio {
	private final int id;
	private String nome;
	private double prezzo;
	private String tipo;
	private String paroleChiave;
	private LocalDate dataScadenza;
	private final String userCreatore;
	
	/**
	 * Regex per controllo correttezza parole chiave
	 * @param paroleChiaveRegex
	 */
	private final String paroleChiaveRegex = "^(\\w+;)+\\w*;?$";

	private static final Random random = new Random();			  // ISTANZIATA VARIABILE RANDOM PER LA GENERAZIONE DI ID CASUALI
	
	
	
	private static int generaIdCasuale() {
        return 10000 + random.nextInt(90000); // Genera un numero casuale tra 10000 e 99999
    }
	

	/**
	 * Costruttore con PARAMETRO ID PER CARICAMENTO ANNUNCIO DA FILE
	 * @param id, id del'annuncio preso dal file
	 * @param nome, nomde dell'annuncio
	 * @param prezzo, prezzo dell'annuncio
	 * @param tipo, tipo dell'annuncio (vendere - acquistare)
	 * @param paroleChiave, parole chiave dell'annuncio
	 * @param dataScadenza, data scadenza dell'annuncio, NULL se tipo acquisto
	 * @param userCreatore, mail dell'utente che crea l'annuncio
	 */
	public Annuncio(int id, String nome, double prezzo, String tipo, String paroleChiave, LocalDate dataScadenza, String userCreatore ) throws AnnuncioException {
		if(!tipo.equals("acquistare") && !tipo.equals("vendere")) {
			throw new AnnuncioException("Formato tipo non valido (vendere - acquistare)");
		}
		
		if( !paroleChiave.matches( paroleChiaveRegex)) {
			throw new AnnuncioException("Formato parole chiave errato");
		}
		
		this.id = id;
		this.nome = nome;
		this.prezzo = prezzo;
		this.tipo = tipo;
		this.paroleChiave = paroleChiave;
		this.dataScadenza = dataScadenza;
		this.userCreatore = userCreatore;		
	}
	
	/**
	 * Costruttore senza PARAMETRO ID, generazione casuale
	 * @param nome, nomde dell'annuncio
	 * @param prezzo, prezzo dell'annuncio
	 * @param tipo, tipo dell'annuncio (vendere - acquistare)
	 * @param paroleChiave, parole chiave dell'annuncio
	 * @param dataScadenza, data scadenza dell'annuncio, NULL se tipo acquisto
	 * @param userCreatore, mail dell'utente che crea l'annuncio
	 */
	public Annuncio(String nome, double prezzo, String tipo, String paroleChiave, LocalDate dataScadenza, String userCreatore ) throws AnnuncioException {
		this(generaIdCasuale(), nome, prezzo, tipo, paroleChiave, dataScadenza, userCreatore);
	}
	
	//Get
	
	public int getId() {
		return id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public double getPrezzo() {
		return prezzo;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public String getParoleChiave(){
		return paroleChiave;
	}
	
	public LocalDate getData() {
		return dataScadenza;
	}
	
	public String getUserCreatore() {
		return userCreatore;
	}
	
	//------
	
	// Setter per modificare le parole chiave di un annuncio
	public void setParoleChiave(String paroleChiave) {
		this.paroleChiave = paroleChiave;
	}

	//verifica se la data odierna è maggiore della data di scadenza
	public boolean scaduto() {
		if(dataScadenza != null) {		
			if(dataScadenza.isBefore(LocalDate.now())) {
				return true;
			}		
		}
		return false;
	}
	
	//------
	
	
	@Override
	public String toString() {
		return "[id: " + id + " | Nome: " + nome + " | Prezzo: " + prezzo + " | Tipo: " + tipo + " | Parole Chiave: " + paroleChiave + " | Data Scadenza: " + dataScadenza + " | User: "+ userCreatore + "]";         
	}

	@Override
	public int hashCode() {
		return Objects.hash(dataScadenza, id, nome, paroleChiave, paroleChiaveRegex, prezzo, tipo, userCreatore);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Annuncio other = (Annuncio) obj;
		return Objects.equals(dataScadenza, other.dataScadenza) && id == other.id && Objects.equals(nome, other.nome)
				&& Objects.equals(paroleChiave, other.paroleChiave)
				&& Objects.equals(paroleChiaveRegex, other.paroleChiaveRegex)
				&& Double.doubleToLongBits(prezzo) == Double.doubleToLongBits(other.prezzo)
				&& Objects.equals(tipo, other.tipo) && Objects.equals(userCreatore, other.userCreatore);
	}
	
}
