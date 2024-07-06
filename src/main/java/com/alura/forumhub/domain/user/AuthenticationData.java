package com.alura.forumhub.domain.user;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationData(
        @NotBlank
        String email,

        @NotBlank
        String password
) {
}
