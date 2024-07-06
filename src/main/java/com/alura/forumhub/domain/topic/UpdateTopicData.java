package com.alura.forumhub.domain.topic;

public record UpdateTopicData(
        String title,
        String message,
        String course
) {
}
