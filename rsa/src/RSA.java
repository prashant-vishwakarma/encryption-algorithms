import java.io.*;
import java.math.BigInteger;
import java.util.Random;

public class RSA{
	
	final static int bitLength = 1024;
	
	public static void main(String args[]) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter Plaintext:");
		String plaintext = br.readLine();
		System.out.println("Plaintext In Bytes: " + plaintext.getBytes());
		
		Random rnd = new Random();
		BigInteger p = BigInteger.probablePrime(1024, rnd);
		BigInteger q = BigInteger.probablePrime(1024, rnd);
		BigInteger N = p.multiply(q);
		BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
		
		BigInteger e = BigInteger.probablePrime(bitLength/2, rnd);
		while(phi.gcd(e).compareTo(BigInteger.ONE)>0 && e.compareTo(phi) < 0) {
			e.add(BigInteger.ONE);
		}
		BigInteger d = e.modInverse(phi);
		
		byte[] encrypt = new BigInteger(plaintext.getBytes()).modPow(e, N).toByteArray();
		System.out.println("Encrypt: " + encrypt);
		
		byte[] decrypt = new BigInteger(encrypt).modPow(d, N).toByteArray();
		System.out.println("Decrypt: " + decrypt);
		String output = new String(decrypt);
		System.out.println("Output: " + output);
	}
}
