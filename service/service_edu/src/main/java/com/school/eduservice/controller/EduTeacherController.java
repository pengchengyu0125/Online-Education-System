package com.school.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.school.commonutils.R;
import com.school.eduservice.entity.EduTeacher;
import com.school.eduservice.entity.vo.TeacherQuery;
import com.school.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-01-07
 */
@RestController
@RequestMapping("/eduservice/edu-teacher")
public class EduTeacherController {
    //注入service
    @Autowired
    private EduTeacherService teacherService;

    //查询讲师表所有数据
    @GetMapping("findAll")
    public R findAllTeacher() {
        //调用service方法查询所有
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("items", list);
    }

    //逻辑删除讲师
    @DeleteMapping("{id}")
    public R removeTeacher(@PathVariable String id) {
        boolean flag = teacherService.removeById(id);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    /**
     * 分页查询讲师
     *
     * @param current 当前页
     * @param limit   每页记录数
     * @return
     */
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable long current,
                             @PathVariable long limit) {
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
        teacherService.page(pageTeacher, null);
        //总记录数
        long total = pageTeacher.getTotal();
        //数据list集合
        List<EduTeacher> records = pageTeacher.getRecords();
        return R.ok().data("total", total).data("rows", records);
    }

    /**
     * 条件查询
     *
     * @param current      当前页
     * @param limit        每页数据量
     * @param teacherQuery 条件
     * @return
     */
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current,
                                  @PathVariable long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery) {
        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
        //构造条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create", end);
        }
        //调用方法实现条件查询分页
        teacherService.page(pageTeacher, wrapper);
        //总记录数
        long total = pageTeacher.getTotal();
        //数据list集合
        List<EduTeacher> records = pageTeacher.getRecords();
        return R.ok().data("total", total).data("rows", records);
    }

    /**
     * 增加讲师
     *
     * @param eduTeacher 讲师
     * @return
     */
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = teacherService.save(eduTeacher);
        if (save) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    /**
     * 根据讲师id查询
     *
     * @param id 讲师id
     * @return
     */
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id) {
        EduTeacher eduTeacher = teacherService.getById(id);
        return R.ok().data("teacher", eduTeacher);
    }

    /**
     * 更新讲师信息
     * @param eduTeacher 讲师
     * @return
     */
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean flag = teacherService.updateById(eduTeacher);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }
}

