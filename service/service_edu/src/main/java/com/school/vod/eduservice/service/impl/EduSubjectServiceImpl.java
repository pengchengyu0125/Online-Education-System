package com.school.vod.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.school.vod.eduservice.entity.EduSubject;
import com.school.vod.eduservice.entity.excel.SubjectData;
import com.school.vod.eduservice.entity.subject.OneSubject;
import com.school.vod.eduservice.entity.subject.TwoSubject;
import com.school.vod.eduservice.listener.SubjectExcelListener;
import com.school.vod.eduservice.mapper.EduSubjectMapper;
import com.school.vod.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-01-19
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    /**
     * 添加课程分类
     * @param file excel文件
     * @param subjectService
     */
    @Override
    public void saveSubject(MultipartFile file, EduSubjectService subjectService) {
        try {
            InputStream in = file.getInputStream();
            EasyExcel.read(in, SubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 课程列表
     * @return
     */
    @Override
    public List<OneSubject> getAllSubject() {
        //查询一级分类
        QueryWrapper<EduSubject> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("parent_id", "0");
        List<EduSubject> list1 = baseMapper.selectList(wrapper1);
        //查询二级分类
        QueryWrapper<EduSubject> wrapper2 = new QueryWrapper<>();
        wrapper2.ne("parent_id", "0");
        List<EduSubject> list2 = baseMapper.selectList(wrapper2);

        List<OneSubject> list = new ArrayList<>();
        for (int i = 0; i < list1.size(); i++) {
            OneSubject oneSubject = new OneSubject();
            BeanUtils.copyProperties(list1.get(i),oneSubject);
            List<TwoSubject> children = new ArrayList<>();
            for (int j = 0; j < list2.size(); j++) {
                if (list2.get(j).getParentId().equals(oneSubject.getId())){
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(list2.get(j),twoSubject);
                    children.add(twoSubject);
                }
            }
            oneSubject.setChildren(children);
            list.add(oneSubject);
        }
        return list;
    }
}
