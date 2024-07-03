package com.hry.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hry.bean.HistoryBean;
import com.hry.dao.BookDao;

/**
 * Servlet implementation class DeleteServlet
 */
@WebServlet("/QueryServlet")
public class QueryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public QueryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//删除图书信息
		//设置编码类型
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset= UTF-8");
		//Integer 类：parseInt() 方法用于将字符串参数作为有符号的十进制整数进行解析
		int bid = Integer.parseInt(request.getParameter("bid"));
		BookDao bookdao = new BookDao();
		ArrayList<HistoryBean> data = bookdao.getBidListBlog(bid);
		//将获取的结果存入请求中
		request.setAttribute("data", data);
		String url ="";
		//判断，转发界面
			url = response.encodeURL("admin_blog.jsp");
		request.getRequestDispatcher(url).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
