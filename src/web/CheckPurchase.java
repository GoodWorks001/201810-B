package web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



public class CheckPurchase extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");

		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession(false);

		if(session.getAttribute("loginName") == null) {
			response.sendRedirect("./signIn.jsp");
		} else {

		//パラメータの取得
		String yes=	null;
		String no = null;
		yes=request.getParameter("yes");
		no=request.getParameter("no");

		String url = "jdbc:mysql://localhost/ginizishop";
		String id = "root";
		String pw = "password";

		  List<Integer> proCd = new ArrayList<Integer>();
	        List<Integer> num = new ArrayList<Integer>();

		Connection cnct = null;
		PreparedStatement ps = null;
		Statement st =null;
		ResultSet rs = null;


		//もしYesボタンを押されたら次の画面に繋がる
		if(no==null) {

			try {
			Class.forName("com.mysql.jdbc.Driver");
			cnct = DriverManager.getConnection(url,id,pw);

			st = cnct.createStatement();

			rs = st.executeQuery("select * from purchase");
			while(rs.next()) {
				proCd.add(rs.getInt("pro_cd"));
				num.add(rs.getInt("order_no"));
			}


			st.executeUpdate("delete from purchase");



			//各購入商品の在庫を購入分減らしている
			String query ="update product set stock_no=stock_no-? where pro_cd=?;";

			for(int i=0;i<proCd.size();i++) {
			ps=cnct.prepareStatement(query);
			ps.setInt(1, num.get(i));
			ps.setInt(2, proCd.get(i));
			ps.executeUpdate();
			}

			RequestDispatcher rd = request.getRequestDispatcher("./Result");
			rd.forward(request, response);

			}catch(ClassNotFoundException ex) {
				ex.printStackTrace();
			}catch(SQLException ex) {
				ex.printStackTrace();
			}finally {
				try {
					if(cnct!=null) cnct.close();
					if(rs!=null) rs.close();
					if(ps!=null) ps.close();
				}catch(Exception ex) {}
			}

			} else {
				response.sendRedirect("./itemList");
			}
			//もしNoボタンが押されたらログイン画面に繋がる
		}
	}


}

