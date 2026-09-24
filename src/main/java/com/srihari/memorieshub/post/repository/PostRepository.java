package com.srihari.memorieshub.post.repository;



import com.srihari.memorieshub.post.entity.Category;
import com.srihari.memorieshub.post.entity.Post;
import com.srihari.memorieshub.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // ✅ For a single user
    List<Post> findByUserOrderByCreatedAtDesc(User user);

    // ✅ For multiple users (following list)
    List<Post> findByUserInOrderByCreatedAtDesc(List<User> users);

    // Optional: if you just want posts by multiple users without ordering
    List<Post> findByUserIn(List<User> users);

    List<Post> findByUserId(Long userId);

    List<Post> findByCategory(Category category);
}

