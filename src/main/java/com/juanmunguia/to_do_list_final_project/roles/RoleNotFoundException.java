package com.juanmunguia.to_do_list_final_project.roles;

public class RoleNotFoundException extends RoleException {

    public RoleNotFoundException(String message) {
        super(message);
    }

    public RoleNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
