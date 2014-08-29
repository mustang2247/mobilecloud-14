package org.magnum.mobilecloud.servlet.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;
import org.magnum.mobilecloud.video.servlet.VideoServlet;


/**
 * 
 * 这个测试程序将向 VideoServlet 发送一个 POST 请求来添加一个新的 video，然后发送第二个
 * GET 请求并检查刚刚添加的 video 是否出现在了返回的视频列表当中。
 * 
 * 测试程序运行之前需要首先启动 Servlet（查看 README.md 文件了解如何在 Web Container 中
 * 启动 Servlet）
 * 
 * 测试程序使用了 Apache HttpClient，它是 HTTP Components 项目的一部分。如何使用该组件
 * 并没有包含在视频课程当中。虽然我们没有详细的讲解如何使用这些底层的类库，如果你比较熟悉 HTTP 
 * 的概念并阅读相应的类库文档，这些代码会很容易理解。在视频当中我们会更专注在如何使用更便捷而且
 * 安全的 Retrofit 客户端。但是目前我们还没有进入到 Retrofit 和 JSON 的视频课程，所以我
 * 们先使用HTTPClient。 
 * 
 * 运行测试项目：在 Eclipse 界面中的测试项目上点击右键，然后选择 
 *   "Run As"->"JUnit Test"
 */
public class VideoServletHttpTest {

	private final String TEST_URL = "http://localhost:8080/2-VideoServlet/video";

	// HTTP Client 实际上类似一个模拟的浏览器，用它可以给 VideoServlet 发送请求。
	private final CloseableHttpClient httpClient = HttpClients.createDefault();
	
	/**
	 * 这个测试方法将首先给 VideoServlet 发送一个 POST 请求来添加一个新的 video，然后发送第
	 * 二个 GET 请求来检查视频是否出现在返回的视频列表当中。
	 * 
	 * 这个方法比较长，是为了给你展示与 VideoServlet 交互的全过程。
	 * 
	 * @throws Exception
	 */
	@Test
	public void testVideoAddAndList() throws Exception {
		// 构造 video 对象的数据信息
		// 创建一个随机的 String 作为视频的标题，以确保每次运行这个测试方法都能正确的添加视频。
		String myRandomID = UUID.randomUUID().toString();
		String title = "Video - " + myRandomID;
		String videoUrl = "http://coursera.org/some/video-"+myRandomID;
		long duration = 60 * 10 * 1000; // 用毫秒表示 10 分钟
		
		// 创建将 video 发送给服务器的 HTTP POST 请求。
		HttpPost post = createVideoPostRequest(title, videoUrl, duration);
		
		// 使用 HttpClient 发送 POST 请求并获取从服务器返回的 HTTP Response。
		HttpResponse response = httpClient.execute(post);
		
		// 检查 Response Code 是否是 200（HTTP 200 OK）
		assertEquals(200, response.getStatusLine().getStatusCode());

		// 获取 HTTP Response 中 Body 数据
		String responseBody = extractResponseBody(response);
		
		// 确保返回的数据符合我们的期望。在测试程序中再定义一遍期待的返回数据，不如直接引用 VideoServlet
		// 中定义的 public final 变量，这样的话会避免测试程序中的定义与 Servlet 中的定义不一致。
		assertEquals(VideoServlet.VIDEO_ADDED, responseBody);
		
		// 代码执行到这里，我们已经向服务器发送（POST）了一个 video，现在需要构建一个 HTTP GET 
		// 请求从 VideoServlet 抓去视频列表。
		HttpGet getVideoList = new HttpGet(TEST_URL);
		// 执行 GET 请求并获取服务器返回的 HTTP Response
		HttpResponse listResponse = httpClient.execute(getVideoList);
		
		// 检查返回的 Response Code 是否是 HTTP 200 OK
		assertEquals(200, listResponse.getStatusLine().getStatusCode());
		
		// 从 HTTP Response 中获取 Body 数据
		String receivedVideoListData = extractResponseBody(listResponse);
		
		// 构建一个字符串，这个字符串代表期望在 HTTP Response 中看到的视频数据。
		String expectedVideoEntry = title + " : " + videoUrl + "\n";
		
		// 通过在 Response Body 的文本数据中搜索 expectedVideoEntry 检查我们的视频是否存在于视频列表中。
		assertTrue(receivedVideoListData.contains(expectedVideoEntry));
	}
	
	/**
	 * 这个测试方法向 VideoServet 发送一个 POST 请求，将 "name" 参数设定为空字符串。
	 * 请求发送后会导致 VideoServlet 返回一个 400 错误（HTTP Bad Request）。
	 * 
	 * @throws Exception
	 */
	@Test
	public void testMissingRequestParameter() throws Exception {
		// 构造 video 对象的数据信息
		// 创建一个随机的 String 作为视频的标题，以确保每次运行这个测试方法都能正确的添加视频。
		
		// 我们有意的将 title 为空字符串，确保 VideoServlet 会产生一个值为 400 的 Response Code。 
		String title = "";
		String myRandomID = UUID.randomUUID().toString();
		String videoUrl = "http://coursera.org/some/video-"+myRandomID;
		long duration = 60 * 10 * 1000; // 用毫秒表示 10 分钟
		
		// 创建将 video 发送给服务器的 HTTP POST 请求。
		HttpPost post = createVideoPostRequest(title, videoUrl, duration);
		
		// 使用 HttpClient 发送 POST 请求并获取从服务器返回的 HTTP Response。
		HttpResponse response = httpClient.execute(post);
		
		// VideoServlet 应当生成一个值为 400 的错误（HTTP Bad Request）。
		assertEquals(400, response.getStatusLine().getStatusCode());
	}

	/*
	 * 这个方法用来抓去 HTTP Response Body 中的数据。 
	 */
	private String extractResponseBody(HttpResponse response)
			throws IOException {
		return IOUtils.toString(response.getEntity().getContent());
	}

	/*
	 * 这个方法用来构建一个可以被发送给 VideoServlet 的 POST 请求。
	 * 
	 * @param title
	 * @param videoUrl
	 * @param duration
	 * @return
	 */
	private HttpPost createVideoPostRequest(String title, String videoUrl,
			long duration) {
		// 使用 Apache HttpClient 的类库创建一个 POST 请求对象。
		HttpPost post = new HttpPost(TEST_URL);
		
		// 用键值对组装发送给服务器的 Video 数据。
		List<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("name", title));
		params.add(new BasicNameValuePair("url", videoUrl));
		params.add(new BasicNameValuePair("duration", "" + duration));
		
		// 将请求数据进行 URL Encoded 编码，并将编码好的数据放入 Request body。
		UrlEncodedFormEntity requestBody = new UrlEncodedFormEntity(params, Consts.UTF_8);
		
		// 将编码（URL Encoded）之后的 Video 数据附着到请求中。
		post.setEntity(requestBody);
		return post;
	}

}
