package com.rebels.alliance.gateways.controllers.responses;

import com.rebels.alliance.domains.Report;
import com.rebels.alliance.domains.enums.ItemsName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class ReportResponse {

    @ApiModelProperty(value = "Number of times a Rebel was registered in the DB")
    private Long rebelsRegistered;

    @ApiModelProperty(value = "Number of times a Rebel was removed from the DB")
    private Long rebelsDeleted;

    @ApiModelProperty(value = "Rebels currently on DB")
    private Long rebelsActive;

    @ApiModelProperty(value = "Number of times a Rebel was converted to Traitor")
    private Long traitorsRegistered;

    @ApiModelProperty(value = "Number of times a Traitor was removed from the DB")
    private Long traitorsDeleted;

    @ApiModelProperty(value = "Traitors currently on DB")
    private Long traitorsActive;

    @ApiModelProperty(value = "Number of times a new registration occurred")
    private Long totalRegistered;

    @ApiModelProperty(value = "Number of times a delete occurred")
    private Long totalDeleted;

    @ApiModelProperty(value = "Rebels and Traitors currently on DB")
    private Long totalActive;

    @ApiModelProperty(value = "Percentage of Rebels amongst all registers")
    private Double rebelPercentage;

    @ApiModelProperty(value = "Percentage of Traitors amongst all registers")
    private Double traitorPercentage;

    @ApiModelProperty(value = "Average distribution of resources amongst rebels")
    private Map<ItemsName, Double> averageResourcesPerRebel = new HashMap<>();

    @ApiModelProperty(value = "Total score of Active Traitors' items")
    private Long lostPointsDueToTraitors;

    public ReportResponse(Report report) {
        rebelsRegistered = report.getRebelsRegistered();
        rebelsDeleted = report.getRebelsDeleted();
        rebelsActive = report.getRebelsActive();
        traitorsRegistered = report.getTraitorsRegistered();
        traitorsDeleted = report.getTraitorsDeleted();
        traitorsActive = report.getTraitorsActive();
        totalRegistered = report.getTotalRegistered();
        totalDeleted = report.getTotalDeleted();
        totalActive = report.getTotalActive();
        rebelPercentage = report.getRebelPercentage();
        traitorPercentage = report.getTraitorPercentage();
        averageResourcesPerRebel = report.getAverageResourcesPerRebel();
        lostPointsDueToTraitors = report.getLostPointsDueToTraitors();
    }
}
