package com.juanmunguia.to_do_list_final_project.users;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping(path = "${api-endpoint}/users")
public class UserController {

    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping(path = "/")
    public List<User> getAllUsers() {
        return service.getAllUsers();
    }

    @PutMapping(path = "/changePassword/{id}")
    public ResponseEntity<User> changePassword(@PathVariable Long id, @RequestBody UserDTO dto) throws Exception {
        return ResponseEntity.accepted().body(service.changePassword(dto, id));
    }

    @PutMapping("changeFullName/{id}")
    public ResponseEntity<User> changeFullName(@PathVariable Long id, @RequestBody UserDTO dto) throws Exception {
        return ResponseEntity.accepted().body(service.changeFullName(dto, id));
    }
}
