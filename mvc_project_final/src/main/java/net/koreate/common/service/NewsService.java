package net.koreate.common.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import net.koreate.common.vo.News;

@Service
public class NewsService {

	/**
	 *  naver news에서 언론사별 실시간 많이 본 뉴스 목록 반환
	 */
	public List<News> getNewsList(){
		
		List<News> newsList = new ArrayList<>();
		
		String url = "https://news.naver.com/main/officeList.naver";
		
		try {
			
			Document doc = Jsoup.connect(url).get();
			Elements elements = doc.select("#_rankingList0 > li");
			
			for(Element e : elements) {
				// 검색된 첫 번째 태그 요소 1개
				Element img = e.selectFirst("a > img");
				String src = img.attr("src");
				Element title_a = e.selectFirst("div > div > div > a:first-child");
				String title = title_a.text();
				String href = title_a.attr("href");
				News news = new News();
				news.setImg(src);
				news.setTitle(title);
				news.setLink(href);
				newsList.add(news);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return newsList;
	}
	
}
