package net.koreate.board.backup;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import net.koreate.board.dao.BoardDAO;
import net.koreate.board.vo.BoardVO;
import net.koreate.common.util.Criteria;

@Repository
@RequiredArgsConstructor
public class BoardDAOImpl implements BoardDAO {
	
	private final SqlSession session;

	@Override
	public int create(BoardVO board) throws Exception {
		int result = session.insert("boardMapper.create", board);
		return result;
	}

	@Override
	public BoardVO read(int bno) throws Exception {
		return session.selectOne("boardMapper.read", bno);
	}

	@Override
	public int update(BoardVO board) throws Exception {
		return session.update("boardMapper.update", board);
	}

	@Override
	public int delete(int bno) throws Exception {
		return session.delete("boardMapper.delete", bno);
	}

	@Override
	public void updateCnt(int bno) throws Exception {
		session.update("boardMapper.updateCnt", bno);
	}

	@Override
	public int totalCount() throws Exception {
		return session.selectOne("boardMapper.totalCount");
	}

	@Override
	public List<BoardVO> listCriteria(Criteria cri) throws Exception {
		List<BoardVO> list = session.selectList("boardMapper.listCriteria", cri);
		return list;
	}

}
