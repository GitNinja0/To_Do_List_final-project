package com.juanmunguia.to_do_list_final_project.roles;

public class RoleException extends RuntimeException {

    public RoleException(String message) {
        super(message);
    }

    public RoleException(String message, Throwable cause) {
        super(message, cause);
    }
}
