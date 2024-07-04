package com.alura.forumhub.topic;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "Topic")
@Table(name = "topics")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 100)
    private String title;

    @Column(unique = true)
    private String message;

    @Column(length = 100)
    private String author;
    @Column(length = 100)
    private String course;

    @Column(length = 20)
    private String state;
    private LocalDateTime createdAt;

    public Topic(CreateTopicData data) {
        this.title = data.title();
        this.message = data.message();
        this.author = data.author();
        this.course = data.course();
        this.state = "ativo";
        this.createdAt = LocalDateTime.now();
    }
}
