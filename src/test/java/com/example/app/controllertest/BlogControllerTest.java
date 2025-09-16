package com.example.app.controllertest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.app.controller.BlogController;
import com.example.app.repo.Blog;
import com.example.app.service.BlogService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(BlogController.class)
class BlogControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	Blog blog;
	
	@MockitoBean
	BlogService blogService;
	
	@BeforeEach
	public void setup() {
		blog = new Blog();
		blog.setId(1L);
		blog.setTitle("Test Blog 1");
		blog.setContent("Hi, This blog is from Blog 1");
		blog.setAuthor("Jack Sparrow");
		blog.setDate(Date.valueOf(LocalDate.of(2025, 9, 5)));

	}
	
	@Test
	@DisplayName("Get all blogs (Endpoint)")
	public void getBlogsTest() throws Exception{
		when(blogService.getBlogs())
		.thenReturn(new ArrayList<Blog>(List.of(blog)));
		
		mockMvc.perform(get("/api/blogs"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].title").value(blog.getTitle()));
	}
	
	@Test
	@DisplayName("Get Blog by ID (Endpoint)")
	public void getBlogByIdTest() throws Exception{
		when(blogService.getBlogById(1L))
		.thenReturn(Optional.of(blog));
		
		mockMvc.perform(get("/api/blogs/1"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id").value(1L));
	}
	
	@Test
	@DisplayName("Add Blog (Endpoint)")
	public void addBlogTest() throws Exception {
		
		//When this method receives and json with schema same as Blog.class, return blog.
		when(blogService.addBlog(any(Blog.class)))
		.thenReturn(blog);
		
		//to test the endpoint we need to supply a json body as content
		ObjectMapper objMapper = new ObjectMapper();
		String json = objMapper.writeValueAsString(blog);
		
		mockMvc.perform(post("/api/blogs/addBlog")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id").value(blog.getId()));
	}
	
	@Test
	@DisplayName("Delete a blog (Endpoint)")
	public void deleteBlogByIdTest() throws Exception{
		doNothing().when(blogService).deleteBlogById(blog.getId());
		
		mockMvc.perform(delete("/api/blogs/deleteBlog/{blogId}", blog.getId()))
		.andDo(print())
		.andExpect(status().isNoContent());
		
	}
	
	@Test
	@DisplayName("Update a Blog (Endpoint)")
	public void updateBlogById() throws Exception {
		Blog updatedBlog = new Blog();
		updatedBlog.setId(blog.getId());   //intact
		updatedBlog.setDate(blog.getDate());
		updatedBlog.setContent(blog.getContent());
		updatedBlog.setAuthor(blog.getAuthor());
		
		updatedBlog.setTitle("My First Blog");
		
		when(blogService.updateBlogById(eq(blog.getId()), any(Blog.class))).thenReturn(updatedBlog);
		
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(updatedBlog);
		
		mockMvc.perform(put("/api/blogs/updateBlog/{blogId}", blog.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.title").value("My First Blog"));
	}

}
