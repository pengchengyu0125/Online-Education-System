package com.school.vod.eduservice.service;

import com.school.vod.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.school.vod.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjavac
 * @since 2021-01-20
 */
public interface EduChapterService extends IService<EduChapter> {

    //获取课程大纲
    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    //删除章节
    boolean deleteChapter(String chapterId);

    //根据课程ID删除章节
    void removeChapterByCourseId(String courseId);
}
