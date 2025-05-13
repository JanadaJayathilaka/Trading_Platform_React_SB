package com.example.trading.controller;

import com.example.trading.model.User;
import com.example.trading.model.Wallet;
import com.example.trading.model.WalletTransaction;
import com.example.trading.service.TransactionalService;
import com.example.trading.service.UserService;
import com.example.trading.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TransactionController {

    @Autowired
    private WalletService walletService;

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionalService transactionalService;

    @GetMapping("/api/transactions")
    public ResponseEntity<List<WalletTransaction>> getUserWallet(
            @RequestHeader("Authorization") String jwt
    )throws Exception{
        User user = userService.findUserProfileByJwt(jwt);

        Wallet wallet = walletService.getUserWallet(user);

        List<WalletTransaction> transactionList = transactionalService.getTransactionsByWallet(wallet);

    return new ResponseEntity<>(transactionList, HttpStatus.ACCEPTED);
    }
}
