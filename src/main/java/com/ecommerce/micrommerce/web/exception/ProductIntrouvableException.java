package com.ecommerce.micrommerce.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductIntrouvableException extends RuntimeException {
    public ProductIntrouvableException(String s) {
        super(s);
    }
}
