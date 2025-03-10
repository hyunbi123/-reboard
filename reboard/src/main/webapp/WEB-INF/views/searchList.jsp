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
        <p>총게시물 ${boardpage.totalCount }
        /총페이지 ${boardpage.totalPage } 중 ${boardpage.requestPage}페이지
        /한페이지당 글의 수
        <select> 
        <option value="${boardpage.pagePerCount}">${boardpage.pagePerCount}</option>
        </select>
        </p>
        <p>
        <button onclick="location.href='/board/write'">글쓰기</button>
        </p>
        <!-- 검색기능 -->
        <p>
        <form id="search">
        <select id="searchField">
            <option value="title">제목</option>
            <option value="content">내용</option>
            <option value="author">글쓴이</option>
            <option value="createdat">날짜</option>
            <option value="title_content">제목+내용</option>
        </select>
        <input type="text" id="searchQuery" 
        placeholder="검색어를 입력하세요" value="">
        <span id="searchDateForm">
       	검색시작일:<input type="date" id="startDate" value="">
       	검색종료일:<input type="date" id="endDate" value="">
        </span>
        <button type="button" onclick="performSearch()">검색</button>
        </form>
        </p>
        <script>
        //페이지가 로드될 때 startDate, endDate는 hidden
        //특정태그를 숨기는 방법?display(block, none), visibility(visible, hidden)
        function pageLoaded(){
        	//document.getElementById("startDate").type="hidden"
        	//document.getElementById("endDate").type="hidden"
        	document.getElementById("searchDateForm").style.display='none';
        	//document.getElementById("searchDateForm").style.visibility='hidden';
        }
        
        //특정태그가 클릭될 때(이벤트) 함수가 호출되는 방법
        //id searchField의 값이 변화할 때 이벤트처리
        const sf = document.getElementById('searchField');
        sf.addEventListener('change',function(event){
        	//alert(sf.value)
        	//alert(event.target.value)
        if(event.target.value==='createdat'){
       	document.getElementById("searchDateForm").style.display='block';
       	document.getElementById("searchQuery").type="hidden"
       	}else{
       	document.getElementById("searchDateForm").style.display='none';
       	document.getElementById("searchQuery").type="text"
        }
        })
        
        //검색버튼클릭할 때
        function performSearch(){
        	//alert("test")
        	let searchField=document.getElementById("searchField").value;
        	let searchQuery=document.getElementById("searchQuery").value;
        	 //alert(searchField + searchQuery)
        	let startDate=document.getElementById("startDate").value;
        	let endDate=document.getElementById("endDate").value;
        	//alert(startDate + endDate)
        	
        	//Q.검색버튼을 클릭할 때 검색필드와 검색값을 추출하여 
        	//컨트롤러(search함수로 전송)로 전송하시오.
        	if(searchField==="createdat"){
        		location.href="/board/search?searchField="
        			+searchField
        			+"&startDate="+startDate
        			+"&endDate="+endDate;
        	}else{
        		location.href="/board/search?searchField="
        			+searchField
        			+"&searchQuery="+searchQuery;
        	}
        	
        }
        
        //실시간검색:id가 serachQuery인 입력상자에 키보드가 up될때 이벤트발생
        const sq=document.getElementById("searchQuery")
        sq.addEventListener("keyup",function(event){
        	console.log(event.target.value)
        	let searchField=document.getElementById("searchField").value;
        	//아래 내용을 실행할 경우 페이지가 새로고침 되므로 ajax로 처러
        	location.href="/board/search?searchField="
        			+searchField
        			+"&searchQuery="+event.target.value;
        	//추가사항 : 엔터키를 입력했을 때 편집창이 clear
        	if(event.key==='Enter'){
        		sq.value="";
        	}
        })
        </script>
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
                    <img src="/img/reply_icon.gif" style="width:42px;height:15px">
                    </c:forEach>
                    ${writing.title}
                    <!-- 파일첨부를 여부를 확인 후 디스크 이미지 표시 -->
                    <c:if test="${not empty writing.attachment}">
                    <a href="/file/uploadFold/${writing.attachment}" download>
                    <img src="/img/file.png" 
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
            <button onclick="location.href='/board/list?requestPage=${boardpage.startPage-5}'">
            이전페이지</button>
            </c:if>
           
            <c:forEach var="pnum" begin="${boardpage.startPage}" 
            end="${boardpage.endPage}">
            <button onclick="location.href='/board/list?requestPage=${pnum}'">
            ${pnum}
            </button>
            </c:forEach>
         
            <c:if test="${boardpage.isNext}">
            <button onclick="location.href='/board/list?requestPage=${boardpage.endPage+1}'">
            다음페이지</button>
            </c:if>
            </div>
    </div>

    <!-- Bootstrap JS 및 의존성 (Popper.js 포함) -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.min.js"></script>
</body>
</html>
