<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script>
        $(document).ready(function(){
		// .ajax함수는 입력값은 1개고
		// 객체에 입력값2개 : 첫번째 입력값은 url주소, 두번째 입력 값은 콜백함수)
		/*
		$.ajax({url: "/board/ajaxList", success: function(result){
		      $("#result").html(result);
		    }});
		*/
	
		/*
		let searchQuery = '${searchQuery}';
		let requestPage = ${requestPage};
		let url = "/board/ajaxList?searchQuery="+searchQuery+"&requestPage="+requestPage;
		$.ajax({url: "/board/ajaxList", success: function(result){
			$("#result").html(result);
		}});
		*/
		
		//ajax를 이용한 페이지 처리
		let requestPage=${requestPage};
		let searchQuery='${searchQuery}';
		let url='/board/ajaxList?searchQuery='
			+searchQuery+'&requestPage='+requestPage;
		$.ajax({url: url, success: function(result){
		      $("#result").html(result);
		    }});
		$("#searchQuery").val(searchQuery);
		
		/*
		${button}.click(function(){
			$.ajax({url: "/board/ajaxList", success: function(result){
				$("#result").html(result);
			}});
		});
		*/
		
		$("#searchQuery").keyup(function(event) {
			//console.log(event.target.value);
			let searchQuery = event.target.value;
			let url = "/board/ajaxList?searchQuery="+searchQuery;
			console.log(url);
			$.ajax({url: url, success: function(result){
				$("#result").html(result);
				}});
		});
 });
</script>
</head>

<body>
<!-- <button>검색</button> -->
<input type="text" id="searchQuery" value="">
<div id="result"></div>
</body>

</html>