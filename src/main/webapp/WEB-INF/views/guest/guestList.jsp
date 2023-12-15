<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<% pageContext.setAttribute("newLine", "\n"); %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>guestList</title>
	<jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
	<script>
		'use strict'
		function btnD(idx){
			let ans = confirm("정말로 삭제하시겠습니까?");
			
			if(ans){
				location.href="guest/guestDe?idx="+idx;
			}
		}
		
		// 관리자로 로그인한 경우만 삭제가능한 버튼
		function delCheck(idx){
			let ans = confirm("현재 게시글을 삭제하시겠습니까?");
			
			if(ans){
				location.href="guestDelete?idx="+idx;
			}
		}
		
		function pageS(){
			let pageSize = document.getElementById("pageSize").value;
			location.href="guestList?pageSize="+pageSize;
		}
		
	</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp"></jsp:include>
<div class="w3-content" style="max-width:2000px;margin-top:46px">
<jsp:include page="/WEB-INF/views/include/slide2.jsp"></jsp:include>
<p><br/></p>
<div class="container">
	<h2 class="text-center">방 명 록 리 스 트</h2>
	<table class="table table-borderless">
		<tr>
			<!-- 실무에서는 버튼 태그<botton></botton>를 사용한다... -->
			<!-- 현재 a(앵커) 태그를 사용하는 이유는 마우스를 올리면 주소가 밑에 나오기 때문이다.(학습용) -->
			
			<!-- 세션 값이 들어가 있지 않으면 로그인창으로 이동 -->
			<c:if test="${sAdmin != 'adminOk'}">
				<td><a href="adminLogin" class="btn btn-secondary">관리자</a></td>
			</c:if>
			<c:if test="${sAdmin == 'adminOk'}">
				<td><a href="adminLogout" class="btn btn-secondary">관리자 로그아웃</a></td>
			</c:if>
			<!-- /guestInput으로 적으면 컨텍스트 루트가 아닌, url주소로 보기 때문에 /를 넣으면 안된다. (같은 위치기 때문에 guest/...를 안붙여도 됨. -->
			<td class="text-right"><a href="guestInput" class="btn btn-success">글쓰기</a></td>
		</tr>
	</table>
	<table class="table borderless m-0 p-0">
		<tr>
			<td>
				<select name="pageSize" id="pageSize" onchange="pageS()" >
					<option <c:if test="${pageSize == 2}">selected</c:if>>2</option>
					<option <c:if test="${pageSize == 3}">selected</c:if>>3</option>
					<option <c:if test="${pageSize == 5}">selected</c:if>>5</option>
					<option <c:if test="${pageSize == 10}">selected</c:if>>10</option>
				</select>건
			</td>
			<!-- 페이지처리 시작(이전/다음) -->
			<td class="text-right">
				<c:if test="${pag > 1}">
					<a href="guestList?pag=1&pageSize=${pageSize}" title="첫페이지" >◁◁</a>
					<a href="guestList?pag=${pag-1}&pageSize=${pageSize}" title="이전페이지">◀</a>
				</c:if>	
				${pag} / ${totPage}
				<c:if test="${pag < totPage}">
					<a href="guestList?pag=${pag+1}&pageSize=${pageSize}" title="다음페이지">▶</a>
					<a href="guestList?pag=${totPage}&pageSize=${pageSize}" title="마지막페이지">▷▷</a>
				</c:if>	
			</td>
			<!-- 페이지처리 끝(이전/다음) -->
		</tr>
	</table>
	<hr/>
	<!-- 번호 역순으로 뿌리기 -->
	<%-- <c:set var="curScrStartNo" value="${curScrStartNo}"/> --%> <!-- GuestList에서 request로 넘겼기 때문에 선언을 안하고 ${curScrStartNo}로 사용해도 괜찮다... -->
	<c:forEach var="vo" items="${vos}" varStatus="st">
		<table class="table table-borderless  m-0 p-0">
			<tr>
				<!-- 역순 출력 실패..(스스로 해본 거) -->
				<%-- <c:set var="cnt" value="0"/>
				<c:forEach var="i" begin="${fn:length(vos)-1}" end="0" step="-1">
					<c:if test="${vo.idx !=null }">
						<c:set var="cnt" value="${fn:length(vos)-i}"/>
						<td class="text-left">번호 : ${cnt}</td>
					</c:if> 
				</c:forEach> --%>
				<td class="text-left">
					번호 : ${curScrStartNo} <%-- ${vo.idx} --%>
					<c:if test="${sAdmin == 'adminOk'}"><a href="javascript:delCheck(${vo.idx})" class="btn btn-danger btn-sm">삭제</a></c:if>
				</td>
				<td class="text-right">
					방문IP : ${vo.hostIp}
					<%-- <input type="button" name="deleteBtn" onclick="btnD(${vo.idx})" value="글 삭제(집에서 만든거)" class="btn btn-danger"/> --%>
				</td>
			</tr>
		</table>
		<table class="table table-bordered">
			<tr>
				<th>성명</th>
				<td>${vo.name}</td>
				<th>방문일자</th>
				<td>${fn: substring(vo.visitDate,0,19)}</td>
			</tr>
			<tr >
				<th>메일주소</th>
				<td colspan="3">
				<c:if test="${empty vo.email || fn:length(vo.email)<5 || fn: indexOf(vo.email,'@') == -1 || fn: indexOf(vo.email,'.') == -1 }">
					- 잘못된 메일 주소 -
				</c:if>
				<c:if test="${!empty vo.email && fn:length(vo.email)>=5 && fn: indexOf(vo.email,'@') != -1 && fn: indexOf(vo.email,'.') != -1 }">
					${fn: substring(vo.email,0,19)}
				</c:if>
				</td>
			</tr>
			<tr>
				<th>홈페이지</th>
				<td colspan="3">
					<!-- 잘못된 주소 간단한 것 처리(주소가 비어있으면,글자가 10글자 아래면, 주소안에 .이 들어가 있지 않으면..) -->
					<c:if test="${empty vo.homePage || fn:length(vo.homePage)<10 || fn: indexOf(vo.homePage,'.')== -1}">
						- 잘못된 주소 -
					</c:if>
					<!-- else if가 없기 때문에 배타적으로 적어야 함. -->
					<c:if test="${!empty vo.homePage && fn:length(vo.homePage)>=10 && fn: indexOf(vo.homePage,'.')!= -1}">
						<a href="${vo.homePage}" target="_blank">${vo.homePage}</a>
					</c:if>
				</td>
			</tr>
			<tr>
				<th>방문소감</th>
				<!-- 글을 여러줄 작성했을 시, 한 줄 내려가는 처리를 하기 위해서 이렇게 적었다. -->
				<!-- 직접적으로 '\n'으로 적으면 오류가 뜨기 때문에 위에 변수에서 변수에 담아서 사용한다. -->
				<td colspan="3">${fn:replace(vo.content,newLine,'<br/>')}</td>
			</tr>
		</table>
		<c:set var="curScrStartNo" value="${curScrStartNo-1}"/>
	</c:forEach>
	<br/><br/>
	<!-- 블록페이지 시작(1블록의 크기를 3개(3Page)로 한다. -->
	<div class="text-center">
		<ul class="pagination justify-content-center">
		<c:if test="${pag > 1}"><li class="page-item"><a class="page-link text-secondary " href="guestList?pag=1&pageSize=${pageSize}">첫페이지</a></li></c:if>
		<!-- 이전블록 pag=${(curBlock-1)*blockSize+1} 부분 해석 => ex)1블럭(4/5/6)일 경우, blockSize는 3 /  1(블럭)-1 = 0 , 0*3(blockSize)=0 , 0+1=1 ==> 1블럭에서 "이전블록"을 클릭했을 시, 1page로 간다..라는 의미이다.. -->
		<c:if test="${curBlock > 0}"><li class="page-item"><a class="page-link text-secondary" href="guestList?pag=${(curBlock-1)*blockSize+1}&pageSize=${pageSize}">이전블록</a></li></c:if>
		<c:if test="${pag > 1}"><li class="page-item"><a class="page-link text-secondary" href="guestList?pag=${pag-1}&pageSize=${pageSize}">◀</a></li></c:if>
		<c:forEach var="i" begin="${(curBlock * blockSize)+1}" end="${(curBlock * blockSize)+blockSize}" varStatus="st">
			<!-- 토탈페이지 보다 작을때까지 돌아가야 글이 적혀져 있는 페이지까지만 나온다..  -->
			<!-- 현재 페이지일 경우 폰트색을 빨간색, 글씨 굴게로 설정 -->
			<c:if test="${i <= totPage && pag == i}"><li class="page-item active"><a class="page-link bg-secondary border-secondary" href="guestList?pag=${i}&pageSize=${pageSize}">${i}</a></c:if>
			<c:if test="${i <= totPage && pag != i}"><li class="page-item"><a class="page-link text-secondary" href="guestList?pag=${i}&pageSize=${pageSize}">${i}</a></c:if>
		</c:forEach>
		<c:if test="${pag < totPage }"><li class="page-item"><a class="page-link text-secondary" href="guestList?pag=${pag+1}&pageSize=${pageSize}">▶</a></li></c:if>
		<c:if test="${curBlock < lastBlock}"><li class="page-item"><a class="page-link text-secondary" href="guestList?pag=${(curBlock+1)*blockSize+1}&pageSize=${pageSize}">다음블록</a></li></c:if>
		<c:if test="${pag < totPage}"><li class="page-item"><a class="page-link text-secondary" href="guestList?pag=${totPage}&pageSize=${pageSize}">마지막페이지</a></li></c:if>
		</ul>
	</div>
	<!-- 블록페이지 끝 -->
	<!-- 
	1. 내가 현재 어디 블록에 있는지 알아여됨
	2. 마지막 블럭을 구해야 한다
	
	
	3. 화면에 뿌리기
	현재블록(1) : 이전블록(0) :     (현재블록이 1에 있을경우 이전블록은 0으로..)
	4/5/6             1   
	
	[이전블록 구하는 공식] (0블록이 시작이지만, 이전페이지로 넘어가기 위해서는 무조건 1블록 이상이 있어야 한다../ 0블록에서는 이전으로 가지 못하지만, 1블록에서 이전페이지를 누르면 밑 1)에 대한 공식에 의해서 0블럭 1페이지로 간다)
	(1블록-1)*3+1  = 1페이지
	(2블록-1)*3 +1 = 4페이지
	(3블록-1)*3 +1 = 7페이지
	
	현재블록(1) : 다음블록(2)
	(1블록+1)*3+1  = 7페이지   )   (==> 1+1=2  , 2*3=6 , 6+1=7 이브로 7페이지..)
	(2블록+1)*3+1  = 1페이지 -->
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
</body>
</html>