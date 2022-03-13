package com.rebels.alliance.domains;

import com.rebels.alliance.domains.enums.Gender;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Rebel {
    Long id;
    String name;
    int age;
    Gender gender;
    Location location;
    boolean[] reportStatus = {false, false, false};
    Inventory inventory;

}




