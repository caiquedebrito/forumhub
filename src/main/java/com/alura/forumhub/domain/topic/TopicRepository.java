package com.alura.forumhub.domain.topic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    @Query("""
        select t from Topic t
        where year(t.createdAt) = :year
        and t.state != 'DELETED'
    """)
    Page<Topic> findByYear(String year, Pageable pageable);

    @Query("""
        select t from Topic t
        where t.course = :course
        and t.state != 'DELETED'
    """)
    Page<Topic> findByCourse(String course, Pageable pageable);

    @Query("""
        select t from Topic t
        where year(t.createdAt) = :year 
        and t.course = :course
        and t.state != 'DELETED'
    """)
    Page<Topic> findByYearAnByCourse(String year, String course, Pageable pageable);

    @Query("""
        select t from Topic t
        where t.state != 'DELETED'
    """)
    Page<Topic> findAllTopics(Pageable pageable);

    @Query("""
        select t from Topic t
        where t.id = :id
        and t.state != 'DELETED'
    """)
    Topic getTopic(Long id);
}
