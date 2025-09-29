package net.koreate.board.dao;

import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import net.koreate.board.provider.BoardQueryProvider;

public interface AttachmentDAO {
	
	/**
	 * 첨부파일 등록
	 */
	 @Insert("INSERT INTO tbl_attach(fullName, bno) VALUES(#{fullName}, #{bno})")
	void addAttachment(@Param("fullName")String fullName, @Param("bno")int bno)throws Exception;

	/**
	 * 게시글 번호와 일치하는 첨부파일 목록
	 */
	 @Select("SELECT fullName FROM tbl_attach WHERE bno = #{bno}")
	List<String> getAttach(int bno) throws Exception;

	/**
	 * 게시글 첨부파일 table 목록에서 삭제
	 */
	void deleteAttach(int bno) throws Exception;

	/**
	 * 실행 기준 이전날 등록된 모든 첨부파일 목록
	 */
	List<String> getTrashAttach()throws Exception;

}











