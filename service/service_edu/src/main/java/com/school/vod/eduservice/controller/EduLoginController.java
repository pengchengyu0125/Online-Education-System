package com.school.vod.eduservice.controller;

import com.school.vod.commonutils.R;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eduservice/user")
@CrossOrigin
public class EduLoginController {
    /**
     * 登录
     * @return
     */
    @PostMapping("login")
    public R login(){
        return R.ok().data("token","admin");
    }

    /**
     * 用户信息
     * @return
     */
    @GetMapping("info")
    public R info(){
        return R.ok().data("roles","admin").data("name","admin").data("avatar","https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=314177801,1568281183&fm=26&gp=0.jpg");
    }
}
