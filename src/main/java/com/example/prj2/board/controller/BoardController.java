package com.example.prj2.board.controller;

import com.example.prj2.board.dto.BoardDto;
import com.example.prj2.board.dto.BoardWrite;
import com.example.prj2.board.entity.Board;
import com.example.prj2.board.service.BoardService;
import com.example.prj2.member.dto.MemberDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("board")
public class BoardController {
    private final BoardService boardService;
    private final DataSource dataSource;

    // 게시물 작성(화면)
    @GetMapping("write")
    public String writeBoard(HttpSession session, RedirectAttributes rttr) {
        // 세션에 저장된 사용자 정보를 가져온다
        Object user = session.getAttribute("loggedInUser");

        if (user != null) {
            // 값이 있다 -> 로그인이 되었다
            return "board/write";
        } else {
            // 값이 없다 -> 로그인 해야함
            // html로 보이고 안 보이고 하게 할건데 이렇게 하면 url로 뭐 접근하는 거 차단 가능한듯?
            rttr.addFlashAttribute("alert",
                    Map.of("code", "warning", "message", "로그인 후 글을 작성해주세요."));
            return "redirect:/member/login";
        }
    }

    // 게시물 작성(실제로 값이 들어가게)
    @PostMapping("write")
    public String writePost(BoardWrite boardWrite,
                            @SessionAttribute(name = "loggedInUser", required = false)
                            MemberDto user,
                            RedirectAttributes rttr) {
        if (user != null) {
            boardService.add(boardWrite, user);
            rttr.addFlashAttribute("alert",
                    Map.of("code", "success", "message", "게시물이 작성되었습니다."));
            return "redirect:/board/list";
        } else {
            return "redirect:/member/login";
        }
    }

    // 게시물 목록 보기
    @GetMapping("list")
    public String listBoard(@RequestParam(defaultValue = "")
                            String keyword,
                            @RequestParam(defaultValue = "1")
                            Integer page,
                            Model model) {
        Map<String, Object> result = boardService.list(keyword, page);
//        model.addAttribute("boardList", result); // 하나 볼때는 이걸로 해주는 듯?
        model.addAllAttributes(result); // 나중에 쓸 듯
        return "board/list";
    }

    // 게시물 하나 보기
    @GetMapping("view")
    public String viewBoard(Integer id, Model model, RedirectAttributes rttr) {
        try {
            BoardDto view = boardService.view(id);
            model.addAttribute("view", view);
            return "board/view";
        } catch (NoSuchElementException e) {
            // Exception으로 포괄처리 말고
            // NoSuchElementException 등 정확히 처리해줘야 함 나중에는 ㅎㅎ
            rttr.addFlashAttribute("alert",
                    Map.of("code", "danger", "message", "존재하지 않는 게시물 입니다."));
            return "redirect:/board/list";
        }
    }

    // 게시물 수정(화면)-값은 게시물 하나보기 처럼 가져오는데, edit.html이라서 화면이 다름
    @GetMapping("edit")
    public String editBoard(Integer id, Model model, RedirectAttributes rttr) {
        try {
            BoardDto view = boardService.view(id);
            model.addAttribute("view", view);
            return "board/edit";
        } catch (NoSuchElementException e) {
            rttr.addFlashAttribute("alert",
                    Map.of("code", "danger", "message", "존재하지 않는 게시물 입니다."));
            return "redirect:/board/list";
        }
    }

    // 게시물 수정(실제로 처리 되는)
    @PostMapping("edit")
    public String editPost(BoardWrite data, RedirectAttributes rttr) {
        boardService.update(data);

        rttr.addFlashAttribute("alert",
                Map.of("code", "success", "message", "게시물이 수정되었습니다."));

        rttr.addAttribute("id", data.getId());

        return "redirect:/board/view";
    }

    // 게시물 삭제
    @PostMapping("remove")
    public String removeBoard(Integer id, RedirectAttributes rttr) {
        boardService.remove(id);
        rttr.addFlashAttribute("alert",
                Map.of("code", "warning", "message", "게시물이 삭제되었습니다."));
        return "redirect:/board/list";
    }

}
