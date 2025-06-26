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
    private MemberDto writer; // TODO : 이렇게 해줘야하는 것 같은데 아무튼 확인해보기
    private LocalDateTime createdAt;
    private String content;
}
// 하나 보는 용으로 사용