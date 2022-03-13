package com.rebels.alliance.domains.dto;

import com.rebels.alliance.domains.Item;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class TradeRequest {
    Long rebel1Id;
    Long rebel2Id;
    List<Item> itemsRebel1;
    List<Item> itemsRebel2;
}
