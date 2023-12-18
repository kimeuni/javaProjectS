<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>memberLogin.jsp</title>
	<jsp:include page="/WEB-INF/views/include/bs4.jsp" />
	<style>
		
	</style>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp"></jsp:include>
<div class="w3-content" style="max-width:2000px;margin-top:46px">
<jsp:include page="/WEB-INF/views/include/slide2.jsp"></jsp:include>
<p><br/></p>
<div class="container">
	<h2 style="text-align:center;">로 그 인</h2>
	<p><br/></p>
	<div class="container">
		<form name="loginForm" method="post">
			<table class="table table-bordered " style="width:800px; margin:0 auto;">
				<tr>
					<th colspan="2" class="text-center">회원 로그인</th>
				</tr>
				<tr>
					<th>아이디</th>
					<td>
						<input type="text" name="mid" id="mid" value="${cMid}" autofocus required class="form-control" />
						<!-- id저장을 해서 쿠키에 값이 있으면..ID저장 체크를 하고, 아니면 ID저장에 체크를 하지 않도록 한다. -->
						<c:if test="${cMid != null}"><input type="checkbox" name="idCheck" value="save" checked>ID저장</c:if>
						<c:if test="${cMid == null}"><input type="checkbox" name="idCheck" value="save">ID저장</c:if>
					</td>
				</tr>
				<tr>
					<th>비밀번호</th>
					<td><input type="password" name="pwd" id="pwd" value="1234" required class="form-control" /></td>
				</tr>
				<tr>
					<td colspan="2" class="text-center">
						<input type="submit" value="로그인" class="btn btn-success mr-3"/>
						<input type="reset" value="다시입력" class="btn btn-warning "/>
						<input type="button" value="회원가입" onclick="location.href='memberJoin';" class="btn btn-info "/>
					</td>
				</tr>
			</table>
			<table style="width:1050px; margin: 10px">
				<tr>
					<td colspan="2" class="text-center">
						[<a href="memberFindMid.mem">아이디찾기</a>] /
						[<a href="memberFindPwd.mem">비밀번호찾기</a>]
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