package com.api.ga5000.ecommerce.services;

import com.api.ga5000.ecommerce.dtos.AddCommentDTO;
import com.api.ga5000.ecommerce.repositories.CommentRepository;
import com.api.ga5000.ecommerce.services.interfaces.ICommentService;

public class CommentService implements ICommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public void addComment(AddCommentDTO addCommentDTO) {

    }
}
