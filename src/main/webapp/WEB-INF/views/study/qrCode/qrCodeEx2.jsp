<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>qrCodeEx2.jsp</title>
    <jsp:include page="/WEB-INF/views/include/bs4.jsp" />
    <script>
    	'use strict'
    	function qrCreate(){
    		let moveUrl = $("#moveUrl").val();
    		
    		if(moveUrl.trim() == ""){
    			alert("공백-_- No")
    			return false;
    		}
    		
    		let query = {
   				moveUrl : moveUrl,
    		}
    		
    		$.ajax({
    			url : "${ctp}/study/qrCode/qrCodeEx2",
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
    <h2>소개할 사이트를 QR코드에 담아서 생성하기</h2>
    <div>(소개하고 싶은 사이트의 주소를 입력해주세요 : 예 - 고궁, 문화재, 상품, ...)</div>
    <form method="post">
	    <table class="table table-bordered">
	    	<tr>
	    		<th>이동할 주소</th>
	    		<td><input type="text" name="moveUrl" id="moveUrl" value="https://mvnrepository.com/artifact/com.google.zxing/javase/3.5.0" class="form-control" /></td>
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