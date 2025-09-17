package com.example.app.integrationtest;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Date;
import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.app.repo.Blog;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BlogControllerIntegrationTest {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	@DisplayName("Integration Test - POST and GET Blog through REST API")
	public void createAndGetBlogTest() {
		
		Blog lastBlog = new Blog();
		lastBlog.setTitle("Last Blog");
		lastBlog.setContent("Hi, This blog is from Last Blog");
		lastBlog.setAuthor("Jack Sparrow");
		lastBlog.setDate(Date.valueOf(LocalDate.of(2025, 9, 17)));
		
		//POST blog
		ResponseEntity<Blog> postResponse = restTemplate.postForEntity("/api/blogs/addBlog", lastBlog, Blog.class);
		assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		
		Blog createdBlog = postResponse.getBody();
		assertThat(createdBlog).isNotNull();
		assertThat(createdBlog.getId()).isNotNull();
		
		
		//GET blog
		Long createdBlogId = createdBlog.getId();
		ResponseEntity<Blog> getResponse = restTemplate.getForEntity("/api/blogs/" + createdBlogId, Blog.class);
		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(getResponse.getBody().getTitle()).isEqualTo(createdBlog.getTitle());
	}

}
