package net.koreate.di.dao;

import org.springframework.stereotype.Repository;

@Repository("td") // bean name 지정
public class TestDBCPDAOImpl implements TestDAO{

	@Override
	public void select(String message) {
		System.out.println("TEST DBCP select : " + message);
	}
	
}
