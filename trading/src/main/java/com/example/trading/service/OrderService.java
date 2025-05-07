package com.example.trading.service;

import com.example.trading.domain.OrderType;
import com.example.trading.model.Coin;
import com.example.trading.model.Order;
import com.example.trading.model.OrderItem;
import com.example.trading.model.User;

import java.util.List;


public interface OrderService {


    Order createOrder(User user, OrderItem orderItem, OrderType orderType);

    Order getOrderByID(Long orderId) throws Exception;

    List<Order> getAllOrderOfUser(Long userId, OrderType orderType, String assetSymbol);

    Order processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception;
}
