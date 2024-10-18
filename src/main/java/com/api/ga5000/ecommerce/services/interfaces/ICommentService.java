package com.api.ga5000.ecommerce.services.interfaces;

import com.api.ga5000.ecommerce.dtos.AddCommentDTO;

public interface ICommentService {
    void addComment(Long productId, Long userId, AddCommentDTO addCommentDTO);
    void deleteComment(Long commentId);
    void updateComment(Long commentId, AddCommentDTO addCommentDTO);
}
