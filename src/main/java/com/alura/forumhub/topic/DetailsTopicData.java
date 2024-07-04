package com.alura.forumhub.topic;

import java.time.LocalDateTime;

public record DetailsTopicData(
        Long id,
        String title,
        String message,
        String author,
        String course,
        TopicState state,
        LocalDateTime createdAt
) {
    public DetailsTopicData(Topic topic) {
       this(topic.getId(), topic.getTitle(), topic.getMessage(), topic.getAuthor(), topic.getCourse(), topic.getState(), LocalDateTime.now());
    }
}
