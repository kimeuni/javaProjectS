<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>error404.jsp</title>
    <jsp:include page="/WEB-INF/views/include/bs4.jsp" />
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp"></jsp:include>
<div class="w3-content" style="max-width:2000px;margin-top:46px">
<jsp:include page="/WEB-INF/views/include/slide2.jsp"></jsp:include>
<p><br/></p>
<div class="container">
    <h2>현재 시스템 정비중입니다.(404에러 발생시 보이는 화면)</h2>
    <div>(사용에 불편을 드려 죄송합니다.)</div>
    <div>빠른시일내에 복구되도록 하겠습니다.</div>
    <hr/>
    <div><img src="${ctp}/images/chicago.jpg" width="300px"></div>
    <div>
    	<a href="${cpt}/errorPage/errorMain" class="btn btn-success">돌아가기</a>
    </div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
</body>
</html>