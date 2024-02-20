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

    //회원가입
    public Member saveMember(Member member){
        //System.out.println("가입 서비스계층 ");
        //validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    public boolean checkEmailDuplicate(String email) {
        System.out.println("서비스 계층 이메일 중복체크 메소드 작동");
        return memberRepository.existsByEmail(email);
    }

    public boolean checkNicknameDuplicate(String nickname) {
        System.out.println("서비스 계층 nickname 중복체크 메소드 작동");
        return memberRepository.existsByNickname(nickname);
    }

    //로그인
    public Member findMember(String email){
        return memberRepository.findByEmail(email);
    }

 //   @Transactional(readOnly = true)
  //  public Member login(String email, String password){
  //      return memberRepository.findByEmailAndPassword(email, password);
   // }

    
    //보류
/*    private void validateDuplicateEmail(Member member){
        Member findMember = memberRepository.findByEmail(member.getEmail());
       System.out.println("서비스 계층 이메일 중복체크 메소드 작동");
        if(findMember != null){
            throw new IllegalStateException("이미 가입된 메일주소 입니다.");
        }else {
            System.out.println("가입이 가능한 이메일 입니다.");
        }


    }*/




}
