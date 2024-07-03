package com.hry.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hry.bean.BookBean;
import com.hry.dao.BookDao;
import com.hry.bean.HistoryBean;

/**
 * Servlet implementation class SelectBlogServlet
 */
@WebServlet("/SelectBlogServlet")
public class SelectBlogServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SelectBlogServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//设置编码类型
		request.setCharacterEncoding("UTF-8");
		response.setContentType("bookname");
		
		//因为在管理员界面和读者界面都有查找功能，为了将查找的结果返回正确的页面，设置了tip，tip=1表示管理员界面
		int tip = Integer.parseInt(request.getParameter("tip"));
		System.out.println(tip);
		String bookname = request.getParameter("bookname");
		BookDao bookdao = new BookDao();
		ArrayList<HistoryBean> data = bookdao.getLikeListBlog(bookname);
		//将获取的结果存入请求中
		request.setAttribute("data", data);
		String url ="";
		//判断，转发界面
		if (tip == 1) {
			url = response.encodeURL("admin_blog.jsp");
		} else {
			url = response.encodeURL("select.jsp");
		}
		//请求转发
		request.getRequestDispatcher(url).forward(request, response);
	}

}
