package com.alura.forumhub.domain.user;

public record CreatedUserData(Long id, String name, String email, String token) {

    public CreatedUserData(User user, String token) {
        this(user.getId(), user.getName(), user.getEmail(), token);
    }
}
