package com.rebels.alliance.gateways.implementations.collection;

import com.rebels.alliance.domains.Rebel;
import com.rebels.alliance.domains.Traitor;
import com.rebels.alliance.gateways.RebelGateway;
import com.rebels.alliance.usecases.InventoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Repository;

import java.beans.PropertyDescriptor;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class RebelPersistenceCollection implements RebelGateway {

    static public List<Rebel> rebels = new ArrayList<>();
    public static Map<String, Long> reportUseLog = new HashMap<>();
    private static InventoryService inventoryService;
    private static TraitorPersistenceCollection traitorPersistenceCollection = new TraitorPersistenceCollection();
    private static long REBELID = 0;

    private static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] propertyDescriptors = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor pd : propertyDescriptors) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    private void updateReportLog(String key) {
        Long usages = reportUseLog.getOrDefault(key, 0L);
        reportUseLog.put(key, usages + 1);
    }

    private Long generateID() {
        Set<Long> usedIDs = new TreeSet<>();
        for (Traitor traitor : traitorPersistenceCollection.findAll()) {
            usedIDs.add(traitor.getId());
        }
        for (Rebel rebel : findAll()) {
            usedIDs.add(rebel.getId());
        }
        for (Long i = 0L; i < REBELID; i++) {
            if (!usedIDs.contains(i)) {
                return i;
            }
        }
        return REBELID++;
    }

    @Override
    public Map<String, Long> getReportUsage() {
        return reportUseLog;
    }

    @Override
    public Rebel register(Rebel rebel) {
        Rebel newRebel = rebel;
        newRebel.setId(generateID());
        newRebel.getInventory().setOwnerId(newRebel.getId());
//        newRebel.getInventory().setPoints(inventoryService.getInventoryPoints(newRebel.getInventory()));
        newRebel.setReportStatus(new boolean[]{false, false, false});
        rebels.add(newRebel);
        updateReportLog("Registered");
        return newRebel;
    }

    @Override
    public void delete(Rebel rebel) {
        rebels = rebels.stream()
                .filter(rb -> rb.getId() != rebel.getId())
                .collect(Collectors.toList());
        updateReportLog("Deleted");
    }

    @Override
    public List<Rebel> findAll() {
        updateReportLog("Searched");
        return rebels;
    }

    @Override
    public <V> List<Rebel> findByParam(String parameter, V value) {
        List<Rebel> matches = new ArrayList<>();
        String lowerCased = parameter.toLowerCase();

        if (lowerCased.equals("id")) {
            matches.addAll(rebels.stream()
                    .filter(r -> r.getId() == Long.parseLong(String.valueOf(value)))
                    .collect(Collectors.toList()));
        }

        if (lowerCased.equals("name")) {
            matches.addAll(rebels.stream()
                    .filter(r -> Objects.equals(r.getName(), String.valueOf(value)))
                    .collect(Collectors.toList()));
        }
        if (lowerCased.equals("age")) {
            matches.addAll(rebels.stream()
                    .filter(r -> r.getAge() == Integer.parseInt(String.valueOf(value)))
                    .collect(Collectors.toList()));
        }
        if (lowerCased.equals("gender")) {
            matches.addAll(rebels.stream()
                    .filter(r -> Objects.equals(String.valueOf(r.getGender()), String.valueOf(value)))
                    .collect(Collectors.toList()));
        }
        if (lowerCased.equals("location")) {
            matches.addAll(rebels.stream()
                    .filter(r -> Objects.equals(r.getLocation().getGalaxyName(), String.valueOf(value)))
                    .collect(Collectors.toList()));
        }

        updateReportLog("Searched by param [" + lowerCased + "]");

        return matches;
    }

    @Override
    public Rebel updateRebel(Rebel rebel) {
        for (Rebel rb : rebels) {
            if (Objects.equals(rb.getId(), rebel.getId())) {
                BeanUtils.copyProperties(rebel, rb, getNullPropertyNames(rebel));
                updateReportLog("Updated");
                return rb;
            }
        }
        return null;
    }

    @Override
    public Rebel findOneById(int id) {
        return rebels.stream()
                .filter(r -> r.getId() == id)
                .collect(Collectors.toList()).get(0);
    }
}
