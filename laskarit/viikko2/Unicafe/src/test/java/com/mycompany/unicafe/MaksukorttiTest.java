package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }

	@Test
	public void alkuSaldo() {
		assertEquals("saldo: 0.10", kortti.toString());
	}

	@Test
	public void saldonLisays() {
		kortti.lataaRahaa(100);
		assertEquals("saldo: 1.10", kortti.toString());
	}

	@Test
	public void saldonVahennys() {
		kortti.otaRahaa(1);
		assertEquals("saldo: 0.9", kortti.toString());
	}

	@Test
	public void saldonYlivahennys() {
		kortti.otaRahaa(11);
		assertEquals("saldo: 0.10", kortti.toString());
	}

	@Test
	public void otaRahaaPalauttaaTrue() {
		assertTrue(kortti.otaRahaa(10));
	}

	@Test
	public void otaRahaaPalauttaaFalse() {
		assertTrue(!kortti.otaRahaa(11));
	}

	@Test
	public void saldoTest() {
		assertEquals(10, kortti.saldo());
	}
}
