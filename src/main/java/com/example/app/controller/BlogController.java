package com.example.app.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.app.repo.Blog;
import com.example.app.service.BlogService;

@RestController
@RequestMapping("/api/blogs")
public class BlogController {
	
	@Autowired
	BlogService blogService;
	
	@GetMapping
	public List<Blog> getBlogs() {
		return blogService.getBlogs();
	}
	
	@GetMapping("/{blogId}")
	public Optional<Blog> getBlogById(@PathVariable("blogId") Long blogId) {
		return blogService.getBlogById(blogId);
	}
	
	@PostMapping("/addBlog")
	public Blog addBlog(@RequestBody Blog blog) {
		return blogService.addBlog(blog);
	}
	
	@DeleteMapping("/deleteBlog/{blogId}")
	public void deleteBlogById(@PathVariable("blogId") Long id) {
		blogService.deleteBlogById(id);
	}
	
	@PutMapping("/updateBlog/{blogId}")
	public Blog updateBlogById(@PathVariable("blogId") Long id, @RequestBody Blog newBlog) {
		return blogService.updateBlogById(id, newBlog);
	}
}
