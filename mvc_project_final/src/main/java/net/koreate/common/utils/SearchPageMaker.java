package net.koreate.common.utils;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SearchPageMaker extends PageMaker{
	
	public SearchPageMaker(Criteria criteria,  int totalCount, int displayPageNum){
		super(criteria, totalCount, displayPageNum);
	}

	@Override
	public String makeQuery(int page) {
		SearchCriteria sCri = (SearchCriteria)criteria;
		UriComponents uriComponentsents = 
				UriComponentsBuilder.newInstance()
				.queryParam("page", page)
				.queryParam("perPageNum", criteria.getPerPageNum())
				.queryParam("searchType", sCri.getSearchType())
				.queryParam("keyword",sCri.getKeyword())
				.build();
		String query = uriComponentsents.toUriString();
		return query;
	}
	
	public String makeQueryBno(int bno) {
		SearchCriteria sCri = (SearchCriteria)criteria;
		UriComponents uriComponentsents = 
				UriComponentsBuilder.newInstance()
				.queryParam("page", criteria.getPage())
				.queryParam("perPageNum", criteria.getPerPageNum())
				.queryParam("searchType", sCri.getSearchType())
				.queryParam("keyword",sCri.getKeyword())
				.queryParam("bno", bno)
				.build();
		String query = uriComponentsents.toUriString();
		return query;
	}
	
	

}






