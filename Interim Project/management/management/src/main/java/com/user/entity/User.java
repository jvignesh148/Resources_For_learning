package com.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(nullable = false, unique = true, length = 255)
    @Email
    private String email;

    @Column(name = "password_hash", nullable = false, length = 255)
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must be of length of 8 with uppercase, lowercase, digit, and special character"
    )
    private String passwordHash;

    @Column(length = 20)
    @Pattern(regexp = "^[6-9][0-9]{9}$", message = "Phone number should have 10 digits and first digit should be 6,7 ,8 or 9")
    private String phone;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "profile_image_url", length = 500)
    private String profileImageUrl;

    @Column(name = "passport_number", length = 50)
    private String passportNumber;

    @Column(length = 100)
    private String nationality;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private Role role = Role.PASSENGER;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public enum Role    { PASSENGER, ADMIN }
    public enum Gender  { MALE, FEMALE, OTHER }

    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }

}
