package dev.ordy.erp.user_management.user;

import dev.ordy.erp.business.item.Item;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public Users createUser(String username,String password,String roles){
        Users user=new Users(username,"{noop}" +password,roles);
        userRepository.save(user);
        return user;
    }

    @Transactional(readOnly = true)
    public Users findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Item not found with id: " + username));
    }

}
