package com.api.ga5000.ecommerce.repositories;

import com.api.ga5000.ecommerce.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
