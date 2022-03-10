package com.rebels.alliance.gateways;

import com.rebels.alliance.domains.Rebel;
import com.rebels.alliance.domains.Traitor;

import java.util.List;
import java.util.Map;

public interface TraitorGateway {
    Map<String, Long> getReportUsage();

    Traitor register(Traitor traitor);

    void delete(Traitor traitor);

    List<Traitor> findAll();

    <V> List<Traitor> findByParam(String parameter, V value);

    Traitor updateTraitor(Traitor traitor);

    void complain(Rebel target, Rebel accuser);

    Long[] checkComplaints(Rebel target);
}
