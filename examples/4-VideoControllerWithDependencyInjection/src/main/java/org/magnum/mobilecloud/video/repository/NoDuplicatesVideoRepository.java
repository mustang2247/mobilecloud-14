package org.magnum.mobilecloud.video.repository;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.magnum.mobilecloud.video.controller.Video;

/**
 * VideoRepository 接口的一种实现，不允许存储重复的视频对象。
 * 
 * 当然...NoDuplicatesVideoRepository 中有好多重复的代码可以通过重构的方式放到
 * 基类或者 helper 对象当中。但是这个代码示例的目标是说明什么是依赖注入，而且类的
 * 数量越少越好，所以我们就没有进行重构。
 * 
 * @author anonymous
 *
 */
public class NoDuplicatesVideoRepository implements VideoRepository {

	// Set（集合）类型只存储每个对象的一个实例，如果两个对象是 .equals()
	// 返回真的，那么它会认为是重复实例。
	//
	private Set<Video> videoSet = Collections.newSetFromMap(
	        new ConcurrentHashMap<Video, Boolean>());
	
	@Override
	public boolean addVideo(Video v) {
		return videoSet.add(v);
	}

	@Override
	public Collection<Video> getVideos() {
		return videoSet;
	}

	// 搜索所有与 title 匹配的视频。
	@Override
	public Collection<Video> findByTitle(String title) {
		Set<Video> matches = new HashSet<>();
		for(Video video : videoSet){
			if(video.getName().equals(title)){
				matches.add(video);
			}
		}
		return matches;
	}

}
