package com.rebels.alliance.gateways.controllers;

import com.rebels.alliance.gateways.controllers.requests.TradeRequest;
import com.rebels.alliance.usecases.TradeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rebels/trade")
@RequiredArgsConstructor
@Slf4j
public class TradeController {

    private final TradeService tradeService;

    @PutMapping
    public void tradeItems(@RequestBody TradeRequest tradeRequest) {
        log.info("{}", tradeRequest);
        tradeService.tradeItems(tradeRequest);
    }
}
