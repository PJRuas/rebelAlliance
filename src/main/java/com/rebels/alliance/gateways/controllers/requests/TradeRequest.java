package com.rebels.alliance.gateways.controllers.requests;

import com.rebels.alliance.domains.Item;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class TradeRequest {

    @NotBlank
    @ApiModelProperty(required = true, value = "First Rebel's Id", example = "25")
    Long rebel1Id;

    @NotBlank
    @ApiModelProperty(required = true, value = "Second Rebel's Id", example = "3")
    Long rebel2Id;

    @Valid
    @ApiModelProperty(required = true,
            value = "First Rebel's trading item(s)",
            example = "[{\"name\":\"WATER\", \"qtd\":3}, \n" +
                    "{\"name\":\"GUN\", \"qtd\":1}]")
    List<Item> itemsRebel1;

    @NotBlank
    @ApiModelProperty(required = true,
            value = "Second Rebel's trading item(s)",
            example = "[{\"name\":\"FOOD\", \"qtd\":10}]")
    List<Item> itemsRebel2;
}
