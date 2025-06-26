package com.example.prj2.home.controller;

import com.example.prj2.home.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final HomeService homeService;

    @GetMapping("home")
    public String home() {
        return "layout/home"; // templates/layout/home.html
    }

//    // 얘네는 나중에 익숙해지면 하는 거로 하구여
//    @GetMapping("login")
//    public String login() {
//        return "layout/login"; // 얘도 layout 이름 나중에 바꿀듯
//    }
//
//    @GetMapping("logout")
//    public String logout() {
//        return "redirect:/--";
//    }
}
