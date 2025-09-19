package com.rido.shop.repository;

import com.rido.shop.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByParentId(Long parentId);
}
