package com.example.prj2.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@Entity
@Table(name = "member", schema = "prj2")
public class Member {
    @Id
    @Column(name = "id", nullable = false, length = 30)
    private String id;

    @Column(name = "password", nullable = false, length = 30)
    private String password;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "nick_name", nullable = false, length = 30)
    private String nickName;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "info", length = 1000)
    private String info;

}