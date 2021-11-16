package com.rubminds.api.post.domain.repository;

import com.rubminds.api.post.domain.Post;
import com.rubminds.api.post.domain.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SkiilRepository extends JpaRepository<Skill, Long> {
    Optional<Skill> findById(Long id);


}
