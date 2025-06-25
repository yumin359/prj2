package com.example.prj2.board.service;

import com.example.prj2.board.dto.BoardWrite;
import com.example.prj2.board.entity.Board;
import com.example.prj2.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {
    private final BoardRepository boardRepository;

    // 게시물 작성
    public void add(BoardWrite boardWrite) {
        Board board = new Board();
        board.setTitle(boardWrite.getTitle());
        board.setContent(boardWrite.getContent());
        board.setWriter(boardWrite.getWriter());
        boardRepository.save(board);
    }

//    // 게시물 목록 보기
//    public List<Board> list(Board board) {
//        List<Board> list = boardRepository.findAll();
//        return list;
//    }

}
