package com.blog.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
/*This makes the username and email fields unique keys in the table*/
@Table(name = "users",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"email"})
        })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String username;
    private String email;
    private String password;

    /*FetchType.EAGER retrieves the roles along with the user*/
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    /*@ManyToMany uses a third table to maintain foreign key constraints*/
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();
}
