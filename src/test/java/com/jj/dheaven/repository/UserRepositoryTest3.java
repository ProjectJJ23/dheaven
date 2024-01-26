package com.jj.dheaven.repository;

import com.jj.dheaven.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ComponentScan
@TestPropertySource(locations = "classpath:application-test.properties")
class UserRepositoryTest3 {

    @Autowired
    UserRepository userRepository;


  /*  @Test
    public User findByUsernum(){
        User user = new User();
       // user.setUsernum(2L);
        user.setUsername("김지희");
        user.setPassword("11");
        user.setEmail("loopy@naver.com");
        user.setCreate_time(LocalDateTime.now());
        User savedUser = userRepository.save(user);
        return  user;

    }*/


    @Test
    @DisplayName("유저테스트")
    public void userTest(){

        User user = new User();
        user.setId(1L);
        user.setUsername("김지희");
        user.setPassword("11");
        user.setEmail("loopy@naver.com");
        user.setCreate_time(LocalDateTime.now());
        User savedUser = userRepository.save(user);
        System.out.println(savedUser.toString());


    }

}