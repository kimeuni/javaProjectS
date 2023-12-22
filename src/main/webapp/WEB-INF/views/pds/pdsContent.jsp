<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<% pageContext.setAttribute("newLine", "\n"); %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>pdsContent.jsp</title>
	<jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
	<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/css/all.min.css" rel="stylesheet">
	<script>
		'use strict'
		//다운로드 수 증가시키기
		function downNumCheck(idx){
			$.ajax({
				url : "pdsDownNumCheck.pds",
				type : "post",
				data : {idx:idx},
				success : function(res){
					location.reload();
				},
				error : function(){
					
				}
			});
		}
		function reviewCheck(){
			let star = starForm.star.value;
			let review = $("#review").val();
			
			if(star == ""){
				alert("별점을 부여해 주세요");
				return false;
			}
			let query = {
				part : 'pds',
				partIdx : ${vo.idx},
				mid : '${sMid}',
				star : star,
				content : review
			}
			$.ajax({
				url : "reviewInput.ad",
				type : "post",
				data : query,
				success : function(res){
					if(res == "1"){
						alert("리뷰가 등록되었습니다.")
						location.reload();
					}
					else alert("리뷰 등록 실패~~");
				},
				error : function(){
					alert("전송 실패");
				}
			});
		}
		// 화살표클릭시 화면 처음으로 부드럽게 이동시키기
		$(window).scroll(function(){ //윈도우이에 스크롤이 일어났을 때
			if($(this).scrollTop() > 100) { //현재 스크롤이 100px 아래로 갈시
				$("#topBtn").addClass("on");  //화살표 추가
			}
			else {
				$("#topBtn").removeClass("on");  //아닐 시 삭제
			}
			$("#topBtn").click(function(){
				window.scrollTo({top:0,behavior:"smooth"}) //현재 페이지에서 특정 위치로 스크롤이동시키는 명령어  //만약 topBtn을 클릭했을 시 스크롤 위치 0 .. 그리고 부드럽게 올라가기
			});
		});  
		
		
	</script>
	<style>
		th{
			background-color:#eee;
		}
		/* 별점 스타일 설정하기 */
		#starForm fieldset{
			direction: rtl;
		}
		#starForm input[type=radio]{
			display:none;
		}
		#starForm label{
			font-size:1.6em;
			color : transparent;
			text-shadow:0 0 0 #f0f0f0;
		}
		#starForm label:hover{
			/* rgba중에 rgb는 색.. a부분은 투명도.. */
			text-shadow:0 0 0 rgba(250,200,0,0.95);
		}
		#starForm label:hover ~ label {
			text-shadow:0 0 0 rgba(250,200,0,0.95);
		}
		#starForm input[type="radio"]:checked ~ label {
			text-shadow:0 0 0 rgba(250,200,0,0.95);
		}
		
		#topBtn{
		    position: fixed;
		    bottom: 50px;
		    right: 100px;
		}
	</style>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp"></jsp:include>
<div class="w3-content" style="max-width:2000px;margin-top:46px">
<jsp:include page="/WEB-INF/views/include/slide2.jsp"></jsp:include>
<p><br/></p>
<div class="container">
	<h2 class="text-center">자 료 내 용 상 세 보 기</h2>
	<br/>
	<table class="table table-bordered text-center">
		<tr>
			<th>올린이</th>
			<td>${vo.nickName}</td>
			<th>올린날짜</th>
			<td>${fn:substring(vo.fDate,0,fn:length(vo.fDate)-2)}</td>
		</tr>
		<tr>
			<th>파일명</th>
			<td>
				<c:set var="fNames" value="${fn:split(vo.fName,'/')}"/>
				<c:set var="fSNames" value="${fn:split(vo.fSName,'/')}"/>
				<c:forEach var="fName" items="${fNames}" varStatus="st">
					<a href="${ctp}/images/pds/${fSNames[st.index]}" download="${fName}" onclick="downNumCheck(${vo.idx})">${fName}</a><br/> <!-- $ {fSName} : 다운받는 실제 파일 이름 $ {fName} : 화면에 보여주는 이름 -->
				</c:forEach>
				(<fmt:formatNumber value="${vo.fSize/1024}" pattern="#,##0"/> KByte) 
			</td>
			<th>다운횟수</th>
			<td>${vo.downNum}</td>
		</tr>
		<tr>
			<th>분류</th>
			<td>${vo.part}</td>
			<th>접속IP</th>
			<td>${vo.hostIp}</td>
		</tr>
		<tr>
			<th >제목</th>
			<td class="text-left" colspan="3">${vo.title }</td>
		</tr>
		<tr>
			<th>상세내용</th>
			<td class="text-left" colspan="3">${fn :replace(vo.content,newLine,"br/")}</td>
		</tr>
		<tr> 
			<td colspan="4"><input type="button" value="돌아가기" onclick="location.href='pdsList.pds?pageSu=${pageSu}&pageSize=${pageSize}&part=${part}'" class="btn btn-success"></td>
		</tr>
	</table>
	<hr/>
	<div class="text-center">
		<!-- 만복문 돌려 이미지 출력하기 -->
		<c:forEach var="fSName" items="${fSNames}" varStatus="st">
			${st.count}. ${fSName}<br/>
			<c:set var="len" value="${fn:length(fSName)}"/>
			<c:set var="ext" value="${fn:substring(fSName,len-3,len)}"/> <!-- 확장자 명이 몇개인지 알면 이렇게 사용가능 -->
			<c:set var="exLower" value="${fn:toLowerCase(ext)}"/> <!-- 무조건 소문자로 -->
			<c:if test="${exLower == 'jpg' || extLower == 'gif' || exLower == 'png' }">
				<img src="${ctp}/images/pds/${fSName}" width="85%"/>
			</c:if>
			<hr/>
		</c:forEach>
	</div>
	<hr/>
	<div>별점 
		<form name="starForm" id="starForm" >
			<fieldset style="border:0px">
				<div class="text-left viewPoint m-0 b-0">
				<div class="text-right">리뷰평점 : <fmt:formatNumber value="${reviewAvg}" pattern="#.0"/></div> <!-- fmt로 수소이하 1자리 처리 -->
					<input type="radio" name="star" id="star1" value="5"/><label for="star1">★</label>
					<input type="radio" name="star" id="star2" value="4"/><label for="star2">★</label>
					<input type="radio" name="star" id="star3" value="3"/><label for="star3">★</label>
					<input type="radio" name="star" id="star4" value="2"/><label for="star4">★</label>
					<input type="radio" name="star" id="star5" value="1"/><label for="star5">★</label>
					<span class="text-bold"> : 별점을 선택해주세요 ■</span>
				</div>
			</fieldset>
				<div>
					<textarea name="review" id="review" rows="3" placeholder="별점 후기를 남겨주시면 200포인트를 지급합니다." class="form-control"></textarea>
				</div>
				<div>
					<input type="button" value="별점/리뷰등록" onclick="reviewCheck()" class="btn btn-primary mt-2 form-control" />
				</div>
		</form>
	</div>
	<hr/>
	<c:forEach var="vo" items="${rVOS}" varStatus="st">
	  <div id="reviewBox">
	  	<div class="row">
	  	  <div class="col text-left"><b>${vo.mid}</b> ${fn:substring(vo.rDate,0,10)}</div>
	  	  <div class="col"></div>
	  	  <div class="col text-right">
	  	    <c:forEach var="i" begin="1" end="${vo.star}" varStatus="iSt">
	  	    	<font color="gold">★</font>
	  	    </c:forEach>
	  	    <c:forEach var="i" begin="1" end="${5 - vo.star}" varStatus="iSt">☆</c:forEach>
	  	  </div>
	  	</div>
	  	<div class="row border m-1 p-2">
	  	  ${fn:replace(vo.content,newLine,'<br/>')}
	  	</div>
	  </div>
	  <hr/>
	</c:forEach>
</div>
<div>
	<h6 id="topBtn" class="text-right"><i class='far fa-arrow-alt-circle-up' style='font-size:36px'></i></h6>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
</body>
</html>