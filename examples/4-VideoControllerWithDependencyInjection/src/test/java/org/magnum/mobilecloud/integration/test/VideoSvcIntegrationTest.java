package org.magnum.mobilecloud.integration.test;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.magnum.mobilecloud.video.TestData;
import org.magnum.mobilecloud.video.client.VideoSvcApi;
import org.magnum.mobilecloud.video.controller.Application;
import org.magnum.mobilecloud.video.controller.Video;
import org.magnum.mobilecloud.video.controller.VideoSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * 
 * 这个测试希望为你展示一下如何在集成测试中完整的安装并配置一个 Controller（或者你可以安装所有的
 * 控制器）。真实的 Application 类会被用来配置应用程序。Spring 会配置好支撑应用程序运行所需的
 * 所有基础架构。
 * 
 * 测试程序会构造一个 MockMvc 或者叫伪造的 Web 接口，利用这个伪造的网络接口向 Controller 发送
 * 请求然后验证返回结果。与 VideoSvcTest 单元测试不同的是，继承测试需要向 Controller 发送伪造
 * 的 HTTP 请求，而且在发送伪造的 HTTP 请求之前会把测试数据编码成 JSON。 
 * 
 * 在代码中使用了很多标注类型来设置这个继承测试环境。如果你想集成测试其他的 Controller，这些标注
 * 基本上会保持不变。所以你可以基于下面的继承测试代码，进行一点简单的修改就可以测试其他的 Controller：
 * 修改标注为 @Autowired 的 Controller 的类型及变量名，然后把变量传递给 MockMvcBuilder.standaloneSetup(...)。
 * 
 * @author anonymous
 *
 */

// 告诉 Spring 为测试程序配置 Web Container
@WebAppConfiguration
// 告诉 JUnit 使用 Spring 特定的 JUnit Runner
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
// 这里是告诉 Spring 使用哪个 Application 或者 Configuration 对象
@ContextConfiguration(classes = Application.class, loader = SpringApplicationContextLoader.class)
public class VideoSvcIntegrationTest {

	// 让 Spring 自动的构建一个 VideoSvc 对象并注入到测试程序中。
	@Autowired
	private VideoSvc videoService;

	// 这个就是我们的应用程序的伪装接口，用来发送伪造的 HTTP 请求。
	private MockMvc mockMvc;

	@Before
	public void setUp() {
		// 为 VideoSvc 对象配置 Standalone 模式的 Spring 测试环境
		mockMvc = MockMvcBuilders.standaloneSetup(videoService).build();
	}
	
	// 这个测试与 VideoSvcTest.testVideoAddAndList() 功能上是等价的。但核心的区别在
	// 于集成测试的版本会创建整个 Spring 基础设施来支持应用程序的运行，而且会使用 Application 
	// 类中指定的真实的对象来配置应用程序。
	@Test
	public void testVideoAddAndList() throws Exception {
		Video video = TestData.randomVideo();
		String videoJson = TestData.toJson(video);
		
		// 发送一个请求，会调用 VideoSvc.addVideo(Video v)，
		// 并检查请求是否成功
		mockMvc.perform(
				post(VideoSvcApi.VIDEO_SVC_PATH)
				.contentType(MediaType.APPLICATION_JSON)
	            .content(videoJson))
	            .andExpect(status().isOk())
	            .andReturn();
		
		// 发送一个请求，毁掉用 VideoSvc.getVideos()，
		// 并检查上面添加的 Video 对象（JSON 数据）在返回的视频列表中
		mockMvc.perform(
				get(VideoSvcApi.VIDEO_SVC_PATH))
	            .andExpect(status().isOk())
	            .andExpect(content().string(containsString(videoJson)))
	            .andReturn();
	}

}
