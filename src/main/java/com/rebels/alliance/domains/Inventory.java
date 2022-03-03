package com.rebels.alliance.domains;

import com.rebels.alliance.domains.enums.Items;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Data
public class Inventory {
//    private final InventoryService inventoryService;

    Long ownerId;
    boolean status;    //    EnumMap<Items, Integer> items;
    //    int points = inventoryService.getInventoryPoints(this);
    int points = 32;
    Map<Items, Integer> items = Map.ofEntries(
            Map.entry(Items.GUN, 0),
            Map.entry(Items.AMMUNITION, 0),
            Map.entry(Items.WATER, 0),
            Map.entry(Items.FOOD, 0)
    );
//    Object[][] items;

}
