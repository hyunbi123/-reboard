<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>글 수정하기</title>
  <!-- Bootstrap CSS 연결 -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>

<div class="container mt-5">
  <div class="card">
    <div class="card-header">
      <h4>글 수정하기</h4>
    </div>
    <form action="/board/update" method="post">
    <div class="card-body">
      <!-- 글 번호 (id) -->
      <div class="mb-3">
        <label for="id" class="form-label">글 번호</label>
        <input type="text" class="form-control" id="id" name="id" value="${view.id}" readonly>
      </div>

      <!-- 작성자 (author) -->
      <div class="mb-3">
        <label for="author" class="form-label">작성자</label>
        <input type="text" class="form-control" id="author" name="author" value="${view.author}">
      </div>

      <!-- 제목 (title) -->
      <div class="mb-3">
        <label for="title" class="form-label">제목</label>
        <input type="text" class="form-control" id="title" name="title" value="${view.title}">
      </div>

      <!-- 내용 (content) -->
      <div class="mb-3">
        <label for="content" class="form-label">내용</label>
        <textarea class="form-control" id="content" name="content" rows="5">${view.content}</textarea>
      </div>

      <!-- 첨부파일 (attachment) -->
      <div class="mb-3">
        <label for="attachment" class="form-label">첨부파일</label>
        <input type="file" class="form-control" id="attachment" name="attachment" value="${view.attachment}">
      </div>

      <!-- 버튼들 -->
      <div class="d-flex justify-content-between">
        <input type="submit" class="btn btn-success" value="완료">
        <input type="button" class="btn btn-secondary"
          onclick="location.href='/board/list'" value="취소">
      </div>
    </div>
    </form>
  </div>
</div>

<!-- Bootstrap JS 및 종속성 연결 -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
