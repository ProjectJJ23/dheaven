package com.jj.dheaven.보관용;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class KakaoInfo {
//카카오에서 받아온 정보를 저장하는 클래스 (KakaoInfo)

    private Long id;
    private String email;
    private String nickname;
    private String name;
    private String birthyear;
    private String birthday;

    public KakaoInfo(Long id, String email, String nickname,
                     String name, String birthyear, String birthday) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.name = name;
        this.birthyear = birthyear;
        this.birthday = birthday;

    }





}
