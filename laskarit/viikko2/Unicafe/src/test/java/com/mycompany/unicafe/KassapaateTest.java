package com.mycompany.unicafe;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class KassapaateTest {
	Kassapaate kassa;	
	Maksukortti kortti;
	
	public KassapaateTest() {
	}
	
	@Before
	public void setUp() {
		kassa = new Kassapaate();	
		kortti = new Maksukortti(1000);
	}

	@Test
	public void alkuRaha() {
		assertEquals(100000, kassa.kassassaRahaa());
	}

	@Test
	public void alkuLounaatMyyty() {
		assertEquals(0, kassa.maukkaitaLounaitaMyyty() + kassa.edullisiaLounaitaMyyty());
	}

	@Test
	public void edullinenKasvattaaRahamaaraa() {
		kassa.syoEdullisesti(240);
		assertEquals(100240, kassa.kassassaRahaa());
	}

	@Test
	public void maukasKasvattaaRahamaaraa() {
		kassa.syoMaukkaasti(400);
		assertEquals(100400, kassa.kassassaRahaa());
	}

	@Test
	public void edullinenVaihtoRaha() {
		assertEquals(60, kassa.syoEdullisesti(300));
	}

	@Test
	public void maukasVaihtoRaha() {
		assertEquals(10, kassa.syoMaukkaasti(410));
	}

	@Test
	public void edullinenKasvattaaMyytyja() {
		kassa.syoEdullisesti(240);
		assertEquals(1, kassa.edullisiaLounaitaMyyty());
	}

	@Test
	public void maukasKasvattaaMyytyja() {
		kassa.syoMaukkaasti(400);
		assertEquals(1, kassa.maukkaitaLounaitaMyyty());
	}
	
	@Test
	public void riittamatonEdullinenVaihtoraha() {
		assertEquals(230, kassa.syoEdullisesti(230));
	}

	@Test
	public void riittamatonMaukasVaihtoraha() {
		assertEquals(230, kassa.syoMaukkaasti(230));
	}

	@Test
	public void riittamatonEdullinenMyytyMaara() {
		kassa.syoEdullisesti(230);
		assertEquals(0, kassa.edullisiaLounaitaMyyty());
	}

	@Test
	public void riittamatonMaukasMyytyMaara() {
		kassa.syoMaukkaasti(230);
		assertEquals(0, kassa.maukkaitaLounaitaMyyty());
	}

	@Test
	public void riittamatonEdullinenRaha() {
		kassa.syoEdullisesti(230);
		assertEquals(100000, kassa.kassassaRahaa());
	}

	@Test
	public void riittamatonMaukasRaha() {
		kassa.syoMaukkaasti(230);
		assertEquals(100000, kassa.kassassaRahaa());
	}

	@Test
	public void edullinenKorttiVeloitus() {
		kassa.syoEdullisesti(kortti);
		assertEquals(760, kortti.saldo());
	}

	@Test
	public void maukasKorttiVeloitus() {
		kassa.syoMaukkaasti(kortti);
		assertEquals(600, kortti.saldo());
	}

	@Test
	public void edullinenKorttiTrue() {
		assertTrue(kassa.syoEdullisesti(kortti));
	}

	@Test
	public void edullinenKorttiFalse() {
		kassa.syoEdullisesti(kortti);
		kassa.syoEdullisesti(kortti);
		kassa.syoEdullisesti(kortti);
		kassa.syoEdullisesti(kortti);
		assertTrue(!kassa.syoEdullisesti(kortti));
	}

	@Test
	public void maukasKorttiTrue() {
		assertTrue(kassa.syoMaukkaasti(kortti));
	}

	@Test
	public void maukasKorttiFalse() {
		kassa.syoMaukkaasti(kortti);
		kassa.syoMaukkaasti(kortti);
		assertTrue(!kassa.syoMaukkaasti(kortti));
	}

	@Test
	public void edullinenKorttiMyytyMaara() {
		kassa.syoEdullisesti(kortti);
		assertEquals(1, kassa.edullisiaLounaitaMyyty());
	}

	@Test
	public void maukasKorttiMyytyMaara() {
		kassa.syoMaukkaasti(kortti);
		assertEquals(1, kassa.maukkaitaLounaitaMyyty());
	}

	@Test
	public void riittamatonEdullinenKorttiMyytyMaara() {
		kassa.syoEdullisesti(new Maksukortti(0));
		assertEquals(0, kassa.edullisiaLounaitaMyyty());
	}

	@Test
	public void riittamatonMaukasKorttiMyytyMaara() {
		kassa.syoMaukkaasti(new Maksukortti(0));
		assertEquals(0, kassa.maukkaitaLounaitaMyyty());
	}
	
	@Test
	public void riittamatonEdullinenKorttiRaha() {
		kassa.syoEdullisesti(kortti);
		kassa.syoEdullisesti(kortti);
		kassa.syoEdullisesti(kortti);
		kassa.syoEdullisesti(kortti);
		kassa.syoEdullisesti(kortti);
		assertEquals(40, kortti.saldo());
	}

	@Test
	public void riittamatonMaukasKorttiRaha() {
		kassa.syoMaukkaasti(kortti);
		kassa.syoMaukkaasti(kortti);
		kassa.syoMaukkaasti(kortti);
		assertEquals(200, kortti.saldo());
	}

	@Test
	public void edullinenKorttiEiMuutaKassaa() {
		kassa.syoEdullisesti(kortti);
		assertEquals(100000, kassa.kassassaRahaa());
	}

	@Test
	public void maukasKorttiEiMuutaKassaa() {
		kassa.syoMaukkaasti(kortti);
		assertEquals(100000, kassa.kassassaRahaa());
	}

	@Test
	public void korttiLatausSaldo() {
		kassa.lataaRahaaKortille(kortti, 10);
		assertEquals(1010, kortti.saldo());
	}

	@Test
	public void korttiLatausKassaRaha() {
		kassa.lataaRahaaKortille(kortti, 10);
		assertEquals(100010, kassa.kassassaRahaa());
	}

	@Test
	public void negatiivinenKorttiLatausKassaRaha() {
		kassa.lataaRahaaKortille(kortti, -10);
		assertEquals(100000, kassa.kassassaRahaa());
	}
}
