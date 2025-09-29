package net.koreate.db.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import net.koreate.db.vo.UserVO;

@Repository("dao")
public class UserDAOMybatisImpl implements UserDAO{

	private final SqlSession session;
	
	private String namespace;
	
	public UserDAOMybatisImpl(SqlSession session) {
		this.session = session;
		namespace = "userMapper";
	}

	@Override
	public int insertUser(UserVO user) {
		return session.insert(namespace+".insertUser", user);
	}

	@Override
	public UserVO readUser(String userid) {
		return session.selectOne(namespace+".readUser", userid);
	}

	@Override
	public UserVO readUserWithPass(String userid, String userpw) {
		Map<String, String> map = new HashMap<>();
		map.put("userid", userid);
		map.put("userpw", userpw);
		UserVO user = session.selectOne(namespace+".readUserWithPass", map);
		return user;
	}

	@Override
	public List<UserVO> readUserList() {
		List<UserVO> userList = session.selectList(namespace+".readUserList");
		return userList;
	}

	@Override
	public int readMax() {
		return session.selectOne(namespace+".max");
	}

	
	
}
