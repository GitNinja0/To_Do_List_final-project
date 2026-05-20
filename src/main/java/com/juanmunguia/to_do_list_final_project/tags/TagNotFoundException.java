package com.juanmunguia.to_do_list_final_project.tags;

public class TagNotFoundException extends TagException {

    public TagNotFoundException(String message) {
        super(message);
    }

    public TagNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
