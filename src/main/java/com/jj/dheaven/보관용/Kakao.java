package com.jj.dheaven.보관용;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Kakao {

    private String email; //id 대신 이메일

    private String nickname;

    private String name;

    private String kakao_token; //비번대신

    private LocalDate birthdate; //생년월일

    public Kakao(Kakao kakao){
        this.email = kakao.getEmail();
        this.nickname = kakao.getNickname();
        this.name = kakao.getName();
        this.kakao_token = kakao.getKakao_token();
        this.birthdate = kakao.getBirthdate();
    }


}
