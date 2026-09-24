package com.srihari.memorieshub.feed;

import com.srihari.memorieshub.post.repository.PostRepository;
import com.srihari.memorieshub.user.entity.User;
import com.srihari.memorieshub.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public FeedService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public List<FeedResponse> getFeed(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<User> following = user.getFollowing();

        return postRepository.findByUserInOrderByCreatedAtDesc(following)
                .stream()
                .map(FeedResponse::new)
                .collect(Collectors.toList());
    }

}

