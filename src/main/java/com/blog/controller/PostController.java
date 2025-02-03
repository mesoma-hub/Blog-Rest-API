package com.blog.controller;

import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.PostDTO;
import com.blog.payload.PostResponse;
import com.blog.service.PostService;
import com.blog.utils.AppConstants;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PreAuthorize("hasRole('ADMIN')") // only admins can access this method
    @PostMapping
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO) {
        PostDTO savedPostDTO = this.postService.createPost(postDTO);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location", "/api/posts/" + savedPostDTO.getId());
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    /*Adding support for pagination and sorting*/
    @GetMapping
    public PostResponse getAllPosts(@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NO, required = false) int pageNo,
                                    @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                    @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                    @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
        return this.postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/post/{id}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable(name = "id") Long id, @Valid @RequestBody PostDTO postDTO) {
        PostDTO updatedPostDTO = this.postService.updatePost(id, postDTO);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location", "/api/posts/post/" + updatedPostDTO.getId());
        return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable(name = "id") Long id) {
        PostDTO postDTO = this.postService.getPostById(id).orElseThrow(() ->
                new ResourceNotFoundException("Post", "Id", id));
        return ResponseEntity.ok(postDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/post/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") Long id) {
        this.postService.deletePost(id);
        return new ResponseEntity<>("Post deleted successfully", HttpStatus.OK);
    }
}
/*Pagination & Sorting
* These are done through query parameters. For pagination we have 2 fields we can specify
* 1) pageSize and 2) pageNo
* While for sorting we can specify sortBy as a query parameter in the request url
* */