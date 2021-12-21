package com.rubminds.api.post.domain;

import com.rubminds.api.common.domain.BaseEntity;
import com.rubminds.api.post.dto.PostRequest;
import com.rubminds.api.skill.domain.CustomSkill;
import com.rubminds.api.team.domain.Team;
import com.rubminds.api.user.domain.User;
import com.rubminds.api.user.security.userdetails.CustomUserDetails;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "post")
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User writer;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int headcount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Kinds kinds;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Meeting meeting;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PostStatus postStatus;

    @Column(nullable = false)
    private String region;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<PostSkill> postSkills = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<CustomSkill> customSkills = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private Set<PostLike> postLikeList = new LinkedHashSet<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    public boolean isLike(CustomUserDetails customUserDetails) {
        return customUserDetails != null && this.postLikeList.stream().anyMatch(postLike -> postLike.getUser().getId().equals(customUserDetails.getUser().getId()));
    }

    public void update(PostRequest.CreateOrUpdate request) {
        this.title = request.getTitle();
        this.content = request.getContent();
        this.headcount = request.getHeadcount();
        this.meeting = request.getMeeting();
    }

    public static Post create(PostRequest.CreateOrUpdate request, Team team, User user) {
        return Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .headcount(request.getHeadcount())
                .kinds(request.getKinds())
                .kinds(Kinds.PROJECT)
                .meeting(request.getMeeting())
                .postStatus(PostStatus.RECRUIT)
                .region(request.getRegion())
                .writer(user)
                .team(team)
                .build();
    }
}