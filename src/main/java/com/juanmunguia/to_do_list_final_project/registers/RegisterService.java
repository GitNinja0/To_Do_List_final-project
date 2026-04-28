package com.juanmunguia.to_do_list_final_project.registers;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.juanmunguia.to_do_list_final_project.facades.EncoderFacade;
import com.juanmunguia.to_do_list_final_project.roles.Role;
import com.juanmunguia.to_do_list_final_project.roles.RoleRepository;
import com.juanmunguia.to_do_list_final_project.roles.RoleService;
import com.juanmunguia.to_do_list_final_project.users.User;
import com.juanmunguia.to_do_list_final_project.users.UserDTO;
import com.juanmunguia.to_do_list_final_project.users.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RegisterService {

    UserRepository userRepository;
    RoleRepository roleRepository;
    RoleService roleService;
    EncoderFacade encoderFacade;

    public String registerUser(UserDTO dto) {

        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            return "Usuario ya registrado";
        }

        String passwordDecode = encoderFacade.decode("base64", dto.getPassword());
        String passwordEncode = encoderFacade.encode("bcrypt", passwordDecode);

        dto.setPassword(passwordEncode);

        User newUser = new User();

        newUser.setUsername(dto.getUsername());
        newUser.setPassword(dto.getPassword());
        newUser.setEmail(dto.getEmail());
        assignDefaultRole(newUser);

        userRepository.save(newUser);
        return "Usuario registrado correctamente";
    }

    public void assignDefaultRole(User user) {

        Role defaultRole = roleService.getRoleById(1L);
        Set<Role> roles = new HashSet<>();
        roles.add(defaultRole);

        user.setRoles(roles);
    }

}
