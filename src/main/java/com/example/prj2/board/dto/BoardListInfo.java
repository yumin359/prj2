package com.example.prj2.board.dto;

import java.time.LocalDateTime;

public interface BoardListInfo {
    Integer getId();

    String getTitle();

    String getWriter();

    LocalDateTime getCreated();
}
