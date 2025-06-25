package com.example.prj2.board.controller;

import com.example.prj2.board.dto.BoardWrite;
import com.example.prj2.board.entity.Board;
import com.example.prj2.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.sql.DataSource;
import java.util.List;

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

}
