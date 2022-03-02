package com.rebels.alliance.usecases;

import com.rebels.alliance.domains.Rebel;
import com.rebels.alliance.gateways.RebelGateway;
import com.rebels.alliance.gateways.controllers.requests.RebelRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RebelService {
    private final RebelGateway rebelGateway;

    public Rebel createRebel(Rebel rebel) {
        return rebelGateway.register(rebel);
    }

    public Rebel updateRebel(RebelRequest rebel) {
        return rebelGateway.updateRebel(rebel);
    }

    public void deleteRebel(Rebel rebel) {
        rebelGateway.delete(rebel);
    }

    public List<Rebel> listAll() {
        return rebelGateway.findAll();
    }

//    public List<Rebel> listBy
}
