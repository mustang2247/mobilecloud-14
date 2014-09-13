package org.magnum.mobilecloud.video.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * VideoServlet 可以提供两个接口：
 *   1. 接收客户端发送来的 HTTP POST 请求，
 *      并将客户端发送来的视频数据存储在内存中的林彪当中。
 *   2. 接收客户端发送来的 HTTP GET 请求，
 *      并将目前客户端已经发送来的视频列表数据以 plain/text 的方式返回给客户端。
 * VideoServlet 停止运行后，将丢失所有客户端已经发送来的视频数据，因为视频数据是
 * 存储在内存当中的。
 */

public class VideoServlet extends HttpServlet // 必须从 HttpServlet 派生
{
	
	public static final String VIDEO_ADDED = "视频以添加。";
	
	// 内存中的List类型，用于存储客户端发来的视频数据。
	private List<Video> videos = new ArrayList<Video>();

	/**
	 * 这个方法用于接收并处理所有 Web Container 路由到当前 Servlet 的 
	 * HTTP GET 请求。本方法将循环遍历到目前为止在 List 当中存储的所有 video 
	 * 数据，并生成 plain/text 的视频列表数据发送回客户端。
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// 确保正确设置 content-type 头信息，客户端将根据该信息用适当的方法（安全的方法）
		// 展现服务器返回的内容。
		resp.setContentType("text/plain; charset=utf-8");

		// PrintWriter 用来将返回给客户端的数据 写入 HTTP Response Body。
		PrintWriter sendToClient = resp.getWriter();
		
		// 循环遍历所有已经存储的 video 数据，并打印到客户端。
		for (Video v : this.videos) {
			
			// 对于每一个 Video 对象，将它的 name 和 url 写入到 HTTP Response Body 中。
			sendToClient.write(v.getName() + " : " + v.getUrl() + "\n");
		}

	}

	/**
	 * 这个方法用于接收并处理所有 Web Container 路由到本 Servlet 的 HTTP POST 请求。
	 * 
	 * 通过 POST 的方式将 'name'，'duration'，'url' 数据参数发送到 Servlet，创建一
	 * 个新的 video 对象，并将它添加到 videos 列表当中。
	 * 
	 * 如果客户端没有正确的发送这些参数数据，本 Servlet 生成一个 HTTP 400 错误（错误请求）
	 * 并返回给客户端，指示客户端请求当中所需要的参数没有正确的发送。
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		// 首先，从 URL Query String 或者 URL Encoded Form Body 中获取 HTTP 请求
		// 当中的参数信息。
		String name = req.getParameter("name");
		String url = req.getParameter("url");
		String durationStr = req.getParameter("duration");
		
		// 检查客户端发送的 duration 参数是否是一个数字。 
		long duration = -1;
		try{
			duration = Long.parseLong(durationStr);
		}catch(NumberFormatException e){
			// 客户端发送的 duration 数据不是一个数字！
		}

		// 确保正确设置 content-type 头信息，让客户端知道如何处理返回的数据。
		resp.setContentType("text/plain; charset=utf-8");

		// 现在，Servlet 必须检查客户端发送来每一个请求参数是否满足期望的要求，确保不为 null，
		// empty，等等。
		if (name == null || url == null || durationStr == null
				|| name.trim().length() < 1 || url.trim().length() < 10
				|| durationStr.trim().length() < 1
				|| duration <= 0) {
			
			// 如果数据参数没有通过最基本的检查，我们需要返回 HTTP 400 Bad Request 给客户端，
			// 并给出一些为什么错误的提示信息。
			resp.sendError(400, "未找到 ['name','duration','url'].");
		} 
		else {
			// 如果运行到这里，说明客户端提供的数据是正确的，
			// 那么使用这些数据来构建一个新的 Video 类型的对象。
			Video v = new Video(name, url, duration);
			
			// 将 video 对象添加到内存中的 videos 列表当中。
			videos.add(v);
			
			// 在 HTTP Response Body 中写入一些信息，
			// 告知客户端服务器已经成功的添加了新的 vdieo 对象。
			resp.getWriter().write(VIDEO_ADDED);
		}
	}
}
