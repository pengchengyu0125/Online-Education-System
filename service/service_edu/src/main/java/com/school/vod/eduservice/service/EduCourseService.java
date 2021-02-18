package com.school.vod.eduservice.service;

import com.school.vod.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.school.vod.eduservice.entity.vo.CourseInfoVo;
import com.school.vod.eduservice.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-01-20
 */
public interface EduCourseService extends IService<EduCourse> {

    //添加课程基本信息
    String saveCourseInfo(CourseInfoVo courseInfoVo);

    //查询课程信息
    CourseInfoVo getCourseInfo(String courseId);

    //修改课程信息
    void updateCourseInfo(CourseInfoVo courseInfoVo);

    //获取课程确认信息
    CoursePublishVo publishCourseInfo(String id);

    //删除课程
    void removeCourse(String courseId);
}
