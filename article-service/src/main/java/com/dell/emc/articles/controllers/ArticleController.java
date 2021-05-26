package com.dell.emc.articles.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.dell.emc.articles.exception.ArticleNotFoundException;
import com.dell.emc.articles.models.Article;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController

public class ArticleController {

	
	private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);
	
    private List<Article> articles;
    
    @LoadBalanced
    @Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

    @Autowired
	RestTemplate restTemplate;

    public ArticleController() {
        this.articles = new ArrayList<>();

        this.articles.add(new Article(1, "First Article", new Date(), 1));
        this.articles.add(new Article(2, "Second Article", new Date(), 2));
        this.articles.add(new Article(3, "Third Article", new Date(), 2));
        this.articles.add(new Article(4, "Fourth Article", new Date(), 1));
        this.articles.add(new Article(5, "Fifth Article", new Date(), 1));
    }

    @GetMapping(path = "/{id}")
    public Article findById(@PathVariable("id") Integer id) {
    	logger.info(String.format("Articles.findById(%d)", id));
    	//String randomString = this.restTemplate.getForObject("http://author-service/m2", String.class);
    	//logger.info( "Server Response from another microservice :: " + randomString);
		
		return this.articles.stream()
                .filter(article -> article.getId().intValue() == id.intValue())
                .findFirst()
                .orElseThrow(() -> new ArticleNotFoundException("id : " + id));
    }

    @GetMapping(path = "/author/{authorId}")
    public List<Article> findByAuthor(@PathVariable("authorId") final Integer authorId) {
    	logger.info(String.format("Articles.findByAuthor(%d)", authorId));
      return this.articles.stream().filter(article -> article.getAuthorId().intValue() == authorId.intValue()).collect(Collectors.toList());
    	
    }

    @HystrixCommand(fallbackMethod = "getAllCached")
    @GetMapping(path = "/")
    public List<Article> getAll() {
    	logger.info("Articles.getAll()");
        return this.articles;
    }

    public List<Article> getAllCached() {
    	logger.info("Articles.getAllCached()");
    	logger.warn("Return cached result here");

        return new ArrayList<>();
    }
    
   

    @GetMapping(path = "/client/m1")
	public String callAuthorService() {
		String randomString = this.restTemplate.getForObject("http://author-service/m2", String.class);
		return "Server Response from another microservice :: " + randomString;
	}
}
