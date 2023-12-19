<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>memberPwdChange.jsp</title>
    <jsp:include page="/WEB-INF/views/include/bs4.jsp" />
    <script>
    	'use strict'
    	function pwdCheck(){
    		let pwd = $("#pwd").val();
    		let pwdOk = $("#pwdOk").val();
    		
    		if(pwd.trim() == ""){
    			alert("수정 비밀번호를 입력하세요.");
    			$("#pwd").focus;
    			return false;
    		}
    		else if(pwdOk.trim() == ""){
    			alert("비밀번호 확인을 입력하세요.");
    			$("#pwdOk").focus;
    			return false;
    		}
    		else if(pwd != pwdOk){
    			alert("수정 비밀번호와 비밀번호 확인이 같지 않습니다.");
    			$("#pwd").focus;
    			return false;
    		}
    		else {
    			
    		}
    		
    	}
    </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp"></jsp:include>
<div class="w3-content" style="max-width:2000px;margin-top:46px">
<jsp:include page="/WEB-INF/views/include/slide2.jsp"></jsp:include>
<p><br/></p>
<div class="container">
    <h2 class="text-center mb-3">비밀번호 변경</h2>
    <form>
    	<table class="table tabel-hover">
    		<tr>
    			<th>수정 비밀번호</th>
    			<td><input type="password" name="pwd" id="pwd" autofocus required class="form-control" /></td>
    		</tr>
    		<tr>
    			<th>비밀번호 확인</th>
    			<td><input type="password" name="pwdOk" id="pwdOk" required class="form-control" /></td>
    		</tr>
    		<tr>
    			<td colspan="2"><input type="button" value="비밀번호 변경" onclick="pwdCheck()" class="form-control btn btn-success" /></td>
    		</tr>
    	</table>
    </form>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
</body>
</html>