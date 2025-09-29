package net.koreate.msg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:/spring/root-context.xml"})
public class TransactionTest {

	@Autowired
	DataSource ds;
	
	@Test
	public void transactionTest() {
		Connection conn = null;
		try {
			conn = ds.getConnection();
			System.out.println(conn);
			conn.setAutoCommit(false); // autoCommit 사용 안 함 Manual Commit
			// 기본적으로 Connection은 autoCommit();
			System.out.println("commit mode : " + conn.getAutoCommit());
			
			Statement stmt = conn.createStatement();
			String test = "UPDATE tbl_user SET upoint = upoint+10 WHERE userid = 'id002'";
			stmt.executeUpdate(test);
			
			String sql = "INSERT INTO tbl_message(targetid, sender, message) VALUES(?, ?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "dhkdkd");
			pstmt.setString(2, "id002");
			pstmt.setString(3, "test commit");
			
			int result = pstmt.executeUpdate();
			System.out.println("삽입된 행 : " + result);
			
			conn.commit();
			
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	} // end transactionTest()
	
}












