package com.rebels.alliance.usecases;

import com.rebels.alliance.domains.Inventory;
import com.rebels.alliance.domains.Item;
import com.rebels.alliance.exceptions.BusinessValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class InventoryServiceTest {

    private InventoryService inventoryService = new InventoryService();
    private Item item = new Item("GUN", 1);
    private List<Item> emptyItems = new ArrayList();
    private Inventory emptyInventory = new Inventory();

    @Test
    void ShouldReturnZeroWhen_getInventoryPointsZero() {
        emptyInventory.setItems(emptyItems);
        int points = inventoryService.getInventoryPoints(emptyInventory);
        Assertions.assertEquals(points, 0);
    }

    @Test
    void ShouldNotReturnZeroWhen_getInventoryPointsValid() {
        List<Item> items = emptyItems;
        Inventory inventory = emptyInventory;
        items.add(new Item("GUN", 1));
        inventory.setItems(items);
        int points = inventoryService.getInventoryPoints(inventory);
        Assertions.assertNotEquals(points, 0);
    }

    @Test
    void shouldReturnZeroWhen_getItemPointsNull(){
        int points = inventoryService.getItemsPoints(emptyItems);
        Assertions.assertEquals(points, 0);
    }

    @Test
    void shouldNotReturnZeroWhen_getItemPointsValid(){
        List<Item> items = emptyItems;
        Inventory inventory = emptyInventory;
        items.add(new Item("GUN", 1));
        inventory.setItems(items);
        int points = inventoryService.getItemsPoints(items);
        Assertions.assertNotEquals(points, 0);
    }

    @Test
    void shouldReturnZero_when_getRemainingPointsInvalid() {
        Inventory inventory = new Inventory();
        inventory.setItems(emptyItems);
        int points = inventoryService.getRemainingPoints(inventory);
        Assertions.assertNotEquals(points, 0);
    }

    @Test
    void shouldReturnInventory_when_removeItem() {
        List<Item> items = new ArrayList<>();
        items.add(item);

        Inventory inventory = emptyInventory;
        inventory.setItems(items);

        Inventory newInventory = emptyInventory;
        newInventory.setItems(items);

        inventory = inventoryService.removeItem(item, inventory);
        Assertions.assertEquals(inventory, newInventory);
    }

    @Test
    void shouldThrowException_when_noItemsToRemove_andTryIt(){
        List<Item> items = new ArrayList<>();

        Inventory inventory = emptyInventory;
        inventory.setItems(items);

        Assertions.assertThrows(BusinessValidationException.class, () ->
                inventoryService.removeItem(item, inventory));
    }

    @Test
    void shouldThrowException_when_removeInvalidItem(){
        List<Item> items = new ArrayList<>();
        items.add(item);

        Inventory inventory = emptyInventory;
        inventory.setItems(items);

        inventoryService.removeItem(item, inventory);

        Assertions.assertThrows(BusinessValidationException.class, () ->
                inventoryService.removeItem(new Item("GUN", 2), inventory));
    }

    @Test
    void shoulNotgenerateRandomItems_when_invalidInventory() {
        Inventory inventory = new Inventory();
        inventory.setItems(emptyItems);

        Assertions.assertThrows(IllegalArgumentException.class, ()->
                inventoryService.generateRandomItems(inventory));

    }
}