package com.rebels.alliance.gateways;

import com.rebels.alliance.domains.Rebel;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Component
public interface RebelGateway {
    Rebel register(Rebel rebel);

    void delete(Rebel rebel);

    List<Rebel> findAll();

    <V> List<Rebel> findByParam(String parameter, V value);

    Rebel updateRebel(Rebel rebel);
}
