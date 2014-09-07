package org.magnum.mobilecloud.controller.test;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.magnum.mobilecloud.video.controller.Video;
import org.magnum.mobilecloud.video.controller.VideoSvc;

/**
 * 
 * 这个测试程序构造了一个 VideoSvc 对象，向它添加一个 Video 对象，然后
 * 调用 getVideoList() 方法检查刚刚添加的 Video 对象是否在返回列表中。
 * 
 * 运行测试项目：在 Eclipse 界面中的测试项目上点击右键，然后选择 
 *   "Run As"->"JUnit Test"
 *   
 */
public class VideoSvcTest {
	
	private VideoSvc videoService = new VideoSvc();

	/**
	 * 创建一个 Video 对象，添加到 VideoSvc 当中，然后调用 getVideoList() 检查
	 * 被添加的视频是否包含在返回列表中。
	 * 
	 * @throws Exception
	 */
	@Test
	public void testVideoAddAndList() throws Exception {
		
		// video 对象信息
		String title = "Java 云计算编程";
		String url = "http://coursera.org/some/video";
		long duration = 60 * 10 * 1000; // 用毫秒表示 10分钟
		Video video = new Video(title, url, duration);

		// 直接测试 Controller，先不走网络。
		boolean ok = videoService.addVideo(video);
		assertTrue(ok);
		
		List<Video> videos = videoService.getVideoList();
		assertTrue(videos.contains(video));
	}

}
