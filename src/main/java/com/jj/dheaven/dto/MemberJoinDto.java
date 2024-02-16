package com.jj.dheaven.dto;

import com.jj.dheaven.domain.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
public class MemberJoinDto {

    //private Long mem_id;
    @NotBlank(message = "이메일은 필수로 입력하셔야 합니다.")
    @Email(message = "이메일의 형식에 맞게 작성해주세요.")
    private String email; //id 대신 이메일

    @NotBlank(message = "비밀번호는 필수로 입력하셔야 합니다.")
    @Length(min = 8, max = 15, message = "8자이상, 15자 이하로 입력해주세요")
    private String password;

    @NotBlank(message = "닉네임은 필수로 입력하셔야 합니다.")
    @Length(min = 1, max = 6, message = "1자이상, 6자 이하로 입력해주세요")
    private String nickname;

    @NotBlank(message = "성함은 필수로 입력하셔야 합니다.")
    private String name;

    @NotBlank(message = "생년월일은 필수로 입력하셔야 합니다.")
    private LocalDate birthdate; //생년월일

    @NotBlank(message = "주소는 필수로 입력하셔야 합니다.")
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

   // private String role;


}
