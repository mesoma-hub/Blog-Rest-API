package com.blog.service.impl;

import com.blog.entity.Comment;
import com.blog.entity.Post;
import com.blog.exception.BlogAPIException;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.CommentDTO;
import com.blog.repository.CommentRepository;
import com.blog.repository.PostRepository;
import com.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository,
                              ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    private CommentDTO mapToDTO(Comment comment) {
        return modelMapper.map(comment, CommentDTO.class);
    }

    private Comment mapToComment(CommentDTO dto) {
        return modelMapper.map(dto, Comment.class);
    }

    @Override
    public CommentDTO createComment(Long postId, CommentDTO commentDTO) {
        Comment comment = mapToComment(commentDTO);
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "id", postId));
        comment.setPost(post);
        Comment savedComment = commentRepository.save(comment);
        return mapToDTO(savedComment);
    }

    @Override
    public List<CommentDTO> getCommentsByPostId(Long postId) {
        List<Comment> comments = this.commentRepository.findByPostId(postId);
        return comments.stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public void deleteCommentById(Long postId, Long commentId) {
       Comment commentToDelete = getComment(postId, commentId);
        commentRepository.delete(commentToDelete);
    }

    @Override
    public CommentDTO updateComment(Long postId, Long commentId, CommentDTO commentDTO) {
        Comment comment = getComment(postId, commentId);
        comment.setBody(commentDTO.getBody());
        comment.setEmail(commentDTO.getEmail());
        comment.setName(commentDTO.getName());
        Comment savedComment = commentRepository.save(comment);
        return mapToDTO(savedComment);
    }

    @Override
    public CommentDTO getCommentById(Long postId, Long commentId) {
        Comment comment = getComment(postId, commentId);
        return mapToDTO(comment);
    }

    private Comment getComment(Long postId, Long commentId) {
        /*Retrieve post entity by id*/
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "ID", postId));

        /*Retrieve comment*/
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "ID", commentId));

        /*Check if this comment belongs to this post*/
        if (!comment.getPost().getId().equals(postId)) {
           throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post with ID: " + postId);
        }
        return comment;
    }
}
