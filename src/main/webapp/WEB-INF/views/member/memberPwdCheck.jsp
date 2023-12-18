<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>memberPwdCheck.jsp</title>
	<jsp:include page="/WEB-INF/views/include/bs4.jsp" />
	<style>
	</style>
	<script>
		'use strict'
		function pwdCheck(){
			let pwd = $("#pwd").val();
			
			$.ajax({
				
			});
		}
	</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp"></jsp:include>
<div class="w3-content" style="max-width:2000px;margin-top:46px">
<jsp:include page="/WEB-INF/views/include/slide2.jsp"></jsp:include>
<p><br/></p>
<div class="container">
	<h2 style="text-align:center;">비밀번호 확인</h2>
	<p><br/></p>
	<div class="container">
		<form name="loginForm" method="post">
			<table class="table table-bordered " style="width:800px; margin:0 auto;">
				<tr>
					<th>비밀번호 확인</th>
					<td>
						<input type="password" name="pwd" id="pwd"  autofocus required class="form-control" />
						<input type="submit" value="로그인" onclick="pwdCheck()" class="btn btn-success mr-3"/>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<p><br/></p>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
</body>
</html>