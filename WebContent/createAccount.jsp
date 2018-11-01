<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ログイン画面</title>
</head>
<body>
	<center><h1>登録</h1>

	<span class="label label-danger"><font color = "red">${message}</font></span>

	<form action = "Create" method = "POST">
	<table>
		<tr><td>名前:</td><td><input type="text" name = "name"></td></tr>
		<tr><td>パスワード:</td><td><input type="password" name = "pass"></td></tr>
		<tr><td>パスワード確認:</td><td><input type="password" name ="passAuth"></td></tr>
	</table>
	<input type="submit" value = "CREATE ACCOUNT">
	</form>

	<br>


	<input type="button" onclick="location.href='signIn.jsp'"value="ログイン">
	</center>


</body>
</html>