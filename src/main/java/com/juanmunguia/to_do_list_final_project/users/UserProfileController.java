package com.juanmunguia.to_do_list_final_project.users;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping(path = "${api-endpoint}/user")
public class UserProfileController {

    private final UserService service;

    public UserProfileController(UserService service) {
        this.service = service;
    }

    @PutMapping("/profile")
    public ResponseEntity<User> updateProfile(@RequestBody UserDTO dto, Principal principal) throws Exception {
        return ResponseEntity.ok(service.updateProfile(dto, principal.getName()));
    }
}
