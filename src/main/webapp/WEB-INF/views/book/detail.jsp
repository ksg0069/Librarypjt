<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>도서 상세 화면</title>
<link href="<c:url value='/resources/css/book/detail.css'/>" rel="stylesheet" type="text/css">
</head>
<body>
   <jsp:include page="../include/header.jsp"/>
   <jsp:include page="../include/nav.jsp"/>
   <section>   
      <div id="section_wrap">
         <div class="word">
            <h3>도서 상세 보기</h3>
         </div>
         <div class="book_detail">
            <ul>
               <li>
                  <img src="<c:url value="/libraryUploadImg/${bookVo.b_thumbnail}"/>">
               </li>
               <li>
                  <table>
                     <tr>
                        <td>도서명</td>
                        <td>${bookVo.b_name}</td>
                     </tr>
                     <tr>
                        <td>저자</td>
                        <td>${bookVo.b_author}</td>
                     </tr>
                     <tr>
                        <td>발행처</td>
                        <td>${bookVo.b_publisher}</td>
                     </tr>
                     <tr>
                        <td>발행년도</td>
                        <td>${bookVo.b_publish_year}</td>
                     </tr>
                     <tr>
                        <td>등록일</td>
                        <td>${bookVo.b_reg_date}</td>
                     </tr>
                     <tr>
                        <td>수정일</td>
                        <td>${bookVo.b_mod_date}</td>
                     </tr>
                  </table>
               </li>
            </ul>
            
         </div>
         
         <div class="buttons">
            <a class="modify_book_button" href="/book/modify/${bookVo.b_no}">도서 수정</a>
            <a class="delete_book_button" onclick="deleteBook('${bookVo.b_no}')">도서 삭제</a>         
         </div>
      </div>
   </section>
   <script type="text/javascript">
   	function deleteBook(bookNo){
   		//1. 도서 삭제 유무 확인
   		let result = confirm('해당 도서를 정말 삭제하시겠습니까?');
   		//2. 도서 삭제 후 결과 arlert로 알려주기
   		if(result){
   			fetch('/book/'+bookNo,{
   				method:'DELETE',
   				headers:{
   					'Content-Type':'application/json;charset=utf-8'
   				}
   				
   			})
   			.then(response => response.text())
   			.then(data => {
   				if(data === '400'){
   					
   					alert('삭제 성공');
   					location.replace('/book');
   				}else{
   					alert('삭제 실패');
   				}
   				
   			})
   			.catch(error=>{
   				console.log(error);
   			});
   		}
   		
   	}
   </script>
</body>


















</html>