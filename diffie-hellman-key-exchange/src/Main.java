package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		//public variables both are prime
		int p = 997;
		int g = 167;
		
		
		System.out.println("Diffie Hellman Key Exchange");
		System.out.println("Enter Alice's Secret Number");
		int a = Integer.parseInt(br.readLine());	//Alice Decides Secret Key
		System.out.println("Enter Bob's Secret Number");
		int b = Integer.parseInt(br.readLine());	//Bob Decides Secret Key
		
		int x = (int) (Math.pow(g, a)%p);//Alice Computes X
		int y = (int) (Math.pow(g, b)%p);//Bob Computes Y
		
		int ka = (int) (Math.pow(y, a)%p);//Alice Computes X
		int kb = (int) (Math.pow(x, b)%p);//Alice Computes X

		System.out.println("Key of Alice: " + ka);
		System.out.println("Key of Bob: " + kb);
		
		if(ka == kb) {
			System.out.println("Since both keys are equal, key exchange is successful");
		}else {
			System.out.println("Unexpected Scenario");
		}

	}

}
