package web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
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

public class ItemListServlet extends HttpServlet {

	String word = null;
	String cat = null;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		res.setContentType("text/html;charset=UTF-8");
		req.setCharacterEncoding("UTF-8");

		word = req.getParameter("word");
		cat = req.getParameter("cat");

		List<ItemBean> rs = getItemListDAO(word, cat);
		req.setAttribute("rs", rs);

		RequestDispatcher rd = req.getRequestDispatcher("/itemList.jsp");
		rd.forward(req, res);

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {


		res.setContentType("text/html;charset=UTF-8");
		req.setCharacterEncoding("UTF-8");

		word = req.getParameter("word");
		cat = req.getParameter("cat");


		List<ItemBean> rs = getItemListDAO(word, cat);
		req.setAttribute("rs", rs);

		RequestDispatcher rd = req.getRequestDispatcher("/itemList.jsp");
		rd.forward(req, res);

	}


	public static List<ItemBean> getItemListDAO(String word_, String cat_) {

		String url = "jdbc:mysql://localhost/ginizishop?autoReconnect=true&useSSL=false";
		String id = "root";
		String pw = "password";

		Connection cnct = null;
		Statement stmt = null;
		ResultSet rs = null;

		List<ItemBean> items = new ArrayList<ItemBean>();


		try {
			Class.forName("com.mysql.jdbc.Driver");

			cnct = DriverManager.getConnection(url,id,pw);

			stmt = cnct.createStatement();

			String query = null;
			if( word_ != null || cat_ != null ) {
				if( word_ != null && cat_ != null ) {
					query = "SELECT * FROM product WHERE pro_name LIKE '%" + word_ + "%' AND cat_id =" + cat_ + " ;";
				} else if(word_ != null) {
					query = "SELECT * FROM product WHERE pro_name LIKE '%" + word_ + "%';";
				} else if(cat_ != null) {
					query = "SELECT * FROM product WHERE cat_id =" + cat_ + ";";
				}
			}  else {
				query = "SELECT * FROM product";
			}


			rs = stmt.executeQuery(query);

			while(rs.next()){

				int pro_cd = rs.getInt("pro_cd");
				String pro_name = rs.getString("pro_name");
				int stock_no = rs.getInt("stock_no");
				int pro_price = rs.getInt("pro_price");
				int cat_id = rs.getInt("cat_id");
				String pro_img = rs.getString("pro_img");
				String pro_msg = rs.getString("pro_msg");

				ItemBean Item = new ItemBean(
						pro_cd,
						pro_name,
						stock_no,
						pro_price,
						cat_id,
						pro_img,
						pro_msg

											);
				items.add(Item);
			}

		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (stmt != null) stmt.close();
				if (cnct != null) cnct.close();
			} catch (Exception ex) {
					ex.printStackTrace();
			}
		}
		return items;
	}

}
