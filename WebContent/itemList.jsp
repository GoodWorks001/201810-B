<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="java.util.List"%>
	<%@page import="java.util.ArrayList"%>
	<%@page import="web.ItemBean"%>


<% List<ItemBean> bean =  (ArrayList<ItemBean>)request.getAttribute("rs");%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<%if(bean!=null){%>
	<%--送信先のサーブレットを指定。doGetメソッドで --%>
	<form action="./itemList" method="get">
		<center><h3>検索</h3>
		<%--検索ワードの入力 --%>
			<input type="text" name="word">
		<br>
		<br>

		<%--カテゴリーの選択 --%>
			カテゴリ
		<select name= "cat" >
			<option value= "" disabled selected>未選択</option>
			<option value= "1" >食べ物</option>
			<option value= "2">飲み物</option>
		</select>
		<br>
		<input type="submit" value="検索">
		<br>

		<table border='1' width="300" cellspacing="0">
			<tr>
			<th>商品名</th>
			<th>価格</th>
			<th>詳細</th>
			</tr>

			<%for(ItemBean item:bean){%>


			<tr>
			<td><%=item.getProName() %></td>
			<td><%=item.getProPrice() %></td>
			<td>


			<%--ここに次のサーブレットのURL --%>


			<%--選択した商品のボタンによって、次のサーブレットにproCdを渡す --%>
			<a href="./Det?product=<%=item.getProCd()%>">詳細</a>


			<%}%>

			<% }else{ %>
			<h2>検索できません</h2>
			<%}%>

			</tr>
			</table>
			</center>



</form>
	<br>

</body>
</html>