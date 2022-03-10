package com.rebels.alliance.domains.dto;

import com.rebels.alliance.domains.Item;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class TradeDto {
    Long rebel1Id;
    Long rebel2Id;
    List<Item> itemRebel1;
    List<Item> itemRebel2;
}
