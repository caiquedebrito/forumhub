package com.alura.forumhub.topic;

public record UpdateTopicData(
        String title,
        String message,
        String course
) {
}
