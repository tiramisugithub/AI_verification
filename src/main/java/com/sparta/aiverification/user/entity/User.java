package com.sparta.aiverification.user.entity;


import com.sparta.aiverification.Timestamped;
import com.sparta.aiverification.user.dto.UserRequestDto;
import com.sparta.aiverification.user.enums.UserRoleEnum;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "p_users")
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;


    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String address;

    @Setter
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;


    public void updateInfo(UserRequestDto userRequestDto) {
        if(userRequestDto.getUsername() != null & !userRequestDto.getUsername().isBlank()){
            this.username = userRequestDto.getUsername();
        }
        if (userRequestDto.getEmail() != null && !userRequestDto.getEmail().isBlank()) {
            this.email = userRequestDto.getEmail();
        }
        if (userRequestDto.getAddress() != null && !userRequestDto.getAddress().isBlank()) {
            this.address = userRequestDto.getAddress();
        }
        if (userRequestDto.getPhone() != null && !userRequestDto.getPhone().isBlank()) {
            this.phone = userRequestDto.getPhone();
        }
    }

}


