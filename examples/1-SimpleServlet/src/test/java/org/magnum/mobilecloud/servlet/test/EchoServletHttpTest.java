package org.magnum.mobilecloud.servlet.test;
import static org.junit.Assert.assertEquals;

import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

/**
 * 这个测试程序将给 EchoServlet 发送 GET 请求。运行这个测试程序之前，要首先启动
 * Servlet（查看 README.md 文件了解如何在 Web Container 中启动 Servlet）。
 * 
 * 运行测试项目：在 Eclipse 界面中的测试项目上点击右键，然后选择 
 *   "Run As"->"JUnit Test"
 */
public class EchoServletHttpTest {

	// 在默认情况下，测试服务器会在 localhost 上运行并监听 8080 端口。如果服务已经运行，
	// 但是测试程序并不能连接到服务，那么你需要看看防火墙（例如：Windows Firewall）是否
	// 阻止了你的访问请求。
	private final String TEST_URL = "http://localhost:8080/1-SimpleServlet/echo";
	
	/**
	 * 这个测试方法将发送带有一个 msg 参数的 GET 请求，
	 * 并检测 Servlet 返回 "Echo:" + msg。
	 * 
	 * @throws Exception
	 */
	@Test
	public void testMsgEchoing() throws Exception {
		// 发送到 EchoServlet 的消息
		String msg = "1234";
		
		// 将参数附加到 URL 的后面，然后 EchoServlet 会将消息发送回来。
		String url = TEST_URL + "?msg=" + msg;
		
		// 发送 HTTP GET 请求到 EchoServer 并将 Response Body 转换成一个字符串。
		URL urlobj = new URL(url);
		String content = IOUtils.toString(urlobj.openStream());
		
		// 检查 HTTP Response Body 中的数据是否满足我们的期望
		// （例如，"Echo:" + msg）
		assertEquals("Echo:"+msg, content);
	}

}
