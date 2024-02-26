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
    Member findByKakaoIDAndEmail(Long kakaoID, String kakao_email);


    List<Member> findAllBy();


}
