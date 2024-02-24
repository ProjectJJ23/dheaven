package com.jj.dheaven.보관용;

import com.jj.dheaven.domain.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class KaKaoJoinDto {

    //lenth 는 String만 size
    //private Long mem_id;
    @NotBlank(message = "이메일은 필수로 입력하셔야 합니다.")
    private String kakao_email; //id 대신 이메일

    @NotBlank(message = "액세스토큰")
    private String kakao_token;

    @NotBlank(message = "닉네임은 필수로 입력하셔야 합니다.")
    @Size(min = 1, max = 6, message = "1자이상, 6자 이하로 입력해주세요")
    private String nickname;

    @Size(min = 2, max = 4)
    @NotBlank(message = "성함은 필수로 입력하셔야 합니다.")
    private String name;

    /*@Pattern(regexp = "\\b(19|20)\\d{2}\\b", message = "유효한 출생년도를 입력하세요.")
    @NotBlank(message = "출생년도는 필수로 입력하셔야 합니다.")
    private String birthyear; //1998 형식

    @Pattern(regexp = "^(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])$", message = "유효한 생일을 입력하세요.")
    @NotBlank(message = "생일은 필수로 입력하셔야 합니다.")
    private String birthday; //1203 형식*/

    @DateTimeFormat(pattern = "yyyyMMdd")
    @NotBlank(message = "생년월일은 필수로 입력하셔야 합니다.")
    private LocalDate birthdate; //생년월일


    public LocalDate parseBirthdate(String birthyear, String birthday){
        String birthdateStr = birthyear.substring(0,4) + birthday;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate birthdate = LocalDate.parse(birthdateStr, formatter);
        System.out.println("생년월일kakao 합쳐진거 체크 dto 란: "+birthdate);
        return birthdate;
    }

    public Member toMember(){
        //LocalDate birthdate = parseBirthdate(birthyear, birthday); // 메서드 호출
        return Member.builder()
                .email(kakao_email)
                .password(kakao_token)
                .nickname(nickname)
                .name(name)
                .birthdate(birthdate)
                .build();
    }

}

