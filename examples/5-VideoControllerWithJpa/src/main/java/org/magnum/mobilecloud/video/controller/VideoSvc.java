package org.magnum.mobilecloud.video.controller;

import java.util.Collection;

import org.magnum.mobilecloud.video.client.VideoSvcApi;
import org.magnum.mobilecloud.video.repository.Video;
import org.magnum.mobilecloud.video.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;

/**
 * 这个 VideoSvc 很简单，它允许客户端向它发送包含视频数据的 HTTP POST 请求，并且
 * 将这些视频数据存储在内存中的列表对象中。客户端还可以发送 HTTP GET 请求，并可
 * 接收到一个包含到目前为止所有已经发送到服务器的视频数据的 JSON 列表。
 * 
 * 请大家注意的是要仔细观察这个 VideoSvc 比之前的 VideoServlet 简化了多少？Spring
 * 框架极大的简化了服务的开发。另外一个很重要的方面是，这个版本的服务中定义了 VideoSvcApi，
 * 这个接口为客户端和服务器端之间的通讯提供了强类型，确保不会发送错误的参数等等。
 * 
 * @author anonymous
 * 
 */

// 告诉 Spring 这个类是一个 Controller，可以从 DispatcherServlet 
// 接收特定的 HTTP 请求。
@Controller
public class VideoSvc implements VideoSvcApi {
	
	// VideoRepository 是一个接口描述，我们将通过这个接口将视频数据进
	// 行存储。我们没有在这里的代码中显性的构造一个 VideoRepository，
	// 但是把它标注成依赖项，需要 Spring 进行注入。在 Application 类
	// 中有一个标注为 @Bean 的方法，这个方法最终决定了具体被注入到这个
	// 成员变量中的对象。
	// 
	// 另外还需要注意的是，我们不需要为 Spring 框架提供 setter 方法来
	// 完成注入工作。 
	//
	@Autowired
	private VideoRepository videos;

	// 接收目标路径是 /video 的 POST 请求，将 HTTP Request Body
	// 中的 JSON 数据转换为对象，然后添加到列表当中。@RequestBody
	// 标注在 Video 参数上，意思是告诉 Spring 将 HTTP Request Body
	// 中的数据按照 JSON 来进行解析，并它转化为一个 Video 对象。
	// @ResponseBody 标注告诉 Spring 将方法执行后的返回值转变为
	// JSON，然后通过 HTTP Response Body 返回给客户端。
	// 
	// VIDEO_SVC_PATH 在 VideoSvcApi 接口中定义为 "/video"。
	// 对于 VideoSvc 来说，用这个常量确保客户端请求和服务的路径总保持
	// 一致。
	// 
	@RequestMapping(value=VideoSvcApi.VIDEO_SVC_PATH, method=RequestMethod.POST)
	public @ResponseBody boolean addVideo(@RequestBody Video v){
		 videos.save(v);
		 return true;
	}
	
	// 接收目标路径是 /video 的 GET 请求，并返回当前内容中的视频列表。
	// 由于在方法的返回值位置标注了 @ResponseBody，Spring 自动的将视
	// 频列表转换为 JSON。
	@RequestMapping(value=VideoSvcApi.VIDEO_SVC_PATH, method=RequestMethod.GET)
	public @ResponseBody Collection<Video> getVideoList(){
		return Lists.newArrayList(videos.findAll());
	}
	
	// 接收目标路径是 /video/find 的 GET 请求，并返回所有匹配 title
	// （例如，Video.name）的视频对象，title 的值为客户端发送来的键值
	// 为 “title” 的查询参数值。
	@RequestMapping(value=VideoSvcApi.VIDEO_TITLE_SEARCH_PATH, method=RequestMethod.GET)
	public @ResponseBody Collection<Video> findByTitle(
			// 告诉 Spring 使用 HTTP Request 查询参数中的 “title” 参数作为
			// 这个 title 方法参数的值。
			@RequestParam(TITLE_PARAMETER) String title
	){
		return videos.findByName(title);
	}

}
