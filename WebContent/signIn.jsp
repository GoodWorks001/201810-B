<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ログイン画面</title>

</head>
<body>

	<center><img src="C:\Users\education\Desktop\写真\giniziShop.png">

	<br><br>
	<h1>ログイン</h1>


	<span class="label label-danger"><font color = "red">${message}</font></span>

	<form action = "SignIn" method = "POST">
	<table>
		<tr><td>名前:</td><td><input type="text" name = "name"></td></tr>
		<tr><td>パスワード:</td><td><input type="password" name = "pass"></td></tr>
	</table>
	<br>
	<input type="submit" value = "LOGIN">
	</form>

	<br>

	<input type="button" onclick="location.href='createAccount.jsp'"value="新規登録">
	</center>


</body>
</html>