package com.rebels.alliance.usecases;

import com.rebels.alliance.domains.Item;
import com.rebels.alliance.domains.Rebel;
import com.rebels.alliance.domains.Report;
import com.rebels.alliance.domains.Traitor;
import com.rebels.alliance.domains.enums.ItemsName;
import com.rebels.alliance.gateways.RebelGateway;
import com.rebels.alliance.gateways.TraitorGateway;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class ReportService {
    private final Map<String, Map<String, String>> databaseKeys = new HashMap<>();
    private final Report report = new Report();

    private final BeanWrapper reportWrapper = new BeanWrapperImpl(report);
    private final PropertyDescriptor[] reportPDs = reportWrapper.getPropertyDescriptors();

    private final RebelGateway rebelGateway;
    private final TraitorGateway traitorGateway;
    private final InventoryService inventoryService;
    private final String[] databasePrefixes = {"rebels", "traitors"};
    private final String[] IGNORED_PROPERTIES = {"totalRegistered",
            "totalDeleted",
            "totalActive",
            "rebelPercentage",
            "traitorPercentage",
            "averageResourcesPerRebel",
            "lostPointsDueToTraitors"};

    public Report showReports() {
        getDataFromDB();
        reportDataToObject();
        return report;
    }


    private void reportDataToObject() {
        Map<String, Long> data = getDataFromDB();
        report.setRebelsRegistered(data.get("rebelsRegistered"));
        report.setTraitorsRegistered(data.get("traitorsRegistered"));
        report.setRebelsDeleted(data.get("rebelsDeleted") - report.getTraitorsRegistered());
        report.setRebelsActive(report.getRebelsRegistered() - (report.getRebelsDeleted() + report.getTraitorsRegistered()));
        report.setTraitorsDeleted(data.get("traitorsDeleted"));

        report.setTraitorsActive(report.getTraitorsRegistered() - report.getTraitorsDeleted());
        report.setTotalRegistered(report.getRebelsRegistered());
        report.setTotalDeleted(report.getTraitorsDeleted() + report.getRebelsDeleted());
        report.setTotalActive(report.getRebelsActive() + report.getTraitorsActive());

        report.setRebelPercentage((double) report.getRebelsActive() / report.getTotalActive());
        report.setTraitorPercentage((double) report.getTraitorsActive() / report.getTotalActive());

        Long pointsLost = 0L;

        for (ItemsName item : inventoryService.getItemsValue().keySet()) {
            Double quantity = 0D;
            for (Rebel rebel : rebelGateway.findAll()) {
                for (Item rebelItem : rebel.getInventory().getItems()) {
                    if (ItemsName.valueOf(rebelItem.getName()) == item) {
                        quantity += rebelItem.getQtd();
                    }
                }
            }
            report.getAverageResourcesPerRebel().put(item, quantity / report.getRebelsActive());
        }

        for (Traitor traitor : traitorGateway.findAll()) {
            pointsLost += traitor.getInventory().getPoints();
        }

        report.setLostPointsDueToTraitors(pointsLost);
    }


    private Map<String, Long> getDataFromDB() {
        Map<String, Long> rebelData = rebelGateway.getReportUsage();
        Map<String, Long> traitorData = traitorGateway.getReportUsage();
        Map<String, Map<String, Long>> dataMap = new HashMap<>();
        dataMap.put(databasePrefixes[0], rebelData);
        dataMap.put(databasePrefixes[1], traitorData);

        Map<String, Long> dataValues = new HashMap<>();

        if (databaseKeys.isEmpty()) {
            findKeys(IGNORED_PROPERTIES);
        }

        for (String firstKey : databaseKeys.keySet()) {
            for (String secondKey : databaseKeys.get(firstKey).keySet()) {
                Long finalValue = dataMap.get(firstKey).getOrDefault(databaseKeys.get(firstKey).get(secondKey), 0L);
                dataValues.put(secondKey, finalValue);
            }
        }
        return dataValues;
    }

    private void findKeys(String... toIgnore) {
        Map<String, String> rebelKeys = new HashMap<>();
        Map<String, String> traitorKeys = new HashMap<>();
        Map<String, Map<String, String>> keyMaps = new HashMap<>();
        keyMaps.put(databasePrefixes[0], rebelKeys);
        keyMaps.put(databasePrefixes[1], traitorKeys);

        for (PropertyDescriptor property : reportPDs) {
            String propertyName = property.getName();
            if (!Arrays.asList(toIgnore).contains(propertyName)) {
                String prefix = getSourcePrefix(property);
                String keyValue = getSourceSuffix(property, prefix);
                if (!Arrays.asList(databasePrefixes).contains(prefix)) {
                    continue;
                }
                keyMaps.get(prefix).put(propertyName, keyValue);
            }
        }

        for (String prefix : databasePrefixes) {
            databaseKeys.put(prefix, keyMaps.get(prefix));
        }
    }

    private String getSourcePrefix(PropertyDescriptor propertyDescriptor) {
        String result = "";
        for (String prefix : databasePrefixes) {
            if (propertyDescriptor.getName().startsWith(prefix)) {
                result = prefix;
            }
        }
        return result;
    }

    private String getSourceSuffix(PropertyDescriptor propertyDescriptor, String prefix) {
        return propertyDescriptor.getName().substring(prefix.length());
    }

}
