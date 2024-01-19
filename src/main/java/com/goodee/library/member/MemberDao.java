package com.goodee.library.member;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
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
	private SqlSession sqlSession;
	
	final private String NAMESPACE="com.goodee.library.member.MemberDao.";
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
//	@Autowired
//	JdbcTemplate jdbcTemplate;
	
	
	//아이디 중복 검사 - mybatis
			public boolean isMemberCheck(String m_id) {
				LOGGER.info("[MemberDao] isMemberCheck();");
				
				int result = sqlSession.selectOne(NAMESPACE+"isMemberCheck",m_id);
				if(result > 0) {
					return true;
				}else {
					return false;
				}
			}
			
			
		//회원 정보 추가 - mybatis
		public int insertMember(MemberVo vo) {
			LOGGER.info("[MemberDao] insertMember();");
			vo.setM_pw(passwordEncoder.encode(vo.getM_pw()));
			int result = -1;
			result = sqlSession.insert(NAMESPACE+"insertMember", vo);
			return result;
			
		}
		
		// 로그인 - 회원정보 조회 및 비밀번호 확인
		public MemberVo selectMember(MemberVo vo) {
			LOGGER.info("[MemberDao] selectMember();");
			MemberVo resultVo = new MemberVo();
			resultVo = sqlSession.selectOne(NAMESPACE+"selectMember", vo.getM_id());
			
			
				if(resultVo != null && passwordEncoder.matches(vo.getM_pw(), resultVo.getM_pw()) == false) { //1.암호화되지않은 비밀번호, 2.암호화된 비밀번호
					
					resultVo= null;
				}
			
			return resultVo;
		}
		
		public List<MemberVo> selectMemberList(){
			LOGGER.info("[MemberDao] selectMemberList();");
			List<MemberVo> resultList = new ArrayList<MemberVo>();
			resultList = sqlSession.selectList(NAMESPACE+"selectMemberList");
			
			return resultList;

		}
		
		//회원정보 수정
		public int updateMember(MemberVo vo) {
			LOGGER.info("[MemberDao] updateMember();");
			int result = sqlSession.update(NAMESPACE+"updateMember", vo);
			return result;
		}
		
		//회원 단일 정보 데이터베이스에서 조회 - m_no기준으로 조회
		public MemberVo selectMemberOne(int m_no) {
			LOGGER.info("[MemberDao] selectMemberOne();");
			return sqlSession.selectOne(NAMESPACE+"selectMemberOne",m_no);
		}
		
		//아이디, 이름, 메일 기준 회원 조회
		public MemberVo selectMemberOne(MemberVo vo) {
			LOGGER.info("[MemberDao] selectMemberOne();");
			
			MemberVo memberVo = sqlSession.selectOne(NAMESPACE+"selectMemberForPassword",vo);
			return memberVo;
		}
		
		public int updatePassword(String m_id,String newPassword) {
			LOGGER.info("[MemberDao] updatePassword();");
			Map<String, String> params = new HashMap<String, String>();
			params.put("m_id", m_id);
			params.put("m_pw",passwordEncoder.encode(newPassword)); //비밀번호 암호화
			int result = -1;
		    try {
		    	result = sqlSession.update(NAMESPACE+"updatePassword",params);
				
			} catch (Exception e) {
				LOGGER.error(e.toString());
			}
		    return result;
		}
	
	//아이디 종복 검사 - jdbcTemplate
//	public boolean isMemberCheck(String m_id) {
//		LOGGER.info("[MemberDao] isMemberCheck();");
//		
//		String sql = "SELECT COUNT(*) FROM tbl_member WHERE m_id = ?";
//		int result = jdbcTemplate.queryForObject(sql, Integer.class,m_id);
//		if(result > 0) {
//			return true;
//		}else {
//			return false;
//		}
//		 
//		
//	}
	

	// 회원 정보 추가 jdbcTemplate
//	public int insertMember(MemberVo vo) {
//		LOGGER.info("[MemberDao] insertMeber();");
//		
//		String sql = "INSERT INTO tbl_member(m_id, m_pw, m_name, m_gender, m_mail, m_phone, m_reg_date, m_mod_date)"
//				+ "VALUES(?,?,?,?,?,?,now(),now())";
//		
//		List<String> args = new ArrayList<String>();
//		args.add(vo.getM_id());
//		args.add(passwordEncoder.encode(vo.getM_pw()));
//		args.add(vo.getM_name());
//		args.add(vo.getM_gender());
//		args.add(vo.getM_mail());
//		args.add(vo.getM_phone());
//		int result = -1;
//		result = jdbcTemplate.update(sql,args.toArray());
//		
//		return result;
//	}
	
	
//	public MemberVo selectMember(MemberVo vo) {
//		LOGGER.info("[MemberDao] selectMember();");
//		String sql = "SELECT * FROM tbl_member WHERE m_id = ?";
//		
//		List<MemberVo> memberVos = new ArrayList<MemberVo>();
//		try {
//			
//			memberVos = jdbcTemplate.query(sql,new RowMapper<MemberVo>(){
//
//				@Override
//				public MemberVo mapRow(ResultSet rs, int rowNum) throws SQLException {
//					MemberVo memberVo = new MemberVo();
//					memberVo.setM_no(rs.getInt("m_no"));
//					memberVo.setM_id(rs.getNString("m_id"));
//					memberVo.setM_pw(rs.getString("m_pw"));
//					memberVo.setM_name(rs.getString("m_name"));
//					memberVo.setM_gender(rs.getString("m_gender"));
//					memberVo.setM_mail(rs.getString("m_mail"));
//					memberVo.setM_phone(rs.getString("m_phone"));
//					memberVo.setM_reg_date(rs.getString("m_reg_date"));
//					memberVo.setM_mod_date(rs.getString("m_mod_date"));
//					
//					return memberVo;
//				}
//			}, vo.getM_id());
//			
//			if(passwordEncoder.matches(vo.getM_pw(),memberVos.get(0).getM_pw()) == false) {
//				
//				memberVos.clear();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return memberVos.size() > 0 ? memberVos.get(0) : null;
//	}
//	
//	public List<MemberVo> selectMemberList(){
//		LOGGER.info("[MemberDao] selectMemberList();");
//		String sql = "SELECT * FROM tbl_member";
//		List<MemberVo> memberVos = new ArrayList<MemberVo>();
//		
//		memberVos = jdbcTemplate.query(sql,new RowMapper<MemberVo>(){
//
//			@Override
//			public MemberVo mapRow(ResultSet rs, int rowNum) throws SQLException {
//				MemberVo memberVo = new MemberVo();
//				memberVo.setM_no(rs.getInt("m_no"));
//				memberVo.setM_id(rs.getNString("m_id"));
//				memberVo.setM_pw(rs.getString("m_pw"));
//				memberVo.setM_name(rs.getString("m_name"));
//				memberVo.setM_gender(rs.getString("m_gender"));
//				memberVo.setM_mail(rs.getString("m_mail"));
//				memberVo.setM_phone(rs.getString("m_phone"));
//				memberVo.setM_reg_date(rs.getString("m_reg_date"));
//				memberVo.setM_mod_date(rs.getString("m_mod_date"));
//				
//				return memberVo;
//			}
//		});
//		return memberVos;
//	}
	
	
}


















