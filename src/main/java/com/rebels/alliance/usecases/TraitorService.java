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
        boolean[] complaints = rat.getReportStatus();
        if (complaints[0] && complaints[1] && complaints[2]) {
            Traitor markedTraitor = new Traitor(rat);
            log.info("{}", markedTraitor.getReportStatus());
            log.info("{}", Arrays.asList(rat.getReportStatus()).contains(false));
            traitorGateway.register(markedTraitor);
            rebelService.deleteRebel(rat);
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

    public void delete(Traitor traitorToDelete) {
        traitorGateway.delete(traitorToDelete);
    }
}
