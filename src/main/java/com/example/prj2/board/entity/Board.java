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
    // 실무에선 @JoinColumn(name = "member_id") 이런식으로 많이 쓴대여 
    // 근데 일단 애초에 나는 초보라 sql로 둘이 관계 맺음
    private Member writer;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;
}
