<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<%@include file="../temp/header.jsp" %>

<script src ="${rPath}/js/ajaxDemo.js"></script>

<!-- [바디 시작] -->
<article>
	<header>
		<h1 style="text-align: center;">EventForm:only for Admin</h1>
	</header>
	<ul class="list-unstyled">
		<li class="border-top my-3"></li>
	</ul>
	<div class="container">
		<%-- boardForm 들어 갈 자리. --%>
	
		<form action="evInsert" method="post" enctype="multipart/form-data" id="eventform">
			<input type="hidden" name="evreip" value="<%=request.getRemoteAddr()%>">
			
			<div class="form-group">
				<label for="evTitle">제목</label>
				<input type="text" class="form-control" id="evTitle"
					placeholder="제목 입력(5-100)" name="evTitle" maxlength="100"
					required="required" pattern=".{2,50}">
			</div>
			<div class="form-group">
				<label for="evContent">내용</label>
				<textarea class="form-control" rows="5" id="evContent" name="evContent"
					placeholder="내용 작성" required="required"></textarea>
			</div>
			<div class="form-group"> 
				<label for="evWriter">작성자</label> 
				<input type="text"
					class="form-control" id="evWriter" value="Admin"
					name="evWriter" pattern=".{1,10}" readonly="readonly">
			</div>
			<div class="form-group">
				<label for="reward">상품</label> 
				<input type="text"
					class="form-control" id="reward"
					name="reward" pattern=".{1,10}">
			</div>
			<!-- 이벤트 시작일 -->
			<div class="form-group">
				<label for="evSdate">이벤트 시작일</label> 
				<input type="date"
					class="form-control" id="evSdate"
					name="evSdate" pattern=".{1,10}"required="required">
			</div>
			<!-- 이미지 -->
			<div class="form-group">
				<input type="hidden" name="evImg" id="evImg">
				<label for="evfile">이미지</label>
				<div class="col-sm-10"><input type="file" class="form-control" id="evfile"
					name="evfile">
			    </div>		
			    <div class="col-sm-10">
			    <img src="${rPath}/img/event_form/noimage.jpg" id="imgev"
			    style="width: 210px; border: dotted 1px; margin: 5px 5px">
			    </div>
			</div>
			<!-- 비디오추가 -->
			<div class="form-group">
				<input type="hidden" name="evVeo" id="evVeo">
				<label for="evfile2">동영상</label>
				<div class="col-sm-10"><input type="file" class="form-control" id="evfile2"
					name="evfile2">
			    </div>		
			    <div class="col-sm-10">
			    <img src="${rPath}/img/event_form/noimage.jpg" id="imgev2"
			    style="width: 210px; border: dotted 1px; margin: 5px 5px">
			    </div>
			</div>
			<div class="form-group" style="text-align: right; margin-top: 10px;">
				<button type="submit" class="btn btn-primary">등록</button>
				 <input type="button" value="리스트"  class="btn btn-danger"
				 onclick="location='eventMain'"/>
			</div>
		</form>
	</div>
</article>

<script>
// 이미지 미리보기 함수 => javascript I/O 기능을 활용
function readURL(input, targetID){
   if(input.files && input.files[0]){
	   var reader = new FileReader();
	   reader.onload = function(e){
		   $(targetID).attr('src', e.target.result);
	   }
	   reader.readAsDataURL(input.files[0]);
   }
}

// jQuery 문법 - 축약형 
$(function () {
	// 첫 번째 이미지 미리보기
	$('#evfile').change(function() {
		if($(this).val().length > 0){
			readURL(this, '#imgev');
		}else{
			console.log("이미지 없음");
		}
	});
	
	// 두 번째 이미지 미리보기
	$('#evfile2').change(function() {
		if($(this).val().length > 0){
			readURL(this, '#imgev2');
		}else{
			console.log("이미지 없음2");
		}
	});
});

	
</script>


    <%@include file="../temp/footer.jsp" %>