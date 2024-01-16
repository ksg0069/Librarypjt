package com.goodee.library.member;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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
	
	public MemberVo selectMember(MemberVo vo) {
		LOGGER.info("[MemberDao] selectMember();");
		String sql = "SELECT * FROM tbl_member WHERE m_id = ?";
		
		List<MemberVo> memberVos = new ArrayList<MemberVo>();
		try {
			
			memberVos = jdbcTemplate.query(sql,new RowMapper<MemberVo>(){

				@Override
				public MemberVo mapRow(ResultSet rs, int rowNum) throws SQLException {
					MemberVo memberVo = new MemberVo();
					memberVo.setM_no(rs.getInt("m_no"));
					memberVo.setM_id(rs.getNString("m_id"));
					memberVo.setM_pw(rs.getString("m_pw"));
					memberVo.setM_name(rs.getString("m_name"));
					memberVo.setM_gender(rs.getString("m_gender"));
					memberVo.setM_mail(rs.getString("m_mail"));
					memberVo.setM_phone(rs.getString("m_phone"));
					memberVo.setM_reg_date(rs.getString("m_reg_date"));
					memberVo.setM_mod_date(rs.getString("m_mod_date"));
					
					return memberVo;
				}
			}, vo.getM_id());
			
			if(passwordEncoder.matches(vo.getM_pw(),memberVos.get(0).getM_pw()) == false) {
				
				memberVos.clear();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return memberVos.size() > 0 ? memberVos.get(0) : null;
	}
	
	public List<MemberVo> selectMemberList(){
		LOGGER.info("[MemberDao] selectMemberList();");
		String sql = "SELECT * FROM tbl_member";
		List<MemberVo> memberVos = new ArrayList<MemberVo>();
		
		memberVos = jdbcTemplate.query(sql,new RowMapper<MemberVo>(){

			@Override
			public MemberVo mapRow(ResultSet rs, int rowNum) throws SQLException {
				MemberVo memberVo = new MemberVo();
				memberVo.setM_no(rs.getInt("m_no"));
				memberVo.setM_id(rs.getNString("m_id"));
				memberVo.setM_pw(rs.getString("m_pw"));
				memberVo.setM_name(rs.getString("m_name"));
				memberVo.setM_gender(rs.getString("m_gender"));
				memberVo.setM_mail(rs.getString("m_mail"));
				memberVo.setM_phone(rs.getString("m_phone"));
				memberVo.setM_reg_date(rs.getString("m_reg_date"));
				memberVo.setM_mod_date(rs.getString("m_mod_date"));
				
				return memberVo;
			}
		});
		return memberVos;
	}
}


















