<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>guestInput.jsp</title>
	<jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
	<style>
		.pil{
			color : red;
		}
	</style>
	<script>
		'use strict'
		function contentC(){
			let name = document.getElementById("name").value.trim();
			let cont = document.getElementById("cont").value.trim();
			
			if(name == ""){
				alert("성명을 입력하세요.")
			}
			else if(cont == ""){
				alert("본문 내용을 입력해주세요.")
			}
			else {
				myform.submit();
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
	<h2 class="text-center mb-4">게 시 글 작 성 하 기</h2>
	<form name="myform" method="post" >
		<table class="table table-bordered">
			<tr>
				<th>성명<span class="pil">*</span></th>
				<td colspan="3"><input type="text" name="name" id="name" class="form-control" required autofocus/></td>
			</tr>
			<tr>
				<th>이메일</th>
				<td><input type="text" name="email" id="email" class="form-control""/></td>
				<th>홈페이지</th>
				<td><input type="text" name="homePage" id="homeP" class="form-control" value="https://"></td>
			</tr>
			<tr>
				<th>글 작성<span class="pil">*</span></th>
				<td colspan="3"><textarea rows="8" name="content" id="cont" class="form-control"></textarea></td>
			</tr>
			<tr>
				<td colspan="4" class="text-right">
				<input type="reset" name="btnRe" value="다시쓰기" class="btn btn-warning" />
				<input type="button" onclick="contentC()" name="btn" value="글쓰기" class="btn btn-success" />
				</td>
			
			</tr>
		</table>
			<input type="hidden" name="hostIp" value="${pageContext.request.remoteAddr}"/>
	</form>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
</body>
</html>