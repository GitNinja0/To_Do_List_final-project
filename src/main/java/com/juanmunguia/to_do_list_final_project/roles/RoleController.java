package com.juanmunguia.to_do_list_final_project.roles;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping(path = "${api-endpoint}/roles")
public class RoleController {

    private RoleService service;

    public RoleController(RoleService service) {
        this.service = service;
    }

    @PutMapping("changeRole/{id}")
    public ResponseEntity<Role> changeRole(@PathVariable Long id, @RequestBody RoleDTO dto) {
        return ResponseEntity.accepted().body(service.changeRole(id, dto));
    }

}
