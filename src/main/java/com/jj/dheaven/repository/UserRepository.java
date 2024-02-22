package com.jj.dheaven.repository;

import com.jj.dheaven.domain.User;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@ComponentScan
@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    List<User> findByEmail(String email);



}
