package com.alura.forumhub.domain.topic;


import jakarta.validation.constraints.NotBlank;

public record CreateTopicData(
        @NotBlank
        String title,
        @NotBlank
        String message,
        @NotBlank
        String author,
        @NotBlank
        String course) {
}
