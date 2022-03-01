package com.rebels.alliance.domains.models.DTOs;

import com.rebels.alliance.domains.models.Inventory;
import com.rebels.alliance.domains.models.Location;
import lombok.Data;

@Data
public class RebelDTO {
    private Inventory inventory;
    private String id;
    private String name;
    private Integer age;
    private String gender;
    private Location location;
    private boolean[] reportStatus;
}
