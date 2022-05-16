package hr.fer.oprpp1.hw05.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Program used for encryption/decryption of files using the AES crypto-algorithm and the 128-bit encryption key or calculating and checking the SHA-256 file digest.
 * First argument can be either checksha (to calculate file digest) or encrypt/decrypt (for encryption/decryption).
 * If first argument is checksha, program expects file path as a second argument.
 * If first argument is encrypt/decrypt, program expects old file as second argument and new file as third argument.
 * @author Josip
 *
 */
public class Crypto {
	public static void main(String[] args) {
		if (args.length == 2) {
			if (!args[0].equals("checksha")) {
				System.out.println("Invalid command.");
				return; 
			}
			
			String expectedDigest;
			MessageDigest md;
			String digest;
			
			try {
				md = MessageDigest.getInstance("SHA-256");
			} catch (NoSuchAlgorithmException e) {
				System.out.println("Internal error. Invalid hash algorithm.");
				return;
			}
			
			System.out.println("Please provide expected sha-256 digest for " + args[1] + ":");
			System.out.print("> ");
			
			
			try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
				expectedDigest = br.readLine();
			} catch (IOException e) {
				System.out.println("Error while reading user input.");
				return;
			}
			
			
			try (BufferedInputStream in = new BufferedInputStream(Files.newInputStream(Paths.get(args[1])), 4096)) {
				byte[] b = new byte[4096];
				int r;
				while ((r = in.read(b)) > 0) {
					md.update(b, 0, r);
				}
				byte[] digestByteArray = md.digest();
				digest = Util.bytetohex(digestByteArray);
			} catch (IOException e) {
				System.out.println("Error while reading file.");
				return;
			}
			
			boolean matches = expectedDigest.equals(digest);
			
			if (matches) {
				System.out.printf("Digesting completed. Digest of %s matches expected digest.\n", args[1]);
			}
			else {
				System.out.printf("Digesting completed. Digest of %s does not match the expected digest. Digest was: %s\n",
						args[1], digest);
			}
						
		}
		
		
		else if (args.length == 3) {
			if (!args[0].equals("encrypt") && !args[0].equals("decrypt")) {
				System.out.println("Invalid command.");
				return; 
			}
			
			String keyText;
			String ivText;
			boolean encrypt = args[0].equals("encrypt");
			
			try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
				System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
				System.out.print("> ");
				keyText = br.readLine();
				System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
				System.out.print("> ");
				ivText = br.readLine();
			} catch (IOException e) {
				System.out.println("Error while reading user input.");
				return;
			}
			
			SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
			Cipher cipher;
			try {
				cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			} catch (NoSuchAlgorithmException e1) {
				System.out.println("Internal error. Invalid algorithm name.");
				return;
			} catch (NoSuchPaddingException e1) {
				System.out.println("Internal error. Particular padding mechanicsm not available in this environment.");
				return;
			}
			try {
				cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
			} catch (InvalidKeyException e) {
				System.out.println("Invalid key.");
				return;
			} catch (InvalidAlgorithmParameterException e) {
				System.out.println("Internal error. Invalid algorithm name.");
				return;
			}
			
			
			try (BufferedInputStream in = new BufferedInputStream(Files.newInputStream(Paths.get(args[1])), 4096);
					BufferedOutputStream out = new BufferedOutputStream(Files.newOutputStream(Paths.get(args[2])), 4096)) {
				byte[] b = new byte[4096];
				int r;
				byte[] data;
				while ((r = in.read(b)) > 0) {
					data = cipher.update(b, 0, r);
					if (data != null) {
						out.write(data);
					}
				}
				try {
					data = cipher.doFinal();
					if (data != null) {
						out.write(data);
					}
				} catch (IllegalBlockSizeException | BadPaddingException e) {
					System.out.println("Internal error while crypting/decrypting data.");
					e.printStackTrace();
					return;
				}
			} catch (IOException e) {
				System.out.println("Error while reading file.");
				return;
			}
			
			if (encrypt) {
				System.out.printf("Encryption completed. Generated file %s based on file %s.\n", args[2], args[1]);
			}
			else {
				System.out.printf("Decryption completed. Generated file %s based on file %s.\n", args[2], args[1]);
			}
			
			
		}
		else {
			System.out.println("Invalid number of arguments.");
			return;
		}
	}
}
