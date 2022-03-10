package com.rebels.alliance.domains;

import com.rebels.alliance.domains.enums.ItemsName;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Data
@NoArgsConstructor
public class Report {
    Long rebelsRegistered;
    Long rebelsDeleted;
    Long rebelsActive;
    Long traitorsRegistered;
    Long traitorsDeleted;
    Long traitorsActive;
    Long totalRegistered;
    Long totalDeleted;
    Long totalActive;
    Double rebelPercentage;
    Double traitorPercentage;
    Map<ItemsName, Double> averageResourcesPerRebel = new HashMap<>();
    Long lostPointsDueToTraitors;
}
