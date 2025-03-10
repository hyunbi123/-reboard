<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시글 목록</title>
    <!-- Bootstrap CSS 링크 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body onload="pageLoaded()">
    <div class="container mt-5">
        <h1>게시글 목록</h1>
        <table class="table table-bordered table-striped">
            <thead class="table-light">
                <tr>
                    <th>글번호</th>
                    <th>제목</th>
                    <th>작성자</th>
                    <th>작성일</th>
                    <th>조회수</th>
                </tr>
            </thead>
            <tbody>
            <c:set var="num" value="${boardpage.startnum}"/>
            <c:forEach var="writing" items="${boardpage.list}">
                <tr onclick="location.href='/board/view?id=${writing.id}'">
                    <td><c:out value="${num}"/></td>
                    <td>
                    <!-- re 이미지 표시 -->
                    <c:forEach begin="1" end="${writing.tab}">
                    <img src="/image/reply_icon.gif" style="width:42px;height:15px">
                    </c:forEach>
                    ${writing.title}
                    <!-- 파일첨부를 여부를 확인 후 디스크 이미지 표시 -->
                    <c:if test="${not empty writing.attachment}">
                    <a href="/file/uploadFold/${writing.attachment}" download>
                    <img src="/image/file.png" 
                    style="width:20px;height:20px"
                    onclick="event.cancelBubble=true"
                    alt="${writing.attachment}"
                    >
                    </a>
                    </c:if>
                    </td>
                    <td>${writing.author}</td>
                    <td><fmt:formatDate value="${writing.createdat}" pattern="yy/MM/dd"/></td>
                    <td>${writing.viewcnt}</td>
                </tr>
                <c:set var="num" value="${num+1}"/>
             </c:forEach>
            </tbody>
            </table>
            <div>
           
            <c:if test="${boardpage.isPre}"> 
            <button onclick="pageMove(${boardpage.startPage-5}, '${searchQuery}')">
            이전페이지</button>
            </c:if>
           
            <c:forEach var="pnum" begin="${boardpage.startPage}" 
            end="${boardpage.endPage}">
            <button onclick="pageMove(${pnum}, '${searchQuery}')">
            ${pnum}
            </button>
            </c:forEach>
         
            <c:if test="${boardpage.isNext}">
            <button onclick="pageMove(${boardpage.endPage+1})">
            다음페이지</button>
            </c:if>
            </div>
    </div>
    <script>
    function pageMove(requestPage) {
    	//alert(requestPage)
    	//alert('${searchQuery}'); //jsp파일에서 자바스크립트 코드 안에 el태그하는 방법
    	
    	let searchQuery = '${searchQuery}';
    	
    	let url='/board/?searchQuery='
    			+searchQuery+'&requestPage='+requestPage;
    	
    	//위의 url을 이용하여 페이지 요청(요청을 index.jsp에서 요청하는 것으로 변경)
    	location.href=url;
    	
    	/*
    	let searchQuery = [[${searchQuery}]];
    	let url='/board/ajaxList?searchQuery='+searchQuery'&requestPage='+requestPage;
    	*/
    }
    </script>

    <!-- Bootstrap JS 및 의존성 (Popper.js 포함) -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.min.js"></script>
</body>
</html>
