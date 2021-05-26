package com.dell.emc.authors.controllers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.dell.emc.authors.exception.AuthorNotFoundException;
import com.dell.emc.authors.models.Author;
import com.dell.emc.authors.models.AuthorType;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController

public class AuthorController {
	private static final Logger logger = LoggerFactory.getLogger(AuthorController.class);
    private List<Author> authors;
    @Autowired
	Environment environment;


    public AuthorController() {
        this.authors = new ArrayList<>();

        this.authors.add(new Author(1, "Wendell Adriel", "wendelladriel.ti@gmail.com", AuthorType.EDITOR));
        this.authors.add(new Author(2, "John McQueide", "mcqueide@gmail.com", AuthorType.WRITER));
    }

    @GetMapping(path = "/{id}")
    public Author findById(@PathVariable("id") Integer id) {
    	logger.info(String.format("Authors.findById(%d)", id));
        return this.authors.stream()
                .filter(aut -> aut.getId().intValue() == id.intValue())
                .findFirst()
                .orElseThrow(() -> new AuthorNotFoundException("id : " + id));
    }

    @HystrixCommand(fallbackMethod = "getAllCached")
    @GetMapping(path = "/")
    public List<Author> getAll() {
    	logger.info("Authors.getAll()");
        return this.authors;
    }

    public List<Author> getAllCached() {
    	logger.info("Authors.getAllCached()");
    	logger.warn("Return cached result here");

        return new ArrayList<>();
    }
    
	@GetMapping("/m2")
	public String backend() {
		System.out.println("Inside MyRestController::M2 Service...");

		String serverPort = environment.getProperty("local.server.port");

		System.out.println("Port : " + serverPort);

		return "Hello form M2 !!! " + " Host : localhost " + " :: Port : " + serverPort;
	}

}
