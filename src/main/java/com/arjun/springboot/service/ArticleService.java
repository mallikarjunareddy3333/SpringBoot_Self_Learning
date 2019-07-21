package com.arjun.springboot.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arjun.springboot.entity.Article;
import com.arjun.springboot.repository.ArticleRepository;

@Service
public class ArticleService implements IArticleService {

	@Autowired
	private ArticleRepository articleRepo;

	@Override
	public List<Article> getAllArticles() {
		List<Article> list = new ArrayList<>();
		articleRepo.findAll().forEach(e -> list.add(e));
		return list;
	}

	@Override
	public Article getArticleById(long articleId) {
		return articleRepo.findById(articleId).get();
	}

	@Override
	public synchronized boolean addArticle(Article article) {
		List<Article> list = articleRepo.findByTitleAndCategory(article.getTitle(), article.getCategory());
		if (list.size() > 0) {
			return false;
		} else {
			articleRepo.save(article);
			return true;
		}
	}

	@Override
	public void updateArticle(Article article) {
		articleRepo.save(article);
	}

	@Override
	public void deleteArticle(int articleId) {
		articleRepo.delete(getArticleById(articleId));
	}

}
