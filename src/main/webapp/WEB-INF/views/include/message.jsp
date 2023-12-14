<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>message.jsp</title>
    <jsp:include page="/WEB-INF/views/include/bs4.jsp" />
    <script>
    	'use strict';
    	
    	//문자로 넘어올것이기 때문에 따옴표 넣어야함!
    	let msg = '${msg}';
    	let url = '${ctp}/${url}';
    	
    	alert(msg);
    	location.href=url;
    </script>
</head>
<body>
<p><br/></p>
<div class="container">
    
</div>
<p><br/></p>
</body>
</html>