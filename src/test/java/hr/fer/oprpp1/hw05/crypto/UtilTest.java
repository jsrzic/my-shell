package hr.fer.oprpp1.hw05.crypto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class UtilTest {
	
	@Test
	public void testHexToByte1() {
		byte[] expected = new byte[] {1, -82, 34};
		assertArrayEquals(expected, Util.hextobyte("01aE22"));
	}
	
	@Test
	public void testHexToByte2() {
		byte[] expected = new byte[] {5, 4, 8};
		assertArrayEquals(expected, Util.hextobyte("050408"));
	}
	
	@Test
	public void testHexToByte3() {
		byte[] expected = new byte[] {1, -1, 0};
		assertArrayEquals(expected, Util.hextobyte("01fF00"));
	}
	
	@Test
	public void testHexToByte4() {
		byte[] expected = new byte[] {};
		assertArrayEquals(expected, Util.hextobyte(""));
	} 
	
	@Test
	public void testHexToByteInvalidHex() {
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("abcdefgh"));
	}
	
	@Test
	public void testHexToByteOddLength() {
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("a1b2c3d4e5f"));
	}
	
	@Test
	public void testByteToHex1() {
		String expected = "01ae22";
		assertEquals(expected, Util.bytetohex(new byte[] {1, -82, 34}));
	}
	
	@Test
	public void testByteToHex2() {
		String expected = "11ff7f00";
		assertEquals(expected, Util.bytetohex(new byte[] {17, -1, 127, 0}));
	}
	
	@Test
	public void testByteToHex3() {
		String expected = "053264";
		assertEquals(expected, Util.bytetohex(new byte[] {5, 50, 100}));
	}
	
	@Test
	public void testByteToHex4() {
		String expected = "";
		assertEquals(expected, Util.bytetohex(new byte[] {}));
	}
}
