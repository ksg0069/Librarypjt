package com.goodee.library.member;

import java.lang.reflect.Member;
import java.security.SecureRandom;
import java.util.Date;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MemberService.class);
	
	@Autowired
	MemberDao memberDao;
	
	@Autowired
	JavaMailSenderImpl javaMailSenderImpl;
	
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
	
	public int modifyMember(MemberVo vo) {
		LOGGER.info("[MemberService] modifyMember();");
		return memberDao.updateMember(vo);
	}
	
	public MemberVo getLoginedMemberVo(int m_no) {
		LOGGER.info("[MemberService] getLoginedMemberVo();");
		
		return memberDao.selectMemberOne(m_no);
		
	}
	
	public int findPasswordConfirm(MemberVo vo) {
		LOGGER.info("[MemberService] findPasswordConfirm();");
		//1. 입력한 정보와 일치하는 사용자가 있는지 확인
		int result =0;
		
		MemberVo selectedMember = memberDao.selectMemberOne(vo);
		if(selectedMember != null) {
			//2. 새로운 비밀번호 생성
			String newPassword = createNewPassword();
			//3. 생성된 비밀번호 업데이트
			result = memberDao.updatePassword(vo.getM_id(),newPassword);
			if(result > 0) {
				//4. 메일 보내기
				sendNewPasswordByMail(vo.getM_mail(), newPassword);
				
			}
		
		}
		return result;
	}
	
	//메일로 비밀번호 보내기
	private void sendNewPasswordByMail(String toMailAddr, String NewPw) {
		LOGGER.info("[MemberService] sendNewPasswordByMail();");
		
		final MimeMessagePreparator mime = new MimeMessagePreparator() {
			
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				final MimeMessageHelper mimeHelper = new MimeMessageHelper(mimeMessage,true,"UTF-8");
				mimeHelper.setTo(toMailAddr);
				mimeHelper.setSubject("[구디도서관]새로운 비밀번호 안내입니다");
				mimeHelper.setText("새 비밀번호: "+NewPw,true);
				
			}
		};
		javaMailSenderImpl.send(mime);
		
		

	}
	
	//비밀번호 생성
	private String createNewPassword() {
		LOGGER.info("[MemberService] createNewPassword();");
		 char[] chars = new char[] {
		         '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
		         'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 
		         'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 
		         'u', 'v', 'w', 'x', 'y', 'z'
		         };
		 StringBuffer sb = new StringBuffer();
		 SecureRandom sr = new SecureRandom();
		 sr.setSeed(new Date().getTime()); //현재시간
		 int index = 0;
		 int length = chars.length;
		 for(int i = 0; i < 8; i++) {
			 index = sr.nextInt(length);
			 if(index % 2 ==0) {
				 sb.append(String.valueOf(chars[index]).toUpperCase());
			 }else {
				 sb.append(String.valueOf(chars[index]).toLowerCase());
			 }
			 
		 }
		 return sb.toString();

	}

}
