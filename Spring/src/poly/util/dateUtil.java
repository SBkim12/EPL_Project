package poly.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class dateUtil {
	
	private static Calendar cal = Calendar.getInstance();
	
	//현재시간
	private static Date now = new Date(cal.getTimeInMillis());
	//어제시간
	private static Date date = new Date(now.getTime()+(1000*60*60*-24));
	

	//날짜 형식
	public static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd E요일", Locale.KOREAN);
	public static SimpleDateFormat day = new SimpleDateFormat("d", Locale.UK);
	public static SimpleDateFormat en_month = new SimpleDateFormat("MMMM", Locale.UK);
	
	private static SimpleDateFormat year_month = new SimpleDateFormat("yyyy_MM");
	
	//활용
	public static String today = SDF.format(date);
	public static String today_day = day.format(date);
	public static String today_month = en_month.format(date);
	public static String today_year_month = year_month.format(date);
	
	// 저번 달
	public static String month_ago() {

		Calendar cal = Calendar.getInstance();

		cal.add(cal.MONTH, -1);

		String date = year_month.format(cal.getTime());
		return date;
	}
		
	//어제 날짜
//	private static Date yesterday_date = new Date(date.getTime()+(1000*60*60*24*-1));
	
//	public static String yesterday = SDF.format(yesterday_date);
//	public static String yesterday_day = day.format(yesterday_date);
//	public static String yesterday_month = en_month.format(yesterday_date);
	
	
	
}
