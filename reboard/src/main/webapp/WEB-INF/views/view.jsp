<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>상세보기</title>
  <!-- Bootstrap CSS 연결 -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>

<div class="container mt-5">
  <div class="card">
    <div class="card-header">
      <h4>글 상세보기</h4>
    </div>
    <div class="card-body">
      <!-- 글 번호 (id) -->
      <div class="mb-3">
        <label for="id" class="form-label">글 번호</label>
        <input type="text" class="form-control" id="id" name="id" value="${view.id}" readonly>
      </div>

      <!-- 작성자 (author) -->
      <div class="mb-3">
        <label for="author" class="form-label">작성자</label>
        <input type="text" class="form-control" id="author" name="author" value="${view.author}" readonly>
      </div>

      <!-- 제목 (title) -->
      <div class="mb-3">
        <label for="title" class="form-label">제목</label>
        <input type="text" class="form-control" id="title" name="title" value="${view.title}" readonly>
      </div>

      <!-- 내용 (content) -->
      <div class="mb-3">
        <label for="content" class="form-label">내용</label>
        <textarea class="form-control" id="content" name="content" rows="5" readonly>${view.content}</textarea>
      </div>

      <!-- 첨부파일 (attachment) -->
      <div class="mb-3">
        <label for="attachment" class="form-label">첨부파일</label>
        <input type="file" class="form-control" id="attachment" name="attachment" value="${view.attachment}">
      </div>

      <!-- 버튼들 -->
      <div class="d-flex justify-content-between">
        <button type="button" class="btn btn-primary" id="edit_button" name="edit_button" onclick="location.href='/board/updateform?id=${view.id}'">수정</button>
        <button type="button" class="btn btn-danger" id="delete_button" name="delete_button" 
        onclick="deleteConfirm()">삭제</button>
        <input type="button" class="btn btn-info" 
        id="comment_button" name="comment_button" 
        onclick="location.href='/board/reply?parentid=${view.id}'"
        value="댓글쓰기">
        <button type="button" class="btn btn-secondary" id="cancel_button" name="cancel_button" onclick="location.href='/board/list'">취소</button>
      </div>
    </div>
  </div>
</div>
<script>
function deleteConfirm(){
	//let answer=prompt("정말 삭제하시겠습니까?");
	let answer=confirm("정말 삭제하시겠습니까?");
	//alert(answer);
	if(answer){
	location.href='/board/delete?id=${view.id}'
	}else{
		alert("삭제를 취소하셨습니다.");
		location.href="/board/list";
	}
}
</script>
<!-- Bootstrap JS 및 종속성 연결 -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
