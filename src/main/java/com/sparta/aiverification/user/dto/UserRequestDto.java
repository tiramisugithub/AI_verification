package com.sparta.aiverification.user.dto;

import com.sparta.aiverification.user.enums.UserRoleEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {

    @Pattern(regexp = "^[a-z0-9]{4,10}$")
    private String username;

    @Email(message = "올바른 형식의 이메일 주소어야 합니다.")
    private String email;

    @Pattern(regexp = "^[0-9]{3}-[0-9]{3,4}-[0-9]{4}$", message = "전화번호 형식이 올바르지 않습니다.")
    private String phone;


    private String address;

    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$",
            message = "비밀번호는 영문, 숫자, 특수문자를 포함하여 8자 이상 15자 이내여야 합니다.")
    private String password;
}
