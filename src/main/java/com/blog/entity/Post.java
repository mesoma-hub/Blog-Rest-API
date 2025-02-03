package com.blog.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
/*When using ModelMapper only 1 entity should have a toString() or @Data annotation*/
/*The title field is unique*/
@Table(name = "posts", uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})})
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Post {
    /*Primary Key*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /*The title field is not null*/
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "content", nullable = false)
    private String content;
    /*Since in this case Post entity is the winning relationship (one post has many comments)
     it gets to be the one that does the mapping. CascadeType.ALL: when we save the parent
     (ie Post). Then the same applies to the child or children.
     orphanRemoval = true: whenever we remove the parent then the child also gets removed*/
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();
}
