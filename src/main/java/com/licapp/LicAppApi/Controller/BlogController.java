package com.licapp.LicAppApi.Controller;

import com.licapp.LicAppApi.Entity.Blog;
import com.licapp.LicAppApi.Entity.User;
import com.licapp.LicAppApi.Service.BlogService;
import com.licapp.LicAppApi.Service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private UserService userService;

    private String getAuthenticatedUsername() {
        String username = SecurityContextHolder.getContext().getAuthentication() != null
                ? SecurityContextHolder.getContext().getAuthentication().getName()
                : null;
        System.out.println("Authenticated username: " + username);
        return username;
    }

    @GetMapping
    public ResponseEntity<?> getBlog() {
        return new ResponseEntity<>(blogService.getBlog(), HttpStatus.OK);
    }

    @GetMapping("/{title}")
    public ResponseEntity<?> getByTitle(@PathVariable String title) {
        Optional<Blog> blog = blogService.getBlogByTitle(title);
        if (blog.isPresent()) {
            return new ResponseEntity<>(blog.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Blog not found", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{username}/addblog")
    public ResponseEntity<String> addBlog(@PathVariable String username, @RequestBody Blog blog) {
        String authUsername = getAuthenticatedUsername();
        System.out.println("Authenticated username: " + authUsername + ", Path username: " + username);
        if (authUsername == null) {
            System.out.println("Unauthorized access: No authentication");
            return new ResponseEntity<>("Unauthorized access", HttpStatus.FORBIDDEN);
        }
        if (blog.getTitle() == null || blog.getTitle().trim().isEmpty()) {
            System.out.println("Invalid blog data: Title is null or empty");
            return new ResponseEntity<>("Blog title cannot be null or empty", HttpStatus.BAD_REQUEST);
        }
        try {
            System.out.println("Attempting to find user: " + username);
            Optional<User> optionalUser = userService.getuserUsername(username);
            if (optionalUser.isPresent()) {
                blogService.saveBlog(blog);
                System.out.println("Blog saved successfully for user: " + username);
                return new ResponseEntity<>("Blog added successfully", HttpStatus.OK);
            } else {
                System.out.println("User not found in database: " + username);
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            System.out.println("Error adding blog: " + e.getMessage());
            return new ResponseEntity<>("Error adding blog: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{title}")
    public ResponseEntity<String> updateBlog(@PathVariable String title, @RequestBody Blog updatedBlog) {
        if (getAuthenticatedUsername() == null) {
            return new ResponseEntity<>("Unauthorized access", HttpStatus.FORBIDDEN);
        }
        try {
            Blog blog = blogService.updateByTitle(title, updatedBlog);
            if (blog != null) {
                return new ResponseEntity<>("Blog updated successfully", HttpStatus.OK);
            }
            return new ResponseEntity<>("Blog not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating blog: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{title}")
    public ResponseEntity<String> deleteBlog(@PathVariable String title) {
        if (getAuthenticatedUsername() == null) {
            return new ResponseEntity<>("Unauthorized access", HttpStatus.FORBIDDEN);
        }
        try {
            boolean deleted = blogService.deleteByTitle(title);
            if (deleted) {
                return new ResponseEntity<>("Blog deleted successfully", HttpStatus.OK);
            }
            return new ResponseEntity<>("Blog not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting blog: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/test")
    public String test() {
        return "Blog is working";
    }
}