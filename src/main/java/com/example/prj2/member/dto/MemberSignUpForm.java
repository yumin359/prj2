package com.example.prj2.member.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MemberSignUpForm {
    private String id;
    private String password;
    private String name;
    private String nickName;
    private LocalDate birthDate;
    private String info;
}
