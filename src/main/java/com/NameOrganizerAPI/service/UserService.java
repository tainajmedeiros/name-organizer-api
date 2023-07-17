package com.NameOrganizerAPI.service;

import com.NameOrganizerAPI.model.User;
import com.NameOrganizerAPI.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Boolean add(User user){
        Optional<User> searchName = userRepository.findByName(user.getName());
        if (!searchName.isPresent()) {
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public List<User> list(){
        return userRepository.findAll();
    }

    public Optional<User> findByName(String name) {
        return userRepository.findByName(name);
    }
}
