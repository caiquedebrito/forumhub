package com.alura.forumhub.controller;

import com.alura.forumhub.domain.topic.*;
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

import java.util.Optional;

@RestController
@RequestMapping("topics")
public class TopicController {

    @Autowired
    private TopicRepository topicRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<DetailsTopicData> create(@RequestBody @Valid CreateTopicData data, UriComponentsBuilder uriBuilder) {
        var topic = new Topic(data);
        topicRepository.save(topic);

        var uri = uriBuilder.path("/topics/{id}").buildAndExpand(topic.getId()).toUri();

        return ResponseEntity.created(uri).body(new DetailsTopicData(topic));
    }

    @GetMapping
    public ResponseEntity<Page<DetailsTopicData>> list(
            @RequestParam(required = false, name = "course") Optional<String> course,
            @RequestParam(required = false, name = "year") Optional<String> year,
            @PageableDefault(sort = {"createdAt"}, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<Topic> topics;

        if (course.isPresent() && year.isPresent()) {
            topics = topicRepository.findByYearAnByCourse(year.get(), course.get(), pageable);
        } else if (course.isPresent()) {
            topics = topicRepository.findByCourse(course.get(), pageable);
        } else if (year.isPresent()) {
            topics = topicRepository.findByYear(year.get(), pageable);
        } else {
            topics = topicRepository.findAllTopics(pageable);
        }

        return ResponseEntity.ok(topics.map(DetailsTopicData::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetailsTopicData> get(@PathVariable Long id) {
        var topic = topicRepository.getTopic(id);
        return ResponseEntity.ok(new DetailsTopicData(topic));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DetailsTopicData> update(@PathVariable Long id, @RequestBody UpdateTopicData data) {
        var topic = topicRepository.getTopic(id);
        topic.update(data);

        return ResponseEntity.ok(new DetailsTopicData(topic));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id) {
        var topic = topicRepository.getReferenceById(id);
        topic.delete();

        return ResponseEntity.noContent().build();
    }
}
