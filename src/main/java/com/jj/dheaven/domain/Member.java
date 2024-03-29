package com.jj.dheaven.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

//@AllArgsConstructor 생성자에 builer 붙일거면 사용하면 x
@Entity
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

    //그냥 회원 가입시 패스워드
    @Column(name = "password",nullable = true)
    private String password;

    //카카오 회원과 통합테이블이라 닉네임 유니크 제거
    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "birthdate",nullable = false)
    private LocalDate birthdate; //생년월일 시간제외

    //카카오 가입자는 주문시에 주소작성하여 테이블에 들어오도록
    @Column(name = "address", nullable = true)
    private String address;

    //카카오 키를 위한
    @Column(name = "kakaoID", nullable = true)
    private Long kakaoID;

    //일반, 카카오, 구글 등 회원 구분
 /*   @Enumerated(EnumType.STRING)
    @Column(name = "memtype", nullable = false)
    private String memtype;
*/
    @Enumerated(EnumType.STRING)
    @Column(name = "role",nullable = false)
    private Roles role;

    //회원정보 업데이트 메서드
    public Member update(String nickname, String address) {
        this.nickname  =nickname;
        this.address = address;
        return this;
    }


    @Builder
    public Member(String email, String password, String nickname,
                  String name, LocalDate birthdate, String address, Roles role, Long kakaoID){

        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.name = name;
        this.birthdate = birthdate;
        this.address = address;
        this.role = role;
        this.kakaoID = kakaoID;
        System.out.println("멤버 생성자 발생");
    }


 /* 이부분은 자동으로 설정해줌   @Column(name = "cre_date")
    private LocalDateTime cre_date; // 회원가입 날짜

    @Column(name = "upd_date")
    private LocalDateTime upd_date; // 회원정보 수정날짜
*/

}
