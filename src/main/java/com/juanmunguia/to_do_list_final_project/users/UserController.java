package com.juanmunguia.to_do_list_final_project.users;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public org.springframework.data.domain.Page<User> getAllUsers(
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(required = false, defaultValue = "") String role,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(page, size);
        return service.getPaginatedUsers(search, role, pageable);
    }

    @PutMapping(path = "/changePassword/{id}")
    public ResponseEntity<User> changePassword(@PathVariable Long id, @RequestBody UserDTO dto) throws Exception {
        return ResponseEntity.accepted().body(service.changePassword(dto, id));
    }

    @PutMapping("changeFullName/{id}")
    public ResponseEntity<User> changeFullName(@PathVariable Long id, @RequestBody UserDTO dto) throws Exception {
        return ResponseEntity.accepted().body(service.changeFullName(dto, id));
    }

    @PostMapping("/{id}/promote")
    public ResponseEntity<User> promoteUser(@PathVariable Long id) {
        return ResponseEntity.ok(service.promoteToGestor(id));
    }

    @PostMapping("/{id}/demote")
    public ResponseEntity<User> demoteUser(@PathVariable Long id) {
        return ResponseEntity.ok(service.demoteToUser(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(service.deleteUser(id));
    }
}
