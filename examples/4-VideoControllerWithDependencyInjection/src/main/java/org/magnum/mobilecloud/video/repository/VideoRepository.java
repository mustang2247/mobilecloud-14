package org.magnum.mobilecloud.video.repository;

import java.util.Collection;

import org.magnum.mobilecloud.video.controller.Video;

/**
 * 定义了一个视频仓库的接口，它可以存储 Video 对象，并且可以
 * 支持按标题进行查找。
 * 
 * @author anonymous
 *
 */
public interface VideoRepository {

	// 添加一个视频
	public boolean addVideo(Video v);
	
	// 获取到目前为止已经添加了的所有视频
	public Collection<Video> getVideos();
	
	// 查询所有与 title （e.g., Video.name）匹配的视频
	public Collection<Video> findByTitle(String title);
	
}
