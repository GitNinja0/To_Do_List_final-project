package com.juanmunguia.to_do_list_final_project.tags;

public class TagException extends RuntimeException {

    public TagException(String message) {
        super(message);
    }

    public TagException(String message, Throwable cause) {
        super(message, cause);
    }
}
