package com.rebels.alliance.usecases;

import com.rebels.alliance.domains.Rebel;
import com.rebels.alliance.exceptions.BusinessValidationException;
import com.rebels.alliance.gateways.controllers.requests.TradeRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TradeService {
    private final RebelService rebelService;
    private final InventoryService inventoryService;

    public void tradeItems(TradeRequest tradeRequest) {
        Rebel rebel1 = rebelService.listByParam("id", tradeRequest.getRebel1Id().intValue()).get(0);
        Rebel rebel2 = rebelService.listByParam("id", tradeRequest.getRebel2Id().intValue()).get(0);

        if (inventoryService.getItemsPoints(tradeRequest.getItemsRebel1()) != inventoryService.getItemsPoints(tradeRequest.getItemsRebel2())) {
            throw new BusinessValidationException("Unfair trade request. Items score must match on both sides");
        } else {
            tradeRequest.getItemsRebel1().forEach(item -> rebelService.removeItem(rebel1, item));
            tradeRequest.getItemsRebel2().forEach(item -> rebelService.removeItem(rebel2, item));

            tradeRequest.getItemsRebel2().forEach(item -> rebelService.addItem(rebel1, item));
            tradeRequest.getItemsRebel1().forEach(item -> rebelService.addItem(rebel2, item));
        }
    }
}
