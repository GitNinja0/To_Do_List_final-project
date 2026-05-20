package com.juanmunguia.to_do_list_final_project.tasks;

public class TaskException extends RuntimeException {

    public TaskException(String message) {
        super(message);
    }

    public TaskException(String message, Throwable cause) {
        super(message, cause);
    }
}
