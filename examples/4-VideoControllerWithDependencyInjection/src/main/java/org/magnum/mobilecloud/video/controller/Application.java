package org.magnum.mobilecloud.video.controller;

import org.magnum.mobilecloud.video.repository.NoDuplicatesVideoRepository;
import org.magnum.mobilecloud.video.repository.VideoRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

// 告诉 Spring 框架这个对象是一个应用程序的 Configuration 对象
@Configuration

// 告诉 Spring 框架打开 WebMVC（例如，他会启动一个 DispatcherServlet，
// 让请求可以被路由到我们编写的各种 Controller 当中）
@EnableWebMvc

// 告诉 Spring 框架在 controller package 中（和所有的子程序包）扫描所有的 Controller
// 和应用程序的其他的组件。Spring 将自动发现程序包中所有用 @Controller 标注的类，并将
// 它们连接到 DispatcherServlet。
@ComponentScan("org.magnum.mobilecloud.video.controller")

// 告诉 Spring 框架自动的依赖注入所有在程序中用 @Autowired 标注的类。
@EnableAutoConfiguration
public class Application {

	// 告诉 Spring 框架启动应用！
	public static void main(String[] args){
		SpringApplication.run(Application.class, args);
	}
	
	// 我们需要告诉 Spring 应当使用接口 VideoRepository 的哪种实现。Spring 会自动的
	// 将我们返回的对象注入到 VideoSvc 中标注了 @Autowired 的 videos 成员变量中。
	@Bean
	public VideoRepository videoRepository(){
		return new NoDuplicatesVideoRepository();
	}
}
