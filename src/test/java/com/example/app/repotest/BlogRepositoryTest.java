package com.example.app.repotest;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.example.app.repo.Blog;
import com.example.app.repo.BlogRepository;

@DataJpaTest
//@ActiveProfiles("mysql - test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BlogRepositoryTest {
	
	@Autowired
	private BlogRepository blogRepository;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	@DisplayName("Save Blog Test")
	public void saveBlogTest() {
		Blog blog1 = new Blog();
		blog1.setId(1L);
		blog1.setTitle("Test Blog 1");
		blog1.setContent("Hi, This blog is from Blog 1");
		blog1.setAuthor("Jack Sparrow");
		blog1.setDate(Date.valueOf(LocalDate.of(2025, 9, 5)));
		
		Blog saved = blogRepository.save(blog1);
		assertThat(saved).isNotNull();
		assertThat(saved.getId()).isNotNull();
	}
	
	@Test
	@DisplayName("Find Blog By ID")
	public void testFindById() {
		Blog blog1 = new Blog();
		blog1.setId(1L);
		blog1.setTitle("Test Blog 1");
		blog1.setContent("Hi, This blog is from Blog 1");
		blog1.setAuthor("Jack Sparrow");
		blog1.setDate(Date.valueOf(LocalDate.of(2025, 9, 5)));
		
		Blog saved = entityManager.merge(blog1);
		
		Optional<Blog> found = blogRepository.findById(saved.getId());
		assertThat(found).isPresent();
		assertThat(found.get().getTitle()).isEqualTo("Test Blog 1");
	}
	
	@Test
	@DisplayName("Delete Blog By Id")
	public void deleteById() {
		Blog blog1 = new Blog();
		blog1.setId(1L);
		blog1.setTitle("Test Blog 1");
		blog1.setContent("Hi, This blog is from Blog 1");
		blog1.setAuthor("Jack Sparrow");
		blog1.setDate(Date.valueOf(LocalDate.of(2025, 9, 5)));
		
		Blog saved = entityManager.merge(blog1);
		
		blogRepository.deleteById(blog1.getId());
		Optional<Blog> found = blogRepository.findById(blog1.getId());
		assertThat(found).isEmpty();
		
	}

}
