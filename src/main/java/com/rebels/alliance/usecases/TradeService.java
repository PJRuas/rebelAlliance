package com.rebels.alliance.usecases;

import com.rebels.alliance.domains.Rebel;
import com.rebels.alliance.domains.dto.TradeDto;
import com.rebels.alliance.exceptions.BusinessValidationException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TradeService {
    private final RebelService rebelService;
    private final InventoryService inventoryService;

    public void tradeItems (TradeDto tradeDto) {
        Rebel rebel1 = rebelService.listByParam("id", tradeDto.getRebel1Id().intValue()).get(0);
        Rebel rebel2 = rebelService.listByParam("id", tradeDto.getRebel2Id().intValue()).get(0);

        if(inventoryService.getItemsPoints(tradeDto.getItemsRebel1()) != inventoryService.getItemsPoints(tradeDto.getItemsRebel2())){
            throw new BusinessValidationException("Items points are not equals");
        } else {
            tradeDto.getItemsRebel1().forEach(item -> rebelService.removeItem(rebel1, item));
            tradeDto.getItemsRebel2().forEach(item -> rebelService.removeItem(rebel2, item));

            tradeDto.getItemsRebel2().forEach(item -> rebelService.addItem(rebel1, item));
            tradeDto.getItemsRebel1().forEach(item -> rebelService.addItem(rebel2, item));
        }
    }
}
