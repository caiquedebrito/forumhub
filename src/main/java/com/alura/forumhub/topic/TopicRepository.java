package com.alura.forumhub.topic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    @Query("""
        select t from Topic t
        where year(t.createdAt) = :year
    """)
    Page<DetailsTopicData> findByYear(String year, Pageable pageable);

    Page<DetailsTopicData> findByCourse(String course, Pageable pageable);

    @Query("""
        select t from Topic t
        where year(t.createdAt) = :year 
        and t.course = :course
    """)
    Page<DetailsTopicData> fingByYearAnByCourse(String year, String course, Pageable pageable);
}
