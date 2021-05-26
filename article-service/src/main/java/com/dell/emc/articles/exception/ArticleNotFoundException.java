package com.dell.emc.articles.exception;

public class ArticleNotFoundException extends RuntimeException {
    public ArticleNotFoundException(String cause) {
        super("Article not found with " + cause);
    }
}
