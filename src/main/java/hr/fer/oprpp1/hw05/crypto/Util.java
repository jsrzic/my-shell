package hr.fer.oprpp1.hw05.crypto;

/**
 * Utility class providing methods which are used for conversion from byte[] to hex value, and vice versa.
 * @author Josip
 *
 */
public class Util {
	public static byte[] hextobyte(String keyText) {
		if(keyText.length() % 2 != 0 || !keyText.matches("[0-9a-fA-f]*")) {
			throw new IllegalArgumentException("Invalid hex string.");
		}
		
		byte[] result = new byte[keyText.length() / 2];
		
		for (int i = 0; i < keyText.length(); i += 2) {
			byte b = (byte) (16 * convertCharToValue(keyText.charAt(i)) + convertCharToValue(keyText.charAt(i + 1)));
			result[i/2] = b;
		}
		
		return result;
	}
	
	public static String bytetohex(byte[] bytearray) {
		StringBuilder sb = new StringBuilder(bytearray.length * 2);
		
		for (int i = 0;  i < bytearray.length; i++) {
			int v = (int) bytearray[i];
			v = v < 0 ? v + 256 : v;
			char c1 = convertValueToChar(v % 16);
			v /= 16;
			char c2 = convertValueToChar(v % 16);
			sb.append(c2);
			sb.append(c1);
		}
		return sb.toString();
	}
	
	private static int convertCharToValue(char c) {
		return switch(c) {
			case 'A', 'a' -> 10;
			case 'B', 'b' -> 11;
			case 'C', 'c' -> 12;
			case 'D', 'd' -> 13;
			case 'E', 'e' -> 14;
			case 'F', 'f' -> 15;
			default -> c - '0';
		};
	}
	
	private static char convertValueToChar(int v) {
		return switch(v) {
			case 10 -> 'a';
			case 11 -> 'b';
			case 12 -> 'c';
			case 13 -> 'd';
			case 14 -> 'e';
			case 15 -> 'f';
			default -> (char) (v + '0');
		};
	}
}
