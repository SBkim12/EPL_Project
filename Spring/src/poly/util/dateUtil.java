package poly.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class dateUtil {
	
	//오늘 날짜
	private static Date date = new Date(Calendar.getInstance().getTimeInMillis());
	//어제 날짜
	private static Date yesterday_date = new Date(date.getTime()+(1000*60*60*24*-1));
	
	//날짜 형식
	private static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
	private static SimpleDateFormat day = new SimpleDateFormat("d", Locale.UK);
	private static SimpleDateFormat en_month = new SimpleDateFormat("MMMM", Locale.UK);
	
	//활용
	public static String today = SDF.format(date);
	public static String today_day = day.format(date);
	public static String today_month = en_month.format(date);
	
	public static String yesterday = SDF.format(yesterday_date);
	public static String yesterday_day = day.format(yesterday_date);
	public static String yesterday_month = en_month.format(yesterday_date);
	
}
