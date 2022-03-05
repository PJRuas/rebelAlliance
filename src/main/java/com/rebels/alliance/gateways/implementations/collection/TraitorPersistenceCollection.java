package com.rebels.alliance.gateways.implementations.collection;

import com.rebels.alliance.domains.Rebel;
import com.rebels.alliance.domains.Traitor;
import com.rebels.alliance.gateways.TraitorGateway;
import lombok.Getter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Repository;

import java.beans.PropertyDescriptor;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class TraitorPersistenceCollection implements TraitorGateway {

    static public List<Traitor> traitors = new ArrayList<>();
    @Getter
    static public Map<Long, Long[]> complaints = new HashMap<>();

    private static String[] getNullPropertyNames(Object source) {
        Class clazz;
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

    @Override
    public Traitor register(Traitor traitor) {
        traitors.add(traitor);
        return traitor;
    }

    @Override
    public void delete(Traitor traitor) {
        traitors = traitors.stream()
                .filter(tr -> tr.getId() != traitor.getId())
                .collect(Collectors.toList());
    }

    @Override
    public List<Traitor> findAll() {
        return traitors;
    }

    @Override
    public <V> List<Traitor> findByParam(String parameter, V value) {
        List<Traitor> matches = new ArrayList<>();
        String lowerCased = parameter.toLowerCase();

        if (lowerCased.equals("id")) {
            matches.addAll(traitors.stream()
                    .filter(r -> r.getId() == Long.parseLong(String.valueOf(value)))
                    .collect(Collectors.toList()));
        }

        if (lowerCased.equals("name")) {
            matches.addAll(traitors.stream()
                    .filter(r -> Objects.equals(r.getName(), String.valueOf(value)))
                    .collect(Collectors.toList()));
        }
        if (lowerCased.equals("age")) {
            matches.addAll(traitors.stream()
                    .filter(r -> r.getAge() == Integer.parseInt(String.valueOf(value)))
                    .collect(Collectors.toList()));
        }
        if (lowerCased.equals("gender")) {
            matches.addAll(traitors.stream()
                    .filter(r -> Objects.equals(String.valueOf(r.getGender()), String.valueOf(value)))
                    .collect(Collectors.toList()));
        }
        if (lowerCased.equals("location")) {
            matches.addAll(traitors.stream()
                    .filter(r -> Objects.equals(r.getLocation().getGalaxyName(), String.valueOf(value)))
                    .collect(Collectors.toList()));
        }

        return matches;
    }

    @Override
    public Traitor updateTraitor(Traitor traitor) {
        for (Traitor tr : traitors) {
            if (Objects.equals(tr.getId(), traitor.getId())) {
                BeanUtils.copyProperties(traitor, tr, getNullPropertyNames(traitor));
                return tr;
            }
        }
        return null;
    }

    @Override
    public void complain(Rebel target, Rebel accuser) {
        Long accuserId = accuser.getId();
        Long[] accusers = checkComplaints(target);
        if (Arrays.asList(accusers).contains(null) && !Arrays.asList(accusers).contains(accuserId)) {
            for (int i = 0; i < accusers.length; i++) {
                if (accusers[i] != null) {
                    target.getReportStatus()[i] = true;
                } else {
                    accusers[i] = accuserId;
                    complaints.put(target.getId(), accusers);
                    target.getReportStatus()[i] = true;
                    break;
                }
            }
        }
    }

    @Override
    public Long[] checkComplaints(Rebel target) {
        return complaints.getOrDefault(target.getId(), new Long[3]);
    }
}
