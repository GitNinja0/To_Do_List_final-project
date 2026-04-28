package com.juanmunguia.to_do_list_final_project.facades;

import java.util.Base64;

public class Base64Encoder implements IEncoder{


    @Override
    public String encode(String data) {
        return Base64.getEncoder().encodeToString(data.getBytes());
    }

    public String decode(String data){
        return new String(Base64.getDecoder().decode(data));
    }
}
