package com.jj.dheaven.보관용;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class KakaoDto {

    //long형

    //lenth 는 String만 size
    @NotBlank(message = "이메일은 필수로 입력하셔야 합니다.")
    private String kakao_email; //id 대신 이메일

    //@Size(min = 1, max = 6, message = "1자이상, 6자 이하로 입력해주세요")
    @NotBlank(message = "닉네임은 필수로 입력하셔야 합니다.")
    private String nickname;

    //@Size(min = 2, max = 4)
    @NotBlank(message = "성함은 필수로 입력하셔야 합니다.")
    private String name;

    @DateTimeFormat(pattern = "yyyyMMdd")
    @NotBlank(message = "생년월일은 필수로 입력하셔야 합니다.")
    private LocalDate birthdate; //생년월일


   // @NotBlank(message = "액세스토큰")
    //private String kakao_token;

    public LocalDate parseBirthdate(String birthyear, String birthday){
        String birthdateStr = birthyear + birthday;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate birthdate = LocalDate.parse(birthdateStr, formatter);
        System.out.println("kakaoDto-카카오생년월일 합치기 확인: "+birthdate);
        return birthdate;
    }



}
