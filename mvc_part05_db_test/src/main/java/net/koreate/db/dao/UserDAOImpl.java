package net.koreate.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import net.koreate.db.vo.UserVO;

// @Component @Controller @Service @Repository
@Repository
public class UserDAOImpl implements UserDAO{

	private final DataSource ds;
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	private UserDAOImpl(DataSource ds) {
		this.ds = ds;
	}
	
	@Override
	public int insertUser(UserVO user) {
		int result = 0;
		
		String sql = "INSERT INTO test_user(userid, userpw, username) VALUES(?, ?, ?)";
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUserid());
			pstmt.setString(2, user.getUserpw());
			pstmt.setString(3, user.getUsername());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {}
			} // end pstmt close
			
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {}
			} // end conn close
			
		} // end try/catch/finally
		
		
		return result;
	}

	@Override
	public UserVO readUser(String userid) {
		return null;
	}

	@Override
	public UserVO readUserWithPass(String userid, String userpw) {
		return null;
	}

	@Override
	public List<UserVO> readUserList() {
		return null;
	}

	@Override
	public int readMax() {
		int num = 0;
		
		String sql = "SELECT max(uno) FROM test_user";
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				num = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {}
			} // end rs close
			
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {}
			} // end pstmt close

			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {}
			} // end conn close
			
		} // end try/catch/finally
		
		
		return num;
	}
	
}



