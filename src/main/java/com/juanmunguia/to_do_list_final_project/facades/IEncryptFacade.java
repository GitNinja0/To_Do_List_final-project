package com.juanmunguia.to_do_list_final_project.facades;

public interface IEncryptFacade {

    String encode(String type, String data);
    String decode(String type, String data);

}
