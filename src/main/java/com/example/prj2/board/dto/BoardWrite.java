package com.example.prj2.board.dto;

import lombok.Data;

// html에서 게시물 작성하면 값 담아올 객체
@Data
public class BoardWrite {
    private Integer id;
    private String title;
    private String content;
    private String writer;
}
