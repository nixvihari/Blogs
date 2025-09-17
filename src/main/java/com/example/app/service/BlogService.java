package com.example.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.app.exceptions.BadRequestException;
import com.example.app.exceptions.BlogNotFoundException;
import com.example.app.repo.Blog;
import com.example.app.repo.BlogRepository;

@Service
public class BlogService {

	@Autowired
	BlogRepository blogRepository;
	
	public List<Blog> getBlogs() {
		return blogRepository.findAll();
	}
	
	public Optional<Blog> getBlogById(Long id) throws BadRequestException{
		if (id < 1L) {
			throw new BadRequestException("Request unacceptable as ID of a blog cannot be less than 1");
		}
		
		Optional<Blog> found = blogRepository.findById(id);
		if (found.isEmpty()) {
			throw new BlogNotFoundException("The Requested blog with ID : "+ id +" was not found.");
		}
		return found;
	}
	
	public Blog addBlog(Blog blog) {
		return blogRepository.save(blog);
	}
	
	public void deleteBlogById(Long id) {
		blogRepository.deleteById(id);
	}
	
	public Blog updateBlogById(Long id, Blog newBlog) {
		Optional<Blog> existingBlog = blogRepository.findById(id);
		if(existingBlog.isPresent()) {
			existingBlog.get().setTitle(newBlog.getTitle());
			existingBlog.get().setContent(newBlog.getContent());
			existingBlog.get().setAuthor(newBlog.getAuthor());
			existingBlog.get().setDate(newBlog.getDate());
			return blogRepository.save(existingBlog.get());
		} else {
			return null;
		}
		
	}
}
