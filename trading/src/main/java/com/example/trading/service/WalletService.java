package com.example.trading.service;

import com.example.trading.model.Order;
import com.example.trading.model.User;
import com.example.trading.model.Wallet;

public interface WalletService {
    Wallet getUserWallet(User user);

    Wallet addBalance(Wallet wallet,Long money);

    Wallet findWalletById(Long id) throws Exception;

    Wallet walletToWalletTransfer(User sender, Wallet receiverWallet,Long amount) throws Exception;

    Wallet payOrderPayment(Order order, User user) throws Exception;

}
