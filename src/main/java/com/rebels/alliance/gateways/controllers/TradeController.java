package com.rebels.alliance.gateways.controllers;

import com.rebels.alliance.domains.dto.TradeDto;
import com.rebels.alliance.usecases.TradeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/trades")
@RequiredArgsConstructor
@Slf4j
public class TradeController {

    private final TradeService tradeService;

    @PutMapping
    public void tradeItems(@RequestBody TradeDto tradeDto){
        log.info("{}", tradeDto);
        tradeService.tradeItems(tradeDto);
    }
}
