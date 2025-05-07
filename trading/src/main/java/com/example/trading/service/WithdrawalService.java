package com.example.trading.service;

import com.example.trading.model.User;
import com.example.trading.model.Withdrawal;

import java.util.List;

public interface WithdrawalService {
    Withdrawal requestWithdrawal (Long amount, User user);

    Withdrawal proceedWithdrawal(Long withdrawalId,boolean accept);

    List<Withdrawal> getUsersWithdrawalHistory(User user);

    List<Withdrawal> getAllWithdrawalRequest();

}
