package com.blog.controller;

import com.blog.entity.Comment;
import com.blog.payload.CommentDTO;
import com.blog.payload.PostResponse;
import com.blog.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {

    private final CommentService commentService;
    public static final String POST_PATH = "/api/posts/";
    public static final String COMMENTS_PATH = POST_PATH + "{postId}/comments";
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(@PathVariable(name = "postId") Long id,
                                                    @Valid @RequestBody CommentDTO commentDTO) {
        CommentDTO savedCommentDTO = commentService.createComment(id, commentDTO);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location", POST_PATH + id + "/" +
                "comments/" + savedCommentDTO.getId());
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}/comments")
    public List<CommentDTO> getAllPostComments(@PathVariable(name = "postId") Long id) {
        return this.commentService.getCommentsByPostId(id);
    }

    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> getCommentsById(@PathVariable(name = "postId") Long postId,
                                      @PathVariable(name = "commentId") Long commentId) {
        CommentDTO commentDTO = commentService.getCommentById(postId, commentId);

        return ResponseEntity.ok(commentDTO);
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> updateCommentById(@PathVariable(name = "postId") Long postId,
                                                        @PathVariable(name = "commentId") Long commentId,
                                                        @Valid @RequestBody CommentDTO commentDTO) {
        CommentDTO updatedCommentDTO = commentService.updateComment(postId, commentId, commentDTO);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location", "/api/posts/" + postId  + "/comments" +
                updatedCommentDTO.getId());
        return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteCommentById(@PathVariable(name = "postId") Long postId,
                                                    @PathVariable(name = "commentId") Long commentId) {
        commentService.deleteCommentById(postId, commentId);
        return new ResponseEntity<>("Deleted comment with Id: %d successfully".formatted(commentId),
                HttpStatus.OK);

    }
}
