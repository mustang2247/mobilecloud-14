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

/**
 *                             请不要修改这个类
 *                       
                    ___                    ___           ___                            
     _____         /\  \                  /\  \         /\  \                           
    /::\  \       /::\  \                 \:\  \       /::\  \         ___              
   /:/\:\  \     /:/\:\  \                 \:\  \     /:/\:\  \       /\__\             
  /:/  \:\__\   /:/  \:\  \            _____\:\  \   /:/  \:\  \     /:/  /             
 /:/__/ \:|__| /:/__/ \:\__\          /::::::::\__\ /:/__/ \:\__\   /:/__/              
 \:\  \ /:/  / \:\  \ /:/  /          \:\~~\~~\/__/ \:\  \ /:/  /  /::\  \              
  \:\  /:/  /   \:\  /:/  /            \:\  \        \:\  /:/  /  /:/\:\  \             
   \:\/:/  /     \:\/:/  /              \:\  \        \:\/:/  /   \/__\:\  \            
    \::/  /       \::/  /                \:\__\        \::/  /         \:\__\           
     \/__/         \/__/                  \/__/         \/__/           \/__/           
      ___           ___                                     ___                         
     /\  \         /\  \         _____                     /\__\                        
    |::\  \       /::\  \       /::\  \       ___         /:/ _/_         ___           
    |:|:\  \     /:/\:\  \     /:/\:\  \     /\__\       /:/ /\__\       /|  |          
  __|:|\:\  \   /:/  \:\  \   /:/  \:\__\   /:/__/      /:/ /:/  /      |:|  |          
 /::::|_\:\__\ /:/__/ \:\__\ /:/__/ \:|__| /::\  \     /:/_/:/  /       |:|  |          
 \:\~~\  \/__/ \:\  \ /:/  / \:\  \ /:/  / \/\:\  \__  \:\/:/  /      __|:|__|          
  \:\  \        \:\  /:/  /   \:\  /:/  /   ~~\:\/\__\  \::/__/      /::::\  \          
   \:\  \        \:\/:/  /     \:\/:/  /       \::/  /   \:\  \      ~~~~\:\  \         
    \:\__\        \::/  /       \::/  /        /:/  /     \:\__\          \:\__\        
     \/__/         \/__/         \/__/         \/__/       \/__/           \/__/        
 */
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import io.magnum.autograder.junit.Rubric;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.magnum.dataup.model.Video;
import org.magnum.dataup.model.VideoStatus;
import org.magnum.dataup.model.VideoStatus.VideoState;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

public class AutoGradingTest {

	private static final String SERVER = "http://localhost:8080";

	private File testVideoData = new File(
			"src/test/resources/test.m4v");
	
	private Video video = Video.create().withContentType("video/m4v")
			.withDuration(123).withSubject(UUID.randomUUID().toString())
			.withTitle(UUID.randomUUID().toString()).build();

	private VideoSvcApi videoSvc = new RestAdapter.Builder()
			.setEndpoint(SERVER).build()
			.create(VideoSvcApi.class);

	@Rubric(
			value="Video 对象可以被正确保存", 
			goal="这个测评的目标是检查你编写的 Spring 控制器是否可以正确的将接收到的数据反串列化为 "
			        + "Video 对象，并检查用于添加视频的 HTTP API 是否可以正确的运行。测试程序检查"
					+ "你的代码可以正确接收 Request Body 中的 application/json 数据，并将客户端"
			        + "设置的所有视频属性保存下来。测试程序还要检查服务器能够生成 ID 和上传视频数据"
					+ "的 URL。",
			points=20.0,
			reference="这个测试的学习视频资料包括："
					+ "http://learn.kaikeba.com/courses/840/modules/items/45326 "
					+ "http://learn.kaikeba.com/courses/840/modules/items/45329 "
					+ "http://learn.kaikeba.com/courses/840/modules/items/45331"
			)
	@Test
	public void testAddVideoMetadata() throws Exception {
		Video received = videoSvc.addVideo(video);
		assertEquals(video.getTitle(), received.getTitle());
		assertEquals(video.getDuration(), received.getDuration());
		assertEquals(video.getContentType(), received.getContentType());
		assertEquals(video.getLocation(), received.getLocation());
		assertEquals(video.getSubject(), received.getSubject());
		assertTrue(received.getId() > 0);
		assertTrue(received.getDataUrl() != null);
	}
	
	@Rubric(
			value="在执行了一个添加命令后视频列表能够正确更新",
			goal="目标是测评你的 Spring 控制器可以向视频列表中添加视频，并存储到服务器的内存当中。"
			        + "这个测试还会测试服务器是否能够正确的返回 JSON 格式的视频列表数据。",
			points=20.0,
			reference="这个测试的学习视频资料包括："
					+ "http://learn.kaikeba.com/courses/840/modules/items/45326 "
					+ "http://learn.kaikeba.com/courses/840/modules/items/45329 "
					+ "http://learn.kaikeba.com/courses/840/modules/items/45331"
			)
	@Test
	public void testAddGetVideo() throws Exception {
		videoSvc.addVideo(video);
		Collection<Video> stored = videoSvc.getVideoList();
		assertTrue(stored.contains(video));
	}
	
	@Rubric(
			value="能够为某个视频上传 MPEG 视频数据",
			goal="目标是测评你的 Spring 控制器可以允许为某个视频对象提交一个 MPEG 视频数据。这个测"
			        + "试还会检查控制器可以将视频数据返回给客户端，并验证上传的数据与返回的数据是否"
					+ "一致。",
			points=20.0,
			reference="这个测试的学习视频资料包括："
					+ "http://learn.kaikeba.com/courses/840/modules/items/45330 "
					+ "http://learn.kaikeba.com/courses/840/modules/items/45328 "
					+ "http://learn.kaikeba.com/courses/840/modules/items/45331 "
					+ "http://learn.kaikeba.com/courses/840/modules/items/45318"
			)
	@Test
	public void testAddVideoData() throws Exception {
		Video received = videoSvc.addVideo(video);
		VideoStatus status = videoSvc.setVideoData(received.getId(),
				new TypedFile(received.getContentType(), testVideoData));
		assertEquals(VideoState.READY, status.getState());
		
		Response response = videoSvc.getData(received.getId());
		assertEquals(200, response.getStatus());
		
		InputStream videoData = response.getBody().in();
		byte[] originalFile = IOUtils.toByteArray(new FileInputStream(testVideoData));
		byte[] retrievedFile = IOUtils.toByteArray(videoData);
		assertTrue(Arrays.equals(originalFile, retrievedFile));
	}
	
	@Rubric(
			value="当请求不存在的视频数据时返回 404 错误",
			goal="目标是测评你的 Spring 控制器是否能够正确的向客户端返回 404 错误。当客户端请求一个"
			        + "不存在的视频对象的视频数据时，服务器能够返回一个 404 错误。",
			points=10.0,
			reference="这个测试的学习视频资料包括："
					+ "http://learn.kaikeba.com/courses/840/modules/items/45328 "
					+ "http://learn.kaikeba.com/courses/840/modules/items/45318"
			)
	@Test
	public void testGetNonExistantVideosData() throws Exception {
		
		long nonExistantId = getInvalidVideoId();
		
		try{
			Response r = videoSvc.getData(nonExistantId);
			assertEquals(404, r.getStatus());
		}catch(RetrofitError e){
			assertEquals(404, e.getResponse().getStatus());
		}
	}
	
	@Rubric(
			value="向不存在的视频对象提交视频数据时会生成 404 错误",
			goal="目标是测评你的 Spring 控制器可以在客户端试图向一个不存在的视频对象提交视频数据"
			        + "时生成一个 404 错误。",
			points=10.0,
			reference="这个测试的学习视频资料包括："
			        + "http://learn.kaikeba.com/courses/840/modules/items/45318 "
					+ "http://learn.kaikeba.com/courses/840/modules/items/45330 "
					+ "http://learn.kaikeba.com/courses/840/modules/items/45328"
			)
	@Test
	public void testAddNonExistantVideosData() throws Exception {
		long nonExistantId = getInvalidVideoId();
		try{
			videoSvc.setVideoData(nonExistantId, new TypedFile(video.getContentType(), testVideoData));
			fail("如果在 setVideoData() 方法中提供一个无效的视频 ID，客户端应当接收到一个 404 "
					+ "错误并抛出异常。");
		}catch(RetrofitError e){
			assertEquals(404, e.getResponse().getStatus());
		}
	}

	private long getInvalidVideoId() {
		Set<Long> ids = new HashSet<Long>();
		Collection<Video> stored = videoSvc.getVideoList();
		for(Video v : stored){
			ids.add(v.getId());
		}
		
		long nonExistantId = Long.MIN_VALUE;
		while(ids.contains(nonExistantId)){
			nonExistantId++;
		}
		return nonExistantId;
	}

}
