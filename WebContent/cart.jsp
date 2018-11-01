<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="java.sql.*"%>
        <%@page import="java.util.List"%>
	<%@page import="java.util.ArrayList"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>カート</title>
</head>
<body>
	<center><h1>カート</h1></center>
	<center><table border='1' width="300" cellspacing="0"><tr><th>商品名</th><th>単価</th><th>数量</th></tr>

<%
        // データベースへのアクセス開始
		Connection cnct = null;
		Statement stmt = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		PreparedStatement st = null;

        List<Integer> plist = new ArrayList<Integer>();
        List<Integer> nlist = new ArrayList<Integer>();
        int total=0;
        int tax =0;

		String url = "jdbc:mysql://localhost/giniziShop?autoReconnect=true&useSSL=false";
		String idd = "root";
		String pw = "password";
        try {
			//ドライバのロードとインスタンス化
			Class.forName("com.mysql.jdbc.Driver");
            // データベースに接続するConnectionオブジェクトの取得
				cnct = DriverManager.getConnection(url,idd,pw);
            // データベース操作を行うためのStatementオブジェクトの取得
            stmt = cnct.createStatement();

            rs1 = stmt.executeQuery("select * from purchase;");
			while(rs1.next()){
				int num = rs1.getInt("pro_cd");


            // SQL()を実行して、結果を得る
           st = cnct.prepareStatement(
              "select product.pro_name,product.pro_price,purchase.order_no from purchase inner join product on purchase.pro_cd = product.pro_cd where purchase.pro_cd = ?;");

			st.setInt(1,num);
			rs = st.executeQuery();
            // 得られた結果をレコードごとに表示
            while (rs.next()) {
%>
                <tr>

                <td><%= rs.getString("pro_name")%></td>

                <td align="right">¥<%= rs.getInt("pro_price")%></td>
                <% plist.add(rs.getInt("pro_price")); %>

                <td align="right"><%= rs.getInt("order_no") %>個</td>
                <% nlist.add(rs.getInt("order_no")); %>
                </tr>

<%
            }
			}
			for(int i = 0;i < plist.size(); i++){
				total += plist.get(i) * nlist.get(i);
			}
			tax = (int)(total * 0.08);

			%>
			<tr>
			<td colspan="2" align="center">消費税</td><td align="right">¥<%=tax %></td></tr>
			<tr>
			<td colspan="2" align="center">合計金額</td><td align="right">¥<%= total %></td></tr>


<%
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // データベースとの接続をクローズ
            try { rs.close(); } catch (Exception e) {}
            try { stmt.close(); } catch (Exception e) {}
            try { cnct.close(); } catch (Exception e) {}
        }
%>

        </table></center>
        <form action = "cart" method = "POST">
        <center><input type="submit" value = "買い物を続ける" name = "cont"><input type="submit" value = "購入" name = "buy"></center>
        </form>

</body>
</html>