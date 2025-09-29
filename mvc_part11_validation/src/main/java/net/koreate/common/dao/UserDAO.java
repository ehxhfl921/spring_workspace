package net.koreate.common.dao;

import org.apache.ibatis.annotations.Insert;

import net.koreate.valid.vo.ValidationUserVO;

public interface UserDAO {

	@Insert("INSERT INTO validation_user"
			+ "(u_id, u_pw, u_name, u_phone, u_birth, u_addr, u_addr_detail, u_addr_post, u_profile) "
			+ "VALUES"
			+ "(#{u_id}, #{u_pw}, #{u_name}, #{u_phone}, #{u_birth}, #{u_addr}, #{u_addr_detail}, #{u_addr_post}, #{u_profile})")
	int memberJoin(ValidationUserVO vo) throws Exception;
	
}
