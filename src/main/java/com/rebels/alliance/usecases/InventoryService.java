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

    public void addItem(Items item, Inventory inventory) {
        Integer amount = inventory.getItems().getOrDefault(item, 0) + 1;
        inventory.getItems().put(item, amount);
        inventory.setPoints(getInventoryPoints(inventory));
    }

    public void removeItem(Items item, Inventory inventory) {
        Integer amount = inventory.getItems().getOrDefault(item, 0);
        if (amount > 0) {
            amount -= 1;
        }
        inventory.getItems().put(item, amount);
        inventory.setPoints(getInventoryPoints(inventory));
    }

    public void generateRandomItems(Inventory inventory) {
        Items[] items = {Items.FOOD, Items.WATER, Items.AMMUNITION, Items.GUN};
        while (getRemainingPoints(inventory) > 0) {
            double limit = Math.min(4, getRemainingPoints(inventory));
            double randomNumber = Math.random() * limit;
            addItem(items[(int) randomNumber], inventory);
        }
    }

}