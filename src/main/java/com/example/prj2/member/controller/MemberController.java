package com.example.prj2.member.controller;

import com.example.prj2.member.dto.MemberDto;
import com.example.prj2.member.dto.MemberSignUpForm;
import com.example.prj2.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.sql.DataSource;

@Controller
@RequiredArgsConstructor
@RequestMapping("member")
public class MemberController {

    private final MemberService memberService;
    private final DataSource dataSource;

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

    // 회원 목록 보기(여러개)
    @GetMapping("list")
    public String list(Model model) {
        model.addAttribute("memberList", memberService.list());
        return "member/list";
    }

    // 회원 정보 보기(하나보기)
    @GetMapping("view")
    public String view(String id, Model model) {
        model.addAttribute("memberView", memberService.view(id));
        return "member/view";
    }
}
