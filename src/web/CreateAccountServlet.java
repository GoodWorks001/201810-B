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

public class CreateAccountServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//		resp.setContentType("text/html;charset=UTF-8");
//		req.setCharacterEncoding("UTF-8");

		resp.setContentType("text/html;charset=UTF-8");
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession(false);
		session.invalidate();

		String NAME=null;

		String name = req.getParameter("name");
		String pass = req.getParameter("pass");
		String passAuth = req.getParameter("passAuth");
		int descID = 0;

		//DBMSへの接続
		String url = "jdbc:mysql://localhost/giniziShop?autoReconnect=true&useSSL=false";
		String idd = "root";
		String pw = "password";

		//定義
		Connection cnct = null;
		Statement stmt = null;
		PreparedStatement st = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		ResultSet rsId = null;

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
			}

			String OutIdQuery = "SELECT user_id FROM user ORDER BY user_id DESC LIMIT 1";
			stmt = cnct.createStatement();
			rsId = stmt.executeQuery(OutIdQuery);
			while(rsId.next()) {
				descID = rsId.getInt("user_id");
			}

			//名前、パスワードの判定
			if(name == "" || name == null || pass == "" || pass == null) {
				req.setAttribute("message", "名前またはパスワードを入力してください");
				req.getRequestDispatcher("createAccount.jsp").forward(req, resp);
				return;
			}else if(name.equals(NAME)) {
				req.setAttribute("message", "既に使用されている名前です");
				req.getRequestDispatcher("createAccount.jsp").forward(req, resp);
				return;
			}else {

					if(pass.equals(passAuth)) {

						query = "INSERT INTO user VALUES (?, ?, ?, ?);";
						pst = cnct.prepareStatement(query);
						pst.setInt(1, ++descID);
						pst.setString(2, name);
						pst.setString(3, name);
						pst.setString(4, pass);
						pst.executeUpdate();

//						HttpSession session = req.getSession(true);
//						session.setAttribute("loginName",name);
//						session.setAttribute("loginPass",pass);


						resp.sendRedirect("./itemList");
					} else {
						req.setAttribute("message","パスワード確認と一致していません");
						req.getRequestDispatcher("createAccount.jsp").forward(req, resp);
					}

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
