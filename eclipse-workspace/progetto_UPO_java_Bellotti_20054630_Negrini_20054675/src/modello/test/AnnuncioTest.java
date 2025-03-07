package modello.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import modello.Annuncio;
import modello.exceptions.AnnuncioException;

class AnnuncioTest {
	private Annuncio annuncio;

//	@BeforeEach 
//	void addAnnuncio() throws AnnuncioException{
//		annuncio = new Annuncio("Iphone 16", 500.00, "vendere", "apple;iphone", LocalDate.of(2025, 02, 10), "utente1");
//	}
	
//	@AfterEach
//	void removeAnnuncio() {
//		annuncio = null;
//	}
	
	// test costruttore con id randomico
	@Test
	void testCostruttore() throws AnnuncioException{
		annuncio = new Annuncio("Iphone 16", 500.00, "vendere", "apple;iphone;", LocalDate.of(2026, 02, 10), "utente1");
		assertEquals("Iphone 16", annuncio.getNome());
		assertEquals(500.00, annuncio.getPrezzo());
		assertEquals("vendere", annuncio.getTipo());
		assertTrue(annuncio.getParoleChiave().contains("apple"));
		assertEquals(LocalDate.of(2026, 02, 10), annuncio.getData());
		assertEquals("utente1", annuncio.getUserCreatore());
	}
	// test costruttore con id caricato da file
	@Test
	void testCostruttoreID() throws AnnuncioException{
		annuncio = new Annuncio(1,"Iphone 16", 500.00, "vendere", "apple;iphone;", LocalDate.of(2026, 02, 10), "utente1");
		assertEquals(1, annuncio.getId());
		assertEquals("Iphone 16", annuncio.getNome());
		assertEquals(500.00, annuncio.getPrezzo());
		assertEquals("vendere", annuncio.getTipo());
		assertTrue(annuncio.getParoleChiave().contains("apple"));
		assertEquals(LocalDate.of(2026, 02, 10), annuncio.getData());
		assertEquals("utente1", annuncio.getUserCreatore());
	}
	
	// test inserimento tipo errato
	@Test
	void testControlloTipo() {
		AnnuncioException except = assertThrows(AnnuncioException.class, () -> new Annuncio("Iphone 16", 500.00, "prova", "apple;iphone", LocalDate.of(2025, 02, 10), "utente1"));
		assertEquals("Formato tipo non valido (vendere - acquistare)", except.getMessage());
	}
	
	// test per verificare se l'annuncio è scaduto
	@Test
	void testScadenza() throws AnnuncioException{
		annuncio = new Annuncio("Iphone 16", 500.00, "vendere","apple;iphone;", LocalDate.of(2026, 02, 12), "utente1");
		assertFalse(annuncio.scaduto());
		annuncio = new Annuncio("Iphone 16", 500.00, "vendere", "apple;iphone;", LocalDate.of(2024, 02, 10), "utente1");
		assertTrue(annuncio.scaduto());
	}
	
	@Test
	void testParoleChiave() throws AnnuncioException{
		AnnuncioException except = assertThrows(AnnuncioException.class, () -> new Annuncio("Iphone 16", 500.00, "acquistare", "apple iphone", LocalDate.of(2025, 02, 10), "utente1"));
		assertEquals("Formato parole chiave errato", except.getMessage());
	}

}

