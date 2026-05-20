package com.juanmunguia.to_do_list_final_project.categories;

public class CategoryNotFoundException extends CategoryException {

    public CategoryNotFoundException(String message) {
        super(message);
    }

    public CategoryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
