package com.jj.dheaven.service;

import com.jj.dheaven.domain.Member;
import com.jj.dheaven.dto.MemberJoinDto;
import com.jj.dheaven.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member saveMember(Member member){
        //System.out.println("가입 서비스계층 ");
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }


    private void validateDuplicateMember(Member member){
        Member findMember = memberRepository.findByEmail(member.getEmail());
       // System.out.println("서비스 계층 가입 중복 메소드 작동");
        if(findMember != null){
            throw new IllegalStateException("이미 가입된 메일주소 입니다.");
        }


    }




}
