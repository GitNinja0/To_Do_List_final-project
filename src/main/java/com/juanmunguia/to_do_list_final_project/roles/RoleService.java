package com.juanmunguia.to_do_list_final_project.roles;

import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private RoleRepository repository;

    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }

    public Role getRoleById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Role not found"));
    }

    public Role changeRole(Long id, RoleDTO dto) {
        Role role = repository.findById(id).orElseThrow(() -> new RuntimeException("Role not found"));
        role.setName(dto.getName());
        return repository.save(role);
    }
}
