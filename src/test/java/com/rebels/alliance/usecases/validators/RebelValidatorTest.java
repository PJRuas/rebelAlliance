package com.rebels.alliance.usecases.validators;

import com.rebels.alliance.domains.Inventory;
import com.rebels.alliance.domains.Item;
import com.rebels.alliance.domains.Location;
import com.rebels.alliance.domains.Rebel;
import com.rebels.alliance.domains.enums.Gender;
import com.rebels.alliance.gateways.RebelGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class RebelValidatorTest {

    @Mock private InventoryValidator inventoryValidator;
    @Mock private LocationValidator locationValidator;
    @Mock private RebelGateway rebelGateway;
    @InjectMocks private RebelValidator validator;

    @Test
    public void shouldValidateNullRebel(){
        List<String> validationResult = validator.validateCreation(null);
        Assertions.assertEquals(validationResult, List.of("Rebel not provided"));
    }

    @Test
    public void shouldValidateCreation(){
        Rebel rebel = new Rebel();
        rebel.setId(1L);
        rebel.setAge(-2);
        List<String> validationResult = validator.validateCreation(rebel);

        Assertions.assertEquals(validationResult, List.of(
                "Rebel already on DB. Id= " + rebel.getId(),
                "Missing Rebel's name",
                "Missing Rebel's age",
                "Missing Rebel's gender."
        ));
    }

    @Test
    public void shouldValidateCreationWithNoErrors(){
        Rebel rebel = getMockRebel();
        rebel.setAge(12);
        List<String> validationResult = validator.validateCreation(rebel);

        Assertions.assertEquals(validationResult, List.of());
    }

    @Test
    public void shouldValidateInvalidIdOnDelete(){
        Rebel rebel = new Rebel();
        rebel.setId(1L);
        Mockito.when(rebelGateway.findByParam("id", rebel.getId())).thenReturn(List.of());
        List<String> validationResult = validator.validateDelete(rebel);
        Assertions.assertEquals(validationResult, List.of("Provided id does not match any rebel"));
    }

    @Test
    public void shouldValidateNullIdOnDelete(){
        Rebel rebel = new Rebel();
        Mockito.when(rebelGateway.findByParam("id", null)).thenReturn(List.of());
        List<String> validationResult = validator.validateDelete(rebel);

        Assertions.assertEquals(validationResult, List.of(
                "Rebel's Id not provided"
        ));
    }

    @Test
    public void shouldValidateDeleteWhithNoErrors(){
        Rebel rebel = getMockRebel();
        rebel.setId(1L);
        Mockito.when(rebelGateway.findByParam("id", rebel.getId())).thenReturn(List.of(rebel));
        List<String> validationResult = validator.validateDelete(rebel);

        Assertions.assertEquals(validationResult, List.of());
    }

    @Test
    public void shouldValidateSearchWithNoErrors(){
        List<String> validationResult = validator.validateSearch("id");

        Assertions.assertEquals(validationResult, List.of());
    }

    @Test
    public void shouldValidateSearchParam(){
        List<String> validationResult = validator.validateSearch("");

        Assertions.assertEquals(validationResult, List.of("Invalid search parameter"));
    }

    @Test
    public void shouldValidateNullIdUpdate(){
        Rebel rebel = new Rebel();
        Mockito.when(rebelGateway.findByParam("id", null)).thenReturn(List.of());
        List<String> validationResult = validator.validateUpdate(rebel);

        Assertions.assertEquals(validationResult, List.of(
                "Rebel's Id not provided"
        ));
    }

    @Test
    public void shouldValidateNoMatchIdOnUpdate(){
        Rebel rebel = new Rebel();
        rebel.setId(1L);
        Mockito.when(rebelGateway.findByParam("id", rebel.getId())).thenReturn(List.of());
        List<String> validationResult = validator.validateUpdate(rebel);

        Assertions.assertEquals(validationResult, List.of(
                "Provided id does not match any rebel"
        ));
    }

    @Test
    public void shouldValidateUpdateWithNoErrors(){
        Rebel rebel = new Rebel();
        rebel.setId(1L);
        Mockito.when(rebelGateway.findByParam("id", rebel.getId())).thenReturn(List.of(rebel));
        List<String> validationResult = validator.validateUpdate(rebel);

        Assertions.assertEquals(validationResult, List.of());
    }

    public Rebel getMockRebel(){
        Rebel rebel = new Rebel();
        Inventory inventory = new Inventory();
        inventory.setItems(List.of(new Item("GUN", 1)));
        Location location = new Location();
        location.setGalaxyName("Lactea");
        location.setLongitude(2D);
        location.setLongitude(1D);
        rebel.setInventory(inventory);
        rebel.setGender(Gender.ALIEN);
        rebel.setLocation(location);
        rebel.setName("Langley");
        rebel.setAge(12);
        return rebel;
    }
}