package hrrss.ui.util;

public class RoundUtil {
	public static double round2(double num) {
		double result = num * 100;
		result = Math.round(result);
		result = result / 100;
		return result;
	}
	
	public static double round3(double num) {
		double result = num * 10000;
		result = Math.round(result);
		result = result / 10000;
		return result;
	}
}
