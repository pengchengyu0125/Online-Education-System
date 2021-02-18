package com.school.vod.eduservice.service;

import com.school.vod.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.school.vod.eduservice.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-01-19
 */
public interface EduSubjectService extends IService<EduSubject> {

    //添加课程分类
    void saveSubject(MultipartFile file, EduSubjectService subjectService);

    //课程列表
    List<OneSubject> getAllSubject();
}
