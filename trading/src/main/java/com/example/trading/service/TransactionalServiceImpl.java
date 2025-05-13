package com.example.trading.service;


import com.example.trading.domain.WalletTransactionType;
import com.example.trading.model.Wallet;
import com.example.trading.model.WalletTransaction;
import com.example.trading.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionalServiceImpl implements TransactionalService{
    @Autowired
    private TransactionRepository transactionRepository;

   @Override
    public List<WalletTransaction> getTransactionsByWallet(Wallet wallet) throws Exception {
        if (wallet == null) {
            throw new Exception("Wallet not found");
        }
        return transactionRepository.findByWallet(wallet);
    }

    @Override
    public WalletTransaction createTransaction(
            Wallet wallet,
            WalletTransactionType type,
            Long toUserId,
            String description,
            Long amount
    ) {
        WalletTransaction transaction = new WalletTransaction();
        transaction.setWallet(wallet);
        transaction.setType(type);
        transaction.setId(toUserId); // Optional: Set null if not a transfer
        transaction.setPurpose(description);
        transaction.setAmount(amount);
        transaction.setDate(LocalDate.from(LocalDateTime.now()));

        return transactionRepository.save(transaction);
    }
}
