<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입 성공</title>
<link href="<c:url value='/resources/css/member/create_result.css'/>" rel="stylesheet" type="text/css">
</head>
<body>
   <jsp:include page="../include/header.jsp" />
   <jsp:include page="../include/nav.jsp" />
   <section>
      <div id="section_wrap">
         <div class="word">
            <h3>회원가입 성공! 로그인해주세요.</h3>
         </div>
         <div class="others">
            <a href="<c:url value='' />">로그인</a>
         </div>
      </div>
   </section>
</body>
</html>