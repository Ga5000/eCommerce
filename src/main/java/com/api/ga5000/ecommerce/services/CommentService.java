package com.api.ga5000.ecommerce.services;

import com.api.ga5000.ecommerce.dtos.AddCommentDTO;
import com.api.ga5000.ecommerce.entities.Comment;
import com.api.ga5000.ecommerce.entities.Product;
import com.api.ga5000.ecommerce.entities.User;
import com.api.ga5000.ecommerce.repositories.CommentRepository;
import com.api.ga5000.ecommerce.repositories.UserRepository;
import com.api.ga5000.ecommerce.services.interfaces.ICommentService;
import org.springframework.transaction.annotation.Transactional;

public class CommentService implements ICommentService {
    private final CommentRepository commentRepository;
    private final ProductService productService;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, ProductService productService, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.productService = productService;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public void addComment(Long productId, Long userId, AddCommentDTO addCommentDTO) {
        Product product = findProductById(productId);
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        Comment comment = new Comment();
        comment.setProduct(product);
        comment.setContent(addCommentDTO.content());
        comment.setRating(addCommentDTO.rating());
        comment.setUser(user);

        commentRepository.save(comment);
    }

    @Transactional
    @Override
    public void deleteComment(Long commentId) {
        Comment comment = findCommentById(commentId);
        commentRepository.delete(comment);
    }

    @Transactional
    @Override
    public void updateComment(Long commentId, AddCommentDTO addCommentDTO) {
        Comment comment = findCommentById(commentId);
        comment.setContent(addCommentDTO.content());
        comment.setRating(addCommentDTO.rating());

        commentRepository.save(comment);
    }

    private Product findProductById(Long productId) {
        return productService.getProductById(productId);
    }

    private Comment findCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("Comment not found"));
    }


}
