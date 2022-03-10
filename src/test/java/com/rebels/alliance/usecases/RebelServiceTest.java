package com.rebels.alliance.usecases;

import com.rebels.alliance.domains.Item;
import com.rebels.alliance.domains.Rebel;
import com.rebels.alliance.gateways.RebelGateway;
import com.rebels.alliance.usecases.validators.InventoryValidator;
import com.rebels.alliance.usecases.validators.RebelValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.CollectionUtils;
import org.yaml.snakeyaml.util.ArrayUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RebelServiceTest {
    @InjectMocks RebelService rebelService;
    @Mock RebelValidator rebelValidator;
    @Mock RebelGateway rebelGateway;
    @Mock InventoryValidator inventoryValidator;
    @Mock InventoryService inventoryService;

    @Test
    void when_createRebel_then_registerIt() {
        Rebel newRebel = new Rebel();
        Mockito.when(rebelValidator.validateCreation(newRebel)).thenReturn(List.of());
        rebelService.createRebel(newRebel);
        Mockito.verify(rebelGateway).register(newRebel);
    }

    @Test
    void when_updateRebel_then_saveIt() {
        Rebel newRebel = new Rebel();
        Mockito.when(rebelValidator.validateUpdate(newRebel)).thenReturn(List.of());
        rebelService.updateRebel(newRebel);
        Mockito.verify(rebelGateway).updateRebel(newRebel);
    }

    @Test
    void when_deleteRebel_then_removeFromStorage() {
        Rebel rebelToDelete = new Rebel();
        Mockito.when(rebelValidator.validateDelete(rebelToDelete)).thenReturn(List.of());
        rebelService.deleteRebel(rebelToDelete);
        Mockito.verify(rebelGateway).delete(rebelToDelete);
    }

    @Test
    void when_listAllRebels_then_ReturnListOfRebels() {
        Mockito.when(rebelGateway.findAll()).thenReturn(List.of(new Rebel()));
        rebelService.listAll();
        Mockito.verify(rebelGateway).findAll();
    }

    @Test
    void when_listByName_then_ReturnListOfRebels() {
        String rebelName = "rebelde";
        Mockito.when(rebelValidator.validateSearch("name")).thenReturn(List.of());
        rebelService.listByParam("name", rebelName);
        Mockito.verify(rebelGateway).findByParam("name", rebelName);
    }
    @Test
    void when_listById_then_ReturnListOfRebels() {
        String rebelId = "rebelde";
        Mockito.when(rebelValidator.validateSearch("id")).thenReturn(List.of());
        rebelService.listByParam("id", rebelId);
        Mockito.verify(rebelGateway).findByParam("id", rebelId);
    }

    @Test
    void when_addItem_then_SetToRebel() {
        Rebel rebel = new Rebel();
        Item item = new Item("água", 5);
        Mockito.when(inventoryValidator.validateItem(item)).thenReturn(List.of());
        rebelService.addItem(rebel, item);
        Mockito.verify(inventoryService).addItem(item, rebel.getInventory());
    }

    @Test
    void when_removeItem_then_updateRebelsInventory() {
        Rebel rebel = new Rebel();
        Item item = new Item("água", 5);
        Mockito.when(inventoryValidator.validateItem(item)).thenReturn(List.of());
        rebelService.removeItem(rebel, item);
        Mockito.verify(inventoryService).removeItem(item, rebel.getInventory());
    }
}