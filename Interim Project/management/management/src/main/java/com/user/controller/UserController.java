package com.user.controller;

import com.user.dto.request.ChangePasswordRequest;
import com.user.dto.request.UpdateProfileRequest;
import com.user.dto.response.ApiResponse;
import com.user.dto.response.UserProfileResponse;
import com.user.entity.User;
import com.user.security.CustomUserDetailsService;
import com.user.service.UserService;
import com.user.service.UserServiceImplementation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "Profile CRUD and admin search")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    @Operation(summary = "Get current user's profile")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getMyProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        UserProfileResponse profile = userService.getProfile(userDetails.getUserId());
        return ResponseEntity.ok(
                ApiResponse.ok("Profile retrieved", profile));
    }

    @PutMapping("/me")
    @Operation(summary = "Update current user's profile")
    public ResponseEntity<ApiResponse<UserProfileResponse>> updateProfile(
            @AuthenticationPrincipal CustomUserDetailsService.CustomUserDetails userDetails,
            @Valid @RequestBody UpdateProfileRequest request) {

        UserProfileResponse updated =
                userService.updateProfile(userDetails.getUserId(), request);
        return ResponseEntity.ok(
                ApiResponse.ok("Profile updated", updated));
    }

    @PutMapping("/me/password")
    @Operation(summary = "Change current user's password")
    public ResponseEntity<ApiResponse<Void>> changePassword(
            @AuthenticationPrincipal CustomUserDetailsService.CustomUserDetails userDetails,
            @Valid @RequestBody ChangePasswordRequest request) {

        userService.changePassword(userDetails.getUserId(), request);
        return ResponseEntity.ok(ApiResponse.ok("Password changed successfully"));
    }

    @DeleteMapping("/me")
    @Operation(summary = "Deactivate current user's account (soft delete)")
    public ResponseEntity<ApiResponse<Void>> deactivateAccount(
            @AuthenticationPrincipal CustomUserDetailsService.CustomUserDetails userDetails) {

        userService.deactivateAccount(userDetails.getUserId());
        return ResponseEntity.ok(ApiResponse.ok("Account deactivated"));
    }

    // ----- Admin endpoint -----

    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Search users (admin only)")
    public ResponseEntity<ApiResponse<Page<UserProfileResponse>>> searchUsers(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Page<UserProfileResponse> results =
                userService.searchUsers(query, PageRequest.of(page, size));
        return ResponseEntity.ok(
                ApiResponse.ok("Search results", results));
    }

    // ----- Internal endpoint (for other microservices) -----

    @GetMapping("/{userId}")
    @Operation(summary = "Get user by ID (internal service-to-service)")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getUserById(
            @PathVariable Long userId) {

        UserProfileResponse profile = userService.getProfile(userId);
        return ResponseEntity.ok(
                ApiResponse.ok("User found", profile));
    }
}
