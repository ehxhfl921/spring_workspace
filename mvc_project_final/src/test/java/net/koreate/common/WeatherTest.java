package net.koreate.common;

import java.time.Duration;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WeatherTest {

	@Test
	public void weatherTest() {
		// 드라이버 자동 설치 및 설치 경로 지정
		WebDriverManager.chromedriver().setup();
		
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");	// 시작 시 브라우저 화면 최대화
		
		WebDriver driver = new ChromeDriver(options);
		
		// driver로 요소를 제어할 때 요소 출력(검색)에 따른 암묵적 대기 시간 지정
		// java.time.Duration : 시간 간격을 저장하는 객체
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		// 특정 조건에 따른 명시적 대기 시간 지정
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		
		try {
			
			// 네이버 메인 페이지 접속
			driver.get("https://naver.com");
			
			WebElement search = wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.id("query"))
			);
			
			/*
			// 검색 입력 태그에 날씨 value 삽입
			search.sendKeys("날씨");
			WebElement searchBtn = driver.findElement(By.className("btn_search"));
			searchBtn.click();
			*/
			
			// 입력 완료 후 수행할 키 지정
			search.sendKeys("날씨", Keys.ENTER);
			
			WebElement locationTarget = wait.until(
				ExpectedConditions.visibilityOfElementLocated(
					By.cssSelector("div.title_area._area_panel > h2.title")
				)
			);
			
			// target 요소의 text content - 지역
			String location = locationTarget.getText();
			System.out.println("지역 : " + location);
			
			// 현재 온도
			String temperature = driver.findElement(By.cssSelector(".temperature_text strong")).getText();
			System.out.println("현재 온도 : " + temperature);
			
			// 날씨 상태
			String status = driver.findElement(By.cssSelector(".weather.before_slash")).getText();
			System.out.println("날씨 상태 : " + status);
			
			// 이미지 아이콘
			WebElement icon = driver.findElement(By.cssSelector(".cs_weather_new .wt_icon.ico_wt5"));
			
			// CSS 속성 읽기
			String bgImage = icon.getCssValue("background-image");
			System.out.println("background-image 속성 : " + bgImage);
			// url("https://ssl.pstatic.net/sstatic/keypage/outside/scui/weather_new_new/img/weather_svg_v2/icon_flat_wt5.svg")
			// https://ssl.pstatic.net/sstatic/keypage/outside/scui/weather_new_new/img/weather_svg_v2/icon_flat_wt5.svg")
			// https://ssl.pstatic.net/sstatic/keypage/outside/scui/weather_new_new/img/weather_svg_v2/icon_flat_wt5.svg")

			String src = bgImage.replace("url(\"", "").replace("\")", "");
			System.out.println("추출된 이미지 경로 : " + src);
					
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			// 현재 작업 중인 스레드 5초 대기
			try {Thread.sleep(5000);} catch(InterruptedException e) {}
			// 브라우저 사이트 접속 종료
			driver.quit();
		}
		
	} // end test
}











