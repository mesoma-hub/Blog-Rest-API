package com.blog.service;

import com.blog.payload.PostDTO;
import com.blog.payload.PostResponse;

import java.util.List;
import java.util.Optional;

public interface PostService {
    PostDTO createPost(PostDTO postDTO);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDTO updatePost(Long id, PostDTO postDTO);

    Optional<PostDTO> getPostById(Long id);

    void deletePost(Long id);
}
