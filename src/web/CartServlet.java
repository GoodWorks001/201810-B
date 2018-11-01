package web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CartServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setContentType("text/html;charset=UTF-8");
		req.setCharacterEncoding("UTF-8");

		HttpSession session = req.getSession();

		if(session.getAttribute("loginName") == null) {
			resp.sendRedirect("./signIn.jsp");
		} else {
			req.setCharacterEncoding("UTF-8");
			String cont = req.getParameter("cont");
			String buy = req.getParameter("buy");

			if(cont != null) {
				resp.sendRedirect("./itemList");
			}else if(buy != null) {
				req.getRequestDispatcher("purCheck.jsp").forward(req, resp);
			}
		}
	}


}
