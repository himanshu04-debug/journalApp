package com.himanshu.journalApp.Services;

import com.himanshu.journalApp.Entity.User;
import com.himanshu.journalApp.Repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void saveEntry(User user){
        try{
            userRepository.save(user);
        }catch (Exception e){
            //log.error("Exception", e);
        }


    }
    public Optional<User> findById(ObjectId id){
        return userRepository.findById(id);
    }
    public void deleteById(ObjectId id){
        userRepository.deleteById(id);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }
    public User findByUserName(String username){
        return userRepository.findByUsername(username);
    }
}
