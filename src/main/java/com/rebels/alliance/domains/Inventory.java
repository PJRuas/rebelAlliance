package com.rebels.alliance.domains;

import com.rebels.alliance.domains.enums.Items;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Data
public class Inventory {
    Long ownerId;
    boolean status;
    Map<Items, Integer> items = new HashMap<>();
    int points;
}
