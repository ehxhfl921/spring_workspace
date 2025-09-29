package net.koreate.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *  Oracle2DataSourceTest class로 JUnit test 진행 시
 *  Spring Context와 함께 테스트를 진행하도록 설정 추가(spring-test)
 */
@RunWith(SpringJUnit4ClassRunner.class)
/**
 *  test를 진행할 때 사용할 context 설정 파일 경로 지정
 */
@ContextConfiguration(
		locations = { "classpath:/spring/root-context.xml" }
)
public class Oracle2DataSourceTest {

	@Autowired
	DataSource ds;
	
	@Test
	public void testDataSource() {
	
		Connection conn = null;
		
		try {
			conn = ds.getConnection();
			System.out.println("datasource connection : " + conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} // end try/catch/finally
		
	} // end testDataSource()
	
}
