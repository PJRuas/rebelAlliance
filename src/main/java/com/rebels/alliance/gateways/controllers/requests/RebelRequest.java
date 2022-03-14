package com.rebels.alliance.gateways.controllers.requests;

import com.rebels.alliance.domains.Inventory;
import com.rebels.alliance.domains.Location;
import com.rebels.alliance.domains.Rebel;
import com.rebels.alliance.domains.enums.Gender;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RebelRequest {

    @ApiModelProperty(value = "Rebel's database ID (auto-generated when creating new",
            example = "23")
    private Long id;

    @NotBlank
    @ApiModelProperty(required = true,
            value = "Rebel's name",
            example = "Frodo Baggins")
    private String name;

    @NotBlank
    @ApiModelProperty(required = true,
            value = "Rebel's name",
            example = "602")
    private Integer age;

    @NotBlank
    @ApiModelProperty(required = true,
            value = "Rebel's gender",
            example = "OTHER")
    private Gender gender;

    @NotBlank
    @ApiModelProperty(required = true,
            value = "Rebel's current location",
            example = "{" +
                    "\"galaxyName\":\"Middle-earth\"," +
                    "\"latitude\":-213.4," +
                    "\"longitude\":306" +
                    "}")
    private Location location;

    @NotBlank
    @ApiModelProperty(required = false,
            value = "Rebel's reports on system",
            example = "[false, false, false]")
    private boolean[] reportStatus;

    @NotBlank
    @ApiModelProperty(required = true,
            value = "Rebel's initial possessions",
            example = "{" +
                    "\"items\": " +
                    "[" +
                    "{\"name\":\"WATER\", \"qtd\":3}," +
                    "{\"name\":\"GUN\", \"qtd\":1}" +
                    "]" +
                    "}")
    private Inventory inventory;

    public Rebel toRebel() {
        Rebel rebel = new Rebel();
        rebel.setId(getId());
        rebel.setName(getName());
        rebel.setAge(getAge());
        rebel.setGender(getGender());
        rebel.setLocation(getLocation());
        rebel.setReportStatus(getReportStatus());
        rebel.setInventory(getInventory());
        return rebel;
    }
}
