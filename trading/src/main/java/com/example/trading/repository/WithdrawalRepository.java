package com.example.trading.repository;

import com.example.trading.model.Withdrawal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WithdrawalRepository extends JpaRepository<Withdrawal,Long> {
List<Withdrawal> findByUserId(Long userId);
}
