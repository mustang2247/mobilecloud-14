package org.magnum.mobilecloud.controller.test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.magnum.mobilecloud.video.TestData;
import org.magnum.mobilecloud.video.controller.VideoSvc;
import org.magnum.mobilecloud.video.repository.Video;
import org.magnum.mobilecloud.video.repository.VideoRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * 
 * 这个测试程序直接调用 VideoSvc 的方法来测试它们。测试程序使用了 Mockito 类库，该类库
 * 可以将一个伪装的 VideoRepository 依赖注入到 VideoSvc 对象里面。
 * 
 * 运行测试项目：在 Eclipse 界面中的测试项目上点击右键，然后选择 
 *   "Run As"->"JUnit Test"
 * 
 * 注意学习这个测试中的代码，使用 HTTP 进行测试和还是直接调用 VideoSvc 的方法进行测试，
 * 测试的代码本质上是一致的。不同的地只是如何初始化 videoService 变量。当让，测试程序
 * 可以被更好的重构，消除代码的重复...但是本测试程序的目的是展示 Retrofit 如何简化了与
 * 服务程序的交互！
 * 
 * @author anonymous
 *
 */
public class VideoSvcTest {

	// 这个标注类型告诉 Mockito 创建一个实现了 VideoRepository 接口的伪装对象，
	// 这个伪装对象将被用于测试 VideoSvc 对象。伪装对象是对接口的假实现，我们可以
	// 使用脚本来为不同的输入指定特定的输出。
	@Mock
	private VideoRepository videoRepository;

	// 自动将伪装的 VideoRepository 注入到 VideoSvc 对象当中。
	@InjectMocks
	private VideoSvc videoService;

	private Video video = TestData.randomVideo();

	@Before
	public void setUp() {
		// 处理 @Mock 标注，并将伪装的 VideoRepository 注入到 VideoSvc 对象。
		MockitoAnnotations.initMocks(this);

		// 告诉伪装的 VideoRepository 对象，当它的 findAll() 方法被调用时，
		// 总是返回在前面的代码中创建的随机 Video 对象。
		when(videoRepository.findAll()).thenReturn(Arrays.asList(video));
	}
	
	
	// 这个测试程序的代码很少，因为 VideoSvc 的功能实际上代理（delegate）
	// 给了 VideoReposity。我们只是想为大家提供一个简单的例子来学习如何使用
	// 伪装对象和依赖注入进行 Controller 的测试。
	@Test
	public void testVideoAddAndList() throws Exception {

		// 确保 addVideo 可以正常调用
		boolean ok = videoService.addVideo(video);
		assertTrue(ok);

		// 确保被添加的 Video 对象在返回的视频列表中。
		Collection<Video> videos = videoService.getVideoList();
		assertTrue(videos.contains(video));
	}

}
