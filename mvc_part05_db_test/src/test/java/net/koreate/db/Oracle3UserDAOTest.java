package net.koreate.db;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.koreate.db.dao.UserDAO;
import net.koreate.db.vo.UserVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/root-context.xml"})
public class Oracle3UserDAOTest {

	@Autowired
	UserDAO dao;
	
	// 회원 번호를 저장할 필드 변수
	Integer num;
	
	@Before
	public void initNum() {
		System.out.println(dao);
		num = dao.readMax()+1;
		System.out.println("Before readMax : " + num);
	}

	@Test
	public void testInsertUser() {
		
		UserVO user = new UserVO();
		user.setUserid("user"+num);
		user.setUserpw("pass"+num);
		user.setUsername("김도은");
		
		System.out.println(user);
		
		int result = dao.insertUser(user);
		System.out.println("test insert user : " + result);
		
	}
	
	@Test
	public void readUser() {
		
		UserVO user = dao.readUser("id999");
		System.out.println("readUser : " + user);
		
		System.out.println("-------------------------------------------");
		
		UserVO userWithPass = dao.readUserWithPass("user1", "pass1");
		System.out.println("readUserWithPass : " + userWithPass);
		
		System.out.println("-------------------------------------------");
		
		List<UserVO> userList = dao.readUserList();
		
		for(UserVO u : userList) {
			System.out.println(u);
		}
	}
	
}
