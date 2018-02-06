package hrrss.ui.util;

public class StringUtil {

	
	public static String stringWithNewline(String text) {
		text = text.replaceAll("(\r\n|\n)", "<br />");
		return text;
	}
	public static String stringWithNewlineBackslashN(String text) {
		text = text.replaceAll("(\r\n|\n)", "\n");
		return text;
	}
	 
	 

}
