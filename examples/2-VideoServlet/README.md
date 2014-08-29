## 如何运行 VideoServlet

运行 Servlet 的步骤：

在 Eclipse 中右键点击 build.gradle 文件，Gradle->Quick Tasks Launcher，然后输入
"JettyRun" 并运行指令。

停止 Servlet 的步骤：

在 Eclipse 中右键点击 build.gradle 文件，Gradle->Quick Tasks Launcher，然后输入
"JettyStop" 并运行指令。

## 如何访问 Servlet 中的服务

启动 Servlet 之后，打开浏览器并输入下面的 URL 会看到已经添加进去的视频列表：

http://localhost:8080/2-VideoServlet/video

为了添加一个视频并在列表中显示出来，运行 VideoServletHttpTest JUnit 测试代码，然后刷新浏览器。


## 代码演练视频

代码分析视频请参看：
［URL］