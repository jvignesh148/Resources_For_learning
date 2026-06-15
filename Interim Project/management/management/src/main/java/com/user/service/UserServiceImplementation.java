package com.user.service;

import com.user.dto.request.ChangePasswordRequest;
import com.user.dto.request.UpdateProfileRequest;
import com.user.dto.response.UserProfileResponse;
import com.user.entity.User;
import com.user.exception.UserNotFoundException;
import com.user.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImplementation implements UserService{

    private UserRepository userRepo;

    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserProfileResponse getProfile(Long userId) {
        User user = findUserOrThrow(userId);
        return mapToProfile(user);
    }

    @Override
    public UserProfileResponse updateProfile(Long userId, UpdateProfileRequest req) {
        User user = findUserOrThrow(userId);

        if (req.getFirstName() != null)
            user.setFirstName(req.getFirstName());
        if (req.getLastName() != null)
            user.setLastName(req.getLastName());
        if (req.getPhone() != null)
            user.setPhone(req.getPhone());
        if (req.getDateOfBirth() != null)
            user.setDateOfBirth(req.getDateOfBirth());
        if (req.getPassportNumber() != null)
            user.setPassportNumber(req.getPassportNumber());
        if (req.getNationality() != null)
            user.setNationality(req.getNationality());
        if (req.getGender() != null)
            user.setGender(req.getGender());

        return mapToProfile(userRepo.save(user));
    }

    @Override
    public void changePassword(Long userId, ChangePasswordRequest request) {
        User user = findUserOrThrow(userId);

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }

        user.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
        userRepo.save(user);
    }

    @Override
    public void deactivateAccount(Long userId) {
        findUserOrThrow(userId);
        userRepo.softDeleteUser(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserProfileResponse> searchUsers(String query, Pageable pageable) {
        return userRepo.searchUsers(query, pageable).map(this::mapToProfile);
    }

    // ---------- Private helpers ----------

    private User findUserOrThrow(Long userId) {
        return userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(
                        "User not found with ID: " + userId));
    }

    private UserProfileResponse mapToProfile(User user) {
        return UserProfileResponse.builder()
                .userId(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .dateOfBirth(user.getDateOfBirth())
                .passportNumber(user.getPassportNumber())
                .nationality(user.getNationality())
                .gender(user.getGender())
                .role(user.getRole())
                .profileImageUrl(user.getProfileImageUrl())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
