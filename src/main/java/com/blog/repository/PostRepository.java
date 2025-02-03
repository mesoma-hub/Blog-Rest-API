package com.blog.repository;

import com.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

/*We don't need the @Repository annotation because JpaRepository class has an
implementation that is already annotated with @Repository*/
public interface PostRepository extends JpaRepository<Post, Long> {
}
