package com.jj.dheaven.domain;

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
public class Member extends BaseTimeEntitiy {

    //기본키
    @Id
    @Column(name = "mem_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long mem_id;

    @Column(name = "email", unique = true)
    private String email; //id 대신 이메일

    @Column(name = "password")
    private String password;

    @Column(name = "nickname", unique = true)
    private String nickname;

    @Column(name = "name")
    private String name;

    @Column(name = "birthdate")
    private LocalDate birthdate; //생년월일 시간제외

    @Column(name = "address")
    private String address;

    @Column(name = "role")
    private String role;

    @Builder
    public Member(String mem_id, String password, String nickname,
                  String name, LocalDate birthdate, String address, Roles role){

        this.password = password;
        this.nickname = nickname;
        this.name = name;
        this.birthdate = birthdate;
        this.address = address;
        this.role = String.valueOf(Roles.MEMBER);

    }

/*

    @Column(name = "cre_date")
    private LocalDateTime cre_date; // 회원가입일

    @Column(name = "upd_date")
    private LocalDateTime upd_date; // 회원정보 수정날짜?
*/
/*
@ElementCollection(fetch = FetchType.LAZY)
@Builder.Default
private Set<Roles> rolesSet = new HashSet<>();

public void addRoles(Roles roles){
    rolesSet.add(roles);
}
*/
/*    @Enumerated(EnumType.STRING)
    private Roles roles;*/


}
