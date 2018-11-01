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
import javax.servlet.http.HttpSession;

public class SignInServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String NAME=null,PASS=null;

		String name = req.getParameter("name");
		String pass = req.getParameter("pass");

		//DBMSへの接続
		String url = "jdbc:mysql://localhost/giniziShop";
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


			//sql文
			String query ="select * from user where user_name = ?";
			st = cnct.prepareStatement(query);
			st.setString(1,name);
			rs = st.executeQuery();

			while(rs.next()) {
				NAME = rs.getString("user_name");
				PASS = rs.getString("plgin_pw");
			}


			//名前、パスワードの判定
			if(name == "" || name == null || pass == "" || pass == null) {
				req.setAttribute("message", "名前またはパスワードを入力してください");
				req.getRequestDispatcher("signIn.jsp").forward(req, resp);
				return;
			}else if(name.equals(NAME) || pass.equals(PASS)) {
				HttpSession session = req.getSession(true);
				session.setAttribute("loginName",NAME);
				session.setAttribute("loginPass",PASS);

//				req.getRequestDispatcher("/itemList").forward(req, resp);
				resp.sendRedirect("./itemList");

			}else {
					req.setAttribute("message", "名前またはパスワードが一致しません");
					req.getRequestDispatcher("signIn.jsp").forward(req, resp);
					return;
			}



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


