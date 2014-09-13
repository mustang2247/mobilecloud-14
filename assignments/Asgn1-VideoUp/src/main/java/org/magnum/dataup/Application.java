/*
 * 
 * 版权所有 2014 []
 * 
 * 根据 2.0 版本Apache许可证（"许可证"）授权；根据本许可证，用户可以不使用此文件。
 * 用户可从下列网址获得许可证副本：
 *   
 *   http://www.apache.org/licenses/LICENSE-2.0
 *   
 * 除非因适用法律需要或书面同意，根据许可证分发的软件是基于"按原样"基础提供，无任何
 * 明示的或暗示的保证或条件。详见根据许可证许可下，特定语言的管辖权限和限制。
 */

package org.magnum.dataup;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.MultiPartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

// 这个标注告诉 Spring auto-wire 你的应用程序
@EnableAutoConfiguration

// 这个标注告诉 Spring 从当前程序包开始遍历所有的 Controller，还有其他的标注信息。
@ComponentScan

// 这个标注告诉 Spring 这个类有配置信息。
@Configuration
public class Application {

	private static final String MAX_REQUEST_SIZE = "150MB";

	// 应用程序入口
	public static void main(String[] args) {

		// 这个方法调用会启动应用程序并且使用 LocalApplication 中的
		// 配置信息来设置应用程序的各种组件。
		SpringApplication.run(Application.class, args);
	}

	// 这个配置模块可以让 Web Container 有能力接受 multipart 请求。
	@Bean
    public MultipartConfigElement multipartConfigElement() {

		// 设置应用程序容器可以接受 multipart 请求。
		final MultiPartConfigFactory factory = new MultiPartConfigFactory();

		// 设置请求数据大小的上限，避免客户端不会发送过大的请求滥用服务。
		factory.setMaxFileSize(MAX_REQUEST_SIZE);
		factory.setMaxRequestSize(MAX_REQUEST_SIZE);

		// 返回配置信息，设置容器的 multipart 相关参数。
		return factory.createMultipartConfig();
	}

}
