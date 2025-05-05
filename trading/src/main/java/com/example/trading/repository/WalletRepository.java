package com.example.trading.repository;

import com.example.trading.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet,Long> {

    Wallet findByUserId(Long userId);
}
