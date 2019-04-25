package ui;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter PlainText:");
		String plaintext = br.readLine();
		int shift = 5;
		
		String cipher = encrypt(plaintext, shift);
		String plain = decrypt(cipher, shift);

		System.out.println(cipher);
		System.out.println(plain);
	}
	
	static String encrypt(String string, int shift) {
		String ciphertext = "";
		for(int i = 0; i < string.length(); i++) {
			ciphertext += (char)((int)string.charAt(i) + shift);
		}
		return ciphertext;
	}
	
	static String decrypt(String string, int shift) {
		String ciphertext = "";
		for(int i = 0; i < string.length(); i++) {
			ciphertext += (char)((int)string.charAt(i) - shift);
		}
		return ciphertext;
	}

}
