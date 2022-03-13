package com.rebels.alliance.gateways.controllers.responses;

import com.rebels.alliance.domains.Inventory;
import com.rebels.alliance.domains.Location;
import com.rebels.alliance.domains.Rebel;
import com.rebels.alliance.domains.enums.Gender;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RebelResponse {

    @ApiModelProperty(value = "Rebel's database ID (auto-generated when creating new)",
            example = "23")
    private Long id;

    @ApiModelProperty(
            value = "Rebel's name",
            example = "Frodo Baggins")
    private String name;

    @ApiModelProperty(
            value = "Rebel's age",
            example = "602")
    private int age;

    @ApiModelProperty(
            value = "Rebel's gender",
            example = "OTHER")
    private Gender gender;

    @ApiModelProperty(
            value = "Rebel's current location",
            example = "{" +
                    "\"galaxyName\":\"Middle-earth\"," +
                    "\"latitude\":-213.4," +
                    "\"longitude\":306" +
                    "}")
    private Location location;

    @ApiModelProperty(
            value = "Rebel's reports on system",
            example = "[false, false, false]")
    private boolean[] reportStatus;

    @ApiModelProperty(
            value = "Rebel's initial possessions",
            example = "{" +
                    "\"items\": " +
                    "[" +
                    "{\"name\":\"WATER\", \"qtd\":3}," +
                    "{\"name\":\"GUN\", \"qtd\":1}" +
                    "]" +
                    "}")
    private Inventory inventory;


    public RebelResponse(Rebel rebel) {
        id = rebel.getId();
        name = rebel.getName();
        age = rebel.getAge();
        gender = rebel.getGender();
        location = rebel.getLocation();
        reportStatus = rebel.getReportStatus();
        inventory = rebel.getInventory();
    }
}
