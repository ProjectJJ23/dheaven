package com.jj.dheaven.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
@Getter
@ToString
public class Member extends BaseTimeEntity {

    //기본키
    @Id
    @Column(name = "mem_no")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long mem_no;

    @Column(name = "email", unique = true,nullable = false)
    private String email; //id 대신 이메일

    //그냥 회원 가입시 패스워드, 카카오 가입시 비번대신 토큰
    @Column(name = "password",nullable = true)
    private String password;

    @Column(name = "kakao_token",nullable = true)
    private String kakao_token;


    @Column(name = "nickname", unique = true,nullable = false)
    private String nickname;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "birthdate",nullable = false)
    private LocalDate birthdate; //생년월일 시간제외

    //카카오 가입자는 주문시에 주소작성하여 테이블에 들어오도록
    @Column(name = "address", nullable = true)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "role",nullable = false)
    private Roles role;

    @Builder
    public Member(String email, String password, String nickname,
                  String name, LocalDate birthdate, String address){

        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.name = name;
        this.birthdate = birthdate;
        this.address = address;
        this.role = Roles.MEMBER;

    }


 /* 이부분은 자동으로 설정해줌   @Column(name = "cre_date")
    private LocalDateTime cre_date; // 회원가입 날짜

    @Column(name = "upd_date")
    private LocalDateTime upd_date; // 회원정보 수정날짜
*/

}
