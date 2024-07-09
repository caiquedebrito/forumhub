package com.alura.forumhub.domain.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record RegisterUserData(
        @NotNull
        String name,
        @NotNull
        @Email
        String email,
        @NotNull
        String password
) {
}
