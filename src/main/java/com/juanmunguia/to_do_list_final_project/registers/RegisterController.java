package com.juanmunguia.to_do_list_final_project.registers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juanmunguia.to_do_list_final_project.users.UserDTO;

@RestController
@RequestMapping(path = "${api-endpoint}/register")
public class RegisterController {

    private RegisterService service;

    public RegisterController(RegisterService service) {
        this.service = service;
    }

    @PostMapping
    public String registerUser(@RequestBody UserDTO dto) {
        return service.registerUser(dto);
    }
}
