package net.koreate.board;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.koreate.board.vo.BoardVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:/spring/root-context.xml"})
public class BoardMapperTest {

	@Autowired
	private SqlSession session;
	
	@Test
	public void testCreate() {
		
		BoardVO board = new BoardVO();
		board.setTitle("첫 번째 게시글");
		board.setContent("춥다");
		board.setWriter("김도은");
		
		int result = session.insert("boardMapper.create", board);
		System.out.println("삽입된 행의 개수 : " + result);
	}
	
}
