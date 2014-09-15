package org.magnum.mobilecloud.video.client;

import java.util.Collection;

import org.magnum.mobilecloud.video.repository.Video;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * 这个接口定义了 VideoSvc 所包含的 API。接口用来在 client/server
 * 之间定义交互契约。接口的定义中使用 Retrofit 的注释类型进行了标注，
 * 这样客户端就可以自动将接口转化为客户端对象。VideoSvcClientApiTest
 * 中通过代码示例说明如何使用 Retrofit 将这个接口变换为客户端对象。
 * 
 * @author anonymous
 */

public interface VideoSvcApi {
	
	public static final String TITLE_PARAMETER = "title";

	// 我们希望 VideoSvc 对应的运行路径
	public static final String VIDEO_SVC_PATH = "/video";

	// 根据视频标题（title）进行搜索的路径
	public static final String VIDEO_TITLE_SEARCH_PATH = VIDEO_SVC_PATH + "/find";

	@GET(VIDEO_SVC_PATH)
	public Collection<Video> getVideoList();
	
	@POST(VIDEO_SVC_PATH)
	public boolean addVideo(@Body Video v);
	
	@GET(VIDEO_TITLE_SEARCH_PATH)
	public Collection<Video> findByTitle(@Query(TITLE_PARAMETER) String title);
	
}
