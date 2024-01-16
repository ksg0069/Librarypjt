package com.goodee.library.member;

import java.lang.reflect.Member;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MemberService.class);
	
	@Autowired
	MemberDao memberDao;
	
	public int createMember(MemberVo vo) {
		LOGGER.info("[MemberService] createMember();");
		
		int result =0;
		
		if(memberDao.isMemberCheck(vo.getM_id()) == false) {
			return memberDao.insertMember(vo);
		}
		return result;
	}
	
	public MemberVo loginMember(MemberVo vo) {
		LOGGER.info("[MemberService] loginMember();");
		MemberVo loginedMember = memberDao.selectMember(vo);
		return loginedMember;
		
	}
	
	public List<MemberVo> listupMember(){
		LOGGER.info("[MemberService] listupMember();");
		return memberDao.selectMemberList();
	}

}
