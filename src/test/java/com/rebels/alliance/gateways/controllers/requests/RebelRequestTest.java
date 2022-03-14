package com.rebels.alliance.gateways.controllers.requests;

import com.rebels.alliance.domains.Inventory;
import com.rebels.alliance.domains.Item;
import com.rebels.alliance.domains.Location;
import com.rebels.alliance.domains.Rebel;
import com.rebels.alliance.domains.enums.Gender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RebelRequestTest {
    public RebelRequest getRebelRequest(){
        Inventory inventory = new Inventory();
        inventory.setItems(new ArrayList<>(List.of(new Item("GUN", 1))));

        Location location = new Location();
        location.setGalaxyName("Via láctea");
        location.setLongitude(22D);
        location.setLongitude(25D);

        RebelRequest rebel = new RebelRequest();

        rebel.setAge(18);
        rebel.setGender(Gender.ALIEN);
        rebel.setInventory(inventory);
        rebel.setLocation(location);
        rebel.setName("Asuka");

        return rebel;
    }

    @Test
    void toRebel() {
        RebelRequest rebelRequest = getRebelRequest();
        Rebel rebel = rebelRequest.toRebel();
        Assertions.assertEquals(rebelRequest.toRebel(), rebel);
    }
}