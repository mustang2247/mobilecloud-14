
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
 * 做了一个 HTML 表单收集并现实视频元数据。允许客户端通过 HTTP POST 请求发送
 * 视频数据，并将数据存储在服务器的内存列表对象中。客户端还可以发送 HTTP GET 
 * 请求以获取到目前为止已经发送到 Servlet 中的视频对象列表。由于视频对象存储
 * 在内存当中，所以停止 Servlet 会导致丢失所有的视频历史数据。
 * 
 * @author Anonymous
 *
 */
public class HtmlVideoServlet extends HttpServlet // Servlet 需要继承 HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public static final String VIDEO_ADDED = "视频已添加。";
	
	// 内存中的List类型，用于存储客户端发来的视频数据。
    private List<Video> videos = new java.util.concurrent.CopyOnWriteArrayList<Video>();
    
    protected void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // 这个 PrintWriter 可以将返回给客户端的数据写入 HTTP Response Body。
        PrintWriter sendToClient = resp.getWriter();
        
        // UI 表单
        sendToClient.write(
            "<form name='formvideo' method='POST' target='_self'>" +
            "<fieldset><legend>视频数据</legend>" +
            "<table><tr>" +
            "<td><label for='name'>名称：&nbsp;</label></td>" +
            "<td><input type='text' name='name' id='name' size='64' maxlength='64' /></td>" +
            "</tr><tr>" +
            "<td><label for='url'>URL:&nbsp;</label></td>" +
            "<td><input type='text' name='url' id='url' size='64' maxlength='256' /></td>" +
            "</tr><tr>" +
            "<td><label for='duration'>长度：&nbsp;</label></td>" +
            "<td><input type='text' name='duration' id='duration' size='16' maxlength='16' /></td>" +
            "</tr><tr>" +
            "<td style='text-align: right;' colspan=2><input type='submit' value='添加视频' /></td>" +
            "</tr></table></fieldset></form>");
        
        // 循环遍历所有已经存储的 video 数据，并打印到客户端。
        for (Video v : this.videos) {
            
        	// 对于每一个 Video 对象，将它的 name 和 url 写入到 HTTP Response Body 中。
            sendToClient.write(v.getName() + " : " + v.getUrl() + " (" + v.getDuration() + ")<br />");
        }
        sendToClient.write("</body></html>");
    }
    
	/**
	 * 这个方法用于接收并处理所有 Web Container 路由到当前 Servlet 的 
	 * HTTP GET 请求。本方法将循环遍历到目前为止在 List 当中存储的所有 video 
	 * 数据，并生成 plain/text 的视频列表数据发送回客户端。
	 */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    	
    	// 确保设置好正确的 content-type，这样客户端可以正确的（安全的！）显示
    	// 你返回的数据。
        resp.setContentType("text/html; charset=utf-8");

    	resp.getWriter().write("<html><body>");
        processRequest(req, resp);
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

		// 现在，Servlet 必须检查客户端发送来每一个请求参数是否满足期望的要求，确保不为 null，
		// empty，等等。
        if (name == null || url == null || durationStr == null
                || name.trim().length() < 1 || url.trim().length() < 10
                || durationStr.trim().length() < 1
                || duration <= 0) {
            
        	// 确保设置好正确的 content-type，这样客户端可以正确的（安全的！）显示
        	// 你返回的数据。
            resp.setCharacterEncoding("utf-8");

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
            
        	// 确保设置好正确的 content-type，这样客户端可以正确的（安全的！）显示
        	// 你返回的数据。
            resp.setContentType("text/html; charset=utf-8");
            
			// 在 HTTP Response Body 中写入一些信息，
			// 告知客户端服务器已经成功的添加了新的 vdieo 对象。
            resp.getWriter().write("<html><body>" + VIDEO_ADDED);
            
            processRequest(req, resp);
        }
    }
}