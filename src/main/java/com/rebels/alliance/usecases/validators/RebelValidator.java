package com.rebels.alliance.usecases.validators;

import com.rebels.alliance.domains.Rebel;
import com.rebels.alliance.gateways.RebelGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RebelValidator {

    private final InventoryValidator inventoryValidator;
    private final LocationValidator locationValidator;
    private final RebelGateway rebelGateway;

    public List<String> validateCreation(Rebel rebel) {
        List<String> validationErrors = new ArrayList<>();

        if (rebel == null) return List.of("Rebel not provided");

        if (rebel.getId() != null) {
            validationErrors.add("Rebel already on DB. Id= " + rebel.getId());
        }

        if (!StringUtils.hasText(rebel.getName())) {
            validationErrors.add("Missing Rebel's name");
        }

        if (rebel.getAge() <= 0) {
            validationErrors.add("Missing Rebel's age");
        }

        if (rebel.getGender() == null) {
            validationErrors.add("Missing Rebel's gender.");
        }

        List<String> locationErros = locationValidator.validate(rebel.getLocation());
        validationErrors.addAll(locationErros);

        List<String> inventoryErros = inventoryValidator.validate(rebel.getInventory());
        validationErrors.addAll(inventoryErros);

        return validationErrors;
    }


    public List<String> validateDelete(Rebel rebel) {
        boolean rebelExists = !rebelGateway.findByParam("id", rebel.getId()).isEmpty();
        List<String> validationErrors = new ArrayList<>();

        if (rebel.getId() == null) {
            validationErrors.add("Rebel's Id not provided");
        }

        if (!rebelExists) {
            validationErrors.add("Provided id ({}) does not match any rebel");
        }
        return validationErrors;
    }

    public List<String> validateSearch(String parameter) {
        String[] parameters = {"id", "name", "age", "gender", "location"};
        List<String> validationErrors = new ArrayList<>();
        boolean test = false;

        for (String param : parameters) {
            if (param.equals(parameter.toLowerCase())) {
                test = true;
            }
        }

        if (!test) {
            validationErrors.add("Invalid search parameter");
        }

        return validationErrors;
    }

    public List<String> validateUpdate(Rebel rebel) {
        boolean rebelExists = !rebelGateway.findByParam("id", rebel.getId()).isEmpty();
        List<String> validationErrors = new ArrayList<>();

        if (rebel.getId() == null) {
            validationErrors.add("Rebel's Id not provided");
        }

        if (!rebelExists) {
            validationErrors.add("Provided id ({}) does not match any rebel");
        }
        return validationErrors;
    }
}
