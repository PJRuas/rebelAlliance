package com.rebels.alliance.domains;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
public class Traitor extends Rebel {

    public Traitor(Rebel rebel) {
        id = rebel.getId();
        name = rebel.getName();
        age = rebel.getAge();
        gender = rebel.getGender();
        location = rebel.getLocation();
        reportStatus = rebel.getReportStatus();
        inventory = rebel.getInventory();
    }
}
