package com.example.prj2.board.repository;

import com.example.prj2.board.dto.BoardListInfo;
import com.example.prj2.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardRepository extends JpaRepository<Board, Integer> {
    // 이렇게 보드로 가져올 수 있지만 그러면 성능 저하+ 불필요한 데이터 조회 -> 프로젝션 사용하겠다. (또는 dto로 쓴대여)
//    Page<Board> findAllByTitleContainsOrWriterContains(String k1, String k2, PageRequest pageRequest);
//    Page<Board> findAllBy(PageRequest pageRequest);

    @Query("""
            SELECT b
            FROM Board b
            WHERE b.title LIKE :keyword
            OR b.writer LIKE :keyword
            """)
    Page<BoardListInfo> findKeyword(String keyword, PageRequest pageRequest);

    Page<BoardListInfo> findAllBy(PageRequest pageRequest);

}