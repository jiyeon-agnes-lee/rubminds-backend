package com.rubminds.api.skill.dto;

import com.rubminds.api.post.domain.Post;
import com.rubminds.api.skill.domain.Skill;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostSkillRequest {
    private Long skillId;
    private Long postId;
}