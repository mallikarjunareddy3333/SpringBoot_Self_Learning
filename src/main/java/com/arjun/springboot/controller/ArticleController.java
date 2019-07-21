package com.arjun.springboot.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.arjun.springboot.entity.Article;
import com.arjun.springboot.service.IArticleService;
import com.arjun.springboot.vo.ArticleVO;

@RestController
@RequestMapping("user")
//@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:8080" })
public class ArticleController {

	@Autowired
	private IArticleService articleService;

	// Fetches article by id
	@GetMapping(value = "/article/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ArticleVO> getArticleById(@PathVariable Integer id) {
		ArticleVO obj = new ArticleVO();
		BeanUtils.copyProperties(articleService.getArticleById(id), obj);
		return new ResponseEntity<ArticleVO>(obj, HttpStatus.OK);
	}

	// Fetches all articles
	@GetMapping(value = "/articles", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<ArticleVO>> getAllArticles() {
		List<ArticleVO> resArticles = new ArrayList<>();
		List<Article> articles = articleService.getAllArticles();
		for (int i = 0; i < articles.size(); i++) {
			ArticleVO ob = new ArticleVO();
			BeanUtils.copyProperties(articles.get(i), ob);
			resArticles.add(ob);
		}
		return new ResponseEntity<List<ArticleVO>>(resArticles, HttpStatus.OK);
	}

	// Creates a new article
	@PostMapping(value = "/article", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Void> addArticle(@RequestBody ArticleVO articleVO, UriComponentsBuilder builder) {
		Article article = new Article();
		BeanUtils.copyProperties(articleVO, article);
		boolean flag = articleService.addArticle(article);

		if (flag == false) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/article/{id}").buildAndExpand(article.getArticleId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@PutMapping(value = "/article", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ArticleVO> updateArticle(@RequestBody ArticleVO articleVO) {
		Article article = new Article();
		BeanUtils.copyProperties(articleVO, article);
		articleService.updateArticle(article);

		ArticleVO ob = new ArticleVO();
		BeanUtils.copyProperties(article, ob);
		return new ResponseEntity<ArticleVO>(ob, HttpStatus.OK);
	}

	@DeleteMapping(value = "/article/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Void> deleteArticle(@PathVariable Integer id) {
		articleService.deleteArticle(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
