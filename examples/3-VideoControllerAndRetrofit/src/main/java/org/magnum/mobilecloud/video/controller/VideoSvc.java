package org.magnum.mobilecloud.video.controller;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.magnum.mobilecloud.video.client.VideoSvcApi;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 这个 VideoSvc 很简单，它允许客户端向它发送包含视频数据的 HTTP POST 请求，并且
 * 将这些视频数据存储在内存中的 list 对象中。客户端还可以发送 HTTP GET 请求，并可
 * 接收到一个包含到目前为止所有已经发送到服务器的视频数据的 JSON 列表。
 * 
 * 请大家注意的是要仔细观察这个 VideoSvc 比之前的 VideoServlet 简化了多少？Spring
 * 框架极大的简化了服务的开发。另外一个很重要的方面是，这个版本的服务中定义了 VideoSvcApi，
 * 这个接口为客户端和服务器端之间的通讯提供了强类型，确保不会发送错误的参数等等。
 */

// 告诉 Spring 这个类是一个 Controller，可以从 DispatcherServlet 
// 接收特定的 HTTP 请求。
@Controller
public class VideoSvc implements VideoSvcApi {
	
	// 一个内存对象，Servlet 用它来存储客户端发送来的视频数据。
	private List<Video> videos = new CopyOnWriteArrayList<Video>();

	// 接收目标路径是 /video 的 POST 请求，将 HTTP Request Body
	// 中的 JSON 数据转换为对象，然后添加到列表当中。@RequestBody
	// 标注在 Video 参数上，意思是告诉 Spring 将 HTTP Request Body
	// 中的数据按照 JSON 来进行解析，并它转化为一个 Video 对象。
	// @ResponseBody 标注告诉 Spring 将方法执行后的返回值转变为
	// JSON，然后通过 HTTP Response Body 返回给客户端。
	// 
	// VIDEO_SVC_PATH 在 VideoSvcApi 接口中定义为 "/video"。
	// 对于 VideoSvc 来说，用这个常量确保客户端请求和服务的路径总保持
	// 一直。
	// 
	// 了解更多关于如何改进 Video 对象的验证，可以查看 Spring 的官方
	// 文档：http://docs.spring.io/spring/docs/3.2.x/spring-framework-reference/html/validation.html#validation-beanvalidation

	@RequestMapping(value=VIDEO_SVC_PATH, method=RequestMethod.POST)
	public @ResponseBody boolean addVideo(@RequestBody Video v){
		return videos.add(v);
	}
	
	// 接收目标路径是 /video 的 GET 请求，并返回当前内容中的视频列表。
	// 由于在方法的返回值位置标注了 @ResponseBody，Spring 自动的将视频列表转换为 JSON。
	@RequestMapping(value=VIDEO_SVC_PATH, method=RequestMethod.GET)
	public @ResponseBody List<Video> getVideoList(){
		return videos;
	}

}
