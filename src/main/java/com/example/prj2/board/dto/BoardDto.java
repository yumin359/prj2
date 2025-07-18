package com.example.prj2.board.dto;

import com.example.prj2.member.dto.MemberDto;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class BoardDto { // implements Serializable {
    private Integer id;
    private String title;
    //    private String writer;
    private MemberDto writer; // 바꿔줌
    private LocalDateTime createdAt;
    private String content;
}
// 하나 보는 용으로 사용