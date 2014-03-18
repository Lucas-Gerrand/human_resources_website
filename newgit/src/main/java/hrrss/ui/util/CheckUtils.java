package hrrss.ui.util;

public class CheckUtils {

	
	public static Boolean checkTwoDates(String from, String till) {
		String[] buf;
    	buf = from.split("-");
    	Integer fromYear = Integer.parseInt(buf[0]);
    	Integer fromMonth = Integer.parseInt(buf[1]);
    	Integer fromDay = Integer.parseInt(buf[2]);
    	
    	String[] buf1;
    	buf1 = till.split("-");
    	Integer tillYear = Integer.parseInt(buf1[0]);
    	Integer tillMonth = Integer.parseInt(buf1[1]);
    	Integer tillDay = Integer.parseInt(buf1[2]);
    	
    	if(fromYear < tillYear) {
    		return true;
    		
    	}
    	
    	if(fromYear > tillYear) {
    		return false;
    	}
    	
    	if(fromYear.toString().equals(tillYear.toString())) {
    		if(fromMonth < tillMonth) {
    			return true;
    		}
    		if(fromMonth > tillMonth) {
    			return false;
    		}
    		if(fromMonth.toString().equals(tillMonth.toString())) {
    			if(fromDay < tillDay) {
    				return true;
    			} else {
    				return false;
    			}
    		}
    	}
    	return false;
	}
	
	 public static Boolean checkDate(String date) {
	    	String[] buf;
	    	buf = date.split("-");
	    	if(buf.length == 3) {
	    		String year = buf[0];
	    		String month = buf[1];
	    		String day = buf[2];
	    		Integer yearInt;
	    		Integer monthInt;
	    		Integer dayInt;
	    		if(year.length() == 4) {
	    			try {
	    				yearInt = Integer.parseInt(year);
	    				if(month.length() == 2) {
	    					monthInt = Integer.parseInt(month);
	    					if(day.length() == 2) {
	    						dayInt = Integer.parseInt(day);
	    						
	    						if(yearInt > 1880 && yearInt < 2014) {
	    							if(monthInt > 0 && monthInt < 13) {
	    								if(dayInt > 0 && dayInt < 32) {
	    									return true;
	    								} else {
	    									return false;
	    								}
	    							} else {
	    								return false;
	    							}
	    						} else {
	    							return false;
	    						}
	    					}
	    				}
	    			} catch(Exception e) {
	    				return false;
	    			}
	    			
	    		} else {
	    			return false;
	    		}
	    	} else {
	    		return false;
	    	}
			return false;
	    }
	 
	 

}
