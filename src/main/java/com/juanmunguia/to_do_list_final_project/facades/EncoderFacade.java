package com.juanmunguia.to_do_list_final_project.facades;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class EncoderFacade implements IEncryptFacade{

    BCryptPasswordEncoder bCryptPasswordEncoder;

    public EncoderFacade(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public String encode(String type, String data) {

        String dataEncrypted = "";

        if ("bcrypt".equals(type)) dataEncrypted = new BcryptEncoder(bCryptPasswordEncoder).encode(data);
        if ("base64".equals(type)) dataEncrypted = new Base64Encoder().encode(data);

        return dataEncrypted;
    }
    @Override
    public String decode(String type, String data) {

        String dataDecoded = "";

        if ("base64".equals(type)) dataDecoded = new Base64Encoder().decode(data);

        return dataDecoded;
    }

}
