package com.sud.userservice.dtos;

import com.sud.userservice.models.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SignUpResponseDto {
    private Long userId;
    private String name;
    private String email;

    public static SignUpResponseDto fromUser(User user){
        return SignUpResponseDto.builder().userId(user.getId())
                .name(user.getName())
                .email(user.getEmail()).build();
    }

}
