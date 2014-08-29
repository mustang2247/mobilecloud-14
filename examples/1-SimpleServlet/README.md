## 如何运行 EchoServlet ##

运行 Servlet 的步骤：

在 Eclipse 中右键点击 build.gradle 文件，Gradle->Quick Tasks Launcher，然后输入
"JettyRun" 并运行指令。

停止 Servlet 的步骤：

在 Eclipse 中右键点击 build.gradle 文件，Gradle->Quick Tasks Launcher，然后输入
"JettyStop" 并运行指令。

## 如何访问 Servlet 中的服务

启动 EchoServlet 之后，可以通过浏览器与它交互：

http://localhost:8080/1-SimpleServlet/echo?msg=test1234

更改 URL查询参数 "msg" 可以发送不同的值给 EchoServlet 并返回。

## 注意事项

1. 仔细看一下 src/main/webapp/WEB-INF/web.xml 中的 web.xml 文件，理解将 HTTP 请求
   路由到 EchoServlet 的具体规则是如何被设置的。
2. 注意观察 EchoServlet 是如何从请求当中获取 "msg" 参数的。
3. 注意观察 EchoServlet 明确的在 response 中设置 content-type，以便客户端能够知道
   如何解释包含在 Response Body 中的数据。如果你修改一下 content-type，它会影响浏览
   器如何显示返回结果。
4. 阅读 EchoServletHttpTest 的示例代码，学习如何通过程序向 Servlet 发送 HTTP GET 请求。

## 安全相关的问题

虽然这个 Servlet 没有存储任何客户端发送来的数据，但它潜在被注入攻击（Injection Attack）
的风险。

查看视频课件中“了解SQL注入式攻击（SQL Injection）”的讨论，理解为什么 EchoServlet 会存在
注入式攻击的风险： 

[URL]