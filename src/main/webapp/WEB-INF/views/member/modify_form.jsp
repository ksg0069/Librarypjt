<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 수정</title>
<link href="<c:url value='/resources/css/member/modify_form.css'/>" rel="stylesheet" type="text/css">
</head>
<body>
   <jsp:include page="../include/header.jsp"/>
   <jsp:include page="../include/nav.jsp"/>
   <section>
      <div id="section_wrap">
         <div class="word">
            <h3>회원 정보 수정</h3>
         </div>
         <br>
         <div class="modify_account_form">
            <form action="<c:url value='/member/${loginMember.m_no}' />" name="modify_account_form" method="post">
               <input type="hidden" name="m_no" value="${loginMember.m_no}">
               <input type="text" name="m_id" value="${loginMember.m_id}" readonly disabled> <br>
               <input type="password" name="m_pw" value="******" readonly disabled> <br>
               <input type="text" name="m_name" value="${loginMember.m_name}" placeholder="이름"> <br>
               <select name="m_gender">
                  <option value="">성별 선택</option>
                  <c:set var="gender" value="${loginMember.m_gender}" />
                  <option value="M" <c:if test = "${gender eq 'M'}"> selected </c:if>>남성</option>
                  <option value="W" <c:if test = "${gender eq 'W'}"> selected </c:if>>여성</option>
               </select> <br>
               <input type="email"   name="m_mail"   value="${loginMember.m_mail}" placeholder="이메일" ><br>
               <input type="text"   name="m_phone" value="${loginMember.m_phone}" placeholder="전화번호"> <br>
               <input type="button" value="정보수정" onclick="modifyMemberForm();">
               <input type="reset"   value="초기화">
            </form>
         </div>
      </div>   
   </section>   
   <script type="text/javascript">
      function modifyMemberForm() {
         let form = document.modify_account_form;
         if (form.m_name.value == '') {
            alert('이름을 입력하세요.');
            form.m_name.focus();
         } else if (form.m_gender.value == '') {
            alert('성별을 입력하세요.');
            form.m_gender.focus();
         } else if (form.m_mail.value == '') {
            alert('메일 주소를 입력하세요.');
            form.m_mail.focus();
         } else if (form.m_phone.value == '') {
            alert('전화번호를 입력하세요.');
            form.m_phone.focus();
         } else {
            form.submit();
         }
      }
</script>
</body>
</html>