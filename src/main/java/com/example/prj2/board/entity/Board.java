package com.example.prj2.board.entity;

import com.example.prj2.member.entity.Member;
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

    @ManyToOne
    @JoinColumn(name = "writer")
    private Member writer;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;
}
