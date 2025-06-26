package com.example.prj2.member.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MemberDto {
    private String id;
    private String password;
    private String name;
    private String nickName;
    private LocalDate birthday;
    private String info;
}
