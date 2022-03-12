package com.rebels.alliance.usecases;

import com.rebels.alliance.domains.Inventory;
import com.rebels.alliance.domains.Item;
import com.rebels.alliance.domains.enums.ItemsName;
import com.rebels.alliance.exceptions.BusinessValidationException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
@AllArgsConstructor
public class InventoryService {

    private final int maxPoints = 32;
    //    private final int[] itemsValue = {4, 3, 2, 1};
    private final Map<ItemsName, Integer> itemsValue = Map.ofEntries(
            Map.entry(ItemsName.GUN, 4),
            Map.entry(ItemsName.AMMUNITION, 3),
            Map.entry(ItemsName.WATER, 2),
            Map.entry(ItemsName.FOOD, 1)
    );

    public int getInventoryPoints(Inventory inventory) {
        int points = 0;
        for (Item item : inventory.getItems()) {
            points += item.getQtd() * itemsValue.get(ItemsName.valueOf(item.getName().toUpperCase(Locale.ROOT)));
        }
        return points;
    }

    public int getItemsPoints(List<Item> items) {
        int points = 0;
        for (Item item : items) {
            points += item.getQtd() * itemsValue.get(ItemsName.valueOf(item.getName()));
        }
        return points;
    }

    public int getRemainingPoints(Inventory inventory) {
        return maxPoints - getInventoryPoints(inventory);
    }

    public Inventory addItem(Item item, Inventory inventory) {
        if (inventory.getItems().stream().anyMatch(i -> i.getName().equals(item.getName()))) {
            inventory.getItems().forEach(i -> {
                if (i.getName().equals(item.getName())) {
                    i.setQtd(i.getQtd() + item.getQtd());
                }
            });
        } else {
            inventory.getItems().add(item);
        }
        inventory.setPoints(getInventoryPoints(inventory));
        return inventory;
    }

    public Inventory removeItem(Item item, Inventory inventory) {
        if (inventory.getItems().stream().anyMatch(i -> i.getName().equals(item.getName()))) {
            inventory.getItems().forEach(i -> {
                if (i.getName().equals(item.getName())) {
                    if (i.getQtd() < item.getQtd()) {
                        throw new BusinessValidationException("Item insufficient amount");
                    } else {
                        i.setQtd(i.getQtd() - item.getQtd());
                    }
                }
            });
        } else {
            throw new BusinessValidationException("Item not found in inventory to remove");
        }
        inventory.setPoints(getInventoryPoints(inventory));
        return inventory;
    }

    public void generateRandomItems(Inventory inventory) {
        Item[] items = {new Item(ItemsName.FOOD.toString(), 2), new Item(ItemsName.WATER.toString(), 10), new Item(ItemsName.AMMUNITION.toString(), 10), new Item(ItemsName.GUN.toString(), 5)};
        while (getRemainingPoints(inventory) > 0) {
            double limit = Math.min(4, getRemainingPoints(inventory));
            double randomNumber = Math.random() * limit;
            addItem(items[(int) randomNumber], inventory);
        }
    }

}