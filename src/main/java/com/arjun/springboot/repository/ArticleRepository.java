package com.arjun.springboot.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.arjun.springboot.entity.Article;

@Repository
public interface ArticleRepository extends CrudRepository<Article, Long> {
	
	List<Article> findByTitle(String title);
	List<Article> findDistinctByCategory(String category);
	List<Article> findByTitleAndCategory(String title, String category);
	
}
