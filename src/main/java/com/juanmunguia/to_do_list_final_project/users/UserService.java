package com.juanmunguia.to_do_list_final_project.users;

import com.juanmunguia.to_do_list_final_project.facades.EncoderFacade;
import com.juanmunguia.to_do_list_final_project.roles.Role;
import com.juanmunguia.to_do_list_final_project.roles.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository repository;
    private EncoderFacade encoderFacade;
    private RoleRepository roleRepository;

    public UserService(UserRepository repository, EncoderFacade encoderFacade, RoleRepository roleRepository) {
        this.repository = repository;
        this.encoderFacade = encoderFacade;
        this.roleRepository = roleRepository;
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User deleteUser(Long id) throws Exception {
        User userToDelete = repository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        repository.delete(userToDelete);

        return userToDelete;
    }

    public User changePassword(UserDTO dto, Long id) throws Exception {
        User user = repository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        String passwordDecode = encoderFacade.decode("base64", dto.getPassword());
        String passwordEncode = encoderFacade.encode("Bcrypt", passwordDecode);

        user.setPassword(passwordEncode);

        return repository.save(user);
    }

    public User changeFullName(UserDTO dto, Long id) throws Exception {
        User user = repository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setFullname(dto.getFullname());

        return repository.save(user);
    }

    public User updateProfile(UserDTO dto, String username) throws Exception {
        User user = repository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (dto.getUsername() != null && !dto.getUsername().isEmpty()) {
            user.setUsername(dto.getUsername());
        }
        if (dto.getEmail() != null && !dto.getEmail().isEmpty()) {
            user.setEmail(dto.getEmail());
        }
        if (dto.getFullname() != null && !dto.getFullname().isEmpty()) {
            user.setFullname(dto.getFullname());
        }
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            String passwordEncode = encoderFacade.encode("Bcrypt", dto.getPassword());
            user.setPassword(passwordEncode);
        }

        return repository.save(user);
    }

    public User promoteToGestor(Long id) {
        User user = repository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        Role gestorRole = roleRepository.findByName("ROLE_GESTOR")
                .orElseGet(() -> roleRepository.findByName("GESTOR")
                        .orElseThrow(() -> new RuntimeException("Role GESTOR not found")));

        user.getRoles().add(gestorRole);
        return repository.save(user);
    }

    public User demoteToUser(Long id) {
        User user = repository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        Role gestorRole = roleRepository.findByName("ROLE_GESTOR")
                .orElseGet(() -> roleRepository.findByName("GESTOR")
                        .orElseThrow(() -> new RuntimeException("Role GESTOR not found")));

        user.getRoles().remove(gestorRole);
        return repository.save(user);
    }
}
