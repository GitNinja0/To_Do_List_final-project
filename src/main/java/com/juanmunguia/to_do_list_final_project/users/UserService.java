package com.juanmunguia.to_do_list_final_project.users;

import com.juanmunguia.to_do_list_final_project.facades.EncoderFacade;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository repository;
    private EncoderFacade encoderFacade;

    public UserService(UserRepository repository, EncoderFacade encoderFacade) {
        this.repository = repository;
        this.encoderFacade = encoderFacade;
    }

    public List<User> getAllUsers(){
        return repository.findAll();
    }

    public User deleteUser (Long id) throws Exception{
        User userToDelete = repository.findById(id).orElseThrow( ()-> new RuntimeException("User not found"));
        repository.delete(userToDelete);

        return userToDelete;
    }

    public User changePassword (UserDTO dto, Long id) throws Exception{
        User user = repository.findById(id).orElseThrow( ()-> new RuntimeException("User not found"));

        String passwordDecode = encoderFacade.decode("base64", dto.getPassword());
        String passwordEncode = encoderFacade.encode("Bcrypt", passwordDecode);

        user.setPassword(passwordEncode);

        return repository.save(user);
    }
}
