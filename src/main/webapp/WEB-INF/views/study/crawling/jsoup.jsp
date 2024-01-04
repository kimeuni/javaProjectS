<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>jsoup.jsp</title>
    <jsp:include page="/WEB-INF/views/include/bs4.jsp" />
    <script>
    	'use strict'
    	function crawling(){
    		let url =$("#url").val();
    		let selector =$("#selector").val();
    		
    		if(url.trim() == ""){
    			alert("웹크롤링할 주소를 입력해주세요.");
    			$("#url").focus();
    			return false;
    		}
    		
    		$.ajax({
    			type : "post",
    			url : "${ctp}/study/crawling/jsoup",
    			data : {
    				url : url,
    				selector : selector
   				},
    			success : function(vos){
    				$("#demo").show();
    				if(vos != ""){
    					let str = '';
    					for(let i=0; i<vos.length; i++){
    						str += vos[i] + '<br/>';
    					}
    					$("#demo").html(str);
    				} 
    				else{
	    				$("#demo").show();
    					$("#demo").html("검색된 자료가 없습니다.");
    				}
    			},
    			error : function(){
    				
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
    <h2>jsoup를 이용한 웹 크롤링</h2>
    <hr/>
    <form name="myform">
    	<div class="input-group mb-3">
    		<div class="input-group-prepend"><span class="input-group-text">클롤링할 웹 주소</span></div>
    		<!-- <input type="text" name="url" id="url" value="https://news.naver.com/" class="form-control" /> -->
    		<select name="url" id="url" class="form-control">
    			<option>URL을 선택하세요.</option>
    			<option value="https://news.naver.com/">1(네이버뉴스).https://news.naver.com</option>
    			<option value="https://news.naver.com/">2(네이버뉴스).https://news.naver.com</option>
    			<option value="https://movie.daum.net/ranking/reservation">3(다음 영화순위).https://movie.daum.net/ranking/reservation</option>
    			<option value="https://movie.daum.net/ranking/reservation">3(다음 영화순위).https://movie.daum.net/ranking/reservation</option>
    		</select>
    		<div class="input-group-append"><input type="button" value="새로고침" onclick="location.reload()" class="btn btn-success"/></div>
    	</div>
    	<div class="input-group mb-3">
    		<div class="input-group-prepend"><span class="input-group-text">검색할 셀렉터 내용</span></div>
    		<select name="selector" id="selector" class="form-control">
    			<option>셀렉터를 선택하세요.</option>
    			<option value="div.cjs_t">div.cjs_t</option>
    			<option value="div.cjs_news_mv">div.cjs_news_mv</option>
    			<option value="div.item_poster">div.item_poster</option>
    			<option value="div.thumb_cont">div.item_poster</option>
    		</select>
    		<div class="input-group-append"><input type="button" value="크롤링" onclick="crawling()" class="btn btn-success"/></div>
    	</div>
    </form>
    <hr/>
    <div id="demo" style="width: 100%; height: 600px; overflow: auto; border: 1px solid; display: none"></div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
</body>
</html>