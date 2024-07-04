package com.alura.forumhub.controller;

import com.alura.forumhub.topic.CreateTopicData;
import com.alura.forumhub.topic.DetailsTopicData;
import com.alura.forumhub.topic.Topic;
import com.alura.forumhub.topic.TopicRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("topics")
public class TopicController {

    @Autowired
    private TopicRepository topicRepository;

    @PostMapping
    @Transactional
    public ResponseEntity create(@RequestBody @Valid CreateTopicData data, UriComponentsBuilder uriBuilder) {
        var topic = new Topic(data);
        topicRepository.save(topic);

        var uri = uriBuilder.path("/topics/{id}").buildAndExpand(topic.getId()).toUri();

        return ResponseEntity.created(uri).body(new DetailsTopicData(topic));
    }

    @GetMapping
    public ResponseEntity<Page<DetailsTopicData>> list(
            @RequestParam(required = false, name = "course") String course,
            @RequestParam(required = false, name = "year") String year,
            @PageableDefault(size = 10, sort = {"createdAt"}, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<DetailsTopicData> topics;
        if (course == null && year == null) {
            topics = topicRepository.findAll(pageable).map(DetailsTopicData::new);
        } else if (course == null) {
            topics = topicRepository.findByYear(year, pageable);
        } else if (year == null) {
            topics = topicRepository.findByCourse(course, pageable);
        } else {
            topics = topicRepository.fingByYearAnByCourse(year, course, pageable);
        }

        return ResponseEntity.ok(topics);
    }
}
