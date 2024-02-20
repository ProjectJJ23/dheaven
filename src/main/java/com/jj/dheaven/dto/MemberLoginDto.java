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
public class MemberLoginDto {

    @NotBlank(message = "이메일은 필수로 입력하셔야 합니다.")
    @Email(message = "이메일의 형식에 맞게 작성해주세요.")
    private String email; //id 대신 이메일

    @NotBlank(message = "비밀번호는 필수로 입력하셔야 합니다.")
    @Size(min = 8, max = 15, message = "8자이상, 15자 이하로 입력해주세요")
    private String password;


}
