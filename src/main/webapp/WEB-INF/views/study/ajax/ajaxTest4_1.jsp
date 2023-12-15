<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ajaxTest4_1.jsp</title>
    <jsp:include page="/WEB-INF/views/include/bs4.jsp" />
    <script>
    	'use strict'
    	
    	function fCheck1(){
    		let mid = document.getElementById("mid").value;
    		if(mid.trim() == ""){
    			alert("아이디를 입력하세요.")
    			document.getElementById("mid").focus();
    			return false;
    		}
    		$.ajax({
    			url : "${ctp}/study/ajax/ajaxTest4_1",
    			type : "post",
    			data : {mid : mid},
    			success : function(vo){
    				console.log(vo);
    				let str = '';
    				
    				// 값이 없을 시 공백으로 넘어온다.
    				if(vo == ""){
    					str += "<b>찾는 정보가 없습니다.</b>"
    				}
    				else{
	    				str += vo.idx + "<br/>";
	    				str += "아이디 : " + vo.mid + "<br/>";
	    				str += "성명 : " + vo.name + "<br/>";
	    				str += "나이 : " + vo.age + "<br/>";
	    				str += "주소 : " + vo.address + "<br/>";
    				}
    				
    				$("#demo").html(str);
    			},
    			error : function(){
    				alert("전송오류")
    			}
    		});
    	}
    	
    	function fCheck2(){
    		let mid = document.getElementById("mid").value;
    		if(mid.trim() == ""){
    			alert("아이디를 입력하세요.")
    			document.getElementById("mid").focus();
    			return false;
    		}
    		$.ajax({
    			url : "${ctp}/study/ajax/ajaxTest4_2",
    			type : "post",
    			data : {mid : mid},
    			success : function(vos){
    				console.log(vos);
    				let str = '';
    				
    				// 값이 없을 시 공백으로 넘어온다.
    				if(vos == ""){
    					str += "<b>찾는 정보가 없습니다.</b>"
    				}
    				else{
    					str += '<table class="table table-bordered">';
    					str += '<tr class="table-dark text-dark">';
    					str += '<th>아이디</th>';
    					str += '<th>성명</th>';
    					str += '<th>나이</th>';
    					str += '<th>주소</th>';
    					str += '</tr>';
	    				for(let i=0; i<vos.length; i++ ){
	    					str += '<tr>';
	    					str += '<td>'+vos[i].mid+'</td>';
	    					str += '<td>'+vos[i].name+'</td>';
	    					str += '<td>'+vos[i].age+'</td>';
	    					str += '<td>'+vos[i].address+'</td>';
	    					str += '</tr>';
	    				}
    					str += '</table>';
    				}
    				
    				$("#demo").html(str);
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
    <h2>ajaxTest4_1.jsp(Ajax 회원정보검색(vo,vos) 처리)</h2>
    <hr/>
    <form>
    	<div>아이디 :
    		<input type="text" name="mid" id="mid" autofocus class="form-control"/>
    	</div>
    	<div class="mt-3">
	    	<input type="button" value="아아디일치(vo)" onclick="fCheck1()" class="btn btn-info mr-3"/>
	    	<input type="button" value="아이디부분일치(vos)" onclick="fCheck2()" class="btn btn-success mr-3"/>
	    	<input type="button" value="다시 입력" onclick="location.reload()" class="btn btn-primary mr-3"/>
	    	<input type="button" value="돌아가기" onclick="location.href='ajaxForm'" class="btn btn-warning mr-3"/>
    	</div>
    </form>
    <hr/>
    <div id="demo"></div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
</body>
</html>