package poly.service.impl.comm;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public abstract class AbstractgetUrlForJson {
	
	//로그생성
	private Logger log = Logger.getLogger(this.getClass());
	
	
	// API로 불러온 JSON 파일 가져오기
	protected String getUrlForJSON(String callUrl)throws Exception {

		log.info(this.getClass().getName() + ".getUrlForJSON start!");

		log.info("requested URL: " + callUrl);

		StringBuilder sb = new StringBuilder();
		URLConnection urlConn = null;
		InputStreamReader in = null;

		// json 결과값 저장
		String json = "";

		// SSL 적용된 사이트일 경우, 데이터 증명을 위해 사용
		HostnameVerifier allHostsValid = new HostnameVerifier() {
			@Override
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		};
		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

		try {
			// 웹 사이트 접속을 위한 URL 파싱
			URL url = new URL(callUrl);

			// 접속
			urlConn = url.openConnection();
			urlConn.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.141 Safari/537.36");
			// 접속화면, 응답을 60초(60 * 1000ms)동안 기다림
			if (urlConn != null) {
				urlConn.setReadTimeout(60 * 1000);
			}

			if (urlConn != null && urlConn.getInputStream() != null) {
				in = new InputStreamReader(urlConn.getInputStream(), Charset.forName("UTF-8"));

				BufferedReader bufferedReader = new BufferedReader(in);

				// 주어진 문자 입력 스트림 inputStream에대해 기본 크기의 버퍼를 갖는 개체를 생성
				if (bufferedReader != null) {
					int cp;
					while ((cp = bufferedReader.read()) != -1) {
						sb.append((char) cp);
					}
					bufferedReader.close();
				}
			}
			in.close();
		} catch (Exception e) {
			throw new RuntimeException("Exception URL:" + callUrl, e);
		}

		json = sb.toString(); // json 결과 저장
		log.info("JSON result: " + json);

		log.info(this.getClass().getName() + ".getUrlForJSON End !");

		return json;
	}
	
	protected String Selenium(StringBuilder enNews) {
		// WebDriver 경로 설정
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
}
