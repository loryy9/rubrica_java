package modello;

import java.util.Objects;
import modello.exceptions.UtenteException;

public class Utente {
	private final String email;
	private final String nome;
	
	/**
	 * Regex per controllo correttezza email 
	 * @param emailReg
	 */
	private final String emailReg = "[\\w\\.]+@[\\w\\.]+\\.[\\w]{2,}";
	
	/**
	 * Regex per controllo correttezza nome
	 * @param nomeReg
	 */
	private final String nomeReg = "[A-Z][a-zA-Z\\s]*";

	
	/**
	 * Costruttore per oggetto Utente
	 * @param email, email dell'utente
	 * @param nome, nomde dell'utente
	 */
	public Utente( String email, String nome ) throws UtenteException {
		if( !email.matches( emailReg )) {
			throw new UtenteException("Formato email errato!");
		}
		if( !nome.matches( nomeReg )) {
			throw new UtenteException("Formato nome errato!");
		}
		this.email = email;
		this.nome = nome;
	}
	
	// Get 
	public String getEmail() {
		return email;
	}
	
	public String getNome() {
		return nome;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Utente other = (Utente) obj;
		return Objects.equals(email, other.email) && Objects.equals(nome, other.nome);
	}

	@Override
	public String toString() {
		return "[email=" + email + ", nome=" + nome + "]";
	}
	
}
