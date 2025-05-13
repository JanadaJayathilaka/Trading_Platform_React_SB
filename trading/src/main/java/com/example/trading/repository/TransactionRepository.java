package com.example.trading.repository;

import com.example.trading.model.Wallet;
import com.example.trading.model.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<WalletTransaction,Long> {
    List<WalletTransaction> findByWallet(Wallet wallet);
}
