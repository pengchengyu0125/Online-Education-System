package com.school.vod.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface Vodservice {
    //上传视频
    String uploadVideoAly(MultipartFile file);
}
