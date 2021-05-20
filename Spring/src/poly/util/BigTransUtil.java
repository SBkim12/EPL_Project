package poly.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BigTransUtil {
	//test
	
	public static String transNews(List<String> enNews) {
		
		//String koNews= "";
		
		List<String> contents = new ArrayList<>();
		
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<enNews.size(); i++) {
			if(enNews.get(i).length()<1) {
				continue;
			}
			if(enNews.get(i).length()+sb.length()<4500) {
				sb.append(enNews.get(i));
				sb.append("\r\n");
			}else {
				contents.add(sb.toString());
				//koNews+=trans(sb);
				sb.setLength(0);
				sb.append(enNews.get(i));
				sb.append("\r\n");
			}
		}
		if(sb.length()>0) {
			contents.add(sb.toString());
			//koNews+=trans(sb);
		}
		
		String ko_contents = trans(contents);
		
		return ko_contents;
	}
	
	
	public static String trans(List<String> contents) {
		
		// WebDriver 경로 설정 (리눅스가면 변경해야함)
		System.setProperty("webdriver.chrome.driver", "C:\\selenium\\chromedriver.exe");

		// WebDriver 옵션 설정
		ChromeOptions options = new ChromeOptions();
		//options.addArguments("--start-maximized"); // 전체화면으로 실행
		options.addArguments("headless");
		options.addArguments("--disable-gpu");
		options.addArguments("--disable-popup-blocking"); // 팝업 무시
		options.addArguments("--disable-default-apps"); // 기본앱 사용안함

		// WebDriver 객체 생성
		ChromeDriver driver = new ChromeDriver(options);
		
		// 페이지 대기 시간 최대 2초 설정
		WebDriverWait wait = new WebDriverWait(driver, 2);
		
		String ko_contents = "";
		
		try {

			// 웹페이지 요청
			driver.get("https://papago.naver.com/");
			Thread.sleep(1000);
			
			WebElement en = driver.findElement(By.xpath("//*[@id=\"sourceEditArea\"]"));
			WebElement ko = driver.findElement(By.xpath("//*[@id=\"txtTarget\"]"));
			WebElement x = driver.findElement(By.xpath("//*[@id=\"sourceEditArea\"]/button"));
			
//			Iterator<String> test = contents.iterator();
//			
//			while(test.hasNext()) {
//				System.out.print(test.next());
//			}
			
			Iterator<String> it = contents.iterator();
			
			while(it.hasNext()) {
				// 뉴스 입력
				String splited_contents = "0";
				splited_contents+=it.next();
				en.sendKeys(splited_contents);
				Thread.sleep(10000);
				
				//번역 받아오기
				ko_contents+=ko.getText();
				Thread.sleep(3000);
				
				//입력 초기화
				x.click();
			}

			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			driver.close();
		}
		
		
		return ko_contents;
	}
}