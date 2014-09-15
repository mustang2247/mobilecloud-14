package org.magnum.mobilecloud.video;

import org.magnum.mobilecloud.video.repository.VideoRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

// 告诉 Spring 框架自动的依赖注入所有在程序中用 @Autowired 标注的类。
@EnableAutoConfiguration

// 告诉 Spring 自动为 VideoRepository 创建 JPA 实现。
@EnableJpaRepositories(basePackageClasses = VideoRepository.class)

// 告诉 Spring 框架这个对象是一个应用程序的 Configuration 对象
@Configuration

// 告诉 Spring 框架打开 WebMVC（例如，他会启动一个 DispatcherServlet，
// 让请求可以被路由到我们编写的各种 Controller 当中）
@EnableWebMvc

// 告诉 Spring 框架扫描所有程序包（和所有的子程序包）中的 Controller
// 和应用程序的其他的组件。Spring 将自动发现程序包中所有用 @Controller 
// 标注的类，并将它们连接到 DispatcherServlet。
@ComponentScan
public class Application {
	
	// 告诉 Spring 框架启动应用！
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
}
