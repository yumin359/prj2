package com.example.prj2.board.service;

import com.example.prj2.board.dto.BoardDto;
import com.example.prj2.board.dto.BoardListInfo;
import com.example.prj2.board.dto.BoardWrite;
import com.example.prj2.board.entity.Board;
import com.example.prj2.board.repository.BoardRepository;
import com.example.prj2.member.dto.MemberDto;
import com.example.prj2.member.entity.Member;
import com.example.prj2.member.repository.MemberRepository;
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
    private final MemberRepository memberRepository;

    // 게시물 작성 + 로그인한 사람만 가능(작성자는 로그인 한 사람 닉네임 바로 뜨게)
    public void add(BoardWrite boardWrite, MemberDto memberDto) {
        Board board = new Board();
        board.setTitle(boardWrite.getTitle());
        board.setContent(boardWrite.getContent());
//        board.setWriter(boardWrite.getWriter()); // TODO: Member랑 관계 맺으면서 생긴 오류1

        // 로그인 하면, MemberDto에
        // 사용자 아이디(id), 닉네임(nick_name), 자기소개(info)를 가져와서
        // 세션에 저장해두므로 지금 writer에 (id)를 넣은 거
        // 식별을 위해 id를 가져오지만, 화면에 보이는 건 nickName 으로 하면 됨
        // 값 자체는 세개가 넘어오는 거니까!
        Member member = memberRepository.findById(memberDto.getId()).get();
        board.setWriter(member);

        boardRepository.save(board);
    }

    // 게시물 목록 보기
    public Map<String, Object> list(String keyword, Integer page) {
        Page<BoardListInfo> pageBoard;

        if (keyword == null || keyword.isBlank()) {
            pageBoard = boardRepository.findAllBy(
                    PageRequest.of(page - 1, 10, Sort.by("id").descending()));
        } else {
            pageBoard = boardRepository.findKeyword("%" + keyword + "%",
                    PageRequest.of(page - 1, 10, Sort.by("id").descending()));
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
        // 근데 보통 서비스에서 다 잡아주고 컨트롤러에는 어떤 오류인지 출력만 하는 느낌인가봄
        Board board = boardRepository.findById(id).get();
        BoardDto boardDto = new BoardDto();
        boardDto.setId(board.getId()); // edit에서 제대로 나오려면 필요함
        boardDto.setTitle(board.getTitle());

//        boardDto.setWriter(board.getWriter()); // 고침
        // board의 작성자(writer)는 member의 아이디(id)와 관계가 있음
        // 근데 이제 작성자 이름은 아이디(id)가 아닌 닉네임(nick_name)으로 보이게 하려고
        MemberDto memberDto = new MemberDto();
        memberDto.setId(board.getWriter().getId());
        memberDto.setNickName(board.getWriter().getNickName());
        // 두 값을 가져와서 board의 writer에 넣어줌
        boardDto.setWriter(memberDto);

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
