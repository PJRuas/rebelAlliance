package com.rebels.alliance.usecases;

import com.rebels.alliance.domains.Inventory;
import com.rebels.alliance.domains.enums.Items;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Data
public class InventoryService {

    private final int maxPoints = 32;
    //    private final int[] itemsValue = {4, 3, 2, 1};
    private final Map<Items, Integer> itemsValue = Map.ofEntries(
            Map.entry(Items.GUN, 4),
            Map.entry(Items.AMMUNITION, 3),
            Map.entry(Items.WATER, 2),
            Map.entry(Items.FOOD, 1)
    );

    public int getInventoryPoints(Inventory inventory) {
        int points = 0;
        for (Items item : inventory.getItems().keySet()) {
            points += inventory.getItems().get(item) * itemsValue.get(item);
        }
        return points;
    }

    public int getRemainingPoints(Inventory inventory) {
        return maxPoints - getInventoryPoints(inventory);
    }

//    private static EnumMap<Items, Integer> itemsValue = new EnumMap<Items, Integer>(Items.class) {
//        static {
//            itemsValue.put(Items.GUN, 4);
//            itemsValue.put(Items.AMMUNITION, 3);
//            itemsValue.put(Items.WATER, 2);
//            itemsValue.put(Items.FOOD, 1);
//        }
//    };

}