package com.example.prj2.board.dto;

import java.time.LocalDateTime;

public interface BoardListInfo {
    Integer getId();

    String getTitle();

    String getWriter();

    LocalDateTime getCreated();
}
// 페이징+검색할 때 제목이나 작성자만 필요하니까 이렇게 만들어서 프로젝션으로