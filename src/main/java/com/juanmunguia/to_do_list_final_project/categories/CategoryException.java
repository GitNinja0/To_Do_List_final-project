package com.juanmunguia.to_do_list_final_project.categories;

public class CategoryException extends RuntimeException {

    public CategoryException(String message) {
        super(message);
    }

    public CategoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
