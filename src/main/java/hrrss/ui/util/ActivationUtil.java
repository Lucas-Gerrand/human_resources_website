package hrrss.ui.util;

import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class ActivationUtil {
	
	public static String getActivation() {
		char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 20; i++) {
		    char c = chars[random.nextInt(chars.length)];
		    sb.append(c);
		}
		String output = sb.toString();
	
		return output;
	}

	
}
