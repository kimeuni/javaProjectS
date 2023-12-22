<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>fileUpload.jsp</title>
    <jsp:include page="/WEB-INF/views/include/bs4.jsp" />
    <script>
    	'use strict'
    	
    	//파일 업로드 체크
    	function fCheck(){
    		let fName = document.getElementById("fName").value;
    		let ext = fName.substring(fName.lastIndexOf(".")+1).toLowerCase();
    		let maxSize = 1024 * 1024 * 20; // 업로드 가능파일 최대용량은 20MByte
    		
    		if(fName.trim() == ""){
    			alert("업로드할 파일을 선택하세요.");
    		}
    		
    		let fileSize = document.getElementById("fName").files[0].size; // 파일의 사이즈 체크
    		if(ext != "jpg" && ext != "gif" && ext != "png" && ext != "zip" && ext != "ppt" && ext != "pptx" && ext != "hwp"){
    			alert("업로드 가능한 파일은 'jpg/gif/png/zip/ppt/pptx/hwp' 파일 입니다. ");
    		}
    		else if(fileSize > maxSize){
    			alert("업로드할 파일의 최대용량은 20MBtye 입니다.");
    		}
    		else {
    			//alert("전송완료");
    			myform.submit();
    		}
    	}
    	
    	// 서버의 파일 삭제 처리
    	function fileDelete(file){
    		let ans = confirm("선택한 파일을 삭제하시겠습니까?")
    		if(!ans) return false;
    		
    		$.ajax({
    			url : "${ctp}/study/fileUpload/fileDelete",
    			type : "post",
    			data : {file : file},
    			success : function(res){
    				alert("res" + res);
    				if(res == "1"){
    					alert("파일이 삭제되었습니다.");
    					location.reload();
    				}
    				else alert("파일 삭제 실패~~");
    			},
    			error : function(){
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
    <form name="myform" method="post" enctype="multipart/form-data">
    	<p>올린이 :
    		<input type="text" name="mid" value="${sMid}"/>
    	</p>
    	<p>파일명 :
    	<!-- accept는 받고 싶은 파일들의 확장자명을 적는다. -->
    		<input type="file" name="fName" id="fName" class="form-control-file border" accept=".jpg,.gif,.png,.zip,.ppt,.pptx,.hwp" />
    	</p>
		<p>
			<input type="button" value="파일업로드" onclick="fCheck()" class="btn btn-success"/>
			<input type="reset" value="다시선택" onclick="fCheck()" class="btn btn-warning"/>
		</p>    	
    </form>
    
    <hr/>
    
    <!-- ckeditor에서 파일을 업로드 할 때, data-> ckeditor에는 임시 저장된 파일이 엄청 많이 있을 것이다. 이것을 이런식으로 화면에 보이도록해서 삭제처리 할 수 있도록 한다.(관리자가 볼 때) -->
    <div id="downLoadFile">
    	<h3>서버에 저장된 파일정보(총 : ${fileCount} 건)</h3>
    	<p>저장경로 : ${ctp}/resources/data/study/*.*</p>
    	<table class="table table-hover text-center">
    		<tr>
    			<th>번호</th>
    			<th>파일명</th>
    			<th>파일형식</th>
    			<th>비고</th>
    		</tr>
   			<c:forEach var="file" items="${files}" varStatus="st">
    			<tr>
    				<td>${st.count}</td>
    				<td><a href="${ctp}/study/${file}" download>${file}</a></td>
    				<td>
    					<!-- 파일 이름이 배열로 들어옴 -->
    					<c:set var="fNameArray" value="${fn:split(file,'.')}" />
    					<c:set var="extName" value="${fn:toLowerCase(fNameArray[fn:length(fNameArray)-1]) }" />
    					<c:if test="${extName == 'zip' }">압축파일</c:if>
    					<c:if test="${extName == 'ppt' || extName == 'pptx'}">파워포인트파일</c:if>
    					<c:if test="${extName == 'hwp' }">한글파일</c:if>
    					<c:if test="${extName == 'jpg' || extName == 'gif' || extName == 'png' }">
    						<img src="${ctp}/study/${file}" width="150px">
    					</c:if>
    				</td>
    				<td>
    					<input type="button" value="다운로드" onclick="location.href='${ctp}/study/fileUpload/fileDownAction?file=${file}'" class="btn btn-success btn-sm"/>
    					<input type="button" value="삭제" onclick="fileDelete('${file}')" class="btn btn-danger btn-sm"/>
    				</td>
  	  			</tr>
   			</c:forEach>
   			<tr><td colspan="4" class="m-0 p-0"></td></tr>
    	</table>
    </div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
</body>
</html>