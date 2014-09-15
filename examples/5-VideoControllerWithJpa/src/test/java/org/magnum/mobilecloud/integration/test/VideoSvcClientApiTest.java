package org.magnum.mobilecloud.integration.test;

import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Test;
import org.magnum.mobilecloud.video.TestData;
import org.magnum.mobilecloud.video.client.VideoSvcApi;
import org.magnum.mobilecloud.video.repository.Video;

import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;

/**
 * 
 * 这个集成测试首先向 VideoSvc 发送一个 POST 请求，添加一个新的视频对象；然后再发送一个 GET
 * 请求，检查再返回的视频列表中是否包含刚刚添加的视频。这个测试将使用实际的 HTTP 网络通信。
 * 
 * 这个测试需要先运行 VideoSvc 服务（查看 README.md 了解如何启动 Application）。 
 * 
 * 运行测试程序的方法是，在 Eclipse 中右键点击这个文件，然后选择：
 * “Run As”->“JUnit Test”。
 * 
 * 注意学习这个测试中的代码，使用 HTTP 进行测试和还是直接调用 VideoSvc 的方法进行测试，
 * 测试的代码本质上是一致的。不同的地只是如何初始化 videoService 变量。当让，测试程序
 * 可以被更好的重构，消除代码的重复...但是本测试程序的目的是展示 Retrofit 如何简化了与
 * 服务程序的交互！
 * 
 * @author anonymous
 *
 */
public class VideoSvcClientApiTest {

	private final String TEST_URL = "http://localhost:8080";

	private VideoSvcApi videoService = new RestAdapter.Builder()
			.setEndpoint(TEST_URL).setLogLevel(LogLevel.FULL).build()
			.create(VideoSvcApi.class);

	private Video video = TestData.randomVideo();
	
	/**
	 * 这个测试方法将创建一个 Video 对象，将这个 Video 对象添加到 VideoSvc，然后再
	 * 检查这个 Video 对象是否包含在调用 getVideoList() 方法时返回的视频列表中。 
	 * 
	 * @throws Exception
	 */
	@Test
	public void testVideoAddAndList() throws Exception {
		
		// 添加 Video 对象。
		boolean ok = videoService.addVideo(video);
		assertTrue(ok);

		// 应该能够获得在上面刚刚添加的视频对象。
		Collection<Video> videos = videoService.getVideoList();
		assertTrue(videos.contains(video));
	}

}
