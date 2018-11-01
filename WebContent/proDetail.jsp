<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% request.setCharacterEncoding("UTF8");
	String proName = (String)request.getAttribute("proName");
	String catName = (String)request.getAttribute("catName");
	int proPrice =(Integer)request.getAttribute("proPrice");
	int stockNo = (Integer)request.getAttribute("stockNo");
	String proMsg = (String)request.getAttribute("proMsg");
	String proImg = (String)request.getAttribute("proImg");
	int proCd = (Integer)request.getAttribute("proCd"); %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品紹介</title>
</head>
<body>
	<center><h1>商品紹介</h1>

	<form action = "ProDet" method = "POST">
	<img src="C:\Users\education\Desktop\写真\<%= proImg%>" align="left">

	<table border = "1" cellspacing="0">
	<tr><td>商品名</td><td><%= proName%></td></tr>
	<tr><td>カテゴリ</td><td><%= catName%></td></tr>
	<tr><td>価格</td><td><%= proPrice%></td></tr>
	<tr><td>在庫</td><td><%= stockNo%></td></tr>
	<tr><td>商品紹介</td><td><%= proMsg%></td></tr>
	</table>

	個数<select name = "num">
	<% for(int i = 1;i <= stockNo; i++){ %><option value=<%= i %>><%= i%></option><% } %>
	</select>

	<input type="submit" value = "カートへ" name = "cart"><input type="submit" value = "戻る" name = "back">
	<input type="hidden" name="proName" value=<%= proName %>>
	<input type="hidden" name="proPrice" value=<%= proPrice %>>
	<input type="hidden" name="proCd" value=<%= proCd %>>
	</form>
	</center>

</body>
</html>