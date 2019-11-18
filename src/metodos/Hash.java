package metodos;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Hash {
	
	String resultado;
	static Date fecha = new Date();
	static SimpleDateFormat formateo = new SimpleDateFormat("HH:mm:ss dd-MMM-yyyy");
	
	
	
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
	
	public static String timestamp() {
		fecha = new Date();
		return formateo.format(fecha);
	}
}
