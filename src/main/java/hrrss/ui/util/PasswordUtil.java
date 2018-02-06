package hrrss.ui.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public class PasswordUtil {
	
	private static final String SALT = "92834zlka98(/Ualasdflasdjf";

	public static String getHashedPw(String password) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		byte[] dig = md.digest(password.concat(SALT).getBytes());
		return byteArray2Hex(dig);
	}
	
	private static String byteArray2Hex(final byte[] hash) {
	    Formatter formatter = new Formatter();
	    for (byte b : hash) {
	        formatter.format("%02x", b);
	    }
	    return formatter.toString();
	}
	
	public static String generatePW() {
		String password = Integer.toString((int) (Math.random() * Integer.MAX_VALUE), 36);
		return password;
	}
}
