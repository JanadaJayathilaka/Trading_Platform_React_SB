package com.example.trading.service;

import com.example.trading.model.PaymentDetails;
import com.example.trading.model.User;

public interface PaymentDetailsService {
    public PaymentDetails addPaymentDetails(String accountNumber,
                                            String accountHolder,
                                            String ifsc,
                                            String bankName,
                                            User user);

    public PaymentDetails getUsersPaymentDetails(User user);
}
