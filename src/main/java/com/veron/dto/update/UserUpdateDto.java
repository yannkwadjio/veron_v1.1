package com.veron.dto.update;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDto {
    private String fullName;
    private String phoneNumber;
    private String role;
    private boolean statusUser;
    private String enterprise;
    private String username;

    public UserUpdateDto(String fullName, String phoneNumber, boolean enabled, String enterprise, String username) {
    }
}
