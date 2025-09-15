package com.example.app.servicetest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	
	@Test
	@DisplayName("GET BLOG BY ID")
	public void getBlogByIdTest() {
		when(blogRepository.findById(2L)).thenReturn(Optional.of(blog2));
		
		Optional<Blog> blog = blogService.getBlogById(2L);
		
		assertThat(blog).isPresent();
		assertThat(blog.get().getTitle()).isEqualTo("Test Blog 2");
		
	}
	
	@Test
	@DisplayName("ADD BLOG")
	public void addBlogTest() {
		
		Blog newBlog = new Blog();
		newBlog.setId(3L);
		newBlog.setTitle("Blog 3");
		newBlog.setContent("hey, this is from blog 3");
		newBlog.setAuthor("Ben Hur");
		newBlog.setDate(Date.valueOf(LocalDate.of(2025, 9, 15)));
		
		when(blogRepository.save(newBlog)).thenReturn(newBlog);
		
		Blog savedBlog = blogService.addBlog(newBlog);
		
		assertThat(savedBlog).isNotNull();
		assertThat(savedBlog.getTitle()).isEqualTo(newBlog.getTitle());
		assertThat(savedBlog.getAuthor()).isEqualTo(newBlog.getAuthor());
		assertThat(savedBlog.getDate())
					.isEqualTo(Date.valueOf(LocalDate.of(2025, 9, 15)));
		
		verify(blogRepository, times(1)).save(newBlog);	
	}
	
	@Test
	@DisplayName("DELETE BLOG")
	public void deleteBlogTest() {
		blogService.deleteBlogById(2L);
		
		verify(blogRepository, times(1)).deleteById(2L);
		
		when(blogRepository.findById(2L)).thenReturn(Optional.empty());
		
		Optional<Blog> deletedNonExistentBlog = blogService.getBlogById(2L);
		assertThat(deletedNonExistentBlog).isEmpty(); 
	}
	
	@Test
	@DisplayName("UPDATE BLOG")
	public void updateBlogTest() {
		Blog blogToUpdate;
		
		when(blogRepository.findById(2L)).thenReturn(Optional.of(blog2));

		blogToUpdate = blogService.getBlogById(2L).get();

		blogToUpdate.setContent(blog2.getContent());
		blogToUpdate.setDate(blog2.getDate());
		blogToUpdate.setId(blog2.getId());
		
		blogToUpdate.setTitle("Second Blog Updated Title");
		blogToUpdate.setAuthor("Rango");
		
		when(blogRepository.save(blogToUpdate)).thenReturn(blogToUpdate);
		
		Blog updatedBlog = blogService.updateBlogById(2L, blogToUpdate);
		
		verify(blogRepository, times(1)).save(blogToUpdate);
		
		assertThat(updatedBlog).isNotNull();
		assertThat(updatedBlog.getTitle()).isEqualTo(blogToUpdate.getTitle());
		assertThat(updatedBlog.getAuthor()).isEqualTo(blogToUpdate.getAuthor());
	}
}
