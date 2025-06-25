package com.example.prj2.board.repository;

import com.example.prj2.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Integer> {
    Page<Board> findAllByTitleContainsOrWriterContains(String k1, String k2, PageRequest pageRequest);

    Page<Board> findAllBy(PageRequest pageRequest);

}