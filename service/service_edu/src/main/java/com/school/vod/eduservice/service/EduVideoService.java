package com.school.vod.eduservice.service;

import com.school.vod.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-01-20
 */
public interface EduVideoService extends IService<EduVideo> {

    //删除小节
    void removeVideoByCourseId(String courseId);
}
