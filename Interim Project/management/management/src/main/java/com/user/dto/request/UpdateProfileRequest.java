package com.user.dto.request;

import com.user.enums.Gender;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateProfileRequest {

    @Size(max = 100)
    private String firstName;

    @Size(max = 100)
    private String lastName;

    @Size(max = 20)
    private String phone;

    private LocalDate dateOfBirth;

    @Size(max = 50)
    private String passportNumber;

    @Size(max = 100)
    private String nationality;

    private Gender gender;
}
