package com.rebels.alliance.usecases;

import com.rebels.alliance.domains.Rebel;
import com.rebels.alliance.domains.Traitor;
import com.rebels.alliance.gateways.TraitorGateway;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class TraitorService {
    private final TraitorGateway traitorGateway;
    private final RebelService rebelService;

    public Traitor markTraitor(Rebel rat) {
        if (!Arrays.asList(rat.getReportStatus()).contains(false)) {
            log.info("rebel id {}", rat.getId());
            Traitor markedTraitor = new Traitor(rat);
            traitorGateway.register(markedTraitor);
            rebelService.deleteRebel(rat);
            log.info("{}", markedTraitor.getId());
            return markedTraitor;
        }
        return new Traitor();
    }

    public void markAllTraitors() {
        for (Rebel rebel : rebelService.listAll()) {
            markTraitor(rebel);
        }
    }

    public void delateTraitor(Rebel target, Rebel accuser) {
        traitorGateway.complain(target, accuser);
    }

    public List<Traitor> listAll() {
        return traitorGateway.findAll();
    }

    public <V> List<Traitor> listByParam(String param, V value) {
        return traitorGateway.findByParam(param, value);
    }
}
