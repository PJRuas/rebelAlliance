package com.rebels.alliance.gateways.controllers.requests;

import com.rebels.alliance.domains.Inventory;
import com.rebels.alliance.domains.Location;
import lombok.Data;

@Data
public class RebelRequest {
    private Inventory inventory;
    private String id;
    private String name;
    private Integer age;
    private String gender;
    private Location location;
    private boolean[] reportStatus;
}
