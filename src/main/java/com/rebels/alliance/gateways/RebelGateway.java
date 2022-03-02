package com.rebels.alliance.gateways;

import com.rebels.alliance.domains.Rebel;
import com.rebels.alliance.gateways.controllers.requests.RebelRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.function.Predicate;

@Repository
@Component
public interface RebelGateway {
    Rebel register(Rebel rebel);

    void delete(Rebel rebel);

    List<Rebel> findAll();

    List<Rebel> findByParam(Predicate<? super Rebel> parameter);

    Rebel updateRebel(RebelRequest rebel);
}
