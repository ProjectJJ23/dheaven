package com.jj.dheaven.보관용;

import com.jj.dheaven.domain.Roles;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/*@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "kakaoMember")
@Getter
@ToString*/
public class KakaoMember // extends BaseTimeEntity
{

    //기본키
    @Id
    @Column(name = "kakaomem_no")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long kakaomem_no;

    @Column(name = "kakao_email", unique = true, nullable = false)
    private String kakao_email; //id 대신 이메일

    @Column(name = "kakao_nickname", unique = true,nullable = false)
    private String kakao_nickname;

    @Column(name = "kakao_name",nullable = false)
    private String kakao_name;

    @Column(name = "kakao_birthdate",nullable = false)
    private LocalDate kakao_birthdate; //생년월일 시간제외

    //카카오 가입자는 주문시에 주소작성하여 테이블에 들어오도록
    @Column(name = "address", nullable = true)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "role",nullable = false)
    private Roles role;

    //회원정보 업데이트 메서드
    public KakaoMember update(String kakao_nickname, String address) {
        this.kakao_nickname  =kakao_nickname;
        this.address = address;
        return this;
    }


    @Builder
    public KakaoMember(String kakao_email, String kakao_nickname,
                  String kakao_name, LocalDate kakao_birthdate, String address, Roles role){

        this.kakao_email = kakao_email;
        this.kakao_nickname = kakao_nickname;
        this.kakao_name = kakao_name;
        this.kakao_birthdate = kakao_birthdate;
        this.address = address;
        this.role = role;
        System.out.println("카카오 멤버 생성자 발생");
    }


 /* 이부분은 자동으로 설정해줌   @Column(name = "cre_date")
    private LocalDateTime cre_date; // 회원가입 날짜

    @Column(name = "upd_date")
    private LocalDateTime upd_date; // 회원정보 수정날짜
*/







}
