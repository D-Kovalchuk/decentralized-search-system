package com.fly.house.authentication;

import org.springframework.core.ParameterizedTypeReference;

/**
 * Created by dimon on 1/31/14.
 */
public class Message<T> extends ParameterizedTypeReference<Message<T>> {

    private String message;
    private int code;
    private T body;

    public Message() {
    }

    public Message(T body) {
        this.body = body;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
