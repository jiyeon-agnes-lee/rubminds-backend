package com.rubminds.api.post.dto;

import com.rubminds.api.post.domain.Post;
import com.rubminds.api.post.domain.PostEnumClass.Kinds;
import com.rubminds.api.post.domain.PostEnumClass.Meeting;
import com.rubminds.api.post.domain.PostEnumClass.PostStatus;
import com.rubminds.api.post.domain.PostEnumClass.Region;
import com.rubminds.api.skill.domain.CostomSkill;
import com.rubminds.api.skill.domain.PostSkill;
import com.rubminds.api.skill.dto.CostomSkillResponse;
import com.rubminds.api.skill.dto.PostSkillResponse;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

public class PostResponse {
    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class OnlyId {
        private Long id;

        public static PostResponse.OnlyId build(Post post) {
            return PostResponse.OnlyId.builder()
                    .id(post.getId())
                    .build();
        }

    }
    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Info {
        private Long id;
        private String writer;
        private String title;
        private String content;
        private int headcount;
        private Kinds kinds;
        private Meeting meeting;
        private PostStatus postsStatus;
        private Region region;
        private List<PostSkillResponse.GetPostSkill> postSkills;
        private List<CostomSkillResponse.GetCostomSkill> customSkills;
        private boolean postLikeStatus;

        public static PostResponse.Info build(Post post, List<PostSkill> postSkills, List<CostomSkill> customSkills, boolean postLikeStatus) {
            return PostResponse.Info.builder()
                    .id(post.getId())
                    .writer(post.getWriter().getNickname())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .headcount(post.getHeadcount())
                    .kinds(post.getKinds())
                    .meeting(post.getMeeting())
                    .postsStatus(post.getPostStatus())
                    .region(post.getRegion())
                    .postSkills(postSkills.stream().map(PostSkillResponse.GetPostSkill::build).collect(Collectors.toList()))
                    .customSkills(customSkills.stream().map(CostomSkillResponse.GetCostomSkill::build).collect(Collectors.toList()))
                    .postLikeStatus(postLikeStatus)
                    .build();
        }

    }
    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GetPostLike {
        private boolean postLikeStatus;

        public static PostResponse.GetPostLike build(boolean postLikeStatus) {
            return PostResponse.GetPostLike.builder()
                    .postLikeStatus(postLikeStatus)
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GetPost {
        private Long id;
        private String writer;
        private String title;
        private Kinds kinds;
        private Meeting meeting;
        private PostStatus postStatus;
        private Region region;
        private List<PostSkillResponse.GetPostSkill> postSkills;
        private List<CostomSkillResponse.GetCostomSkill> customSkills;
        private boolean postLikeStatus;

        public static PostResponse.GetPost build(Post post, boolean postLikeStatus) {
            return PostResponse.GetPost.builder()
                    .id(post.getId())
                    .writer(post.getWriter().getNickname())
                    .title(post.getTitle())
                    .kinds(post.getKinds())
                    .meeting(post.getMeeting())
                    .postStatus(post.getPostStatus())
                    .region(post.getRegion())
                    .postSkills(post.getPostSkills().stream().map(PostSkillResponse.GetPostSkill::build).collect(Collectors.toList()))
                    .customSkills(post.getCostomSkills().stream().map(CostomSkillResponse.GetCostomSkill::build).collect(Collectors.toList()))
                    .postLikeStatus(postLikeStatus)
                    .build();
        }

    }
    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GetPosts{
        private List<PostResponse.GetPost> posts;

        public static PostResponse.GetPosts build(List<PostResponse.GetPost> posts) {
            return PostResponse.GetPosts.builder()
                    .posts(posts)
                    .build();
        }
    }

}
