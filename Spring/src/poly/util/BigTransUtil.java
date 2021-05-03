package poly.util;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class BigTransUtil {
	//test
	/*
	public static void main(String[] args) {
		List<String> enNews = new ArrayList<>();
		
		enNews.add("Should Antonio miss out against the Foxes, Jarrod Bowen is ready to step in after scoring in each of his last two outings - against Arsenal and Wolves either side of the international break - and Moyes has been impressed with his steady progress since joining the Hammers from Hull City a little over a year ago.");
		enNews.add("“Jarrod Bowen is a great example of a player who has that hunger to prove that they can compete,” the manager added, “He came through at Hull and is making his way in the Premier League.");
		enNews.add("“There are a lot of players in the Championship who can go on and do big things, but I think the important thing is you can’t lose your hunger or think suddenly: ‘I’ve made it’. The minute you stop working, it finishes for you.");
		enNews.add("“Jarrod’s really hungry. He’s at it - he’s still trying to establish himself. This is his first full season in the Premier League, and you have to say he’s making a good go of it.");
		enNews.add("“Probably over the second half of the season we’ve had to make the odd change here and there due to injuries, but thankfully the players who have come in have done a brilliant job and I hope that remains.”");
		
		System.out.println(transNews(enNews));
	}
	*/
	
	public static String transNews(List<String> enNews) {
		
		String koNews= "";
		
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<enNews.size(); i++) {
			if(enNews.get(i).length()+sb.length()<49000) {
				sb.append(enNews.get(i));
				sb.append("\r\n\r\n");
			}else {
				koNews+=trans(sb);
				sb.setLength(0);
				sb.append(enNews.get(i));
				sb.append("\r\n\r\n");
			}
		}
		if(sb.length()>0) {
			koNews+=trans(sb);
		}
		return koNews;
	}
	
	
	public static String trans(StringBuilder enNews) {
		// WebDriver 경로 설정
		System.setProperty("webdriver.chrome.driver", "C:\\selenium\\chromedriver.exe");

		// WebDriver 옵션 설정
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized"); // 전체화면으로 실행
		options.addArguments("--disable-popup-blocking"); // 팝업 무시
		options.addArguments("--disable-default-apps"); // 기본앱 사용안함

		// WebDriver 객체 생성
		ChromeDriver driver = new ChromeDriver(options);
		
		String koNews = "";
		
		try {

			// 웹페이지 요청
			driver.get("https://papago.naver.com/");
			Thread.sleep(100);
			// 뉴스 입력
			WebElement element = driver.findElement(By.id("txtSource"));
			element.sendKeys(enNews);

			Thread.sleep(10000);

			element = driver.findElement(By.id("txtTarget"));
			koNews = element.getText();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			driver.close();
		}
		
		
		return koNews;
	}

	/*
	 * public static void main(String[] args) {
	 * 
	 * // WebDriver 경로 설정 System.setProperty("webdriver.chrome.driver",
	 * "C:\\selenium\\chromedriver.exe");
	 * 
	 * // WebDriver 옵션 설정 ChromeOptions options = new ChromeOptions();
	 * options.addArguments("--start-maximized"); // 전체화면으로 실행
	 * options.addArguments("--disable-popup-blocking"); // 팝업 무시
	 * options.addArguments("--disable-default-apps"); // 기본앱 사용안함
	 * 
	 * // WebDriver 객체 생성 ChromeDriver driver = new ChromeDriver(options);
	 * 
	 * 
	 * String news =
	 * "Hector Bellerin was in philosophical mood after our 1-1 draw with Slavia Prague on Thursday night.\r\n"
	 * + "\r\n" +
	 * "While unhappy to have conceded a last-gasp equaliser, our right back believes we showed more than enough to head to the Czech Republic confident of getting a result.\r\n"
	 * + "\r\n" +
	 * "\"Well, obviously it’s really disappointing not to win this game when you get the feeling throughout the game that you should have won that,\" he said.\r\n"
	 * + "\r\n" +
	 * "\"It’s difficult to take, but at the end of the day there’s still everything to play for and it’s 1-1. We know how they play and I think we had really good moments in the game. \r\n"
	 * + "\r\n" +
	 * "\"We broke down their pressure and we created chances, so there’s nothing that worries me for us to go to Prague and do the job there.\r\n"
	 * + " \r\n" +
	 * "\"You can blame it on anything you want, but at the end of the day it comes down to the goals we score and the goals we concede, and I think throughout the game we put ourselves in situations that we could have taken the lead, but this is football,\" he added. \r\n"
	 * + "\r\n" +
	 * "\"You’ve got to keep trying, you’ve got to keep trying, and on another game we will score them and today wasn’t the day. I think also when you put yourself 1-0 up there’s better ways of managing the game in the way we did and as players we take the responsibility. \r\n"
	 * + "\r\n" +
	 * "\"We know when we make mistakes and at the same time, I think it’s a very positive performance when you look to go away next Thursday. They’re a team that makes things difficult for every single team that they’ve played against, and I think I felt comfortable on the ball and I felt like the team did all the right things that we were asked to do and there’s still 90 minutes to play for. We’re ready for it.\"\r\n"
	 * + "\r\n" +
	 * "Copyright 2021 The Arsenal Football Club plc. Permission to use quotations from this article is granted subject to appropriate credit being given to www.arsenal.com as the source."
	 * ; String trans = ""; try {
	 * 
	 * // 웹페이지 요청 driver.get("https://papago.naver.com/"); Thread.sleep(100); // 뉴스
	 * 입력 WebElement element = driver.findElement(By.id("txtSource"));
	 * element.sendKeys(news);
	 * 
	 * Thread.sleep(6000);
	 * 
	 * element = driver.findElement(By.id("txtTarget")); trans = element.getText();
	 * } catch (Exception e) { e.printStackTrace(); } finally { driver.close(); }
	 * 
	 * System.out.println(trans); }
	 */
}