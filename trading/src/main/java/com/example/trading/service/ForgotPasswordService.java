package com.example.trading.service;

import com.example.trading.domain.VerificationType;
import com.example.trading.model.ForgotPasswordToken;
import com.example.trading.model.User;

public interface ForgotPasswordService {

    ForgotPasswordToken createToken(User user, String id, String otp,VerificationType verificationType,String sendTo);


    ForgotPasswordToken findById(String id);

    ForgotPasswordToken findByUser(Long userId);

    void deleteToken(ForgotPasswordToken token);
}
