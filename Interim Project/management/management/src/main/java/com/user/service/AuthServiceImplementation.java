package com.user.service;

import com.user.dto.request.LoginRequest;
import com.user.dto.request.RegisterRequest;
import com.user.dto.response.AuthResponse;
import com.user.dto.response.UserProfileResponse;
import com.user.entity.RefreshToken;
import com.user.entity.User;
import com.user.enums.Role;
import com.user.entity.User.Gender;
import com.user.exception.InvalidTokenException;
import com.user.exception.UserNotFoundException;
import com.user.repository.RefreshTokenRepository;
import com.user.repository.UserRepository;
import com.user.security.JwtTokenProvider;
import lombok.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.user.exception.UserAlreadyExistException;

import java.time.Instant;
import java.util.UUID;

public class AuthServiceImplementation implements AuthService{

    private UserRepository userRepo;
    private RefreshTokenRepository refreshTokenRepo;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtProvider;
    private AuthenticationManager authManager;

    @Value("${jwt.refresh-expiration-ms}")
    private Long refreshExpirationMs;


    @Override
    public AuthResponse register(RegisterRequest request) {

        if (userRepo.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistException(
                    "Email already registered: " + request.getEmail());
        }

        if (request.getPhone() != null && userRepo.existsByPhone(request.getPhone())) {
            throw new UserAlreadyExistException(
                    "Phone already registered: " + request.getPhone());
        }

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .dateOfBirth(request.getDateOfBirth())
                .nationality(request.getNationality())
                .gender(request.getGender())
                .role(Role.PASSENGER)
                .build();

        userRepo.save(user);
        return generateAuthResponse(user);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()));

        User user = (User) userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return generateAuthResponse(user);
    }

    @Override
    public AuthResponse refreshToken(String refreshToken) {

        RefreshToken token = refreshTokenRepo.findByToken(refreshToken)
                .orElseThrow(() -> new InvalidTokenException("Invalid refresh token"));

        if (token.isExpired()) {
            refreshTokenRepo.delete(token);
            throw new InvalidTokenException("Refresh token has expired. Please login again.");
        }

        User user = token.getUser();

        // Delete old token (rotation)
        refreshTokenRepo.delete(token);

        return generateAuthResponse(user);
    }

    @Override
    public void logout(String refreshToken) {
        refreshTokenRepo.findByToken(refreshToken)
                .ifPresent(refreshTokenRepo::delete);
    }

    private AuthResponse generateAuthResponse(User user) {
        String accessToken = jwtProvider.generateToken(user);
        String refreshToken = createRefreshToken(user);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(jwtProvider.getExpirationMs())
                .user(mapToProfile(user))
                .build();
    }

    private String createRefreshToken(User user) {
        // Remove any existing refresh token for this user
        refreshTokenRepo.deleteByUser(user);

        RefreshToken token = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(refreshExpirationMs))
                .build();

        return refreshTokenRepo.save(token).getToken();
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
