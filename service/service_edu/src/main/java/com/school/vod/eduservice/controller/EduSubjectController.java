package com.school.vod.eduservice.controller;


import com.school.vod.commonutils.R;
import com.school.vod.eduservice.entity.subject.OneSubject;
import com.school.vod.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-01-19
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {
    @Autowired
    private EduSubjectService subjectService;

    /**
     * 添加课程分类
     *
     * @return
     */
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file) {
        subjectService.saveSubject(file, subjectService);
        return R.ok();
    }

    /**
     * 获取课程分类列表
     * @return
     */
    @GetMapping("getAllSubject")
    public R getAllSubject() {
        List<OneSubject> list = subjectService.getAllSubject();
        return R.ok().data("list", list);
    }
}

