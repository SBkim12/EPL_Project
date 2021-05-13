package poly.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class test {

	public static void main(String[] args) {
		Date date = new Date(Calendar.getInstance().getTimeInMillis());

		SimpleDateFormat time = new SimpleDateFormat("d MMMM", Locale.UK);
		
		System.out.println(time.format(date));
	}

}
