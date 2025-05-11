package com.example.trading.service;

import com.example.trading.model.Coin;
import com.example.trading.model.User;
import com.example.trading.model.WatchList;

public interface WatchListService {
    WatchList findUserWatchList(Long userId) throws Exception;
    WatchList createWatchList(User user);
    WatchList findById(Long id) throws Exception;

    Coin addItemToWatchList(Coin coin, User user) throws Exception;

}
