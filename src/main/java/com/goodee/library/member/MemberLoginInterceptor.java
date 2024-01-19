package com.goodee.library.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class MemberLoginInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		if(session != null) {
			Object obj = session.getAttribute("loginMember");
			if(obj != null) {
				return true;
			}
			}
			response.sendRedirect(request.getContextPath()+"/member/login");
			return false;
		
		
		
	}
}
