package com.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    PASSENGER("Passenger"),
    ADMIN("Administrator");

    private final String displayName;


    public boolean isAdmin() {
        return this == ADMIN;
    }
}
