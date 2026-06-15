package com.user.dto.response;

import com.user.enums.Gender;
import com.user.enums.Role;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class UserProfileResponse {

    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate dateOfBirth;
    private String passportNumber;
    private String nationality;
    private Gender gender;
    private Role role;
    private String profileImageUrl;
    private LocalDateTime createdAt;
}