package org.magnum.mobilecloud.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 一个简单的 Servlet 示例，它能够接收一个请求参数，并且将参数数据通过 "text/plain"
 * 的方式返回。
 */
public class EchoServlet extends HttpServlet // Servlets should inherit from HttpServlet
{

	/**
	 * 所有被路由到这个 Servlet 当中的 HTTP GET 请求将被下面的这个方法接收并
	 * 处理。哪些请求应当被路由到这个 Servlet 在 web.xml 当中描述（查看：
	 * src/main/webapp/WEB-INF/web.xml）
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		// 设置 content-type 头信息，它将通过 HTTP Response 被返回到客户端，客户端
		// 通过该信息决定如何显示结果。
		resp.setContentType("text/plain");
		
		// 查看 HTTP Request 当中是否有命名为 "msg" 的查询参数（Query Parameter）
		// 或者表单参数（URL Encoded Form Parameter）。
		String msg = req.getParameter("msg");
		
		// http://localhost:8080/1-SimpleServlet/echo?msg=hello
		
		// 将客户端发送来的 msg 信息返回。
		resp.getWriter().write("Echo:"+ msg);
	}

	/*
	 * 这个 Servlet 没有提供 doPost()，doPut() 等父类方法的重载实现，那么这个 Servlet
	 * 并不能处理相应的 HTTP Method。如果你需要支持 POST 请求的话，你需要重载 doPost()
	 * 方法。
	 */
	
}
