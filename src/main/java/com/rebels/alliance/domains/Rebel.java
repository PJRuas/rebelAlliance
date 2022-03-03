package com.rebels.alliance.domains;

import com.rebels.alliance.domains.enums.Genders;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Rebel {
    Long id;
    String name;
    int age;
    Genders gender;
    Location location;
    boolean[] reportStatus = {false, false, false};
    Inventory inventory;

}




