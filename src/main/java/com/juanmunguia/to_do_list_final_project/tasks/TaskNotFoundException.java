package com.juanmunguia.to_do_list_final_project.tasks;

public class TaskNotFoundException extends TaskException {

    public TaskNotFoundException(String message) {
        super(message);
    }

    public TaskNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
