/*
package com.jj.dheaven.dto;

import com.jj.dheaven.domain.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class KaKaoJoinDto {

    //lenth 는 String만 size
    //private Long mem_id;
    @NotBlank(message = "이메일은 필수로 입력하셔야 합니다.")
    private String kakao_email; //id 대신 이메일

    @NotBlank(message = "비밀번호는 필수로 입력하셔야 합니다.")
    @Size(min = 8, max = 15, message = "8자이상, 15자 이하로 입력해주세요")
    private String password;

    @NotBlank(message = "닉네임은 필수로 입력하셔야 합니다.")
    @Length(min = 1, max = 6, message = "1자이상, 6자 이하로 입력해주세요")
    private String nickname;

    @Length(min = 2, max = 4)
    @NotBlank(message = "성함은 필수로 입력하셔야 합니다.")
    private String name;

    //주민번호 정규표현식
    //@Pattern(regexp = "\\d{6}", message = "생년월일은 6자리 숫자로 입력하세요 (예: 940912).")
    @DateTimeFormat(pattern = "yyyyMMdd")
    @NotBlank(message = "생년월일은 필수로 입력하셔야 합니다.")
    private LocalDate birthdate; //생년월일

    private String address;

    public Member toMember(){
        return Member.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .name(name)
                .birthdate(birthdate)
                .address(address)
                .build();
    }

}
*/
