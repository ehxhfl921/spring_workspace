package net.koreate.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Test;

public class Oracle1ConnectionTest {

	@Test
	public void testConnection() {
		
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("Driver class 로드 완료");
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521/xe",
					"develop_spring",
					"12345"
			);
			System.out.println("create Connection : " + conn);
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 클래스를 찾을 수 없습니다.");
			System.out.println("클래스 이름이나 jdbc 라이브러리를 확인해 주세요.");
		} catch (SQLException e) {
			System.out.println("DB 연결 정보 오류 : " + e.getMessage());
		}
		
	} // end testConnection()
	
}
