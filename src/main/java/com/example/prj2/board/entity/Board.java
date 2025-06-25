package com.example.prj2.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "board")
public class Board {
    @Id // jakarta.뭐시기로 해야함
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 실무에선 값이 많아서 보통 Long 쓴대용
    private String title;
    private String content;

    // TODO : 아마 member랑 외래키 할 듯
    private String writer;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;
}
