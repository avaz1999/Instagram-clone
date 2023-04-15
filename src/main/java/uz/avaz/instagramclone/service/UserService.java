package uz.avaz.instagramclone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.avaz.instagramclone.entity.User;
import uz.avaz.instagramclone.repository.UserRepository;

@Service
@Transactional
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username).orElse(null);
    }


}
