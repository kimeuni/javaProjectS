<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>transaction.jsp</title>
    <jsp:include page="/WEB-INF/views/include/bs4.jsp" />
    <script>
    	'use strict'
    	
    	function userDelete(idx){
    		let ans = confirm("선택된 user를 삭제하시겠습니까?")
    		if(!ans) return false;
    		else location.href="user2Delete?idx="+idx;
    	}
    	
    	function nameSearch(){
    		let name = document.getElementById("name").value;
    		location.href="user2Search?name="+name
    	}
    	
    	function fNewForm(){
    		let str = '';
    		str += '<form method="post" id="myform">';
    		str += '<table class="table table-bordered"';
    		str += '<tr >';
    		str += '<td colspan="2" class="table-dark">user테이블에 입력되는 필드</td>';
    		str += '</tr>';
    		str += '<tr>';
    		str += '<th>아이디</th>';
    		str += '<td><input type="text" name="mid" id="mid"  class="form-control"/></td>';
    		str += '</tr>';
    		str += '<tr>';
    		str += '<th>성명</th>';
    		str += '<td><input type="text" name="name" id="name" class="form-control"/></td>';
    		str += '</tr>';
    		str += '<tr>';
    		str += '<th>나이</th>';
    		str += '<td><input type="number" name="age" id="age" class="form-control"/></td>';
    		str += '</tr>';
    		str += '<tr>';
    		str += '<th>주소</th>';
    		str += '<td><input type="text" name="address" id="address" class="form-control"/></td>';
    		str += '</tr>';
    		str += '<tr>';
    		str += '<td colspan="2" class="table-dark">user2테이블에 추가되는 필드</td>';
    		str += '</tr>';
    		str += '<tr>';
    		str += '<th>직업</th>';
    		str += '<td><input type="text" name="jab" id="jab" class="form-control"/></td>';
    		str += '</tr>';
    		str += '<tr>';
    		str += '<td colspan="2" class="text-center">';
    		str += '<input type="submit" value="회원가입1" class="btn btn-info form-control"/>';
    		str += '<input type="button" value="회원가입2" onclick="transaction2()" class="btn btn-info form-control"/>';
    		str += '</td>';
    		str += '</tr>';
    		str += '</table>';
    		str += '</form>';
    		
    		document.getElementById("demo").innerHTML = str;
    		
    	}
    	
    	function transaction2(){
    		let mid = $("#mid").val();
    		let name = $("#name").val();
    		let age = $("#age").val();
    		let address = $("#address").val();
    		let jab = $("#jab").val();
    		
    		alert("age : " + age);
    		
    		let query = {
    			mid : mid,
    			name : name,
    			age : age,
    			address : address,
    			jab : jab
    		}
    		
    		$.ajax({
    			url : "${ctp}/study/transaction/transaction2",
    			type : "get",
    			data : query,
    			success : function(res){
    				alert("등록Ok")
    				location.reload();
    			},
    			error : function(){
    				alert("전송오류")
    			}
    		});
    	}
    	
    	
    	function user2Update(idx,mid,name,age,address){
    		$("#myModal .modal-body #idx").val(idx);
    		$("#myModal .modal-body #mid").val(mid);
    		$("#myModal .modal-body #name").val(name);
    		$("#myModal .modal-body #age").val(age);
    		$("#myModal .modal-body #address").val(address);
    	}
    </script>
</head>
<body>
<!-- 메뉴 -->
<jsp:include page="/WEB-INF/views/include/nav.jsp"></jsp:include>
<!-- Page content -->
<div class="w3-content" style="max-width:2000px;margin-top:46px">
<!-- slide show -->
<jsp:include page="/WEB-INF/views/include/slide2.jsp"></jsp:include>
<p><br/></p>
<div class="container">
    <h2>회 원 리 스 트2</h2>
    <br/>
    <div class="row mb-2">
    	<div class="col-7">
    		<input type="button" onclick="fNewForm()" value="회원가입폼" class="btn btn-primary btn-sm"/>
    	</div>
    	<div class="col-5 text-right"> 
    		<div class="input-group">
	    	<input type="text" name="name" id="names" value="${name}" class="form-contrl"/>
	    	<div class="input-group-append"><input type="button" value="검색" onclick="nameSearch()" class="btn btn-success"/></div>
    		</div>
    	</div>
    </div>
    <div id="demo"></div>
    <table class="table table-hover">
    	<tr class="table-dark text-dark">
    		<th>번호</th>
    		<th>아이디</th>
    		<th>성명</th>
    		<th>나이</th>
    		<th>주소</th>
    		<th>비고</th>
    	</tr>
    	<c:forEach var="vo" items="${vos}" varStatus="st">
    		<tr>
    			<td>${vo.idx }</td>
    			<td>${vo.mid }</td>
    			<td>${vo.name }</td>
    			<td>${vo.age }</td>
    			<td>${vo.address }</td>
    			<td>
	    			<a href="#" onclick="user2Update('${vo.idx}','${vo.mid}','${vo.name}','${vo.age}','${vo.address}')" class="btn btn-warning btn-sm" data-toggle="modal" data-target="#myModal">수정</a>
	    			<a href="javascript:userDelete(${vo.idx})" class="btn btn-danger btn-sm">삭제</a>
    			</td>
    		</tr>
    	</c:forEach>
    	<tr><td colspan="6" class="m-0 p-0"></td></tr>
    </table>
    <hr/><h3>User2 리스트</h3><br/>
    <table class="table table-hover">
    	<tr class="table-dark text-dark">
    		<th>번호</th>
    		<th>아이디</th>
    		<th>직업</th>
    	</tr>
    	<c:forEach var="vo" items="${vos2}" varStatus="st">
    		<tr>
    			<td>${st.count}</td>
    			<td>${vo.mid }</td>
    			<td>${vo.jab }</td>
    		</tr>
    	</c:forEach>
    	<tr><td colspan="3" class="m-0 p-0"></td></tr>
    </table>
    <div><a href="${ctp}/" class="btn btn-warning">돌아가기</a></div>
</div>

<!-- The Modal -->
  <div class="modal fade" id="myModal">
    <div class="modal-dialog">
      <div class="modal-content">
      
        <!-- Modal Header -->
        <div class="modal-header">
          <h4 class="modal-title">회원 정보 수정</h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
        
        <!-- Modal body -->
        <div class="modal-body">
        	<form method="post" action="${ctp}/user2/user2UpdateOk">
        		<div>아이디 : <input type="text" name="mid" id="midM" readonly class="form-control mb-2"/></div>
        		<div>성명 : <input type="text" name="name" id="nameM" required class="form-control mb-2"/></div>
        		<div>나이 : <input type="number" name="age" id="ageM" class="form-control mb-2"/></div>
        		<div>주소 : <input type="text" name="address" id="addressM" class="form-control mb-2"/></div>
        		<div><input type="submit" value="정보수정" class="form-control""/></div>
        		<div><input type="hidden" name="idx" id="idxM"/></div>
        	</form>
        </div>
        
        <!-- Modal footer -->
        <div class="modal-footer">
          <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
        </div>
        
      </div>
    </div>
  </div>

<p><br/></p>
<!-- footer -->
<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
</body>
</html>