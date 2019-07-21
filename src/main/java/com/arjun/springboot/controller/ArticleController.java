package com.arjun.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arjun.springboot.service.IArticleService;

@RestController
@RequestMapping("user")
@CrossOrigin(origins = { "http://localhost:4200" })
public class ArticleController {
	
	@Autowired
	private IArticleService articleService;
	
	
	
}
