<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>errorMain.jsp</title>
    <jsp:include page="/WEB-INF/views/include/bs4.jsp" />
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp"></jsp:include>
<div class="w3-content" style="max-width:2000px;margin-top:46px">
<jsp:include page="/WEB-INF/views/include/slide2.jsp"></jsp:include>
<p><br/></p>
<div class="container">
    <pre>
    	JSP(view)파일에서의 서블릿에러가 발생시는 JSP파일 상단에 @page 지시자를 이용한 에러페이지로 이동처리한다.
    	< % @ page errorPage = "에러발생시 처리할 jsp파일 경로와 파일명 지정하기" % >
    </pre>
    <div>
	    <a href="error1" class="btn btn-success mr-2">JSP파일에서 오류페이지 호출</a>
	    <a href="errorMessage1" class="btn btn-success mr-2">오류발생시 호출할 메세지페이지</a>
    </div>
    <hr/>
    <pre>
    	서블릿(servlet)에서의 에러발생시에 대처하는 방법?(web.xml 사용처리)
    	- web.xml에 error에 필요한 설정을 미리해두고 지정페이지로 보내준다.
    </pre>
    <div>
    	<a href="${ctp}/0000000" class="btn btn-success mr-2">404오류</a>
    	<a href="${ctp}/errorPage/errorMessage1Get" class="btn btn-success mr-2">405오류</a>
   	</div>
   	<hr/>
   	<div>
	   	<a href="${ctp}/errorPage/error500Check" class="btn btn-success mr-2">500오류</a>
	   	<a href="${ctp}/errorPage/errorNumberFormat" class="btn btn-success mr-2">NumberFormat오류</a>
   	</div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
</body>
</html>