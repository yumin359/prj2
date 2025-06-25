package com.example.prj2.board.controller;

import com.example.prj2.board.dto.BoardDto;
import com.example.prj2.board.dto.BoardWrite;
import com.example.prj2.board.entity.Board;
import com.example.prj2.board.service.BoardService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public String writeBoard() {
        return "board/write";
    }

    // 게시물 작성(실제로 값이 들어가게)
    @PostMapping("write")
    public String writePost(BoardWrite boardWrite) {
        boardService.add(boardWrite);
        return "redirect:/board/list";
    }

    // 게시물 목록 보기
    @GetMapping("list")
    public String listBoard(Board data, Model model) {
        List<Board> result = boardService.list(data);
        model.addAttribute("boardList", result);
//        model.addAllAttributes(result); // 나중에 쓸 듯
        return "board/list";
    }

    // 게시물 하나 보기
    @GetMapping("view")
    public String viewBoard(Integer id, Model model, RedirectAttributes rttr) {
        try {
            BoardDto view = boardService.view(id);
            model.addAttribute("view", view);
            return "board/view";
        } catch (Exception e) {
            // Exception으로 포괄처리 말고
            // NoSuchElementException 등 정확히 처리해줘야 함 나중에는 ㅎㅎ
            rttr.addFlashAttribute("alert",
                    Map.of("code", "danger", "message", "존재하지 않는 게시물 입니다."));
            // alert은 지금 안 나오는데 gpt는 html에서 처리해줘야 한다는데 흠 강사님이랑 할 때는 그런 거 안 했음
            // FIXME : 이따 꾸밀 때 확인해 봐야겠음
            return "redirect:/board/list";
        }
    }

}
