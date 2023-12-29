<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>qrCodeEx1.jsp</title>
    <jsp:include page="/WEB-INF/views/include/bs4.jsp" />
    <script>
    	'use strict'
    	function qrCreate(){
    		let mid = $("#mid").val();
    		let name = $("#name").val();
    		let email = $("#email").val();
    		
    		if(mid.trim() == "" || name.trim() == "" || email.trim() == ""){
    			alert("공백-_- No")
    			return false;
    		}
    		
    		let query = {
    			mid : mid,
    			name : name,
    			email : email
    		}
    		
    		$.ajax({
    			url : "${ctp}/study/qrCode/qrCodeEx1",
    			type : "post",
    			data : query,
    			success : function(res){
    				if(res != ""){
    					alert("생성된 QR코드명 : " + res);
    					let qrCode = 'QR Code 명 : ' + res + '<br/>';
    					qrCode += '<img src="${ctp}/qrCode/'+ res +'.png">';
    					
    					$("#demo").html(qrCode);
    				}
    				else alert("QR코드 생성 실패~~")
    			},
    			error : function(){
    				alert("전송오류")
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
    <h2>개인정보를 QR코드로 생성하기</h2>
    <form method="post">
	    <table class="table table-bordered">
	    	<tr>
	    		<th>아이디</th>
	    		<td><input type="text" name="mid" id="mid" value="${sMid}" class="form-control" /></td>
	    	</tr>
	    	<tr>
	    		<th>성명</th>
	    		<td><input type="text" name="name" id="name" value="${sNickName}" class="form-control" /></td>
	    	</tr>
	    	<tr>
	    		<th>이메일</th>
	    		<td><input type="text" name="email" id="email" value="axdc1234@naver.com" class="form-control" /></td>
	    	</tr>
	    	<tr>
	    		<td colspan="2" class="text-center">
	    			<input type="button" value="QR코드 생성" onclick="qrCreate()" class="btn btn-success"/>
	    			<input type="button" value="다시입력" onclick="location.reload()" class="btn btn-warning"/>
	    			<input type="button" value="돌아가기" onclick="location.href='qrCodeForm'" class="btn btn-info"/>
	    		</td>
	    	</tr>
	    </table>
    </form>
    <hr/>
    <div> 생성된 QR 코드 : <br/>
    	<div id="demo"></div>
    </div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
</body>
</html>