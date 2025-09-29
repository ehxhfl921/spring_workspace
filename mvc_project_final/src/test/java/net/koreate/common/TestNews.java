package net.koreate.common;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

public class TestNews {

	
	@Test
	public void testNews() {
		
		String url = "https://news.naver.com/main/officeList.naver";
		
		try {
			
			Document doc = Jsoup.connect(url).get();
			Elements elements = doc.select("#_rankingList0 > li");
			
			for(Element e : elements) {
				System.out.println(e);
				// 검색된 첫 번째 태그 요소 1개
				Element img = e.selectFirst("a > img");
				String src = img.attr("src");
				System.out.println(src);
				Element title_a = e.selectFirst("div > div > div > a:first-child");
				String title = title_a.text();
				System.out.println("제목 : "+title);
				
				String href = title_a.attr("href");
				System.out.println("링크 : "+href);
				
				System.out.println("--------------------------------");
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
