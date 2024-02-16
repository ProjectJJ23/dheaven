package com.jj.dheaven.repository;

import com.jj.dheaven.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    //Optional<Member> findByEmail(String email);

    boolean existsByEmail(String email);

    Member findByEmail(String email);

    List<Member> findAllBy();


}
