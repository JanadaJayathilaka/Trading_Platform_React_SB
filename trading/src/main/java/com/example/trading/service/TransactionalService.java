package com.example.trading.service;

import com.example.trading.domain.WalletTransactionType;
import com.example.trading.model.Wallet;
import com.example.trading.model.WalletTransaction;

import java.util.List;

public interface TransactionalService {
    List<WalletTransaction> getTransactionsByWallet(Wallet wallet) throws Exception;

    WalletTransaction createTransaction(
            Wallet wallet,
            WalletTransactionType type,
            Long toUserId,
            String description,
            Long amount
    );
}
