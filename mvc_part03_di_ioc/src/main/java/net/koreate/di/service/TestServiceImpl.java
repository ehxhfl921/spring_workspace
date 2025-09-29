package net.koreate.di.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import lombok.Setter;
import net.koreate.di.dao.TestDAO;

/**
 *  TestService 인터페이스 구현 객체
 */
@Service
public class TestServiceImpl  implements TestService{

	@Setter(onMethod_= { @Autowired, @Qualifier("td")})
	private TestDAO dao;
	
	/*
	@Autowired
	@Qualifier("td")
	public void setDao(TestDAO dao) {
		this.dao = dao;
	}
	*/
	
	@Override
	public void testService(String message) {
		System.out.println(message + " Test Service");
		// dao = new TestDAOImpl(); // 수행(의존) 객체 임의 변경 가능
		System.out.println("test service dao : " + dao);
	}
	
}
