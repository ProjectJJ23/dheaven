package com.jj.dheaven.보관용;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class KaKaoLoginDto {

    @NotBlank(message = "이메일은 필수로 입력하셔야 합니다.")
    private String kakao_email; //id 대신 이메일

    @NotBlank(message = "액세스토큰")
    private String kakao_token;



}
