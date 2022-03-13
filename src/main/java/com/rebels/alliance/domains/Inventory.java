package com.rebels.alliance.domains;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
public class Inventory {
    Long ownerId;
    List<Item> items;
    int points;
}
