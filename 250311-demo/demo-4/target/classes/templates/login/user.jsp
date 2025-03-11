<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" 
uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>user page</h1>
<!-- a tag는 get방식, 로그아웃시 post방식으로 처리해야 함. -->
<!-- <a href="/logout">[LOGOUT]</a> -->
<form action="/logout" method="post">
<input type="submit" value="[로그아웃]">
<input type="hidden" 
name="${_csrf.parameterName}"
value="${_csrf.token }" />
</form>
<p>
	<sec:authentication property="principal.username" />
	<sec:authentication property="principal" var="user" />
    <p>로그인 사용자 정보: ${user}</p>
    <p>사용자 이름: ${user.username}</p>
    
 	<sec:authorize access="isAuthenticated()">
        <p>인증된 사용자!</p>
    </sec:authorize>
    
    <sec:authorize access="!isAuthenticated()">
        <p>인증되지 않은 사용자</p>
    </sec:authorize>

    <sec:authorize access="hasRole('USER')">
        <p>권한은 USER!</p>
    </sec:authorize>

    
</body>
</html>