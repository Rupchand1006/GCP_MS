package com.dell.emc.authors.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


public class Author {

    private Integer id;
    private String name;
    private String email;
    private AuthorType type;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public AuthorType getType() {
		return type;
	}
	public void setType(AuthorType type) {
		this.type = type;
	}
	public Author(Integer id, String name, String email, AuthorType type) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.type = type;
	}

    
    
}
