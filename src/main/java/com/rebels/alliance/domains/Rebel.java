package com.rebels.alliance.domains;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Rebel {
    Inventory inventory;
    String id;
    String name;
    int age;
    String gender;
    Location location;
    boolean[] reportStatus = {false, false, false};

}




