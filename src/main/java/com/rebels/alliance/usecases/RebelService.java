package com.rebels.alliance.usecases;

import com.rebels.alliance.domains.Inventory;
import com.rebels.alliance.domains.Item;
import com.rebels.alliance.domains.Rebel;
import com.rebels.alliance.exceptions.BusinessValidationException;
import com.rebels.alliance.gateways.RebelGateway;
import com.rebels.alliance.usecases.validators.InventoryValidator;
import com.rebels.alliance.usecases.validators.RebelValidator;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RebelService {
    private final RebelGateway rebelGateway;
    private final RebelValidator rebelValidator;
    private final InventoryValidator inventoryValidator;
    private final InventoryService inventoryService;

    public Rebel createRebel(Rebel rebel) {
        val validationErrors = rebelValidator.validateCreation(rebel);

        if (!validationErrors.isEmpty()) {
            throw new BusinessValidationException(validationErrors);
        }

        return rebelGateway.register(rebel);
    }

    public Rebel updateRebel(Rebel rebel) {
        val validationErrors = rebelValidator.validateUpdate(rebel);

        if (!validationErrors.isEmpty()) {
            throw new BusinessValidationException(validationErrors);
        }
        return rebelGateway.updateRebel(rebel);
    }

    public void deleteRebel(Rebel rebel) {
        val validationErrors = rebelValidator.validateDelete(rebel);

        if (!validationErrors.isEmpty()) {
            throw new BusinessValidationException(validationErrors);
        }

        rebelGateway.delete(rebel);
    }

    public List<Rebel> listAll() {
        return rebelGateway.findAll();
    }

    public <V> List<Rebel> listByParam(String parameter, V value) {
        val validationErrors = rebelValidator.validateSearch(parameter);

        if (!validationErrors.isEmpty()) {
            throw new BusinessValidationException(validationErrors);
        }

        return rebelGateway.findByParam(parameter, value);
    }

    public void addItem(Rebel rebel, Item item) {
        val validationErrors = inventoryValidator.validateItem(item);
        if (!validationErrors.isEmpty()) {
            throw new BusinessValidationException(validationErrors);
        }
        Inventory inventory = inventoryService.addItem(item, rebel.getInventory());
        rebel.setInventory(inventory);
    }

    public void removeItem(Rebel rebel, Item item) {
        val validationErrors = inventoryValidator.validateItem(item);
        if (!validationErrors.isEmpty()){
            throw new BusinessValidationException(validationErrors);
        }
        Inventory inventory = inventoryService.removeItem(item, rebel.getInventory());
        rebel.setInventory(inventory);
    }
}
