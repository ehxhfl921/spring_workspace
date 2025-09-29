package net.koreate.board.provider;

import org.apache.ibatis.jdbc.SQL;

import net.koreate.common.utils.SearchCriteria;

/**
 * Provider 공급자 제공자 <br>
 * 동적(파라미터에 따라 변경되는) SQL Query 제공 객체
 */
public class BoardQueryProvider {
	
	public String searchSelectSQL(SearchCriteria cri) {
	
		SQL sql = new SQL();
		sql.SELECT("R.*");									// 검색 컬럼
		sql.SELECT("U.u_name AS writer");					// 검색 컬럼 추가
		sql.FROM("re_tbl_board R");							// 검색 테이블
		sql.JOIN("validation_user U ON R.u_no = U.u_no"); 	// join table , join 조건
		
		// 매개변수로 전달된 sql 객체에 조건에 따라 WHERE LIKE 절 추가
		getWhereLike(cri, sql);
		
		sql.ORDER_BY("R.bno DESC");
		
		String query = sql.toString();
		
		query += " OFFSET #{startRow} ROWS FETCH NEXT #{perPageNum} ROWS ONLY";
		
		System.out.println(query);
		
		return query;
	} // end searchSelectSQL

	
	public String searchListCount(SearchCriteria cri) {
		SQL sql = new SQL();
		sql.SELECT("count(*)");
		sql.FROM("re_tbl_board R NATURAL JOIN validation_user U");
		
		getWhereLike(cri, sql);
		
		return sql.toString();
	} // end searchListCount()
	
	
	private void getWhereLike(SearchCriteria cri, SQL sql) {

		String titleQuery = "title LIKE '%' || '" + cri.getKeyword() + "' || '%'";
		String contentQuery = "content LIKE '%' || #{keyword} || '%'";
		String writerQuery = "U.u_name LIKE '%' || #{keyword} || '%'";
		
		if(cri.getSearchType() != null && 				// searchType이 파라미터로 존재하고
		   !cri.getSearchType().trim().equals("") &&	// searchType의 value가 빈문자열이 아니고
		   !cri.getSearchType().trim().equals("n")) {	// searchType이 'n'이 아닐 때	
			// searchType 검색 컬럼이 존재할 때
			if(cri.getSearchType().contains("t")) {
				// t, tc, tw, tcw
				sql.OR().WHERE(titleQuery);
			}
			
			if(cri.getSearchType().contains("c")) {
				sql.OR().WHERE(contentQuery);
			}
			
			if(cri.getSearchType().contains("w")) {
				sql.OR().WHERE(writerQuery);
			}
			
		} // end WHERE if
		
	} // end getWhereLike()
}










