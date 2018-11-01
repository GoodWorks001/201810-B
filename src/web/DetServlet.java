package web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DetServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		int proCd = Integer.parseInt(req.getParameter("product"));


		String proName="";
	 	int stockNo=0;
	 	int proPrice=0;
	 	int catId=1;
		String proImg="";
		String proMsg="";
		String catName="";

		//DBMSへの接続
		String url = "jdbc:mysql://localhost/giniziShop?autoReconnect=true&useSSL=false";
		String idd = "root";
		String pw = "password";

		//定義
		Connection cnct = null;
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			//データベース接続
			Class.forName("com.mysql.jdbc.Driver");
			cnct = DriverManager.getConnection(url,idd,pw);


			//sql文でproductテーブルの値を取得
			String query ="select * from product where pro_cd = ?";
			st = cnct.prepareStatement(query);
			st.setInt(1,proCd);
			rs = st.executeQuery();

			while(rs.next()) {
				 proName = rs.getString("pro_name");
				 stockNo = rs.getInt("stock_no");
				 proPrice = rs.getInt("pro_price");
				 catId= rs.getInt("cat_id");
				 proImg = rs.getString("pro_img");
				 proMsg = rs.getString("pro_Msg");
			}

			query ="select * from category where cat_id = ?";
			st = cnct.prepareStatement(query);
			st.setInt(1,catId);
			rs = st.executeQuery();

			while(rs.next()) {
				catName = rs.getString("cat_name");
			}

			req.setAttribute("catName",catName);
			req.setAttribute("proName",proName);
			req.setAttribute("proPrice",proPrice);
			req.setAttribute("stockNo",stockNo);
			req.setAttribute("proImg",proImg);
			req.setAttribute("proMsg",proMsg);
			req.setAttribute("proCd",proCd);


			req.getRequestDispatcher("proDetail.jsp").forward(req, resp);

		}catch(ClassNotFoundException ex) {
			ex.printStackTrace();
		}catch(SQLException ex) {
			ex.printStackTrace();
		}finally {
			try {
				if(st != null)st.close();
				if(cnct != null)cnct.close();
				if(rs != null)rs.close();
			}catch(Exception ex) {}
		}


	}


}
