package com.example.trading.service;

import com.example.trading.config.JwtProvider;
import com.example.trading.domain.VerificationType;
import com.example.trading.model.User;
import com.example.trading.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Service
//@RestController
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findUserByJwt(String jwt) throws Exception {
        String email = JwtProvider.getEmailFromToken(jwt);
        User user = userRepository.findByEmail(email);

        if(user==null){
            throw new Exception("User not found by jwt");
        }
        return user;
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user =  userRepository.findByEmail(email);
        if (user==null){
            throw new Exception("User not found by email");
        }
        return user;
    }

    @Override
    public User findUserById(Long userId) throws Exception {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            throw new Exception("User not found by id");
        }
        return user.get();
    }

    @Override
    public User enableTwoFactorAuthentication(VerificationType verificationType, String sendTo, User user) {
        return null;
    }


    @Override
    public User updatePassword(User user, String newPassword) {
        return null;
    }
}
