package modello.test;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import modello.Utente;
import modello.exceptions.UtenteException;

class UtenteTest {
	private Utente user;	
	
	@BeforeEach
	void logIn() throws UtenteException{
		user = new Utente("prova@gmail.com", "Prova");
	}
	
	@AfterEach
	void logOut() {
		user = null;
	}
	
	@Test
	void testCostruttore() throws UtenteException{
		user = new Utente("lorenzo@gmail.com", "Lorenzo");
		assertEquals("lorenzo@gmail.com", user.getEmail());
		assertEquals("Lorenzo", user.getNome());
	}
	
	@Test
	void testCostruttoreEccezioniEmail() {
		UtenteException except = assertThrows(UtenteException.class, () -> new Utente("errore?@test.it","Prova"));
		assertEquals("Formato email errato!", except.getMessage());	
		UtenteException except2 = assertThrows(UtenteException.class, () -> new Utente("erroretest.it","Prova"));
		assertEquals("Formato email errato!", except2.getMessage());	
		UtenteException except3 = assertThrows(UtenteException.class, () -> new Utente("errore@testit","Prova"));
		assertEquals("Formato email errato!", except3.getMessage());
		UtenteException except4 = assertThrows(UtenteException.class, () -> new Utente("errore@test.i","Prova"));
		assertEquals("Formato email errato!", except4.getMessage());	
	}
	
	@Test 
	void testCostruttoreEccezioniNome() {
		UtenteException except = assertThrows(UtenteException.class, () -> new Utente("lorenzo@gmail.com", "Lorenzo1"));
		assertEquals("Formato nome errato!", except.getMessage());
	}
}

