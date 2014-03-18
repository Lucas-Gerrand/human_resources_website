package hrrss.test.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.Test;

public class TestUtils {

    public void parseString() {

	String start_dt = "2011-01-01";
	DateFormat formatter = new SimpleDateFormat("YYYY-MM-DD");
	Date date = new Date();
	try {
	    date = (Date) formatter.parse(start_dt);
	} catch (ParseException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	SimpleDateFormat newFormat = new SimpleDateFormat("MM-dd-yyyy");
	String finalString = newFormat.format(date);

	System.out.println(date);
    }

    @Test
    public void parseString2() {

	String string = "05/12/2012";
	Date date = new Date();
	try {
	    date = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
		    .parse(string);
	} catch (ParseException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	System.out.println(date); // Sat Jan 02 00:00:00 BOT 2010

    }
}
