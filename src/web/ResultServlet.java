package web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ResultServlet extends HttpServlet {


	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	resp.setContentType("text/html;charset=UTF-8");




//    //セッション継続
//	if(msg !=null) {
//		 HttpSession session = req.getSession(false);
//
//		    if(session== null)
//		    	return;
//
//    //セッション終了
//	}else if(msg1 !=null){
//		HttpSession session = req.getSession(false);
//		if (session != null) {
//		session.invalidate();

   //Result.jspを呼び出す
	RequestDispatcher dispatcher = req.getRequestDispatcher("Result.jsp");
    dispatcher.forward(req, resp);


   }
}
