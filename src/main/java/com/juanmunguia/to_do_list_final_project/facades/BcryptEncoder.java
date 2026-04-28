package com.juanmunguia.to_do_list_final_project.facades;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BcryptEncoder implements IEncoder{

    BCryptPasswordEncoder encoder;

    public BcryptEncoder(BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public String encode(String data) {
        return encoder.encode(data);
    }
}
