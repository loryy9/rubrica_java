package modello.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import modello.Annuncio;
import modello.Bacheca;

import modello.exceptions.*;

class BachecaTest {

	private Bacheca bacheca;
	
	
	@BeforeEach 
	void setup(){
		bacheca = new Bacheca();
	}
	
	@AfterEach
	void svuota() {
		bacheca.getListaAnnunci().clear();
	}
	
	
	//TEST PER AGGIUNTA DI ANNUNCIO TIPO VENDITA
	@Test 
	void testAggiungiAnnuncioVendita() throws Exception {
		ArrayList<Annuncio> listaVendita;
		Annuncio annuncio1 = new Annuncio("Iphone 16", 500.00, "vendere", "apple;iphone;", LocalDate.of(2026, 02, 10), "utente 1");
		listaVendita = bacheca.nuovoAnnuncio(annuncio1);
		assertEquals(bacheca.getSize(), 1);
		Annuncio annuncio2 = new Annuncio("Iphone 15", 400.00, "vendere", "apple;iphone;", LocalDate.of(2026, 02, 10), "utente 2");
		listaVendita = bacheca.nuovoAnnuncio(annuncio2);
		assertEquals(bacheca.getSize(), 2);
	}

	//TEST PER AGGIUNTA ANNUNCIO DI TIPO ACQUISTO
	@Test
	void testAggiungiAnnuncioAcquisto() throws Exception {
		ArrayList<Annuncio> listaVendita;
		Annuncio annuncio1 = new Annuncio("Iphone 16", 500.00, "vendere", "apple;iphone;", LocalDate.of(2025, 02, 10), "utente 1");
		listaVendita = bacheca.nuovoAnnuncio(annuncio1);
		assertEquals(bacheca.getSize(), 1);
		Annuncio annuncio2 = new Annuncio("Iphone 15", 400.00, "vendere", "apple;iphone;", LocalDate.of(2026, 02, 10), "utente 2");
		listaVendita = bacheca.nuovoAnnuncio(annuncio2);
		Annuncio annuncio3 = new Annuncio("Iphone 14", 500.00, "acquistare", "apple;iphone;", LocalDate.of(2027, 02, 10), "utente 2");
		listaVendita = bacheca.nuovoAnnuncio(annuncio3);
		assertTrue(listaVendita.size()>0);
	}	
	
	//TEST ANNUNCIO GIA' ESISTENTE
	@Test
	void testControllaDuplicato() throws Exception {
		ArrayList<Annuncio> listaVendita; 
		Annuncio annuncio1 = new Annuncio("Iphone 16", 500.00, "vendere", "apple;iphone;", LocalDate.of(2025, 02, 10), "utente 1");
		listaVendita = bacheca.nuovoAnnuncio(annuncio1);
		
		BachecaException except = assertThrows(BachecaException.class, () -> {
			bacheca.nuovoAnnuncio(annuncio1);
		});
		assertEquals("Annuncio già presente", except.getMessage());
	}
	
	//TEST RIMUOVI ANNUNCIO CORRETTO
	@Test
	void testRimuoviAnnuncioOK() throws Exception {
		ArrayList<Annuncio> listaVendita; 
		Annuncio annuncio1 = new Annuncio("Iphone 16", 500.00, "vendere", "apple;iphone;", LocalDate.of(2025, 02, 10), "utente 1");
		listaVendita = bacheca.nuovoAnnuncio(annuncio1);
		assertTrue(bacheca.cancellaAnnuncio(annuncio1.getId(), "utente 1"));
	}
	
	//TEST RIMUOVI ANNUNCIO NON PRESENTE
	@Test
	void testRimuoviAnnuncioInesistente () throws Exception {
		ArrayList<Annuncio> listaVendita; 
		Annuncio annuncio1 = new Annuncio("Iphone 16", 500.00, "vendere", "apple;iphone;", LocalDate.of(2025, 02, 10), "utente 1");
		listaVendita = bacheca.nuovoAnnuncio(annuncio1);
		
		BachecaException except = assertThrows(BachecaException.class, () -> {
			bacheca.cancellaAnnuncio(2, "utente 1");
		});
		
		assertEquals("Annuncio non trovato", except.getMessage());
	}
	
	//TEST RIMUOVI ANNUNCIO UTENTE DIVERSO
	@Test
	void testRimuoviAnnuncioUtenteDiverso() throws Exception {
		ArrayList<Annuncio> listaVendita;
		Annuncio annuncio1 = new Annuncio("Iphone 16", 500.00, "vendere", "apple;iphone;", LocalDate.of(2025, 02, 10), "utente 1");
		listaVendita = bacheca.nuovoAnnuncio(annuncio1);
	
		BachecaException except = assertThrows(BachecaException.class, () -> {
			bacheca.cancellaAnnuncio(annuncio1.getId(), "utente 2");
		});
		
		assertEquals("Utente non corretto", except.getMessage());
	}
	
	//TEST PULISCI BACHECA (ANNUNCI SCADUTI)
	@Test
	void testPulisciBacheca() throws Exception {
		ArrayList<Annuncio> listaVendita;
		Annuncio annuncio1 = new Annuncio("Iphone 16", 500.00, "vendere", "apple;iphone;", LocalDate.of(2027, 02, 10), "utente 1");
		listaVendita = bacheca.nuovoAnnuncio(annuncio1);
		assertEquals(bacheca.getSize(), 1);
		Annuncio annuncio2 = new Annuncio("Iphone 15", 400.00, "vendere", "apple;iphone;", LocalDate.of(2026, 02, 10), "utente 2");
		listaVendita = bacheca.nuovoAnnuncio(annuncio2);
		Annuncio annuncio3 = new Annuncio("Iphone 14", 500.00, "acquistare", "apple;iphone;", LocalDate.of(2027, 02, 10), "utente 2");
		listaVendita = bacheca.nuovoAnnuncio(annuncio3);
		Annuncio annuncioScaduto = new Annuncio("Iphone 14", 500.00, "acquistare", "apple;iphone;", LocalDate.of(2024, 02, 10), "utente 2");
		listaVendita = bacheca.nuovoAnnuncio(annuncioScaduto);
		assertEquals(bacheca.getSize(), 4);
		bacheca.pulisciBacheca();
		assertEquals(bacheca.getSize(), 3);
	}
	
	//TEST CERCA PAROLE CHIAVE TROVATA
	@Test 
	void testRicercaParolaChiavePresente() throws Exception {
		ArrayList<Annuncio> listaVendita;
		ArrayList<Annuncio> listaRicercata;
		Annuncio annuncio1 = new Annuncio("Iphone 16", 500.00, "vendere", "iphone;", LocalDate.of(2027, 02, 10), "utente 1");
		listaVendita = bacheca.nuovoAnnuncio(annuncio1);
		assertEquals(bacheca.getSize(), 1);
		Annuncio annuncio2 = new Annuncio("Iphone 15", 400.00, "vendere", "apple;iphone;", LocalDate.of(2026, 02, 10), "utente 2");
		listaVendita = bacheca.nuovoAnnuncio(annuncio2);
		Annuncio annuncio3 = new Annuncio("Iphone 14", 500.00, "acquistare", "apple;iphone;", LocalDate.of(2027, 02, 10), "utente 2");
		listaVendita = bacheca.nuovoAnnuncio(annuncio3);
		
		listaRicercata = bacheca.cercaAnnunci("apple");
		assertEquals(listaRicercata.size(), 2);
	}
	
	//TEST CERCA PAROLE CHIAVE NON TROVATA
	@Test 
	void testRicercaParolaChiaveAssente() throws Exception {
		ArrayList<Annuncio> listaVendita;
		ArrayList<Annuncio> listaRicercata;
		Annuncio annuncio1 = new Annuncio("Iphone 16", 500.00, "vendere", "iphone;", LocalDate.of(2027, 02, 10), "utente 1");
		listaVendita = bacheca.nuovoAnnuncio(annuncio1);
		assertEquals(bacheca.getSize(), 1);
		Annuncio annuncio2 = new Annuncio("Iphone 15", 400.00, "vendere", "apple;iphone;", LocalDate.of(2026, 02, 10), "utente 2");
		listaVendita = bacheca.nuovoAnnuncio(annuncio2);
		Annuncio annuncio3 = new Annuncio("Iphone 14", 500.00, "acquistare", "apple;iphone;", LocalDate.of(2027, 02, 10), "utente 2");
		listaVendita = bacheca.nuovoAnnuncio(annuncio3);
		
		listaRicercata = bacheca.cercaAnnunci("samsung");
		assertEquals(listaRicercata.size(), 0);
	}
	
	//TEST AGGIUNGI PAROLA CHIAVE ANNUNCIO TROVATO
	@Test 
	void testAggiungiParolaChiaveAnnuncioOK () throws Exception {
		ArrayList<Annuncio> listaVendita;
		Annuncio annuncio1 = new Annuncio("Iphone 16", 500.00, "vendere", "apple;iphone;", LocalDate.of(2026, 02, 10), "utente 1");
		listaVendita = bacheca.nuovoAnnuncio(annuncio1);
		bacheca.aggiungiParolaChiave(annuncio1.getId(), "utente 1", "telefono");
		assertEquals("apple;iphone;telefono;", annuncio1.getParoleChiave());
	}

	//TEST AGGIUNGI PAROLA CHIAVE ANNUNCIO NON TROVATO
	@Test 
	void testAggiungiParolaChiaveAnnuncioInesistente () throws Exception {
		ArrayList<Annuncio> listaVendita;
		Annuncio annuncio1 = new Annuncio("Iphone 16", 500.00, "vendere", "apple;iphone;", LocalDate.of(2026, 02, 10), "utente 1");
		listaVendita = bacheca.nuovoAnnuncio(annuncio1);
		
		BachecaException except = assertThrows(BachecaException.class, () -> {
			bacheca.aggiungiParolaChiave(99, "utente 1", "telefono");
		});
		
		assertEquals("Annuncio non trovato", except.getMessage());
	}
	
	//TEST AGGIUNGI PAROLA CHIAVE ANNUNCIO NON TROVATO
	@Test 
	void testAggiungiParolaChiaveUtenteErrato () throws Exception {
		ArrayList<Annuncio> listaVendita;
		Annuncio annuncio1 = new Annuncio("Iphone 16", 500.00, "vendere", "apple;iphone;", LocalDate.of(2026, 02, 10), "utente 1");
		listaVendita = bacheca.nuovoAnnuncio(annuncio1);
		
		BachecaException except = assertThrows(BachecaException.class, () -> {
			bacheca.aggiungiParolaChiave(annuncio1.getId(), "utente 2", "telefono");
		});
		
		assertEquals("Utente non corretto", except.getMessage());
	}

	//TEST SCRITTURA E LETTURA FILE
	@Test
	void testScritturaLetturaFile () throws Exception {
		ArrayList<Annuncio> listaVendita;
		Annuncio annuncio1 = new Annuncio("Iphone 16", 500.00, "vendere", "apple;iphone;", LocalDate.of(2027, 02, 10), "utente 1");
		listaVendita = bacheca.nuovoAnnuncio(annuncio1);
		Annuncio annuncio2 = new Annuncio("Iphone 15", 400.00, "vendere", "apple;iphone;", LocalDate.of(2026, 02, 10), "utente 2");
		listaVendita = bacheca.nuovoAnnuncio(annuncio2);
		assertEquals(bacheca.getSize(), 2);
		bacheca.scriviFile("src/bacheca.txt");
		bacheca.getListaAnnunci().clear();
		assertEquals(bacheca.getSize(), 0);
		bacheca.caricaFile("src/bacheca.txt");
		assertEquals(bacheca.getSize(), 2);
	}
}
