package com.rebels.alliance.gateways.controllers.responses;

import com.rebels.alliance.domains.Inventory;
import com.rebels.alliance.domains.Location;
import com.rebels.alliance.domains.Traitor;
import com.rebels.alliance.domains.enums.Gender;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TraitorResponse {
    @ApiModelProperty(value = "Traitor's database ID (herd from Rebel)",
            example = "8")
    private Long id;

    @ApiModelProperty(
            value = "Traitor's name",
            example = "Sméagol")
    private String name;

    @ApiModelProperty(
            value = "Traitor's age",
            example = "734")
    private int age;

    @ApiModelProperty(
            value = "Traitor's gender",
            example = "OTHER")
    private Gender gender;

    @ApiModelProperty(
            value = "Traitor's current location",
            example = "{" +
                    "\"galaxyName\":\"Middle-earth\"," +
                    "\"latitude\":-605," +
                    "\"longitude\":402.8" +
                    "}")
    private Location location;

    @ApiModelProperty(
            value = "Traitor's reports on system",
            example = "[true, true, true]")
    private boolean[] reportStatus;

    @ApiModelProperty(
            value = "Traitor's locked possessions",
            example = "{" +
                    "\"items\": " +
                    "[" +
                    "{\"name\":\"WATER\", \"qtd\":3}," +
                    "{\"name\":\"GUN\", \"qtd\":1}" +
                    "]" +
                    "}")
    private Inventory inventory;


    public TraitorResponse(Traitor traitor) {
        id = traitor.getId();
        name = traitor.getName();
        age = traitor.getAge();
        gender = traitor.getGender();
        location = traitor.getLocation();
        reportStatus = traitor.getReportStatus();
        inventory = traitor.getInventory();
    }
}
