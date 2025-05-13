package com.example.trading.controller;

import com.example.trading.model.PaymentDetails;
import com.example.trading.model.User;
import com.example.trading.service.PaymentDetailsService;
import com.example.trading.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentDetailsController {
    @Autowired
    private PaymentDetailsService paymentDetailsService;

    @Autowired
    private UserService userService;

    @PostMapping("/payment-details")
    public ResponseEntity<PaymentDetails> addPaymentDetails(
            @RequestBody PaymentDetails paymentDetailsRequest,
            @RequestHeader("Authorization") String jwt)throws Exception{

        User user = userService.findUserProfileByJwt(jwt);

        PaymentDetails paymentDetails = paymentDetailsService.addPaymentDetails(
                paymentDetailsRequest.getAccountNumber(),
                paymentDetailsRequest.getAccountNumber(),
                paymentDetailsRequest.getIfsc(),
                paymentDetailsRequest.getBankName(),
                user
        );
        return new ResponseEntity<>(paymentDetails, HttpStatus.CREATED);

    }

    @GetMapping("/payment-details")
    public ResponseEntity<PaymentDetails> getPaymentDetails(
            @RequestHeader("Authorization") String jwt)throws Exception{

        User user = userService.findUserProfileByJwt(jwt);

        PaymentDetails paymentDetails = paymentDetailsService.getUsersPaymentDetails(user);
        return new ResponseEntity<>(paymentDetails, HttpStatus.CREATED);

    }
}
