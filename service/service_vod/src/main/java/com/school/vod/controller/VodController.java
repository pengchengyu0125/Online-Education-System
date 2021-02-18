package com.school.vod.controller;

import com.school.vod.commonutils.R;
import com.school.vod.service.Vodservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduvod/video")
public class VodController {
    @Autowired
    private Vodservice vodservice;

    /**
     * 上传视频
     * @param file 视频文件
     * @return
     */
    @PostMapping("uploadAlyiVideo")
    public R uploadAlyiVideo(MultipartFile file){
        String videoId = vodservice.uploadVideoAly(file);
        return R.ok().data("videoId", videoId);
    }
}
