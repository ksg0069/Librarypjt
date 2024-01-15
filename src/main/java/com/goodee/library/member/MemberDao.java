package com.goodee.library.member;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(MemberDao.class);
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	//아이디 종복 검사
	public boolean isMemberCheck(String m_id) {
		LOGGER.info("[MemberDao] isMemberCheck();");
		
		String sql = "SELECT COUNT(*) FROM tbl_member WHERE m_id = ?";
		int result = jdbcTemplate.queryForObject(sql, Integer.class,m_id);
		if(result > 0) {
			return true;
		}else {
			return false;
		}
		 
		
	}
	
	// 회원 정보 추가
	public int insertMember(MemberVo vo) {
		LOGGER.info("[MemberDao] insertMeber();");
		
		String sql = "INSERT INTO tbl_member(m_id, m_pw, m_name, m_gender, m_mail, m_phone, m_reg_date, m_mod_date)"
				+ "VALUES(?,?,?,?,?,?,now(),now())";
		
		List<String> args = new ArrayList<String>();
		args.add(vo.getM_id());
		args.add(passwordEncoder.encode(vo.getM_pw()));
		args.add(vo.getM_name());
		args.add(vo.getM_gender());
		args.add(vo.getM_mail());
		args.add(vo.getM_phone());
		int result = -1;
		result = jdbcTemplate.update(sql,args.toArray());
		
		return result;
	}
}


















