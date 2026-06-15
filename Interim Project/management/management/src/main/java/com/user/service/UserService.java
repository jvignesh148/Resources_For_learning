package com.user.service;

import com.user.dto.request.ChangePasswordRequest;
import com.user.dto.request.UpdateProfileRequest;
import com.user.dto.response.UserProfileResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserProfileResponse getProfile(Long userId);

    UserProfileResponse updateProfile(Long userId, UpdateProfileRequest request);

    void changePassword(Long userId, ChangePasswordRequest request);

    void deactivateAccount(Long userId);

    Page<UserProfileResponse> searchUsers(String query, Pageable pageable);
}
