package com.example.prj2.member.controller;

import com.example.prj2.member.dto.MemberSignUpForm;
import com.example.prj2.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("member")
public class MemberController {

    private final MemberService memberService;

    // 회원가입(화면)
    @GetMapping("signup")
    public String signupForm() {
        return "member/signup";
    }

    // 회원가입(값 넘어가는거)
    @PostMapping("signup")
    public String signupPost(MemberSignUpForm member) {
        memberService.signup(member);
        return "redirect:/board/list";
    }
}
