<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>memberMain.jsp</title>
	<jsp:include page="/WEB-INF/views/include/bs4.jsp" />
	<script>
		'use strict'
		function chatInput(){
			let chat = $("#chat").val();
			
			if(chat.trim() != ""){
				$.ajax({
					url : "memberMassageInput.mem",
					type : "post",
					data : {chat:chat},
					error : function(){
						alert("전송오류")
					}
				});
			}
		}
		
		
		// 13번은 Enter..
		// Enter를 했을 시 chatInput()를 불러서 처리하도록 한다
		$(function() {
	    	$("#chat").on("keydown", function(e) {
	    		if(e.keyCode == 13) {
	    			chatInput();
	    		}
	    	});
	    });
	</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp"></jsp:include>
<div class="w3-content" style="max-width:2000px;margin-top:46px">
<jsp:include page="/WEB-INF/views/include/slide2.jsp"></jsp:include>
<p><br/></p>
<div class="container">
	<h2>회 원 전 용 방</h2>
	<hr/>
	<c:if test="${level == 1 }">
		<pre>
			정회원 등업조건....
		</pre>
	</c:if>
	<hr/>
	<!-- 실시간 DB 채팅 -->
	<div style="width: 500px">
		<form name="chatForm">
			<label for="chat"><b>실시간 대화방</b></label>
			<iframe src="${ctp}/include/memberMessage.jsp" width="100%" height="250px" class="border"></iframe>
			<div class="input-group mt-1">
				<input type="text" name="chat" id="chat" class="form-control" placeholder="대화내용을 입력하세요." autofocus/>
				<div class="input-group-append">
					<input type="button" value="글등록" onclick="chatInput()" class="btn btn-success"/> 
				</div>
			</div>
		</form>
	</div>
	<hr/>
	<div><img src="${ctp}/member/noimage.jpg" width="200px"></div>
	<div>
		<p>현재 <font color="blue"><b>${sNickName}(${strLevel})</b></font>님이 로그인 중이십니다.</p>
		<p>총 방문횟수 : ${mVO.visitCnt }</p>
		<p>오늘 방문횟수 : ${mVO.todayCnt}</p>
		<p>총 보유 포인트 : ${mVO.point }</p>
		<hr/>
		<h4>활동내역 : </h4>
		<p>방명록에 올린글 수 : </p>  <!-- 닉네임이 같은게 올라와 있으면 방명록에 올린글 수 올라가는 것으로 처리함. -->
		<p>게시판에 올린글 수 : </p>
		
		
	</div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
</body>
</html>