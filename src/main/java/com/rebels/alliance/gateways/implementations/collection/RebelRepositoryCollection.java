package com.rebels.alliance.gateways.implementations.collection;

import com.rebels.alliance.domains.Rebel;
import com.rebels.alliance.gateways.RebelGateway;
import com.rebels.alliance.gateways.controllers.requests.RebelRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class RebelRepositoryCollection implements RebelGateway {

    static public List<Rebel> rebels = new ArrayList<>();

    @Override
    public Rebel register(Rebel rebel) {
        rebels.add(rebel);
        return rebel;
    }

    @Override
    public void delete(Rebel rebel) {
        rebels = rebels.stream()
                .filter(rb -> rb.getId() != rebel.getId())
                .collect(Collectors.toList());
    }

    @Override
    public List<Rebel> findAll() {
        return rebels;
    }

    @Override
    public List<Rebel> findByParam(Predicate<? super Rebel> parameter) {
        return rebels.stream()
                .filter(parameter)
                .collect(Collectors.toList());
    }

    @Override
    public Rebel updateRebel(RebelRequest rebel) {
        for (Rebel rb : rebels) {
            if (Objects.equals(rb.getId(), rebel.getId())) {
                BeanUtils.copyProperties(rebel, rb);
                return rb;
            }
        }
        return null;
    }
}
