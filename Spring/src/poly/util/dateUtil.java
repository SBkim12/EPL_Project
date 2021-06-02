package poly.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class dateUtil {

	// 날짜 형식(skysports용)
	public static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd E요일", Locale.KOREAN);
	public static SimpleDateFormat day = new SimpleDateFormat("d", Locale.UK);
	public static SimpleDateFormat en_month = new SimpleDateFormat("MMMM", Locale.UK);
	
	// Mongo DB 이름 형식
	private static SimpleDateFormat year_month = new SimpleDateFormat("yyyy_MM");
	
	// 날짜 형식(TheGuardians용)
	private static SimpleDateFormat month_day = new SimpleDateFormat("/MMM/dd/", Locale.UK);
	
	//test
	public static SimpleDateFormat time = new SimpleDateFormat("dd hh::mm::ss", Locale.UK);
	


	// 저번 달
	public static String month_ago() {
		Calendar cal = Calendar.getInstance();

		cal.add(cal.MONTH, -1);

		String date = year_month.format(cal.getTime());
		return date;
	}
	
	//Mongo Collection 날짜
	public static String today_year_month() {
		Calendar cal = Calendar.getInstance();
		Date now = new Date(cal.getTimeInMillis());
		Date date = new Date(now.getTime() + (1000 * 60 * 60 * -24));
		
		String today_year_month = year_month.format(date);
		return today_year_month;
	}
	
	//어제 날짜의 월
	public static String today_month(){
		Calendar cal = Calendar.getInstance();
		Date now = new Date(cal.getTimeInMillis());
		Date date = new Date(now.getTime() + (1000 * 60 * 60 * -24));
		
		String today_month = en_month.format(date);
		return today_month;
	}
	
	// 어제 날짜의 일
	public static String today_day() {
		Calendar cal = Calendar.getInstance();
		Date now = new Date(cal.getTimeInMillis());
		Date date = new Date(now.getTime() + (1000 * 60 * 60 * -24));

		String today_day = day.format(date);
		return today_day;
	}
	// 뉴스에 저장될 어제 날짜
	public static String today() {
		Calendar cal = Calendar.getInstance();
		Date now = new Date(cal.getTimeInMillis());
		Date date = new Date(now.getTime() + (1000 * 60 * 60 * -24));
		
		String today = SDF.format(date);
		return today;
	}
	
	//어제 날짜 월_일 (the guardians 용)
	public static String today_month_day() {
		Calendar cal = Calendar.getInstance();
		Date now = new Date(cal.getTimeInMillis());
		Date date = new Date(now.getTime() + (1000 * 60 * 60 * -24));
		
		String today_month_day = month_day.format(date).toLowerCase();
		return today_month_day;
	}
	//test
	public static String hh_mm_ss() {
		Calendar cal = Calendar.getInstance();
		Date now = new Date(cal.getTimeInMillis());
		Date date = new Date(now.getTime() + (1000 * 60 * 60 * -24));
		
		String hh_mm_ss = time.format(date);
		return hh_mm_ss;
	}


}
