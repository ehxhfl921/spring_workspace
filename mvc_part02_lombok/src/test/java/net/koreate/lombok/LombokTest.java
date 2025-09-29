package net.koreate.lombok;

import java.util.ArrayList;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *  Junit - java용 단위 테스트 도구(라이브러리)
 *  
 *  @Before - 테스트 메서드가 실행되기 전 호출되는 메서드
 *  
 *  @Test - 실제 테스트할 메서드 지정
 *  
 *  @After - 테스트 메서드가 실행 완료되면 호출되는 메서드
 */
public class LombokTest {

	@Before
	public void beforeTest() {
		System.out.println("테스트 전 수행");
	}
	
	@Test
	public void test() {
		System.out.println("Test Code 수행");
		UserVO user = new UserVO("id001", "pw001", "홍길동");
		user.setUno(1);
		user.setUid("id001");
		user.setUpw("pw001");
//		user.setUname("홍길동");
		user.setRegdate(new Date());
		user.setFriends(new ArrayList<>());
		
		System.out.println(user);
	}
	
	@After
	public void testAfter() {
		System.out.println("테스트 완료 후 수행");
	}
	
	@Test
	public void equalsTest() {
		System.out.println("equals test");
		
		UserVO user1 = new UserVO(1, "id001", "pw001", "최기근", new Date(), new ArrayList<>());
		System.out.println(user1);
		
		UserVO user2 = new UserVO(2, "id001", "pw001", "홍길동", new Date(), new ArrayList<>());
		System.out.println(user2);
		System.out.println("user1 equals user2");
		System.out.println(user1.equals(user2));
	}
	
	@Test
	public void builderTest() {
		System.out.println("@Builder test");
		/**
		 *  Builder Pattern
		 *  객체 생성 패턴
		 *  복잡한 객체를 단계적으로 생성하면서 가독성과 유연성을 높여주는 객체 생성 방식
		 */
		UserVO user = UserVO.builder()
					  .uno(1)
					  .uid("id001")
					  .upw("pw001")
					  .uname("최기근")
					  // .friends(new ArrayList<>())
					  .list("짱구").list("로보").list("미애").list("근성")
					  .regdate(new Date()).build();
	
		System.out.println(user);
	}
	
	
}




















