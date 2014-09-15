package org.magnum.mobilecloud.video.repository;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * 定义了一个视频仓库的接口，它可以存储 Video 对象，并且可以
 * 支持按标题进行查找。
 * 
 * @author anonymous
 *
 */
@Repository
public interface VideoRepository extends CrudRepository<Video, Long>{

	// 查询所有与 title （e.g., Video.name）匹配的视频
	public Collection<Video> findByName(String title);
	
}
