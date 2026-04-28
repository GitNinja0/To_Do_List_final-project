package com.juanmunguia.to_do_list_final_project.users;

public class UserException extends RuntimeException{

    public UserException(String message){
        super(message);
    }
    public UserException(String message, Throwable cause){
        super(message, cause);
    }

}
