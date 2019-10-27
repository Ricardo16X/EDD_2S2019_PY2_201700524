package metodos;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {
	public static byte[] sha256(String entrada) throws NoSuchAlgorithmException{
		MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
		return mDigest.digest(entrada.getBytes(StandardCharsets.UTF_8));
	}
	
	public static String get_sha256(byte[] hash) {
		BigInteger numeroBigInteger = new BigInteger(1,hash);
		StringBuilder hexBuilder = new StringBuilder(numeroBigInteger.toString(16));
		while (hexBuilder.length() < 32) {
			hexBuilder.insert(0, '0');			
		}
		return hexBuilder.toString();
	}
}
