package com.example.app.integrationtest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.app.exceptions.BadRequestException;
import com.example.app.exceptions.BlogNotFoundException;
import com.example.app.repo.Blog;
import com.example.app.service.BlogService;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class BlogServiceIntegrationTest {
	
	@Autowired
	private BlogService blogService;
	
	@Test
	@DisplayName("Integration Test - Save and get all blogs")
	public void getAllBlogsTest() {
		
		Blog lastBlog = new Blog();
//		lastBlog.setId(11L);
		lastBlog.setTitle("Last Blog");
		lastBlog.setContent("Hi, This blog is from Last Blog");
		lastBlog.setAuthor("Jack Sparrow");
		lastBlog.setDate(Date.valueOf(LocalDate.of(2025, 9, 17)));
		
		blogService.addBlog(lastBlog);
		
		List<Blog> blogs = blogService.getBlogs();
		
		assertThat(blogs).isNotEmpty();
		assertThat(blogs.getLast().getTitle()).isEqualTo("Last Blog");
	}
	
	@Test
	@DisplayName("Integration Test - Missing Blog")
	public void getBlogById_ThrowBlogNotFoundExceptionWhenNotFound() {
		Long missing_id = 1000L;
		
		assertThrows(BlogNotFoundException.class , () -> blogService.getBlogById(missing_id));
	}
	
	@Test
	@DisplayName("Integration Test - Request ID less than 1")
	public void getBlogById_ThrowBadRequestExceptionWhenIdLessThan1() {
		Long invalid_id = 0L;
		
		assertThrows(BadRequestException.class, () -> blogService.getBlogById(invalid_id));
	}
}
