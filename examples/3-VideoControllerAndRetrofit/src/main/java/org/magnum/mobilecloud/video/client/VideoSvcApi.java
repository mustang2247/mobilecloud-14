package org.magnum.mobilecloud.video.client;

import java.util.List;

import org.magnum.mobilecloud.video.controller.Video;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * 这个接口定义了 VideoSvc 所包含的 API。接口用来在 client/server
 * 之间定义交互契约。接口的定义中使用 Retrofit 的注释类型进行了标注，
 * 这样客户端就可以自动将接口转化为客户端对象。VideoSvcClientApiTest
 * 中通过代码示例说明如何使用 Retrofit 将这个接口变换为客户端对象。
 * 
 * @author 
 */

public interface VideoSvcApi {
	
	// 我们希望 VideoSvc 对应的路径
	public static final String VIDEO_SVC_PATH = "/video";

	@GET(VIDEO_SVC_PATH)
	public List<Video> getVideoList();
	
	@POST(VIDEO_SVC_PATH)
	public boolean addVideo(@Body Video v);
	
}
