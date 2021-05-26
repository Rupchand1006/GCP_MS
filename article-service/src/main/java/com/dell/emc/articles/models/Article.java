package com.dell.emc.articles.models;

import java.util.Date;

public class Article {

	private Integer id;
	private String name;
	private Date publishDate;
	private Integer authorId;

	public Article(Integer id, String name, Date publishDate, Integer authorId) {
		super();
		this.id = id;
		this.name = name;
		this.publishDate = publishDate;
		this.authorId = authorId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

}
