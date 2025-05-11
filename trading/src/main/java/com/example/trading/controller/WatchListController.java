package com.example.trading.controller;

import com.example.trading.model.Coin;
import com.example.trading.model.User;
import com.example.trading.model.WatchList;
import com.example.trading.service.CoinService;
import com.example.trading.service.UserService;
import com.example.trading.service.WatchListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/watchList")
public class WatchListController {
    @Autowired
    private  WatchListService watchListService;
    @Autowired
    private  UserService userService;

    @Autowired
    private  CoinService coinService;

    @GetMapping("/user")
    public ResponseEntity<WatchList> getUserWatchList(
            @RequestHeader("Authorization") String jwt
    )throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        WatchList watchList = watchListService.findUserWatchList(user.getId());
        return ResponseEntity.ok(watchList);

    }

    @GetMapping("/{watchListId}")
    public ResponseEntity<WatchList> getWatchListById(
            @PathVariable Long watchListId
    )throws Exception{
       WatchList watchList = watchListService.findById(watchListId);

        return ResponseEntity.ok(watchList);

    }

    @PatchMapping("/add/coin/{coinId}")
    public ResponseEntity<Coin> addItemToWatchList(
            @RequestHeader("Authorization") String jwt,
            @PathVariable String coinId
    )throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        Coin coin = coinService.findById(coinId);
        Coin addedCoin =  watchListService.addItemToWatchList(coin,user);
        return ResponseEntity.ok(addedCoin);

    }



}
