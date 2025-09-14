package com.example.app.servicetest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.app.repo.Blog;
import com.example.app.repo.BlogRepository;
import com.example.app.service.BlogService;

@ExtendWith(MockitoExtension.class)
class BlogServiceTest {

	@Mock
	private BlogRepository blogRepository;

	@InjectMocks
	private BlogService blogService;

	private Blog blog1;
	private Blog blog2;

	@BeforeEach
	public void setup() {
		blog1 = new Blog();
		blog1.setId(1L);
		blog1.setTitle("Test Blog 1");
		blog1.setContent("Hi, This blog is from Blog 1");
		blog1.setAuthor("Jack Sparrow");
		blog1.setDate(Date.valueOf(LocalDate.of(2025, 9, 5)));

		blog2 = new Blog();
		blog2.setId(2L);
		blog2.setTitle("Test Blog 2");
		blog2.setContent("2222222222222222222222222222222");
		blog2.setAuthor("Terminator is back , get ready");
		blog2.setDate(Date.valueOf(LocalDate.of(2025, 9, 8)));
	}

	@Test
	@DisplayName("GET ALL BLOGS")
	public void getBlogs() {
		when(blogRepository.findAll()).thenReturn(new ArrayList<>(List.of(blog1, blog2)));
		
		List<Blog> blogs = blogService.getBlogs();
		
		assertThat(blogs).isNotNull();
		assertThat(blogs).hasSize(2);
		assertThat(blogs.get(0).getTitle()).isEqualTo("Test Blog 1");
	}
}
