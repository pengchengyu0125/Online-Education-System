package com.school.vod.eduservice.service.impl;

import com.school.vod.eduservice.entity.EduCourse;
import com.school.vod.eduservice.entity.EduCourseDescription;
import com.school.vod.eduservice.entity.vo.CourseInfoVo;
import com.school.vod.eduservice.entity.vo.CoursePublishVo;
import com.school.vod.eduservice.mapper.EduCourseMapper;
import com.school.vod.eduservice.service.EduChapterService;
import com.school.vod.eduservice.service.EduCourseDescriptionService;
import com.school.vod.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.school.vod.eduservice.service.EduVideoService;
import com.school.servicebase.exceptionhandler.MyException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-01-20
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService courseDescriptionService;

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private EduChapterService chapterService;

    /**
     * 添加课程基本信息
     * @param courseInfoVo 课程基本信息
     * @return
     */
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        //添加课程基本信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if (insert <= 0) {
            throw new MyException(20001, "添加课程信息失败");
        }
        //获取课程ID
        String cid = eduCourse.getId();
        //添加课程简介信息
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescription.setId(cid);
        courseDescriptionService.save(courseDescription);
        return cid;
    }

    /**
     * 查询课程基本信息
     * @param courseId 课程ID
     * @return
     */
    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        //查询课程
        EduCourse eduCourse = baseMapper.selectById(courseId);
        //查询课程描述
        EduCourseDescription courseDescription = courseDescriptionService.getById(courseId);

        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse, courseInfoVo);
        courseInfoVo.setDescription(courseDescription.getDescription());
        return courseInfoVo;
    }

    /**
     * 修改课程信息
     * @param courseInfoVo 课程信息
     */
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if (update == 0){
            throw new MyException(20001,"修改课程信息失败");
        }
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo, eduCourseDescription);
        courseDescriptionService.updateById(eduCourseDescription);
    }

    /**
     * 获取课程确认信息
     * @param id 课程ID
     * @return
     */
    @Override
    public CoursePublishVo publishCourseInfo(String id) {
        CoursePublishVo publishVo = baseMapper.getPublishCourseInfo(id);
        return publishVo;
    }

    /**
     * 删除课程
     * @param courseId 课程ID
     */
    @Override
    public void removeCourse(String courseId) {
        eduVideoService.removeVideoByCourseId(courseId);
        chapterService.removeChapterByCourseId(courseId);
        courseDescriptionService.removeById(courseId);
        int result = baseMapper.deleteById(courseId);
        if (result == 0){
            throw new MyException(20001,"删除课程失败");
        }
    }
}
