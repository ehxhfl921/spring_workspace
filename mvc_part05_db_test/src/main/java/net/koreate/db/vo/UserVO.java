package net.koreate.db.vo;

import java.util.Date;


public class UserVO {

	private int uno;
	private String userid;
	private String userpw;
	private String username;
	private Date regdate;
	private Date updatedate;
	
	public int getUno() {
		return uno;
	}
	public void setUno(int uno) {
		this.uno = uno;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUserpw() {
		return userpw;
	}
	public void setUserpw(String userpw) {
		this.userpw = userpw;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	public Date getUpdatedate() {
		return updatedate;
	}
	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}
	
	@Override
	public String toString() {
		return "UserVO [uno=" + uno + ", userid=" + userid + ", userpw=" + userpw + ", username=" + username
				+ ", regdate=" + regdate + ", updatedate=" + updatedate + "]";
	}
	
	
	
}
