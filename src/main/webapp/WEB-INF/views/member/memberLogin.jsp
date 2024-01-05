<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>memberLogin.jsp</title>
	<jsp:include page="/WEB-INF/views/include/bs4.jsp" />
	<style>
		
	</style>
	<script>
	$(function() {
    	$("#searchMid").hide();
    	$("#searchPassword").hide();
    	$("#spinner").hide();
    	$("#codeOk").hide();
    });
    
    // 아이디 찾기 폼 보기
    function midSearch() {
    	$("#searchPassword").hide();
    	$("#searchMid").show();
    }
    
    // 인증 번호 이메일 전송
    function emailFind() {
    	let email = $("#emailSearch").val().trim();
    	if(email == "") {
    		alert("가입시 등록한 email을 입력하세요");
    		$("#emailSearch").focus();
    		return false;
    	}
    	
    	$("#spinner").show();
    	
    	$.ajax({
    		url  : "${ctp}/member/memberEmailCode",
    		type : "post",
    		data : {email : email},
    		success : function(res) {
    			if(res != "0"){
    				alert("인증번호가 발송되었습니다.");
    				$("#spinner").hide();
			    	$("#codeOk").show();
    			}
    			else {
    				alert("이메일을 다시 확인하세요.");
    				$("#spinner").hide();
    			}
    		},
    		error : function() {
    			alert("전송 오류!");
    		}
    	});
    }
    
    // 이메일로 아이디 검색처리
    function MailCodeOk() {
    	let email = $("#emailSearch").val().trim();
    	let code = $("#code").val().trim();
    	if(code == "") {
    		alert("인증번호를 입력해주세요.");
    		$("#code").focus();
    		return false;
    	}
    	
    	let query = {
    		email : email,
    		code : code
    	}
    	
    	$.ajax({
    		url  : "${ctp}/member/memberEmailCodeOk",
    		type : "post",
    		data : query,
    		success:function(res) {
    			if(res == "1") alert("인증번호가 만료되었습니다.");
    			else if (res == "2") alert("작성하신 인증번호가 일치하지 않습니다");
    			else {
	    			let temp = res.split("/");
	    			console.log("temp :", temp);
	    			let str = '검색결과 : <br/><font color=blue><b>';
	    			for(let i=0; i<temp.length; i++) {
	    				let jump = Math.floor((Math.random()*(4-2)) + 2);
	    				let tempMid = temp[i].substring(0,1);
	    				console.log("tempMid",tempMid,", jump",jump);
	    				for(let j=1; j<temp[i].length; j++) {
	    					if(j % jump == 0) tempMid += "*";
	    					else tempMid += temp[i].substring(j,j+1);
	    				}
		    			str += tempMid;
		    			
		    			str += "<br/>";
	    			}
	    			str += '</b></font>';
	    			$("#codeOk").hide();
	    			midShow.innerHTML = str;
    			}
    		},
    		error : function() {
    			alert("전송 오류!");
    		}
    	});
    }
    
    // 비밀번호 검색폼 보여주기
    function pwdSearch() {
    	$("#searchMid").hide();
    	$("#searchPassword").show();
    }
    
    // 비밀번호 검색처리
    function passwordFind() {
    	let mid = $("#midSearch").val().trim();
    	let email = $("#emailSearch2").val().trim();
    	if(mid == "" || email == "") {
    		alert("가입시 등록한 아이디와 메일주소를 입력하세요");
    		$("#midSearch").focus();
    		return false;
    	}
    	let query = {
   			mid   : mid,
   			email : email
    	}
    	
    	$.ajax({
    		url  : "${ctp}/member/memberPasswordSearch",
    		type : "post",
    		data : query,
    		success:function(res) {
    			if(res == "1"){
    				alert("새로운 비밀번호가 회원님 메일로 발송 되었습니다.")
    			}
    			else {
    				alert("등록하신 정보가 잘못되었습니다. 확인후 다시 전송하세요.")
    			}
    		},
    		error : function() {
    			alert("전송오류!");
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
	<h2 style="text-align:center;">로 그 인</h2>
	<p><br/></p>
	<div class="container">
		<form name="loginForm" method="post">
			<table class="table table-bordered " style="width:800px; margin:0 auto;">
				<tr>
					<th colspan="2" class="text-center">회원 로그인</th>
				</tr>
				<tr>
					<th>아이디</th>
					<td>
						<input type="text" name="mid" id="mid" value="${cMid}" autofocus required class="form-control" />
						<!-- id저장을 해서 쿠키에 값이 있으면..ID저장 체크를 하고, 아니면 ID저장에 체크를 하지 않도록 한다. -->
						<c:if test="${cMid != null}"><input type="checkbox" name="idCheck" value="save" checked>ID저장</c:if>
						<c:if test="${cMid == null}"><input type="checkbox" name="idCheck" value="save">ID저장</c:if>
					</td>
				</tr>
				<tr>
					<th>비밀번호</th>
					<td><input type="password" name="pwd" id="pwd" value="f7fb2b39" required class="form-control" /></td>
				</tr>
				<tr>
					<td colspan="2" class="text-center">
						<input type="submit" value="로그인" class="btn btn-success mr-3"/>
						<input type="reset" value="다시입력" class="btn btn-warning "/>
						<input type="button" value="회원가입" onclick="location.href='memberJoin';" class="btn btn-info "/>
					</td>
				</tr>
			</table>
			<table style="width:1050px; margin: 10px">
				<tr>
					<td colspan="2" class="text-center">
						[<a href="javascript:midSearch()">아이디찾기</a>] /
  	      				[<a href="javascript:pwdSearch()">비밀번호찾기</a>]
					</td>
				</tr>
			</table>
		</form>
		</form>
		<form name="searchForm">
		  <div id="searchMid">
		    <hr/>
		  	<table class="table table-borderless p-0 text-center">
		  	  <tr>
		  	    <td class="text-center">
		  	      <font size="4"><b>아이디 찾기</b></font>
		  	      (가입시 입력한 메일주소를 입력하세요)
		  	    </td>
		  	  </tr>
		  	  <tr>
		  	    <td>
		  	      <div class="input-group">
		  	        <input type="text" name="eamilSearch" id="emailSearch" class="form-control" placeholder="이메일 입력"/>
		  	        <div class="input-group-append">
		  	          <input type="button" value="이메일검색" onclick="emailFind()" class="btn btn-info" />
		  	        </div>
		  	      </div>
		  	    </td>
		  	  </tr>
		  	  <tr>
		  	    <td><div id="midShow"></div></td>
		  	  </tr>
		  	</table>
	  	</div>
	  	<div id="searchPassword">
		  	<hr/>
		  	<table class="table table-bordered p-0 text-center">
		  	  <tr>
		  	    <td class="text-center" colspan="2">
		  	      <font size="4"><b>비밀번호 찾기</b></font>
		  	      (가입시 입력한 아이디와 메일주소를 입력하세요)
		  	    </td>
		  	  </tr>
		  	  <tr>
		  	    <th>아이디</th>
		  	    <td><input type="text" name="midSearch" id="midSearch" class="form-control" placeholder="아이디를 입력하세요"/></td>
		  	  </tr>
		  	  <tr>
		  	    <th>이메일</th>
		  	    <td><input type="text" name="emailSearch2" id="emailSearch2" class="form-control" placeholder="메일주소를 입력하세요"/></td>
		  	  </tr>
		  	  <tr>
		  	    <td colspan="2">
		  	      <input type="button" value="새비밀번호발급" onclick="passwordFind()" class="btn btn-info" />
		  	    </td>
		  	  </tr>
		  	</table>
		  	<table class="table table-borderless">
		  	  <tr>
		  	    <td><div id="passwordShow"></div></td>
		  	  </tr>
		  	</table>
	  	</div>
		</form>
		<div id="spinner" class="text-center">
			<div class="spinner-border " ></div>
		</div>
		<div class="row" id="codeOk">
			<div class="col-2">인증번호 :</div>
			<div class="col-9">
				<div class="input-group-append">
					<input type="text" name="code" id="code" value="${sMailCode}" class="form-control"  />
					<div class="input-group-append">
						<input type="button" value="인증하기" onclick="MailCodeOk()" class="btn btn-success"/>
					</div>
				</div>
			</div>
		</div> 
	</div>
	<p><br/></p>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
</body>
</html>