package com.jj.dheaven.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@Getter
@Setter
@ToString
public class User {

    @Id
    @Column(name = "usernum")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // --기본키

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email; // --부서번호

    @Column(name = "password")
    private String password; // --담당자 번호

    @Column(name = "create_time")
    private LocalDateTime create_time; // --담당자 아이디(이메일) 유니크 걸기




}
