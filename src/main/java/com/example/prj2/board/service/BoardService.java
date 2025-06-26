package com.example.prj2.board.service;

import com.example.prj2.board.dto.BoardDto;
import com.example.prj2.board.dto.BoardListInfo;
import com.example.prj2.board.dto.BoardWrite;
import com.example.prj2.board.entity.Board;
import com.example.prj2.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

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
//        board.setWriter(boardWrite.getWriter()); // TODO: Member랑 관계 맺으면서 생긴 오류1
        boardRepository.save(board);
    }

    // 게시물 목록 보기
    public Map<String, Object> list(String keyword, Integer page) {
        Page<BoardListInfo> pageBoard;

        if (keyword == null || keyword.isBlank()) {
            pageBoard = boardRepository.findAllBy(
                    PageRequest.of(page - 1, 10, Sort.by("id")));
        } else {
            pageBoard = boardRepository.findKeyword("%" + keyword + "%",
                    PageRequest.of(page - 1, 10, Sort.by("id")));
        }
        List<BoardListInfo> list = pageBoard.getContent();

        int rightPage = ((page - 1) / 10 + 1) * 10;
        int leftPage = rightPage - 9;
        rightPage = Math.min(rightPage, pageBoard.getTotalPages());

        var result = Map.of("boardList", list,
                "totalElements", pageBoard.getTotalElements(),
                "totalPages", pageBoard.getTotalPages(),
                "rightPage", rightPage,
                "leftPage", leftPage,
                "currentPage", page);

        return result;
    }

    // 게시물 하나 보기
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
        boardDto.setId(board.getId()); // edit에서 제대로 나오려면 필요함
        boardDto.setTitle(board.getTitle());
//        boardDto.setWriter(board.getWriter()); // TODO: Member랑 관계 맺으면서 생긴 오류2
        boardDto.setCreatedAt(board.getCreatedAt());
        boardDto.setContent(board.getContent());
        return boardDto;
    }

    // 게시물 수정
    public void update(BoardWrite data) {
        // 조회
        Board board = boardRepository.findById(data.getId()).get();
        // 수정
        board.setTitle(data.getTitle());
        board.setContent(data.getContent());
//        board.setWriter(data.getWriter()); // TODO: Member랑 관계 맺으면서 생긴 오류3
//        board.setCreatedAt(data.getCreatedAt()); // 얜 있어도 안 변함
        // 저장
        boardRepository.save(board);
    }

    // 게시물 삭제
    public void remove(Integer id) {
        boardRepository.deleteById(id);
    }

}
