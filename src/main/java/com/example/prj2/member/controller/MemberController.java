package com.example.prj2.member.controller;

import com.example.prj2.board.dto.BoardDto;
import com.example.prj2.member.dto.MemberForm;
import com.example.prj2.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.sql.DataSource;
import java.util.Map;

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
    public String signupPost(MemberForm member, RedirectAttributes rttr) {
        memberService.signup(member);
        rttr.addFlashAttribute("alert",
                Map.of("code", "success", "message", "회원가입이 완료되었습니다"));
        return "redirect:/board/list";
    }

    // 회원 목록 보기(여러개)
    @GetMapping("list")
    public String list(Model model, HttpSession session, RedirectAttributes rttr) {
        Object user = session.getAttribute("loggedInUser");
        // 로그인 안 한 사람 회원목록 url 접근 차단
        if (user == null) {
            rttr.addFlashAttribute("alert",
                    Map.of("code", "warning", "message", "로그인 후 이용가능합니다."));
            return "redirect:/member/login";
        }
        // 이거 Member로 받아와서 MemberListInfo로 바꿔줌
        model.addAttribute("memberList", memberService.list());
        return "member/list";
    }

    // 회원 정보 보기(하나보기)
    @GetMapping("view")
    public String view(String id, Model model) {
        model.addAttribute("memberView", memberService.view(id));
        return "member/view";
    }

    // 회원 삭제
    @PostMapping("remove")
    public String remove(String id, RedirectAttributes rttr) {
        memberService.remove(id);
        rttr.addFlashAttribute("alert",
                Map.of("code", "danger", "message", "탈퇴되었습니다."));
        return "redirect:/member/list";
    }

    // 회원 수정(화면)
    @GetMapping("edit")
    public String editForm(String id, Model model, RedirectAttributes rttr) {
        model.addAttribute("memberEditView", memberService.view(id));
        return "member/edit";
    }

    // 회원 수정(실제 값 수정되는)
    @PostMapping("edit")
    public String editPost(MemberForm member, RedirectAttributes rttr) {
        memberService.update(member);
        rttr.addAttribute("id", member.getId());
        rttr.addFlashAttribute("alert",
                Map.of("code", "success", "message", "회원 정보가 수정되었습니다."));
        return "redirect:/member/view";
    }

    // FIXME : 나중에 경로 8080:/member/login에서 8080:/login(아님 패키지 만들어서 또 다른 컨트롤러 만들기) 되도록 하기
    // 로그인
    @GetMapping("login")
    public String loginForm() {
        return "member/login";
    }

    @PostMapping("login")
    public String loginPost(String id, String password,
                            HttpSession session,
                            RedirectAttributes rttr) {
        boolean login = memberService.login(id, password, session);

        if (login) {
            rttr.addFlashAttribute("alert",
                    Map.of("code", "success", "message", "로그인 되었습니다"));
            return "redirect:/home";
        } else {
            rttr.addFlashAttribute("alert",
                    Map.of("code", "danger", "message", "아이디/패스워드가 일치하지 않습니다"));
            rttr.addFlashAttribute("id", id);
            return "redirect:/member/login";
        }
    }

    // 로그아웃
    @GetMapping("logout")
    public String logout(HttpSession session, RedirectAttributes rttr) {
        session.invalidate();
        rttr.addFlashAttribute("alert",
                Map.of("code", "warning", "message", "로그아웃 되었습니다."));
        return "redirect:/home";
    }

}
