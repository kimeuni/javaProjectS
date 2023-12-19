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
		function pwdCheck(flag,mid){
			let pwd = $("#pwd").val();
			
			let query = {
				flag : flag,
				mid : mid,
				pwd : pwd
			}
			$.ajax({
				url : "${ctp}/member/memberPwdCheckOk",
				type : "post",
				data : query,
				success : function(res){
					if(res == "0"){
						alert("비밀번호를 다시 확인해주세요.");
					}
					else if(res == "update") location.href="${ctp}/member/memberUpdate?mid="+mid;
					else if(res == "pwdChange") location.href="${ctp}/member/memberPwdChange";
				},
				error : function() {
					alert("전송 오류")
				}
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
		<form >
			<table class="table table-bordered " style="width:800px; margin:0 auto;">
				<tr>
					<th>비밀번호 확인</th>
					<td>
						<input type="password" name="pwd" id="pwd"  autofocus required class="form-control" />
						<input type="button" value="확인" onclick="pwdCheck('${flag}','${sMid}')" class="btn btn-success form-control mt-3 mr-3"/>
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