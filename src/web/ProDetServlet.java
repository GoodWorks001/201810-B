package web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ProDetServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setContentType("text/html;charset=UTF-8");
		req.setCharacterEncoding("UTF-8");

		HttpSession session = req.getSession(false);

		if(session.getAttribute("loginName") == null) {
			resp.sendRedirect("./signIn.jsp");
		} else {
			req.setCharacterEncoding("UTF-8");
			int num = Integer.parseInt(req.getParameter("num"));
			String proName = req.getParameter("proName");
			String proPrice = req.getParameter("proPrice");
			int proCd = Integer.parseInt(req.getParameter("proCd"));

			String cart = req.getParameter("cart");
			String back = req.getParameter("back");

			//DBMSへの接続
			String url = "jdbc:mysql://localhost/giniziShop?autoReconnect=true&useSSL=false";
			String idd = "root";
			String pw = "password";

			//定義
			Connection cnct = null;
			PreparedStatement st = null;
			Statement stmt = null;
			ResultSet rs = null;


			try {
				//ドライバのロードとインスタンス化
				Class.forName("com.mysql.jdbc.Driver");
				//データベースとの接続を確立
				cnct = DriverManager.getConnection(url,idd,pw);
				//cart押された場合
				if(cart != null) {
				//Mysqlの
				String query = "insert into purchase (pro_cd,order_no) values(?,?)";
				st = cnct.prepareStatement(query);

				st.setInt(1,proCd);
				st.setInt(2,num);
				st.executeUpdate();


					HttpSession sessionSet =req.getSession(true);
					sessionSet.setAttribute("proNameCart", proName);
					sessionSet.setAttribute("proPriceCart", proPrice);
					sessionSet.setAttribute("proNumCart", num);

					req.getRequestDispatcher("cart.jsp").forward(req, resp);

				//back押された場合
				}else if(back != null) {
					resp.sendRedirect("./itemList");
				}

			//ClassNotFoundExceptionが起きた時実行
			}catch(ClassNotFoundException ex) {
				ex.printStackTrace();
			//SQLExceptionが起きた時実行
			}catch(SQLException ex) {
				ex.printStackTrace();
			//最後
			}finally {
				try {
					if(st != null) st.close();
					if(cnct != null) cnct.close();
				} catch(Exception ex) { }
			}
		}

	}

}
