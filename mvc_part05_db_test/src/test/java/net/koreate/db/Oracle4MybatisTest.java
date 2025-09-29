package net.koreate.db;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.koreate.db.vo.UserVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/root-context.xml"})
public class Oracle4MybatisTest {
	
	@Autowired
	SqlSessionFactory sqlSessionFactory;
	
	@Before
	public void init() {
		System.out.println(sqlSessionFactory);
	}
	
	@Autowired
	SqlSession ses;
	
	@Test
	public void testSelectOne() {
		// table에서 1개의 row(행) 정보 검색
		UserVO readUser = ses.selectOne("userMapper.readUser", "id999");
		System.out.println(readUser);
	}
	
	//@Test
	public void testSqlSession() {
		// 실제 DB 요청 처리를 하는 MyBatis 실행 객체
		SqlSession session = new SqlSessionTemplate(sqlSessionFactory);
		// insert 쿼리 작업을 수행하는 mapper 실행
		UserVO user = new UserVO();
		user.setUserid("id999");
		user.setUserpw("pw999");
		user.setUsername("윤동주");
		
		int result = session.insert("userMapper.insertUser", user);
		System.out.println("result : " + result);
		System.out.println("DB 작업 실행을 위한 SqlSession 객체 : " + session);
		System.out.println("connection : " + session.getConnection());
		
		session.close();
	}
	
}













