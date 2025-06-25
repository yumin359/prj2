package com.example.prj2.board.service;

import com.example.prj2.board.dto.BoardDto;
import com.example.prj2.board.dto.BoardWrite;
import com.example.prj2.board.entity.Board;
import com.example.prj2.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    // 게시물 목록 보기
    public List<Board> list(Board board) {
        List<Board> list = boardRepository.findAll();
        return list;
    }

    public BoardDto view(Integer id) {
        /*
        Optional<Board> byId = boardRepository.findById(id);
        if (byId.isPresent()) {
            BoardDto dto = new BoardDto();
            dto.setId(byId.get().getId());
            dto.setTitle(byId.get().getTitle());
            dto.setContent(byId.get().getContent());
            dto.setWriter(byId.get().getWriter());
            return dto;
        } else {
            return null;
        }
         */
        // controller에서 try catch로 nullpointException 잡아주면 되니까 여기서 바로 .get 해서 해도 될듯
        Board board = boardRepository.findById(id).get();
        BoardDto boardDto = new BoardDto();
        boardDto.setTitle(board.getTitle());
        boardDto.setWriter(board.getWriter());
        boardDto.setCreatedAt(board.getCreatedAt());
        boardDto.setContent(board.getContent());
        return boardDto;
    }
}
