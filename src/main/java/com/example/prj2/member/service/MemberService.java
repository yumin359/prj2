package com.example.prj2.member.service;

import com.example.prj2.member.dto.MemberSignUpForm;
import com.example.prj2.member.entity.Member;
import com.example.prj2.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    // 회원 가입
    public void signup(MemberSignUpForm m) {
        Member member = new Member();

        member.setId(m.getId());
        member.setPassword(m.getPassword());
        member.setName(m.getName());
        member.setNickName(m.getNickName());
        member.setBirthDate(m.getBirthDate());
        member.setInfo(m.getInfo());

        memberRepository.save(member);
    }

    // 회원 목록 보기(여러개 보기)
    public List<Member> list() {
        return memberRepository.findAll();
    }

}
