package com.jj.dheaven.보관용;

import com.jj.dheaven.model.KakaoProfile;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class SocialUserInfoDto {

    private Long id;
    private String nickname;
    private String email;
    private String name;
    private String birthyear;
    private String birthday;

    public SocialUserInfoDto(Long id, String nickname, String email, String name, String birthyear,
                             String birthday) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.birthyear = birthyear;
        this.birthday = birthday;
    }




}
