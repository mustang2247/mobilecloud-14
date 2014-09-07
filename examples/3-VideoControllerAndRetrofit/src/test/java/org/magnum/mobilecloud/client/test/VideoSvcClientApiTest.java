package org.magnum.mobilecloud.client.test;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.magnum.mobilecloud.video.client.VideoSvcApi;
import org.magnum.mobilecloud.video.controller.Video;

import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;

/**
 * 
 * 这个测试程序将向 VideoServlet 发送一个 POST 请求来添加一个新的 video，然后发送第二个
 * GET 请求并检查刚刚添加的 video 是否出现在了返回的视频列表当中。
 * 
 * 测试程序运行之前需要首先启动 Servlet（查看 README.md 文件了解如何在 Web Container 中
 * 启动应用程序）
 * 
 * 运行测试项目：在 Eclipse 界面中的测试项目上点击右键，然后选择 
 *   "Run As"->"JUnit Test"
 * 
 * 注意学习这个测试中的代码，测试代码直接调用 VideoSvc 的方法，但本质上一样还是使用 HTTP。
 * 变化在于我们创建了一个 videoService 变量，重点学习如何使用 Retrofit 来大大的简化
 * 客户端与服务的交互！
 */

public class VideoSvcClientApiTest {

	private final String TEST_URL = "http://localhost:8080";

	/**
	 * 这段代码实现了将 VideoSvcApi 转变为对象，然后将 VideoSvcApi 接口中
	 * 定义的方法调用翻译为服务器上的 HTTP 请求。发送的参数数据被编组为 JSON 进行发送，
	 * 返回值对象通过将返回 JSON 数据反编组获得。
	 */
	private VideoSvcApi videoService = new RestAdapter.Builder()
			.setEndpoint(TEST_URL)
			.setLogLevel(LogLevel.FULL)
			.build()
			.create(VideoSvcApi.class);

	/**
	 * 测试程序首先向 VideoSvc 发送一个 POST 请求（addVideo）添加一个新的视频，然后
	 * 发送一个 GET 请求（getVideoList）来检查刚刚添加的新视频是否出现在返回的视频列表当中。
	 * 
	 * @throws Exception
	 */
	@Test
	public void testVideoAddAndList() throws Exception {

		// 视频信息
		String title = "Java 云计算编程";
		String url = "http://www.kaikeba.com/courses/53";
		long duration = 60 * 10 * 1000; // 用毫秒表示十分钟
		Video video = new Video(title, url, duration);
		
		// 使用 Retrofit 向 VideoSvc 发送 POST 请求添加视频数据。注意学习 Retrofit 如何使用
		// 强类型的接口去访问一个支持 HTTP 的视频服务，这样的代码看上去比包含了一大堆的 URL 查询
		// 参数什么的干净许多。
		boolean ok = videoService.addVideo(video);
		assertTrue(ok);
		
		List<Video> videos = videoService.getVideoList();
		assertTrue(videos.contains(video));
	}
}
