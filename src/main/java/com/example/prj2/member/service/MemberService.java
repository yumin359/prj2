package com.example.prj2.member.service;

import com.example.prj2.member.dto.MemberDto;
import com.example.prj2.member.dto.MemberForm;
import com.example.prj2.member.entity.Member;
import com.example.prj2.member.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    // 회원 가입
    public void signup(MemberForm m) {
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

    // 회원 정보 보기(하나 보기)
    public MemberDto view(String id) {
        Member member = memberRepository.findById(id).get();
        MemberDto dto = new MemberDto();
        dto.setId(member.getId());
        dto.setPassword(member.getPassword());
        dto.setName(member.getName());
        dto.setNickName(member.getNickName());
        dto.setBirthDate(member.getBirthDate());
        dto.setInfo(member.getInfo());
        return dto;
    }

    // 회원 삭제
    public void remove(String id) {
        memberRepository.deleteById(id);
    }

    // 회원 수정
    public void update(MemberForm m) {
        // 조회
        Member member = memberRepository.findById(m.getId()).get();
        // 수정
        member.setName(m.getName());
        member.setNickName(m.getNickName());
        member.setInfo(m.getInfo());
        // 저장
        memberRepository.save(member);
    }

    // 로그인
    public boolean login(String id, String password, HttpSession session) {
        // 입력 받은 id를 findById 해서
        Optional<Member> db = memberRepository.findById(id); // optional은 null 값 반환함
        // 존재하는지 확인, 즉 없으면 null 값을 반환 할 거
        if (db.isPresent()) { // 존재하면
            // db에 있는 비밀번호를 가져와서
            String dbPassword = db.get().getPassword();
            // 입력받은 비번이랑 같은 지 확인하고
            if (dbPassword.equals(password)) {
                // 같으면 dto로 만들어서
                Member member = db.get();
                MemberDto dto = new MemberDto();
                dto.setId(member.getId());
                dto.setPassword(member.getPassword());
                dto.setName(member.getName());
                dto.setNickName(member.getNickName());
                dto.setBirthDate(member.getBirthDate());
                dto.setInfo(member.getInfo());

                // session에 넣기
                session.setAttribute("loggedInUser", dto);

                return true;
            }
        }
        return false;
    }

}
