<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>회원가입</h2>
	<form action="/member/process" method="post">
		<p>아이디:<input type="text" name="id">
		<p>비밀번호:<input type="password" name="passwd">
		<p>이름:<input type="text" name="name">
		<p>연락처:<input type="text" name="phone">
		<p>이메일:<input type="text" name="email">
		<p><input type="submit" value="회원가입">
	</form>
</body>
</html>





