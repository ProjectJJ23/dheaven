<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../temp/header.jsp"%>

<script src="${rPath}/js/ajaxDemo.js"></script>


<!-- [바디 시작] -->
<article>
	<header>
		<h1 style="text-align: center;">evModifyForm :only for Admin</h1>
	</header>
	<ul class="list-unstyled">
		<li class="border-top my-3"></li>
	</ul>
	<div class="container">
		<%-- boardForm 들어 갈 자리. --%>

		<form action="evModify" method="post" enctype="multipart/form-data"
			id="eventModForm">
			<input type="hidden" name="evreip"
				value="<%=request.getRemoteAddr()%>"> 
				<input type="hidden"
				name="evNum" id="evNum" value="${vo.evNum}">

			<div class="mb-3 mt-3">
				<label class="form-label">이벤트 번호 : </label>${vo.evNum}
			</div>
			<div class="form-group">
				<label for="evTitle">제목</label> <input type="text"
					class="form-control" id="evTitle" name="evTitle" maxlength="100"
					required="required" pattern=".{2,50}" value="${vo.evTitle}">
			</div>
			<div class="form-group">
				<label for="evContent">내용</label>
				<textarea class="form-control" rows="5" id="evContent"
					name="evContent" required="required">${vo.evContent}</textarea>
			</div>
			<div class="form-group">
				<label for="evWriter">작성자</label> <input type="text"
					class="form-control" id="evWriter" value="Admin" name="evWriter"
					pattern=".{1,10}" readonly="readonly">
			</div>
			<div class="form-group">
				<label for="reward">상품</label> <input type="text"
					class="form-control" id="reward" name="reward" pattern=".{1,10}"
				  value="${vo.reward}">
			</div>
			<!-- 이벤트 시작일 -->
			<div class="form-group">
				<label for="evSdate">기존 이벤트 시작일 : ${vo.evSdate} </label> <br> 새
				이벤트 시작일 : <input type="date" class="form-control" id="evSdate"
					name="evSdate"
					required="required" value="${vo.evSdate}">
				
			</div>
			<!-- 기존의 이미지 -->
			<c:choose>
				<c:when test="${vo.evImg == null}">
					<!-- 이미지가 없을 경우 -->
					<div class="mb-3">
						<label class="form-label"></label> <img
							src="${rPath}/img/event_form/basic.PNG"
							style="width: 80px; border: dotted 1px; cursor: pointer;"
							name="evImg" id="evImg">
					</div>
				</c:when>

				<c:when test="${vo.evImg != null}">
					<!-- 이미지가 있을 경우 -->
					<div class="mb-3">
						<label class="form-label"></label> <img
							src="${rPath}/img/eventImg/${vo.evImg}"
							style="width: 80px; border: dotted 1px; cursor: pointer;"
							name="evImg" id="evImg">
					</div>
				</c:when>
			</c:choose>

			<%--이미지 수정 폼  --%>
		<c:choose> <%--기존에 업로드된 이미지가 없다면 --%>
			<c:when test="${vo.evImg == null}">
					<div class="form-group">
				<input type="hidden" name="evImg" id="evImg"> <label
					for="evfile">이미지</label>
				<div class="col-sm-10">
					<input type="file" class="form-control" id="evfile" name="evfile">
				</div>
				<div class="col-sm-10">
					<img src="${rPath}/img/event_form/noimage.jpg" id="imgev"
						style="width: 210px; border: dotted 1px; margin: 5px 5px">
				</div>
			</div>
			</c:when>
			<c:otherwise> <%--기존에 업로드된 이미지가 있다면--%>
					<div class="form-group">
				<input type="hidden" name="evImg" id="evImg"> <label
					for="evfile">이미지</label>
				<div class="col-sm-10">
					<input type="file" class="form-control" id="evfile" name="evfile">
				</div>
				<div class="col-sm-10">
					<img src="${rPath}/img/eventImg/${vo.evImg}" id="imgev"
						style="width: 210px; border: dotted 1px; margin: 5px 5px">
				</div>
			</div>
			</c:otherwise>
		</c:choose>
			

			<!-- 기존의 영상 -->
			<c:choose>
				<c:when test="${vo.evVeo == null}">
					<!-- 이미지가 없을 경우 -->
					<div class="mb-3">
						<label class="form-label"></label> <img
							src="${rPath}/img/event_form/basic.PNG"
							style="width: 80px; border: dotted 1px; cursor: pointer;"
							name="evVeo" id="evVeo">
					</div>
				</c:when>

				<c:when test="${vo.evVeo != null}">
					<!-- 이미지가 있을 경우 -->
					<div class="mb-3">
						<label class="form-label"></label> <img
							src="${rPath}/img/eventImg/${vo.evVeo}"
							style="width: 80px; border: dotted 1px; cursor: pointer;"
							name="evVeo" id="evVeo">
					</div>
				</c:when>
			</c:choose>



			<%--비디오 수정 폼  --%>
				<c:choose> <%--기존에 업로드된 영상이 없다면 --%>
			<c:when test="${vo.evImg == null}">
					<div class="form-group">
				<input type="hidden" name="evVeo" id="evVeo"> <label
					for="evfile2">동영상</label>
				<div class="col-sm-10">
					<input type="file" class="form-control" id="evfile2" name="evfile2">
				</div>
				<div class="col-sm-10">
					<img src="${rPath}/img/event_form/noimage.jpg" id="imgev2"
						style="width: 210px; border: dotted 1px; margin: 5px 5px">
				</div>
			</div>
			
			</c:when>
			
			<c:otherwise> <%--기존에 업로드된 영상이 있다면--%>
						<div class="form-group">
				<input type="hidden" name="evVeo" id="evVeo"> <label
					for="evfile2">동영상</label>
				<div class="col-sm-10">
					<input type="file" class="form-control" id="evfile2" name="evfile2">
				</div>
				<div class="col-sm-10">
					<img src="${rPath}/img/eventImg/${vo.evVeo}" id="imgev2"
						style="width: 210px; border: dotted 1px; margin: 5px 5px">
				</div>
			</div>
			</c:otherwise>
		</c:choose>
			
			<div class="form-group" style="text-align: right; margin-top: 10px;">
				<button type="submit" class="btn btn-primary">등록</button>
				<input type="button" value="리스트" class="btn btn-danger"
					onclick="location='eventMain'" />
			</div>


		</form>
	</div>
</article>

<script>
//이미지 미리보기 함수 => javascript I/O 기능을 활용
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


<%@include file="../temp/footer.jsp"%>