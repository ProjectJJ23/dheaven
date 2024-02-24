package com.jj.dheaven.repository;

import com.jj.dheaven.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    //Optional<Member> findByEmail(String email);

    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);

    Member findByEmail(String email);

    //kakao가입전용
    //Member findByEmailAndBirthdate(String kakao_email, LocalDate kakao_birthdate);
    //Member findByKakao_idAndEmail(Long kakao_id, String email);

    Member findByKakaoIDAndEmail(Long kakaoID, String kakao_email);

    //로그인
    //Member findByEmailAndPassword(String email, String password);

    List<Member> findAllBy();


}
