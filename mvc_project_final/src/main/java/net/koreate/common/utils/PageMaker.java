package net.koreate.common.utils;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.Getter;
import lombok.ToString;

	
/**
 * 이동 가능한 페이지 번호 출력(페이지 블럭) 에 
 * 필요한 연산을 처리하고 정보를 제공하는 class
 */
@Getter
@ToString
public class PageMaker {
	
	/**
	 * 전체 게시물 개수 <br>
	 * table 마다 조건에 따른 검색된 전체 행의 개수 <br>
	 */
	private int totalCount;	
	
	/**
	 * 요청 페이지에 따라 table에서 행 정보를  <br>
	 * 검색할 기준 정보를 저장하는 class <br>
	 * page : 요청 페이지  <br>
	 * perPageNum : 한번에 검색할 게시물 개수  <br>
	 * getStartRow() : 검색 범위 내에서 추출할 행 시작 번호  <br>
	 */
	protected Criteria criteria;	
	
	/**
	 * 한 페이지 블럭에 출력할 페이지 번호 개수 <br>
	 * [1][2][3][4][5]	<br>
	 * [6][7][8][9][10]	<br>
	 */
	private int displayPageNum;	// 
	
	/**
	 * 계산된 결과를 저장할 필드
	 * 출력 시작 페이지 번호
	 */
	private int startPage;
	/**
	 * 출력 마지막 페이지 번호
	 */
	private int endPage;			
	
	/**
	 * 이동 가능한 최대 페이지 번호
	 */
	private int maxPage;	 
	
	/**
	 * 페이지 블럭 이동 가능 여부를 저장할 변수
	 */
	private boolean moveFirst, moveLast, movePrev, moveNext;
	
	/**
	 * 이동할 페이지 번호
	 */
	private int last, prev, next;
	
	public PageMaker() {
		this(new Criteria(), 0 , 5);
	}
	
	
	public PageMaker(Criteria criteria,  int totalCount, int displayPageNum) {
		setTotalCount(totalCount);
		setCriteria(criteria);
		setDisplayPageNum(displayPageNum);
	}
	
	
	/**
	 * 외부에서 제공된 데이터에 따라 페이지 블럭에 출력
	 * 번호를 계산하는 method
	 */
	protected void calculate() {
		if(criteria == null) criteria = new Criteria();
		// 페이지 번호 출력에 필요한 정보를 가지고 연산된 결과를 도출할 메서드
		
		endPage = (int)(Math.ceil(criteria.getPage() / (double)displayPageNum) * displayPageNum);
		
		startPage = (endPage - displayPageNum) + 1;
		
		maxPage = (int)Math.ceil(totalCount / (double)criteria.getPerPageNum());
		
		if(endPage > maxPage){
			endPage = maxPage;
		}
		
		moveFirst = (criteria.getPage() != 1) ? true : false;
		moveLast = (criteria.getPage() < maxPage) ? true : false;
		movePrev = (startPage != 1) ? true : false;
		moveNext = (endPage < maxPage) ? true : false;
		
		last = maxPage;
		prev = startPage - 1;
		next = endPage + 1;
		
	} // end calculate()

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		calculate();
	}


	public void setCriteria(Criteria criteria) {
		this.criteria = criteria;
		calculate();
	}


	public void setDisplayPageNum(int displayPageNum) {
		this.displayPageNum = displayPageNum;
		calculate();
	}
	
	
	public String makeQuery(int page) {
		UriComponents uriComponents 
			= UriComponentsBuilder.newInstance()
			  .queryParam("page",page)
			  .queryParam("perPageNum", criteria.getPerPageNum())
			  .build();
		String query = uriComponents.toUriString();
		System.out.println(query);
		return query;
	}
	
}








