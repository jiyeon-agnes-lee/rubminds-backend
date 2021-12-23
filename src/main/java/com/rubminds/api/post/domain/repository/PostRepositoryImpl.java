package com.rubminds.api.post.domain.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rubminds.api.post.domain.Kinds;
import com.rubminds.api.post.domain.Post;
import com.rubminds.api.post.domain.PostStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Objects;
import java.util.Optional;

import static com.rubminds.api.post.domain.QPost.post;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Post> findByIdWithSkillAndUser(Long postId) {
        return Optional.ofNullable(queryFactory.selectFrom(post)
                .leftJoin(post.customSkills).fetchJoin()
                .leftJoin(post.postLikeList).fetchJoin()
                .leftJoin(post.postFileList).fetchJoin()
                .join(post.writer).fetchJoin()
                .where(post.id.eq(postId))
                .fetchOne());
    }

    @Override
    public Page<Post> findAllByKindsAndStatus(Kinds kinds, PostStatus postStatus, Pageable pageable) {
        final QueryResults<Post> result = queryFactory.selectFrom(post)
                .where(postKindsEq(kinds))
                .where(postStatusEq(postStatus))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(post.id.desc())
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    private BooleanExpression postKindsEq(Kinds kinds) {
        if (Objects.nonNull(kinds)) {
            return post.kinds.eq(kinds);
        }
        return null;
    }

    private BooleanExpression postStatusEq(PostStatus postStatus) {
        if (Objects.nonNull(postStatus)) {
            return post.postStatus.eq(postStatus);
        }
        return null;
    }
}