package com.rebels.alliance.usecases;

import com.rebels.alliance.domains.Rebel;
import com.rebels.alliance.domains.dto.TradeDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TradeService {
    private final RebelService rebelService;

    public void tradeItems (TradeDto tradeDto) {
        Rebel rebel1 = rebelService.findOneById(tradeDto.getRebel1Id().intValue());
        Rebel rebel2 = rebelService.findOneById(tradeDto.getRebel2Id().intValue());

        tradeDto.getItemRebel1().forEach(item -> rebelService.removeItem(rebel1, item));
        tradeDto.getItemRebel2().forEach(item -> rebelService.removeItem(rebel2, item));

        tradeDto.getItemRebel2().forEach(item -> rebelService.addItem(rebel1, item));
        tradeDto.getItemRebel1().forEach(item -> rebelService.addItem(rebel2, item));
    }
}
