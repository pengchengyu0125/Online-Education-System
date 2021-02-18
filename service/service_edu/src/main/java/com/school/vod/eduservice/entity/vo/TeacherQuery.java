package com.school.vod.eduservice.entity.vo;

import lombok.Data;

@Data
public class TeacherQuery {
    //教师名称
    private String name;
    //头衔
    private Integer level;
    //查询开始时间
    private String begin;
    //查询结束时间
    private String end;
}
