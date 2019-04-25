package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {

	static int[] init_perm = { 1, 5, 2, 0, 3, 7, 4, 6 };
	static int[] final_perm = { 3, 0, 2, 4, 6, 1, 7, 5 };
	static int[] e_p = { 3, 0, 1, 2, 1, 2, 3, 0 };
	static String[][] s_0 = { { "01", "00", "11", "10" }, { "11", "10", "01", "00" }, { "00", "10", "01", "11" },
			{ "11", "01", "11", "10" } }; // check
	static String[][] s_1 = { { "00", "01", "10", "11" }, { "10", "00", "01", "11" }, { "11", "00", "01", "00" },
			{ "10", "01", "00", "11" } }; // check
	static int[] p_4 = { 1, 3, 2, 0 };

	public static void main(String[] args) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int[] p_10 = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 }; // P10 Order
		int[] p_8 = { 0, 1, 2, 3, 4, 5, 6, 7 }; // P8 Order

		System.out.println("Enter 10 bit Key");
		// String key = br.readLine();
		String key = "1010100110";

		System.out.println("Applying Initial Permutation to " + key);
		String initial_permutation_key = "";
		for (int i = 0; i < 10; i++) {
			// System.out.println(initial_permutation[i]);
			initial_permutation_key += key.charAt(p_10[i]); // first permutation P10
			// System.out.println(initial_permutation_key);
		}

		String left = initial_permutation_key.substring(0, 4); // divide left half
		String right = initial_permutation_key.substring(5, 9); // divide right half
		System.out.println("Left: " + left);
		System.out.println("Right: " + right);

		left = leftShift(left, 1); // LS1 left
		right = leftShift(right, 1); // LS1 right

		String first_key = "";
		for (int i = 0; i < 8; i++) {
			first_key += (left + right).charAt(p_8[i]); // apply P8 to get first_key
			// System.out.println((left+right).charAt(i));
		}

		left = leftShift(left, 2); // LS2 left
		right = leftShift(right, 2); // LS2 right

		String second_key = "";
		for (int i = 0; i < 8; i++) {
			second_key += (left + right).charAt(p_8[i]); // apply P8 to get second_key
			// System.out.println((left+right).charAt(i));
		}

		System.out.println("First Key: " + first_key);
		System.out.println("Second Key: " + second_key);

		System.out.println("Enter plain text in 8-bit binary format");
		// String plain_text=br.readLine();
		String plain_text = "01101101";
		String sample_first_key = "10100100";
		String sample_second_key = "01000011";

		
		System.out.println("PlainText: " + plain_text);
		String cipher = permutation(plain_text, init_perm);
		String ciper_text = encrypt(sample_first_key, cipher);
		System.out.println("FirstRoundOutput: " + ciper_text);
		
		 ciper_text = encrypt(sample_second_key, ciper_text);
		System.out.println("cc:"+ciper_text);
		ciper_text= ciper_text.substring(4,8) + ciper_text.substring(0,4);
		ciper_text= permutation(ciper_text, final_perm);
		System.out.println("SecondRoundOutput: " + ciper_text);
		
		System.out.println("Decryption");
		String decode = permutation(ciper_text, init_perm);
		decode = encrypt(sample_second_key, decode);
		
		decode = encrypt(sample_first_key, decode);
		decode = decode.substring(4,8) + decode.substring(0,4);
		decode = permutation(decode, final_perm);
		
		System.out.println("Decrypted: " + decode);
	}

	static String encrypt(String key, String cipher) {
		//String cipher = "";
		
		 // step 1
		System.out.println("Cipher: " + cipher);
		String cipher_ls = cipher.substring(0, 4);
		String cipher_rs = cipher.substring(4, 8);
		
		// System.out.println("EP: " + e_p[0]);
		System.out.println("Cipher LS: " + cipher_ls);
		System.out.println("Cipher RS: " + cipher_rs);
		
		String updated_rs_l = permutation(cipher_rs, Arrays.copyOfRange(e_p, 0, 4));
		String updated_rs_r = permutation(cipher_rs, Arrays.copyOfRange(e_p, 4, 8));
		// String updated_rs = permuatation(cipher_rs, Arrays.copyOfRange(e_p, 0, 3)) +
		// permuatation(cipher_rs, Arrays.copyOfRange(e_p, 4, 7));
		String updated_rs = updated_rs_l + updated_rs_r;
		System.out.println("Expanded: " + updated_rs);
		
		updated_rs=Exor(updated_rs, key);
		
		String s0 = updated_rs.substring(0, 4);
		String s1 = updated_rs.substring(4, 8);
		System.out.println("S0: " + s0);
		System.out.println("S1: " + s1);
		
		int row_s0 = binary(s0.charAt(0), s0.charAt(3));
		int col_s0 = binary(s0.charAt(1), s0.charAt(2));
		System.out.println("s0_row :"+row_s0+"s0_col :"+col_s0);
		
		int row_s1 = binary(s1.charAt(0), s1.charAt(3));
		int col_s1 = binary(s1.charAt(1), s1.charAt(2));
		System.out.println("s1_row :"+row_s1+"s1_col :"+col_s1);

		
		String table_output = s_0[row_s0][col_s0] + s_1[row_s1][col_s1];
		System.out.println("Table Output: " + table_output);
		cipher = permutation(table_output, p_4);
		System.out.println("p_4 ;"+cipher);
		cipher = cipher_rs + Exor(cipher_ls, cipher);
		
		return cipher;
	}
	
	static String leftShift(String string, int shift) throws Exception {
		if (shift < string.length())
			return (string.substring(shift) + string.substring(0, shift));
		else
			throw new Exception("Range Out of Bound");
	}

	static String permutation(String string, int[] order) {
		String permutation = "";
		//System.out.println("Order Length: " + order.length);
		for (int i = 0; i < order.length; i++) {
			permutation += string.charAt(order[i]); //
			//System.out.println(permutation);
		}

		return permutation;
	}

	static String doEXOR(String string1, String string2) {
		return Integer.toString((Integer.parseInt(string1) ^ Integer.parseInt(string2)));
	}
	
	
	static int binary(char i, char j) {
		int num = 0;
		if (i == '0' && j == '0') {
			num = 0;
		}
		if (i == '0' && j == '1') {
			num = 1;
		}
		if (i == '1' && j == '0') {
			num = 2;
		}
		if (i == '1' && j == '1') {
			num = 3;
		}
		return num;
	}

	static String Exor(String string, String key) {
		for (int i = 0; i < string.length(); i++) // step 3
		{
			if ((string.charAt(i) == key.charAt(i))) {
				string = string.substring(0, i) + "0" + string.substring(i + 1); // kya karu
			} else {
				string = string.substring(0, i) + "1" + string.substring(i + 1); // kya karu
			}
		}
		return string;
	}

}
