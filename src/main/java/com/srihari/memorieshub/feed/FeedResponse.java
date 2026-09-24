package com.srihari.memorieshub.feed;

import com.srihari.memorieshub.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class FeedResponse {
    private Long postId;
    private String description;   // ✅ match Post entity
    private String authorName;
    private LocalDateTime createdAt;
    private int likeCount;
    private int commentCount;

    public FeedResponse(Post post) {
        this.postId = post.getId();
        this.description = post.getDescription();   // ✅ use description
        this.authorName = post.getUser().getUsername(); // ✅ use username field
        this.createdAt = post.getCreatedAt();
        this.likeCount = post.getLikes().size();
        this.commentCount = post.getComments().size();
    }
}


