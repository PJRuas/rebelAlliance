package com.rebels.alliance.usecases;

import com.rebels.alliance.domains.Inventory;
import com.rebels.alliance.domains.Item;
import com.rebels.alliance.domains.enums.ItemsName;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
            points += inventory.getItems().indexOf(item) * itemsValue.get(item);
        }
        return points;
    }

    public int getRemainingPoints(Inventory inventory) {
        return maxPoints - getInventoryPoints(inventory);
    }

    public Inventory addItem(Item item, Inventory inventory) {
        Integer amount = (int) inventory.getItems().stream().filter(i -> i.getName() == item.getName()).count();
        if (amount > 0) {
            Item it = inventory.getItems().stream().filter(i -> i.getName() == item.getName()).collect(Collectors.toList()).get(0);
            List<Item> its = inventory.getItems().stream().filter(i -> i.getName() != item.getName()).collect(Collectors.toList());
            item.setQtd(item.getQtd() + it.getQtd());
            inventory.setItems(null);
            System.out.println("items sem o principal");
            System.out.println(its);
            inventory.setItems(its);
        }
        inventory.getItems().add(item);
        return inventory;
//        inventory.setPoints(getInventoryPoints(inventory));
    }

    public Inventory removeItem(Item item, Inventory inventory) {
        Integer amount = (int) inventory.getItems().stream().filter(i -> i.getName() == item.getName()).count();
        if (amount > 0) {
            Item it = inventory.getItems().stream().filter(i -> i.getName() == item.getName()).collect(Collectors.toList()).get(0);
            List<Item> its = inventory.getItems().stream().filter(i -> i.getName() != item.getName()).collect(Collectors.toList());
            item.setQtd(it.getQtd() - item.getQtd());
            inventory.setItems(null);
            System.out.println("items sem o principal");
            System.out.println(its);
            inventory.setItems(its);
        }
        inventory.getItems().add(item);
        return inventory;
//        inventory.setPoints(getInventoryPoints(inventory));
    }

    public void generateRandomItems(Inventory inventory) {
        Item[] items = {new Item(ItemsName.FOOD.toString(),2), new Item(ItemsName.WATER.toString(), 10), new Item(ItemsName.AMMUNITION.toString(),10), new Item(ItemsName.GUN.toString(), 5)};
        while (getRemainingPoints(inventory) > 0) {
            double limit = Math.min(4, getRemainingPoints(inventory));
            double randomNumber = Math.random() * limit;
            addItem(items[(int) randomNumber], inventory);
        }
    }

}