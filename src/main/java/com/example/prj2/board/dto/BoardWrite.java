package com.example.prj2.board.dto;

import lombok.Data;

// html에서 게시물 작성하면 값 담아올 객체
@Data
public class BoardWrite {
    private Integer id;
    private String title;
    private String content;
//    private String writer;
// 로그인 한 사람만 쓸 수 있으니까 필요없어짐
    // TODO: 그리고 자동으로 로그인 한 사람 이름이 뜨도록 하게 하면 됨
}
