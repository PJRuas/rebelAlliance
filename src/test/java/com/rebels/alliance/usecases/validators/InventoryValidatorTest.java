package com.rebels.alliance.usecases.validators;

import com.rebels.alliance.domains.Inventory;
import com.rebels.alliance.domains.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class InventoryValidatorTest {

    private InventoryValidator validator = new InventoryValidator();

    @Test
    public void validateInventory(){
        Inventory inventory = new Inventory();
        inventory.setItems(List.of(new Item("GUN", 1)));
        List<String> validationResult = validator.validate(inventory);

        Assertions.assertEquals(validationResult, List.of());
    }

    @Test
    public void validateNullInventory(){
        List<String> validationResult = validator.validate(null);

        Assertions.assertEquals(validationResult, List.of("Inventory not provided"));
    }

    @Test
    public void validateEmptyInventory(){
        Inventory inventory = new Inventory();
        inventory.setItems(List.of());
        List<String> validationResult = validator.validate(inventory);

        Assertions.assertEquals(validationResult, List.of("Invalid empty inventory"));
    }

    @Test
    public void validateNullItems(){
        List<String> validationErrors = validator.validateItem(null);

        Assertions.assertEquals(validationErrors, List.of("item not provided"));
    }

    @Test
    public void validateItem(){
        Item item = new Item("GUN", 1);
        List<String> validationResult = validator.validateItem(item);

        Assertions.assertEquals(validationResult, List.of());
    }

}