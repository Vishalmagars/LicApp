package com.licapp.LicAppApi.Service;

import com.licapp.LicAppApi.Entity.Blog;
import com.licapp.LicAppApi.Entity.User;
import com.licapp.LicAppApi.Repo.BlogRepo;
import com.licapp.LicAppApi.Repo.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BlogService {

    @Autowired
    BlogRepo blogRepo;

    @Autowired
    UserRepo userRepo;

    public Optional<Blog> getBlogByTitle(String title) {
        return blogRepo.findByTitle(title);
    }

    public Optional<User> getBlogByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public void saveBlog(Blog blog) {
        blogRepo.save(blog);
    }

    public List<Blog> getBlog() {
        return blogRepo.findAll();
    }

    public Blog updateByTitle(String title, Blog updatedBlog) {
        Optional<Blog> optionalBlog = blogRepo.findByTitle(title);
        if (optionalBlog.isPresent()) {
            Blog existingBlog = optionalBlog.get();
            existingBlog.setTitle(updatedBlog.getTitle());
            existingBlog.setDescription(updatedBlog.getDescription());
            existingBlog.setImg(updatedBlog.getImg());
            existingBlog.setTags(updatedBlog.getTags());
            existingBlog.setContent(updatedBlog.getContent());
            existingBlog.setAuthor(updatedBlog.getAuthor());
            existingBlog.setPostdate(updatedBlog.getPostdate());
            return blogRepo.save(existingBlog);
        }
        return null;
    }

    public boolean deleteByTitle(String title) {
        if (blogRepo.findByTitle(title).isPresent()) {
            blogRepo.deleteByTitle(title);
            return true;
        }
        return false;
    }




}